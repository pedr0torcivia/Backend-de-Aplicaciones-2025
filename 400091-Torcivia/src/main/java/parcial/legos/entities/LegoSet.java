package parcial.legos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "LEGO_SETS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LegoSet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LEGO_SET_ID")
    @SequenceGenerator(name = "SEQ_LEGO_SET_ID", sequenceName = "SEQ_LEGO_SET_ID", allocationSize = 1)
    @Column(name = "ID_SET")
    private Integer id;

    @Column(name = "PROD_ID", nullable = false)
    private Integer productId;

    @Column(name = "SET_NAME", length = 200, nullable = false)
    private String setName;

    @Column(name = "PROD_DESC", length = 2048)
    private String productDescription;

    @Column(name = "REVIEW_DIFFICULTY", length = 32)
    private String reviewDifficulty;

    @Column(name = "PIECE_COUNT")
    private Integer pieceCount;

    // DECIMAL(3,1) y DECIMAL(10,2) → BigDecimal
    @Column(name = "STAR_RATING", precision = 3, scale = 1)
    private BigDecimal starRating;

    @Column(name = "LIST_PRICE", precision = 10, scale = 2)
    private BigDecimal listPrice;

    // FKs (obligatorias)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "THEME_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_LEGOSET_THEME"))
    private Theme theme;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "AGE_GROUP_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_LEGOSET_AGEGROUP"))
    private AgeGroup ageGroup;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRY_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_LEGOSET_COUNTRY"))
    private Country country;
}
