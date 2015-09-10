/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sunflower.points;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author den
 */
public class PointsPersistenceJPA implements PointsPersistence {

  public static String unitName = "points";
  private EntityManagerFactory emf;

  // PointsPersistence
  
  @Override
  public List<Point> findAll() {
    EntityManager em = getEMF().createEntityManager();
    try {
      TypedQuery<Point> nq = em.createNamedQuery("findAll", Point.class);
      return nq.getResultList();
    } finally {
      em.close();
    }
  }
  
  @Override
  public Point find(String name) {
    EntityManager em = getEMF().createEntityManager();
    try {
      return em.find(Point.class, name);
    } finally {
      em.close();
    }
  }

  @Override
  public void save(Point point) {
    EntityManager em = getEMF().createEntityManager();
    try {
      EntityTransaction tx = em.getTransaction();
      tx.begin();
      em.persist(point);
      tx.commit();
    } finally {
      em.close();
    }
  }

  // private
  private EntityManagerFactory getEMF() {
    if (emf == null) {
      emf = Persistence.createEntityManagerFactory(unitName);
    }
    return emf;
  }

  

}
