package com.example.demo;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class VehicleDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Vehicle v) {
        entityManager.persist(v);
    }

    public void update(Vehicle v) {
        entityManager.merge(v);
        entityManager.flush();
    }

    public void delete(Vehicle v) {
        entityManager.remove(v);
    }

    public Vehicle getById(int id) {
        return entityManager.find(Vehicle.class, id);
    }

    public List<Vehicle> getLatestVehicles(String sqlQuery) {
        Query query = entityManager.createNativeQuery(sqlQuery);
        return query.getResultList();
    }
}
