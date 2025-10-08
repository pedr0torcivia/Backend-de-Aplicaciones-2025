package museos.repositories;

import jakarta.persistence.TypedQuery;
import museos.entities.Autor;
import java.util.List;

public class AutorRepository extends Repository<Autor, Integer> {

    public AutorRepository() {
        super();
    }

    @Override
    protected Class<Autor> getEntityClass() {
        return Autor.class;
    }

    @Override
    public Autor getByName(String name) {
        String jpql = "SELECT a FROM Autor a WHERE a.nombre = :name";
        TypedQuery<Autor> query = manager.createQuery(jpql, Autor.class);
        query.setParameter("name", name);
        List<Autor> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    // Ejemplo: autores con más de una obra
    public List<Object[]> findAutoresConMasDeUnaObra() {
        String jpql = "SELECT a.nombre, COUNT(o) FROM ObraArtistica o JOIN o.autor a GROUP BY a.nombre HAVING COUNT(o) > 1";
        TypedQuery<Object[]> query = manager.createQuery(jpql, Object[].class);
        return query.getResultList();
    }
}
