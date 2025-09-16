package com.frc.isi.fintech.entidades;

import java.time.LocalDate;

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

public class TarjetaCredito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // indica que el valor se generará automaticamente e incremetará en 1
    private int id;
    private String nombre;
    private String numero;
    private LocalDate fechaVencimiento;

    @OneToOne (mappedBy = "tarjetaCredito", fetch= fetchType.EAGER)
    @JoinColumn(referenceColumnName = "id", name = "empleado_id")
    private Empleado empleado; 
}
