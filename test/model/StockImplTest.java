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
    assertNotEquals(-1.0,price,0);
  }

  //TODO handle incorrect ticker for creating stocks


}