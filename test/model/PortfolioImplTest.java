package model;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PortfolioImplTest {

  @Test
  public void testPortfolioCreate() {
    try {
      Map<String, Double> order = new HashMap<>();
      order.put("Googl", 10.0);
      order.put("ibm", 12.0);
      Portfolio p = new PortfolioImpl(order, "tech");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }


  @Test
  public void testPortfolioCreateForRetrieval() {
    try {
      Portfolio p = new PortfolioImpl("tech");
      System.out.println(p.getCurrentValue());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

}