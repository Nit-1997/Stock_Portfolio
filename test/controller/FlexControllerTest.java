package controller;

import static org.junit.Assert.*;

import constants.ViewConstants;
import controller.Commands.AddFlexPortfolio;
import controller.Commands.LoadFlexPortfolio;
import controller.Commands.LoadSingleFlexPortfolioDetail;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import model.FlexMockModel;
import model.UserFlex;
import org.junit.Before;
import org.junit.Test;

public class FlexControllerTest {

  FlexController controller;
  UserFlex model;
  StringBuilder log;
  ByteArrayOutputStream bytes;
  InputStream in;
  PrintStream out;

  @Before
  public void init() {
    bytes = new ByteArrayOutputStream();
    out = new PrintStream(bytes);
    log = new StringBuilder();
    model = new FlexMockModel(log);
  }

  @Test
  public void testStart(){
    in = new ByteArrayInputStream("0\n0".getBytes());
    controller = new FlexController(in, out);
    controller.start(model);
    assertEquals(ViewConstants.FLEXIBLE_PORTFOLIO_HEADER+
        ViewConstants.MENU_MESSAGE+
        ViewConstants.FLEX_PORTFOLIO_EXIT_MSG, bytes.toString());
  }

  @Test
  public void testAddControllerOneStock(){
    in = new ByteArrayInputStream("controllerTest\naapl\n10\n2022-01-01\n4.8\nn".getBytes());
    AddFlexPortfolio.addStocksToPortfolioController(new Scanner(in),model,out);
    assertEquals(ViewConstants.ADD_PORTFOLIO +
        "-------------For the Portfolio controllerTest: Add stocks------------\n"+
        ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_TICKER_SYMBOL+
        ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_STOCK_NUMBER+
        ViewConstants.ASK_DATE+
        ViewConstants.ASK_COMMISSION_FEE+
        ViewConstants.ADD_STOCKS_IN_PORTFOLIO_CONFIRMATION+
        ViewConstants.WAIT_MESSAGE+"\n"+
        "\ncontrollerTest Portfolio has been added to your list of portfolios\n",bytes.toString());

    assertEquals("unique name checker"+"\nget stock list"+"Name of ticker is AAPL"+"Name of"
        + " ticker is AAPL"+
        "checking the date 2022-01-01"+"\nInput controllerTest"+"\n stock map: \nAAPL->2022-01-01->"
        + "10.0,4.8\n",log.toString());
  }

  @Test
  public void testAddControllerMultipleStocks(){
    in = new ByteArrayInputStream(("controllerTest\naapl\n10\n2020-05-15\n4.8\ny\n1\ngoog\n"
        + "13\n2018-09-28\n3.5\nn").getBytes());
    AddFlexPortfolio.addStocksToPortfolioController(new Scanner(in),model,out);
    assertEquals(ViewConstants.ADD_PORTFOLIO + "-------------For the Portfolio"
        + " controllerTest: Add stocks------------\n"+
         ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_TICKER_SYMBOL+
        ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_STOCK_NUMBER+
        ViewConstants.ASK_DATE+ViewConstants.ASK_COMMISSION_FEE+
        ViewConstants.ADD_STOCKS_IN_PORTFOLIO_CONFIRMATION+
        ViewConstants.STOCKS_IN_PORTFOLIO_ADD_OR_REMOVE_MENU+
        ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_TICKER_SYMBOL+
        ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_STOCK_NUMBER+
        ViewConstants.ASK_DATE+ViewConstants.ASK_COMMISSION_FEE+
        ViewConstants.ADD_STOCKS_IN_PORTFOLIO_CONFIRMATION+
        ViewConstants.WAIT_MESSAGE+"\n"+
        "\ncontrollerTest Portfolio has been added to your list of portfolios\n",bytes.toString());

    assertEquals("unique name checker"+"\nget stock list"+"Name of ticker is AAPL"+
        "Name of ticker is AAPL"+ "checking the date 2020-05-15"+"Name of ticker is GOOG"+
        "Name of ticker is GOOG"+"checking the date 2018-09-28"+ "\nInput controllerTest"+
        "\n stock map: \n"+"GOOG->2018-09-28->13.0,3.5\n"+"AAPL->2020-05-15->10.0,4.8\n",
        log.toString());
  }

  @Test
  public void testAddControllerMultipleStocksAddRemove(){
    in = new ByteArrayInputStream(("controllerTest\naapl\n10\n2020-05-15\n4.8\ny\n2\naapl\n6\n"
        + "2020-05-15\nn").getBytes());
    AddFlexPortfolio.addStocksToPortfolioController(new Scanner(in),model,out);
    assertEquals(ViewConstants.ADD_PORTFOLIO + "-------------For the Portfolio "
        + "controllerTest: Add stocks------------\n"+
        ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_TICKER_SYMBOL+
        ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_STOCK_NUMBER+
        ViewConstants.ASK_DATE+
        ViewConstants.ASK_COMMISSION_FEE+
        ViewConstants.ADD_STOCKS_IN_PORTFOLIO_CONFIRMATION+
        ViewConstants.STOCKS_IN_PORTFOLIO_ADD_OR_REMOVE_MENU+
        ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_TICKER_SYMBOL+
        ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_STOCK_NUMBER+
        ViewConstants.ASK_DATE+
        ViewConstants.REMOVE_STOCKS_IN_PORTFOLIO_SUCCESSFUL_CONFIRMATION+
        ViewConstants.WAIT_MESSAGE+"\n"+
        "\ncontrollerTest Portfolio has been added to your list of portfolios\n",bytes.toString());

    assertEquals("unique name checker"+"\nget stock list"+"Name of ticker is AAPL"+
            "Name of ticker is AAPL"+ "checking the date 2020-05-15"+"Name of ticker is AAPL"+
            "Name of ticker is AAPL"+"checking the date 2020-05-15"+"\nInput controllerTest"+
            "\n stock map: \n"+"AAPL->2020-05-15->4.0,4.8\n",
        log.toString());
  }

  @Test
  public void testAddControllerUnsuccessful(){
    in = new ByteArrayInputStream("controllerTest\nmeta\n10\n2010-01-05\n4.8\nn".getBytes());
    AddFlexPortfolio.addStocksToPortfolioController(new Scanner(in),model,out);
    assertEquals(ViewConstants.ADD_PORTFOLIO +
        "-------------For the Portfolio controllerTest: Add stocks------------\n"+
        ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_TICKER_SYMBOL+
        ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_STOCK_NUMBER+
        ViewConstants.ASK_DATE+
        ViewConstants.ASK_COMMISSION_FEE+
        ViewConstants.ADD_STOCKS_IN_PORTFOLIO_CONFIRMATION+
        ViewConstants.WAIT_MESSAGE+"\n\n"+
        "Asked stock didn't exist on that date\n"+
        ViewConstants.UNSUCCESSFUL_PORTFOLIO_CREATION_MSG,bytes.toString());

    assertEquals("unique name checker"+"\nget stock list"+"Name of ticker is META"+"Name of"
        + " ticker is META"+
        "checking the date 2010-01-05"+"\nInput controllerTest"+"\n stock map: \nMETA->2010-01-05->"
        + "10.0,4.8\n",log.toString());
  }

  @Test
  public void testLoadFlexController(){
    in = new ByteArrayInputStream("2".getBytes());
    LoadFlexPortfolio.loadPortfoliosController(new Scanner(in),model,out);
    assertEquals("\nYour portfolio lists : \n"+
        "* flexTest\n"+ViewConstants.LOAD_PORTFOLIO_MENU,bytes.toString());
    assertEquals("get Portfolios list",log.toString());
  }

  @Test
  public void testLoadSinglePortfolio(){
    in = new ByteArrayInputStream("flexControllerTest\n9".getBytes());
    LoadSingleFlexPortfolioDetail.loadSinglePortfolioDetailController(new Scanner(in),model,out);
    assertEquals(ViewConstants.ASK_NAME_OF_PORTFOLIO+
        "For the portfolio: flexControllerTest\n"+
        ViewConstants.LOAD_FLEX_PORTFOLIO_DETAIL_MENU,bytes.toString());
    assertEquals("unique name checker",log.toString());
  }

  @Test
  public void testLoadSinglePortfolioSummary(){
    in = new ByteArrayInputStream("flexControllerTest\n1\n2018-07-16\n9".getBytes());
    LoadSingleFlexPortfolioDetail.loadSinglePortfolioDetailController(new Scanner(in),model,out);
    assertEquals(ViewConstants.ASK_NAME_OF_PORTFOLIO+
        "For the portfolio: flexControllerTest\n"+
        ViewConstants.LOAD_FLEX_PORTFOLIO_DETAIL_MENU+
        ViewConstants.WAIT_LOAD_MESSAGE+
        ViewConstants.ASK_DATE+
        "             Table for portfolio summary\n"
            + "----------------------------------------------------------\n"
            + "       Ticker Symbol             Quantity\n"
            + "----------------------------------------------------------\n"
            + "           MSFT                    10.0 \n"
            + "           AAPL                    12.0 \n"
            + "----------------------------------------------------------\n"
            + "\n"+
        ViewConstants.LOAD_FLEX_PORTFOLIO_DETAIL_MENU,bytes.toString());
    assertEquals("unique name checker"+"checking the date 2018-07-16"+
        "get portfolio creation date for flexControllerTest"+"2018-07-16 is before date 2011-02-15"+
        "\ngetting portfolio summary for flexControllerTest for the date: 2018-07-16",
        log.toString());
  }

  @Test
  public void testLoadSinglePortfolioCurrentValue(){
    String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
    in = new ByteArrayInputStream("flexControllerTest\n2\n9".getBytes());
    LoadSingleFlexPortfolioDetail.loadSinglePortfolioDetailController(new Scanner(in),model,out);
    assertEquals(ViewConstants.ASK_NAME_OF_PORTFOLIO+
        "For the portfolio: flexControllerTest\n"+
        ViewConstants.LOAD_FLEX_PORTFOLIO_DETAIL_MENU+

        ViewConstants.WAIT_LOAD_MESSAGE+
        "Value of the portfolio on that day: 0.00\n"+
        ViewConstants.LOAD_FLEX_PORTFOLIO_DETAIL_MENU,bytes.toString());
    assertEquals("unique name checker"+"get portfolio creation date for flexControllerTest"
        +"2022-11-13 is before date 2011-02-15"+
        "\ngetting portfolio value for flexControllerTest for the date " + date,log.toString());
  }

  @Test
  public void testLoadSinglePortfolioHistoricValue(){
    in = new ByteArrayInputStream("flexControllerTest\n3\n2018-09-28\n9".getBytes());
    LoadSingleFlexPortfolioDetail.loadSinglePortfolioDetailController(new Scanner(in),model,out);
    assertEquals(ViewConstants.ASK_NAME_OF_PORTFOLIO+
        "For the portfolio: flexControllerTest\n"+
        ViewConstants.LOAD_FLEX_PORTFOLIO_DETAIL_MENU+
        ViewConstants.ASK_DATE+
        ViewConstants.WAIT_LOAD_MESSAGE+
        "Value of the portfolio on that day: 0.00\n"+
        ViewConstants.LOAD_FLEX_PORTFOLIO_DETAIL_MENU,bytes.toString());
    assertEquals("unique name checker"+"checking the date 2018-09-28"+
        "get portfolio creation date for flexControllerTest"+
        "2018-09-28 is before date 2011-02-15"+
        "\ngetting portfolio value for flexControllerTest for the date 2018-09-28" ,log.toString());
  }

  @Test
  public void testLoadSinglePortfolioBuyStock(){
    in = new ByteArrayInputStream("flexControllerTest\n4\naapl\n6\n4.8\n2018-09-28\n9".getBytes());
    LoadSingleFlexPortfolioDetail.loadSinglePortfolioDetailController(new Scanner(in),model,out);
    assertEquals(ViewConstants.ASK_NAME_OF_PORTFOLIO+
        "For the portfolio: flexControllerTest\n"+
        ViewConstants.LOAD_FLEX_PORTFOLIO_DETAIL_MENU+
        ViewConstants.WAIT_LOAD_MESSAGE+
        "            Portfolio creation date      2011-02-15\n"
            + "----------------------------------------------------------------------\n"
            + "  Ticker Symbol        Quantity         Last Transaction Date\n"
            + " ----------------------------------------------------------------------\n"
            + "        MSFT          2013-06-18                  10.00\n"
            + "        AAPL          2016-03-29                  17.00\n"
            + "------------------------------------------------------------------------\n"+
        ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_TICKER_SYMBOL+
        ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_STOCK_NUMBER+
        ViewConstants.ASK_COMMISSION_FEE+
        ViewConstants.ASK_DATE+
        ViewConstants.SUCCESSFUL_TRANSACTION+
        ViewConstants.LOAD_FLEX_PORTFOLIO_DETAIL_MENU,bytes.toString());

    assertEquals("unique name checker"+"get portfolio creation date for flexControllerTest"
        +"get portfolio state for flexControllerTest\n"
        + "get stock list"+"Name of ticker is AAPL"+"Name of ticker is AAPL"+
        "checking the date 2018-09-28"+"2018-09-28 is before date 2016-03-29"
        +"transaction for portfolio flexControllerTest:\nAAPL 2018-09-28 6.0 4.8" ,log.toString());
  }

  @Test
  public void testLoadSinglePortfolioSellStock(){
    in = new ByteArrayInputStream("flexControllerTest\n5\naapl\n6\n4.8\n2018-09-28\n9".getBytes());
    LoadSingleFlexPortfolioDetail.loadSinglePortfolioDetailController(new Scanner(in),model,out);
    assertEquals(ViewConstants.ASK_NAME_OF_PORTFOLIO+
        "For the portfolio: flexControllerTest\n"+
        ViewConstants.LOAD_FLEX_PORTFOLIO_DETAIL_MENU+
        ViewConstants.WAIT_LOAD_MESSAGE+"\n"
        + "----------------------------------------------------------------------\n"
        + "  Ticker Symbol        Quantity         Last Transaction Date\n"
        + " ----------------------------------------------------------------------\n"
        + "        MSFT          2013-06-18                  10.00\n"
        + "        AAPL          2016-03-29                  17.00\n"
        + "------------------------------------------------------------------------\n"+
        ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_TICKER_SYMBOL+
        ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_STOCK_NUMBER+
        ViewConstants.ASK_COMMISSION_FEE+
        ViewConstants.ASK_DATE+
        ViewConstants.SUCCESSFUL_TRANSACTION+
        ViewConstants.LOAD_FLEX_PORTFOLIO_DETAIL_MENU,bytes.toString());
    assertEquals("unique name checker"+"get portfolio state for flexControllerTest"+
        "Name of ticker is AAPL"+"Name of ticker is AAPL"+"checking the date 2018-09-28"+
        "2018-09-28 is before date 2016-03-29"+
        "transaction for portfolio flexControllerTest:\n"
        + "AAPL 2018-09-28 -6.0 4.8",log.toString());
  }

  @Test
  public void testLoadSinglePortfolioCostBasis(){
    in = new ByteArrayInputStream("flexControllerTest\n6\n2018-09-28\n9".getBytes());
    LoadSingleFlexPortfolioDetail.loadSinglePortfolioDetailController(new Scanner(in),model,out);
    assertEquals(ViewConstants.ASK_NAME_OF_PORTFOLIO+
        "For the portfolio: flexControllerTest\n"+
        ViewConstants.LOAD_FLEX_PORTFOLIO_DETAIL_MENU+
        ViewConstants.WAIT_LOAD_MESSAGE+
        ViewConstants.ASK_DATE+
        "\nCost basis of the portfolio till the given date : 0.0\n"+
        ViewConstants.LOAD_FLEX_PORTFOLIO_DETAIL_MENU,bytes.toString());

    assertEquals("unique name checker"+"checking the date 2018-09-28"+
        "get portfolio creation date for flexControllerTest"+"2018-09-28 is before date 2011-02-15"
        +"checking the date 2018-09-28"+"get portfolio creation date for flexControllerTest"+
        "2018-09-28 is before date 2011-02-15"+
         "Cost basis of portfolio flexControllerTest for the date 2018-09-28",log.toString());
  }

  @Test
  public void testLoadSinglePortfolioGraph(){
    in = new ByteArrayInputStream("flexControllerTest\n7\n2022-01-01\n2022-06-06\n9".getBytes());
    LoadSingleFlexPortfolioDetail.loadSinglePortfolioDetailController(new Scanner(in),model,out);
    assertEquals(ViewConstants.ASK_NAME_OF_PORTFOLIO+
        "For the portfolio: flexControllerTest\n"+
        ViewConstants.LOAD_FLEX_PORTFOLIO_DETAIL_MENU+
        ViewConstants.ASK_START_DATE_FOR_GRAPH+
        ViewConstants.ASK_DATE+
        ViewConstants.ASK_END_DATE_FOR_GRAPH+
        ViewConstants.ASK_DATE+"\n"+
        "\t\tPerformance of portfolio flexControllerTest from 2022-01-01 to 2022-06-06\n"
        + "\tJAN 2022 : * * * * * * * \n"
        + "\tFEB 2022 : * * * * * * * * * \n"
        + "\tMAR 2022 : * * * * * * * * * * * * * * * * \n"
        + "\tAPR 2022 : * * * * * * * * * * * * * * * * * * * * * * * * * * * * \n"
        + "\tMAY 2022 : * * * * * \n"
        + "\n"
        + "\t Scale : * = 235"+"\n\n"+
        ViewConstants.LOAD_FLEX_PORTFOLIO_DETAIL_MENU,bytes.toString());

    assertEquals("unique name checker"+"checking the date 2022-01-01"+
        "checking the date 2022-06-06"+
        "get graph data for flexControllerTest from 2022-01-01 to date 2022-06-06",log.toString());
  }

  @Test
  public void testLoadSinglePortfolioBackToPortfolioList(){
    in = new ByteArrayInputStream("flexControllerTest\n8\n2".getBytes());
    LoadSingleFlexPortfolioDetail.loadSinglePortfolioDetailController(new Scanner(in),model,out);
    assertEquals(ViewConstants.ASK_NAME_OF_PORTFOLIO+
        "For the portfolio: flexControllerTest\n"+
        ViewConstants.LOAD_FLEX_PORTFOLIO_DETAIL_MENU+
        "\nYour portfolio lists : \n"+
        "* flexTest\n"+ViewConstants.LOAD_PORTFOLIO_MENU,bytes.toString());

    assertEquals("unique name checker"+"get Portfolios list",log.toString());
  }







}