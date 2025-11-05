package TPI_G11.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;

import TPI_G11.entities.converters.DurationSecondsConverter;

@Entity
@Table(name = "solicitudes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Solicitud {

    /** Número de solicitud (PK). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero;

    /** Contenedor asociado a la solicitud. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contenedor_id", nullable = false)
    private Contenedor contenedor;

    /** Cliente asociado a la solicitud. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    /** Costo estimado del envío (moneda). */
    @NotNull
    @PositiveOrZero
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal costoEstimado;

    /** Tiempo estimado (p. ej. resultado de distancias de Google Directions). */
    @NotNull
    @Convert(converter = DurationSecondsConverter.class)
    @Column(nullable = false)
    private Duration tiempoEstimado;

    /** Costo final real (puede ser null hasta cerrar la entrega). */
    @PositiveOrZero
    @Column(precision = 15, scale = 2)
    private BigDecimal costoFinal;

    /** Tiempo real medido (puede ser null hasta finalizar). */
    @Convert(converter = DurationSecondsConverter.class)
    private Duration tiempoReal;
}
