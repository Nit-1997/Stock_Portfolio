package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import constants.Constants;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit class to test UserImpl Class.
 */
public class UserImplTest {

  User user;

  @Before
  public void init() {
    user = new UserImpl();
  }

  @Test
  public void testUserConstructor() {
    String folder = "portfolios";
    String portfolioDirectory = Paths.get(folder).toAbsolutePath().toString();
    File f = new File(portfolioDirectory);
    assertTrue(f.exists());
  }

  @Test
  public void testUserConstructor2() {
    String folder = "stock_data";
    String portfolioDirectory = Paths.get(folder).toAbsolutePath().toString();
    File f = new File(portfolioDirectory);
    assertTrue(f.exists());
  }

  @Test
  public void testUserConstructor3() {
    assertEquals(20, Constants.STOCK_NAMES.size());
  }

  @Test
  public void testAddPortfolio() {
    boolean val = user.addPortfolio(null, null);
    assertFalse(val);
  }

  @Test
  public void testAddPortfolio2() {
    Map<String, Double> stocks = new HashMap<>();
    stocks.put("GOOG", 10.0);
    stocks.put("MSFT", 10.0);
    boolean val = user.addPortfolio("test", stocks);
    assertTrue(val);
  }

  @Test
  public void testAddPortfolio3() {
    Map<String, Double> stocks = new HashMap<>();
    stocks.put("abcd", 10.0);
    stocks.put("MSFT", 10.0);
    boolean val = user.addPortfolio("test", stocks);
    assertFalse(val);
  }

  @Test
  public void testGetPortfolios() {
    String portfolioDirectory = Paths.get("portfolios").toAbsolutePath().toString();
    File f = new File(portfolioDirectory);
    assertEquals(f.list((f1, name) -> name.endsWith(".csv")).length, user.getPortfolios().size());
  }

  @Test
  public void testGetPortfolioSummary() {
    Map<String, Double> map = user.getPortfolioSummary("test");
    Map<String, Double> testMap = new HashMap<>();
    testMap.put("GOOG", 10.0);
    testMap.put("MSFT", 10.0);
    for (String key : map.keySet()) {
      assertEquals(map.get(key), testMap.get(key));
    }
  }

  @Test
  public void testGetPortfolioSummaryNull() {
    Map<String, Double> map = user.getPortfolioSummary("hbv");
    assertNull(map);
  }

  @Test
  public void testGetPortfolioDetailed() {
    Map<String, List<Double>> map = user.getPortfolioDetailed("test", "2022-11-01");
    for (String key : map.keySet()) {
      for (double val : map.get(key)) {
        assertNotNull(val);
      }
    }
  }

  @Test
  public void testGetPortfolioDetailedWrongName() {
    Map<String, List<Double>> map = user.getPortfolioDetailed("jhdbj", "2022-11-01");
    assertNull(map);
  }

  @Test
  public void testGetPortfolioDetailedWrongDate() {
    Map<String, List<Double>> map = user.getPortfolioDetailed("test", "2022-11-06");
    assertNull(map);
  }

  @Test
  public void testGetPortfolioValueCurrent() {
    String currentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
    double val = user.getPortfolioValue("test", currentDate);
    assertNotNull(val);
  }

  @Test
  public void testGetPortfolioValueHistoric() {
    Double val = user.getPortfolioValue("test", "2022-05-24");
    assertNotNull(val);
  }

  @Test
  public void testGetPortfolioValueWrongFileName() {
    Double val = user.getPortfolioValue("jgevbh", "2022-05-24");
    assertNull(val);
  }

  @Test
  public void testGetPortfolioValueWrongDate() {
    Double val = user.getPortfolioValue("test", "2023-05-24");
    assertNull(val);
  }


  @Test
  public void testGetPortfolioPnLCurrent() {
    String currentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
    Double val = user.getPortfolioPnL("test", currentDate);
    assertNotNull(val);
  }

  @Test
  public void testGetPortfolioPnLHistoric() {
    Double val = user.getPortfolioPnL("test", "2022-05-24");
    assertNotNull(val);
  }

  @Test
  public void testGetPortfolioPnLWrongFileName() {
    Double val = user.getPortfolioPnL("jgevbh", "2022-05-24");
    assertNull(val);
  }

  @Test
  public void testGetPortfolioPnLWrongDate() {
    Double val = user.getPortfolioPnL("test", "2023-05-24");
    assertNull(val);
  }

  @Test
  public void testIsUniqueNameTrue() {
    assertTrue(user.isUniqueName("tester"));
  }

  @Test
  public void testIsUniqueNameFalse() {
    assertFalse(user.isUniqueName("test"));
  }

  @Test
  public void testCleanStockDirectoryNotEmptyDirectory() throws IOException {
    Path portfolioDirectory = Paths.get("stock_data").toAbsolutePath();
    new File(portfolioDirectory + File.separator + "a.txt").createNewFile();
    new File(portfolioDirectory + File.separator + "b.txt").createNewFile();
    user.cleanStockDirectory();
    File[] list = new File(portfolioDirectory.toString()).listFiles();
    assertEquals(0, list.length);
  }

  @Test
  public void testClenStockDirectoryEmptyDirectory() {
    Path portfolioDirectory = Paths.get("stock_data").toAbsolutePath();
    user.cleanStockDirectory();
    File[] list = new File(portfolioDirectory.toString()).listFiles();
    assertEquals(0, list.length);
  }

  @Test
  public void testCleanStockDirectoryNoDirectory() {
    Path portfolioDirectory = Paths.get("stock_data").toAbsolutePath();
    File directory = new File(portfolioDirectory.toString());
    for (File file : directory.listFiles()) {
      file.delete();
    }
    directory.delete();
    assertFalse(directory.exists());
    user.cleanStockDirectory();
    assertTrue(directory.exists());
  }

  @Test
  public void testGetStockList() {
    assertEquals(Constants.STOCK_NAMES, user.getStockList());
    assertEquals(Constants.STOCK_NAMES.size(), user.getStockList().size());
  }

  @Test
  public void testIsValidStockTrue() {
    assertTrue(user.isValidStock("GOOG"));
  }

  @Test
  public void testIsValidStockFalse() {
    assertFalse(user.isValidStock("abcdkj"));
  }


  @Test
  public void testDateCheckerPositive() {
    assertTrue(user.dateChecker("2022-01-01"));
  }

  @Test
  public void testDateCheckerBeforeStartingDate() {
    assertFalse(user.dateChecker("2009-01-01"));
  }

  @Test
  public void testDateCheckerBeforeAfterDate() {
    assertFalse(user.dateChecker("2009-11-04"));
  }

  @Test
  public void testDateCheckerBeforeWrongFormat() {
    assertFalse(user.dateChecker("02-26-2022"));
  }

  @Test
  public void testDateCheckerBeforeWrongFormat2() {
    assertFalse(user.dateChecker("02/26/2022"));
  }


}