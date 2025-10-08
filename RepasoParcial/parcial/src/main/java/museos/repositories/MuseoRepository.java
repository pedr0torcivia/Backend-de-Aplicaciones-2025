package museos.repositories;

import jakarta.persistence.TypedQuery;
import museos.entities.Museo;
import java.util.List;

public class MuseoRepository extends Repository<Museo, Integer> {

    public MuseoRepository() {
        super();
    }

    @Override
    protected Class<Museo> getEntityClass() {
        return Museo.class;
    }

    @Override
    public Museo getByName(String name) {
        String jpql = "SELECT m FROM Museo m WHERE m.nombre = :name";
        TypedQuery<Museo> query = manager.createQuery(jpql, Museo.class);
        query.setParameter("name", name);
        List<Museo> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    // Ejemplo de consulta adicional (opcional)
    public List<Object[]> findCantidadDeObrasPorMuseo() {
        String jpql = "SELECT m.nombre, COUNT(o) FROM ObraArtistica o JOIN o.museo m GROUP BY m.nombre";
        TypedQuery<Object[]> query = manager.createQuery(jpql, Object[].class);
        return query.getResultList();
    }
}
