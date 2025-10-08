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
}
