import java.util.Scanner;

public class Ejercicio3 {
    public static void main (String[] args) {
        System.out.println("Ejercicio 3");
        Scanner miScanner = new Scanner(System.in);
        System.out.println("Ingresá un número pibito: ");
        int n = miScanner.nextInt();
            while (n <= 0){
                System.out.println("Ingresá un número válido pibito: ");
                n = miScanner.nextInt();
            }
            for (int i = 1; i < n; i++) {
                if (((i % 3 != 0) && (i % 5 == 0)) || ((i%3 == 0) && (i%5 != 0))){
                    System.out.println(i);
                }
            }
    }
}