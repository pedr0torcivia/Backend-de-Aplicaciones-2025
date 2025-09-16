package com.frc.isi.fintech.services;

import com.frc.isi.fintech.repository.EmpleadoRepository;

public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService() {
        this.empleadoRepository = new EmpleadoRepository(); // inyeccion de dependencias 
    }
    
    public List<> getEmpleadosTarjetaAVencer(int mesVencimiento) {
        var resultStream = empleadoRepository.getByVencimientoTarjeta(mesVencimiento);
        return resultStream
        .mapToInt(r -> new EmpleadosTarjetaPorVencer(r.getNombre(), r.getTelefono(), r.getNumeroCuenta(), 
        r.getTarjetaCredito().getNumero()).collect(Collectors.toList()));
    }
}
