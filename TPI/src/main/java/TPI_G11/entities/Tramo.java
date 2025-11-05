package TPI_G11.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tramos",
       indexes = {
         @Index(name = "ix_tramo_ruta", columnList = "ruta_id"),
         @Index(name = "ix_tramo_estado", columnList = "estado")
       })
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"ruta", "camion"})
@EqualsAndHashCode(onlyExplicitIncluded = true)
public class Tramo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    /** Ruta a la que pertenece el tramo (una ruta tiene varios tramos). */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ruta_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_tramo_ruta"))
    private Ruta ruta;

    /** Punto de origen del tramo (dirección + coordenadas). */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "direccion", column = @Column(name = "origen_direccion", length = 200)),
        @AttributeOverride(name = "latitud",   column = @Column(name = "origen_latitud", nullable = false)),
        @AttributeOverride(name = "longitud",  column = @Column(name = "origen_longitud", nullable = false))
    })
    @NotNull
    private Punto origen;

    /** Punto de destino del tramo (dirección + coordenadas). */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "direccion", column = @Column(name = "destino_direccion", length = 200)),
        @AttributeOverride(name = "latitud",   column = @Column(name = "destino_latitud", nullable = false)),
        @AttributeOverride(name = "longitud",  column = @Column(name = "destino_longitud", nullable = false))
    })
    @NotNull
    private Punto destino;

    /** Tipo de tramo según el enunciado. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private TipoTramo tipo; // ORIGEN_DEPOSITO, DEPOSITO_DEPOSITO, DEPOSITO_DESTINO, ORIGEN_DESTINO

    /** Estado operativo del tramo. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private EstadoTramo estado; // ESTIMADO, ASIGNADO, INICIADO, FINALIZADO

    /** Costo aproximado (estimación antes de ejecutar). */
    @NotNull
    @PositiveOrZero
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal costoAproximado;

    /** Costo real (se completa al finalizar). */
    @PositiveOrZero
    @Column(precision = 15, scale = 2)
    private BigDecimal costoReal;

    /** Timestamps del tramo (opcional hasta asignar/iniciar/finalizar). */
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;

    /** Camión asignado (puede ser null si aún no se asignó). */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camion_id",
            foreignKey = @ForeignKey(name = "fk_tramo_camion"))
    private Camion camion;
}