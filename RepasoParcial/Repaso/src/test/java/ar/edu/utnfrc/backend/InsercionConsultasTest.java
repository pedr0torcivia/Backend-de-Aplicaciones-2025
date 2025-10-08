package ar.edu.utnfrc.backend;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;


// dependencias del proyecto
import ar.edu.utnfrc.backend.daos.AutorDAO;
import ar.edu.utnfrc.backend.daos.LibroDAO;
import ar.edu.utnfrc.backend.menu.Actions;
import ar.edu.utnfrc.backend.entities.Autor;
import ar.edu.utnfrc.backend.entities.Libro;
import ar.edu.utnfrc.backend.utilities.ArchivoCSV;
import ar.edu.utnfrc.backend.dtos.LibrosXAutorDTO;


public class InsercionConsultasTest {

    private static AutorDAO autorDAO;
    private static LibroDAO libroDAO;
    private static Actions actions;


    // Antes de ejecutar cualquier Test
    @BeforeAll
    static void initAll() {
        // Inicializar DAOs y contexto de prueba (BD en memoria)
        autorDAO = new AutorDAO();
        libroDAO = new LibroDAO();
        actions = new Actions();


        String rutaArchivo = "Libros.csv"; // asegúrate de que el CSV esté en resources/test


        // Ejecutar método
        ArchivoCSV.procesarArchivo(rutaArchivo, autorDAO, libroDAO);


        // Validar resultados
        List<Autor> autores = autorDAO.findAll();
        List<Libro> libros = libroDAO.findAll();


        assertFalse(autores.isEmpty(), "Debe haber autores insertados desde el CSV");
        assertFalse(libros.isEmpty(), "Debe haber libros insertados desde el CSV");


        System.out.println("Autores insertados: " + autores.size());
        System.out.println("Libros insertados: " + libros.size());
    }


    @Test
    @DisplayName("Test de conteo de libros por autor")
    void testCountBooksByAuthor() {
        // Ejecutamos la acción
        actions.countBooksByAuthor(null); // no usa ApplicationContext en tu implementación


        // Obtenemos resultados directamente del DAO
        List<LibrosXAutorDTO> resultados = libroDAO.countBooksByAuthor();


        assertNotNull(resultados, "La consulta no debe devolver null");
        assertFalse(resultados.isEmpty(), "Debe devolver al menos un autor con libros");


        // Validar que ningún autor tenga 0 libros
        resultados.forEach(dto -> {
            assertTrue(dto.getCantidadLibros() > 0,
                    "El autor " + dto.getNombreAutor() + " debe tener al menos 1 libro");
        });


        System.out.println("Resultados de countBooksByAuthor:");
        resultados.forEach(System.out::println);
    }


    @Test
    @DisplayName("Test de listado de libros")
    void testListarLibros() {
        // Ejecutamos la acción
        actions.listarLibros(null); // no usa ApplicationContext en tu implementación
        // Obtenemos resultados directamente del DAO
        List<Libro> libros = libroDAO.findAll();
        assertNotNull(libros, "La lista de libros no debe ser null");
        assertFalse(libros.isEmpty(), "Debe haber libros en la base de datos");
        System.out.println("Total de libros listados: " + libros.size());
    }
}
