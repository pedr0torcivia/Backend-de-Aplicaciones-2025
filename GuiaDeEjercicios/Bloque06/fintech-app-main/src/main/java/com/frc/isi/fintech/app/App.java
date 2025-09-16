package com.frc.isi.fintech.app;

import java.net.URL;

import com.frc.isi.fintech.menu.ApplicationContext;
import com.frc.isi.fintech.menu.Menu;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        var ctx = ApplicationContext.getInstance();
        Menu menu = new Menu();
        menu.setTitulo("Menu de Opciones para Museo");

        Acciones acciones = new Acciones();

        URL folderPath = App.class.getResource("/files");
        ctx.put("path", folderPath);
        ctx.registerService(null, null);

        menu.addOpcion(new itemMenu(1, "Listado empleados tarjeta a vencer", acciones::getEmpleadosTarjetaAVencer));
        menu.ejecutar(ctx);

    }
}
