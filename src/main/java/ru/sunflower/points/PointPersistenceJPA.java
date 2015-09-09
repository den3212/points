/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sunflower.points;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author den
 */
public class PointPersistenceJPA implements PointsPersistence{
  public static String unitName = "points";
  private EntityManagerFactory emf;
     
  // PointsPersistence
  
  public List<Point> findAll() {
    EntityManager em = getEMF().createEntityManager();
    try {
    TypedQuery<Point> nq = em.createNamedQuery("findAll", Point.class);
    return nq.getResultList();
    } finally {
      em.close();
    }
  }

  public boolean save(Point point) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
  // private
  
  private EntityManagerFactory getEMF(){
    if (emf == null) {
      emf = Persistence.createEntityManagerFactory(unitName);
    }
    return emf;
  }

  
}
