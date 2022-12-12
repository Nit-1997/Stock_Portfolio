package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import utils.Utils;


/**
 * Junit class to test StockImpl.
 */
public class StockImplTest {

  @Test(expected = Exception.class)
  public void testGetCurrentPriceForIncorrectTicker() throws Exception {
    String ticker = "xyz";
    Utils.clearStockDirectory();
    Utils.loadStockData(ticker,"stock_data");
    Stock s = new StockImpl(ticker);
    double price = s.getCurrentPrice();
  }

  @Test
  public void testGetCurrentPriceForCorrectTicker() throws Exception {
    String ticker = "CSCO";
    Utils.clearStockDirectory();
    Utils.loadStockData(ticker,"stock_data");
    Stock s = new StockImpl(ticker);
    Double price = s.getCurrentPrice();
    assertNotNull(price);
  }

  @Test
  public void testGetBuyPrice() throws Exception {
    String ticker = "CSCO";
    Utils.clearStockDirectory();
    Utils.loadStockData(ticker,"stock_data");
    Stock s = new StockImpl(ticker);
    Double price = s.getBuyPrice();
    assertNotNull(price);
  }

  @Test
  public void testGetPriceOnDateForCorrectTicker() throws Exception {
    String ticker = "CSCO";
    Utils.clearStockDirectory();
    Utils.loadStockData(ticker,"stock_data");
    String date = "2022-10-27";
    Stock s = new StockImpl(ticker);
    Double price = s.getPriceOnDate(date);
    assertNotNull(price);
    assertEquals(44.419998 , price , 0.1);
  }

  @Test(expected = Exception.class)
  public void testGetPriceOnDateForNullDate() throws Exception {
    String ticker = "CSCO";
    Utils.clearStockDirectory();
    Utils.loadStockData(ticker,"stock_data");
    Stock s = new StockImpl(ticker);
    Double price = s.getPriceOnDate(null);
  }

}