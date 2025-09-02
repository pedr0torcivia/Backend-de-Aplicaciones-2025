package ar.edu.utnfc.backend;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Cliente {
    private String nombre; 
    private String dni;
    private short edad;
    private String ocupacion;
    private int cantidadPosteos;
    private float horasEnPlataforma;
    private boolean verificado;

    
}
