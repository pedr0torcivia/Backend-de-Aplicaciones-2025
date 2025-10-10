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
        AppContext context = AppContext.getInstance();
        context.registerService(LegoSetService.class, new LegoSetService());

        // 5) CSV en resources (opcional)
        URL csvUrl = App.class.getResource("/files/lego.csv");
        if (csvUrl == null) {
            System.err.println("⚠ No se encontró /files/lego.csv en el classpath.");
            System.err.println("   Ruta sugerida: src/main/resources/files/lego.csv");
            // NO devolvemos; seguimos a menú igualmente
        } else {
            context.put("csvUrl", csvUrl);
        }

        // 6) Menú
        Menu<AppContext> menu = new Menu<>();
        Actions actions = new Actions();
        menu.addOption(1, new ItemMenu<>("Cargar sets desde CSV", actions::importarSets));
        menu.addOption(2, new ItemMenu<>("Listar todos los sets", actions::listarSets));

        
        // 7) Scanner compartido
        try (Scanner sc = new Scanner(System.in)) {
            context.put("scanner", sc);
            menu.runMenu(context);
        }
    }
}
