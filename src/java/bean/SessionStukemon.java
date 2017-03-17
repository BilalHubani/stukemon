/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entities.Battle;
import entities.Pokemon;
import entities.Trainer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

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
    public Trainer getTrainerByName(String s){
        return emf.createEntityManager().find(Trainer.class, s);
    }
    public List<Trainer> selectAllTrainers(){
        return emf.createEntityManager().createNamedQuery("Trainer.findAll").getResultList();
        
    }
    public boolean insertStukemon(Pokemon p){
        if (!stukemonExists(p)) {
            EntityManager em = emf.createEntityManager();
            em.persist(p);
            em.close();
            return true;
        }
        return false;
    }
    public boolean stukemonExists(Pokemon p){
        return (emf.createEntityManager().find(Pokemon.class, p.getName())) != null;
    }
    
    public boolean deleteStukemon(Pokemon p){
        EntityManager em = emf.createEntityManager();
        Pokemon poke = em.find(Pokemon.class, p.getName());
        boolean ok = false;
        if (poke != null) {
            em.remove(poke);
            ok = true;
        } 
        em.close();
        return ok;        
    }
    
    public List<Pokemon> selectAllStukemons(){
        return emf.createEntityManager().createNamedQuery("Pokemon.findAll").getResultList();        
    }
    public List<Pokemon> selectAllStukemonsOrdered(){
        return emf.createEntityManager().createQuery("Select p from Pokemon p order by p.level desc, p.life desc").getResultList();        
    }
    public List<Trainer> selectAllTrainersOrdered(){
        return emf.createEntityManager().createQuery("Select t from Trainer t order by t.points desc").getResultList();        
    }
    public List<String> selectAllBattlesOrdered(){
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("Select b.winner, Count(b) from Battle b group by b.winner ");
        List<Object[]> list = q.getResultList();
        List<String> stringList = new ArrayList<String>();
        for (int i = 0; i<list.size();i++) {
            String s = list.get(i)[0].toString().split("=")[1];
            s = s.split("]")[0];
            stringList.add(s);
            stringList.add(list.get(i)[1].toString());
        }
        return stringList;        
    }
    public Pokemon getStukemonByName(String s){
        return emf.createEntityManager().find(Pokemon.class, s);
    }
    
    public boolean setTrainerPotions(Trainer t, int p){
        EntityManager em = emf.createEntityManager();
        Trainer tr = em.find(Trainer.class, t.getName());
        boolean ok = false;
        if (tr != null && p*10 < tr.getPoints()) {
            tr.setPotions(t.getPotions()+p);
            tr.setPoints(t.getPoints()-p*10);            
            em.persist(tr);
            em.close();
            ok = true;
        }
         return ok;
    }
    public boolean lvlUpLife(Trainer t, Pokemon p){
        EntityManager em = emf.createEntityManager();
        Trainer tr = em.find(Trainer.class, t.getName());
        Pokemon poke = em.find(Pokemon.class, p.getName());
        boolean ok = false;
        if (tr != null && p != null && tr.getPotions()>0) {
            tr.setPotions(t.getPotions()-1); 
            poke.setLife(poke.getLife()+50);
            em.persist(tr);
            em.persist(poke);
            em.close();
            ok = true;
        }
         return ok;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
