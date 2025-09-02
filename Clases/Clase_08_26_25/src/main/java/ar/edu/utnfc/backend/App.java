package ar.edu.utnfc.backend;

public class App {
    public static void main(String[] args) {
        int[] vector = new int[10];

        // La carga se hace por acceso directo o secuencial
        vector[9] = 12;

        for (int i = 0; i < vector.length ; i++){
            int num = (int) (Math.random() * 1500) + 1;
            vector[i] = num; 
        }

        try {
            vector[10] = 10;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("no se puede acceder ahi.");
            System.ou.println(e)
        }
    }
}
