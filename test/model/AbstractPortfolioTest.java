package model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the abstract portfolio class.
 */
public class AbstractPortfolioTest {
  Portfolio port;

  @Before
  public void setUp() throws Exception {
    port = new FlexiblePortfolio("Test");
    ((FlexiblePortfolio) port).addStock("AAPL", 2, LocalDate.parse("2022-07-14"), 0.0);
  }

  /**
   * Test getting the portfolio name.
   */
  @Test
  public void getPortName() {
    assertEquals("Test", port.getPortName());
  }

  /**
   * Test getting the number of stocks.
   */
  @Test
  public void getNumStocks() {
    assertEquals(1, port.getNumStocks(), 0.01);
  }

  /**
   * Test getting the value of the portfolio.
   */
  @Test
  public void getPortValue() {
    assertEquals(0, port.getPortValue(LocalDate.parse("2022-07-13")), 0.01);
    assertEquals(296.94, port.getPortValue(LocalDate.parse("2022-07-14")), 0.01);
    // Invalid date
    try {
      port.getPortValue(LocalDate.parse("2069-10-07"));
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date! 2069-10-07 must not be a weekend, or in the future.",
              e.getMessage());
    }
  }

  /**
   * Test getting the values of all the stokcs in the portfolio.
   */
  @Test
  public void getValues() {
    Map<String, Double> expected = new HashMap<>();
    assertTrue(expected.equals(port.getValues(LocalDate.parse("2022-07-13"))));
    expected.put("AAPL", 148.47);
    assertEquals(expected.get("AAPL"), port.getValues(LocalDate.parse("2022-07-14")).get("AAPL"),
            0.01);
  }
}