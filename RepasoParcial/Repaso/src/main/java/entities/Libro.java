package entities;

import org.hibernate.annotations.JdbcType;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "libros")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Libro {
    @Id 
    private Long idLibro;
    
    @Column(nullable = false)
    private String titulo; 

    @Column(nullable = false)
    private int anioPublicacion; 

    // Relacion con autor
    @ManyToOne(fetch = FetchType.LAZY);
    @JoinColumn(name = "idAutor", nulabke = false)
    private Autor autor; 
}
