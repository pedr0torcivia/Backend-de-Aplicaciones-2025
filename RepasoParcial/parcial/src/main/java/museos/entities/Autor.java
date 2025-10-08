package museos.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "autores", uniqueConstraints = @UniqueConstraint(name = "uk_autor_nombre", columnNames = "nombre"))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@ToString
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Builder.Default
    @OneToMany(mappedBy = "autor")
    @ToString.Exclude
    private Set<ObraArtistica> obras = new HashSet<>();
}