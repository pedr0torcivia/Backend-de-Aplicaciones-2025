package ar.edu.utnfrc.backend;
import lombok.Data;

@Data

public class Aereo extends Viaje{
    private int millasAcumuladas;
    private String codAerolinea;

    public Aereo(String codigo, int nroReserva, double precio, int tipo, Cliente cliente,
                 int millasAcumuladas, String codAerolinea) {
        super(codigo, nroReserva, precio, tipo, cliente);   
        this.millasAcumuladas = millasAcumuladas;
        this.codAerolinea = codAerolinea;
    }
}
