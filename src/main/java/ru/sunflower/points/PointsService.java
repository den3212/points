/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sunflower.points;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author den
 */
@Path("/points")
public class PointsService {

  private final PointsPersistence pp = new PointsPersistenceJPA();

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  public List<Point> findAll() {
    return pp.findAll();
  }

  @POST
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  public List<Point> create(Point point) throws PointsException {

    if (point.getName() == null || point.getName().trim().isEmpty()) {
      throw new PointsException("Имя точки не может быть пустым.");
    }

    List<Point> points = YandexGeocoder.direct(point.getAddress());

    if (points.isEmpty()) {
      throw new PointsException("Адрес не найден.");
    }

    for (Point p : points) {
      p.setName(point.getName());
    }

    if (points.size() == 1) {

      if (pp.find(point.getName()) != null) {
        throw new PointsException("Точка с таким именем уже есть.");
      }

      try {
        pp.save(points.get(0));
      } catch (Exception ex) {
        Logger.getLogger(YandexGeocoder.class.getName()).log(Level.SEVERE, null, ex);
        throw new PointsException("При сохранении точки возникла ошибка.");
      }
    }

    return points;
  }

}
