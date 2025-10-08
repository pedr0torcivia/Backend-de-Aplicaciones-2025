package games.repositories;

import java.util.List;

import jakarta.persistence.TypedQuery;
import games.entities.Plataforma;

public class PlataformaRepository extends Repository<Plataforma, Integer> {

    public PlataformaRepository() {
        super();
    }

    @Override
    protected Class<Plataforma> getEntityClass() {
        return Plataforma.class;
    }

    @Override 
    public Plataforma getByName(String name) {
        String jpql = "SELECT p FROM Plataforma p WHERE p.nombre = :name";
        TypedQuery<Plataforma> query = manager.createQuery(jpql, Plataforma.class);
        query.setParameter("name", name);

        List<Plataforma> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
