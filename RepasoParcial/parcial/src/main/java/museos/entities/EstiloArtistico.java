package museos.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "estilos_artisticos", uniqueConstraints = @UniqueConstraint(name = "uk_estilo_nombre", columnNames = "nombre"))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@ToString
public class EstiloArtistico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Builder.Default
    @OneToMany(mappedBy = "estilo")
    @ToString.Exclude
    private Set<ObraArtistica> obras = new HashSet<>();
}
