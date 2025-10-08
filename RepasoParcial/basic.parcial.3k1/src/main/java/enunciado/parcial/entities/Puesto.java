package enunciado.parcial.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "puesto")
public class Puesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")                 // INT AUTO_INCREMENT PRIMARY KEY
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    // Bidireccional (opcional). La FK está en EMPLEADO.
    @OneToMany(mappedBy = "puesto", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<Empleado> empleados;
}
