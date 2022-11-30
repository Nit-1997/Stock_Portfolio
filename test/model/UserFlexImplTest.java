package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import constants.Constants;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for UserFlexImpl class. Note : please run the tests in the order.
 */
public class UserFlexImplTest {

  UserFlex user;

  @Before
  public void init() {
    user = new UserFlexImpl();
  }

  @Test
  public void testConstructor() {
    File file = new File(Paths.get("portfolios" + File.separator + "flex").toAbsolutePath()
        .toString());

    assertTrue(file.exists());

    assertEquals(file.list((f1, name) -> name.endsWith(".csv")).length,
        user.getPortfolios().size());

    assertNotNull(Constants.STOCK_NAMES);

    assertEquals(Constants.STOCK_NAMES.size(), 102);
  }

  @Test
  public void testAddPortfolio() throws Exception {

    Map<String, Map<String, SimpleEntry<Double, Double>>> stocksMap = new HashMap<>();
    stocksMap.put("AAPL", new HashMap<>());
    stocksMap.put("MSFT", new HashMap<>());
    stocksMap.put("AMZN", new HashMap<>());
    stocksMap.put("INTU", new HashMap<>());
    stocksMap.get("AAPL").put("2015-01-15", new SimpleEntry<>(10.0, 3.5));
    stocksMap.get("INTU").put("2019-12-01", new SimpleEntry<>(25.0, 3.5));
    stocksMap.get("AMZN").put("2015-08-11", new SimpleEntry<>(24.0, 3.5));
    stocksMap.get("AAPL").put("2016-07-05", new SimpleEntry<>(7.0, 3.5));
    stocksMap.get("INTU").put("2020-07-05", new SimpleEntry<>(17.0, 3.5));
    stocksMap.get("MSFT").put("2018-09-13", new SimpleEntry<>(7.0, 3.5));
    stocksMap.get("AAPL").put("2017-11-09", new SimpleEntry<>(14.0, 3.5));
    stocksMap.get("AMZN").put("2016-03-21", new SimpleEntry<>(11.0, 3.5));
    stocksMap.get("MSFT").put("2019-01-11", new SimpleEntry<>(5.0, 3.5));
    stocksMap.get("INTU").put("2019-02-05", new SimpleEntry<>(17.0, 3.5));
    stocksMap.get("AMZN").put("2019-10-30", new SimpleEntry<>(2.0, 3.5));
    stocksMap.get("MSFT").put("2018-02-15", new SimpleEntry<>(4.0, 3.5));
    stocksMap.get("INTU").put("2021-01-01", new SimpleEntry<>(7.0, 3.5));

    boolean val = user.addPortfolio("flexUserTest", stocksMap);
    assertTrue(val);
  }

  @Test(expected = Exception.class)
  public void testAddPortfolioSameName() throws Exception {
    Map<String, Map<String, SimpleEntry<Double, Double>>> stocksMap = new HashMap<>();
    user.addPortfolio("flexUserTest", stocksMap);
  }

  @Test(expected = Exception.class)
  public void testAddPortfolioWrongData() throws Exception {
    Map<String, Map<String, SimpleEntry<Double, Double>>> stocksMap = new HashMap<>();

    stocksMap.put("MSFT", new HashMap<>());
    stocksMap.put("INTU", new HashMap<>());
    stocksMap.get("INTU").put(null, new SimpleEntry<>(17.0, 3.5));
    stocksMap.get("MSFT").put("2019-01-11", null);

    boolean val = user.addPortfolio("xdcfvgbh", stocksMap);
  }

  @Test
  public void testGetPortfolioValueBeforePortfolioCreation() throws Exception {
    String portfolioCreationDate = user.getPortfolioCreationDate("flexUserTest2");
    Double portfolioValue;
    try {
      portfolioValue = user.getPortfolioValue("flexUserTest2",
          LocalDate.parse(portfolioCreationDate).minusDays(10).toString());
    } catch (Exception e) {
      portfolioValue=null;
    }
    assertEquals(0.0, portfolioValue, 0.01);
  }

  @Test
  public void testGetPortfolioValueAfterPortfolioCreationDate() throws Exception {
    String portfolioCreationDate = user.getPortfolioCreationDate("flexUserTest2");
    Double portfolioValue;
    try {
      portfolioValue = user.getPortfolioValue("flexUserTest2",
          LocalDate.parse(portfolioCreationDate).plusDays(100).toString());
    } catch (Exception e) {
      portfolioValue=null;
    }
    assertEquals(6293.75, portfolioValue, 0.01);
  }

  @Test
  public void testGetPortfolioCurrentValue() {

    Double portfolioValue;
    try {
      portfolioValue = user.getPortfolioValue("flexUserTest2",
          LocalDate.now().toString());
    } catch (Exception e) {
      portfolioValue=null;
    }
    assertEquals(15621.65, portfolioValue, 0.01);
  }

  @Test
  public void testGetPortfolioValueAfterToday() {

    Double portfolioValue;
    try {
      portfolioValue = user.getPortfolioValue("flexUserTest2",
          LocalDate.now().plusDays(1).toString());
    } catch (Exception e) {
      portfolioValue=null;
    }
    assertNull(portfolioValue);
  }

  @Test
  public void testGetPortfolioValueWrongPortfolioName() {
    Double portfolioValue;
    try {
      portfolioValue = user.getPortfolioValue("svhcjk",
          LocalDate.now().plusDays(1).toString());
    } catch (Exception e) {
      portfolioValue=null;
    }
    assertNull(portfolioValue);
  }

  @Test
  public void testGetPortfolioSummaryBeforeCreationDate() {
    Map<String, Double> summary;
    try {
      summary = user.getPortfolioSummary("flexUserTest2",
          "2014-01-01");
    } catch (Exception e) {
      summary = null;
    }
    assertNull(summary);
  }

  @Test
  public void testGetPortfolioSummaryAfterPortfolioCreationDate() throws Exception {
    String portfolioCreationDate = user.getPortfolioCreationDate("flexUserTest2");
    Map<String, Double> summary;
    try {
      summary = user.getPortfolioSummary("flexUserTest2",
          LocalDate.parse(portfolioCreationDate).plusDays(100).toString());
    } catch (Exception e) {
      summary=null;
    }
    assertEquals(summary.size(), 2);
    assertTrue(summary.containsKey("AAPL"));
    assertTrue(summary.containsKey("INTU"));
    assertEquals(summary.get("AAPL"), 25.0, 0.01);
    assertEquals(summary.get("INTU"), 30.0, 0.01);
  }

  @Test
  public void testGetPortfolioSummaryAfterPortfolioCreationDate2() throws Exception {
    String portfolioCreationDate = user.getPortfolioCreationDate("flexUserTest2");
    Map<String, Double> summary;
    try {
      summary = user.getPortfolioSummary("flexUserTest2",
          LocalDate.parse(portfolioCreationDate).plusWeeks(400).toString());
    } catch (Exception e) {
      summary=null;
    }
    assertEquals(summary.size(), 5);
    assertTrue(summary.containsKey("AAPL"));
    assertTrue(summary.containsKey("INTU"));
    assertTrue(summary.containsKey("AMZN"));
    assertEquals(summary.get("AAPL"), 8.0, 0.01);
    assertEquals(summary.get("INTU"), 2.0, 0.01);
    assertEquals(summary.get("MSFT"), 13.0, 0.01);
  }

  @Test
  public void testGetPortfolioSummaryAfterToday() {
    Map<String, Double> summary;
    try {
      summary = user.getPortfolioSummary("flexUserTest2",
          LocalDate.now().plusDays(3).toString());
    } catch (Exception e) {
      summary=null;
    }
    assertNull(summary);
  }

  @Test
  public void testGetCurrentPortfolioSummary() throws Exception {
    Map<String, Double> summary = user.getPortfolioSummary("flexUserTest2",
        LocalDate.now().toString());
    assertEquals(summary.size(), 5);
    assertTrue(summary.containsKey("AAPL"));
    assertTrue(summary.containsKey("INTU"));
    assertTrue(summary.containsKey("MSFT"));
    assertEquals(summary.get("AAPL"), 8.0, 0.01);
    assertEquals(summary.get("INTU"), 11.0, 0.01);
    assertEquals(summary.get("AMZN"), 46.0, 0.01);
  }

  @Test
  public void testGetPortfolioSummaryWrongPortfolioName() throws Exception {
    Map<String, Double> summary = user.getPortfolioSummary("kfjdvnc", "2016-01-01");
    assertNull(summary);
  }

  @Test
  public void testGetPortfolioCreationDate() throws Exception {
    String creationDate = user.getPortfolioCreationDate("flexUserTest2");
    assertEquals("2015-01-15", creationDate);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPortfolioCreationDateWrongPortfolioName() throws Exception {
    String creationDate = user.getPortfolioCreationDate(null);
    assertNull(creationDate);

    creationDate = user.getPortfolioCreationDate("");
    assertNull(creationDate);

    creationDate = user.getPortfolioCreationDate("jwhcsjnx");
    assertNull(creationDate);
  }

  @Test
  public void testGetPortfolioState() {
    Map<String, SimpleEntry<String, Double>> state = user.getPortfolioState("flexUserTest2");
    assertTrue(state.containsKey("MSFT"));
    assertTrue(state.containsKey("META"));
    assertTrue(state.containsKey("AAPL"));
    assertTrue(state.containsKey("INTU"));
    assertTrue(state.containsKey("AMZN"));
    assertEquals(state.get("AAPL").getKey(), "2017-09-09");
    assertEquals(state.get("MSFT").getKey(), "2018-12-01");
    assertEquals(state.get("META").getKey(), "2021-06-25");
    assertEquals(state.get("AMZN").getKey(), "2019-10-24");
    assertEquals(state.get("AAPL").getValue(), 8.0, 0.01);
    assertEquals(state.get("MSFT").getValue(), 13.0, 0.01);
    assertEquals(state.get("META").getValue(), 22.0, 0.01);
    assertEquals(state.get("INTU").getValue(), 11.0, 0.01);
  }

  @Test
  public void testGetPortfolioStateWrongPortfolioName() {
    Map<String, SimpleEntry<String, Double>> state = user.getPortfolioState("hbcn");
    assertNull(state);
  }

  @Test
  public void testGetCostBasisBeforeCreationDate() {
    Double cost = null;
    try {
      cost = user.getCostBasis("userFlexTest2", "2014-01-01");
    } catch (Exception e) {
      cost = null;
    }
    assertNull(cost);
  }

  @Test
  public void testGetCostBasisAfterCreationdate() throws Exception {
    String portfolioCreationDate = user.getPortfolioCreationDate("flexUserTest2");
    Double cost = user.getCostBasis("flexUserTest2",
        LocalDate.parse(portfolioCreationDate).plusDays(100).toString());
    assertEquals(9415.7, cost, 0.01);

    cost = user.getCostBasis("flexUserTest2",
        LocalDate.parse(portfolioCreationDate).plusWeeks(100).toString());
    assertEquals(67319.66, cost, 0.01);

    cost = user.getCostBasis("flexUserTest2",
        LocalDate.parse(portfolioCreationDate).plusWeeks(400).toString());
    assertEquals(200785.58000000002, cost, 0.01);

    cost = user.getCostBasis("flexUserTest2",
        LocalDate.parse(portfolioCreationDate).plusMonths(13).toString());
    assertEquals(61957.66, cost, 0.01);

    cost = user.getCostBasis("flexUserTest2",
        LocalDate.parse(portfolioCreationDate).plusYears(4).toString());
    assertEquals(79140.07, cost, 0.01);
  }

  @Test
  public void testGetCurrentCostBasis() throws Exception {
    Double cost = user.getCostBasis("flexUserTest2", LocalDate.now().toString());
    assertEquals(204207.73, cost, 0.01);
  }

  @Test
  public void testGetCostBasisAfterToday() {
    Double cost = null;
    try {
      cost = user.getCostBasis("flexUserTest2",
          LocalDate.now().plusDays(10).toString());
    } catch (Exception e) {
      cost = null;
    }
    assertNull(cost);
  }

  @Test
  public void testGetCostBasisWrongPortfolioName() {
    Double cost = null;
    try {
      cost = user.getCostBasis("irhj", LocalDate.now().toString());
    } catch (Exception e) {
      cost = null;
    }
    assertNull(cost);
  }

  @Test
  public void testTransactionForPortfolioBuyNewStock() throws Exception {
    boolean val = user.transactionForPortfolio("flexUserTest",
        new SimpleEntry<>("GOOG", new SimpleEntry<>("2017-01-01", new SimpleEntry<>(15.0, 3.8))));
    assertTrue(val);
  }

  @Test(expected = Exception.class)
  public void testTransactionForPortfolioBuyNewStockBeforePortfolioCreation() throws Exception {
    boolean val = user.transactionForPortfolio("flexUserTest",
        new SimpleEntry<>("GOOG", new SimpleEntry<>("2014-01-01", new SimpleEntry<>(15.0, 3.8))));
  }

  @Test(expected = Exception.class)
  public void testTransactionForPortfolioBuyNewStockAfterCurrentDate() throws Exception {
    boolean val = user.transactionForPortfolio("flexUserTest",
        new SimpleEntry<>("GOOG", new SimpleEntry<>(LocalDate.now().plusDays(1).toString(),
            new SimpleEntry<>(15.0, 3.8))));
  }

  @Test(expected = Exception.class)
  public void testTransactionForPortfolioBuyNewStockWrongTicker() throws Exception {
    boolean val = user.transactionForPortfolio("flexUserTest",
        new SimpleEntry<>("fds",
            new SimpleEntry<>(LocalDate.now().toString(), new SimpleEntry<>(15.0, 3.8))));
  }

  @Test(expected = Exception.class)
  public void testTransactionForPortfolioBuyNewStockNegativeCommissionFee() throws Exception {
    boolean val = user.transactionForPortfolio("flexUserTest",
        new SimpleEntry<>("GOOG", new SimpleEntry<>("2017-01-01", new SimpleEntry<>(15.0, -3.8))));
  }

  @Test
  public void testTransactionForPortfolioBuyOldStock() throws Exception {
    boolean val = user.transactionForPortfolio("flexUserTest",
        new SimpleEntry<>("GOOG", new SimpleEntry<>("2018-01-01", new SimpleEntry<>(15.0, 3.8))));
    assertTrue(val);
  }

  @Test(expected = Exception.class)
  public void testTransactionForPortfolioBuyOldStockBeforeLastTransaction() throws Exception {
    boolean val = user.transactionForPortfolio("flexUserTest",
        new SimpleEntry<>("GOOG", new SimpleEntry<>("2017-01-01", new SimpleEntry<>(15.0, 3.8))));
  }

  @Test(expected = Exception.class)
  public void testTransactionForPortfolioBuyOldStockInvalidDate() throws Exception {
    boolean val = user.transactionForPortfolio("flexUserTest",
        new SimpleEntry<>("GOOG", new SimpleEntry<>("djvhbjnd-01", new SimpleEntry<>(15.0, 3.8))));
  }

  @Test(expected = Exception.class)
  public void testTransactionForPortfolioBuyOldStockAfterToday() throws Exception {
    boolean val = user.transactionForPortfolio("flexUserTest",
        new SimpleEntry<>("GOOG", new SimpleEntry<>(LocalDate.now().plusDays(3).toString(),
            new SimpleEntry<>(15.0, 3.8))));
  }

  @Test
  public void testTransactionForPortfolioSellStock() throws Exception {
    boolean val = user.transactionForPortfolio("flexUserTest",
        new SimpleEntry<>("GOOG", new SimpleEntry<>("2019-01-01", new SimpleEntry<>(-10.0, 2.9))));
    assertTrue(val);
  }

  @Test(expected = Exception.class)
  public void testTransactionForPortfolioSellStockNotExistingTicker() throws Exception {
    boolean val = user.transactionForPortfolio("flexUserTest",
        new SimpleEntry<>("NVDA", new SimpleEntry<>("2019-01-01", new SimpleEntry<>(-10.0, 2.9))));
  }

  @Test(expected = Exception.class)
  public void testTransactionForPortfolioSellStockHigherQuantity() throws Exception {
    boolean val = user.transactionForPortfolio("flexUserTest",
        new SimpleEntry<>("GOOG", new SimpleEntry<>("2019-01-01", new SimpleEntry<>(-40.0, 2.9))));
  }

  @Test(expected = Exception.class)
  public void testTransactionForPortfolioSellStockBeforeLastDate() throws Exception {
    boolean val = user.transactionForPortfolio("flexUserTest",
        new SimpleEntry<>("GOOG", new SimpleEntry<>("2018-01-01", new SimpleEntry<>(-40.0, 2.9))));
  }

  @Test(expected = Exception.class)
  public void testTransactionForPortfolioSellStockInvalidDate() throws Exception {
    boolean val = user.transactionForPortfolio("flexUserTest",
        new SimpleEntry<>("GOOG", new SimpleEntry<>("vjhcbjknwq", new SimpleEntry<>(-40.0, 2.9))));
  }

  @Test(expected = Exception.class)
  public void testTransactionForPortfolioSellStockAfterToday() throws Exception {
    boolean val = user.transactionForPortfolio("flexUserTest",
        new SimpleEntry<>("GOOG", new SimpleEntry<>(LocalDate.now().plusDays(3).toString(),
            new SimpleEntry<>(-40.0, 2.9))));
  }

  @Test
  public void testGetGraphDataMonthly() {
    SimpleEntry<SimpleEntry<List<String>, List<Integer>>, SimpleEntry<Integer, Double>> graphdata =
        user.getGraphData("2018-01-01", "2018-12-31", "flexUserTest2");
    List<String> labels = graphdata.getKey().getKey();
    List<Integer> starPoints = graphdata.getKey().getValue();
    int scale = graphdata.getValue().getKey();
    double baseAmount = graphdata.getValue().getValue();
    assertTrue(labels.size() <= 30);
    assertEquals("JAN 2018", labels.get(0));
    assertTrue(starPoints.size() <= 30);
    assertTrue(Collections.max(starPoints) <= 50);
    assertNotNull(scale);
    assertNotNull(baseAmount);
  }

  @Test
  public void testGetGraphDataQuarterly() {
    SimpleEntry<SimpleEntry<List<String>, List<Integer>>, SimpleEntry<Integer, Double>> graphdata =
        user.getGraphData("2015-02-01", "2017-12-31", "flexUserTest2");
    List<String> labels = graphdata.getKey().getKey();
    List<Integer> starPoints = graphdata.getKey().getValue();
    int scale = graphdata.getValue().getKey();
    double baseAmount = graphdata.getValue().getValue();
    assertTrue(labels.size() <= 30);
    assertEquals("Qtr1 2015", labels.get(0));
    assertTrue(starPoints.size() <= 30);
    assertTrue(Collections.max(starPoints) <= 50);
    assertNotNull(scale);
    assertNotNull(baseAmount);
  }

  @Test
  public void testGetGraphDataWeekly() {
    SimpleEntry<SimpleEntry<List<String>, List<Integer>>, SimpleEntry<Integer, Double>> graphdata =
        user.getGraphData("2015-08-13", "2015-10-10", "flexUserTest2");
    List<String> labels = graphdata.getKey().getKey();
    List<Integer> starPoints = graphdata.getKey().getValue();
    int scale = graphdata.getValue().getKey();
    double baseAmount = graphdata.getValue().getValue();
    assertTrue(labels.size() <= 30);
    assertEquals("AUG Week 3", labels.get(0));
    assertTrue(starPoints.size() <= 30);
    assertTrue(Collections.max(starPoints) <= 50);
    assertNotNull(scale);
    assertNotNull(baseAmount);
  }

  @Test
  public void testGetGraphDataDaily() {
    SimpleEntry<SimpleEntry<List<String>, List<Integer>>, SimpleEntry<Integer, Double>> graphdata =
        user.getGraphData("2021-06-06", "2021-06-26", "flexUserTest2");
    List<String> labels = graphdata.getKey().getKey();
    List<Integer> starPoints = graphdata.getKey().getValue();
    int scale = graphdata.getValue().getKey();
    double baseAmount = graphdata.getValue().getValue();
    assertTrue(labels.size() <= 30);
    assertEquals("2021-06-06", labels.get(0));
    assertTrue(starPoints.size() <= 30);
    assertTrue(Collections.max(starPoints) <= 50);
    assertNotNull(scale);
    assertNotNull(baseAmount);
  }

  @Test
  public void testGetGraphDataLessThan5Days() {
    SimpleEntry<SimpleEntry<List<String>, List<Integer>>, SimpleEntry<Integer, Double>> graphdata =
        user.getGraphData("2021-06-06", "2021-06-09", "flexUserTest2");
    List<String> labels = graphdata.getKey().getKey();
    List<Integer> starPoints = graphdata.getKey().getValue();
    int scale = graphdata.getValue().getKey();
    double baseAmount = graphdata.getValue().getValue();
    assertTrue(labels.size() <= 30);
    assertEquals("2021-06-06", labels.get(0));
    assertTrue(starPoints.size() <= 30);
    assertTrue(Collections.max(starPoints) <= 50);
    assertNotNull(scale);
    assertNotNull(baseAmount);
  }

  @Test
  public void testGetGraphDataWeeklyInvalidRange() {
    SimpleEntry<SimpleEntry<List<String>, List<Integer>>, SimpleEntry<Integer, Double>> graphdata =
        user.getGraphData("2013-08-13", "2015-10-10", "flexUserTest2");
    assertNull(graphdata);

    graphdata = user.getGraphData("2016-08-13", "2022-12-10",
        "flexUserTest2");
    assertNull(graphdata);

    graphdata = user.getGraphData("2019-08-13", "2018-12-10",
        "flexUserTest2");
    assertNull(graphdata);
  }


}