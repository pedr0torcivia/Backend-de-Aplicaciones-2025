package museos.repositories;

import jakarta.persistence.TypedQuery;
import museos.entities.EstiloArtistico;
import java.util.List;

public class EstiloArtisticoRepository extends Repository<EstiloArtistico, Integer> {

    public EstiloArtisticoRepository() {
        super();
    }

    @Override
    protected Class<EstiloArtistico> getEntityClass() {
        return EstiloArtistico.class;
    }

    @Override
    public EstiloArtistico getByName(String name) {
        String jpql = "SELECT e FROM EstiloArtistico e WHERE e.nombre = :name";
        TypedQuery<EstiloArtistico> query = manager.createQuery(jpql, EstiloArtistico.class);
        query.setParameter("name", name);
        List<EstiloArtistico> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    // Ejemplo: estilos más frecuentes en las obras
    public List<Object[]> findTop5EstilosPorCantidadDeObras() {
        String jpql = "SELECT e.nombre, COUNT(o) AS total FROM ObraArtistica o JOIN o.estilo e " +
                      "GROUP BY e.id, e.nombre ORDER BY total DESC";
        TypedQuery<Object[]> query = manager.createQuery(jpql, Object[].class);
        query.setMaxResults(5);
        return query.getResultList();
    }
}
