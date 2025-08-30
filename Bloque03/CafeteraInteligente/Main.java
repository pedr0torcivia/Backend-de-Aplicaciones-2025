public class Main {
    public static void main(String[] args) {
        // Crear la cafetera
        Cafetera cafetera = new Cafetera("Nespresso", "Viva", 500, 0, 20);

        // Imprimir estado inicial
        System.out.println(cafetera);

        // Encender la cafetera
        cafetera.encender();
        System.out.println("Encendiendo cafetera...");
        System.out.println(cafetera);

        // Cargar 300 ml de agua
        cafetera.cargarAgua(300);
        System.out.println("Cargando 300 ml de agua...");
        System.out.println(cafetera);

        // Calentar la cafetera dos veces
        cafetera.calentar();
        cafetera.calentar();
        System.out.println("Calentando cafetera dos veces...");
        System.out.println(cafetera);

        // Servir un café
        boolean servido = cafetera.servirCafe();
        System.out.println("Intentando servir café: " + (servido ? "Servido!" : "No se puede servir"));
        System.out.println(cafetera);

        // Servir otro café
        servido = cafetera.servirCafe();
        System.out.println("Intentando servir otro café: " + (servido ? "Servido!" : "No se puede servir"));
        System.out.println(cafetera);

        // Apagar la cafetera
        cafetera.apagar();
        System.out.println("Apagando cafetera...");
        System.out.println(cafetera);
    }
}
