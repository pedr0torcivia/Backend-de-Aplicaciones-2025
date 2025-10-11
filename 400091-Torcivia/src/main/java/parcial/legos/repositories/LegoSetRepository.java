package parcial.legos.repositories;

import parcial.legos.entities.LegoSet;

import java.util.List;
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

}
