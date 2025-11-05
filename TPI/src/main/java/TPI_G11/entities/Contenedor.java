package TPI_G11.entities;

import TPI_G11.entities.enums.EstadoContenedor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

/**
 * Entidad que representa un contenedor a ser transportado.
 * Cada contenedor está asociado a un cliente y puede tener distintos estados
 * según su ciclo logístico: BORRADOR, PROGRAMADO, EN_TRANSITO, ENTREGADO.
 */
@Entity
@Table(name = "contenedores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contenedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // Identificación única del contenedor

    @NotNull(message = "El peso del contenedor es obligatorio")
    @Positive(message = "El peso debe ser mayor que cero")
    @Column(nullable = false)
    private Double peso;   // Peso en kilogramos

    @NotNull(message = "El volumen del contenedor es obligatorio")
    @Positive(message = "El volumen debe ser mayor que cero")
    @Column(nullable = false)
    private Double volumen; // Volumen en metros cúbicos

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoContenedor estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}