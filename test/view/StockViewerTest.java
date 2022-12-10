package view;

import org.junit.Before;
import org.junit.Test;

import view.terminal.StockViewer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Test the viewer's output.
 */
public class StockViewerTest {
  private StockViewer view;
  private ByteArrayOutputStream outStream;

  /**
   * Create the view with mock output.
   */
  @Before
  public void setUp() {
    this.outStream = new ByteArrayOutputStream();
    this.view = new StockViewer(new PrintStream(outStream));
  }

  /**
   * Test the clearScreen.
   */
  @Test
  public void clearScreen() {
    view.clearScreen();
    assertEquals("\u001B[2J\u001B[1;1H", outStream.toString());
  }

  /**
   * Test the showCommand.
   */
  @Test
  public void showCommand() {
    view.showCommand("Listen to me!");
    assertEquals("\033[1mCommand: Listen to me!", outStream.toString());
  }

  /**
   * Test the showMainMenu.
   */
  @Test
  public void showMainMenu() {
    view.showMainMenu();
    assertEquals("      ::::::::   :::::::::::       ::::::::       ::::    :::       :::    :::\n"
                    +
                    "    :+:    :+:      :+:          :+:    :+:      :+:+:  " +
                    " :+:       :+:   :+:\n" +
                    "   +:+             +:+          +:+    +:+      :+:+:+  +:+       +:+  +:+\n" +
                    "  +#++:++#++      +#+          +#+    +:+      +#+ +:+ +#+       +#++:++\n" +
                    "        +#+      +#+          +#+    +#+      +#+  +#+#+#       +#+  +#+\n" +
                    "#+#    #+#      #+#          #+#    #+#      #+#   #+#+#       #+#   #+#\n" +
                    "########       ###           ########       ###    ####       ###    ###\n" +
                    "                                           V1.0\n" +
                    "                                     \u001B[6;1mN\u001B[0m New Infleixble " +
                    "Portfolio\n" +
                    "                                     \u001B[6;1mF\u001B[0m New Flexible" +
                    " Portfolio\n" +
                    "                                   \u001B[6;1mV\u001B[0m View Portfolios\n" +
                    "                                         \u001B[6;1mQ\u001B[0m Quit\n" +
                    "                                   \u001B[6;1mL\u001B[0m Load Data from " +
                    "File\n" +
                    "                                         \u001B[6;1mB\u001B[0m Buy Stock\n" +
                    "                                         \u001B[6;1mS\u001B[0m Sell Stock\n" +
                    "                                         \u001B[6;1mP\u001B[0m View " +
                    "Performance Portfolio\n",
            outStream.toString());
  }

  /**
   * Test the showPortfolio.
   */
  @Test
  public void showPortfolio() {
    String expected = "                  Portfolio Name:testPort\n" +
            "                  Total Value on Date 2022-10-30   123.0\n";
    view.showPortfolio("testPort", 123.0, "2022-10-30");
    assertEquals(expected, outStream.toString());
  }

  /**
   * Test the showIndividualStocks.
   */
  @Test
  public void showIndividualStocks() {
    String[] values = {"GOOGL", "22", "123.33"};
    String expected = "                  Stock Ticker:  " + values[0]
            +
            "\n                  Stock Shares Owned:  " + values[1]
            +
            "\n                  Stock Value:  " + values[2] + "\n";
    view.showIndividualStocks(values);
    assertEquals(expected, outStream.toString());
  }

  /**
   * Test the showError.
   */
  @Test
  public void showError() {
    String expected = "                ERROR\n \n";
    view.showError(new String[]{"ERROR"});
    assertEquals(expected, outStream.toString());
  }

  /**
   * Test the portfolioCreationConfirmation.
   */
  @Test
  public void portfolioCreationConfirmation() {
    String expected = "\n                  The portfolio  testPort has been created \n \n";
    view.portfolioCreationConfirmation("testPort");
    assertEquals(expected, outStream.toString());
  }

  /**
   * Test the promptPortName.
   */
  @Test
  public void promptPortName() {
    String expected = "                                                    \n" +
            "                        Please Enter Name for Portfolio\n" +
            "                                                                         \n" +
            " ";
    view.promptPortName();
    assertEquals(expected, outStream.toString());
  }

  /**
   * Test the promptAddStock.
   */
  @Test
  public void promptAddStock() {
    String expected = "                                                    \n" +
            "              Please Enter Ticker Symbol of Stock Enter " +
            "'D' to no longer add stocks\n" +
            "       \n" +
            " ";
    view.promptAddStock();
    assertEquals(expected, outStream.toString());
  }

  /**
   * Test the promptShares.
   */
  @Test
  public void promptShares() {
    String expected = "                                                    \n" +
            "            Please Enter Number of Shares you would like to purchase\n" +
            "                                                                         \n" +
            " ";
    view.promptShares();
    assertEquals(expected, outStream.toString());
  }

  /**
   * Test the enterNamePort.
   */
  @Test
  public void enterNamePort() {
    String expected = "                                        \n" +
            "                      Please enter the name of the portfolio " +
            "you would like to view\n" +
            "                                         The possible choices are \n" +
            " ";
    view.enterNamePort();
    assertEquals(expected, outStream.toString());
  }

  /**
   * Test the promptPortfolioChoices.
   */
  @Test
  public void promptPortfolioChoices() {
    String expected = "                                  " + 1 + ")" + "testPort" + "\n";
    view.promptPortfolioChoices(1, "testPort");
    assertEquals(expected, outStream.toString());
  }

  /**
   * Test the promptDate.
   */
  @Test
  public void promptDate() {
    String expected = "      Please type in a date in the format of YYYY-MM-DD\n ";
    view.promptDate();
    assertEquals(expected, outStream.toString());
  }

  /**
   * Test the loadingDate.
   */
  @Test
  public void loadingDate() {
    String expeced = "                        Loading Data....        ";
    view.loadingData();
    assertEquals(expeced, outStream.toString());
  }

  /**
   * Test the quit.
   */
  @Test
  public void quit() {
    String expected = "\n"
            +
            "\n"
            +
            "\n"
            +
            "\n"
            +
            "                                          \033[51;1mGoodbye!\n";
    view.quit();
    assertEquals(expected, outStream.toString());
  }
}