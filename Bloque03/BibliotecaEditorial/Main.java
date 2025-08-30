import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main{
    public static void main (String[] args) {
    
    Libro[] libros = new Libro[100];
    Biblioteca biblioteca = new Biblioteca();
    int cantidadLibros = 0;


    try {
        Scanner sc = new Scanner(new File("libros.csv"));
        
        if (sc.hasNextLine()) sc.nextLine();

        while (sc.hasNextLine()){
            String linea = sc.nextLine();
            String[] datos = linea.split(",");

            // isbn,titulo,nroEstante,paginas,precioPorDia,autorId,autorNombre,autorApellido,autorAniosCarrera
            Autor autor = new Autor(
                        datos[5],
                        datos[6],
                        datos[7],
                        Integer.parseInt(datos[8])
                );
            Libro libro = new Libro(
                        datos[0],
                        datos[1],
                        Integer.parseInt(datos[2]),
                        Integer.parseInt(datos[3]),
                        Double.parseDouble(datos[4]),
                        autor
                );
            
            libros[cantidadLibros] = libro;
            cantidadLibros++;
        }
        Libro[] librosCargados = new Libro[cantidadLibros];
        for (int i = 0; i < cantidadLibros; i++) {
            librosCargados[i] = libros[i];
        }
        biblioteca.setLibros(librosCargados);
        sc.close();

        // Resultados 
        System.out.println("Recaudación estimada: " + biblioteca.recaudacionEstimada());
        System.out.println("Autores con más de 18 años de carrera: " + biblioteca.cantidadAutores());
        System.out.println("Promedio de páginas (estantes pares): " + biblioteca.promedioPags());



    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    }
}