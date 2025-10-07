package ar.edu.utnfrc.backend.common;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerProvider {
    private static EntityManagerProvider emp;
    private EntityManager em;

private EntityManagerProvider(){
    // Iniciar EntityManager
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MiUnidad");
    em = emf.createEntityManager();
    }
    // Crear la instancia mediante SINGLETON
    public static EntityManager getEntityManager(){
    if(emp == null){
    emp = new EntityManagerProvider();
    }
    return emp.em;
    }
    // Cerrar conexion SE EJECUTA CUANDO SE INGRESA EL 0 EN EL MENÚ 
    public static void closeEntityManager(){
    if (emp.em != null){
    emp.em.close();
    emp = null;
    }

}}