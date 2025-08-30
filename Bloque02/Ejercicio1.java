public class Ejercicio1 {
    public static void main(String[] args) {

        //Ejercicio 1 - Ciclo For
        System.out.println("Figura 1");
        for (int i = 0; i < 4; i++) {
            System.out.println("******");
        };

        System.out.println("Figura 2");
        for (int i = 0; i < 4; i++) {
            if ((i == 0) || (i%2 == 0)) {
                System.out.println("******"); 
            } else {
                System.out.println(" ******"); 
            }
        }

        System.out.println("Figura 3");
        for (int i = 0; i < 5; i++) {
            var f = i;
            var linea = "*";
            for (int j = 0; j < f; j++) {
                linea += "*";
        }
        System.out.println(linea);
    }

        System.out.println("Figura 4");
            // Parte creciente
            for (int i = 0; i < 5; i++) {
                String linea = "";
                for (int j = 0; j <= i; j++) {
                    linea += "*";
                }
                System.out.println(linea);
            }

            // Parte decreciente
            for (int i = 3; i >= 0; i--) {
                String linea = "";
                for (int j = 0; j <= i; j++) {
                    linea += "*";
                }
                System.out.println(linea);
            }
}
}