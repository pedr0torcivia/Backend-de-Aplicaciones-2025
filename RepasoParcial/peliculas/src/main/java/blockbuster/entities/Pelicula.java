package blockbuster.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "pelicula")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(name = "fecha_estreno", nullable = false)
    private LocalDate fechaEstreno;

    @Column(name = "precio_base_alquiler", nullable = false)
    private double precioBaseAlquiler;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Clasificacion clasificacion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "genero_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_pelicula_genero"))
    private Genero genero;

    @ManyToOne(optional = false)
    @JoinColumn(name = "director_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_pelicula_director"))
    private Director director;

    /**
     * Calcula el precio del alquiler aplicando las siguientes reglas:
     * - Si la película se estrenó hace <= 365 días: +25%
     * - Si además la clasificación es ADULTOS_18 o ADOLESCENTES_13: +5% adicional
     */
    public double calcularPrecioAlquiler() {
        double precio = precioBaseAlquiler;

        long diasDesdeEstreno = ChronoUnit.DAYS.between(fechaEstreno, LocalDate.now());
        if (diasDesdeEstreno <= 365) {
            precio *= 1.25;
        }

        if (clasificacion == Clasificacion.ADULTOS_18 || clasificacion == Clasificacion.ADOLESCENTES_13) {
            precio *= 1.05;
        }

        return precio;
    }
}
