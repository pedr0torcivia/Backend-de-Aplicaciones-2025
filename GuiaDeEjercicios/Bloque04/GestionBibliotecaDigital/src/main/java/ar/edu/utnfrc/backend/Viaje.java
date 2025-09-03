package ar.edu.utnfrc.backend;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public abstract class Viaje {
    private String codigo;
    private int nroReserva;
    private double precio;
    private int tipo;
    private Cliente cliente;
}
