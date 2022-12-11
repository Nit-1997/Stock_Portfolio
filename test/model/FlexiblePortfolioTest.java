package model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the flexible portfolio class.
 */
public class FlexiblePortfolioTest {
  FlexiblePortfolio port;

  /**
   * Setup portfolio.
   *
   * @throws Exception If fail to make portfolio.
   */
  @Before
  public void setUp() throws Exception {
    this.port = new FlexiblePortfolio("Portfolio");
    port.addStock("GOOGL", 2, LocalDate.parse("2022-07-14"), 0.93);
    port.addStock("AAPL", 4, LocalDate.parse("2022-07-14"), 1.93);
  }

  /**
   * Test selling some stock.
   */
  @Test
  public void sellStock() {
    port.sellStock("AAPL", 2, LocalDate.parse("2022-10-14"), 0.12);
    assertEquals((Double) 4.0, port.getShares("AAPL", LocalDate.parse("2022-10-12")));
    assertEquals((Double) 2.0, port.getShares("AAPL", LocalDate.parse("2022-10-17")));
    assertEquals((Double) 2.0, port.getShares("AAPL", LocalDate.parse("2022-10-14")));
    //Try to sell before we bought
    try {
      port.sellStock("AAPL", 2, LocalDate.parse("2022-07-13"), 0.69);
    } catch (IllegalArgumentException e) {
      assertEquals("Not enough stocks owned at 2022-07-13! Buy more before then, or sell less.",
              e.getMessage());
    }
    //Try to sell ticker we dont have
    try {
      port.sellStock("FXAIX", 2, LocalDate.parse("2022-07-13"), 0.69);
    } catch (IllegalArgumentException e) {
      assertEquals("Not enough stocks owned at 2022-07-13! Buy more before then, or sell less.",
              e.getMessage());
    }
    //Try to sell more than we have
    try {
      port.sellStock("AAPL", 6, LocalDate.parse("2022-10-14"), 0.12);
    } catch (IllegalArgumentException e) {
      assertEquals("Not enough stocks owned at 2022-10-14! Buy more before then, or sell less.",
              e.getMessage());
    }
    //Try to sell on weekend
    try {
      port.sellStock("AAPL", 2, LocalDate.parse("2022-10-30"), 0.12);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date! 2022-10-30 must not be a weekend, or in the future.",
              e.getMessage());
    }
    //Try to sell in future
    try {
      port.sellStock("AAPL", 2, LocalDate.parse("2069-10-14"), 0.12);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date! 2069-10-14 must not be a weekend, or in the future.",
              e.getMessage());
    }
  }

  /**
   * Test buying stocks.
   */
  @Test
  public void addStock() {
    port.addStock("AAPL", 4, LocalDate.parse("2022-07-14"), 0.22);
    assertEquals((Double) 8.0, port.getShares("AAPL", LocalDate.parse("2022-07-14")));
    assertEquals((Double) 0.0, port.getShares("AAPL", LocalDate.parse("2022-07-13")));
    port.addStock("AAPL", 3, LocalDate.parse("2022-07-21"), 0.44);
    assertEquals((Double) 8.0, port.getShares("AAPL", LocalDate.parse("2022-07-14")));
    assertEquals((Double) 11.0, port.getShares("AAPL", LocalDate.parse("2022-07-21")));

    //Try invalid ticker
    try {
      port.addStock("BLAHBLAHBLAH", 3, LocalDate.parse("2022-07-14"), 0.45);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Ticker", e.getMessage());
    }
    //Try to add on weekend
    try {
      port.addStock("AAPL", 2, LocalDate.parse("2022-10-30"), 0.12);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date! 2022-10-30 must not be a weekend, or in the future.",
              e.getMessage());
    }
    //Try to add in future
    try {
      port.addStock("AAPL", 2, LocalDate.parse("2069-10-14"), 0.12);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date! 2069-10-14 must not be a weekend, or in the future.",
              e.getMessage());
    }
    //Try to add on holiday
    try {
      port.addStock("AAPL", 2, LocalDate.parse("2021-12-25"), 0.12);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date! 2021-12-25 must not be a weekend, or in the future.",
              e.getMessage());
    }


  }

  /**
   * Test getting the map of tickers to number of stocks owned.
   */
  @Test
  public void getTickerMap() {
    Map<String, Double> stocks = port.getTickerMap(LocalDate.parse("2022-10-14"));
    Map<String, Double> stocks2 = port.getTickerMap(LocalDate.parse("2022-07-13"));
    Map<String, Double> expected = new HashMap<>();
    assertTrue(expected.equals(stocks2));
    expected.put("AAPL", 4.0);
    expected.put("GOOGL", 2.0);
    assertTrue(expected.equals(stocks));
    // Invalid date
    try {
      port.getTickerMap(LocalDate.parse("2022-10-30"));
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date! 2022-10-30 must not be a weekend, or in the future.",
              e.getMessage());
    }
  }

  /**
   * Test getting the number of shares owned.
   */
  @Test
  public void getShares() {
    assertEquals((Double) 0.0, port.getShares("AAPL", LocalDate.parse("2022-07-13")));
    assertEquals((Double) 4.0, port.getShares("AAPL", LocalDate.parse("2022-07-14")));
    // Illegal date
    try {
      port.getShares("AAPL", LocalDate.parse("2069-07-14"));
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date! 2069-07-14 must not be a weekend, or in the future.",
              e.getMessage());
    }
    // Stock not owned
    try {
      port.getShares("BLAHBLAHBLAH", LocalDate.parse("2022-07-13"));
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Ticker", e.getMessage());
    }
  }

  /**
   * Test the cost basis.
   */
  @Test
  public void getCostBasis() {
    assertEquals(0, port.getCostBasis(LocalDate.parse("2022-07-13")), 0.01);
    assertEquals(5011.44, port.getCostBasis(LocalDate.parse("2022-07-14")), 0.01);
    port.sellStock("AAPL", 2, LocalDate.parse("2022-07-14"), 0.21);
    assertEquals(5011.44, port.getCostBasis(LocalDate.parse("2022-07-14")), 0.01);
    // Invalid Date
    try {
      port.getCostBasis(LocalDate.parse("2069-07-14"));
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date! 2069-07-14 must not be a weekend, or in the future.",
              e.getMessage());
    }
  }

  @Test
  public void testToString() {
    String expected = "{\n" +
            "\t{\n" +
            "\tticker: GOOGL,\n" +
            "\tstocks: 2.0,\n" +
            "\tdate: 2022-07-14,\n" +
            "\tcommission: 0.93\n" +
            "\t}\n" +
            "\t{\n" +
            "\tticker: AAPL,\n" +
            "\tstocks: 4.0,\n" +
            "\tdate: 2022-07-14,\n" +
            "\tcommission: 1.93\n" +
            "\t}\n" +
            "}";
    assertEquals(expected, port.toString());
  }

  /**
   * Test the isFlexible method.
   */
  @Test
  public void isFlexible() {
    assertEquals(true, port.isFlexible());
  }

}