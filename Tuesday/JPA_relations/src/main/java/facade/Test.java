/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Test {
    
    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        Facade facade = Facade.getFacadeInstance(emf);
        
        
    }
}
