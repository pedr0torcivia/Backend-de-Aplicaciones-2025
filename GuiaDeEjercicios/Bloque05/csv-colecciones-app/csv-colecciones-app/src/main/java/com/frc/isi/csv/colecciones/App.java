package com.frc.isi.csv.colecciones;

import java.net.URL;
import java.util.Scanner;

import com.frc.isi.csv.colecciones.menu.Menu;
import com.frc.isi.csv.colecciones.services.VentasService;

public class App {
        private static final Scanner lector = new Scanner(System.in);

        public static void main(String[] args) {
                Menu<VentasService> menuOpciones = new Menu<>();
                VentasService ventas = new VentasService(); //Contexto

                URL folderPath = App.class.getResource("./resources/data");
                if (folderPath == null) {
                        System.out.println("No se encontró el directorio /data en el classpath");
                        System.exit(0);
                }

                //Puede ser una opcion más, puede estar contemplado directamente en el codigo del metodo invocarAction
                //menuOpciones.registrarOpcion(0, "Salir", p -> System.exit(1));
        }
}
