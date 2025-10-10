package parcial.legos.repositories;

import jakarta.persistence.EntityManager;
import parcial.legos.entities.LegoSet;
import parcial.legos.repositories.context.DbContext;

import java.util.List;
import java.util.stream.Stream;

public class LegoSetRepository {

    private final EntityManager em;

    public LegoSetRepository() {
        this.em = DbContext.getInstance().getManager();
    }

    /** Busca un LegoSet por ID */
    public LegoSet getById(Integer id) {
        return em.find(LegoSet.class, id);
    }

    /** Busca un LegoSet por nombre (SET_NAME) */
    public LegoSet getByName(String name) {
        if (name == null || name.isBlank()) return null;

        var q = em.createQuery(
                "SELECT s FROM LegoSet s WHERE UPPER(s.setName) = UPPER(:n)",
                LegoSet.class
        );
        q.setParameter("n", name.trim());

        List<LegoSet> results = q.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    /** Persiste un nuevo LegoSet */
    public void create(LegoSet set) {
        em.getTransaction().begin();
        em.persist(set);
        em.getTransaction().commit();
    }

    /** Devuelve todos los LegoSets como lista */
    public List<LegoSet> getAllList() {
        return em.createQuery("SELECT s FROM LegoSet s", LegoSet.class)
                .getResultList();
    }

    /** Devuelve todos los LegoSets como Stream (útil para procesamientos grandes) */
    public Stream<LegoSet> getAllStream() {
        return em.createQuery("SELECT s FROM LegoSet s", LegoSet.class)
                .getResultStream();
    }

    /** Actualiza un LegoSet existente (merge) */
    public LegoSet update(LegoSet set) {
        em.getTransaction().begin();
        LegoSet merged = em.merge(set);
        em.getTransaction().commit();
        return merged;
    }

    /** Elimina un LegoSet por instancia */
    public void delete(LegoSet set) {
        if (set == null) return;
        em.getTransaction().begin();
        LegoSet attached = em.contains(set) ? set : em.merge(set);
        em.remove(attached);
        em.getTransaction().commit();
    }

    /** Elimina por ID (busca y remueve si existe) */
    public void deleteById(Integer id) {
        LegoSet found = getById(id);
        if (found != null) delete(found);
    }
}
