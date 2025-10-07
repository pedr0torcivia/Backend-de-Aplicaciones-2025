package ar.edu.utnfrc.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibrosXAutorDTO {
    private String nombreAutor;
    private Long cantidadLibros;


    @Override
    public String toString() {
        return "Autor: " + nombreAutor + " | Cantidad de Libros: " + cantidadLibros;
    }
}


