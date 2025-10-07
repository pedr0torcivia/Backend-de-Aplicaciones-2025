package ar.edu.utnfrc.backend.daos;

import java.util.List;
import ar.edu.utnfrc.backend.common.EntityManagerProvider;
import ar.edu.utnfrc.backend.entities.Autor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class AutorDAO {
    final private EntityManager em = EntityManagerProvider.getEntityManager();
    public AutorDAO(){
    }

    public void save(Autor autor) {
        em.getTransaction().begin();
        em.persist(autor);
        em.getTransaction().commit();
    }

    public Autor findById(Long id) {
        return em.find(Autor.class, id);
    }

public Autor findByName(String nombre) {
    try {
        return em.createQuery(
            "SELECT a FROM Autor a WHERE a.nombre = :nombre", Autor.class)
            .setParameter("nombre", nombre)
            .getSingleResult();
    } catch (jakarta.persistence.NoResultException e) {
        return null;
    }
}
    public List<Autor> findAll(){
        TypedQuery<Autor> query = em.createQuery("SELECT a FROM Autor a",
                Autor.class);
        return query.getResultList();
    }

    public void update(Autor autor) {
        em.getTransaction().begin();
        em.merge(autor);
        em.getTransaction().commit();
    }

    public void delete(Long autorId) {
        em.getTransaction().begin();
        Autor autor = this.findById(autorId);
        if(autor != null){
            em.remove(autor);
        }
        em.getTransaction().commit();
    }
}
