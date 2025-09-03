package ar.edu.utnfrc.backend;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.File;

import java.io.FileNotFoundException;

public class App {
    public static void main(String[] args) {
        System.out.println("Gestion de Viajes");
        Map<String, Cliente> clientesPorCuit = new HashMap<>();
        List<Viaje> viajes = new ArrayList();

        try {
            int cont = 0;

            File file = new File("src/main/resources/viajes.csv");
            Scanner sc = new Scanner(file);

            if (sc.hasNextLine()) {
                sc.nextLine();
            }

            while (sc.hasNextLine()) {

                String linea = sc.nextLine();
                String[] d = linea.split(";");

                System.out.println(linea);
               // columnas básicas
                String codigo = d[0];
                int nroReserva = Integer.parseInt(d[1]);
                double precio = Double.parseDouble(d[2]);
                int tipo = Integer.parseInt(d[3]);

                // columnas extras
                int millas = Integer.parseInt(d[4]);
                String codAerolinea = d[5];
                int provincias = Integer.parseInt(d[6]);
                int pasajeros = Integer.parseInt(d[7]);
                int contenedores = Integer.parseInt(d[8]);
                double costoKilo = Double.parseDouble(d[9]);
                double peso = Double.parseDouble(d[10]);

                // clientes
                String nombreEmpresa = d[11];
                String cuit = d[12];

                Cliente cliente = clientesPorCuit.get(cuit);
                
                if (cliente == null) {
                    cliente = new Cliente(nombreEmpresa, cuit);
                    clientesPorCuit.put(cuit, cliente);
                }

                Viaje viaje = null;
                if (tipo == 1) {
                    viaje = new Aereo(codigo, nroReserva, precio, tipo, cliente, millas, codAerolinea);
                } else if (tipo == 2) {
                    viaje = new Terrestre(codigo, nroReserva, precio, tipo, cliente, provincias, pasajeros);
                } else if (tipo == 3) {
                    viaje = new Maritimo(codigo, nroReserva, precio, tipo, cliente, contenedores, costoKilo, peso);
                }

                if (viaje != null) {
                    viajes.add(viaje);
                }
                System.out.println(viaje);
                System.out.println(cliente);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el archivo...");
        }

        // ====== desde acá todo queda dentro de main() ======
        
        int cantPasajeros = 0;
        int cantidadMillas = 0;
        int cantidadContenedoresTransp = 0;
        int acu = 0;

        for (int i = 0; i < viajes.size(); i++) {
            Viaje v = viajes.get(i);
            if (v instanceof Terrestre) {
                Terrestre t = (Terrestre) v;
                cantPasajeros += t.getCantidadPasajeros();
            } else if (v instanceof Aereo) {
                Aereo a = (Aereo) v;
                cantidadMillas += a.getMillasAcumuladas();
            } else if (v instanceof Maritimo) {
                Maritimo m = (Maritimo) v;
                if (m.getCantidadContenedores() >= 5) {
                    acu += m.costoTotal(m);
                }
                cantidadContenedoresTransp += m.getCantidadContenedores();
            }  
        }

        int clientesUnicos = clientesPorCuit.size();

        System.out.println("Viajes leídos: " + viajes.size());
        System.out.println("Clientes únicos: " + clientesUnicos);
        System.out.println("Total pasajeros (Terrestre): " + cantPasajeros);
        System.out.println("Total millas (Aéreo): " + cantidadMillas);
        System.out.println("Total contenedores (Marítimo): " + cantidadContenedoresTransp);
        System.out.println("Costo acumulado total: " + acu);
        Scanner sca = new Scanner(System.in);
        System.out.println("¿De que tipo de viaje desea saber su cantidad?");
        String num = sca.nextLine();
        int msj = Integer.parseInt(num);


        int cant = 0;
        sca.close(); 
        for (int i = 0; i < viajes.size(); i++) {
            Viaje v = viajes.get(i);
            if ((v instanceof Terrestre) && (v.getTipo() == msj)) {
                Terrestre t = (Terrestre) v;
                cant ++;
            } else if (v instanceof Aereo && (v.getTipo() == msj)) {
                Aereo a = (Aereo) v;
                cant ++;
            } else if (v instanceof Maritimo && (v.getTipo() == msj)) {
                Maritimo m = (Maritimo) v;
                cant ++;
            }  
        }
        System.out.println("Cantidad del tipo " + msj + ": " + cant);
    }
}
