package parcial.legos.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "COUNTRIES", uniqueConstraints = {
        @UniqueConstraint(name = "UK_COUNTRIES_CODE", columnNames = "CODE")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COUNTRY_ID")
    @SequenceGenerator(name = "SEQ_COUNTRY_ID", sequenceName = "SEQ_COUNTRY_ID", allocationSize = 1)
    @Column(name = "ID_COUNTRY")
    private Integer id;

    @Column(name = "CODE", length = 3, nullable = false)
    private String code;

    @Column(name = "NAME", length = 100, nullable = false)
    private String name;
}
