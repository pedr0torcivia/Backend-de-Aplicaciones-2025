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

    public Long findJuegosConMasDeUnDesarrollador(){
        String jpql = "SELECT COUNT(j) FROM Juego j WHERE j.desarrollador IS NOT NULL GROUP BY j.titulo HAVING COUNT(j.desarrollador) > 1";
        TypedQuery<Long> query = manager.createQuery(jpql, Long.class);
        List<Long> results = query.getResultList();
        return (long) results.size();
    }

    public String findBestDeveloper(){
        String jpql = "SELECT d.nombre " +
                    "FROM Juego j " +
                    "JOIN j.desarrollador d " +
                    "GROUP BY d.id, d.nombre " +
                    "ORDER BY COUNT(j) DESC";
        TypedQuery<String> query = manager.createQuery(jpql, String.class);
        query.setMaxResults(1);
        List<String> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}