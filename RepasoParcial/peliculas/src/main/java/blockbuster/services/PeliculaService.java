package blockbuster.services;

import blockbuster.entities.Clasificacion;
import blockbuster.entities.Director;
import blockbuster.entities.Genero;
import blockbuster.entities.Pelicula;
import blockbuster.repositories.PeliculaRepository;
import blockbuster.services.interfaces.IService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class PeliculaService implements IService<Pelicula, Integer> {

    private final PeliculaRepository repo;
    private final GeneroService generoService;
    private final DirectorService directorService;

    public PeliculaService(PeliculaRepository repo,
                           GeneroService generoService,
                           DirectorService directorService) {
        this.repo = repo;
        this.generoService = generoService;
        this.directorService = directorService;
    }

    public PeliculaService() {
        this.repo = new PeliculaRepository();
        // Si no querés instanciarlos acá, dejalos en null y el bulkInsert validará.
        this.generoService = new GeneroService();
        this.directorService = new DirectorService();
    }

    public PeliculaService(PeliculaRepository repo) {
        this.repo = repo;
        this.generoService = new GeneroService();
        this.directorService = new DirectorService();
    }

    @Override
    public Pelicula getById(Integer id) {
        return repo.getById(id);
    }

    @Override
    public Pelicula getOrCreateByName(String name) {
        if (name == null) return null;
        String normalized = name.trim().replaceAll("\\s+", " ");
        if (normalized.isEmpty()) return null;

        Pelicula p = repo.getByName(normalized);
        if (p == null) {
            p = new Pelicula();
            p.setTitulo(normalized);
            repo.create(p);
        }
        return p;
    }

    @Override
    public List<Pelicula> getAll() {
        return repo.getAllList();
    }

    @Override
    public Stream<Pelicula> getAllStream() {
        return repo.getAllStream();
    }

    // =========================
    // Bulk insert desde CSV (;)
    // Header: titulo;fechaEstreno;precioBaseAlquiler;clasificacion;genero;director
    // Ej:    Pelicula 001;2023-09-23;9.92;ATP;Drama;Ximena Ledesma
    // =========================
    public void bulkInsert(File fileToImport) throws IOException {
        Files.lines(Paths.get(fileToImport.toURI()))
            .skip(1)
            .forEach(linea -> {
                Pelicula peli = procesarLinea(linea);
                this.repo.create(peli);
            });
    }

    private Pelicula procesarLinea(String linea) {
        String[] t = linea.split(";", -1);

        Pelicula p = new Pelicula();
        p.setTitulo(t[0].trim());
        p.setFechaEstreno(LocalDate.parse(t[1].trim())); // yyyy-MM-dd
        p.setPrecioBaseAlquiler(Double.parseDouble(t[2].trim().replace(',', '.')));
        p.setClasificacion(
            Clasificacion.valueOf(
                t[3].trim().toUpperCase(Locale.ROOT).replace(' ', '_').replace('-', '_')
            )
        );

        Genero genero = generoService.getOrCreateByName(t[4].trim());
        Director director = directorService.getOrCreateByName(t[5].trim());

        p.setGenero(genero);
        p.setDirector(director);

        return p;
    }

    public List<Pelicula> getPeliculasXDirector(String director) {
        return repo.getPeliculasXDirector(director);
    }

    public long countPeliculasRecientes(int dias) {
        LocalDate fechaLimite = LocalDate.now().minusDays(dias);
        return getAllStream()
                .filter(pelicula -> pelicula.getFechaEstreno() != null && pelicula.getFechaEstreno().isAfter(fechaLimite))
                .count();
    }

    public List<Object[]> getPromedioPrecioPorGenero() {
        return repo.promedioPrecioPorGenero();
    }

    public Pelicula getPeliculaMasReciente() {
        return repo.peliculaMasReciente();
    }
}
