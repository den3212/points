/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sunflower.points;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Класс для работы с Yandex Geocoder
 * 
 * @author deniskovalev
 */
public class YandexGeocoder {

  /**
   * Выполнить прямое геокодирование
   * @param address адрес или часть адреса
   * @return найденные адреса
   */
  public static List<Point> direct(String address) {
    try {
      URL url = new URL("https://geocode-maps.yandex.ru/1.x/?geocode=" + URLEncoder.encode(address, "UTF-8") + "&format=xml");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      
      try (InputStream is = con.getInputStream()) {
        return YandexGeocoder.parse(is);
      }
    } catch (IOException ex) {
      Logger.getLogger(YandexGeocoder.class.getName()).log(Level.SEVERE, null, ex);
    }
    return Collections.emptyList();
  }

  private static List<Point> parse(InputStream xml) {
    List<Point> res = new ArrayList();
    try {
      DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = domFactory.newDocumentBuilder();
      Document doc = builder.parse(xml);

      XPathFactory factory = XPathFactory.newInstance();
      XPath xpath = factory.newXPath();
      XPathExpression geoObjectExpr = xpath.compile("//GeoObject");
      XPathExpression textExpr = xpath.compile("metaDataProperty/GeocoderMetaData/text/text()");
      XPathExpression posExpr = xpath.compile("Point/pos/text()");

      NodeList nodes = (NodeList) geoObjectExpr.evaluate(doc, XPathConstants.NODESET);
      for (int i = 0; i < nodes.getLength(); i++) {
        Node geoObject = nodes.item(i);
        String text = (String) textExpr.evaluate(geoObject, XPathConstants.STRING);
        String pos = (String) posExpr.evaluate(geoObject, XPathConstants.STRING);

        String[] a = pos.split(" ");
        res.add(new Point("", text, Double.parseDouble(a[1]), Double.parseDouble(a[0])));
        
        Logger.getLogger(YandexGeocoder.class.getName()).log(Level.SEVERE, pos);
      }

    } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
      Logger.getLogger(YandexGeocoder.class.getName()).log(Level.SEVERE, null, ex);
    }

    return res;
  }
}
