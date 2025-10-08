package games.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PLATAFORMAS")
@Getter @Setter
@NoArgsConstructor
public class Plataforma {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PLAT")
    @SequenceGenerator(name = "SEQ_PLAT", sequenceName = "SEQ_PLATAFORMAS", allocationSize = 1)
    @Column(name = "PLAT_ID")
    private Integer id;

    @Column(name = "NOMBRE", nullable = false, length = 255)
    private String nombre;
}
