package museos.entities;


import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "museos", uniqueConstraints = @UniqueConstraint(name = "uk_museo_nombre", columnNames = "nombre"))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@ToString
public class Museo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Builder.Default
    @OneToMany(mappedBy = "museo", orphanRemoval = false)
    @ToString.Exclude
    private Set<ObraArtistica> obras = new HashSet<>();
}