package museos.repositories;

import jakarta.persistence.TypedQuery;
import museos.entities.ObraArtistica;
import java.util.List;

public class ObraArtisticaRepository extends Repository<ObraArtistica, Integer> {

    public ObraArtisticaRepository() {
        super();
    }

    @Override
    protected Class<ObraArtistica> getEntityClass() {
        return ObraArtistica.class;
    }

    @Override
    public ObraArtistica getByName(String name) {
        String jpql = "SELECT o FROM ObraArtistica o WHERE o.nombre = :name";
        TypedQuery<ObraArtistica> query = manager.createQuery(jpql, ObraArtistica.class);
        query.setParameter("name", name);
        List<ObraArtistica> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    // Ejemplo de consultas útiles para estadísticas:
    public List<Object[]> findObrasPorMuseo() {
        String jpql = "SELECT m.nombre, COUNT(o) FROM ObraArtistica o JOIN o.museo m GROUP BY m.nombre";
        TypedQuery<Object[]> query = manager.createQuery(jpql, Object[].class);
        return query.getResultList();
    }

    public List<Object[]> findObrasPorAutor() {
        String jpql = "SELECT a.nombre, COUNT(o) FROM ObraArtistica o JOIN o.autor a GROUP BY a.nombre";
        TypedQuery<Object[]> query = manager.createQuery(jpql, Object[].class);
        return query.getResultList();
    }

    public Double findMontoTotalAsegurado() {
        String jpql = "SELECT SUM(o.montoAsegurado) FROM ObraArtistica o";
        TypedQuery<Double> query = manager.createQuery(jpql, Double.class);
        return query.getSingleResult();
    }

    public Object[] findMontoTotal() {
        String jpql = """
        SELECT
          SUM(CASE WHEN o.seguroTotal = TRUE  THEN o.montoAsegurado ELSE 0 END),
          SUM(CASE WHEN o.seguroTotal = FALSE THEN o.montoAsegurado ELSE 0 END),
          SUM(o.montoAsegurado)
        FROM ObraArtistica o
    """;
        TypedQuery<Object[]> query = manager.createQuery(jpql, Object[].class);
        return query.getSingleResult();
    }


    public List<Object[]> findCantidadObrasPorEstilo() {
        String jpql = """
            SELECT e.nombre, COUNT(o)
            FROM ObraArtistica o
            JOIN o.estilo e
            GROUP BY e.nombre
            ORDER BY e.nombre
        """;
        return manager.createQuery(jpql, Object[].class).getResultList();
        }

    public List<ObraArtistica> findObrasConSeguroParcialYPorEncimaDelPromedio() {
        String jpql = """
            SELECT o
            FROM ObraArtistica o
            WHERE o.seguroTotal = FALSE
            AND o.montoAsegurado > (
                SELECT COALESCE(AVG(x.montoAsegurado), 0)
                FROM ObraArtistica x
                WHERE x.seguroTotal = FALSE
            )
            ORDER BY CAST(o.anio AS integer) DESC
        """;
        return manager.createQuery(jpql, ObraArtistica.class).getResultList();
        
    }

    public List<ObraArtistica> findByMuseoNombre(String nombreMuseo) {
    String jpql = """
        SELECT o
        FROM ObraArtistica o
        JOIN FETCH o.museo m
        LEFT JOIN FETCH o.autor a
        LEFT JOIN FETCH o.estilo e
        WHERE m.nombre = :nombreMuseo
        ORDER BY o.nombre
    """;
    return manager.createQuery(jpql, ObraArtistica.class)
                  .setParameter("nombreMuseo", nombreMuseo)
                  .getResultList();
}
}
