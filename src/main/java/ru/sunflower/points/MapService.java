/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sunflower.points;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author den
 */
@Path("/points")
public class MapService {
  @GET
  @Produces({MediaType.APPLICATION_JSON})
  public List<Point> findAll(){
    List<Point> res = new ArrayList();
    res.add(new Point("point1", "Дубнинская улица, 30к1", 55.880734, 37.558662));
    res.add(new Point("point2", "Дмитровское шоссе, 100с3", 55.881518, 37.547998));
    return res;
  }
}
