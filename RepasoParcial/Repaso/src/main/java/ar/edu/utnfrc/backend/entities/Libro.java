package ar.edu.utnfrc.backend.entities;

import org.hibernate.annotations.JdbcType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "libros") // nombre de Tabla en BASE DE DATOS
@Data
@AllArgsConstructor //get()
@NoArgsConstructor //set()
@Builder // Auxiliar que usa patrón de diseño Builder 

public class Libro {
    @Id 
    private Long idLibro;
    
    @Column(nullable = false) // Cada columna se debe etiquetar con @Column 
    private String titulo; 

    @Column(nullable = false)
    private int anioPublicacion; 

    // Relacion con autor N:1
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "idAutor", nullable = false) //FK = idAutor 
    @ToString.Exclude // Evitar recusion
    private Autor autor;  // autor debe coincidir con el mappedBy en Autor

    // SE UTILIZA EN utilities/ArchivoCSV.java para crear un nuevo libro y guardarlo en la BD
    public Libro(String linea, Autor autor) {
        String[] valores = linea.split(",");
        this.idLibro = Long.parseLong(valores[0]);
        this.titulo = valores[1];
        this.anioPublicacion = Integer.parseInt(valores[2]);
        this.autor = autor; 
    }

    @Override
    public String toString() {
        return "Libro[ idLibro=" + idLibro + ", titulo=" + titulo + ", anioPublicacion=" + anioPublicacion + ", autor=" + autor.toString() + "]";
    }
}
