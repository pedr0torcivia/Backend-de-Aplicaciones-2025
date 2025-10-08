package games.app;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import games.services.JuegoService;

public class Actions {

    public void importarJuegos(AppContext context) {
        URL csvUrl = (URL) context.get("csvUrl");
        if (csvUrl == null) {
            throw new IllegalStateException("No se encontró la URL del CSV en el contexto.");
        }

        var juegoSrv = context.getService(JuegoService.class);

        try {
            // Si corre desde clases (file:) -> usar File directo
            if (csvUrl.getProtocol().startsWith("file")) {
                File f = new File(csvUrl.toURI());
                juegoSrv.bulkInsert(f);
                System.out.println("✔ Importación OK: " + f.getName());
                return;
            }

            // Si alguna vez corre empacado en JAR (jar:) -> copiar a temp y leer
            try (var in = Actions.class.getResourceAsStream("/files/games_data.csv")) {
                if (in == null) throw new IllegalArgumentException("No se pudo abrir /files/games_data.csv");
                var tmp = java.nio.file.Files.createTempFile("games_data", ".csv").toFile();
                java.nio.file.Files.copy(in, tmp.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                juegoSrv.bulkInsert(tmp);
                System.out.println("✔ Importación OK (modo JAR).");
            }
        } catch (IOException | java.net.URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void listarJuegos(AppContext context) {
        var service = context.getService(JuegoService.class);
        var juegos = service.getAll();

        if (juegos.isEmpty()) {
            System.out.println("⚠ No hay juegos registrados en la base de datos.");
        } else {
            System.out.println("Lista de juegos:");
            juegos.forEach(juego -> {
                String genero     = juego.getGenero()     != null ? juego.getGenero().getNombre()     : "Desconocido";
                String plataforma = juego.getPlataforma() != null ? juego.getPlataforma().getNombre() : "Desconocido";
                System.out.printf("ID: %d | Título: %s | Año: %s | Género: %s | Plataforma: %s%n",
                        juego.getId(),
                        juego.getTitulo(),
                        String.valueOf(juego.getFechaLanzamiento()),
                        genero,
                        plataforma);
            });
        }
    }

    public void top5Generos(AppContext context) {
        var service = context.getService(JuegoService.class);
        var top5 = service.getTop5Generos();

        if (top5.isEmpty()) {
            System.out.println("No hay juegos disponibles");
        } else {
            System.out.println("TOP 5 GENEROS MAS JUGADOS (ESTADO PLAYING):");
            for (Object[] row: top5) {
                String nombreGenero = (String) row[0];
                Long total = (Long) row[1];
                System.out.println("Genero: " + nombreGenero + " | Total: " + total);
            }
        }
    }

    public void cantidadDeJuegosPorDesarrollador(AppContext context) {
        var service = context.getService(JuegoService.class);
        var juegosPorDesarrollador = service.cantidadDeJuegosPorDesarrollador();
        var juegosConMasDeUnDesarrollador = service.cantidadDeJuegosConMasDeUnDesarrollador();

        if (juegosPorDesarrollador.isEmpty()) {
            System.out.println("No hay juegos disponibles por desarrollador");
        } else {
            System.out.println("Cantidad de juegos por desarrollador:");
            for (Object[] row : juegosPorDesarrollador) {
                String nombreDesarrollador = (String) row[0];
                Long total = (Long) row[1];
                System.out.println("Desarrollador: " + nombreDesarrollador + " | Total: " + total);
            }
        }

        System.out.println("Cantidad de juegos con más de un desarrollador: " + juegosConMasDeUnDesarrollador);
    }

    public void mejorDesarrollador(AppContext context) {
        var service = context.getService(JuegoService.class);
        var mejorDeveloper = service.getBestDeveloper();

        if (mejorDeveloper == null) {
            System.out.println("No hay desarrolladores disponibles");
        } else {
            System.out.println("Mejor desarrollador (con más juegos): " + mejorDeveloper);
        }
    }
}
