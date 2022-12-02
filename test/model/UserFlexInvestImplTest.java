package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit class to test UserFlexInvestImpl class.
 */
public class UserFlexInvestImplTest {

  UserFlexInvest user;

  @Before
  public void init() {
    user = new UserFlexInvestImpl();
  }

  @Test
  public void testConstructor() throws Exception {

    Map<String, Double> stockMap = new HashMap<>();
    stockMap.put("AAPL", 20.0);
    stockMap.put("CSCO", 20.0);
    stockMap.put("INTU", 20.0);
    stockMap.put("TSLA", 20.0);
    stockMap.put("MSFT", 20.0);
    user.addPortfolio("flexInvestUser2", 1000.0, stockMap, "2015-01-01",
        "2017-01-01", 30, 2.9);
  }

  @Test
  public void testConstructor2() throws Exception {
    Map<String, Double> stockMap = new HashMap<>();
    stockMap.put("AAPL", 20.0);
    stockMap.put("CSCO", 20.0);
    stockMap.put("INTU", 20.0);
    stockMap.put("TSLA", 20.0);
    stockMap.put("MSFT", 20.0);
    user.addPortfolio("flexInvestUser", 1000.0, stockMap, "2022-01-01",
        "2025-01-01", 30, 2.9);
  }

  @Test
  public void testInvest() throws Exception {
    Map<String, Double> stockMap = new HashMap<>();
    stockMap.put("SBUX", 25.0);
    stockMap.put("SGEN", 25.0);
    stockMap.put("REGN", 25.0);
    stockMap.put("QCOM", 25.0);
    user.investMoney("flexInvestUser", 1000.0, stockMap, "2022-07-30", 4.5);
  }

  @Test
  public void testInvest2() throws Exception {
    Map<String, Double> stockMap = new HashMap<>();
    stockMap.put("SBUX", 25.0);
    stockMap.put("SGEN", 25.0);
    stockMap.put("REGN", 25.0);
    stockMap.put("QCOM", 25.0);
    user.investMoney("flexInvestUser2", 1000.0, stockMap, "2022-07-30", 4.5);
  }

  @Test
  public void testCostBasis() throws Exception {
    Double val = user.getCostBasis("flexInvestUser", "2022-02-02");
    assertEquals(2005.800, val, 0.01);
    val = user.getCostBasis("flexInvestUser", "2020-06-06");
    assertEquals(0, val, 0.01);
    val = user.getCostBasis("flexInvestUser", "2022-06-06");
    assertEquals(6017.3999, val, 0.01);
    val = user.getCostBasis("flexInvestUser", "2022-08-08");
    assertEquals(9027.69999, val, 0.01);
    val = user.getCostBasis("flexInvestUser", "2022-10-10");
    assertEquals(11033.499, val, 0.01);
  }

  @Test
  public void testCostBasis2() throws Exception {
    Double val = user.getCostBasis("flexInvestUser2", "2016-01-01");
    assertEquals(13037.699, val, 0.01);
    val = user.getCostBasis("flexInvestUser2", "2014-01-01");
    assertEquals(0, val, 0.01);
    val = user.getCostBasis("flexInvestUser2", "2015-02-01");
    assertEquals(1002.9, val, 0.01);
    val = user.getCostBasis("flexInvestUser2", "2016-01-01");
    assertEquals(13037.699, val, 0.01);
    val = user.getCostBasis("flexInvestUser2", "2019-01-01");
    assertEquals(25072.5000, val, 0.01);
    val = user.getCostBasis("flexInvestUser2", "2022-10-10");
    assertEquals(26077.000, val, 0.01);
  }

  @Test
  public void testValue() throws Exception {
    Double val = user.getPortfolioValue("flexInvestUser", "2022-02-02");
    assertEquals(1887.94, val, 0.01);
    val = user.getPortfolioValue("flexInvestUser", "2020-06-06");
    assertEquals(0.0, val, 0.01);
    val = user.getPortfolioValue("flexInvestUser", "2022-06-06");
    assertEquals(5055.05, val, 0.01);
    val = user.getPortfolioValue("flexInvestUser", "2022-08-08");
    assertEquals(8776.77, val, 0.01);
    val = user.getPortfolioValue("flexInvestUser", "2022-10-10");
    assertEquals(8350.955, val, 0.01);
  }

  @Test
  public void testValue2() throws Exception {
    Double val = user.getPortfolioValue("flexInvestUser2", "2016-01-01");
    assertEquals(13284.46, val, 0.01);
    val = user.getPortfolioValue("flexInvestUser2", "2014-01-01");
    assertEquals(0, val, 0.01);
    val = user.getPortfolioValue("flexInvestUser2", "2015-02-01");
    assertEquals(954.14, val, 0.01);
    val = user.getPortfolioValue("flexInvestUser2", "2016-01-01");
    assertEquals(13284.469, val, 0.01);
    val = user.getPortfolioValue("flexInvestUser2", "2019-01-01");
    assertEquals(42136.82, val, 0.01);
    val = user.getPortfolioValue("flexInvestUser2", "2022-10-10");
    assertEquals(61289.939, val, 0.01);
  }

  @Test
  public void testGetGraphData() throws Exception {
    SimpleEntry<List<String>, List<Double>> data =
        user.getGraphDataGUI("2015-02-01", "2018-01-01", "flexInvestUser2");
    assertNotNull(data.getKey());
    assertNotNull(data.getValue());

  }

}