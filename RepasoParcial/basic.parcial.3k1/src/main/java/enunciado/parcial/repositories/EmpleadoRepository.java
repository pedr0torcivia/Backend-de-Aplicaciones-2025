package enunciado.parcial.repositories;

import enunciado.parcial.entities.Empleado;

public class EmpleadoRepository extends Repository<Empleado, Integer> {

    public EmpleadoRepository() {
        super();
    }

    @Override
    protected Class<Empleado> getEntityClass() {
        return Empleado.class;
    }
}
