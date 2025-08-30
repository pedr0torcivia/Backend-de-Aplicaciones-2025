import java.util.Scanner;

public class Ejercicio4 {
    public static void main (String[] args) {
        System.out.println("Ejercicio 4");
        Scanner miScanner = new Scanner(System.in);
        System.out.println("Ingrese Nombre: ");
        String nombre = miScanner.nextLine();
        System.out.println("Ingrese Cant Horas: ");
        int cantHoras = miScanner.nextInt();
        System.out.println("Ingrese Cant Tareas: ");
        int cantTareas = miScanner.nextInt();

        int indice = (cantTareas * 10);

        if (cantHoras < 8) {
            indice -= 5;
        } else {
            indice += 5;
        }

        System.out.println("RESUMEN");
        System.out.println("Nombre: " + nombre);
        System.out.println("Cant Horas: " + cantHoras);
        System.out.println("Cant Tareas: " + cantTareas);
        System.out.println("Índice: " + indice);
    }
}