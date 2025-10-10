package parcial.legos.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "THEMES", uniqueConstraints = {
        @UniqueConstraint(name = "UK_THEMES_NAME", columnNames = "NAME")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_THEME_ID")
    @SequenceGenerator(name = "SEQ_THEME_ID", sequenceName = "SEQ_THEME_ID", allocationSize = 1)
    @Column(name = "ID_THEME")
    private Integer id;

    @Column(name = "NAME", length = 120, nullable = false)
    private String name;
}
