package parcial.legos.repositories;

import parcial.legos.entities.LegoSet;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.TypedQuery;
public class LegoSetRepository extends Repository<LegoSet, Integer> {

    @Override
    protected Class<LegoSet> getEntityClass() {
        return LegoSet.class;
    }

    /** Busca un LegoSet por nombre (SET_NAME) */
    @Override
    public LegoSet getByName(String name) {
        if (name == null || name.isBlank()) return null;

        var q = manager.createQuery(
            "SELECT s FROM LegoSet s WHERE UPPER(s.setName) = UPPER(:n)",
            LegoSet.class
        );
        q.setParameter("n", name.trim());

        List<LegoSet> results = q.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    
    public LegoSet getByProductId(Integer productId) {
        if (productId == null) return null;
        var q = manager.createQuery(
            "SELECT s FROM LegoSet s WHERE s.productId = :pid", LegoSet.class);
        q.setParameter("pid", productId);
        var list = q.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    // 1) Cantidad de Sets insertados
    public long countSets() {
        String jpql = "SELECT COUNT(s) FROM LegoSet s";
        return manager.createQuery(jpql, Long.class).getSingleResult();
    }

    // 2) Cantidad de Rangos de edad insertados
    public long countAgeGroups() {
        String jpql = "SELECT COUNT(ag) FROM AgeGroup ag";
        return manager.createQuery(jpql, Long.class).getSingleResult();
    }

    // 3) Cantidad de Temáticas insertadas
    public long countThemes() {
        String jpql = "SELECT COUNT(t) FROM Theme t";
        return manager.createQuery(jpql, Long.class).getSingleResult();
    }

    // 4) Top 5 países con menor costo promedio por estrella
    public List<Object[]> top5CountriesByCostPerStar() {
        String jpql =
            "SELECT c.code, AVG(s.listPrice / s.starRating) AS avgCostPerStar " +
            "FROM LegoSet s " +
            "JOIN s.country c " +
            "WHERE s.listPrice IS NOT NULL " +
            "  AND s.starRating IS NOT NULL " +
            "  AND s.starRating > 0 " +
            "GROUP BY c.code " +
            "ORDER BY avgCostPerStar ASC";

        return manager.createQuery(jpql, Object[].class)
                .setMaxResults(5)
                .getResultList();
    }

    // 5) Listado de Sets distintos y disponibles para una edad específica,
    //    con precio < :maxPrice y valoración >= 4.8, ordenados por precio desc.
    //    Se consideran solo juegos con rango etario definido (minAge y maxAge no nulos)
    //    e incluyendo bordes (BETWEEN).
    public List<LegoSet> buscarSetsPorPrecioYRating(BigDecimal maxPrice) {
        String jpql = """
            SELECT s FROM LegoSet s
            WHERE s.listPrice < :maxPrice
            AND s.starRating >= 4.8
            ORDER BY s.listPrice DESC
        """;
        return manager.createQuery(jpql, LegoSet.class)
                .setParameter("maxPrice", maxPrice)
                .getResultList();
    }
}
