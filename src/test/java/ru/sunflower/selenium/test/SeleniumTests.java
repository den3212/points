package ru.sunflower.selenium.test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.safari.SafariDriver;

/**
 *
 * @author deniskovalev
 */
public class SeleniumTests {
  
  private static WebDriver driver;
  
  public SeleniumTests() {
  }
  
  @BeforeClass
  public static void setUpClass() {
    //driver = new FirefoxDriver();
    //driver = new PhantomJSDriver();
    driver = new ChromeDriver();
    //driver = new SafariDriver();
  }
  
  @AfterClass
  public static void tearDownClass() {
    if (driver != null) {
      driver.close();
    }
  }
  
  @Before
  public void setUp() {
    
  }
  
  @After
  public void tearDown() {
    
  }

  @Test
  public void test1() throws InterruptedException {
   
    driver.get("http://www.ya.ru");
    
    WebElement text = driver.findElement( By.id("text") );
    WebElement button = driver.findElement( By.xpath("//button[@type='submit']") );
    
    ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    
    text.sendKeys("selenium");
    button.click();
    Thread.sleep(5000);
    
    ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    
    driver.navigate().back();
    
    ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    
    //assertNotNull(f);
  }
  
  @Test
  public void test2() {
   
    driver.get("http://www.ya.ru");
    
    WebElement text = driver.findElement( By.id("text") );
    WebElement button = driver.findElement( By.xpath("//button[@type='submit']") );
    
    text.sendKeys("selenium");
    button.click();
    
    driver.navigate().back();
  }
  
}
