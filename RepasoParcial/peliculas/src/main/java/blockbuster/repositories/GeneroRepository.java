package blockbuster.repositories;

import jakarta.persistence.TypedQuery;
import blockbuster.entities.Genero;

import java.util.List;

public class GeneroRepository extends Repository<Genero, Integer> {

    public GeneroRepository() {
        super();
    }

    @Override
    protected Class<Genero> getEntityClass() {
        return Genero.class;
    }

    @Override
    public Genero getByName(String name) {
        String jpql = "SELECT g FROM Genero g WHERE g.nombre = :name";
        TypedQuery<Genero> query = manager.createQuery(jpql, Genero.class);
        query.setParameter("name", name);
        List<Genero> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}