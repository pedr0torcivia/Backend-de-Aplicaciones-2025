package parcial.legos.app;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

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

        int i = 1; // contador para enumerar los sets
        for (LegoSet s : sets) {
            System.out.printf(
                "%2d. %-30s | Tema: %-12s | País: %-10s | Edad: %-6s | Piezas: %4d | Precio: $%7.2f%n",
                i++,
                s.getSetName(),
                s.getTheme() != null ? s.getTheme().getName() : "-",
                s.getCountry() != null ? s.getCountry().getCode() : "-",
                s.getAgeGroup() != null ? s.getAgeGroup().getCode() : "-",
                s.getPieceCount() != null ? s.getPieceCount() : 0,
                s.getListPrice() != null ? s.getListPrice() : 0.0
            );
            }

        System.out.printf("\nTotal de sets importados: %d%n", i - 1);
        System.out.println("============================\n");
    }

    public void resultadoDeImportacion(AppContext context) {
        LegoSetService service = context.getService(LegoSetService.class);

        long legoSetCount = service.obtenerCantidadSets();
        long ageGroupCount = service.obtenerCantidadRangosEdad();
        long themeCount = service.obtenerCantidadTematicas();

        System.out.println("\n=== RESULTADO DE LA IMPORTACIÓN ===");
        System.out.printf("Cantidad de Sets insertados: %d%n", legoSetCount);
        System.out.printf("Cantidad de Grupos de Edad insertados: %d%n", ageGroupCount);
        System.out.printf("Cantidad de Temáticas insertadas: %d%n", themeCount);
        System.out.println("====================================\n");
    }

    public void rankingDePaises(AppContext context) {
        LegoSetService service = context.getService(LegoSetService.class);

        List<Object[]> ranking = service.obtenerRankingPaisesPorCostoValoracion();

        if (ranking.isEmpty()) {
            System.out.println("⚠ No hay datos disponibles para el ranking de países.");
            return;
        }

        System.out.println("\n=== RANKING DE PAÍSES POR COSTO/VALORACIÓN ===");
        System.out.printf("%-10s | %-20s%n", "País", "Costo/Valoración Promedio");
        System.out.println("---------------------------------------------");

        for (Object[] row : ranking) {
            String countryCode = (String) row[0];
            Double avgCostPerStar = (Double) row[1];

            System.out.printf("%-10s | $%20.2f%n", countryCode, avgCostPerStar);
        }

        System.out.println("=============================================\n");
    }

    public void listarSetsPorEdadPrecioYRating(AppContext context) {
        Scanner in = new Scanner(System.in);
        LegoSetService service = context.getService(LegoSetService.class);

        try {
            System.out.print("Ingrese edad: ");
            int edad = Integer.parseInt(in.nextLine().trim());

            System.out.print("Ingrese precio máximo: ");
            BigDecimal maxPrice = new BigDecimal(in.nextLine().trim());

            List<LegoSet> sets = service.buscarSetsPorEdadPrecioYRating(edad, maxPrice);

            System.out.println("\n=== SETS DISPONIBLES PARA EDAD " + edad +
                    " | precio < $" + maxPrice + " | rating ≥ 4.8 ===");

            if (sets.isEmpty()) {
                System.out.println("(sin resultados)");
                return;
            }

            for (LegoSet s : sets) {
                var nombre  = s.getSetName();
                var tema    = s.getTheme()   != null ? s.getTheme().getName() : "-";
                var pais    = s.getCountry() != null ? s.getCountry().getCode() : "-";
                var edadTxt = s.getAgeGroup()!= null ? s.getAgeGroup().getCode() : "-";
                var precio  = s.getListPrice()!= null ? s.getListPrice().toString() : "-";
                var rating  = s.getStarRating()!=null ? s.getStarRating().toString() : "-";

                System.out.printf("• %-35s | Tema: %-14s | País: %-4s | Edad: %-6s | Precio: $%8s | StarRating: %s%n",
                        nombre, tema, pais, edadTxt, precio, rating);
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
