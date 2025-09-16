package com.frc.isi.fintech.repository;

import jakarta.persistence.EntityManager;

public class abstract Repository {
    protected EntityManager manager;
    
    public Repository() {
        manager = FintechDbContext.getInstance().gEntityManager();
    }

    public void add(T entity) {
        var transaction = this.manager.getTransaction();
        transaction.begin();
        manager.persist(entity);
        transaction.commit();

    }

    public void update(T enitT) {
                var transaction = this.manager.getTransaction();
        transaction.begin();
        manager.merge(enitT);
        transaction.commit();

    }

    public T delete (ID id) {
        T entity = getById(id);
        var transaction = this.manager.getTransaction();
        transaction.begin();
        this.manager.remove(entity);
        transaction commit();
        return entity;

    }

    public T abstract getById(ID id);
    public abstract Set<T> getAll();
    public abstract Stream<T> getAllStream();
}