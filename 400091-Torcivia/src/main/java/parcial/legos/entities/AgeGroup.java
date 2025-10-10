package parcial.legos.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "AGE_GROUPS", uniqueConstraints = {
        @UniqueConstraint(name = "UK_AGE_GROUP_CODE", columnNames = "CODE")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AgeGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AGE_GROUP_ID")
    @SequenceGenerator(name = "SEQ_AGE_GROUP_ID", sequenceName = "SEQ_AGE_GROUP_ID", allocationSize = 1)
    @Column(name = "ID_AGE_GROUP")
    private Integer id;

    @Column(name = "CODE", length = 16, nullable = false)
    private String code;

    /**
     * Determina si una edad pertenece al rango representado por este grupo.
     * Formatos válidos de CODE:
     * - "12"    → min = max = 12
     * - "6-12"  → min = 6, max = 12
     * - "13+"   → min = 13, max = null (sin tope superior)
     */
    public boolean matchesAge(int age) {
        if (code == null || code.isBlank()) return false;

        try {
            // Caso 1: intervalo cerrado "num-num"
            if (code.contains("-")) {
                String[] parts = code.split("-");
                int min = Integer.parseInt(parts[0].trim());
                int max = Integer.parseInt(parts[1].trim());
                return age >= min && age <= max;
            }

            // Caso 2: intervalo abierto superior "num+"
            if (code.endsWith("+")) {
                int min = Integer.parseInt(code.replace("+", "").trim());
                return age >= min;
            }

            // Caso 3: valor exacto "num"
            int exact = Integer.parseInt(code.trim());
            return age == exact;

        } catch (NumberFormatException e) {
            // Por si el literal es inválido
            System.err.println("Código de grupo de edad inválido: " + code);
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("AgeGroup{id=%d, code='%s'}", id, code);
    }
}
