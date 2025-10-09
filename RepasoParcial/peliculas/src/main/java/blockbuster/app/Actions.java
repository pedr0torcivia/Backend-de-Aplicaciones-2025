package blockbuster.app;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import blockbuster.services.PeliculaService;

public class Actions {

    public void importarPeliculas(AppContext context) {
        URL csvUrl = (URL) context.get("csvUrl");
        if (csvUrl == null) {
            throw new IllegalStateException("No se encontró la URL del CSV en el contexto.");
        }

        var peliculaService = context.getService(PeliculaService.class);

        try {
            // Si corre desde clases (file:) -> usar File directo
            if (csvUrl.getProtocol().startsWith("file")) {
                File f = new File(csvUrl.toURI());
                peliculaService.bulkInsert(f);
                System.out.println("✔ Importación OK: " + f.getName());
                return;
            }

            // Si alguna vez corre empacado en JAR (jar:) -> copiar a temp y leer
            try (var in = Actions.class.getResourceAsStream("/files/games_data.csv")) {
                if (in == null) throw new IllegalArgumentException("No se pudo abrir /files/games_data.csv");
                var tmp = java.nio.file.Files.createTempFile("games_data", ".csv").toFile();
                java.nio.file.Files.copy(in, tmp.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                peliculaService.bulkInsert(tmp);
                System.out.println("✔ Importación OK (modo JAR).");
            }
        } catch (IOException | java.net.URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void listarPeliculas(AppContext context) {
        var service = context.getService(PeliculaService.class);
        var peliculas = service.getAll();

        if (peliculas.isEmpty()) {
            System.out.println("No hay películas registradas en la base de datos.");
        } else {
            System.out.println("Lista de películas:");
            peliculas.forEach(pelicula -> {
                String director = pelicula.getDirector() != null ? pelicula.getDirector().getNombre() : "Desconocido";
                String genero = pelicula.getGenero() != null ? pelicula.getGenero().getNombre() : "Desconocido";

                System.out.printf(
                    "ID: %d | Título: %s | Fecha de Estreno: %s | Precio: %.2f | Clasificación: %s | Género: %s | Director: %s%n",
                    pelicula.getId(),
                    pelicula.getTitulo(),
                    pelicula.getFechaEstreno(),
                    pelicula.getPrecioBaseAlquiler(),
                    pelicula.getClasificacion(),
                    genero,
                    director
                );
            });
        }

    }

    public void listarPeliculasXDirector(AppContext context) {
        var service = context.getService(PeliculaService.class);
        var scanner  = context.get("scanner", java.util.Scanner.class);

        System.out.println("Ingrese un director:");
        if (scanner.hasNextLine()) scanner.nextLine(); // <-- consume el \n que dejó nextInt()

        String director = scanner.nextLine().trim();
        if (director.isEmpty()) {
            System.out.println("Director vacío. Cancelado.");
            return;
        }

        var peliculas = service.getPeliculasXDirector(director);

        if (peliculas.isEmpty()) {
            System.out.println("No hay películas con director registradas en la base de datos.");

        } else {
            System.out.println("Películas por director:");
            peliculas.forEach(pelicula -> {
                String dir = pelicula.getDirector() != null ? pelicula.getDirector().getNombre() : "Desconocido";
                String genero = pelicula.getGenero() != null ? pelicula.getGenero().getNombre() : "Desconocido";

                System.out.printf(
                    "ID: %d | Título: %s | Fecha de Estreno: %s | Precio: %.2f | Clasificación: %s | Género: %s | Director: %s%n",
                    pelicula.getId(),
                    pelicula.getTitulo(),
                    pelicula.getFechaEstreno(),
                    pelicula.getPrecioBaseAlquiler(),
                    pelicula.getClasificacion(),
                    genero,
                    dir
                );
            });
        }
    }

    public void calcularPeliculasRecientes(AppContext context) {
        var service = context.getService(PeliculaService.class);
        long count = service.countPeliculasRecientes(365);
        System.out.println("Cantidad de películas recientes (<= 365 días): " + count);
    }

    public void calcularPromedioPrecioXGenero(AppContext context) {
        var service = context.getService(PeliculaService.class);
        var promedioPorGenero = service.getPromedioPrecioPorGenero();

        if (promedioPorGenero.isEmpty()) {
            System.out.println("No hay géneros con películas registradas en la base de datos.");
        } else {
            System.out.println("Promedio de precio por género:");
            promedioPorGenero.forEach((Object[] resultado) -> {
                String genero = (String) resultado[0];
                Double promedio = (Double) resultado[1];
                System.out.printf("Género: %s | Precio Promedio: %.2f%n", genero, promedio);
            });
        }
    }

    public void mostrarPeliculaMasReciente(AppContext context) {
        var service = context.getService(PeliculaService.class);
        var peliculaMasReciente  =service.getPeliculaMasReciente();

        System.out.println("Película más reciente:");
        if (peliculaMasReciente == null) {
            System.out.println("No hay películas registradas en la base de datos.");
        } else {
                String dir = peliculaMasReciente.getDirector() != null ? peliculaMasReciente.getDirector().getNombre() : "Desconocido";
                String genero = peliculaMasReciente.getGenero() != null ? peliculaMasReciente.getGenero().getNombre() : "Desconocido";

                System.out.printf(
                    "ID: %d | Título: %s | Fecha de Estreno: %s | Precio: %.2f | Clasificación: %s | Género: %s | Director: %s%n",
                    peliculaMasReciente.getId(),
                    peliculaMasReciente.getTitulo(),
                    peliculaMasReciente.getFechaEstreno(),
                    peliculaMasReciente.getPrecioBaseAlquiler(),
                    peliculaMasReciente.getClasificacion(),
                    genero,
                    dir
                );  
            
    }
}}
