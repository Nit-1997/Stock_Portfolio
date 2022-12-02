package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import constants.Constants;
import java.io.File;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import org.junit.Test;
import utils.Utils;


/**
 * Junit class to test PortfolioImpl.
 */
public class PortfolioImplTest {

  public static final String[] TICKER_LIST = {
      "CSCO", "NFLX", "AAPL", "NVDA", "TSLA", "AMZN"
  };

  public static final double[] TICKER_LIST_QTY = {
      10.0, 11.0, 12.0, 13.0, 14.0, 15.0
  };

  /**
   * Creates dummy portfolio value on date.
   * @param date asked date.
   * @return value of portfolio
   * @throws Exception if date in invalid.
   */
  public static double computeDummyPortfolioValDate(String date) throws Exception {
    double val = 0;
    for (int i = 0; i < TICKER_LIST.length; i++) {
      val += Double.parseDouble(getCurrentValueOfTickerDate(TICKER_LIST[i],
          "stock_data", date))
          * TICKER_LIST_QTY[i];
    }
    String x = String.format("%.2f", val);
    return Double.parseDouble(x);
  }

  /**
   * Computer dummy portfolio value.
   * @return portfolio value.
   * @throws Exception if value is not there.
   */
  public static double computeDummyPortfolioVal() throws Exception {
    double val = 0;
    for (int i = 0; i < TICKER_LIST.length; i++) {
      val += Double.parseDouble(getCurrentValueOfTicker(TICKER_LIST[i], "stock_data"))
          * TICKER_LIST_QTY[i];
    }
    return val;
  }

  /**
   * Fetched current value of ticker.
   * @param ticker stock
   * @param stockDir stock directory
   * @param date date for which value required.
   * @return value of ticker.
   * @throws Exception is arguments are null.
   */
  public static String getCurrentValueOfTickerDate(String ticker, String stockDir, String date)
      throws Exception {
    DataSource ds = new DataSourceImpl();
    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    File stockFile = ds.getFileByName(ticker, stockDir);

    Scanner myReader = new Scanner(stockFile);
    int lineNo = 0;
    String res = null;
    while (myReader.hasNextLine()) {
      String input = myReader.nextLine();
      if (lineNo != 0) {
        String[] out = input.split(",");
        try {
          if (sdf.parse(date).compareTo(sdf.parse(out[0])) >= 0) {
            res = out[4];
            break;
          }
        } catch (ParseException e) {
          System.out.println("wrong date");
        }
      }
      lineNo++;
    }
    myReader.close();
    double x = Double.parseDouble(res);
    res = String.format("%.2f", x);
    return res;
  }

  /**
   * Fetches current value of ticker.
   * @param ticker stock
   * @param stockDir stock directory
   * @return current value
   * @throws Exception if arguments are invalid.
   */
  public static String getCurrentValueOfTicker(String ticker, String stockDir) throws Exception {
    Utils.loadStockData(ticker, stockDir);
    DataSource ds = new DataSourceImpl();
    File stockFile = ds.getFileByName(ticker, stockDir);
    Scanner myReader = new Scanner(stockFile);
    int lineNo = 0;
    String out = null;
    while (myReader.hasNextLine()) {
      String input = myReader.nextLine();
      if (lineNo == 1) {
        out = input.split(",")[4];
        break;
      }
      lineNo++;
    }
    myReader.close();
    return out;
  }

  @Test
  public void testPortfolioCreateFirstTime() throws Exception {
    String portfolioDirectory = Paths.get("portfolios").toAbsolutePath().toString();
    File f = new File(portfolioDirectory);
    if (!f.exists()) {
      f.mkdirs();
    }
    Utils.clearStockDirectory();
    Map<String, Double> order = new HashMap<>();
    Double qt = 10.0;
    for (String s : TICKER_LIST) {
      order.put(s, qt);
      qt++;
    }

    PortfolioInflex p = new PortfolioInflexImpl(order, "tech");
    for (StockOrder o : p.getPortfolioSummary()) {
      String ticker = o.getStock().getStockTickerName();
      assertNotNull(ticker);

      switch (ticker) {
        case "CSCO": {
          assertEquals(Double.parseDouble(getCurrentValueOfTicker(ticker, "stock_data")),
              o.getStock().getCurrentPrice(), 0);
          assertEquals(TICKER_LIST_QTY[0], o.getQuantity(), 0);
          break;
        }
        case "NFLX": {
          assertEquals(Double.parseDouble(getCurrentValueOfTicker(ticker, "stock_data")),
              o.getStock().getCurrentPrice(), 0);
          assertEquals(TICKER_LIST_QTY[1], o.getQuantity(), 0);
          break;
        }
        case "AAPL": {
          assertEquals(Double.parseDouble(getCurrentValueOfTicker(ticker, "stock_data")),
              o.getStock().getCurrentPrice(), 0);
          assertEquals(TICKER_LIST_QTY[2], o.getQuantity(), 0);
          break;
        }
        case "NVDA": {
          assertEquals(Double.parseDouble(getCurrentValueOfTicker(ticker, "stock_data")),
              o.getStock().getCurrentPrice(), 0);
          assertEquals(TICKER_LIST_QTY[3], o.getQuantity(), 0);
          break;
        }
        case "TSLA": {
          assertEquals(Double.parseDouble(getCurrentValueOfTicker(ticker, "stock_data")),
              o.getStock().getCurrentPrice(), 0);
          assertEquals(TICKER_LIST_QTY[4], o.getQuantity(), 0);
          break;
        }
        case "AMZN": {
          assertEquals(Double.parseDouble(getCurrentValueOfTicker(ticker, "stock_data")),
              o.getStock().getCurrentPrice(), 0);
          assertEquals(TICKER_LIST_QTY[5], o.getQuantity(), 0);
          break;
        }
        default:
          return;
      }
    }
  }


  @Test
  public void testPortfolioCreateForRetrieval() throws Exception {
    Constants.STOCK_NAMES = Utils
        .loadStockNames("stocks", "stocks_list.csv");
    PortfolioInflex p = new PortfolioInflexImpl("tech");

    for (StockOrder o : p.getPortfolioSummary()) {
      String ticker = o.getStock().getStockTickerName();
      assertNotNull(ticker);

      switch (ticker) {
        case "CSCO": {
          assertEquals(Double.parseDouble(getCurrentValueOfTicker(ticker, "stock_data")),
              o.getStock().getCurrentPrice(), 0);
          assertEquals(TICKER_LIST_QTY[0], o.getQuantity(), 0);
          break;
        }
        case "NFLX": {
          assertEquals(Double.parseDouble(getCurrentValueOfTicker(ticker, "stock_data")),
              o.getStock().getCurrentPrice(), 0);
          assertEquals(TICKER_LIST_QTY[1], o.getQuantity(), 0);
          break;
        }
        case "AAPL": {
          assertEquals(Double.parseDouble(getCurrentValueOfTicker(ticker, "stock_data")),
              o.getStock().getCurrentPrice(), 0);
          assertEquals(TICKER_LIST_QTY[2], o.getQuantity(), 0);
          break;
        }
        case "NVDA": {
          assertEquals(Double.parseDouble(getCurrentValueOfTicker(ticker, "stock_data")),
              o.getStock().getCurrentPrice(), 0);
          assertEquals(TICKER_LIST_QTY[3], o.getQuantity(), 0);
          break;
        }
        case "TSLA": {
          assertEquals(Double.parseDouble(getCurrentValueOfTicker(ticker, "stock_data")),
              o.getStock().getCurrentPrice(), 0);
          assertEquals(TICKER_LIST_QTY[4], o.getQuantity(), 0);
          break;
        }
        case "AMZN": {
          assertEquals(Double.parseDouble(getCurrentValueOfTicker(ticker, "stock_data")),
              o.getStock().getCurrentPrice(), 0);
          assertEquals(TICKER_LIST_QTY[5], o.getQuantity(), 0);
          break;
        }
        default:
          return;
      }
    }
  }

  @Test
  public void testGetPortfolioByDate() throws Exception {
    Constants.STOCK_NAMES = Utils
        .loadStockNames("stocks", "stocks_list.csv");
    Portfolio p = new PortfolioInflexImpl("tech");
    String date = "2022-10-28";
    double v = computeDummyPortfolioValDate(date);
    assertEquals(v, p.getValueOnDate(date), 0);

  }

  @Test
  public void testGetPortfolioValue() throws Exception {
    Constants.STOCK_NAMES = Utils
        .loadStockNames("stocks", "stocks_list.csv");
    Portfolio p = new PortfolioInflexImpl("tech");
    assertEquals(computeDummyPortfolioVal(), p.getCurrentValue(), 0);
  }

  @Test
  public void testGetPortfolioDetailed() throws Exception {
    Constants.STOCK_NAMES = Utils
        .loadStockNames("stocks", "stocks_list.csv");
    PortfolioInflex p = new PortfolioInflexImpl("tech");
    List<PortfolioDetailedPojo> pojos = p.getCurrentPortfolioDetailed();
    for (PortfolioDetailedPojo pojo : pojos) {
      if (Objects.equals(pojo.getTicker(), "NVDA")) {
        assertEquals(13.0, pojo.getQty(), 0);
      }
      if (Objects.equals(pojo.getTicker(), "AAPL")) {
        assertEquals(12.0, pojo.getQty(), 0);
      }
    }
  }


  @Test
  public void testGetPortfolioDetailedByDate() throws Exception {
    Constants.STOCK_NAMES = Utils
        .loadStockNames("stocks", "stocks_list.csv");

    PortfolioInflex p = new PortfolioInflexImpl("tech");
    String date = "2022-10-28";
    List<PortfolioDetailedPojo> pojos = p.getPortfolioDetailedOnDate(date);
    assertNotNull(pojos);
    for (PortfolioDetailedPojo pojo : pojos) {
      if (Objects.equals(pojo.getTicker(), "NVDA")) {
        assertEquals(13.0, pojo.getQty(), 0);
      }
      if (Objects.equals(pojo.getTicker(), "AAPL")) {
        assertEquals(12.0, pojo.getQty(), 0);
      }
    }
  }

  @Test(expected = Exception.class)
  public void testGetPortfolioDetailedByDateDateNull() throws Exception {
    Constants.STOCK_NAMES = Utils
        .loadStockNames("stocks", "stocks_list.csv");

    PortfolioInflex p = new PortfolioInflexImpl("tech");
    List<PortfolioDetailedPojo> pojos = p.getPortfolioDetailedOnDate(null);
  }

}