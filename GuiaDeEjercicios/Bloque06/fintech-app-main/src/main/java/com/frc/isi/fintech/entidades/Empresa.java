package com.frc.isi.fintech.entidades;

import java.lang.annotation.Inherited;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // indica que el valor se generará automaticamente e incremetará en 1
    private int id;
    private String nombre;
    private double comision;

    @OneToMany (mappedBy = "empresa", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Empleado> empleados; 
}
