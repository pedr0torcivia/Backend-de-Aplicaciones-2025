package ar.edu.utnfrc.backend.menu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Menu {
    private final String title; // titulo del menu
    private final List<MenuOption> options; // opciones del menu LISTA DE OPCIONES DE MENU MenuOption

    // metodo CONSTRUCTOR de la clase, te permite inicializar un nuevo menu
    public Menu(String title, List<MenuOption> options) {
        this.title = title;
        this.options = new ArrayList<>(options);
        this.options.sort(Comparator.comparingInt(MenuOption::code));  
    // ordena las opciones
    // por su codigo en forma ascendente (1ro la 1, despues la 2, y asi)
    }
    // ejecuto el menu
    public void run(ApplicationContext ctx) {
        // Inyectar Scanner en el contexto si no existe
        Scanner scanner; // para ingresar datos por consola 
        if (ctx.contains("in")) {
            scanner = ctx.get("in", Scanner.class);
        } else {
            scanner = new Scanner(System.in);
            ctx.put("in", scanner);
        }
        while (true) {
            showOptions();
            System.out.print("Elige una opción: ");
            String input = scanner.nextLine().trim(); // esperamos entrada del usuario

            int selectedCode;
            try {
                selectedCode = Integer.parseInt(input); // guardo la entrada del usuario

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, por favor ingresa un número.");
                continue; 
            }
            // si ingreso 0 cortar ejecucion
            if (selectedCode == 0) {
                System.out.println("Saliendo del programa. ¡Adiós!");
                break;
            }
            // buscar la opcion elegida del menu
            Optional<MenuOption> selected = options.stream().filter(opt -> opt.code() == selectedCode).findFirst();

            // si existe la opcion elegida (por ejemplo, que efectivamente tengo 2 opciones
            // y el tipo ingreso o un 1 o un 2)
            if (selected.isPresent()) {
            // Ejecutar la acción pasando el contexto
                selected.get().action().run(ctx);
            } else {
                System.out.println("Opción inválida, intenta nuevamente.");
            // si ingreso un 3 ponele
            }
            }
            // No cerramos Scanner si viene del contexto para no cerrar System.in
            if (!ctx.contains("in")) {
            scanner.close();
            }
            }
            // este metodo es solo un printeo por pantalla de las opciones
    private void showOptions() {
        System.out.println("\n===== " + title + " =====");
        for (MenuOption option : options) {
            System.out.printf("%d. %s%n", option.code(), option.label());
        }
            System.out.println("0. Salir"); 
        }
    }