package games.app;

import java.net.URL;
import java.util.Scanner;

import games.menu.ItemMenu;
import games.menu.Menu;
import games.services.JuegoService;

public class App {
    public static void main(String[] args) {
        AppContext context = AppContext.getInstance();

        // Localiza el archivo directamente
        URL csvUrl = App.class.getResource("/files/games_data.csv");
        if (csvUrl == null) {
            System.err.println("⚠ No se encontró /files/games_data.csv en el classpath.");
            System.err.println("   Ruta esperada: src/main/resources/files/games_data.csv");
            return;
        }
        context.put("csvUrl", csvUrl);

        // Servicios
        context.registerService(JuegoService.class, new JuegoService());

        // Menú
        Menu<AppContext> menu = new Menu<>();
        Actions actions = new Actions();
        menu.addOption(1, new ItemMenu<AppContext>("Cargar juegos desde CSV", actions::importarJuegos));
        menu.addOption(2, new ItemMenu<AppContext>("Listar juegos desde DB",  actions::listarJuegos));
        menu.addOption(3, new ItemMenu<AppContext>("Top 5 Generos mas jugados (estado playing)",  actions::top5Generos));
        menu.addOption(4, new ItemMenu<AppContext>("Cantidad de juegos por desarrollador", actions::cantidadDeJuegosPorDesarrollador));
        menu.addOption(5, new ItemMenu<AppContext>("Mejor Desarrollador (mejor rating promedio de juegos)", actions::mejorDesarrollador));
        
        // Scanner
        Scanner sc = new Scanner(System.in);
        context.put("scanner", sc);

        menu.runMenu(context);
        sc.close(); // opcional
    }
}
