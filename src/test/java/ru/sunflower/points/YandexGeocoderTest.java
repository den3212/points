package ru.sunflower.points;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author deniskovalev
 */
public class YandexGeocoderTest {
  
   @Test
   public void direct() {
     List<Point> res = YandexGeocoder.direct("Москва, Красная площадь, 1");
     assertFalse(res.isEmpty());
   }
}
