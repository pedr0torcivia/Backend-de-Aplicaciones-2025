package parcial.legos.repositories;

import jakarta.persistence.TypedQuery;
import parcial.legos.entities.Country;

import java.util.List;

public class CountryRepository extends Repository<Country, Integer> {

    public CountryRepository() {
        super();
    }

    @Override
    protected Class<Country> getEntityClass() {
        return Country.class;
    }

    @Override
    public Country getByName(String name) {
        String jpql = "SELECT c FROM Country c WHERE c.name = :name";
        TypedQuery<Country> query = manager.createQuery(jpql, Country.class);
        query.setParameter("name", name);
        List<Country> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public Country getByCode(String code) {
        if (code == null || code.isBlank()) return null;
        var q = manager.createQuery(
            "SELECT c FROM Country c WHERE UPPER(c.code) = UPPER(:code)", Country.class);
        q.setParameter("code", code.trim());
        var list = q.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
}