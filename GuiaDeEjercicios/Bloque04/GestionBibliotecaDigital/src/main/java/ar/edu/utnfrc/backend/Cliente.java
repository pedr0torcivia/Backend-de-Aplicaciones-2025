package ar.edu.utnfrc.backend;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Cliente {
    private String nombreEmpresa;
    private String cuit;
}
