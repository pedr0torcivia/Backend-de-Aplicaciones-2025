package ar.edu.utnfrc.backend.utilities;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.opencsv.CSVReader;

import ar.edu.utnfrc.backend.daos.AutorDAO;
import ar.edu.utnfrc.backend.daos.LibroDAO;
import ar.edu.utnfrc.backend.entities.Autor;
import ar.edu.utnfrc.backend.entities.Libro;

// NO TIENE ATRIBUTOS PERO SOLO TIENE UN METODO ESTATICO (EL METODO NO PUEDE SER EJECUTADO POR UN OBJETO, 
// SINO QUE LA EJECUTA LA CLASE MISMA)


public class ArchivoCSV {
    public static void procesarArchivo(String rutaArchivo, AutorDAO autorDAO, LibroDAO libroDAO) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(rutaArchivo), StandardCharsets.UTF_8))){
            String[] linea;


            while ((linea = reader.readNext()) != null) {
                // Reconstruyo la línea en formato original para usar los constructores de las entidades
                String lineaCSV = String.join(",", linea);

                // Crear Autor a partir de la línea
                Autor autor = new Autor(lineaCSV);

                // Verificar si el autor ya existe en la BD
                Autor autorExistente = autorDAO.findByName(autor.getNombre());

                if (autorExistente == null) {
                    autorDAO.save(autor);
                    autorExistente = autor;
                }

                Libro libroExistente = libroDAO.findById(Long.parseLong(linea[0]));
                if (libroExistente != null) {
                    System.out.println("El libro con ID " + linea[0] + " ya existe. Se omitirá la inserción.");
                    continue; // Saltar a la siguiente línea del CSV
                }

                // Crear Libro con referencia al autor existente
                Libro libro = new Libro(lineaCSV, autorExistente);
                libroDAO.save(libro);
            }


            System.out.println("Procesamiento del archivo CSV finalizado correctamente.");


        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error general en el procesamiento: " + e.getMessage());
        }
    }
    }
