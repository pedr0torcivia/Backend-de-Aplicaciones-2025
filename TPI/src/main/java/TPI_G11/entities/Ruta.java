package TPI_G11.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
@Table(name = "rutas",
       uniqueConstraints = {
         @UniqueConstraint(name = "uk_ruta_solicitud", columnNames = "solicitud_numero")
       })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Una solicitud puede tener como máximo una ruta asignada. */
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "solicitud_numero")
    private Solicitud solicitud;

    /** Cantidad total de tramos que componen la ruta. */
    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    @Builder.Default
    private Integer cantidadTramos = 0;

    /** Cantidad de depósitos involucrados en la ruta. */
    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    @Builder.Default
    private Integer cantidadDepositos = 0;
}