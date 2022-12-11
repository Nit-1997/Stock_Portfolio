package control.gui;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.FileHandler;
import model.Model;
import model.StockAPI;
import model.StockIO;
import view.gui.IView;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the GUI controller.
 */
public class ControllerTest {
  Features controller;
  Model model;

  IView view;

  /**
   * Setup tests.
   *
   * @throws Exception If something goes wrong.
   */
  @Before
  public void setUp() throws Exception {
    this.model = new MockModel();
    this.controller = new Controller(this.model);
    this.view = new MockGui();
    this.controller.setView(this.view);
  }

  /**
   * Test for the invalid date.
   */
  @Test
  public void InvalidDate() {
    this.controller.getPerformance("TechPort", "asdkjasdji", "2022-11-10");
    List<String> expected = Arrays.asList("getPorftolios\n");
    List<String> expected2 = Arrays.asList("addFeatures", "showError", "showError");
    List<String> actual = ((MockModel) this.model).commands;
    List<String> actual2 = ((MockGui) this.view).commands;
    assertEquals(actual, expected);
    assertEquals(actual2, expected2);
  }

  /**
   * Test for an invalid commission fee.
   */
  @Test
  public void invalidCommission() {
    //this.controller.newFlexiblePortInit();
    this.controller.newFlexiblePort("TechPort");
    this.controller.buyStock("TechPort", "AAPL", "5", "2022-10-27", "-5.00");
    String expected = (Arrays.asList("newFlexiblePortfolio(TechPort)\n" +
            ", getFileHandler, toString(TechPort)\n" +
            ", addStock(TechPort, AAPL, 2022-10-27, -5.0)\n" +
            ", getFileHandler, toString(TechPort)\n")).toString();
    List<String> expected2 = Arrays.asList("addFeatures", "showError", "showOuput");
    String actual = (((MockModel) this.model).commands).toString();
    List<String> actual2 = ((MockGui) this.view).commands;
    assertEquals(actual, expected);
    assertEquals(actual2, expected2);
  }


  /**
   * Tests invalid amount.
   */
  @Test
  public void invalidAmt() {
    ArrayList<String> ticker = new ArrayList<String>();
    ArrayList<String> percentage = new ArrayList<String>();
    ticker.add("AAPL");
    ticker.add("XOM");

    percentage.add("40");
    percentage.add("60");
    //this.controller.newFlexiblePortInit();
    this.controller.newFlexiblePort("TechPort");
    this.controller.newFixedStrategy("TechPort", "2022-10-27", percentage, ticker, "5.00",
            "assaddass");
    String expected = (Arrays.asList("newFlexiblePortfolio(TechPort)\n" +
            ", getFileHandler, toString(TechPort)\n")).toString();
    List<String> expected2 = Arrays.asList("addFeatures", "showError", "showError");
    String actual = (((MockModel) this.model).commands).toString();
    List<String> actual2 = ((MockGui) this.view).commands;
    assertEquals(actual, expected);
    assertEquals(actual2, expected2);
  }

  @Test
  public void invalidInterval() {
    ArrayList<String> ticker = new ArrayList<String>();
    ArrayList<String> percentage = new ArrayList<String>();
    ticker.add("AAPL");
    ticker.add("XOM");

    percentage.add("40");
    percentage.add("60");
    this.controller.newFlexiblePortInit();
    this.controller.newFlexiblePort("TechPort");
    this.controller.newDollarCostStrategy("TechPort", "2022-10-27", "dasdsad", percentage, ticker,
            "5.00", "asdsadsa", "2022-11-10");
    String expected = (Arrays.asList("newFlexiblePortfolio(TechPort)\n" +
            ", getFileHandler, toString(TechPort)\n")).toString();
    List<String> expected2 = Arrays.asList("addFeatures", "setUpNewPort", "showError", "showError");
    String actual = (((MockModel) this.model).commands).toString();
    List<String> actual2 = ((MockGui) this.view).commands;
    assertEquals(actual, expected);
    assertEquals(actual2, expected2);
  }


  @Test
  public void InvalidCommissionFee() {
    //this.controller.newFlexiblePortInit();

    this.controller.newFlexiblePort("TechPort");
    this.controller.buyStock("TechPort", "AAPL", "5", "2022-10-27", "-5.00");
    String expected = (Arrays.asList("newFlexiblePortfolio(TechPort)\n" +
            ", getFileHandler, toString(TechPort)\n" +
            ", addStock(TechPort, AAPL, 2022-10-27, -5.0)\n" +
            ", getFileHandler, toString(TechPort)\n")).toString();
    List<String> expected2 = Arrays.asList("addFeatures", "showError", "showOuput");
    String actual = (((MockModel) this.model).commands).toString();
    List<String> actual2 = ((MockGui) this.view).commands;
    assertEquals(actual, expected);
    assertEquals(actual2, expected2);
  }


  /**
   * Test creating a new flexible portfolio.
   */
  @Test
  public void newFlexiblePortInit() {
    this.controller.newFlexiblePortInit();
    List<String> expected = Arrays.asList();
    List<String> expected2 = Arrays.asList("addFeatures", "setUpNewPort");
    List<String> actual = ((MockModel) this.model).commands;
    List<String> actual2 = ((MockGui) this.view).commands;
    assertEquals(actual, expected);
    assertEquals(actual2, expected2);

  }

  /**
   * Test getting the performance.
   */
  @Test
  public void getPerformance() {
    this.controller.getPerformance("TechPort", "2022-10-27", "2022-11-10");
    List<String> expected = Arrays.asList("getPorftolios\n", "getPerformance(TechPort, " +
            "2022-10-27, 2022-11-10)\n");
    List<String> expected2 = Arrays.asList("addFeatures", "showOuput");
    List<String> actual = ((MockModel) this.model).commands;
    List<String> actual2 = ((MockGui) this.view).commands;
    assertEquals(actual, expected);
    assertEquals(actual2, expected2);

  }


  /**
   * Test making a new fixed strategy.
   */
  @Test
  public void newFixedStrategy() {
    ArrayList<String> ticker = new ArrayList<String>();
    ArrayList<String> percentage = new ArrayList<String>();
    ticker.add("AAPL");
    ticker.add("XOM");

    percentage.add("40");
    percentage.add("60");

    this.controller.newFixedStrategy("TechPort", "2022-10-27", percentage, ticker, "4.00", "100");
    List<String> expected = Arrays.asList("newFixedAmountStrategy(TechPort, {AAPL=40.0, " +
            "XOM=60.0}, 2022-10-27, 100.0, 4.0)\n");
    List<String> expected2 = Arrays.asList("addFeatures");
    List<String> actual = ((MockModel) this.model).commands;
    List<String> actual2 = ((MockGui) this.view).commands;
    assertEquals(actual, expected);
    assertEquals(actual2, expected2);

  }

  /**
   * Test making a new dollar cost strategy.
   */
  @Test
  public void newDollarCostStrategy() {
    ArrayList<String> ticker = new ArrayList<String>();
    ArrayList<String> percentage = new ArrayList<String>();
    ticker.add("AAPL");
    ticker.add("XOM");

    percentage.add("40");
    percentage.add("60");

    this.controller.newDollarCostStrategy("TechPort", "2022-10-27", "5", percentage, ticker,
            "4.00", "100", "2022-11-10");
    List<String> expected = Arrays.asList("newDollarCostAverageStrategy(, TechPort, " +
            "{AAPL=40.0, XOM=60.0}, 2022-10-27, 2022-11-10, 5, 100.0, 4.0)\n");
    List<String> expected2 = Arrays.asList("addFeatures");
    List<String> actual = ((MockModel) this.model).commands;
    List<String> actual2 = ((MockGui) this.view).commands;
    assertEquals(actual, expected);
    assertEquals(actual2, expected2);

  }

  /**
   * Test making a new flexible portfolio.
   */
  @Test
  public void newFlexiblePort() {


    this.controller.newFlexiblePort("TechPort");
    List<String> expected = Arrays.asList("newFlexiblePortfolio(TechPort)\n", "getFileHandler",
            "toString(TechPort)\n");
    List<String> expected2 = Arrays.asList("addFeatures", "showError");
    List<String> actual = ((MockModel) this.model).commands;
    List<String> actual2 = ((MockGui) this.view).commands;
    assertEquals(actual, expected);
    assertEquals(actual2, expected2);


  }

  /**
   * Test loading data.
   */
  @Test
  public void loadFileData() {
    this.controller.loadFileData("FLEXTEST.JSON", "TechPort");
    List<String> expected = Arrays.asList("getFileHandler");
    List<String> expected2 = Arrays.asList("addFeatures", "showError");
    List<String> actual = ((MockModel) this.model).commands;
    List<String> actual2 = ((MockGui) this.view).commands;
    assertEquals(actual, expected);
    assertEquals(actual2, expected2);

  }

  /**
   * Test viewing the portfolio.
   */
  @Test
  public void viewPortfolio() {
    this.controller.newFlexiblePort("TechPort");
    this.controller.viewPortfolio("TechPort", "2022-10-27");


    String expected = (Arrays.asList("newFlexiblePortfolio(TechPort)\n", "getFileHandler, toString"
                    +
                    "(TechPort)\n", "getValues(TechPort, 2022-10-27)\n",
            "getTickerMap(TechPort, 2022-10-27)\n",
            "getPortValue(TechPort, 2022-10-27)\n",
            "getCostBasis(TechPort, 2022-10-27)\n")).toString();

    List<String> expected2 = Arrays.asList("addFeatures", "showError", "showOuput");
    String actual = (((MockModel) this.model).commands).toString();
    List<String> actual2 = ((MockGui) this.view).commands;
    assertEquals(actual, expected);
    assertEquals(actual2, expected2);
  }

  /**
   * Test buying stock.
   */
  @Test
  public void buyStock() {
    this.controller.newFlexiblePort("TechPort");
    this.controller.buyStock("TechPort", "AAPL", "5", "2022-10-27", "5.00");


    String expected = (Arrays.asList("newFlexiblePortfolio(TechPort)\n" +
            ", getFileHandler, toString(TechPort)\n" +
            ", addStock(TechPort, AAPL, 2022-10-27, 5.0)\n" +
            ", getFileHandler, toString(TechPort)\n")).toString();

    List<String> expected2 = Arrays.asList("addFeatures", "showError", "showOuput");
    String actual = (((MockModel) this.model).commands).toString();
    List<String> actual2 = ((MockGui) this.view).commands;
    assertEquals(actual, expected);
    assertEquals(actual2, expected2);

  }

  /**
   * Test selling stock.
   */
  @Test
  public void sellStock() {
    this.controller.newFlexiblePort("TechPort");
    this.controller.sellStock("TechPort", "AAPL", "5", "2022-10-27", "5.00");


    String expected = (Arrays.asList("newFlexiblePortfolio(TechPort)\n" +
            ", getFileHandler, toString(TechPort)\n" +
            ", sellStock(TechPort, AAPL, 2022-10-27, 5.0)\n" +
            ", getFileHandler, toString(TechPort)\n")).toString();

    List<String> expected2 = Arrays.asList("addFeatures", "showError", "showOuput");
    String actual = (((MockModel) this.model).commands).toString();
    List<String> actual2 = ((MockGui) this.view).commands;
    assertEquals(actual, expected);
    assertEquals(actual2, expected2);

  }

  // ------------------------ Mock Model ------------------------------------------------------

  /**
   * Mock class for the Model.
   */
  class MockModel implements Model {
    List<String> commands;
    Map<String, Boolean> portfolios;

    /**
     * Constructor.
     */
    public MockModel() {
      this.commands = new ArrayList<>();
      this.portfolios = new HashMap<>();
    }

    @Override
    public Map<String, Boolean> getPortfolios() {
      commands.add("getPorftolios\n");
      return this.portfolios;
    }

    @Override
    public void newInflexiblePortfolio(String name, Map<String, Integer> tickers)
            throws IllegalArgumentException {
      commands.add("newInflexiblePortfolio(" + name + " , " + tickers + ")\n");
      this.portfolios.put(name, false);
    }

    @Override
    public void newFlexiblePortfolio(String name) throws IllegalArgumentException {
      commands.add("newFlexiblePortfolio(" + name + ")\n");
      this.portfolios.put(name, true);
    }

    @Override
    public boolean isFlexible(String portfolioName) throws IllegalArgumentException {
      commands.add("isFlexible(" + portfolioName + ")\n");
      return false;
    }

    @Override
    public void addStock(String portfolioName, String ticker, int numStocks, LocalDate date,
                         double commission) throws IllegalArgumentException {
      commands.add("addStock(" + portfolioName + ", " + ticker + ", " + date.toString() +
              ", " + commission + ")\n");
    }

    @Override
    public void sellStock(String portfolioName, String ticker, int numStocks, LocalDate date,
                          double commission) throws IllegalArgumentException {
      commands.add("sellStock(" + portfolioName + ", " + ticker + ", " + date.toString() +
              ", " + commission + ")\n");
    }

    @Override
    public void newFixedAmountStrategy(String portfolioName, Map<String, Double> percentages,
                                       LocalDate date, double amount, double commission)
            throws IllegalArgumentException {
      commands.add("newFixedAmountStrategy(" + portfolioName + ", " + percentages + ", " +
              date + ", " + amount + ", " + commission + ")\n");
    }

    @Override
    public void newDollarCostAverageStrategy(String portfolioName, Map<String, Double> percentages,
                                             LocalDate start, LocalDate end, int days,
                                             double amount, double commission)
            throws IllegalArgumentException {
      commands.add("newDollarCostAverageStrategy(" + ", " + portfolioName + ", " + percentages +
              ", " + start + ", " + end + ", " + days + ", " + amount + ", " + commission + ")\n");
    }

    @Override
    public void newDollarCostAverageStrategy(String portfolioName, Map<String, Double> percentages,
                                             LocalDate start, int days, double amount,
                                             double commission) throws IllegalArgumentException {
      commands.add("newDollarCostAverageStrategy(" + ", " + portfolioName + ", " + percentages +
              ", " + start + ", " + days + ", " + amount + ", " + commission + ")\n");
    }

    @Override
    public Map<String, Double> getTickerMap(String portfolioName, LocalDate date)
            throws IllegalArgumentException {
      commands.add("getTickerMap(" + portfolioName + ", " + date.toString() + ")\n");
      Map<String, Double> tickers = new HashMap<>();
      tickers.put("GOOGL", 2.0);
      return tickers;
    }

    @Override
    public Map<String, Double> getValues(String portfolioName, LocalDate date)
            throws IllegalArgumentException {
      commands.add("getValues(" + portfolioName + ", " + date.toString() + ")\n");
      Map<String, Double> tickers = new HashMap<>();
      tickers.put("GOOGL", 2.0);
      return tickers;
    }

    @Override
    public double getPortValue(String portfolioName, LocalDate date)
            throws IllegalArgumentException {
      commands.add("getPortValue(" + portfolioName + ", " + date.toString() + ")\n");
      return 0;
    }

    @Override
    public double getCostBasis(String portfolioName, LocalDate date)
            throws IllegalArgumentException {
      commands.add("getCostBasis(" + portfolioName + ", " + date.toString() + ")\n");
      return 0;
    }

    @Override
    public Map<String, Double> getPerformance(String portfolioName, LocalDate start,
                                              LocalDate end) throws IllegalArgumentException {
      commands.add("getPerformance(" + portfolioName + ", " + start.toString() + ", " +
              end.toString() + ")\n");
      Map<String, Double> tickers = new HashMap<>();
      tickers.put("GOOGL", 2.0);
      return tickers;
    }

    @Override
    public String toString(String portfolioName) throws IllegalArgumentException {
      commands.add("toString(" + portfolioName + ")\n");
      return "";
    }

    @Override
    public boolean validTicker(String ticker) {
      commands.add("validTicker(" + ticker + ")\n");
      return true;
    }

    @Override
    public StockAPI getAPI() {
      commands.add("getAPI");
      return null;
    }

    @Override
    public FileHandler getFileHandler() {
      commands.add("getFileHandler");
      return new StockIO();
    }

    //TODO complete it
    @Override
    public Set<String> getStocksOnDate(String portfolioName, LocalDate date) {
      return null;
    }

    //TODO complete it
    @Override
    public void reBalance(Map<String, Double> stockMap, String portfolioName, LocalDate date) throws Exception {
      return;
    }
  }


  class MockGui implements IView {

    List<String> commands;

    public MockGui() {
      this.commands = new ArrayList<>();
      //this.portfolios = new HashMap<>();
    }

    @Override
    public String setUpNewPort(Features features) {
      commands.add("setUpNewPort");
      return "TechPort";
    }

    @Override
    public void showOutput(String output) {
      commands.add("showOuput");
    }

    @Override
    public void switchPanel(String s) {
      commands.add("switchPanel");
    }


    @Override
    public void gatherFile() {
      commands.add("gatherFile");
    }


    @Override
    public void addPercentageInputs() {
      commands.add("addPercentageInputs");
    }

    @Override
    public void addPercentageInputsCost() {
      commands.add("addPercentageInputsCost");
    }


    @Override
    public void showError(String error) {
      commands.add("showError");
    }

    @Override
    public void checkViewInputs(Features features) {
      commands.add("checkViewInput");
    }

    @Override
    public void checkBuyInputs(Features features) {
      commands.add("checkBuyInput");
    }

    @Override
    public void checkSellInputs(Features features) {
      commands.add("checkSellInput");
    }


    @Override
    public void addFeatures(Features features) {
      commands.add("addFeatures");
    }

  }
}