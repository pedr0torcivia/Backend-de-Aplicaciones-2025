package museos.app;

import java.net.URL;
import java.util.Scanner;

import museos.menu.ItemMenu;
import museos.menu.Menu;
import museos.services.MuseoService;
import museos.services.ObraArtisticaService;

public class App {
    public static void main(String[] args) {
        AppContext context = AppContext.getInstance();

        context.registerService(MuseoService.class, new MuseoService());
        context.registerService(ObraArtisticaService.class, new ObraArtisticaService());
        
        // Localiza el archivo directamente
        URL csvUrl = App.class.getResource("/files/obras.csv");
        if (csvUrl == null) {
            System.err.println("⚠ No se encontró /files/obras.csv en el classpath.");
            System.err.println("   Ruta esperada: src/main/resources/files/obras.csv");
            return;
        }
        context.put("csvUrl", csvUrl);

        // Servicios
        context.registerService(ObraArtisticaService.class, new ObraArtisticaService());

        // Menú
        Menu<AppContext> menu = new Menu<>();
        Actions actions = new Actions();
        menu.addOption(1, new ItemMenu<AppContext>("Cargar obras desde CSV", actions::importarObras));
        menu.addOption(2, new ItemMenu<AppContext>("Listar obras desde DB",  actions::listarObras));
        menu.addOption(3, new ItemMenu<AppContext>("Monto total por destruccion de obras", actions::determinarMontoTotal));
        menu.addOption(4, new ItemMenu<AppContext>("Generar txt con obras por estilo", actions::generarTxtPorEstilo));
        menu.addOption(5, new ItemMenu<AppContext>("Mostrar Obras con seguro parcial y monto superior al promedio", actions::mostrarObrasConSeguroParcial));
        menu.addOption(6, new ItemMenu<AppContext>("Mostrar Obras de un Museo Especifico", actions::mostrarObrasPorMuseo));

        
        // Scanners
        Scanner sc = new Scanner(System.in);
        context.put("scanner", sc);

        menu.runMenu(context);
        sc.close(); // opcional
    }
    
}
