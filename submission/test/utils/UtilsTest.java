package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import constants.Constants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.DataSource;
import model.DataSourceImpl;
import model.StockOrder;
import model.StockOrderImpl;

import org.junit.Test;

/**
 * Junit class to test Utils.
 */
public class UtilsTest {

  @Test
  public void testLoadStockNamesCorrect() throws IOException {
    Set<String> s = Utils.loadStockNames("testingArtifacts", "stocks_list_correct.csv");
    assertEquals(Constants.TOTAL_HANDLED_STOCKS, s.size());
  }

  @Test(expected = IOException.class)
  public void testLoadStockNamesEmpty() throws IOException {
    Set<String> s = Utils.loadStockNames("testingArtifacts",
            "stocks_list.csv");
  }

  @Test(expected = IOException.class)
  public void testLoadStockNamesFileNotFound() throws IOException {
    Set<String> s = Utils.loadStockNames("testingArtifacts", "c.csv");
  }

  @Test(expected = IOException.class)
  public void testLoadStockNamesDirectoryNotFound() throws IOException {
    Set<String> s = Utils.loadStockNames("kela", "c.csv");
  }

  @Test
  public void testLoadPortfolioDataWorking() throws Exception {
    Constants.STOCK_NAMES = Utils.loadStockNames("testingArtifacts",
            "stocks_list_correct.csv");
    List<StockOrder> s = Utils.loadPortfolioData("tech_working",
            "testingArtifacts");
    assertEquals(2, s.size());
  }

  @Test
  public void testLoadPortfolioIncorrectTicker() throws Exception {
    Constants.STOCK_NAMES = Utils.loadStockNames("testingArtifacts",
            "stocks_list_correct.csv");
    List<StockOrder> s = Utils.loadPortfolioData("error_incorrect_ticker",
            "testingArtifacts");
    assertNull(s);
  }

  @Test
  public void testLoadPortfolioLessCols() throws Exception {
    Constants.STOCK_NAMES = Utils.loadStockNames("testingArtifacts",
            "stocks_list_correct.csv");
    List<StockOrder> s = Utils.loadPortfolioData("error_less_column",
            "testingArtifacts");
    assertNull(s);
  }

  @Test
  public void testLoadPortfolioQtyFractional() throws Exception {
    Constants.STOCK_NAMES = Utils.loadStockNames("testingArtifacts",
            "stocks_list_correct.csv");
    List<StockOrder> s = Utils.loadPortfolioData("error_quantity_fractional",
            "testingArtifacts");
    assertNull(s);
  }

  @Test
  public void testLoadPortfolioQtyNegative() throws Exception {
    Constants.STOCK_NAMES = Utils.loadStockNames("testingArtifacts",
            "stocks_list_correct.csv");
    List<StockOrder> s = Utils.loadPortfolioData("error_quantity_negative",
            "testingArtifacts");
    assertNull(s);
  }

  @Test
  public void testLoadPortfolioQtyInvalid() throws Exception {
    Constants.STOCK_NAMES = Utils.loadStockNames("testingArtifacts",
            "stocks_list_correct.csv");
    List<StockOrder> s = Utils.loadPortfolioData("error_quantity_Invalid",
            "testingArtifacts");
    assertNull(s);
  }

  @Test
  public void testLoadPortfolioIncorrectBuyPrice() throws Exception {
    Constants.STOCK_NAMES = Utils.loadStockNames("testingArtifacts",
            "stocks_list_correct.csv");
    List<StockOrder> s = Utils.loadPortfolioData("incorrect_buyPrice",
            "testingArtifacts");
    assertNull(s);
  }

  @Test
  public void testLoadPortfolioNegativeBuyPrice() throws Exception {
    Constants.STOCK_NAMES = Utils.loadStockNames("testingArtifacts",
            "stocks_list_correct.csv");
    List<StockOrder> s = Utils.loadPortfolioData("negative_buyPrice",
            "testingArtifacts");
    assertNull(s);
  }

  @Test
  public void testLoadPortfolioInvalidDate() throws Exception {
    Constants.STOCK_NAMES = Utils.loadStockNames("testingArtifacts",
            "stocks_list_correct.csv");
    List<StockOrder> s = Utils.loadPortfolioData("invalid_date",
            "testingArtifacts");
    assertNull(s);
  }

  @Test
  public void testLoadPortfolioDateBeforeData() throws Exception {
    Constants.STOCK_NAMES = Utils.loadStockNames("testingArtifacts",
            "stocks_list_correct.csv");
    List<StockOrder> s = Utils.loadPortfolioData("date_before_data",
            "testingArtifacts");
    assertNull(s);
  }

  @Test
  public void testLoadPortfolioDateAfterData() throws Exception {
    Constants.STOCK_NAMES = Utils.loadStockNames("testingArtifacts",
            "stocks_list_correct.csv");
    List<StockOrder> s = Utils.loadPortfolioData("date_after_data",
            "testingArtifacts");
    assertNull(s);
  }

  @Test
  public void testStockValueByDateValidTicker() throws Exception {
    Utils.loadStockData("CSCO", "stock_data");
    String v = Utils.fetchStockValueByDate("CSCO", "2022-10-28",
            "stock_data");
    assertNotEquals("-1", v);
  }

  @Test(expected = Exception.class)
  public void testStockValueByDateInvalidDate() throws Exception {
    Utils.loadStockData("CSCO", "stock_data");
    String v = Utils.fetchStockValueByDate("CSCO", "202cx-10-28", "stock_data");
    assertNull(v);
  }

  @Test
  public void testFetchStockValueByDateFutureFoundExact() throws Exception {
    if (!Utils.dataExists("CSCO", "stock_data")) {
      Utils.loadStockData("CSCO", "stock_data");
    }
    AbstractMap.SimpleEntry<String, Double> res = Utils.fetchStockValueByDateFuture("CSCO",
            "2022-11-25", "stock_data");
    assertEquals("2022-11-25", res.getKey());
    assertEquals(48.4, res.getValue(), 0.1);
  }

  @Test
  public void testFetchStockValueByDateFutureNotFound() throws Exception {
    if (!Utils.dataExists("CSCO", "stock_data")) {
      Utils.loadStockData("CSCO", "stock_data");
    }
    AbstractMap.SimpleEntry<String, Double> res = Utils.fetchStockValueByDateFuture("CSCO",
            "2022-11-27", "stock_data");
    assertEquals("2022-11-28", res.getKey());
    assertEquals(48.08, res.getValue(), 0.1);
  }

  @Test
  public void testFetchStockValueByDateFutureFoundPrev() throws Exception {
    if (!Utils.dataExists("CSCO", "stock_data")) {
      Utils.loadStockData("CSCO", "stock_data");
    }
    AbstractMap.SimpleEntry<String, Double> res = Utils.fetchStockValueByDateFuture("CSCO",
            "2022-11-19", "stock_data");
    assertEquals("2022-11-21", res.getKey());
    assertEquals(47.62, res.getValue(), 0.1);
  }


  @Test
  public void testDateCheckerPositive() {
    assertTrue(Utils.dateChecker("2022-01-01"));
  }

  @Test
  public void testDateCheckerBeforeStartingDate() {
    assertFalse(Utils.dateChecker("2009-01-01"));
  }

  @Test
  public void testDateCheckerBeforeAfterDate() {
    assertFalse(Utils.dateChecker("2009-11-04"));
  }

  @Test
  public void testDateCheckerBeforeWrongFormat() {
    assertFalse(Utils.dateChecker("02-26-2022"));
  }

  @Test
  public void testDateCheckerBeforeWrongFormat2() {
    assertFalse(Utils.dateChecker("02/26/2022"));
  }

  @Test
  public void testClearStockDirectoryNotEmptyDirectory() throws IOException {
    Path portfolioDirectory = Paths.get("stock_data").toAbsolutePath();
    new File(portfolioDirectory + File.separator + "a.txt").createNewFile();
    new File(portfolioDirectory + File.separator + "b.txt").createNewFile();
    Utils.clearStockDirectory();
    File[] list = new File(portfolioDirectory.toString()).listFiles();
    assertEquals(0, list.length);
  }

  @Test
  public void testClearStockDirectoryEmptyDirectory() {
    Path portfolioDirectory = Paths.get("stock_data").toAbsolutePath();
    Utils.clearStockDirectory();
    File[] list = new File(portfolioDirectory.toString()).listFiles();
    assertEquals(0, list.length);
  }

  @Test
  public void testClearStockDirectoryNoDirectory() {
    Path portfolioDirectory = Paths.get("stock_data").toAbsolutePath();
    File directory = new File(portfolioDirectory.toString());
    for (File file : directory.listFiles()) {
      file.delete();
    }
    directory.delete();
    assertFalse(directory.exists());
    Utils.clearStockDirectory();
    assertTrue(directory.exists());
  }


  @Test
  public void testCompareDates() throws Exception {
    int ans = Utils.compareDates("2019-04-15", "2019-08-10");
    assertEquals(-1, ans);
    ans = Utils.compareDates("2019-09-15", "2019-08-10");
    assertEquals(1, ans);
    ans = Utils.compareDates("2019-09-15", "2019-09-15");
    assertEquals(0, ans);
  }

  @Test(expected = Exception.class)
  public void testCompareDatesIllegal() throws Exception {
    int ans = Utils.compareDates("hello", "2019-08-10");
  }

  @Test
  public void test() {
    String currentDate = String.valueOf(java.time.LocalDate.now());
    System.out.println(currentDate);
    assertNotNull(currentDate);
  }

  private List<StockOrder> buildStockOrders() {
    List<StockOrder> stockOrders = new ArrayList<>();
    String[] ticker = {"CSCO", "GOOGL", "AAPL"};
    String[] dates = {"2019-08-10", "2018-08-10", "2017-08-10"};
    for (int i = 0; i < ticker.length; i++) {
      StockOrder newOrder = new StockOrderImpl(ticker[i], 44.2, dates[i], 10.0,
              2.0);
      stockOrders.add(newOrder);
    }
    return stockOrders;
  }


  @Test
  public void testLoadPortfolioWithDCA() throws Exception {
    List<StockOrder> stockOrders = buildStockOrders();
    DataSource ds = new DataSourceImpl();
    String portfolioName = "dcaTesting";
    File dcaTestingFile = ds.getFileByName(portfolioName + "_DCA", "portfolios" +
            File.separator + "flex");
    String dcaContent = "2012-08-10,365,2022-08-10,400,3.0,2\n"
            + "AMZN,50\n"
            + "MFST,50\n"
            + "2012-08-10,365,2022-08-10,400,3.0,2\n"
            + "IBM,50\n"
            + "GOOGL,50\n"
            + "2012-08-10,365,null,400,3.0,2\n"
            + "IBM,50\n"
            + "GOOGL,50";
    ds.writeToFile(dcaTestingFile, dcaContent, false);
    Utils.loadPortfolioWithDCA(dcaTestingFile, stockOrders);

    assertTrue(stockOrders.size() >= 67);
    for (StockOrder s : stockOrders) {
      System.out.println(s.getStock().getStockTickerName() + " , "
              + s.getStock().getBuyPrice() + " , "
              + s.getQuantity() + " , "
              + s.getStock().getBuyDate() + " , "
              + s.getCommFee()
      );
    }
  }

  @Test
  public void testUpdatePortfolioFromDCA() throws Exception {
    List<StockOrder> stockOrders = buildStockOrders();
    String portfolioName = "dcaTesting2";
    String startDate = "2012-08-10";
    String endDate = null;
    int interval = 365;
    Double amount = 200.0;
    Double commFee = 5.0;
    Map<String, Double> weightage = new HashMap<>();
    weightage.put("AMZN", 50.0);
    weightage.put("GOOGL", 50.0);

    Utils.updatePortfolioFromDCA(portfolioName, startDate, endDate,
            weightage, interval, amount, commFee, stockOrders);

    assertTrue(stockOrders.size() > 3);
    for (StockOrder s : stockOrders) {
      System.out.println(s.getStock().getStockTickerName() + " , "
              + s.getStock().getBuyPrice() + " , "
              + s.getQuantity() + " , "
              + s.getStock().getBuyDate() + " , "
              + s.getCommFee()
      );
    }

  }


}