package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import constants.Constants;
import java.time.LocalDate;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import utils.Utils;

/**
 * JUnit class for PortfolioFlexImpl class.
 */
public class PortfolioFlexImplTest {

  Map<String, Map<String, SimpleEntry<Double, Double>>> order;

  @Before
  public void beforeSuit() throws Exception {
    Constants.STOCK_NAMES = Utils.loadStockNames("stocks",
        "stocks_list.csv");
    Utils.clearStockDirectory();
    order = new HashMap<>();
    Map<String, SimpleEntry<Double, Double>> ciscoTuple = new HashMap<>();
    ciscoTuple.put("2022-10-28", new SimpleEntry<>(10.0, 4.5));
    ciscoTuple.put("2022-10-29", new SimpleEntry<>(12.0, 4.0));
    ciscoTuple.put("2022-10-31", new SimpleEntry<>(-10.0, 3.5));
    Map<String, SimpleEntry<Double, Double>> netflixTuple = new HashMap<>();
    netflixTuple.put("2022-10-28", new SimpleEntry<>(10.0, 5.0));
    netflixTuple.put("2022-10-29", new SimpleEntry<>(-2.0, 4.5));
    netflixTuple.put("2022-10-31", new SimpleEntry<>(2.0, 4.0));
    Map<String, SimpleEntry<Double, Double>> appleTuple = new HashMap<>();
    appleTuple.put("2020-10-28", new SimpleEntry<>(10.0, 5.0));
    appleTuple.put("2021-10-29", new SimpleEntry<>(-3.0, 3.0));
    appleTuple.put("2022-10-31", new SimpleEntry<>(2.0, 5.5));
    order.put("CSCO", ciscoTuple);
    order.put("NFLX", netflixTuple);
    order.put("AAPL", appleTuple);
  }

  /**
   * Generates Portfolio for Performance Test.
   * @return Portfolio date.
   */
  public Map<String, Map<String, SimpleEntry<Double, Double>>> generatePerfTestPortfolioOrder() {
    Map<String, Map<String, SimpleEntry<Double, Double>>> perfTestOrder = new HashMap<>();
    Map<String, SimpleEntry<Double, Double>> ciscoTuple = new HashMap<>();
    ciscoTuple.put("2013-02-02", new SimpleEntry<>(112.0, 1.5));
    ciscoTuple.put("2019-01-01", new SimpleEntry<>(-80.0, 2.0));
    ciscoTuple.put("2022-10-31", new SimpleEntry<>(200.0, 3.5));
    Map<String, SimpleEntry<Double, Double>> googleTuple = new HashMap<>();
    googleTuple.put("2016-01-02", new SimpleEntry<>(120.0, 2.0));
    googleTuple.put("2021-10-29", new SimpleEntry<>(-100.0, 4.5));
    Map<String, SimpleEntry<Double, Double>> appleTuple = new HashMap<>();
    appleTuple.put("2012-01-01", new SimpleEntry<>(10.0, 1.0));
    appleTuple.put("2015-01-01", new SimpleEntry<>(10.0, 2.0));
    appleTuple.put("2017-01-01", new SimpleEntry<>(-6.0, 2.3));
    appleTuple.put("2018-01-01", new SimpleEntry<>(-10.0, 5.5));
    Map<String, SimpleEntry<Double, Double>> amazonTuple = new HashMap<>();
    amazonTuple.put("2011-02-01", new SimpleEntry<>(130.0, 1.0));
    amazonTuple.put("2020-01-01", new SimpleEntry<>(-100.0, 2.0));
    perfTestOrder.put("CSCO", ciscoTuple);
    perfTestOrder.put("GOOGL", googleTuple);
    perfTestOrder.put("AMZN", amazonTuple);
    perfTestOrder.put("AAPL", appleTuple);
    return perfTestOrder;
  }


  @Test
  public void testPortfolioFlexImplCreatorConst() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(order, "flexTest");
    assertNotNull(p);
  }

  @Test(expected = Exception.class)
  public void testPortfolioFlexImplCreatorConstFailure1() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(null, "flexTest");
  }

  @Test(expected = Exception.class)
  public void testPortfolioFlexImplCreatorConstFailure2() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(order, null);
  }

  //  @Test
  //  public void testPortfolioFlexImplRetreiverConst() throws Exception {
  //    PortfolioFlex p = new PortfolioFlexImpl("flexTest");
  //    assertTrue(1>0);
  //  }

  @Test(expected = Exception.class)
  public void testPortfolioFlexImplRetreiverConstFailure() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(null);
  }


  @Test
  public void testGetLatestState() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(order, "flexTest");
    Map<String, SimpleEntry<String, Double>> stateMap = p.getLatestState();

    Map<String, SimpleEntry<String, Double>> expected = new HashMap<>();
    SimpleEntry<String, Double> cscoEntry = new SimpleEntry<String, Double>("2022-10-31", 12.0);
    expected.put("CSCO", cscoEntry);
    SimpleEntry<String, Double> nflxEntry = new SimpleEntry<String, Double>("2022-10-31", 10.0);
    expected.put("NFLX", nflxEntry);
    SimpleEntry<String, Double> aaplEntry = new SimpleEntry<String, Double>("2022-10-31", 9.0);
    expected.put("AAPL", aaplEntry);

    for (String key : stateMap.keySet()) {
      assertTrue(expected.containsKey(key));
      assertEquals(expected.get(key).getKey(), stateMap.get(key).getKey());
      assertEquals(expected.get(key).getValue(), stateMap.get(key).getValue());
    }
  }


  @Test
  public void testGetValueOnDateBeforePortfolioCreation() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(order, "flexTest");
    String date = "2012-10-01";
    double res = p.getValueOnDate(date);
    assertEquals(0.0, res, 0);
  }


  @Test
  public void testGetValueOnDateAfterPortfolioCreationBeforeLastTrans() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(order, "flexTest");
    String date = "2022-10-31";
    double res = p.getValueOnDate(date);
    assertEquals(4844.02, res, 0);
  }


  @Test
  public void testGetValueOnDateAfterPortfolioCreationAfterLastTrans() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(order, "flexTest");
    String date = "2022-11-01";
    double res = p.getValueOnDate(date);
    assertEquals(4768.870000000001, res, 0);
  }

  @Test
  public void testGetPortfolioSummaryOnLastTransDate() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(order, "flexTest");
    String date = "2022-10-31";
    Map<String, Double> res = p.getPortfolioSummary(date);
    Map<String, Double> expected = new HashMap<>();
    expected.put("NFLX", 10.0);
    expected.put("AAPL", 9.0);
    expected.put("CSCO", 12.0);

    for (String k : res.keySet()) {
      assertTrue(expected.containsKey(k));
      assertEquals(expected.get(k), res.get(k));
    }
  }


  @Test
  public void testGetPortfolioSummaryBeforeLastTransDate() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl("flexTest");
    String date = "2022-10-29";
    Map<String, Double> res = p.getPortfolioSummary(date);
    Map<String, Double> expected = new HashMap<>();
    expected.put("NFLX", 8.0);
    expected.put("AAPL", 7.0);
    expected.put("CSCO", 22.0);

    for (String k : res.keySet()) {
      assertTrue(expected.containsKey(k));
      assertEquals(expected.get(k), res.get(k));
    }
  }


  @Test
  public void testAddBuyTransaction() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(order, "flexTest");
    SimpleEntry<Double, Double> commQtyTuple = new SimpleEntry<>(12.0, 2.0);
    SimpleEntry<String, SimpleEntry<Double, Double>> dateQtyCommTuple = new SimpleEntry<>(
        "2022-11-2", commQtyTuple);
    SimpleEntry<String, SimpleEntry<String, SimpleEntry<Double, Double>>> transTuple =
        new SimpleEntry<>(
            "CSCO", dateQtyCommTuple);
    p.addTransaction(transTuple, LocalDate.now().toString());

    Map<String, SimpleEntry<String, Double>> stateMap = p.getLatestState();

    Map<String, SimpleEntry<String, Double>> expected = new HashMap<>();
    SimpleEntry<String, Double> cscoEntry = new SimpleEntry<String, Double>("2022-11-2", 24.0);
    expected.put("CSCO", cscoEntry);
    SimpleEntry<String, Double> nflxEntry = new SimpleEntry<String, Double>("2022-10-31", 10.0);
    expected.put("NFLX", nflxEntry);
    SimpleEntry<String, Double> aaplEntry = new SimpleEntry<String, Double>("2022-10-31", 9.0);
    expected.put("AAPL", aaplEntry);

    for (String key : stateMap.keySet()) {
      assertTrue(expected.containsKey(key));
      assertEquals(expected.get(key).getKey(), stateMap.get(key).getKey());
      assertEquals(expected.get(key).getValue(), stateMap.get(key).getValue());
    }
  }


  @Test(expected = IllegalArgumentException.class)
  public void testAddBuyTransactionNegCommision() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(order, "flexTest");
    SimpleEntry<Double, Double> commQtyTuple = new SimpleEntry<>(12.0, -2.0);
    SimpleEntry<String, SimpleEntry<Double, Double>> dateQtyCommTuple = new SimpleEntry<>(
        "2022-11-2", commQtyTuple);
    SimpleEntry<String, SimpleEntry<String, SimpleEntry<Double, Double>>> transTuple =
        new SimpleEntry<>(
            "CSCO", dateQtyCommTuple);
    p.addTransaction(transTuple, LocalDate.now().toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddBuyTransactionIncorrectDate() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl("flexTest");
    SimpleEntry<Double, Double> commQtyTuple = new SimpleEntry<>(12.0, 2.0);
    SimpleEntry<String, SimpleEntry<Double, Double>> dateQtyCommTuple = new SimpleEntry<>(
        "02-11-2022", commQtyTuple);
    SimpleEntry<String, SimpleEntry<String, SimpleEntry<Double, Double>>> transTuple =
        new SimpleEntry<>(
            "CSCO", dateQtyCommTuple);
    p.addTransaction(transTuple, LocalDate.now().toString());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testAddBuyTransactionNulLArgs() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl("flexTest");
    p.addTransaction(null, LocalDate.now().toString());
  }

  @Test
  public void testAddSellTransaction() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(order, "flexTest");
    SimpleEntry<Double, Double> commQtyTuple = new SimpleEntry<>(-12.0, 2.0);
    SimpleEntry<String, SimpleEntry<Double, Double>> dateQtyCommTuple = new SimpleEntry<>(
        "2022-11-2", commQtyTuple);
    SimpleEntry<String, SimpleEntry<String, SimpleEntry<Double, Double>>> transTuple =
        new SimpleEntry<>(
            "CSCO", dateQtyCommTuple);
    p.addTransaction(transTuple, LocalDate.now().toString());
    Map<String, SimpleEntry<String, Double>> stateMap = p.getLatestState();

    Map<String, SimpleEntry<String, Double>> expected = new HashMap<>();
    SimpleEntry<String, Double> cscoEntry = new SimpleEntry<String, Double>("2022-11-2", 0.0);
    expected.put("CSCO", cscoEntry);
    SimpleEntry<String, Double> nflxEntry = new SimpleEntry<String, Double>("2022-10-31", 10.0);
    expected.put("NFLX", nflxEntry);
    SimpleEntry<String, Double> aaplEntry = new SimpleEntry<String, Double>("2022-10-31", 9.0);
    expected.put("AAPL", aaplEntry);

    for (String key : stateMap.keySet()) {
      assertTrue(expected.containsKey(key));
      assertEquals(expected.get(key).getKey(), stateMap.get(key).getKey());
      assertEquals(expected.get(key).getValue(), stateMap.get(key).getValue());
    }
  }

  @Test
  public void testGetCostBasisOnLastTransDate() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(order, "flexTest");
    String date = "2022-10-31";
    assertEquals(6002.72, p.getCostBasis(date), 0);
  }

  @Test
  public void testGetCostBasisBeyondLastTransDate() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(order, "flexTest");
    String date = "2022-11-06";
    assertEquals(6002.72, p.getCostBasis(date), 0);
  }

  @Test
  public void testGetCostBasisBeforeLastTransDate() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(order, "flexTest");
    String date = "2022-10-22";
    assertEquals(1120.0, p.getCostBasis(date), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCostBasisInvalidDate() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(order, "flexTest");
    String date = "06-11-2022";
    p.getCostBasis(date);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCostBasisNullDate() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(order, "flexTest");
    p.getCostBasis(null);
  }


  @Test
  public void testGetPerfDataOverTimePrintWeekly() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(this.generatePerfTestPortfolioOrder(),
        "perfTest");
    SimpleEntry<List<String>, List<Double>> ans = p.getPerfDataOverTime("2019-09-30", "2019-11-01");

    String[] expectedLabels = {"OCT Week 1", "OCT Week 2",
        "OCT Week 3", "OCT Week 4",
        "NOV Week 1", "NOV Week 1"};
    double[] expectedDatapoints = {373898.38, 373469.56000000006,
        380245.86, 383176.02,
        388085.44, 388085.44};

    int index = 0;
    for (String s : ans.getKey()) {
      assertEquals(expectedLabels[index], s);
      index++;
    }
    index = 0;
    for (double s : ans.getValue()) {
      assertEquals(expectedDatapoints[index], s, 0);
      index++;
    }
  }

  @Test
  public void testGetPerfDataOverTimePrintDaily() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(this.generatePerfTestPortfolioOrder(),
        "perfTest");
    SimpleEntry<List<String>, List<Double>> ans = p.getPerfDataOverTime("2019-09-30", "2019-10-30");

    String[] expectedLabels = {
        "2019-09-30", "2019-10-01",
        "2019-10-02", "2019-10-03",
        "2019-10-04", "2019-10-05",
        "2019-10-06", "2019-10-07",
        "2019-10-08", "2019-10-09",
        "2019-10-10", "2019-10-11",
        "2019-10-12", "2019-10-13",
        "2019-10-14", "2019-10-15",
        "2019-10-16", "2019-10-17",
        "2019-10-18", "2019-10-19",
        "2019-10-20", "2019-10-21",
        "2019-10-22", "2019-10-23",
        "2019-10-24", "2019-10-25",
        "2019-10-26", "2019-10-27",
        "2019-10-28", "2019-10-29",
        "2019-10-30"
    };
    int index = 0;
    for (String s : ans.getKey()) {
      assertEquals(expectedLabels[index], s);
      index++;
    }
    double[] expectedDatapoints = {
        374682.10000000003, 372780.54,
        366436.06000000006, 369295.4,
        373898.38, 373898.38,
        373898.38, 372672.68000000005,
        366913.98, 370553.7,
        371167.36, 373469.56000000006,
        373469.56000000006, 373469.56000000006,
        374285.38, 381253.0,
        382660.66000000003, 385154.48,
        380245.86, 380245.86,
        380245.86, 383927.92000000004,
        380970.33999999997, 382476.02,
        385054.04, 383176.02,
        383176.02, 383176.02,
        388203.64, 382916.34,
        385177.66000000003
    };
    index = 0;
    for (double s : ans.getValue()) {
      assertEquals(expectedDatapoints[index], s, 0);
      index++;
    }
  }


  @Test
  public void testGetPerfDataOverTimePrintMonthly() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(this.generatePerfTestPortfolioOrder(), "perfTest");
    SimpleEntry<List<String>, List<Double>> ans = p.getPerfDataOverTime("2018-09-30", "2019-11-01");

    String[] expectedLabels = {"SEP 2018", "OCT 2018",
        "NOV 2018", "DEC 2018",
        "JAN 2019", "FEB 2019",
        "MAR 2019", "APR 2019", "MAY 2019", "JUN 2019",
        "JUL 2019", "AUG 2019", "SEP 2019", "OCT 2019",
        "NOV 2019"};
    double[] expectedDatapoints = {411591.36, 330741.95999999996,
        358955.86, 323159.36, 351070.42, 350713.14,
        373679.4, 409477.66000000003, 377921.78,
        378650.94, 404662.81999999995, 372524.45999999996,
        373860.66000000003, 388203.64, 388085.44};

    int index = 0;
    for (String s : ans.getKey()) {
      assertEquals(expectedLabels[index], s);
      index++;
    }
    index = 0;
    for (double s : ans.getValue()) {
      assertEquals(expectedDatapoints[index], s, 0);
      index++;
    }
  }

  @Test
  public void testGetPerfDataOverTimePrintYearly() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(this.generatePerfTestPortfolioOrder(),
        "perfTest");
    SimpleEntry<List<String>, List<Double>> ans = p.getPerfDataOverTime("2011-09-30", "2022-11-01");
    String[] expectedLabels = {"2011", "2012", "2013",
        "2014", "2015", "2016",
        "2017", "2018", "2019",
        "2020", "2021", "2022"};
    double[] expectedDatapoints = {22503.0,
        37934.829000000005,
        59965.060000000005,
        44564.58, 93012.26,
        198278.14, 285097.92000000004,
        326135.22000000003, 403655.32,
        309987.46, 160709.12,
        15862.42};
    int index = 0;
    for (String s : ans.getKey()) {
      assertEquals(expectedLabels[index], s);
      index++;
    }
    index = 0;
    for (double s : ans.getValue()) {
      assertEquals(expectedDatapoints[index], s, 0);
      index++;
    }
  }

  @Test
  public void testGetPerfDataOverTimePrintQtrly() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(this.generatePerfTestPortfolioOrder(),
        "perfTest");
    SimpleEntry<List<String>, List<Double>> ans = p.getPerfDataOverTime("2019-01-01", "2021-12-01");

    String[] expectedLabels = {"Qtr1 2019",
        "Qtr2 2019", "Qtr3 2019",
        "Qtr4 2019", "Qtr1 2020",
        "Qtr2 2020", "Qtr3 2020",
        "Qtr4 2020", "Qtr1 2021",
        "Qtr2 2021", "Qtr3 2021",
        "Qtr4 2021"};
    double[] expectedDatapoints = {375211.78, 378650.94,
        374682.10000000003, 403549.86,
        198785.17999999996,
        255882.28000000003,
        272057.62, 308883.74,
        339371.34, 398463.44,
        421681.36000000004,
        162152.24};

    int index = 0;
    for (String s : ans.getKey()) {
      assertEquals(expectedLabels[index], s);
      index++;
    }
    index = 0;
    for (double s : ans.getValue()) {
      assertEquals(expectedDatapoints[index], s, 0);
      index++;
    }
  }

}