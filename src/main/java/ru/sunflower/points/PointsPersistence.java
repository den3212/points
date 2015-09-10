/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sunflower.points;

import java.util.List;

/**
 *
 * @author den
 */
public interface PointsPersistence {
  List<Point> findAll();
  Point find(String name);
  void save(Point point);
}
