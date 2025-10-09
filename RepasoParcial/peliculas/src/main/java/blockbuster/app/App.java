package blockbuster.app;

import java.net.URL;
import java.util.Scanner;

import blockbuster.menu.ItemMenu;
import blockbuster.menu.Menu;
import blockbuster.services.PeliculaService;

public class App {
    public static void main(String[] args) {
        AppContext context = AppContext.getInstance();

        context.registerService(PeliculaService.class, new PeliculaService());
        
        // Localiza el archivo directamente
        URL csvUrl = App.class.getResource("/files/peliculas.csv");
        if (csvUrl == null) {
            System.err.println("⚠ No se encontró /files/peliculas.csv en el classpath.");
            System.err.println("   Ruta esperada: src/main/resources/files/peliculas.csv");
            return;
        }
        context.put("csvUrl", csvUrl);

        // Servicios
        context.registerService(PeliculaService.class, new PeliculaService());

        // Menú
        Menu<AppContext> menu = new Menu<>();
        Actions actions = new Actions();
        menu.addOption(1, new ItemMenu<AppContext>("Cargar peliculas desde CSV", actions::importarPeliculas));
        menu.addOption(2, new ItemMenu<AppContext>("Listar peliculas desde DB",  actions::listarPeliculas));
        menu.addOption(3, new ItemMenu<AppContext>("Listar peliculas por director",  actions::listarPeliculasXDirector));
        menu.addOption(4, new ItemMenu<AppContext>("Calcular cantidad de peliculas recientes",  actions::calcularPeliculasRecientes));
        menu.addOption(5, new ItemMenu<AppContext>("Promedio de precio x genero",  actions::calcularPromedioPrecioXGenero));
        menu.addOption(6, new ItemMenu<AppContext>("Mostrar pelicula mas reciente",  actions::mostrarPeliculaMasReciente));
        // 1) Cargar Películas desde CSV
        // 2) Listar películas por director
        // 3) Cantidad de películas recientes (<= 365 días)
        // 4) Promedio de precio por género
        // 5) Mostrar película más reciente
                
        // Scanners
        Scanner sc = new Scanner(System.in);
        context.put("scanner", sc);

        menu.runMenu(context);
        sc.close(); // opcional
    }
    
}
