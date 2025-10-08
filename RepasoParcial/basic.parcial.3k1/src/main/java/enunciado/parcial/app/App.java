package enunciado.parcial.app;

import java.util.Scanner;
import java.net.URL;

// import java.util.ArrayList;
import enunciado.parcial.menu.Menu;
import enunciado.parcial.menu.ItemMenu;

// import enunciado.parcial.services.PuestoService;
import enunciado.parcial.services.EmpleadoService;

public class App {
    public static void main(String[] args) {
        
        // inicializar context global de la app como KEY VALUE, STRING: OBJECT
        AppContext context = AppContext.getInstance();

        // reemplaza T por AppContext como variable qeu recibe dinammicamente
        Menu<AppContext> menu = new Menu<>();
        
        // menu.setTitulo("Menu de Opciones para Museo"); // capaz agregar atributo
        URL folderPath = App.class.getResource("/files"); //buscar en resources la carpeta files
        context.put("path", folderPath);
        context.registerService(EmpleadoService.class, new EmpleadoService());
        // context.registerService(EstiloArtisticoService.class, new EstiloArtisticoService());

        Actions actions = new Actions();
        menu.addOption(1, new ItemMenu<>("Cargar empleados desde CSV", actions::importarEmpleados));
        menu.addOption(2, new ItemMenu<>("Listar empleados desde DB", actions::listarEmpleados));
        
        // inicializamos un unico scanner en appContext
        Scanner sc = new Scanner(System.in);
        context.put("scanner", sc);    // preguntar sobre opciones del scanner

        menu.runMenu(context);
    }
}