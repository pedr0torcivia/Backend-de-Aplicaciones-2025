package ar.edu.utnfrc.backend.daos;

import java.util.List;
import ar.edu.utnfrc.backend.common.EntityManagerProvider;
import ar.edu.utnfrc.backend.dtos.LibrosXAutorDTO;
import ar.edu.utnfrc.backend.entities.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class LibroDAO {
    final private EntityManager em = EntityManagerProvider.getEntityManager();

    public LibroDAO(){ // CONSTRUCTOR VACIO PORQUE NO ES NECESARIO INICIALIZAR NADA
    }


    // SIEMPRE SE INICIA CON CADA TRANSACCIÓN em.getTransaction().begin();


    public void save(Libro libro) { // POST 
        em.getTransaction().begin();
        em.persist(libro);
        em.getTransaction().commit();
    }

    public Libro findById(Long id) { // BUSCAR POR ID
        return em.find(Libro.class, id);
    }

    public List<Libro> findAll(){ // BUSCAR TODOS
        TypedQuery<Libro> query = em.createQuery("SELECT a FROM Libro a", Libro.class); // las clases leen las tablas de la BD. Basicamente 
        // es un SELECT * FROM Libros
        return query.getResultList();
    }


    public void update(Libro libro) { // PUT
        em.getTransaction().begin();
        em.merge(libro); // ACTUALIZAR
        em.getTransaction().commit();
    }


    public void delete(Long libroId) { // BORRAR
        em.getTransaction().begin();
        Libro libro = this.findById(libroId);
        if(libro != null){
            em.remove(libro);
        }
        em.getTransaction().commit();
    }

    // Libro que contenga palabra dada
    public List<Libro> findBooksByKeyword(String keyword) {
            return em.createQuery(
                "SELECT l FROM Libro l " +
                "WHERE LOWER(l.titulo) LIKE :kw OR LOWER(l.autor.nombre) LIKE :kw",
                Libro.class
            )
            .setParameter("kw", "%" + keyword + "%")
            .getResultList();
    }

    // Cantidad de libros por autor
public List<LibrosXAutorDTO> countBooksByAuthor() {
    TypedQuery<Object[]> q = em.createQuery(
        "SELECT a.nombre, COUNT(l) " +
        "FROM Libro l JOIN l.autor a " +
        "GROUP BY a.nombre " +
        "ORDER BY COUNT(l) DESC",
        Object[].class
    );

    return q.getResultList()
            .stream()
            .map(r -> new LibrosXAutorDTO((String) r[0], (Long) r[1]))
            .toList();
}

    // Top 3 libros mas antiguos
    public List<Libro> findTop3LibrosMasAntiguos() {
        TypedQuery<Libro> query = em.createQuery(
            "SELECT l FROM Libro l ORDER BY l.anioPublicacion ASC", 
            Libro.class
        );
        query.setMaxResults(3);
        return query.getResultList();
    }
}

