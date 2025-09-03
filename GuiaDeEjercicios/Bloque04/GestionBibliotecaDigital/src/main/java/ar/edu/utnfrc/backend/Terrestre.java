package ar.edu.utnfrc.backend;
import lombok.Data;

@Data

public class Terrestre extends Viaje{
  private int provinciasVisitadas;
  private int cantidadPasajeros;

    public Terrestre(String codigo, int nroReserva, double precio, int tipo, Cliente cliente,
            int provinciasVisitadas, int cantidadPasajeros) {
        super(codigo, nroReserva, precio, tipo, cliente);   // <-- llama al padre
        this.provinciasVisitadas = provinciasVisitadas;
        this.cantidadPasajeros = cantidadPasajeros;
}
}