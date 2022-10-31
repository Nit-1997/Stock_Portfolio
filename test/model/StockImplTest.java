package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class StockImplTest {


  @Test
  public void testGetCurrentPriceForIncorrectTicker(){
    Stock s = new StockImpl("xyz");
    double price = s.getCurrentPrice();
    assertEquals(-1.0,price,0);
  }

  @Test
  public void testGetCurrentPriceForCorrectTicker(){
    Stock s = new StockImpl("Googl");
    double price = s.getCurrentPrice();
    assertNotEquals(-1.0,price,0);
  }

  @Test
  public void testGetBuyPrice(){
    Stock s = new StockImpl("Googl");
    double price = s.getBuyPrice();
    System.out.println(price);
    assertNotEquals(-1.0,price,0);
  }

  //TODO handle incorrect ticker for creating stocks

  @Test
  public void testgetPriceOnDateForCorrectTicker(){
    Stock s = new StockImpl("Googl");
    double price = s.getPriceOnDate("2022-10-27");
    System.out.println(price);
    assertEquals(92.2200 , price , 0);
  }

}