package model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test the model.
 */
public class ModelImplTest {
  Model model;

  /**
   * Initialize.
   *
   * @throws Exception IDK bro.
   */
  @Before
  public void setUp() throws Exception {
    this.model = new ModelImpl();
    model.newFlexiblePortfolio("Test1");
    model.newFlexiblePortfolio("Test2");
    Map<String, Integer> stocks = new HashMap<>();
    stocks.put("AAPL", 2);
    model.newInflexiblePortfolio("Test3", stocks);
  }

  /**
   * Test getting the portfolios from the model.
   */
  @Test
  public void getPortfolios() {
    Map<String, Boolean> expected = new HashMap<>();
    expected.put("Test1", true);
    expected.put("Test2", true);
    expected.put("Test3", false);
    assertTrue(expected.equals(model.getPortfolios()));
  }

  /**
   * Test making a new Inflexible portfolio.
   */
  @Test
  public void newInflexiblePortfolio() {
    Map<String, Integer> stocks = new HashMap<>();
    stocks.put("GOOGL", 2);
    model.newInflexiblePortfolio("Test4", stocks);
    assertTrue(model.getPortfolios().keySet().contains("Test4"));
    // Portfolio name already exists
    try {
      model.newInflexiblePortfolio("Test4", stocks);
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio with that name already exists!", e.getMessage());
    }
    // Invalid Ticker
    try {
      stocks.put("BLAHBLAHBLAH", 3);
      model.newInflexiblePortfolio("Test5", stocks);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Ticker", e.getMessage());
    }
  }

  /**
   * Test making a new flexible portfolio.
   */
  @Test
  public void newFlexiblePortfolio() {
    model.newFlexiblePortfolio("Test4");
    assertTrue(model.getPortfolios().keySet().contains("Test4"));
    // Invalid name
    try {
      model.newFlexiblePortfolio("Test4");
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio with that name already exists!", e.getMessage());
    }
  }

  @Test
  public void isFlexible() {
    assertTrue(model.isFlexible("Test1"));
    assertTrue(!model.isFlexible("Test3"));
    try {
      model.isFlexible("FOO");
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio with that name does not exist!", e.getMessage());
    }
  }

  /**
   * Test adding a stock to a portfolio.
   */
  @Test
  public void addStock() {
    model.addStock("Test1", "FXAIX", 2, LocalDate.parse("2022-07-14"), 1.0);
    assertEquals(0.0, model.getPortValue("Test1", LocalDate.parse("2022-07-13")), 0.01);
    assertEquals(263.2, model.getPortValue("Test1", LocalDate.parse("2022-07-14")), 0.01);
    // Adding to inflexible portfolio
    try {
      model.addStock("Test3", "GOOGL", 2, LocalDate.parse("2022-07-14"), 1.0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio not flexible, cannot add stock!", e.getMessage());
    }
    // Ticker not found
    try {
      model.addStock("Test1", "BLAHBLAHBLAH", 2, LocalDate.parse("2022-07-14"), 1.0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid Ticker", e.getMessage());
    }
    // Invalid date
    try {
      model.addStock("Test1", "FXAIX", 2, LocalDate.parse("2069-12-22"), 1.0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date! 2069-12-22 must not be a weekend, or in the future.",
              e.getMessage());
    }
  }

  /**
   * Test selling stock.
   */
  @Test
  public void sellStock() {
    model.addStock("Test1", "AAPL", 2, LocalDate.parse("2022-10-14"), 0.12);
    model.sellStock("Test1", "AAPL", 2, LocalDate.parse("2022-10-14"), 0.12);
    assertEquals(0, model.getPortValue("Test1", LocalDate.parse("2022-10-12")), 0.01);
    assertEquals(0, model.getPortValue("Test1", LocalDate.parse("2022-10-14")), 0.01);
    //Try to sell before we bought
    try {
      model.sellStock("Test1", "AAPL", 2, LocalDate.parse("2022-07-13"), 0.69);
    } catch (IllegalArgumentException e) {
      assertEquals("Not enough stocks owned at 2022-07-13! Buy more before then, or sell less.",
              e.getMessage());
    }
    //Try to sell ticker we dont have
    try {
      model.sellStock("Test1", "FXAIX", 2, LocalDate.parse("2022-07-13"), 0.69);
    } catch (IllegalArgumentException e) {
      assertEquals("Not enough stocks owned at 2022-07-13! Buy more before then, or sell less.",
              e.getMessage());
    }
    //Try to sell more than we have
    try {
      model.sellStock("Test1", "AAPL", 6, LocalDate.parse("2022-10-14"), 0.12);
    } catch (IllegalArgumentException e) {
      assertEquals("Not enough stocks owned at 2022-10-14! Buy more before then, or sell less.",
              e.getMessage());
    }
    //Try to sell on weekend
    try {
      model.sellStock("Test1", "AAPL", 2, LocalDate.parse("2022-10-30"), 0.12);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date! 2022-10-30 must not be a weekend, or in the future.",
              e.getMessage());
    }
    //Try to sell in future
    try {
      model.sellStock("Test1", "AAPL", 2, LocalDate.parse("2069-10-14"), 0.12);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date! 2069-10-14 must not be a weekend, or in the future.",
              e.getMessage());
    }
    //Try to sell in portfolio we don't have
    try {
      model.sellStock("BLAHBLAHBLAH", "AAPL", 2, LocalDate.parse("2022-10-14"), 0.12);
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio with that name does not exist!", e.getMessage());
    }
  }

  /**
   * Test getting the ticker map for portfolio.
   */
  @Test
  public void getTickerMap() {
    assertTrue(model.getTickerMap("Test1", LocalDate.parse("2022-10-14")).isEmpty());
    model.addStock("Test1", "GOOGL", 2, LocalDate.parse("2022-10-12"), 1.0);
    assertTrue(model.getTickerMap("Test1", LocalDate.parse("2022-10-10")).isEmpty());
    assertTrue(!model.getTickerMap("Test1", LocalDate.parse("2022-10-14")).isEmpty());
    Map<String, Double> expected = new HashMap<>();
    expected.put("GOOGL", 2.0);
    assertTrue(expected.equals(model.getTickerMap("Test1", LocalDate.parse("2022-10-14"))));
    // Invalid portfolio
    try {
      model.getTickerMap("FOO", LocalDate.parse("2022-10-10"));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio with that name does not exist!", e.getMessage());
    }
    // Invalid date
    try {
      model.getTickerMap("Test1", LocalDate.parse("2069-10-10"));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date! 2069-10-10 must not be a weekend, or in the future.",
              e.getMessage());
    }
  }

  /**
   * Test getting the values for a portfolio.
   */
  @Test
  public void getValues() {
    model.addStock("Test1", "AAPL", 4, LocalDate.parse("2022-07-14"), 1.0);
    model.addStock("Test1", "GOOGL", 2, LocalDate.parse("2022-07-14"), 1.0);
    Map<String, Double> stocks = model.getTickerMap("Test1", LocalDate.parse("2022-10-14"));
    Map<String, Double> stocks2 = model.getTickerMap("Test1", LocalDate.parse("2022-07-13"));
    Map<String, Double> expected = new HashMap<>();
    assertTrue(expected.equals(stocks2));
    expected.put("AAPL", 4.0);
    expected.put("GOOGL", 2.0);
    assertTrue(expected.equals(stocks));
    // Invalid date
    try {
      model.getTickerMap("Test1", LocalDate.parse("2022-10-30"));
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date! 2022-10-30 must not be a weekend, or in the future.",
              e.getMessage());
    }
    // Invalid portfolio
    try {
      model.getTickerMap("BLAHBLAHBLAH", LocalDate.parse("2022-10-14"));
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio with that name does not exist!", e.getMessage());
    }
  }

  /**
   * Test getting the value of a portfolio.
   */
  @Test
  public void getPortValue() {
    model.addStock("Test1", "AAPL", 2, LocalDate.parse("2022-07-14"), 0.0);
    assertEquals(0, model.getPortValue("Test1", LocalDate.parse("2022-07-13")), 0.01);
    assertEquals(296.94, model.getPortValue("Test1", LocalDate.parse("2022-07-14")), 0.01);
    // Invalid date
    try {
      model.getPortValue("Test1", LocalDate.parse("2069-10-07"));
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date! 2069-10-07 must not be a weekend, " +
              "or in the future.", e.getMessage());
    }
    // Invalid portfolio
    try {
      model.getPortValue("BLAHBLAHBLAH", LocalDate.parse("2022-10-10"));
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio with that name does not exist!", e.getMessage());
    }
  }

  /**
   * Test getting the cost basis for a portfolio.
   */
  @Test
  public void getCostBasis() {
    model.addStock("Test1", "AAPL", 2, LocalDate.parse("2022-07-14"), 0.0);
    assertEquals(0, model.getCostBasis("Test1", LocalDate.parse("2022-07-13")), 0.01);
    assertEquals(296.94, model.getCostBasis("Test1", LocalDate.parse("2022-07-14")), 0.01);
    model.sellStock("Test1", "AAPL", 2, LocalDate.parse("2022-07-15"), 0.21);
    assertEquals(296.94, model.getCostBasis("Test1", LocalDate.parse("2022-07-15")), 0.01);
    // Invalid Date
    try {
      model.getCostBasis("Test1", LocalDate.parse("2069-07-14"));
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid date! 2069-07-14 must not be a weekend, or in the future.",
              e.getMessage());
    }
    // Invalid portfolio name
    try {
      model.getCostBasis("BLAHBLAHBLAH", LocalDate.parse("2022-10-10"));
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio with that name does not exist!", e.getMessage());
    }
  }

  /**
   * Test the get performance functionality of the model.
   */
  @Test
  public void getPerformance() {
    model.addStock("Test1", "AAPL", 2, LocalDate.parse("2022-07-14"), 0.0);
    Map<String, Double> actual = model.getPerformance("Test1", LocalDate.parse("2022-07-14"),
            LocalDate.parse(
                    "2022-08" +
                            "-14"));
    Map<String, Double> expected = new HashMap<>();
    expected.put("2022-07-14", 296.94);
    expected.put("2022-07-15", 300.34);
    expected.put("2022-07-18", 294.14);
    expected.put("2022-07-19", 302.0);
    expected.put("2022-07-20", 306.08);
    expected.put("2022-07-21", 310.7);
    expected.put("2022-07-22", 308.18);
    expected.put("2022-07-25", 305.9);
    expected.put("2022-07-26", 303.2);
    expected.put("2022-07-27", 313.58);
    expected.put("2022-07-28", 314.7);
    expected.put("2022-07-29", 325.02);
    expected.put("2022-08-01", 323.02);
    expected.put("2022-08-02", 320.02);
    expected.put("2022-08-03", 332.26);
    expected.put("2022-08-04", 331.62);
    expected.put("2022-08-05", 330.7);
    expected.put("2022-08-08", 329.74);
    expected.put("2022-08-09", 329.84);
    expected.put("2022-08-10", 338.48);
    expected.put("2022-08-11", 336.98);
    expected.put("2022-08-12", 344.2);

    assertTrue(expected.equals(actual));
    Map<String, Double> actual2 = model.getPerformance("Test1", LocalDate.parse("2022-07-14"),
            LocalDate.parse(
                    "2022-07" +
                            "-14"));
  }

  /**
   * Test getting the string representation of a model.
   */
  @Test
  public void testToString() {
    model.addStock("Test1", "GOOGL", 2, LocalDate.parse("2022-07-14"), 0.93);
    model.addStock("Test1", "AAPL", 4, LocalDate.parse("2022-07-14"), 1.93);
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
    assertEquals(expected, model.toString("Test1"));
  }

  /**
   * Test if a ticker is valid.
   */
  @Test
  public void validTicker() {
    assertTrue(model.validTicker("GOOGL"));
    assertTrue(!(model.validTicker("BLAHBLAHBLAH")));
    assertTrue(!(model.validTicker("LAKSJDFLKAJSDFLKAJDFLKAJASDLKFJASLDKFJASLKDFJA")));
  }

  /**
   * Test the fixed amount strategy feature of the model.
   */
  @Test
  public void testFixedAmountStrategy() {
    Map<String, Double> percentages = new HashMap<>();

    // BAD INPUTS:
    // Test bad sum of percentages
    percentages.put("AAPL", 23.69);
    percentages.put("GOOGL", 21.42);
    try {
      this.model.newFixedAmountStrategy("Test1", percentages, LocalDate.parse("2022-10-11"), 1000
              , 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Total percentage must be equal to 100! Is 45.11", e.getMessage());
    }
    // Map contains a negative percentage, but sums to 100
    percentages.put("VZ", 64.89);
    percentages.put("FXAIX", -10.0);
    try {
      this.model.newFixedAmountStrategy("Test1", percentages, LocalDate.parse("2022-10-11"), 1000
              , 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No percentage can be negative!", e.getMessage());
    }

    // Portfolio does not exist
    try {
      this.model.newFixedAmountStrategy("Foo", percentages, LocalDate.parse("2022-10-11"), 1000
              , 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio with that name does not exist!", e.getMessage());
    }
    // Inflexible portfolio
    try {
      this.model.newFixedAmountStrategy("Test3", percentages, LocalDate.parse("2022-10-11"), 1000
              , 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio not flexible, cannot create fixed amount strategy!", e.getMessage());
    }

    // GOOD INPUTS:
    // Past transaction
    percentages = new HashMap<>();
    percentages.put("AAPL", 24.67);
    percentages.put("GOOGL", 22.12);
    percentages.put("VZ", 12.2);
    percentages.put("FXAIX", 41.011); // This extra precision will be rounded off
    this.model.newFixedAmountStrategy("Test1", percentages, LocalDate.parse("2022-10-12"), 1000,
            2);
    // Should have no stocks before this fixed amount strategy
    assertEquals(0, this.model.getPortValue("Test1", LocalDate.parse("2022-10-10")), 0.001);
    // Should have all these stocks in our portfolio after
    assertTrue(this.model.getTickerMap("Test1", LocalDate.parse("2022-10-12"))
            .keySet().equals(percentages.keySet()));
    // Value on first day should be 1000 - 4*commission
    assertEquals(992.0, this.model.getPortValue("Test1", LocalDate.parse("2022-10-12")), 0.001);
    // And let's check later too
    assertEquals(1058.17, this.model.getPortValue("Test1", LocalDate.parse("2022-11-14")), 0.001);
    // Check the costBasis
    assertEquals(1000, this.model.getCostBasis("Test1", LocalDate.parse("2022-10-15")), 0.001);

    // Future transaction
    this.model.newFixedAmountStrategy("Test2", percentages, LocalDate.parse("2023-01-12"), 1000,
            2);
    // Should have no stocks before this fixed amount strategy
    assertEquals(0, this.model.getPortValue("Test2", LocalDate.parse("2022-10-10")), 0.001);
    // Cost basis should work for this
    assertEquals(1000, this.model.getCostBasis("Test2", LocalDate.parse("2023-01-15")), 0.001);
  }

  /**
   * Test the dollar cost averaging strategy.
   */
  @Test
  public void testDollarCostAverageStrategy() {
    Map<String, Double> percentages = new HashMap<>();

    // BAD INPUTS:
    // Test bad sum of percentages
    percentages.put("AAPL", 23.69);
    percentages.put("GOOGL", 21.42);
    try {
      this.model.newDollarCostAverageStrategy("Test1", percentages, LocalDate.parse("2022-10-11")
              , 2,
              1000
              , 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Total percentage must be equal to 100! Is 45.11", e.getMessage());
    }
    // Map contains a negative percentage, but sums to 100
    percentages.put("VZ", 64.89);
    percentages.put("FXAIX", -10.0);
    try {
      this.model.newDollarCostAverageStrategy("Test1", percentages, LocalDate.parse("2022-10-11")
              , 2,
              1000
              , 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No percentage can be negative!", e.getMessage());
    }

    // Inflexible portfolio
    try {
      this.model.newDollarCostAverageStrategy("Test3", percentages, LocalDate.parse("2022-10-11")
              , 2,
              1000
              , 2);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio not flexible, cannot create fixed amount strategy!", e.getMessage());
    }

    // GOOD INPUTS:
    // Past start date, no end, new portfolio, every 2 days
    percentages = new HashMap<>();
    percentages.put("AAPL", 24.67);
    percentages.put("GOOGL", 22.12);
    percentages.put("VZ", 12.2);
    percentages.put("FXAIX", 41.011); // This extra precision will be rounded off
    this.model.newDollarCostAverageStrategy("Test", percentages, LocalDate.parse("2022-10-12")
            , 2,
            1000
            , 2);
    // Should have no stocks before this fixed amount strategy
    assertEquals(0, this.model.getPortValue("Test", LocalDate.parse("2022-10-10")), 0.001);
    // Should have all these stocks in our portfolio after
    assertTrue(this.model.getTickerMap("Test", LocalDate.parse("2022-10-12"))
            .keySet().equals(percentages.keySet()));
    // Value on first day should be 1000 - 4*commission
    assertEquals(992.0, this.model.getPortValue("Test", LocalDate.parse("2022-10-12")), 0.001);
    // And let's check later too
    assertEquals(15360.74, this.model.getPortValue("Test", LocalDate.parse("2022-11-14")), 0.001);
    // Check the costBasis
    assertEquals(1000, this.model.getCostBasis("Test", LocalDate.parse("2022-10-12")), 0.001);
    assertEquals(3000, this.model.getCostBasis("Test", LocalDate.parse("2022-10-17")), 0.001);
    // We should know this, even if it's in the future
    assertEquals(34000, this.model.getCostBasis("Test", LocalDate.parse("2022-12-25")), 0.001);

    // Now try selling some of those stocks we bought in our strategy
    this.model.sellStock("Test", "GOOGL", 1, LocalDate.parse("2022-11-14"), 2);
    // Now our total value should be lower than before
    assertEquals(15265.04, this.model.getPortValue("Test", LocalDate.parse("2022-11-14")), 0.001);

    // Let's also just try a regular purchase thrown in the mix
    this.model.addStock("Test", "TSLA", 2, LocalDate.parse("2022-10-12"), 2);
    // Should have no stocks before still
    assertEquals(0, this.model.getPortValue("Test", LocalDate.parse("2022-10-10")), 0.001);
    // Now value on first day should reflect purchase
    assertEquals(1426.48, this.model.getPortValue("Test", LocalDate.parse("2022-10-12")), 0.001);

    // Now do it all again, with an end date, and a portfolio that already exists AND already has
    // stock in it.
    percentages = new HashMap<>();
    percentages.put("AAPL", 24.67);
    percentages.put("GOOGL", 22.12);
    percentages.put("VZ", 12.2);
    percentages.put("FXAIX", 41.011); // This extra precision will be rounded off
    this.model.addStock("Test2", "TSLA", 1, LocalDate.parse("2022-10-11"), 0);
    assertEquals(216.5, this.model.getPortValue("Test2", LocalDate.parse("2022-10-11")), 0.001);
    assertEquals(216.5, this.model.getCostBasis("Test2", LocalDate.parse("2022-10-11")), 0.001);
    this.model.newDollarCostAverageStrategy("Test2", percentages, LocalDate.parse("2022-10-12"),
            LocalDate.parse("2022-11-12")
            , 2,
            1000
            , 2);
    // Should have only teska before this fixed amount strategy
    assertEquals(0, this.model.getPortValue("Test2", LocalDate.parse("2022-10-10")), 0.001);
    // Value on first day
    assertEquals(1209.24, this.model.getPortValue("Test2", LocalDate.parse("2022-10-12")), 0.001);
    // And let's check later too
    assertEquals(14559.69, this.model.getPortValue("Test2", LocalDate.parse("2022-11-14")), 0.001);
    // Check the costBasis
    assertEquals(1216.5, this.model.getCostBasis("Test2", LocalDate.parse("2022-10-12")), 0.001);
    assertEquals(3216.5, this.model.getCostBasis("Test2", LocalDate.parse("2022-10-17")), 0.001);
    // We should know this, even if it's in the future
    assertEquals(14216.5, this.model.getCostBasis("Test2", LocalDate.parse("2022-12-25")), 0.001);

    // Now try selling some of those stocks we bought in our strategy
    this.model.sellStock("Test2", "GOOGL", 1, LocalDate.parse("2022-11-14"), 2);
    // Now our total value should be lower than before
    assertEquals(14463.99, this.model.getPortValue("Test2", LocalDate.parse("2022-11-14")), 0.001);

    // Let's also just try a regular purchase thrown in the mix
    this.model.addStock("Test2", "TSLA", 2, LocalDate.parse("2022-10-12"), 2);
    // Should have no stocks before still
    assertEquals(0, this.model.getPortValue("Test2", LocalDate.parse("2022-10-10")), 0.001);
    // Now value on first day should reflect purchase
    assertEquals(1643.72, this.model.getPortValue("Test2", LocalDate.parse("2022-10-12")), 0.001);
  }

}