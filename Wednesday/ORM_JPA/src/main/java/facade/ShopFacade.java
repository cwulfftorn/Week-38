/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entities.Customer;
import entities.ItemType;
import entities.Order;
import entities.OrderLine;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class ShopFacade {

    private static ShopFacade instance;
    private static EntityManagerFactory emf;
    
    public static ShopFacade getFacadeInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ShopFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Customer createCustomer(Customer newCustomer) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(newCustomer);
            em.getTransaction().commit();
            return newCustomer;
        } finally {
            em.close();
        }
    }

    public Customer getCustomer(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Customer cust = em.find(Customer.class, id);
            return cust;
        } finally {
            em.close();
        }
    }

    public List<Customer> getAllCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c", Customer.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public ItemType createItemType(ItemType newItemType) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(newItemType);
            em.getTransaction().commit();
            return newItemType;
        } finally {
            em.close();
        }
    }

    public ItemType getItemType(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            ItemType item = em.find(ItemType.class, id);
            return item;
        } finally {
            em.close();
        }
    }

    public Order createOrder(Order order, Customer cust) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            order.setCustomer(cust);
            em.persist(order);
            em.getTransaction().commit();
            return order;
        } finally {
            em.close();
        }
    }

    public OrderLine createOrderLine(OrderLine oline, ItemType item, Order order) {
        EntityManager em = emf.createEntityManager();
        oline.setItemType(item);
        List<OrderLine> orderLines = new ArrayList();
        orderLines.add(oline);
        order.setOrderLine(orderLines);
        try {
            em.getTransaction().begin();
            em.persist(oline);
            em.getTransaction().commit();
            return oline;
        } finally {
            em.close();
        }
    }
    
    public List<Order> getAllOrders(Customer cust) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Order> query = em.createNamedQuery("SELECT o FROM Order o WHERE Order.Customer = " + cust.getName(), Order.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
