package com.example.demo;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class GreetingDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Greeting greeting) {
        entityManager.persist(greeting);
        return;
    }

    public Greeting getById(int id) {
        return entityManager.find(Greeting.class, id);
    }

}
