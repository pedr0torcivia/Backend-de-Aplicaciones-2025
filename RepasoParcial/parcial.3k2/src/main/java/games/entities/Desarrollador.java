package games.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DESARROLLADORES")
@Getter @Setter
@NoArgsConstructor
public class Desarrollador {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DESA")
    @SequenceGenerator(name = "SEQ_DESA", sequenceName = "SEQ_DESARROLLADORES", allocationSize = 1)
    @Column(name = "DESA_ID")
    private Integer id;

    @Column(name = "NOMBRE", nullable = false, length = 255)
    private String nombre;
}
