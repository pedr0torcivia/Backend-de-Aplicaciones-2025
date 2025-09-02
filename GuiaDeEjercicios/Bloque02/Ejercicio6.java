import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

public class Ejercicio6 {
    public static void main (String[] args) throws FileNotFoundException {
        int totalNumeros = 0;
        int totalInvalidas = 0;
        int pares = 0;
        int impares = 0;
        double suma = 0;
        System.out.println("Ejercicio 6");
    try {
        File f = new File("./GuiaEjercicios/IntroduccionJava/numeros.txt");
        Scanner miScanner = new Scanner(f);


        while (miScanner.hasNextLine()) {
            String linea = miScanner.nextLine();

            try {
                int num = Integer.parseInt(linea);

                totalNumeros++;
                suma += num;

                if (num % 2 != 0) {
                impares++;
                } else {
                    pares++;
                }
            } catch (NumberFormatException e) {
                totalInvalidas++;
            }
        }
        miScanner.close();

        double promedio = suma / totalNumeros;
        System.out.println("Cantidad de numeros: " + totalNumeros);
        System.out.println("Cantidad de invalidos:" + totalInvalidas); 
        System.out.println("Cantidad par: " + pares);
        System.out.println("Cantidad impares: " + impares);
        System.out.println("Promedio: " + promedio);

    } catch (FileNotFoundException e) {
        System.out.println("Archivo no encontrado");
    }
       
    }   
}