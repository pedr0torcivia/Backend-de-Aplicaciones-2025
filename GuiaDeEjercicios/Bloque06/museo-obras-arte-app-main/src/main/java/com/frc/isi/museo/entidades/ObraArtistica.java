package com.frc.isi.museo.entidades;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "ObraArtistica.GetById", query = "SELECT o FROM ObraArtistica o WHERE o.codigo = :codigo"),
        @NamedQuery(name = "ObraArtistica.GetByNombre", query = "SELECT o FROM ObraArtistica o WHERE o.nombre = :nombre")
})
public class ObraArtistica {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    private String nombre;
    private String anio;
    private double montoAsegurado;
    private boolean seguroTotal;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "museoId", referencedColumnName = "id")
    @ToString.Exclude
    private Museo museo;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "estiloArtisticoId", referencedColumnName = "id")
    @ToString.Exclude
    private EstiloArtistico estiloArtistico;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "autorId", referencedColumnName = "id")
    @ToString.Exclude
    private Autor autor;
}
