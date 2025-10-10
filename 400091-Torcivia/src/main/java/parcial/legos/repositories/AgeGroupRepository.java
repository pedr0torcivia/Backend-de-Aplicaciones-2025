package parcial.legos.repositories;

import parcial.legos.entities.AgeGroup;

import java.util.List;

public class AgeGroupRepository extends Repository<AgeGroup, Integer> {

    public AgeGroupRepository() {
        super();
    }

    @Override
    protected Class<AgeGroup> getEntityClass() {
        return AgeGroup.class;
    }

    public AgeGroup getByCode(String code) {
        if (code == null || code.isBlank()) return null;
        var q = manager.createQuery(
            "SELECT a FROM AgeGroup a WHERE UPPER(a.code) = UPPER(:c)", AgeGroup.class);
        q.setParameter("c", code.trim());
        List<AgeGroup> res = q.getResultList();
        return res.isEmpty() ? null : res.get(0);
    }
}