package games.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "JUEGOS")
@Getter @Setter
@NoArgsConstructor
public class Juego {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_JUEGO")
    @SequenceGenerator(name = "SEQ_JUEGO", sequenceName = "SEQ_JUEGOS", allocationSize = 1)
    @Column(name = "JUEGO_ID")
    private Integer id;

    @Column(name = "TITULO", nullable = false, length = 255)
    private String titulo;

    // INTEGER en DDL (año)
    @Column(name = "FECHA_LANZAMIENTO")
    private Integer fechaLanzamiento;

    // FKs opcionales según DDL (no NOT NULL)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "GENERO_ID", foreignKey = @ForeignKey(name = "FK_JUEGOS_GENEROS"))
    private Genero genero;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "DESARROLLADOR_ID", foreignKey = @ForeignKey(name = "FK_JUEGOS_DESARROLLADORES"))
    private Desarrollador desarrollador;

    @Column(name = "CLASIFICACION_ESRB", length = 4)
    private String clasificacionEsrb;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "PLATAFORMA_ID", foreignKey = @ForeignKey(name = "FK_JUEGOS_PLATAFORMAS"))
    private Plataforma plataforma;

    @Column(name = "RATING")
    private Double rating;

    @Column(name = "JUEGOS_FINALIZADOS")
    private Integer juegosFinalizados;

    @Column(name = "JUGANDO")
    private Integer jugando;

    @Lob
    @Column(name = "RESUMEN", nullable = false)
    private String resumen;
}
