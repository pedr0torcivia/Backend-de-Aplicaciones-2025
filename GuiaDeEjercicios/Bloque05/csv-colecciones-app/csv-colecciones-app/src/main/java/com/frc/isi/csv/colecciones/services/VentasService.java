package com.frc.isi.csv.colecciones.services;

//#region Imports
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.frc.isi.csv.colecciones.entities.Venta;
//#endregion

@AllArgsConstructor @Data @NoArgsConstructor
public class VentasService {
        private ArrayList<Venta> ventas = new ArrayList<>();

        public void importarVentas(URL folderPath) {
                try (Stream<Path> paths = Files.walk(Paths.get(folderPath.toURI()))) {
                        List<File> csvFiles = paths
                                        .filter(Files::isRegularFile)
                                        .filter(path -> path.toString().endsWith(".csv"))
                                        .map(path -> path.toFile())
                                        .collect(Collectors.toList());

                        csvFiles.stream().forEach(file -> {
                                try {
                                        bulkInsert(file);
                                } catch (IOException e) {
                                        e.printStackTrace();
                                        System.out.println("Error al procesar el archivo: " + e.getMessage());
                                }
                        });
                } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                }

                // Optional<File> fileToImport = csvFiles.stream()
                // .filter(p -> p.getName().contains("cafelahumedad"))
                // .findFirst();

                // fileToImport.ifPresentOrElse(
                // f -> {
                // try {
                // bulkInsert(f); // aquí puede lanzar IOException
                // } catch (IOException e) {
                // e.printStackTrace();
                // System.out.println("Error al procesar el archivo: "
                // + e.getMessage());
                // }
                // },
                // () -> System.out.println("Archivo cafelahumedad no encontrado en disco"));
        }

        private void bulkInsert(File fileToImport) throws IOException {
                Files.lines(Paths.get(fileToImport.toURI()))
                                .skip(1)
                                .forEach(linea -> transformarLinea(linea));
        }

        private void transformarLinea(String linea) {
                //Procesar datos correspondientes...

                //ventas.add(venta);
        }
}
