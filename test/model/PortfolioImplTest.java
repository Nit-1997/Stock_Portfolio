package model;

import org.junit.Before;
import org.junit.Test;
import utils.Utils;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import constants.Constants;

import static org.junit.Assert.*;

/**
 * Junit class to test PortfolioImpl.
 */
public class PortfolioImplTest {

  @Test
  public void testPortfolioCreateFirstTime() throws Exception {
    String portfolioDirectory = Paths.get("portfolios").toAbsolutePath().toString();
    File f = new File(portfolioDirectory);
    if (!f.exists()) {
      f.mkdirs();
    }
    Utils.clearStockDirectory();
    Map<String, Double> order = new HashMap<>();
    order.put("AAPL", 10.0);
    order.put("NVDA", 12.0);
    Portfolio p = new PortfolioImpl(order, "tech");
    for (StockOrder o : p.getPortfolioSummary()) {
      String ticker = o.getStock().getStockTickerName();
      assertNotNull(ticker);
      if (Objects.equals(ticker, "AAPL")) {
        assertEquals(10, o.getQuantity(), 0);
      }
      if (Objects.equals(ticker, "NVDA")) {
        assertEquals(12, o.getQuantity(), 0);
      }
    }
  }


  @Test
  public void testPortfolioCreateForRetrieval() throws Exception {
    Constants.stockNames = Utils
            .loadStockNames("stocks", "stocks_list.csv");
    Portfolio p = new PortfolioImpl("tech");
    for (StockOrder o : p.getPortfolioSummary()) {
      String ticker = o.getStock().getStockTickerName();
      assertNotNull(ticker);
      if (Objects.equals(ticker, "AAPL")) {
        assertEquals(10, o.getQuantity(), 0);
      }
      if (Objects.equals(ticker, "NVDA")) {
        assertEquals(12, o.getQuantity(), 0);
      }
    }
  }

  @Test
  public void testGetPortfolioDetailed() throws Exception {
    Constants.stockNames = Utils
            .loadStockNames("stocks", "stocks_list.csv");
    Portfolio p = new PortfolioImpl("tech");
    List<PortfolioDetailedPojo> pojos = p.getCurrentPortfolioDetailed();
    for (PortfolioDetailedPojo pojo : pojos) {
      if (Objects.equals(pojo.getTicker(), "NVDA")) {
        assertEquals(12.0, pojo.getQty(), 0);
      }
      if (Objects.equals(pojo.getTicker(), "AAPL")) {
        assertEquals(10.0, pojo.getQty(), 0);
      }
    }
  }


  @Test
  public void testGetPortfolioDetailedByDate() throws Exception {
    Constants.stockNames = Utils
            .loadStockNames("stocks", "stocks_list.csv");

    Portfolio p = new PortfolioImpl("tech");
    String date = "2022-10-28";
    List<PortfolioDetailedPojo> pojos = p.getPortfolioDetailedOnDate(date);
    assertNotNull(pojos);
    for (PortfolioDetailedPojo pojo : pojos) {
      if (Objects.equals(pojo.getTicker(), "NVDA")) {
        assertEquals(12.0, pojo.getQty(), 0);
      }
      if (Objects.equals(pojo.getTicker(), "AAPL")) {
        assertEquals(10.0, pojo.getQty(), 0);
      }
    }
  }

  @Test(expected = Exception.class)
  public void testGetPortfolioDetailedByDateDateNull() throws Exception {
    Constants.stockNames = Utils
            .loadStockNames("stocks", "stocks_list.csv");

    Portfolio p = new PortfolioImpl("tech");
    List<PortfolioDetailedPojo> pojos = p.getPortfolioDetailedOnDate(null);
  }

}