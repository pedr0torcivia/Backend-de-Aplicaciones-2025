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
    // Setea el nombre de la secuencia y el tamaño de la asignación
    @Column(name = "ID_AGE_GROUP")
    private Integer id;

    @Column(name = "CODE", length = 16, nullable = false)
    private String code;

    // Transient porque no se persisten en la base de datos, solo en memoria
    @Transient
    private Integer minAge;

    @Transient
    private Integer maxAge;

    /**
     * Deriva minAge y maxAge a partir del literal CODE.
     * Ejemplos válidos:
     *  - "12"   → min=max=12
     *  - "6-12" → min=6, max=12
     *  - "13+"  → min=13, max=null
     */
    @PostLoad
    @PostPersist
    @PostUpdate
    public void initAges() {
        if (code == null || code.isBlank()) {
            minAge = null;
            maxAge = null;
            return;
        }

        String c = code.trim();
        try {
            if (c.endsWith("+")) {
                minAge = Integer.parseInt(c.replace("+", ""));
                maxAge = null;
            } else if (c.contains("-")) {
                String[] parts = c.split("-");
                minAge = Integer.parseInt(parts[0].trim());
                maxAge = Integer.parseInt(parts[1].trim());
            } else {
                int exact = Integer.parseInt(c);
                minAge = exact;
                maxAge = exact;
            }
        } catch (NumberFormatException e) {
            minAge = null;
            maxAge = null;
            System.err.println("Código de grupo de edad inválido: " + code);
        }
    }

    /** 
     * Devuelve true si la edad pertenece al rango representado por este grupo.
     */
    public boolean matchesAge(int age) {
        if (minAge == null && maxAge == null) return false; // no definido
        if (maxAge == null) return age >= minAge; // sin tope
        if (minAge.equals(maxAge)) return age == minAge; // exacto
        return age >= minAge && age <= maxAge; // rango
    }

    @Override
    public String toString() {
        return String.format("AgeGroup{id=%d, code='%s'}", id, code);
    }
}
