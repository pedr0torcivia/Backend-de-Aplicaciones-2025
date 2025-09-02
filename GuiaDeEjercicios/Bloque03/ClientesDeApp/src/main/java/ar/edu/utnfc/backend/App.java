package ar.edu.utnfc.backend;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class App {
    public static int contarMayoresDe(Cliente[] clientes, int edad) {
    int cont = 0;
    for (int i = 0; i < clientes.length; i++){
        if (clientes[i].getEdad() > edad){
            cont++;
        }
    }
        return cont;
    }

    public static int totalposteos(Cliente[] clientes) {
        int total = 0;

        for (int i = 0; i < clientes.length; i++){
            total += clientes[i].getCantidadPosteos();
        }
        return total;
    }

    public static float calcularPuntuacion(Cliente cliente){
        float puntuacion = 0;
        if (cliente.getEdad() > 25) {
            puntuacion = cliente.getHorasEnPlataforma() * 2;
        } else {
            puntuacion = cliente.getHorasEnPlataforma() * 3;
        }

        if (cliente.isVerificado()) {
            puntuacion += 20;
        }
        return puntuacion;
    }

    public static float calcularPuntuacionTodos(Cliente[] clientes){
        float total = 0;
        for (int i = 0; i < clientes.length; i++){
            total += calcularPuntuacion(clientes[i]);
        }
        return total;
    }
    public static void main(String[] args) {
        System.out.println("Clientes de App");

        File file = new File("E:\\Universidad\\Backend\\GuiaEjercicios\\Bloque03\\ClientesDeApp\\src\\main\\resources\\clientes.csv");

        try {
            Scanner sc = new Scanner(file);
            int contador = 0;
            Cliente[] clientes = new Cliente[200];

            if (sc.hasNextLine()) {
                sc.nextLine();
            }
            while (sc.hasNextLine()) {

                String linea = sc.nextLine();
                String[] datos = linea.split(",");

                String nombre = datos[0];
                String dni = datos[1];
                short edad = Short.parseShort(datos[2]);
                String ocupacion = datos[3];
                int cantidadPosteos = Integer.parseInt(datos[4]);
                float horasEnPlataforma = Float.parseFloat(datos[5]);
                boolean verificado = Boolean.parseBoolean(datos[6]);

                Cliente cliente = new Cliente(nombre, dni, edad, ocupacion, cantidadPosteos, horasEnPlataforma, verificado);
                clientes[contador] = cliente;
                contador++;
            }
            for (int i = 0; i < 10; i++) {
                System.out.println("Cliente n°" + (i+1) + ": ");
                System.out.println(clientes[i]);
            }

            System.out.println("Cantidad de clientes mayores a 25: " + contarMayoresDe(clientes, 25));
            System.out.println("Cantidad total de posteos: " + totalposteos(clientes));
            System.out.println("Puntuacion de clientes: " + calcularPuntuacionTodos(clientes));
            sc.close();


        } catch (FileNotFoundException e) {
            System.out.println("Equisde no se encontró el archiBo(ca)");
            System.out.println(e.getMessage());
        }


    }
}
