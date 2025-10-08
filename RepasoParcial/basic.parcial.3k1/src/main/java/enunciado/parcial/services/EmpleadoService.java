package enunciado.parcial.services;

import enunciado.parcial.services.interfaces.IService;
import enunciado.parcial.repositories.EmpleadoRepository;
import enunciado.parcial.entities.Empleado;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class EmpleadoService implements IService<Empleado, Integer> {

    private final EmpleadoRepository empleadoRepository;
    private final DepartamentoService departamentoService;
    private final PuestoService puestoService;

    public EmpleadoService() {
        this(new EmpleadoRepository(), new DepartamentoService(), new PuestoService());
    }
    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this(empleadoRepository, new DepartamentoService(), new PuestoService());
    }
    public EmpleadoService(EmpleadoRepository empleadoRepository,
                           DepartamentoService departamentoService,
                           PuestoService puestoService) {
        this.empleadoRepository = Objects.requireNonNull(empleadoRepository);
        this.departamentoService = Objects.requireNonNull(departamentoService);
        this.puestoService = Objects.requireNonNull(puestoService);
    }

    @Override public Empleado getById(Integer id) { return empleadoRepository.getById(id); }
    @Override public Empleado getOrCreateByName(String name) {
        String n = name == null ? null : name.trim();
        Empleado emp = empleadoRepository.getByName(n);
        if (emp == null) { emp = new Empleado(); emp.setNombre(n); empleadoRepository.create(emp); }
        return emp;
    }
    @Override public List<Empleado> getAll() { return empleadoRepository.getAllList(); }
    @Override public Stream<Empleado> getAllStream() { return empleadoRepository.getAllStream(); }

    public void bulkInsert(File file) throws IOException {
        if (this.existsAny()) {
            System.out.println("⚠ Ya hay empleados cargados en la BD. No se realizará la importación.");
            return;
        }
        try (Stream<String> lines = Files.lines(Paths.get(file.toURI()), StandardCharsets.UTF_8)) {
            lines.skip(1).forEach(linea -> {
                Empleado e = procesarLinea(linea);
                empleadoRepository.create(e);
            });
        }
    }

    public Empleado procesarLinea(String linea) {
        String[] t = linea.split(",");
        Empleado e = new Empleado();
        e.setNombre(t[0].trim());
        e.setEdad(Integer.parseInt(t[1].trim()));
        e.setFechaIngreso(LocalDate.parse(t[2].trim()));
        e.setSalario(new BigDecimal(t[3].trim()));
        e.setEmpleadoFijo(Boolean.parseBoolean(t[4].trim()));
        e.setDepartamento(departamentoService.getOrCreateByName(t[5].trim()));
        e.setPuesto(puestoService.getOrCreateByName(t[6].trim()));
        return e;
    }

    public boolean existsAny() {
        // simple y suficiente
        return !empleadoRepository.getAllList().isEmpty();
        // o versión eficiente en repo: SELECT e.id ... setMaxResults(1)
    }
}
