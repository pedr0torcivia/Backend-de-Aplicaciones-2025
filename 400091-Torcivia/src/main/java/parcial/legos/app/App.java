package parcial.legos.app;

import java.net.URL;
import java.util.Scanner;

import jakarta.persistence.EntityManager;

import parcial.legos.menu.ItemMenu;
import parcial.legos.menu.Menu;
import parcial.legos.services.LegoSetService;
import parcial.legos.infra.DbInit;
import parcial.legos.repositories.context.DbContext;

public class App {
    public static void main(String[] args) throws Exception {
        // 1) DDL en H2 memoria
        DbInit.run();

        // 2) EntityManager
        EntityManager em = DbContext.getInstance().getManager();

        // 3) Smoke test
        try {
            Long total = em.createQuery("select count(c) from Country c", Long.class).getSingleResult();
            if (total != null && total > 0) {
                System.out.println("[OK] DB init + JPA mappings verificados. COUNTRIES=" + total);
            } else {
                System.out.println("[FAIL] DB init ok, pero COUNTRIES no tiene registros.");
            }
        } catch (Exception ex) {
            System.out.println("[FAIL] Error ejecutando smoke test: " + ex.getMessage());
            ex.printStackTrace(System.out);
        }

        // 4) Contexto y servicios
        AppContext context = AppContext.getInstance(); // engloba todo los componentes de la aplicacion para centralizar su acceso
        context.registerService(LegoSetService.class, new LegoSetService());

        // 5) CSV en resources
        URL csvUrl = App.class.getResource("/files/lego.csv");
        if (csvUrl == null) {
            System.err.println("⚠ No se encontró /files/lego.csv en el classpath.");
            // NO devolvemos; seguimos a menú igualmente
        } else {
            context.put("csvUrl", csvUrl);
        }

        // 6) Menú
        Menu<AppContext> menu = new Menu<>();
        Actions actions = new Actions();
        menu.addOption(1, new ItemMenu<>("Cargar sets desde CSV", actions::importarSets));
        menu.addOption(2, new ItemMenu<>("Listar todos los sets", actions::listarSets));
        menu.addOption(3, new ItemMenu<>("Resultado de la importación", actions::resultadoDeImportacion));
        menu.addOption(4, new ItemMenu<>("Ranking de países", actions::rankingDePaises));
        menu.addOption(5, new ItemMenu<>("Listado de Sets por Edad", actions::listarSetsPorEdadPrecioYRating));

// 3 - Rtado de la importacion: Resultado de la importación
//  . Cantidad de Rangos de edad que se insertaron en la base de dtaos
//    . Cantidad de Temáticas que se insertaron en la base de datos

// 4 - Ranking de los 5 Países con la relación costo/valoración más baja: Se trata del promedio del costo/valoración que tienen los sets, por país. Si, por ejemplo, en un país
// están disponibles 2 sets: Set A -> precio $100, valoración 1 y Set B -> precio $200, valoración 5, el
// Set A tiene una relación costo valoración de $100/estrella y el Set B tiene una relación de
// $40/estrella. El promedio, en este caso es de $70/estrella para ese país. Deben mostrarse,
// únicamente, aquellos países donde hay juegos disponibles.

// 5 -. Listado de Sets distintos y disponibles para una edad específica, cuyo precio sea menor a $x y
// que tengan una valoración de 4.8 o más estrellas
// Se sugiere aquí que utilice el método propuesto para el rango etario en el pre-enunciado, es decir, la
// edad es exactamente la solicitada el juego aplica, si la edad es un rango, la edad solicitada debe
// estar en el intervalo (incluyendo los límites), si el juego no tiene rango etario no debe ser tenido en
// cuenta. Con dichos juegos verificar las demás condiciones y listar los sets ordenados de mayor a
// menor por precio.

        // 7) Scanner compartido
        try (Scanner sc = new Scanner(System.in)) {
            context.put("scanner", sc);
            menu.runMenu(context);
        }
    }
}
