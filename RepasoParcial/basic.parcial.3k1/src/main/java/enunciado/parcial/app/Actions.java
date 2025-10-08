package enunciado.parcial.app;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import enunciado.parcial.entities.Empleado;
import enunciado.parcial.services.EmpleadoService;

public class Actions {

    /* 
     * Importa empleados desde el primer CSV que contenga "empleado" en el nombre (case-insensitive).
     */
    public void importarEmpleados(AppContext context) {
        var pathToImport = (URL) context.get("path");
        if (pathToImport == null) {
            throw new IllegalArgumentException("Falta 'path' en el contexto (URL del directorio a importar).");
        }

        try (var paths = Files.walk(Paths.get(pathToImport.toURI()))) {

            var csvFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().toLowerCase(Locale.ROOT).endsWith(".csv"))
                    .map(path -> path.toFile())
                    .toList();

            csvFiles.stream()
                    .filter(f -> f.getName().toLowerCase(Locale.ROOT).contains("empleado"))
                    .findFirst()
                    .ifPresentOrElse(f -> {
                        var service = context.getService(EmpleadoService.class);
                        try {
                            service.bulkInsert(f);
                            System.out.println("✔ Importación OK: " + f.getName());
                        } catch (IOException e) {
                            System.err.println("✖ Error leyendo CSV: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }, () -> {
                        throw new IllegalArgumentException("Archivo inexistente: no se encontró CSV con 'empleado' en el nombre.");
                    });

        } catch (IOException | URISyntaxException e) {
            System.err.println("✖ Error accediendo a archivos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void listarEmpleados(AppContext context) {
        var service = context.getService(EmpleadoService.class);
        var empleados = service.getAll();

        if (empleados.isEmpty()) {
            System.out.println("⚠ No hay empleados registrados en la base de datos.");
            return;
        }

        System.out.println("📋 Lista de empleados:");
        empleados.forEach(emp -> {
            var fecha = emp.getFechaIngreso() != null ? emp.getFechaIngreso().toString() : "Desconocido";
            var puesto = emp.getPuesto() != null ? emp.getPuesto().getNombre() : "Desconocido";
            var depto  = emp.getDepartamento() != null ? emp.getDepartamento().getNombre() : "Desconocido";
            var salario = emp.getSalario() != null ? emp.getSalario().doubleValue() : 0.0;

            System.out.printf(
                "ID: %d | Nombre: %s | Ingreso: %s | Puesto: %s | Departamento: %s | Salario: %.2f | Empleado Fijo: %s%n",
                emp.getId(), emp.getNombre(), fecha, puesto, depto, salario, emp.isEmpleadoFijo() ? "Sí" : "No"
            );
        });
    }

    public void mostrarEmpleadosFijosContratados(AppContext context) {
        var service = context.getService(EmpleadoService.class);
        var empleados = service.getAll();

        if (empleados.isEmpty()) {
            System.out.println("⚠ No hay empleados registrados en la base de datos.");
            return;
        }

        long fijos = empleados.stream().filter(Empleado::isEmpleadoFijo).count();
        long contratados = empleados.size() - fijos;
        System.out.printf("👥 Empleados Fijos: %d | Empleados Contratados: %d%n", fijos, contratados);
    }

    public void mostrarCantidadEmpleadosPorDepartamento(AppContext context) {
        var service = context.getService(EmpleadoService.class);
        var empleados = service.getAll();

        if (empleados.isEmpty()) {
            System.out.println("⚠ No hay empleados registrados en la base de datos.");
            return;
        }

        Map<String, Long> deptCount = empleados.stream()
                .filter(emp -> emp.getDepartamento() != null && emp.getDepartamento().getNombre() != null)
                .collect(Collectors.groupingBy(emp -> emp.getDepartamento().getNombre(), Collectors.counting()));

        System.out.println("🏢 Cantidad de empleados por departamento:");
        deptCount.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(String.CASE_INSENSITIVE_ORDER))
                .forEach(e -> System.out.printf("Departamento: %s | Cantidad: %d%n", e.getKey(), e.getValue()));
    }

    public void mostrarSalarioPromedioPorPuesto(AppContext context) {
        var service = context.getService(EmpleadoService.class);
        var empleados = service.getAll();

        if (empleados.isEmpty()) {
            System.out.println("⚠ No hay empleados registrados en la base de datos.");
            return;
        }

        var puestoSalaryAvg = empleados.stream()
                .filter(emp -> emp.getPuesto() != null && emp.getPuesto().getNombre() != null && emp.getSalario() != null)
                .collect(Collectors.groupingBy(
                        emp -> emp.getPuesto().getNombre(),
                        Collectors.averagingDouble(emp -> emp.getSalario().doubleValue())
                ));

        System.out.println("💰 Salario promedio por puesto:");
        puestoSalaryAvg.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(String.CASE_INSENSITIVE_ORDER))
                .forEach(e -> System.out.printf("Puesto: %s | Salario Promedio: %.2f%n", e.getKey(), e.getValue()));
    }
}
