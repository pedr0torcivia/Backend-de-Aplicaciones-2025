package games.repositories;

import java.util.List;

import jakarta.persistence.TypedQuery;
import games.entities.Desarrollador;

public class DesarrolladorRepository extends Repository<Desarrollador, Integer> {

    public DesarrolladorRepository() {
        super();
    }

    @Override
    protected Class<Desarrollador> getEntityClass() {
        return Desarrollador.class;
    }

    @Override 
    public Desarrollador getByName(String name) {
        String jpql = "SELECT p FROM Desarrollador p WHERE p.nombre = :name";
        TypedQuery<Desarrollador> query = manager.createQuery(jpql, Desarrollador.class);
        query.setParameter("name", name);

        List<Desarrollador> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
