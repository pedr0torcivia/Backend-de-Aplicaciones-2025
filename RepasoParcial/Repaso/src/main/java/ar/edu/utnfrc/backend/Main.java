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
            new MenuOption(1, "Cargar datos desde CSV", actions::cargarCSV),
            new MenuOption(2, "Listar libros", actions::listarLibros),
            new MenuOption(3, "Buscar libro por palabra clave", actions::encontrarLibroPorPalabra),
            new MenuOption(4, "Cantidad de libros por autor", actions::countBooksByAuthor),
            new MenuOption(5, "Top 3 libros más antiguos", actions::findTop3LibrosMasAntiguos)
        );
        new Menu("Menú de Opciones", opciones).run(ctx);
    }}