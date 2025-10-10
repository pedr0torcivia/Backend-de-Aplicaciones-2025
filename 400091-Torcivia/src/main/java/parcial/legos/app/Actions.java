package parcial.legos.app;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import parcial.legos.entities.LegoSet;
import parcial.legos.services.LegoSetService;

public class Actions {

    public void importarSets(AppContext context) {
        URL csvUrl = (URL) context.get("csvUrl");
        if (csvUrl == null) {
            throw new IllegalStateException("No se encontró la URL del CSV en el contexto.");
        }

        var legoSetService = context.getService(LegoSetService.class);

        try {
            // Si corre desde clases (file:) -> usar File directo
            if (csvUrl.getProtocol().startsWith("file")) {
                File f = new File(csvUrl.toURI());
                legoSetService.bulkInsert(f);
                System.out.println("✔ Importación OK: " + f.getName());
                return;
            }

            // Si alguna vez corre empacado en JAR (jar:) -> copiar a temp y leer
            try (var in = Actions.class.getResourceAsStream("/files/lego.csv")) {
                if (in == null) throw new IllegalArgumentException("No se pudo abrir /files/lego.csv");
                var tmp = java.nio.file.Files.createTempFile("lego", ".csv").toFile();
                java.nio.file.Files.copy(in, tmp.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                legoSetService.bulkInsert(tmp);
                System.out.println("✔ Importación OK (modo JAR).");
            }
        } catch (IOException | java.net.URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public void listarSets(AppContext context) {
        LegoSetService service = context.getService(LegoSetService.class);

        List<LegoSet> sets = service.getAll();

        if (sets.isEmpty()) {
            System.out.println("⚠ No hay sets cargados en la base de datos.");
            return;
        }

        System.out.println("\n=== LISTADO DE LEGO SETS ===");
        for (LegoSet s : sets) {
            System.out.printf(
                "• %-30s | Tema: %-12s | País: %-10s | Edad: %-6s | Piezas: %4d | Precio: $%7.2f%n",
                s.getSetName(),
                s.getTheme() != null ? s.getTheme().getName() : "-",
                s.getCountry() != null ? s.getCountry().getCode() : "-",
                s.getAgeGroup() != null ? s.getAgeGroup().getCode() : "-",
                s.getPieceCount() != null ? s.getPieceCount() : 0,
                s.getListPrice() != null ? s.getListPrice() : 0.0
            );
        }
        System.out.println("============================\n");
    }


}
