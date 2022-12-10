package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;

/**
 * Test the AlphaVantageAPI.
 */
public class AlphaVantageTest {
  String[][] data;
  Model model = new ModelImpl();

  /**
   * Initialize tests.
   */
  @Before
  public void init() {
    data = model.getAPI().getStock("GOOGL");
  }

  /**
   * Make sure we error when stock not found.
   */
  @Test
  public void testError() {
    try {
      model.getAPI().getStock("ELONCOIN");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Ticker", e.getMessage());
    }
  }

  /**
   * Make sure the return format of the data is correct.
   */
  @Test
  public void getStock() {
    //Just checking lengths are the same, since data will change every day.
    for (int i = 0; i < data.length - 1; ++i) {
      assertTrue(data[i].length == data[i + 1].length);
    }
  }
}