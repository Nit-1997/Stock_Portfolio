package model;

import java.io.IOException;
import org.junit.Test;

import static org.junit.Assert.*;

public class StockImplTest {


  @Test
  public void testGetCurrentPriceForIncorrectTicker() throws IOException {
    Stock s = new StockImpl("xyz");
    double price = s.getCurrentPrice();
    assertEquals(-1.0,price,0);
  }

  @Test
  public void testGetCurrentPriceForCorrectTicker() throws IOException {
    Stock s = new StockImpl("CSCO");
    double price = s.getCurrentPrice();
    assertNotEquals(-1.0,price,0);
  }

  @Test
  public void testGetBuyPrice() throws IOException {
    Stock s = new StockImpl("CSCO");
    double price = s.getBuyPrice();
    System.out.println(price);
    assertNotEquals(-1.0,price,0);
  }

  //TODO handle incorrect ticker for creating stocks

  @Test
  public void testgetPriceOnDateForCorrectTicker() throws IOException {
    Stock s = new StockImpl("CSCO");
    double price = s.getPriceOnDate("2022-10-27");
    System.out.println(price);
    assertEquals(44.419998 , price , 0);
  }

}