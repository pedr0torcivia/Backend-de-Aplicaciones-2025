package enunciado.parcial.repositories;

import java.util.Set;
import java.util.List;
import java.util.stream.Stream;
import jakarta.persistence.TypedQuery; // Consulta tipada en JPA/Hibernate
import java.util.stream.Collectors;   // Métodos utilitarios para recolectar resultados de streams

import enunciado.parcial.repositories.context.DbContext;

import jakarta.persistence.EntityManager;

// Clase abstracta genérica que actúa como base para todos los repositorios JPA
// T -> Tipo de la entidad (por ejemplo, Empleado, Departamento, etc.)
// K -> Tipo de la clave primaria (por ejemplo, Integer, Long, etc.)
public abstract class Repository<T, K> {

    // EntityManager: objeto principal de JPA para interactuar con la base de datos
    protected EntityManager manager;

    // Constructor: inicializa el EntityManager desde un contexto compartido (singleton DbContext)
    public Repository() {
        manager = DbContext.getInstance().getManager();
    }

    // MÉTODOS CRUD BÁSICOS

    // CREATE -> Inserta una nueva entidad en la base de datos
    public void create(T entity) {
        var transaction = manager.getTransaction(); // Obtiene la transacción actual
        transaction.begin();                        // Inicia la transacción
        manager.persist(entity);                    // Inserta la entidad en la base de datos
        transaction.commit();                       // Confirma los cambios (commit)
    }

    // UPDATE -> Actualiza una entidad existente en la base de datos
    public void update(T entity) {
        var transaction = manager.getTransaction();
        transaction.begin();                        // Inicia la transacción
        manager.merge(entity);                      // Fusiona los cambios de la entidad con el registro persistente
        transaction.commit();                       // Confirma los cambios
    }

    // DELETE -> Elimina una entidad a partir de su clave primaria
    public T delete(K id) {
        var transaction = manager.getTransaction();
        transaction.begin();
        var entity = this.getById(id);              // Busca la entidad por su ID
        manager.remove(entity);                     // Elimina la entidad de la base de datos
        transaction.commit();                       // Confirma los cambios
        return entity;                              // Devuelve la entidad eliminada (útil para logs o auditoría)
    }

    // =========================
    // MÉTODOS ABSTRACTOS
    // =========================

    /* Read genericos */
    public Set<T> getAllSet() {
        TypedQuery<T> query = manager.createQuery(
            "SELECT e FROM " + getEntityClass().getSimpleName() + " e", getEntityClass()
        );
        return query.getResultList().stream().collect(Collectors.toSet());
    }

    public List<T> getAllList() {
        TypedQuery<T> query = manager.createQuery(
            "SELECT e FROM " + getEntityClass().getSimpleName() + " e", getEntityClass()
        );
        return query.getResultList();
    }

    public Stream<T> getAllStream() {
        TypedQuery<T> query = manager.createQuery(
            "SELECT e FROM " + getEntityClass().getSimpleName() + " e", getEntityClass()
        );
        return query.getResultStream();
    }

    
    /**
     * Recupera una entidad por su id (PRIMARY KEY).
     * - Abstracto porque depende del tipo de entidad (Museo, Obra, etc.)
     * - Se implementa con manager.find(Entidad.class, id)
     */
    public T getById(K id) {
        // Esto es genérico: usa manager.find() con la clase de la entidad
        return manager.find(getEntityClass(), id);
    }

    /**
     * Método genérico para recuperar una entidad por su campo 'nombre'.
     * - Funciona si la entidad T tiene un atributo llamado 'nombre'.
     * - Devuelve null si no se encuentra ninguna coincidencia.
     */
    public T getByName(String name) {
        // Construimos la query JPQL dinámicamente usando el nombre de la clase T
        // esto solo se puede hacer en Repository si todos las entidades tienen un nombre
        String jpql = "SELECT e FROM " + getEntityClass().getSimpleName() + " e WHERE e.nombre = :name";
        
        TypedQuery<T> query = manager.createQuery(jpql, getEntityClass());
        query.setParameter("name", name); // asigna el parámetro ':name' al valor pasado
        List<T> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * Método abstracto que debe implementar cada subclase
     * para devolver la clase concreta de la entidad T.
     * Esto es necesario para construir queries genéricas.
     */
    protected abstract Class<T> getEntityClass();

    /* 
    // Obtiene una entidad por su clave primaria (debe implementarse en repositorios concretos)
    public abstract T getById(K id);

    // Obtiene todas las entidades como un Set (sin duplicados)
    public abstract Set<T> getAllSet();

    // Obtiene todas las entidades como un Stream (útil para operaciones funcionales con Java Streams)
    public abstract Stream<T> getAllStrem();

    public T getByName(String name) {
        return null;
    }
    */
}