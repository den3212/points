/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sunflower.points;

import java.net.URI;
import java.util.Collection;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Тест REST сервисов
 * @author deniskovalev
 */
public class RestTest extends JerseyTest {

  public RestTest() {
  }


  @Override
  protected Application configure() {
    return new ResourceConfig(PointsResource.class, GeocoderResource.class);
  }

  @Override
  protected URI getBaseUri() {
    return UriBuilder.fromUri(super.getBaseUri()).path("map").build();
  }

  @Test
  public void points() {
    // проверка геокодирования
    GenericType<Collection<Point>> genericType = new GenericType<Collection<Point>>(){};
    Collection<Point> points1 = target("/geocode").queryParam("address", "Москва Полянка 5") .request().get(genericType);
    assertTrue(points1.size() == 1);
    Point point1 = points1.iterator().next();
    assertTrue(point1.getAddress().equals("Россия, Москва, улица Малая Полянка, 5"));
    
    // создание новой точки
    point1.setName("p1");
    Entity<Point> entityPoint = Entity.json(point1);
    Response resp = target("/points").request().post(entityPoint);
    assertTrue(resp.getStatus() == 204);
    
    // вычитка точек
    Collection<Point> points2 = target("/points").request().get(genericType);
    assertTrue(points2.size() == 1);
    Point point2 = points2.iterator().next();
    assertTrue(point1.getName().equals(point2.getName()));
    assertTrue(point1.getAddress().equals(point2.getAddress()));
    assertTrue(point1.getLatitude().equals(point2.getLatitude()));
    assertTrue(point1.getLongitude().equals(point2.getLongitude()));
  }
}
