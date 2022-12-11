package model;

import org.junit.Test;
import org.junit.Before;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the stockImpl class.
 */
public class StockImplTest {
  public Stock testStock;
  private String[] keys;
  private String[][] values;

  /**
   * Setup.
   */
  @Before
  public void init() {
    keys = new String[]{"timestamp", "open", "high", "low", "close", "fuggedaboutit", "volume"};
    values = new String[][]{{"2022-10-27", "94.5200", "95.4300", "91.8000", "92.2200",
            "1234", "60710859"}};
    testStock = new StockImpl("GOOGL", keys, values);
  }

  /**
   * Test the constructor.
   */
  @Test
  public void StockImpl() {
    Map<String, Double> expected = new HashMap<String, Double>();
    expected.put("open", 94.5200);
    expected.put("high", 95.4300);
    expected.put("low", 91.8000);
    expected.put("close", 92.2200);
    expected.put("fuggedaboutit", 1234.0);
    expected.put("volume", 60710859.0);

    Map<String, Double> rtrn = testStock.getValue(LocalDate.parse("2022-10-27"));

    assertEquals("GOOGL", testStock.getTicker());
    assertTrue(expected.equals(rtrn));

    String[] keys2 = {"timestamp", "open", "high", "low", "close", "fuggedaboutit", "volume"};
    String[][] values2 = {{"23-NOV-2022", "94.5200", "95.4300", "91.8000", "92.2200",
            "1234", "60710859"}};
    try {
      Stock testStock2 = new StockImpl("BAR", keys2, values2);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Date in wrong format! Use YYYY-MM-DD");
    }


  }

  /**
   * Test getTicker.
   */
  @Test
  public void getTicker() {
    assertEquals("GOOGL", testStock.getTicker());
  }

  /**
   * Test getValue.
   */
  @Test
  public void getValue() {
    double[] dataEntry = Arrays.stream(Arrays
                    .copyOfRange(values[0], 1, values[0].length))
            .mapToDouble(Double::parseDouble)//Go to doubles.
            .toArray();
    String[] values = (String[]) Arrays.stream(Arrays.copyOfRange(keys, 1, keys.length))
            .toArray(String[]::new);
    Map<String, Double> answer = new HashMap<String, Double>();
    for (int i = 0; i < values.length; ++i) {
      answer.put(values[i], dataEntry[i]);
    }


    assertTrue(answer.equals(testStock.getValue(LocalDate.parse("2022-10-27"))));

  }
}