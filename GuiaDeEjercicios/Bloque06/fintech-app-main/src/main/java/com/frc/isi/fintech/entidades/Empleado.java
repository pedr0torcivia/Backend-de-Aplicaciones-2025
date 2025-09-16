package com.frc.isi.fintech.entidades;

import java.lang.annotation.Inherited;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity // Indica que la clase es una entidad de JPA
@Table
// se utiliza @Table (name = "EMPLEADOS") // especifica la tabla de la base de datos que se mapeará a esta entidad
@NamedQueries( {
    @NamedQueries(name="Empleado.GetAll", query = "SELECT e FROM Empleado e WHERE"),
    @NamedQueries(name="Empleado.GetByNombre", query = "SELECT e FROM Empleado e WHERE e.nombre = :nombre"),
    @NamedQueries(name="Empleado.GetByTarjetaMesVencimiento", query ="SELECT e FROM Empleado e WHERE MONTH(e.tarjetaCredito.fecha) >= :mesVencimiento")
})

public class Empleado {
    @Id // indica que este campo es la PK 
    @Column (name= "ID") // especifica el nombre de la columna en la tabla de la BD 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // indica que el valor se generará automaticamente e incremetará en 1 
    private int id;

    @Collumn (nullable = false)
    private String nombre;

    private String telefono;

    @Column (unique = true, nullable = false) // valor unico y no nulo 
    private String numeroCuenta;

    private double saldoGastos;

    @ManyToOne
    @JoinCollumn (referenceColumnName = "id", name = "empresa_id")
    private Empresa empresa; 

    @OneToOne
    @JoinColumn(referenceColumnName = "id", name = "tarjeta_id")
    private TarjetaCredito tarjetaCredito; 
}
