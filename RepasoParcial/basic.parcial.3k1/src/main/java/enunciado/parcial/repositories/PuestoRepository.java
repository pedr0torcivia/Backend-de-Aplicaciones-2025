package enunciado.parcial.repositories;

import enunciado.parcial.entities.Puesto;

public class PuestoRepository extends Repository <Puesto, Integer>{
    
    public PuestoRepository() {
        super();
    }

    @Override
    protected Class<Puesto> getEntityClass() {
        return Puesto.class;
    }
}
