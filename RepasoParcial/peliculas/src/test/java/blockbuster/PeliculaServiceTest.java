package blockbuster;

import blockbuster.entities.Clasificacion;
import blockbuster.entities.Director;
import blockbuster.entities.Genero;
import blockbuster.entities.Pelicula;
import blockbuster.repositories.PeliculaRepository;
import blockbuster.services.DirectorService;
import blockbuster.services.GeneroService;
import blockbuster.services.PeliculaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeliculaServiceTest {

    @Mock
    PeliculaRepository repo;

    @Mock
    GeneroService generoService;

    @Mock
    DirectorService directorService;

    @Captor
    ArgumentCaptor<Pelicula> peliculaCaptor;

    PeliculaService service;

    @BeforeEach
    void setUp() {
        service = new PeliculaService(repo, generoService, directorService);
    }

    // ========== bulkInsert (importar desde CSV) ==========
    @Test
    void bulkInsert_debeParsearCSV_yCrearPeliculas(@TempDir Path tempDir) throws IOException {
        String csv = String.join("\n",
            "titulo;fechaEstreno;precioBaseAlquiler;clasificacion;genero;director",
            "Peli Uno;2023-09-23;9,92;ATP;Drama;Ximena Ledesma",
            "Peli Dos;2024-01-10;15.50;ATP;Accion;Juan Pérez"
        );
        Path archivo = tempDir.resolve("peliculas.csv");
        Files.writeString(archivo, csv);

        Genero drama = new Genero(); drama.setNombre("Drama");
        Genero accion = new Genero(); accion.setNombre("Accion");
        Director ximena = new Director(); ximena.setNombre("Ximena Ledesma");
        Director juan   = new Director(); juan.setNombre("Juan Pérez");

        when(generoService.getOrCreateByName("Drama")).thenReturn(drama);
        when(generoService.getOrCreateByName("Accion")).thenReturn(accion);
        when(directorService.getOrCreateByName("Ximena Ledesma")).thenReturn(ximena);
        when(directorService.getOrCreateByName("Juan Pérez")).thenReturn(juan);

        service.bulkInsert(archivo.toFile());

        verify(repo, times(2)).create(peliculaCaptor.capture());
        List<Pelicula> creadas = peliculaCaptor.getAllValues();

        assertThat(creadas).hasSize(2);

        Pelicula p1 = creadas.get(0);
        assertThat(p1.getTitulo()).isEqualTo("Peli Uno");
        assertThat(p1.getFechaEstreno()).isEqualTo(LocalDate.parse("2023-09-23"));
        assertThat(p1.getPrecioBaseAlquiler()).isEqualTo(9.92d);
        assertThat(p1.getClasificacion()).isEqualTo(Clasificacion.valueOf("ATP"));
        assertThat(p1.getGenero()).isSameAs(drama);
        assertThat(p1.getDirector()).isSameAs(ximena);

        Pelicula p2 = creadas.get(1);
        assertThat(p2.getTitulo()).isEqualTo("Peli Dos");
        assertThat(p2.getFechaEstreno()).isEqualTo(LocalDate.parse("2024-01-10"));
        assertThat(p2.getPrecioBaseAlquiler()).isEqualTo(15.50d);
        assertThat(p2.getClasificacion()).isEqualTo(Clasificacion.valueOf("ATP"));
        assertThat(p2.getGenero()).isSameAs(accion);
        assertThat(p2.getDirector()).isSameAs(juan);
    }

    // ========== listar todas ==========
    @Test
    void getAll_debeDelegarEnRepositorio() {
        var p = new Pelicula(); p.setTitulo("X");
        when(repo.getAllList()).thenReturn(List.of(p));

        var result = service.getAll();

        assertThat(result).containsExactly(p);
        verify(repo).getAllList();
    }

    // ========== listar por director ==========
    @Test
    void getPeliculasXDirector_debeDelegarEnRepositorio() {
        var p = new Pelicula(); p.setTitulo("DirX");
        when(repo.getPeliculasXDirector("Nolan")).thenReturn(List.of(p));

        var result = service.getPeliculasXDirector("Nolan");

        assertThat(result).containsExactly(p);
        verify(repo).getPeliculasXDirector("Nolan");
    }

    // ========== contar recientes ==========
    @Test
    void countPeliculasRecientes_filtraPorFechaLimite() {
        Pelicula vieja = nuevaPeli("A", LocalDate.now().minusDays(30));
        Pelicula reciente1 = nuevaPeli("B", LocalDate.now().minusDays(3));
        Pelicula reciente2 = nuevaPeli("C", LocalDate.now().minusDays(9));
        Pelicula sinFecha  = nuevaPeli("D", null);

        // <-- CLAVE: devolver un Stream NUEVO en cada llamada
        when(repo.getAllStream()).thenAnswer(inv ->
            Stream.of(vieja, reciente1, reciente2, sinFecha)
        );

        long count10dias = service.countPeliculasRecientes(10);
        assertThat(count10dias).isEqualTo(2);

        long count5dias = service.countPeliculasRecientes(5);
        assertThat(count5dias).isEqualTo(1);

        verify(repo, times(2)).getAllStream();
    }
    // ========== película más reciente ==========
    @Test
    void getPeliculaMasReciente_debeDelegarEnRepositorio() {
        Pelicula p = nuevaPeli("MasNueva", LocalDate.now());
        when(repo.peliculaMasReciente()).thenReturn(p);

        var res = service.getPeliculaMasReciente();

        assertThat(res).isSameAs(p);
        verify(repo).peliculaMasReciente();
    }

    // ========== getOrCreateByName ==========
    @Test
    void getOrCreateByName_normalizaYCreayNoDuplica() {
        Pelicula existente = new Pelicula(); existente.setTitulo("Peli X");
        when(repo.getByName("Peli X")).thenReturn(existente);

        var r1 = service.getOrCreateByName("   Peli   X   ");
        assertThat(r1).isSameAs(existente);
        verify(repo, never()).create(any());

        // Reseteamos sólo interacciones si querés (opcional). Para claridad dejo reset(repo).
        reset(repo);
        when(repo.getByName("Peli Y")).thenReturn(null);

        var r2 = service.getOrCreateByName("  Peli     Y ");
        assertThat(r2.getTitulo()).isEqualTo("Peli Y");
        verify(repo).create(peliculaCaptor.capture());
        assertThat(peliculaCaptor.getValue().getTitulo()).isEqualTo("Peli Y");

        assertThat(service.getOrCreateByName(null)).isNull();
        assertThat(service.getOrCreateByName("   ")).isNull();
    }

    // ========== getById ==========
    @Test
    void getById_debeDelegarEnRepositorio() {
        Pelicula p = new Pelicula();
        when(repo.getById(7)).thenReturn(p);

        assertThat(service.getById(7)).isSameAs(p);
        verify(repo).getById(7);
    }

    // Helper
    private static Pelicula nuevaPeli(String titulo, LocalDate fecha) {
        Pelicula p = new Pelicula();
        p.setTitulo(titulo);
        p.setFechaEstreno(fecha);
        return p;
    }
}
