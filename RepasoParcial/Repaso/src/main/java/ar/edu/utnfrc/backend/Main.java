package ar.edu.utnfrc.backend;

import java.util.List;

import ar.edu.utnfrc.backend.menu.Actions;
import ar.edu.utnfrc.backend.menu.ApplicationContext;
import ar.edu.utnfrc.backend.menu.Menu;
import ar.edu.utnfrc.backend.menu.MenuOption;

public class Main {
    public static void main(String[] args) {
        var ctx = ApplicationContext.getInstance();
        var actions = new Actions();
        var opciones = List.of(
            new MenuOption(1, "Saludar al Usuario", actions::hola_mundo)
        );
        new Menu("Menú de Opciones - Etapa 1", opciones).run(ctx);
    }}