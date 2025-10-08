package games.repositories;

import java.util.List;

import jakarta.persistence.TypedQuery;
import games.entities.Juego;

public class JuegoRepository extends Repository<Juego, Integer> {

    public JuegoRepository() {
        super();
    }

    @Override
    protected Class<Juego> getEntityClass() {
        return Juego.class;
    }

    @Override
    public Juego getByName(String name) {
        String jpql = "SELECT j FROM Juego j WHERE j.titulo = :name";
        TypedQuery<Juego> query = manager.createQuery(jpql, Juego.class);
        query.setParameter("name", name);

        List<Juego> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}