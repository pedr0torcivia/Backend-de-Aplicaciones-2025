package blockbuster.repositories;

import jakarta.persistence.TypedQuery;
import blockbuster.entities.Pelicula;

import java.util.List;

public class PeliculaRepository extends Repository<Pelicula, Integer> {

    public PeliculaRepository() {
        super();
    }

    @Override
    protected Class<Pelicula> getEntityClass() {
        return Pelicula.class;
    }

    @Override
    public Pelicula getByName(String name) {
        String jpql = "SELECT p FROM Pelicula p WHERE p.titulo = :name";
        TypedQuery<Pelicula> query = manager.createQuery(jpql, Pelicula.class);
        query.setParameter("name", name);
        List<Pelicula> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public List<Pelicula> getPeliculasXDirector(String director) {
        String jpql = "SELECT p FROM Pelicula p WHERE LOWER(p.director.nombre) LIKE LOWER(CONCAT('%', :director, '%'))";
        TypedQuery<Pelicula> query = manager.createQuery(jpql, Pelicula.class);
        query.setParameter("director", director);
        return query.getResultList();
    }

    public List<Object[]> promedioPrecioPorGenero() {
    String jpql = "SELECT g.nombre, AVG(p.precioBaseAlquiler) " +
        "FROM Pelicula p JOIN p.genero g " +
        "GROUP BY g.nombre " +
        "ORDER BY g.nombre";
    return manager.createQuery(jpql, Object[].class).getResultList();
    }
        
    public Pelicula peliculaMasReciente() {
        String jpql = "SELECT p FROM Pelicula p ORDER BY p.fechaEstreno DESC";
        TypedQuery<Pelicula> query = manager.createQuery(jpql, Pelicula.class);
        query.setMaxResults(1);
        List<Pelicula> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}