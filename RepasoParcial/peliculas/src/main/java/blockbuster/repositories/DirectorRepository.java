package blockbuster.repositories;

import jakarta.persistence.TypedQuery;
import blockbuster.entities.Director;

import java.util.List;

public class DirectorRepository extends Repository<Director, Integer> {

    public DirectorRepository() {
        super();
    }

    @Override
    protected Class<Director> getEntityClass() {
        return Director.class;
    }

    @Override
    public Director getByName(String name) {
        String jpql = "SELECT d FROM Director d WHERE d.nombre = :name";
        TypedQuery<Director> query = manager.createQuery(jpql, Director.class);
        query.setParameter("name", name);
        List<Director> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}