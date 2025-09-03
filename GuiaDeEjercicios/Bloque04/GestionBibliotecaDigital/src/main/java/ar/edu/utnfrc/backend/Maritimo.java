package ar.edu.utnfrc.backend;
import lombok.Data;

@Data

public class Maritimo extends Viaje{
    private int cantidadContenedores;
    private double costoPorKilo;
    private double pesoTransportado;
    
    public Maritimo(String codigo, int nroReserva, double precio, int tipo, Cliente cliente,
                 int cantidadContenedores, double costoPorKilo, double pesoTransportado) {
        super(codigo, nroReserva, precio, tipo, cliente); 
        this.cantidadContenedores = cantidadContenedores;
        this.costoPorKilo = costoPorKilo;
        this.pesoTransportado = pesoTransportado;
    }

    public double costoTotal(Maritimo viaje) {
        double costoK = viaje.getCostoPorKilo();
        double peso = viaje.getPesoTransportado();
        return (costoK * peso);
    }
}

