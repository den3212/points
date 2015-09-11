/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sunflower.points;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Ресурс - points
 * @author deniskovalev
 */
@Path("/points")
public class PointsResource {

  private final PointsPersistence pp = new PointsPersistenceJPA();

  /**
   * Получить все точки
   * @return 
   */
  @GET
  @Produces({MediaType.APPLICATION_JSON})
  public List<Point> findAll() {
    return pp.findAll();
  }

  /**
   * Получить точку по имени
   * @param name имя точки 
   * @return возвращает страницу точки
   * @throws PointsException 
   */
  @GET
  @Path("{name}")
  @Produces({MediaType.TEXT_HTML})
  public String find(@PathParam("name") String name) throws PointsException {
    Point point = pp.find(name);
    if (point == null) {
      throw new PointsException("Точка не найдена.");
    }
    return renderPointPage(point);
  }

  /**
   * Сохранить точку в базе данных
   * @param point
   * @throws PointsException 
   */
  @POST
  @Consumes({MediaType.APPLICATION_JSON})
  public void create(Point point) throws PointsException {
    if (point.getName() == null || point.getName().trim().isEmpty()) {
      throw new PointsException("Не задано имя точки.");
    }

    if (point.getAddress() == null || point.getAddress().trim().isEmpty()) {
      throw new PointsException("Не задан адрес.");
    }

    if (point.getLatitude() == null) {
      throw new PointsException("Не задана широта.");
    }

    if (point.getLongitude() == null) {
      throw new PointsException("Не задана долгота.");
    }

    if (pp.find(point.getName()) != null) {
      throw new PointsException("Точка с таким именем уже есть.");
    }

    try {
      pp.save(point);
    } catch (Exception ex) {
      Logger.getLogger(YandexGeocoder.class.getName()).log(Level.SEVERE, null, ex);
      throw new PointsException("При сохранении точки возникла ошибка.");
    }
  }

  // private
  private Configuration cfg = null;
  
  private Configuration getCfg() {
    if (cfg == null) {
      cfg = new Configuration(Configuration.VERSION_2_3_22);
      cfg.setClassForTemplateLoading(this.getClass(), "");
    }
    return cfg;
  }
  
  private String renderPointPage(Point point) {
    
    try {
      Template temp = getCfg().getTemplate("Point.html");
      Map params = new HashMap();
      params.put("name", point.getName());
      params.put("address", point.getAddress());
      params.put("latitude", point.getLatitude().toString().replace(",", "."));
      params.put("longitude", point.getLongitude().toString().replace(",", "."));
      
      StringWriter w = new StringWriter();
      temp.process(params, w);
      
      return w.toString();
    } catch (MalformedTemplateNameException ex) {
      Logger.getLogger(PointsResource.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ParseException ex) {
      Logger.getLogger(PointsResource.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException | TemplateException ex) {
      Logger.getLogger(PointsResource.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "";
  }

}
