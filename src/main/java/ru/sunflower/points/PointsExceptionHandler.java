/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sunflower.points;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Обработчик исключений для Jersey 
 * @author deniskovalev
 */
@Provider
public class PointsExceptionHandler implements ExceptionMapper<PointsException>{

  @Override
  public Response toResponse(PointsException e) {
    return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(e.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
  }
  
}
