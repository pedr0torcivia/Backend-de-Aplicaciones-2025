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

    public List<Object[]> findTop5Generos() {
        String jpql = "SELECT g.nombre, SUM(j.jugando) AS total " +
            "FROM Juego j " +
            "JOIN j.genero g " +
            "GROUP BY g.id, g.nombre " +
            "ORDER BY total DESC";
        TypedQuery<Object[]> query = manager.createQuery(jpql, Object[].class);
        query.setMaxResults(5);
        return query.getResultList();
    }

    public List<Object[]> findCantidadDeJuegosPorDesarrollador() {
        String jpql =  "SELECT d.nombre, COUNT(j) AS total " +
                    "FROM Juego j " +
                    "JOIN j.desarrollador d " +
                    "GROUP BY d.id, d.nombre " +
                    "HAVING COUNT(j) > 30";
        TypedQuery<Object[]> query = manager.createQuery(jpql, Object[].class);
        return query.getResultList();
    }
public Long findJuegosConMasDeUnDesarrollador() {
    // Modelo actual: JUEGOS.DESA RROLLADOR_ID es una sola FK -> no hay multi-dev.
    return 0L;
}
public String findBestDeveloper() {
    String jpql = """
        select d.nombre
        from Juego j
        join j.desarrollador d
        where j.rating is not null
        group by d.id, d.nombre0
        order by avg(j.rating) desc
    """;
    var q = manager.createQuery(jpql, String.class).setMaxResults(1);
    var res = q.getResultList();
    return res.isEmpty() ? null : res.get(0);
}
}