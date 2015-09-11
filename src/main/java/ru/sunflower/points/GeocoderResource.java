/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sunflower.points;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Ресурс - geocode
 * @author deniskovalev
 */
@Path("/geocode")
public class GeocoderResource {
  
  /**
   * Выполнить прямое геокодирование
   * @param address запрос
   * @return список подходящих адресов
   * @throws PointsException 
   */
  @GET
  @Produces({MediaType.APPLICATION_JSON})
  public List<Point> direct(@QueryParam("address") String address) throws PointsException {
    List<Point> points = YandexGeocoder.direct(address);
    if (points.isEmpty()) {
      throw new PointsException("Адрес не найден.");
    }
    return points;
  }
}
