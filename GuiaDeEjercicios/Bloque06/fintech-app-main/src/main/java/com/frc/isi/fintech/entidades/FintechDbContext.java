package com.frc.isi.fintech.entidades;

public class FintechDbContext {

    public static FintechDbContext instance = null; 

    private EntityManager manager; 

    private FintechDbContext(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("fintech"); // se coloca el nombre de la unidad de persistencia del xml
        manager = emf.createEntityManager(); // registra el driver y cuando lo requiera utiliza una conexion con la BD
    }

    public static FintechDbContext getInstance() {
        if (instance == null) {
            instance = new FintechDbContext();
            return instance;
        }
    }

    public gEntityManager() {
        return manager;
    }
}
