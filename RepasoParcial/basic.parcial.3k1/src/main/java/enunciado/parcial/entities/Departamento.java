package enunciado.parcial.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "departamento")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")                 // INT AUTO_INCREMENT PRIMARY KEY
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    // Bidireccional (opcional). No crea columna; la FK vive en EMPLEADO.
    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<Empleado> empleados;


// Relación uno a muchos: un Departamento puede tener varios Empleados.
// 'mappedBy = "departamento"' indica que la FK está en la clase Empleado.
// 'cascade = CascadeType.ALL' propaga persist/merge/remove al listado.
// 'orphanRemoval = false' hace que al quitar un empleado de la lista no se elimine de la BD.
// '@ToString.Exclude' y '@EqualsAndHashCode.Exclude' evitan bucles infinitos al imprimir o comparar.
}
