package control;

import control.terminal.StockController;
import control.terminal.TerminalController;

import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.FileHandler;
import model.Model;
import model.StockAPI;
import model.StockIO;
import view.terminal.StockView;

import static org.junit.Assert.assertEquals;

/**
 * Test the stockController.
 */
public class StockControllerTest {
  private StockController controller;
  private Model model;
  private ByteArrayInputStream input;
  private StockView view;
  private PrintStream output;
  private ByteArrayOutputStream outStream;


  @Before
  public void init() {
    this.outStream = new ByteArrayOutputStream();
    this.output = new PrintStream(this.outStream);
    this.view = new MockView(this.output);

    String inputString = "";
    this.input = new ByteArrayInputStream(inputString.getBytes());

    this.model = new MockModel();

    this.controller = new TerminalController(this.model, this.input, this.view);
  }

  @Test
  public void testQuit() {
    this.output = new PrintStream(this.outStream);

    String inputString = "Q";
    this.input = new ByteArrayInputStream(inputString.getBytes());

    this.model = new MockModel();

    this.controller = new TerminalController(this.model, this.input, this.view);
    controller.gogogadget();
    String s = System.lineSeparator();
    String expected = "showMainMenu" + s + "clearScreen" + s + "quit" + s;
    assertEquals(expected, outStream.toString());
  }

  @Test
  public void testInvalidTicker() {
    this.output = new PrintStream(this.outStream);

    String inputString = "N\ntestPort\nasdjasdjlasjiod\nGOOGL\n100\nD\nQ\n";
    this.input = new ByteArrayInputStream(inputString.getBytes());

    this.model = new MockModel();

    this.controller = new TerminalController(this.model, this.input, this.view);
    controller.gogogadget();

    // Check the view for the correct output
    String s = System.lineSeparator();

    assertEquals("showMainMenu\n" +
            "PromptPortName\n" +
            "promptAddStock\n" +
            "promptShares\n" +
            "showError(You must enter a valid number of shares please try again)\n" +
            "promptAddStock\n" +
            "portfolioCreationConfirmation(testPort)\n" +
            "showMainMenu\n" +
            "clearScreen\n" +
            "quit\n", outStream.toString());

    // Check the model for the correct sequence of commands.
    assertEquals("validTicker(asdjasdjlasjiod)\n" +
                    ",newInflexiblePortfolio(testPort , {asdjasdjlasjiod=100})\n" +
                    ",getFileHandler,toString(testPort)\n",
            String.join(",", ((MockModel) model).commands.toArray(new String[0])));

  }


  @Test
  public void testInvalidShares() {
    this.output = new PrintStream(this.outStream);

    String inputString = "N\ntestPort\nGOOGL\nsdfjdsjkf\n1\nD\nQ";
    this.input = new ByteArrayInputStream(inputString.getBytes());

    this.model = new MockModel();

    this.controller = new TerminalController(this.model, this.input, this.view);
    controller.gogogadget();

    // Check the view for the correct output
    String s = System.lineSeparator();
    assertEquals("showMainMenu\n" +
            "PromptPortName\n" +
            "promptAddStock\n" +
            "promptShares\n" +
            "showError(You must enter a valid number of shares please try again)\n" +
            "promptAddStock\n" +
            "portfolioCreationConfirmation(testPort)\n" +
            "showMainMenu\n" +
            "clearScreen\n" +
            "quit\n", outStream.toString());

    // Check the model for the correct sequence of commands.

    assertEquals("validTicker(GOOGL)\n" +
            ",newInflexiblePortfolio(testPort , {GOOGL=1})\n" +
            ",getFileHandler,toString(testPort)\n", String.join(",", ((MockModel) model).commands
            .toArray(new String[0])));


  }


  @Test
  public void testMultipleStocks() {
    this.output = new PrintStream(this.outStream);

    String inputString = "N\ntestPort\nGOOGL\n5\nAAPL\n5\nD\nV\n0\n2022-10-27\nQ\n";
    this.input = new ByteArrayInputStream(inputString.getBytes());

    this.model = new MockModel();

    this.controller = new TerminalController(this.model, this.input, this.view);
    controller.gogogadget();

    // Check the view for the correct output
    String s = System.lineSeparator();
    assertEquals("showMainMenu\n" +
            "PromptPortName\n" +
            "promptAddStock\n" +
            "promptShares\n" +
            "promptAddStock\n" +
            "promptShares\n" +
            "promptAddStock\n" +
            "portfolioCreationConfirmation(testPort)\n" +
            "showMainMenu\n" +
            "enterNamePort\n" +
            "printPortfolios(testPort)\n" +
            "promptDate\n" +
            "showPortfolio(0,0.0,2022-10-27)\n" +
            "showIndividualStocks(GOOGL,2.0,2.0\n" +
            "showMainMenu\n" +
            "clearScreen\n" +
            "quit\n", outStream.toString());

    // Check the model for the correct sequence of commands.

    assertEquals("validTicker(GOOGL)\n" +
            ",validTicker(AAPL)\n" +
            ",newInflexiblePortfolio(testPort , {GOOGL=5, AAPL=5})\n" +
            ",getFileHandler,toString(testPort)\n" +
            ",getPorftolios\n" +
            ",getPorftolios\n" +
            ",isFlexible(0)\n" +
            ",getValues(0, 2022-10-27)\n" +
            ",getTickerMap(0, 2022-10-27)\n" +
            ",getPortValue(0, 2022-10-27)\n" +
            ",isFlexible(0)\n", String.join(",",
            ((MockModel) model).commands.toArray(new String[0])));


  }

  @Test
  public void testNonCommand() {
    this.output = new PrintStream(this.outStream);

    String inputString = "O\nQ\nQ\n";
    this.input = new ByteArrayInputStream(inputString.getBytes());

    this.model = new MockModel();

    this.controller = new TerminalController(this.model, this.input, this.view);
    controller.gogogadget();

    // Check the view for the correct output
    String s = System.lineSeparator();
    assertEquals("showMainMenu" + s +
                    "showError(Command not recognized: O,Press any key to continue)" + s
                    +
                    "showMainMenu" + s
                    +
                    "clearScreen" + s
                    +
                    "quit" + s
            , outStream.toString());

    // Check the model for the correct sequence of commands.

    assertEquals("", String.join(",", ((MockModel) model).commands.toArray(new String[0])));
  }

  @Test
  public void testValidDate() {
    this.output = new PrintStream(this.outStream);

    String inputString = "N\ntestPort\nGOOGL\n5\nD\nV\n0\n2022-10-27\nQ";
    this.input = new ByteArrayInputStream(inputString.getBytes());

    this.model = new MockModel();

    this.controller = new TerminalController(this.model, this.input, this.view);
    controller.gogogadget();

    // Check the view for the correct output
    String s = System.lineSeparator();

    assertEquals("showMainMenu\n" +
            "PromptPortName\n" +
            "promptAddStock\n" +
            "promptShares\n" +
            "promptAddStock\n" +
            "portfolioCreationConfirmation(testPort)\n" +
            "showMainMenu\n" +
            "enterNamePort\n" +
            "printPortfolios(testPort)\n" +
            "promptDate\n" +
            "showPortfolio(0,0.0,2022-10-27)\n" +
            "showIndividualStocks(GOOGL,2.0,2.0\n" +
            "showMainMenu\n" +
            "clearScreen\n" +
            "quit\n", outStream.toString());

    // Check the model for the correct sequence of commands.

    assertEquals("validTicker(GOOGL)\n" +
            ",newInflexiblePortfolio(testPort , {GOOGL=5})\n" +
            ",getFileHandler,toString(testPort)\n" +
            ",getPorftolios\n" +
            ",getPorftolios\n" +
            ",isFlexible(0)\n" +
            ",getValues(0, 2022-10-27)\n" +
            ",getTickerMap(0, 2022-10-27)\n" +
            ",getPortValue(0, 2022-10-27)\n" +
            ",isFlexible(0)\n", String.join(",", ((MockModel) model).commands
            .toArray(new String[0])));
  }

  @Test
  public void testInvalidDate() {
    this.output = new PrintStream(this.outStream);

    String inputString = "N\ntestPort\nGOOGL\n5\nD\nV\n0\n2022-01-01\nQ";
    this.input = new ByteArrayInputStream(inputString.getBytes());

    this.model = new MockModel();

    this.controller = new TerminalController(this.model, this.input, this.view);
    controller.gogogadget();

    // Check the view for the correct output
    String s = System.lineSeparator();


    assertEquals("showMainMenu\n" +
            "PromptPortName\n" +
            "promptAddStock\n" +
            "promptShares\n" +
            "promptAddStock\n" +
            "portfolioCreationConfirmation(testPort)\n" +
            "showMainMenu\n" +
            "enterNamePort\n" +
            "printPortfolios(testPort)\n" +
            "promptDate\n" +
            "showPortfolio(0,0.0,2022-01-01)\n" +
            "showIndividualStocks(GOOGL,2.0,2.0\n" +
            "showMainMenu\n" +
            "clearScreen\n" +
            "quit\n", outStream.toString());

    // Check the model for the correct sequence of commands.

    assertEquals("validTicker(GOOGL)\n" +
            ",newInflexiblePortfolio(testPort , {GOOGL=5})\n" +
            ",getFileHandler,toString(testPort)\n" +
            ",getPorftolios\n" +
            ",getPorftolios\n" +
            ",isFlexible(0)\n" +
            ",getValues(0, 2022-01-01)\n" +
            ",getTickerMap(0, 2022-01-01)\n" +
            ",getPortValue(0, 2022-01-01)\n" +
            ",isFlexible(0)\n", String.join(",", ((MockModel) model).commands
            .toArray(new String[0])));


  }

  @Test
  public void testInvalidDate2() {
    this.output = new PrintStream(this.outStream);

    String inputString = "N\ntestPort\nGOOGL\n5\nD\nV\n0\n10-20-2022\n2022-10-27\nQ";
    this.input = new ByteArrayInputStream(inputString.getBytes());

    this.model = new MockModel();

    this.controller = new TerminalController(this.model, this.input, this.view);
    controller.gogogadget();

    // Check the view for the correct output
    String s = System.lineSeparator();

    assertEquals("showMainMenu\n" +
            "PromptPortName\n" +
            "promptAddStock\n" +
            "promptShares\n" +
            "promptAddStock\n" +
            "portfolioCreationConfirmation(testPort)\n" +
            "showMainMenu\n" +
            "enterNamePort\n" +
            "printPortfolios(testPort)\n" +
            "promptDate\n" +
            "showError(Text '10-20-2022' could not be parsed at index 0)\n" +
            "promptDate\n" +
            "showPortfolio(0,0.0,2022-10-27)\n" +
            "showIndividualStocks(GOOGL,2.0,2.0\n" +
            "showMainMenu\n" +
            "clearScreen\n" +
            "quit\n", outStream.toString());

    // Check the model for the correct sequence of commands.

    assertEquals("validTicker(GOOGL)\n" +
            ",newInflexiblePortfolio(testPort , {GOOGL=5})\n" +
            ",getFileHandler,toString(testPort)\n" +
            ",getPorftolios\n" +
            ",getPorftolios\n" +
            ",isFlexible(0)\n" +
            ",getValues(0, 2022-10-27)\n" +
            ",getTickerMap(0, 2022-10-27)\n" +
            ",getPortValue(0, 2022-10-27)\n" +
            ",isFlexible(0)\n", String.join(",", ((MockModel) model).commands
            .toArray(new String[0])));


  }


  /**
   * Test creating a new portfolio.
   */
  @Test
  public void testNewPortfolio() {
    this.output = new PrintStream(this.outStream);

    String inputString = "N\ntestPort\nGOOGL\n100\nD\nQ";
    this.input = new ByteArrayInputStream(inputString.getBytes());

    this.model = new MockModel();

    this.controller = new TerminalController(this.model, this.input, this.view);
    controller.gogogadget();

    // Check the view for the correct output
    String s = System.lineSeparator();
    assertEquals("showMainMenu\n" +
            "PromptPortName\n" +
            "promptAddStock\n" +
            "promptShares\n" +
            "promptAddStock\n" +
            "portfolioCreationConfirmation(testPort)\n" +
            "showMainMenu\n" +
            "clearScreen\n" +
            "quit\n", outStream.toString());

    // Check the model for the correct sequence of commands.
    assertEquals("validTicker(GOOGL)\n" +
            ",newInflexiblePortfolio(testPort , {GOOGL=100})\n" +
            ",getFileHandler,toString(testPort)\n", String.join(",", ((MockModel) model).commands
            .toArray(new String[0])));
  }

  /**
   * Test viewing a portfolio (including before actually creating any).
   */
  @Test
  public void testViewPortfolio() {
    this.output = new PrintStream(this.outStream);

    String inputString = "V\nN\ntestPort\nGOOGL\n100\nD\nV\n0\n2022-10-30\nQ";
    this.input = new ByteArrayInputStream(inputString.getBytes());

    this.model = new MockModel();

    this.controller = new TerminalController(this.model, this.input, this.view);
    controller.gogogadget();
    String s = System.lineSeparator();
    String viewExpected = "showMainMenu\n" +
            "showError(You must create a portfolio before viewing)\n" +
            "showMainMenu\n" +
            "PromptPortName\n" +
            "promptAddStock\n" +
            "promptShares\n" +
            "promptAddStock\n" +
            "portfolioCreationConfirmation(testPort)\n" +
            "showMainMenu\n" +
            "enterNamePort\n" +
            "printPortfolios(testPort)\n" +
            "promptDate\n" +
            "showPortfolio(0,0.0,2022-10-30)\n" +
            "showIndividualStocks(GOOGL,2.0,2.0\n" +
            "showMainMenu\n" +
            "clearScreen\n" +
            "quit\n";
    String viewOutput = outStream.toString();
    assertEquals(viewExpected, viewOutput);

    String modelOutput = String.join(",", ((MockModel) model).commands.toArray(new String[0]));
    String modelExpected = "getPorftolios\n" +
            ",validTicker(GOOGL)\n" +
            ",newInflexiblePortfolio(testPort , {GOOGL=100})\n" +
            ",getFileHandler,toString(testPort)\n" +
            ",getPorftolios\n" +
            ",getPorftolios\n" +
            ",isFlexible(0)\n" +
            ",getValues(0, 2022-10-30)\n" +
            ",getTickerMap(0, 2022-10-30)\n" +
            ",getPortValue(0, 2022-10-30)\n" +
            ",isFlexible(0)\n";
    assertEquals(modelExpected, modelOutput);

  }

  @Test
  public void testReBalance() {
    this.output = new PrintStream(this.outStream);


    String inputString = "L\narush.JSON\narush\nR\narush\n2018-11-16\n20\n20\n20\n20\n20\nQ\n";
    this.input = new ByteArrayInputStream(inputString.getBytes());

    this.model = new MockModel();

    this.controller = new TerminalController(this.model, this.input, this.view);
    this.controller.gogogadget();
    String viewExpected = "showMainMenu\n"
        + "loadFileData\n"
        + "PromptPortName\n"
        + "loadingData\n"
        + "showMainMenu\n"
        + "enterNamePort\n"
        + "printPortfolios(arush)\n"
        + "promptDate\n"
        + "printAvailableStockReBalance\n"
        + "percentageHeaderReBalance\n"
        + "askPercentageReBalance\n"
        + "askPercentageReBalance\n"
        + "askPercentageReBalance\n"
        + "askPercentageReBalance\n"
        + "askPercentageReBalance\n"
        + "reBalanceConfirmation\n"
        + "showMainMenu\n"
        + "clearScreen\n"
        + "quit\n";
    String viewOutput = outStream.toString();
    assertEquals(viewExpected, viewOutput);

    String modelOutput = String.join(",", ((MockModel) model).commands.toArray(new String[0]));
    String modelExpected = "getFileHandler,newFlexiblePortfolio(arush)\n"
        + ",addStock(arush, AAPL, 2016-01-01, 1.0)\n"
        + ",addStock(arush, META, 2017-03-03, 1.0)\n"
        + ",addStock(arush, MSFT, 2017-10-10, 1.0)\n"
        + ",addStock(arush, TSLA, 2016-06-06, 1.0)\n"
        + ",addStock(arush, PYPL, 2018-02-02, 1.0)\n"
        + ",getPorftolios\n"
        + ",getPorftolios\n"
        + ",isFlexible(arush)\n"
        + ",getValues(arush, 2018-11-16)\n"
        + ",Portfolio Name: arush date : 2018-11-16,StockMap : {A=20.0, B=20.0, C=20.0, D=20.0,"
        + " E=20.0} Portfolio Name : arush Date : 2018-11-16";
    assertEquals(modelExpected, modelOutput);

  }


  /*
   * ------------------------- Mock Model --------------------------------------
   */

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
      commands.add("addStock(" + portfolioName + ", " + ticker + ", " + date.toString() + ", " +
              commission + ")\n");
    }

    @Override
    public void sellStock(String portfolioName, String ticker, int numStocks, LocalDate date,
                          double commission) throws IllegalArgumentException {
      commands.add("sellStock(" + portfolioName + ", " + ticker + ", " + date.toString() + ", " +
              commission + ")\n");
    }

    @Override
    public void newFixedAmountStrategy(String portfolioName, Map<String, Double> percentages,
                                       LocalDate date, double amount, double commission)
            throws IllegalArgumentException {
      // Do nothing.
    }

    @Override
    public void newDollarCostAverageStrategy(String portfolioName, Map<String, Double> percentages,
                                             LocalDate start, LocalDate end, int days,
                                             double amount, double commission)
            throws IllegalArgumentException {
      // Do nothing.
    }

    @Override
    public void newDollarCostAverageStrategy(String portfolioName, Map<String, Double> percentages,
                                             LocalDate start, int days, double amount,
                                             double commission) throws IllegalArgumentException {
      // Do nothing.
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
    public Map<String, Double> getPerformance(String portfolioName, LocalDate start, LocalDate end)
            throws IllegalArgumentException {
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

    @Override
    public Set<String> getStocksOnDate(String portfolioName, LocalDate date) {
      commands.add("Portfolio Name: "+portfolioName+" date : "+date);
      Set<String> stocks = new HashSet<>();
      stocks.add("A");
      stocks.add("B");
      stocks.add("C");
      stocks.add("D");
      stocks.add("E");
      return stocks;
    }

    @Override
    public void reBalance(Map<String, Double> stockMap, String portfolioName, LocalDate date) {
      commands.add("StockMap : "+stockMap+" Portfolio Name : "+portfolioName+" Date : "+date);
    }
  }
  /*
   * ------------------------------------ Mock View ---------------------------
   */

  /**
   * Mock class for view.
   */
  class MockView implements StockView {
    PrintStream out;

    public MockView(PrintStream out) {
      this.out = out;
    }

    @Override
    public void clearScreen() {
      out.println("clearScreen");
    }

    @Override
    public void showMainMenu() {
      out.println("showMainMenu");
    }

    @Override
    public void showCommand(String command) {
      out.println("showCommand(" + command + ")");
    }

    @Override
    public void showPortfolio(String portfolioName, double totalValue, String date) {
      out.println("showPortfolio(" + portfolioName + "," + totalValue + "," + date + ")");
    }

    @Override
    public void loadingData() {
      out.println("loadingData");
    }

    @Override
    public void showIndividualStocks(String[] info) {
      out.println("showIndividualStocks(" + String.join(",", info));
    }

    @Override
    public void showError(String[] error) {
      out.println("showError(" + String.join(",", error) + ")");
    }

    @Override
    public void portfolioCreationConfirmation(String s) {
      out.println("portfolioCreationConfirmation(" + s + ")");
    }

    @Override
    public void promptPortName() {
      out.println("PromptPortName");
    }

    @Override
    public void promptAddStock() {
      out.println("promptAddStock");
    }

    @Override
    public void promptShares() {
      out.println("promptShares");
    }

    @Override
    public void promptPortfolioChoices(int i, String portName) {
      out.println("promptPortfolioChoices(" + i + "," + portName + ")");
    }

    @Override
    public void promptDate() {
      out.println("promptDate");
    }

    @Override
    public void enterNamePort() {
      out.println("enterNamePort");
    }

    @Override
    public void quit() {
      out.println("quit");
    }

    @Override
    public void promptLoadData() {
      out.println("loadFileData");
    }

    @Override
    public void promptDatePurchased() {
      out.println("promptDatePurchased");
    }

    @Override
    public void promptDateSold() {
      out.println("promptDateSold");
    }

    @Override
    public void printGraph(String date, String astericks) {
      out.println("pringGraph(" + date + ", " + astericks + ")");
    }

    @Override
    public void promptBuyStock() {
      out.println("promptBuyStock");
    }

    @Override
    public void promptDateBuyStock() {
      out.println("promptDateBuyStock");
    }

    @Override
    public void promptDateSellStock() {
      out.println("promptDateSellStock");
    }

    @Override
    public void promptSellStock() {
      out.println("promptSellStock");
    }

    @Override
    public void printStocksToSell(String ticker, String shares) {
      out.println("printStocksToSell(" + ticker + ", " + shares + ")");
    }

    @Override
    public void promptWhichPort() {
      out.println("promptWhichPort");
    }

    @Override
    public void printPortfolios(String portfolioName) {
      out.println("printPortfolios(" + portfolioName + ")");
    }

    @Override
    public void promptCommission() {
      out.println("promptCommission");
    }

    @Override
    public void promptStartDate() {
      out.println("promptStartDate");
    }

    @Override
    public void promptEndDate() {
      out.println("promptEndDate");
    }

    @Override
    public void printGraphScale(String scale) {
      out.println("printGraphScale(" + scale + ")");
    }

    @Override
    public void printGraphTitle(String portfolioName, String startDate, String endDate) {
      out.println("printGraphTitle(" + portfolioName + ", " + startDate + ", " + endDate + ")");
    }

    @Override
    public void showIndividualStocksFlex(String[] info) {
      out.println("showIndividualStocksFlex(" + info + ")");
    }

    @Override
    public void emptyPortfolioReBalance() {
      out.println("emptyPortfolioReBalance");
    }

    @Override
    public void printAvailableStockReBalance(Set<String> stockNames) {
      out.println("printAvailableStockReBalance");
    }

    @Override
    public void percentageErrorIntegerFormatReBalance() {
      out.println("percentageErrorIntegerFormatReBalance");
    }

    @Override
    public void percentageErrorOutRangeReBalance() {
      out.println("percentageErrorOutRangeReBalance");
    }

    @Override
    public void reBalanceConfirmation() {
      out.println("reBalanceConfirmation");
    }

    @Override
    public void reBalanceErrorMsg(Exception e) {
      out.println("reBalanceErrorMsg");
    }

    @Override
    public void percentageHeaderReBalance() {
      out.println("percentageHeaderReBalance");
    }

    @Override
    public void askPercentageReBalance(String stock) {
      out.println("askPercentageReBalance");
    }
  }


}