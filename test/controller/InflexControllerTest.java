package controller;


import static org.junit.Assert.assertEquals;

import constants.ViewConstants;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import model.MockModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Junit Class to test StockController.
 */
public class InflexControllerTest {

  InflexController controller;
  MockModel model;
  StringBuilder log;
  ByteArrayOutputStream bytes;
  InputStream in;
  PrintStream out;

  @Before
  public void init() {
    bytes = new ByteArrayOutputStream();
    out = new PrintStream(bytes);
    log = new StringBuilder();
    model = new MockModel(log);
  }

  @Test
  public void testGo() throws Exception {
    in = new ByteArrayInputStream("0".getBytes());
    controller = new InflexController(in, out);
    controller.start(model);
    assertEquals("clean stock directory", log.toString());
    assertEquals(
        ViewConstants.WELCOME_MESSAGE + "\n" + ViewConstants.MENU_MESSAGE +
            ViewConstants.EXIT_NOTE,
        bytes.toString());
  }

  @Test
  public void testAddStocksAskTicker() {
    in = new ByteArrayInputStream("aapl\n".getBytes());
    controller = new InflexController(in, out);
    String ticker = controller.addStocksAskTicker(new Scanner(in), model);
    assertEquals("Name of ticker is AAPLName of ticker is AAPL", log.toString());
    assertEquals(ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_TICKER_SYMBOL,
        bytes.toString());
  }

  @Test
  public void testAddStocksAskTicker2() {
    in = new ByteArrayInputStream("goog\naapl\n".getBytes());
    controller = new InflexController(in, out);
    String ticker = controller.addStocksAskTicker(new Scanner(in), model);
    assertEquals(
        "Name of ticker is GOOGName of ticker is GOOGName of ticker is AAPLName of ticker "
            + "is AAPL",
        log.toString());
    assertEquals(ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_TICKER_SYMBOL
        + ViewConstants.ASK_TICKER_SYMBOL_AGAIN, bytes.toString());
  }

  @Test
  public void testAddStocksAskStockNumber() {
    in = new ByteArrayInputStream("458.00\n".getBytes());
    controller = new InflexController(in, out);
    Double stockQuan = controller.addStocksAskStockNumber(new Scanner(in));
    assertEquals(ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_STOCK_NUMBER,
        bytes.toString());
  }

  @Test
  public void testAddStocksAskStockNumber2() {
    in = new ByteArrayInputStream("-458.28\n23\n".getBytes());
    controller = new InflexController(in, out);
    Double stockQuan = controller.addStocksAskStockNumber(new Scanner(in));
    assertEquals(ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_STOCK_NUMBER
        + ViewConstants.ASK_STOCK_NUMBER_AGAIN, bytes.toString());
  }

  @Test
  public void testAddStocksToPortfolioControllerOnce() throws Exception {
    in = new ByteArrayInputStream("test\naapl\n11.00\nn\n".getBytes());
    controller = new InflexController(in, out);
    controller.addStocksToPortfolioController(new Scanner(in), model);
    assertEquals(
        "unique name checker" + "\nget stock list" + "Name of ticker is AAPLName of ticker "
            + "is AAPL"
            + "\nInput test" + "\n stock map:AAPL->11.0", log.toString());
    assertEquals(ViewConstants.ADD_PORTFOLIO
            + "-------------For the Portfolio test: Add stocks------------\n"
            + ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_TICKER_SYMBOL
            + ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_STOCK_NUMBER
            + ViewConstants.ADD_STOCKS_IN_PORTFOLIO_CONFIRMATION + ViewConstants.WAIT_MESSAGE + "\n"
            + "\ntest Portfolio has been added to your list of portfolios\n",
        bytes.toString());
  }

  @Test
  public void testAddStocksToPortfolioControllerMultiple() throws Exception {
    in = new ByteArrayInputStream("test\naapl\n11.00\ny\n1\naapl\n4\nn\n".getBytes());
    controller = new InflexController(in, out);
    controller.addStocksToPortfolioController(new Scanner(in), model);
    assertEquals("unique name checker\n"
        + "get stock listName of ticker is AAPLName of ticker is AAPLName of ticker is AAPLName of "
        + "ticker is AAPL\n"
        + "Input test\n" + " stock map:AAPL->15.0", log.toString());
    assertEquals(ViewConstants.ADD_PORTFOLIO
            + "-------------For the Portfolio test: Add stocks------------\n"
            + ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_TICKER_SYMBOL
            + ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_STOCK_NUMBER
            + ViewConstants.ADD_STOCKS_IN_PORTFOLIO_CONFIRMATION
            + ViewConstants.STOCKS_IN_PORTFOLIO_ADD_OR_REMOVE_MENU
            + ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_TICKER_SYMBOL
            + ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_STOCK_NUMBER
            + ViewConstants.ADD_STOCKS_IN_PORTFOLIO_CONFIRMATION + ViewConstants.WAIT_MESSAGE + "\n"
            + "\ntest Portfolio has been added to your list of portfolios\n",
        bytes.toString());
  }

  @Test
  public void testAddStocksToPortfolioControllerAddRemove() throws Exception {
    in = new ByteArrayInputStream("test\naapl\n11.00\ny\n2\naapl\n4\nn\n".getBytes());
    controller = new InflexController(in, out);
    controller.addStocksToPortfolioController(new Scanner(in), model);
    assertEquals("unique name checker\n"
        + "get stock listName of ticker is AAPLName of ticker is AAPLName of ticker is AAPLName of "
        + "ticker is AAPL\n"
        + "Input test\n" + " stock map:AAPL->7.0", log.toString());
    assertEquals(ViewConstants.ADD_PORTFOLIO
            + "-------------For the Portfolio test: Add stocks------------\n"
            + ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_TICKER_SYMBOL
            + ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_STOCK_NUMBER
            + ViewConstants.ADD_STOCKS_IN_PORTFOLIO_CONFIRMATION
            + ViewConstants.STOCKS_IN_PORTFOLIO_ADD_OR_REMOVE_MENU
            + ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_TICKER_SYMBOL
            + ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_STOCK_NUMBER
            + ViewConstants.REMOVE_STOCKS_IN_PORTFOLIO_SUCCESSFUL_CONFIRMATION
            + ViewConstants.WAIT_MESSAGE + "\n"
            + "\ntest Portfolio has been added to your list of portfolios\n",
        bytes.toString());
  }

  @Test
  public void testLoadPortfoliosController() throws Exception {
    in = new ByteArrayInputStream("2\n".getBytes());
    controller = new InflexController(in, out);
    controller.loadPortfoliosController(new Scanner(in), model);
    assertEquals("get Portfolios list", log.toString());
    assertEquals("\nYour portfolio lists : \n" + "* tech\n" +
            ViewConstants.LOAD_PORTFOLIO_MENU,
        bytes.toString());
  }

  @Test
  public void testLoadSinglePortfolioDetailControllerSummary() throws Exception {
    in = new ByteArrayInputStream("techLoad\n1\n7\n".getBytes());
    controller = new InflexController(in, out);
    controller.loadSinglePortfolioDetailController(new Scanner(in), model);
    assertEquals("unique name checker\n" + "getting portfolio summary for techLoad",
        log.toString());
    assertEquals(ViewConstants.ASK_NAME_OF_PORTFOLIO + "For the portfolio: techLoad\n"
        + ViewConstants.LOAD_PORTFOLIO_DETAIL_MENU + ViewConstants.WAIT_LOAD_MESSAGE
        + "             Table for portfolio summary\n"
        + "----------------------------------------------------------\n"
        + "       Ticker Symbol             Quantity\n"
        + "----------------------------------------------------------\n"
        + "           MSFT                    10.0 \n"
        + "           AAPL                    12.0 \n"
        + "----------------------------------------------------------\n" + "\n"
        + ViewConstants.LOAD_PORTFOLIO_DETAIL_MENU, bytes.toString());
  }

  @Test
  public void testLoadSinglePortfolioDetailControllerCurrentDetail() throws Exception {
    in = new ByteArrayInputStream("techLoad\n2\n7\n".getBytes());
    controller = new InflexController(in, out);
    controller.loadSinglePortfolioDetailController(new Scanner(in), model);
    String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
    assertEquals(
        "unique name checker\n" + "getting portfolio detail for techLoad for the date "
            + date
            + "\n" + "getting portfolio value for techLoad for the date " + date + "\n"
            + "getting portfolio performance for techLoad for the date " + date, log.toString());

    assertEquals(ViewConstants.ASK_NAME_OF_PORTFOLIO + "For the portfolio: techLoad\n"
        + ViewConstants.LOAD_PORTFOLIO_DETAIL_MENU + ViewConstants.WAIT_LOAD_MESSAGE
        + "                                 Table for portfolio details\n"
        + "---------------------------------------------------------------------------------------"
        + "---\n"
        + "  Ticker Symbol        Quantity         Buy price  Price on asked Date     Performance\n"
        + "--------------------------------------------------------------------------------------"
        + "----\n"
        + "        AAPL             5.00            93.50            95.00             1.50\n"
        + "-------------------------------------------------------------------------------------"
        + "-----\n"
        + "                        Value of the portfolio on that day: 0.00\n"
        + "Your portfolio has the same value as that of buying day\n"
        + ViewConstants.LOAD_PORTFOLIO_DETAIL_MENU, bytes.toString());
  }

  @Test
  public void testLoadSinglePortfolioDetailControllerCurrentValue() throws Exception {
    in = new ByteArrayInputStream("techLoad\n3\n7\n".getBytes());
    controller = new InflexController(in, out);
    controller.loadSinglePortfolioDetailController(new Scanner(in), model);
    String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
    assertEquals(
        "unique name checker\n" + "getting portfolio value for techLoad for the date "
            + date,
        log.toString());
    assertEquals(ViewConstants.ASK_NAME_OF_PORTFOLIO + "For the portfolio: techLoad\n"
            + ViewConstants.LOAD_PORTFOLIO_DETAIL_MENU + ViewConstants.WAIT_LOAD_MESSAGE
            + "Value of the portfolio on that day: 0.00\n" +
            ViewConstants.LOAD_PORTFOLIO_DETAIL_MENU,
        bytes.toString());
  }

  @Test
  public void testLoadSinglePortfolioDetailControllerHostoricalDetail() throws Exception {
    in = new ByteArrayInputStream("techLoad\n4\n2022-02-26\n7\n".getBytes());
    controller = new InflexController(in, out);
    controller.loadSinglePortfolioDetailController(new Scanner(in), model);

    assertEquals("unique name checkerchecking the date 2022-02-26\n"
        + "getting portfolio detail for techLoad for the date 2022-02-26\n"
        + "getting portfolio value for techLoad for the date 2022-02-26\n"
        + "getting portfolio performance for techLoad for the date 2022-02-26", log.toString());

    assertEquals(ViewConstants.ASK_NAME_OF_PORTFOLIO + "For the portfolio: techLoad\n"
        + ViewConstants.LOAD_PORTFOLIO_DETAIL_MENU + ViewConstants.ASK_DATE
        + ViewConstants.WAIT_LOAD_MESSAGE
        + "                                 Table for portfolio details\n"
        + "--------------------------------------------------------------------------------"
        + "----------\n"
        + "  Ticker Symbol        Quantity         Buy price  Price on asked Date     Performance\n"
        + "---------------------------------------------------------------------------------------"
        + "---\n"
        + "        AAPL             5.00            93.50            95.00             1.50\n"
        + "-------------------------------------------------------------------------------------"
        + "-----\n"
        + "                        Value of the portfolio on that day: 0.00\n"
        + "Your portfolio has the same value as that of buying day\n"
        + ViewConstants.LOAD_PORTFOLIO_DETAIL_MENU, bytes.toString());
  }

  @Test
  public void testLoadSinglePortfolioDetailControllerHistoricalValue() throws Exception {
    in = new ByteArrayInputStream("techLoad\n5\n2022-02-26\n7\n".getBytes());
    controller = new InflexController(in, out);
    controller.loadSinglePortfolioDetailController(new Scanner(in), model);
    assertEquals("unique name checkerchecking the date 2022-02-26\n"
        + "getting portfolio value for techLoad for the date 2022-02-26", log.toString());

    assertEquals(ViewConstants.ASK_NAME_OF_PORTFOLIO + "For the portfolio: techLoad\n"
        + ViewConstants.LOAD_PORTFOLIO_DETAIL_MENU + ViewConstants.ASK_DATE
        + ViewConstants.WAIT_LOAD_MESSAGE + "Value of the portfolio on that day: 0.00\n"
        + ViewConstants.LOAD_PORTFOLIO_DETAIL_MENU, bytes.toString());
  }

  @Test
  public void testLoadSinglePortfolioDetailControllerReturnToList() throws Exception {
    in = new ByteArrayInputStream("techLoad\n6\n2\n".getBytes());
    controller = new InflexController(in, out);
    controller.loadSinglePortfolioDetailController(new Scanner(in), model);
    assertEquals("unique name checkerget Portfolios list", log.toString());
    assertEquals(ViewConstants.ASK_NAME_OF_PORTFOLIO + "For the portfolio: techLoad\n"
        + ViewConstants.LOAD_PORTFOLIO_DETAIL_MENU + "\nYour portfolio lists : \n" + "* tech\n"
        + ViewConstants.LOAD_PORTFOLIO_MENU, bytes.toString());
  }

  @Test
  public void testLoadSinglePortfolioDetailControllerReturnToMainMenu() throws Exception {
    in = new ByteArrayInputStream("techLoad\n7\n".getBytes());
    controller = new InflexController(in, out);
    controller.loadSinglePortfolioDetailController(new Scanner(in), model);
    assertEquals("unique name checker", log.toString());
    assertEquals(ViewConstants.ASK_NAME_OF_PORTFOLIO + "For the portfolio: techLoad\n"
        + ViewConstants.LOAD_PORTFOLIO_DETAIL_MENU, bytes.toString());
  }


}