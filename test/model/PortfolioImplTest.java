package model;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
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

  @Test
  public void testGetPortfolioDetailed(){
    try {
      Portfolio p = new PortfolioImpl("tech");
      List<PortfolioDetailedPojo> pojos = p.getCurrentPortfolioDetailed();
      for( PortfolioDetailedPojo pojo : pojos){
        System.out.println(pojo.getBuyPrice());
        System.out.println(pojo.getCurrentPrice());
        System.out.println(pojo.getPnl());
        System.out.println(pojo.getQty());
        System.out.println(pojo.getTicker());
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }


  @Test
  public void testGetPortfolioDetailedByDate(){
    try {
      Portfolio p = new PortfolioImpl("tech");
      String date = "2022-10-28";
      List<PortfolioDetailedPojo> pojos = p.getPortfolioDetailedOnDate(date);
      for( PortfolioDetailedPojo pojo : pojos){
        System.out.println(pojo.getBuyPrice());
        System.out.println(pojo.getCurrentPrice());
        System.out.println(pojo.getPnl());
        System.out.println(pojo.getQty());
        System.out.println(pojo.getTicker());
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

}