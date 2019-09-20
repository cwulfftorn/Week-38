/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entities.Customer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class Facade {
    
    private static Facade instance;
    private static EntityManagerFactory emf;
    
    public static Facade getFacadeInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new Facade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Customer getCustomer(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Customer.class, id);
        } finally {
            em.close();
        }
    }

    public List<Customer> getCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("Select c FROM Customer c");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Customer addCustomer(Customer cust) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cust);
            em.getTransaction().commit();
            return cust;
        } catch (Exception e) {
            em.getTransaction().rollback();

        } finally {
            em.close();
        }
        return null;
    }

    public void deleteCustomer(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("DELETE FROM Customer c WHERE c.id = :id").setParameter("id", id);
            query.executeUpdate();

        } finally {
            em.close();
        }
    }

    public Customer editCustomer(Customer cust) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(cust);
            em.getTransaction().commit();
            return cust;
        } finally {
            em.close();
        }

    }

}