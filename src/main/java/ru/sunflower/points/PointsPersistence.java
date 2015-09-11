/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sunflower.points;

import java.util.List;

/**
 * Интерфейс доступа к данным 
 * 
 * @author deniskovalev
 */
public interface PointsPersistence {
  
  /**
   * Найти все точки
   * @return 
   */
  List<Point> findAll();
  
  /**
   * Найти точку по имени
   * @param name
   * @return 
   */
  Point find(String name);
  
  /**
   * Сохранить точку
   * @param point 
   */
  void save(Point point);
}
