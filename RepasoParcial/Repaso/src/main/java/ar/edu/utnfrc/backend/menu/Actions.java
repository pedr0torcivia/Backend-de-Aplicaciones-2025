package ar.edu.utnfrc.backend.menu;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import ar.edu.utnfrc.backend.daos.AutorDAO;
import ar.edu.utnfrc.backend.daos.LibroDAO;
import ar.edu.utnfrc.backend.dtos.LibrosXAutorDTO;
import ar.edu.utnfrc.backend.entities.Libro;
import ar.edu.utnfrc.backend.utilities.ArchivoCSV;

public class Actions {
AutorDAO autorDAO = new AutorDAO();
LibroDAO libroDAO = new LibroDAO();

public void cargarCSV(ApplicationContext ctx) {
    try {
        ArchivoCSV.procesarArchivo("RepasoParcial/Repaso/src/main/java/ar/edu/utnfrc/backend/Libros.csv", autorDAO, libroDAO); 
    } catch (Exception e) {
        System.out.println("Error al cargar el archivo CSV: " + e.toString());
    }
}

public void listarLibros(ApplicationContext ctx){
    List<Libro> libros= libroDAO.findAll();

    if (libros.isEmpty()){
        System.out.println("No hay libros cargados");
        return; 
    } else {
        libros.forEach(libro -> System.out.println(libro));
    }
}

public void encontrarLibroPorPalabra(ApplicationContext ctx){
    Scanner sc = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));
    System.out.println("Ingrese palabra para buscar: ");
    String keyword = sc.nextLine();
    List<Libro> libros = libroDAO.findBooksByKeyword(keyword);
    libros.forEach(libro -> System.out.println(libro));
}

public void countBooksByAuthor(ApplicationContext ctx) {
    LibroDAO libroDAO = new LibroDAO();
    List<LibrosXAutorDTO> resultados = libroDAO.countBooksByAuthor();
    
    if (resultados.isEmpty()) {
        System.out.println("No hay autores registrados.");
        return;
    }

    System.out.println("📊 Cantidad de libros por autor:");
    resultados.forEach(System.out::println);
    }

public void findTop3LibrosMasAntiguos(ApplicationContext ctx) {
    List<Libro> libros = libroDAO.findTop3LibrosMasAntiguos();
    if (libros.isEmpty()) {
        System.out.println("No hay libros cargados.");
        return;
    } else {
        System.out.println("📚 Top 3 libros más antiguos:");
        libros.forEach(libro -> System.out.println(libro));
    }
}
}
