package model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Test the portfolio interface.
 */
public class InflexiblePortfolioTest {
  public Portfolio port;

  /**
   * Initialize the portfolio with some stocks.
   */
  @Before
  public void init() {
    Map<String, Integer> stocksToAdd = new HashMap<>();
    stocksToAdd.put("GOOGL", 2);
    stocksToAdd.put("AAPL", 4);
    port = new InflexiblePortfolio("Test Portfolio", stocksToAdd);
  }

  /**
   * Test that an error is thrown if we feed a bad ticker to the inflexible portfolio.
   */
  @Test
  public void testBadConstructor() {
    Map<String, Integer> stocksToAdd = new HashMap<>();
    try {
      stocksToAdd.put("BLAHBLAHBLAH", 69);
      port = new InflexiblePortfolio("Test1", stocksToAdd);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Ticker", e.getMessage());
    }
  }

  /**
   * Test getting the name.
   */
  @Test
  public void getPortName() {
    assertEquals("Test Portfolio", port.getPortName());
  }

  /**
   * Test getting the values of the added stocks.
   */
  @Test
  public void getValues() {
    Map<String, Double> expected = new HashMap<>();
    expected.put("GOOGL", 99.63);
    expected.put("AAPL", 143.86);
    Map<String, Double> actual = port.getValues(LocalDate.parse("2022-10-19"));

    assertTrue(actual.equals(expected));

    //Try weekend
    try {
      port.getValues(LocalDate.parse("2022-10-30"));
    } catch (IllegalArgumentException e) {
      assertEquals("Date not found", e.getMessage());
    }
    //Try future date
    try {
      port.getValues(LocalDate.parse("2069-10-14"));
    } catch (IllegalArgumentException e) {
      assertEquals("Date not found", e.getMessage());
    }
    //Try holiday
    try {
      port.getValues(LocalDate.parse("2021-12-25"));
    } catch (IllegalArgumentException e) {
      assertEquals("Date not found", e.getMessage());
    }
  }

  /**
   * Test getting the map of tickers to number of stocks owned.
   */
  @Test
  public void getTickerMap() {
    Map<String, Double> tickerMap = new HashMap<>();
    tickerMap.put("GOOGL", 2.0);
    tickerMap.put("AAPL", 4.0);
    assertTrue(tickerMap.equals(port.getTickerMap(LocalDate.parse("2022-10-19"))));
  }

  /**
   * Test getting the number of shares for each stock owned.
   */
  @Test
  public void getShares() {
    assertEquals(port.getShares("GOOGL", LocalDate.parse("2022-10-19")), (Double) 2.0);
    assertEquals(port.getShares("AAPL", LocalDate.parse("2022-10-19")), (Double) 4.0);
  }

  /**
   * Test getting the number of unique stocks owned.
   */
  @Test
  public void getNumStocks() {
    assertEquals(port.getNumStocks(), 2);
  }

  /**
   * Test getting the total valueof the portfolio.
   */
  @Test
  public void getPortValue() {
    assertEquals((Double) 774.7, (Double) port.getPortValue(LocalDate.parse("2022-10-19")));
    //Try weekend
    try {
      port.getPortValue(LocalDate.parse("2022-10-30"));
    } catch (IllegalArgumentException e) {
      assertEquals("Date not found", e.getMessage());
    }
    //Try future date
    try {
      port.getPortValue(LocalDate.parse("2069-10-14"));
    } catch (IllegalArgumentException e) {
      assertEquals("Date not found", e.getMessage());
    }
    //Try holiday
    try {
      port.getPortValue(LocalDate.parse("2021-12-25"));
    } catch (IllegalArgumentException e) {
      assertEquals("Date not found", e.getMessage());
    }
  }

  /**
   * Test the to string method.
   */
  @Test
  public void toStringTest() {
    String expected = "{\n" +
            "    \"GOOGL\": 2.0,\n" +
            "    \"AAPL\": 4.0\n" +
            "}";
    assertEquals(expected, port.toString());
  }

  /**
   * Test the isFlexible method.
   */
  @Test
  public void testIsFlexible() {
    assertEquals(false, port.isFlexible());
  }
}