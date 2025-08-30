import java.util.Scanner;

public class Ejercicio5 {
    public static void main (String[] args) {
        System.out.println("Ejercicio 5");
        Scanner miScanner = new Scanner(System.in);
        int cont = 0;
        int acu = 0;
        int notaMax = 0;
        int notaMin = 0;
        float promedio = 0;
        int cantAprobado = 0;
        int cantDesaprobado = 0;
        int x = 0;
        System.out.println("Ingrese una nota: ");
        x = miScanner.nextInt();

        while (x != -1) {
            while ((x > 10) || (x <= -1)) {
                System.out.println("Ingrese una nota válida: ");
                x = miScanner.nextInt();
            }

            if ( cont == 0 ) {
                notaMax = x;
                notaMin = x;
            }

            cont += 1;
            acu += x; 
            if (x >= 6) {
                cantAprobado += 1;
            } else {
                cantDesaprobado += 1;
            }

            if ( notaMax < x ) {
                notaMax = x;
            }

            if ( notaMin > x ) {
                notaMin = x;
            }

            System.out.println("Ingrese una nota: ");
            x = miScanner.nextInt();
        }

        if ( cont != 0 ) {
            promedio = acu / cont;
        } else {
            promedio = 0; 
        }

        System.out.println("Nota max: " + notaMax);
        System.out.println("Nota min: " + notaMin);
        System.out.println("Promedio: " + promedio);
        System.out.println("Cant Aprob: " + cantAprobado);
        System.out.println("Cant Desapr: " + cantDesaprobado);
        System.out.println("Cont: " + cont);

    }
}