import java.util.Scanner;

public class Ejercicio2 {
    public static void main (String[] args) {
        System.out.println("Ejercicio 2");
        Scanner miScanner = new Scanner(System.in);
        System.out.println("Ingresa un código ISBN: ");
        String x = miScanner.nextLine();
        if (x.length() != 13) {
            System.out.println("Inválido");
            return; // CONDICIÓN DE CORTE PARA TODO EL PROGRAMA
        }
        for (int i = 0; i < x.length() ; i++) {
            if (x.charAt(i) == '-' && (i != 1 && i != 5 && i != 11)) {
                 System.out.println("Inválido");
                 return;
            }
            // el método var.charAt(n) es equivalente a var[2] en python
        }
        int suma = (x.charAt(0) - '0') * 10 +
                   (x.charAt(2) - '0') * 9 +
                   (x.charAt(3) - '0') * 8 +
                   (x.charAt(4) - '0') * 7 +
                   (x.charAt(6) - '0') * 6 +
                   (x.charAt(7) - '0') * 5 +
                   (x.charAt(8) - '0') * 4 +
                   (x.charAt(9) - '0') * 3 +
                   (x.charAt(10) - '0') * 2 +
                   (x.charAt(12) - '0') * 1;
        // x.charAt(i) - '0' convierte el carácter numérico en su valor entero.
        // Validar ISBN
        if (suma % 11 == 0) {
            System.out.println("Válido");
        } else {
            System.out.println("Inválido");
        }
    }
}