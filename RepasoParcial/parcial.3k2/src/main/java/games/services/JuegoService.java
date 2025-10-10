package games.services;

import games.entities.*;
import games.repositories.JuegoRepository;
import games.services.interfaces.IService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class JuegoService implements IService<Juego, Integer> {

    private final JuegoRepository repo;
    private final GeneroService generoService;
    private final DesarrolladorService desarrolladorService;
    private final PlataformaService plataformaService;
    private final List<Juego> importados = new ArrayList<>();

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
                         importados.add(j); // estructura de datos en memoria (requisito)
                         repo.create(j);    // persiste en BD
                     }
                 });
        }
    }

    /** Parsea una línea del CSV y devuelve un Juego listo para persistir o null si no cumple condiciones. */
    public Juego procesarLinea(String linea) {
        try {
            String[] t = linea.split(";", -1); // usa ; como separador
            // Esperamos al menos 10 columnas (ajustá si tu layout cambia)
            if (t.length < 10) {
                System.err.println("⚠ Línea inválida (columnas): " + linea);
                return null;
            }

            // Campos esperados:
            // 0: título
            // 1: fecha (usamos últimos 4 como año si aplica)
            // 2: desarrollador(es)
            // 3: resumen
            // 4: plataforma(s)
            // 5: género(s)
            // 6: rating (double o N/A)
            // 7: plays (puede venir "10.2k")
            // 8: playing (idem)
            // 9: clasificación ESRB (vacío => UNRATED)

            // Validación condición: al menos un desarrollador y al menos un género
            List<String> devs = parseNamesList(t[2]);
            List<String> generos = parseNamesList(t[5]);
            if (devs.isEmpty() || generos.isEmpty()) {
                System.err.println("↷ Línea omitida (sin desarrollador o sin género): " + linea);
                return null;
            }
            List<String> plats = parseNamesList(t[4]);

            Juego j = new Juego();

            // Título
            j.setTitulo(t[0].trim());

            // Año (si hay): tomamos los últimos 4 dígitos de la fecha
            String fecha = t[1].trim();
            if (fecha.length() >= 4) {
                String ultimos4 = fecha.substring(fecha.length() - 4);
                try {
                    j.setFechaLanzamiento(Integer.parseInt(ultimos4));
                } catch (NumberFormatException e) {
                    j.setFechaLanzamiento(null);
                }
            }

            // Resumen (si queda vacío, no es null para cumplir @Column(nullable=false) si aplica)
            j.setResumen(t[3].trim());

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

            // Plays / Playing (normalizamos "k" y puntos)
            String plays = t[7].toLowerCase(Locale.ROOT).replace("k", "000").replace(".", "");
            j.setJuegosFinalizados(parseIntSafe(plays));

            String playing = t[8].toLowerCase(Locale.ROOT).replace("k", "000").replace(".", "");
            j.setJugando(parseIntSafe(playing));

            // Clasificación ESRB (vacío => UNRATED, códigos cortos)
            j.setClasificacionEsrb(normalizeEsrbOrUnrated(t[9]));

            // Asociaciones (si tu modelo es 1-1: tomamos el primero)
            j.setDesarrollador(desarrolladorService.getOrCreateByName(devs.get(0)));
            j.setGenero(generoService.getOrCreateByName(generos.get(0)));
            if (!plats.isEmpty()) {
                j.setPlataforma(plataformaService.getOrCreateByName(plats.get(0)));
            }

            return j;

        } catch (Exception e) {
            System.err.println("⚠ Error procesando línea: " + linea + " → " + e.getMessage());
            return null;
        }
    }

    /** Devuelve true si ya hay registros en BD (para evitar re-importar). */
    public boolean existsAny() {
        return !repo.getAllList().isEmpty();
    }

    // =================== Helpers ===================

    /** Parsea una cadena con posibles múltiples nombres en formatos comunes.
     *  Soporta separadores: coma, ampersand, slash, pipe. Limpia [ ] ' " y espacios.
     */
    private List<String> parseNamesList(String raw) {
        if (raw == null) return List.of();
        String cleaned = raw.replace("[", "")
                            .replace("]", "")
                            .replace("'", "")
                            .replace("\"", "")
                            .trim();
        if (cleaned.isEmpty()) return List.of();
        // Separadores: ",", "&", "/", "|"
        String[] parts = cleaned.split("\\s*(,|&|/|\\|)\\s+|\\s*,\\s*");
        List<String> out = new ArrayList<>();
        for (String p : parts) {
            String v = p.trim();
            if (!v.isEmpty()) out.add(v);
        }
        return out;
    }

    /** Normaliza ESRB y devuelve "UNRATED" si viene vacío o nulo. */
private String normalizeEsrbOrUnrated(String raw) {
    if (raw == null || raw.trim().isEmpty()) return "NR"; // <- antes devolvías "UNRATED"
    String u = raw.trim().toUpperCase(Locale.ROOT);

    if (u.equals("E") || u.equals("EVERYONE")) return "E";
    if (u.equals("E10+") || u.equals("EVERYONE 10+") || u.equals("EVERYONE10+")) return "E10+";
    if (u.equals("T") || u.equals("TEEN")) return "T";
    if (u.equals("M") || u.equals("MATURE")) return "M";
    if (u.equals("AO") || u.startsWith("ADULTS ONLY")) return "AO";
    if (u.equals("RP") || u.startsWith("RATING PENDING")) return "RP";

    // fallback: recortar a 4 para no violar el DDL
    return u.length() > 4 ? u.substring(0, 4) : u;
}

    /** Int parse tolerante: limpia no-dígitos. */
    private Integer parseIntSafe(String s) {
        try {
            return Integer.parseInt(s.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return 0;
        }
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
