package ar.edu.utnfrc.backend.entities;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "autor") 
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Builder 

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAutor; 

    @Column(nullable = false, unique = true) 
    private String nombre;

    // 1:N un autor tiene muchos libros
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Libro> libros; 

    public Autor(String linea) {
        String[] valores = linea.split(",");
        this.nombre = valores[3];
    }

    @Override
    public String toString() {
        return "Autor[ idAutor=" + idAutor + ", nombre=" + nombre +"]";
    }
}
