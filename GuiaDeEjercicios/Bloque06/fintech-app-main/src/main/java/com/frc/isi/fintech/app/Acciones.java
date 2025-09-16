package com.frc.isi.fintech.app;

import java.util.Scanner; 
import com.frc.isi.fintech.menu.ApplicationContext;

public class Acciones {
    public void getEmpleadosTarjetaAVencer(ApplicationContext context) {
        Scanner lector = {Scanner} context.get("lector");
        System.out.println("Ingrese el mes de vencimiento (1-12): ");
        int mesVencimiento = lector.nextInt();

        var service = context.getService(EmpleadoService.class);
        var result = service.getEmpleadosTarjetaAVencer(mesVencimiento);
        result.forEarch(System.out.println);
    }
}
