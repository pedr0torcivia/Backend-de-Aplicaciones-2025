import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main (String[] args) {
        List<Barco> barcos= new ArrayList<>();
        Puerto puerto = new Puerto();

        try {
            Scanner sc = new Scanner(new File("barcos.csv"));

            if (sc.hasNextLine()) sc.nextLine(); // Pasar la primera linea

             while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] datos = linea.split(",");

                    Capitan capitan = new Capitan(
                    datos[5],               // nombre
                    datos[6],               // apellido
                    datos[4],               // identificador
                    Integer.parseInt(datos[7]) // antiguedad
                    );
                
                Barco barco = new Barco(
                    datos[0],                       // matricula
                    Integer.parseInt(datos[1]),     // numMuelle
                    Float.parseFloat(datos[2]),     // capCarga
                    Float.parseFloat(datos[3]),     // costoAlq
                    capitan
                    );

                barcos.add(barco);
            }

            puerto.setBarcos(barcos);
            sc.close();

            // Punto 3
            float total = puerto.calcRecaudacion(barcos);
            System.out.println(total);

            // Punto 4
            String lista = puerto.barcos18(barcos);
            System.out.println(lista);

            // Punto 5
            float promedio = puerto.cargaPromedio(barcos);
            System.out.println(promedio);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}