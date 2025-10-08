package games.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "GENEROS")
@Getter @Setter
@NoArgsConstructor
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_GENEROS", allocationSize = 1)
    @Column(name = "GEN_ID")
    private Integer id;

    @Column(name = "NOMBRE", nullable = false, length = 255)
    private String nombre;
}
