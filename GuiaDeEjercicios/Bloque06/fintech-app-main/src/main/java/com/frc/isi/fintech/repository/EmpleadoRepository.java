package com.frc.isi.fintech.repository;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.frc.isi.fintech.entidades.Empleado;

public class EmpleadoRepository extends Repository<Empleado, Integer>{
    
    public EmpleadoRepository(){
        super();
    }

    public Empleado getByNombre(String nombre) {
        var stream = this.manager.createNamedQuery("Empleado.GetByNombre", Empleado.class).setParameter("nombre", nombre).getResultStream();
        return stream.findFirst().orElseThrow(()-> new IllegalArgumentException("No existe un empleado con ese nombre"));
    }
    @Override
    public Set<Empleado> getAll() {
        this.manager.createNamedQuery("Empleado.GetAll", Empleado.class).getResultList().stream().collect(Collectors.toSet());
    }

    public abstract Set<Empleado> getAll();{
        return this.manager.createNamedQuery("Empleado.GetAll", Empleado.class).getResultList().stream().collect(Collectors.toSet());
    }

    public abstract Stream<Empleado> getAllStream(){
        return this.manager.createNamedQuery("Empleado.GetAll", Empleado.class).getResultList().stream();
    };

    public Stream<Empleado> getByVencimientoTarjeta(int mes) {
        return this.manager.createNamedQuery("Empleado.GetByTarjetaMesVencimiento", Empleado.class.setParameter("mes vencimiento", mes).getResultSteam())
    }
}