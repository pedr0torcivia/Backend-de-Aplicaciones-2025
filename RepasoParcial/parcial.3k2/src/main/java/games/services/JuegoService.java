package games.services;

import games.entities.*;
import games.repositories.JuegoRepository;
import games.services.interfaces.IService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class JuegoService implements IService<Juego, Integer> {

    private final JuegoRepository repo;
    private final GeneroService generoService;
    private final DesarrolladorService desarrolladorService;
    private final PlataformaService plataformaService;

    public JuegoService() {
        this.repo = new JuegoRepository();
        this.generoService = new GeneroService();
        this.desarrolladorService = new DesarrolladorService();
        this.plataformaService = new PlataformaService();
    }

    public JuegoService(JuegoRepository repo, GeneroService gSrv, DesarrolladorService dSrv, PlataformaService pSrv) {
        this.repo = repo;
        this.generoService = gSrv;
        this.desarrolladorService = dSrv;
        this.plataformaService = pSrv;
    }

    @Override
    public Juego getById(Integer id) {
        return repo.getById(id);
    }

    @Override
    public Juego getOrCreateByName(String name) {
        if (name == null) return null;
        String normalized = name.trim().replaceAll("\\s+", " ");
        if (normalized.isEmpty()) return null;

        Juego j = repo.getByName(normalized);
        if (j == null) {
            j = new Juego();
            j.setTitulo(normalized);
            repo.create(j);
        }
        return j;
    }

    @Override
    public List<Juego> getAll() {
        return repo.getAllList();
    }

    @Override
    public Stream<Juego> getAllStream() {
        return repo.getAllStream();
    }

    public void bulkInsert(File file) throws IOException {
        if (this.existsAny()) {
            System.out.println("⚠ Ya hay juegos cargados en la BD. No se realizará la importación.");
            return;
        }

        try (Stream<String> lines = Files.lines(Paths.get(file.toURI()), StandardCharsets.UTF_8)) {
            lines.skip(1) // salta encabezado
                .filter(l -> !l.trim().isEmpty())
                .forEach(linea -> {
                    Juego j = procesarLinea(linea);
                    if (j != null) {
                        repo.create(j);
                    }
                });
        }
    }

    public Juego procesarLinea(String linea) {
        try {
            String[] t = linea.split(";", -1); // usa ; como separador
            if (t.length < 10) {
                System.err.println("⚠ Línea inválida: " + linea);
                return null;
            }

            Juego j = new Juego();

            // Title
            j.setTitulo(t[0].trim());

            // Año (si hay)
            String fecha = t[1].trim();
            if (fecha.length() >= 4) {
                String ultimos4 = fecha.substring(fecha.length() - 4);
                try {
                    j.setFechaLanzamiento(Integer.parseInt(ultimos4));
                } catch (NumberFormatException e) {
                    j.setFechaLanzamiento(null);
                }
            }

            // Developers
            j.setDesarrollador(
                desarrolladorService.getOrCreateByName(
                    t[2].replace("[", "").replace("]", "").replace("'", "").trim()
                )
            );

            // Summary (si queda vacío, igual no es null => cumple @Column(nullable=false))
            j.setResumen(t[3].trim());

            // Platforms
            j.setPlataforma(
                plataformaService.getOrCreateByName(
                    t[4].replace("[", "").replace("]", "").replace("'", "").trim()
                )
            );

            // Genres
            j.setGenero(
                generoService.getOrCreateByName(
                    t[5].replace("[", "").replace("]", "").replace("'", "").trim()
                )
            );

            // Rating (parse seguro)
            String ratingStr = t[6].trim().replace(",", ".");
            if (ratingStr.equalsIgnoreCase("N/A") || ratingStr.isEmpty()) {
                j.setRating(null);
            } else {
                try {
                    j.setRating(Double.parseDouble(ratingStr));
                } catch (NumberFormatException e) {
                    j.setRating(null);
                }
            }

            // Plays
            String plays = t[7].toLowerCase().replace("k", "000").replace(".", "");
            j.setJuegosFinalizados(parseIntSafe(plays));

            // Playing
            String playing = t[8].toLowerCase().replace("k", "000").replace(".", "");
            j.setJugando(parseIntSafe(playing));

            // ESRB -> normalizo a códigos cortos para que entre en VARCHAR(4)
            j.setClasificacionEsrb(normalizeEsrb(t[9]));

            return j;
        } catch (Exception e) {
            System.err.println("⚠ Error procesando línea: " + linea);
            return null;
        }
    }

    private String normalizeEsrb(String raw) {
        if (raw == null) return null;
        String s = raw.trim();
        if (s.isEmpty()) return null;

        String u = s.toUpperCase();
        if (u.equals("E") || u.equals("EVERYONE")) return "E";
        if (u.equals("E10+") || u.equals("EVERYONE 10+") || u.equals("EVERYONE10+")) return "E10+";
        if (u.equals("T") || u.equals("TEEN")) return "T";
        if (u.equals("M") || u.equals("MATURE")) return "M";
        if (u.equals("AO") || u.startsWith("ADULTS ONLY")) return "AO";
        if (u.equals("RP") || u.startsWith("RATING PENDING")) return "RP";
        // fallback para valores raros: recorta a 4 para no romper el DDL
        return u.length() > 4 ? u.substring(0, 4) : u;
    }

    private Integer parseIntSafe(String s) {
        try {
            return Integer.parseInt(s.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean existsAny() {
        return !repo.getAllList().isEmpty();
    }

    public List<Object[]> getTop5Generos() {
        return repo.findTop5Generos();
    }

    public List<Object[]> cantidadDeJuegosPorDesarrollador() {
        return repo.findCantidadDeJuegosPorDesarrollador();
    }

    public long cantidadDeJuegosConMasDeUnDesarrollador(){
        return repo.findJuegosConMasDeUnDesarrollador();
    }

    public String getBestDeveloper() {
        return repo.findBestDeveloper();
    }
}
