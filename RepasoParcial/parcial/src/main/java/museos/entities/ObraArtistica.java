package museos.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "obras_artisticas", indexes = { @Index(name = "ix_obra_nombre", columnList = "nombre") })
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"museo", "autor", "estilo"})
public class ObraArtistica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 200)
    private String nombre;

    // En el enunciado figura como String; si preferís año numérico, cambiá a Integer.
    @Column(name = "anio", nullable = false, length = 10)
    private String anio;

    @Column(name = "monto_asegurado", nullable = false)
    private Double montoAsegurado;

    // Se mapeará a BOOLEAN en H2; al importar podés convertir 0/1 a false/true
    @Column(name = "seguro_total", nullable = false)
    private Boolean seguroTotal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "museo_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_obra_museo"))
    private Museo museo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autor_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_obra_autor"))
    private Autor autor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estilo_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_obra_estilo"))
    private EstiloArtistico estilo;
}
