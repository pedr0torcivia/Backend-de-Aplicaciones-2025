package ar.edu.utnfrc.backend;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Operacion[] operaciones = new Operacion[] {
            new Suma(), new Resta(), new Multiplicacion(), new Division()
        };

        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Calculadora básica");
            while (true) {
                System.out.println();
                System.out.println("Seleccione una operación:");
                System.out.println("1. Suma");
                System.out.println("2. Resta");
                System.out.println("3. Multiplicación");
                System.out.println("4. División");
                System.out.println("0. Salir");
                System.out.print("> ");

                int opcion;
                try { opcion = sc.nextInt(); }
                catch (InputMismatchException e) { System.out.println("Ingrese 0-4."); sc.nextLine(); continue; }

                if (opcion == 0) { System.out.println("Chau!"); break; }
                if (opcion < 1 || opcion > 4) { System.out.println("Opción inválida."); continue; }

                double a, b;
                try {
                    System.out.print("Ingrese el primer número: ");
                    a = sc.nextDouble();
                    System.out.print("Ingrese el segundo número: ");
                    b = sc.nextDouble();
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida (use números).");
                    sc.nextLine();
                    continue;
                }

                try {
                    double r = operaciones[opcion - 1].aplicar(a, b);
                    System.out.println("El resultado es: " + r);
                } catch (IllegalArgumentException | ArithmeticException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }
}
