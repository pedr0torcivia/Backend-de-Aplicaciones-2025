package parcial.legos.repositories;

import jakarta.persistence.TypedQuery;
import parcial.legos.entities.Theme;

import java.util.List;

public class ThemeRepository extends Repository<Theme, Integer> {

    public ThemeRepository() {
        super();
    }

    @Override
    protected Class<Theme> getEntityClass() {
        return Theme.class;
    }

    @Override
    public Theme getByName(String name) {
        String jpql = "SELECT a FROM Theme a WHERE a.name = :name";
        TypedQuery<Theme> query = manager.createQuery(jpql, Theme.class);
        query.setParameter("name", name);
        List<Theme> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}