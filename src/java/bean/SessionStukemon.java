/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entities.Trainer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author dam
 */
@Stateless
public class SessionStukemon {
    @PersistenceUnit EntityManagerFactory emf;
    
    public boolean insertTrainer(Trainer t){
        if (!trainerExists(t)) {
            EntityManager em = emf.createEntityManager();
            em.persist(t);
            em.close();
            return true;
        }
        return false;
    }
    public boolean trainerExists(Trainer t){
        return (emf.createEntityManager().find(Trainer.class, t.getName())) != null;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
