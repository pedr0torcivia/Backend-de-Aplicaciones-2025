package enunciado.parcial.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")                                   // INT AUTO_INCREMENT PRIMARY KEY
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "edad", nullable = false)
    private Integer edad;                                   // INT NOT NULL

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;                         // DATE NOT NULL

    @Column(name = "salario", nullable = false, precision = 10, scale = 2)
    private BigDecimal salario;                             // DECIMAL(10,2) NOT NULL

    @Column(name = "empleado_fijo", nullable = false)
    private boolean empleadoFijo;                           // BOOLEAN NOT NULL

    // FK: departamento_id -> departamento(id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id",
                foreignKey = @ForeignKey(name = "fk_departamento"))
    private Departamento departamento;

    // FK: puesto_id -> puesto(id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puesto_id",
                foreignKey = @ForeignKey(name = "fk_puesto"))
    private Puesto puesto;

    /** Salario original o +8% si es fijo */
public BigDecimal calcularSalarioFinal() {
    BigDecimal salarioFinal = salario;
    if (empleadoFijo) {
        BigDecimal incremento = salario.multiply(new BigDecimal("0.08"));
        salarioFinal = salario.add(incremento);
    }
    return salarioFinal;
}
    // toString sin forzar carga LAZY
    @Override public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", fechaIngreso=" + fechaIngreso +
                ", salario=" + salario +
                ", empleadoFijo=" + empleadoFijo +
                ", departamento=" + (departamento != null ? departamento.getNombre() : "N/A") +
                ", puesto=" + (puesto != null ? puesto.getNombre() : "N/A") +
                '}';
    }
}
