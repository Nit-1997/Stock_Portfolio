package view;

import constants.ViewConstants;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * This is the view class that contains all the printing function for the application.
 */
public class ViewPrint {

  public static final void welcomeNote(PrintStream out) {
    out.println(ViewConstants.WELCOME_MESSAGE);
  }

  public static final void printMenu(PrintStream out) {
    out.print(ViewConstants.MENU_MESSAGE);
  }

  public static final void errorNote(PrintStream out) {
    out.print(ViewConstants.ERROR_NOTE);
  }

  public static final void exitNote(PrintStream out) {
    out.print(ViewConstants.EXIT_NOTE);
  }


  public static final void addPortfolio(PrintStream out) {
    out.print(ViewConstants.ADD_PORTFOLIO);
  }

  public static final void askPortfolioNameAgain(PrintStream out) {
    out.print(ViewConstants.ASK_PORTFOLIO_NAME_AGAIN);
  }

  public static final void stockNotInPortfolio(PrintStream out) {
    out.print(ViewConstants.STOCK_NOT_IN_PORTFOLIO);
  }

  public static final void addStocksInPortfolioWelcomeNote(String portfolioName, PrintStream out) {
    out.println("-------------For the Portfolio " + portfolioName + ": Add stocks------------");
  }

  /**
   * print available stocks.
   * @param stockList list of stocks.
   */
  public static final void printAvailableStocks(Set<String> stockList) {
    System.out.println("List of available stocks");
    int i = 0;
    for (String stockName : stockList) {
      System.out.print(stockName + "\t\t\t");
      i++;
      if (i == 10) {
        System.out.println();
      }
    }
    System.out.println();
  }

  public static final void askTickerSymbol(PrintStream out) {
    out.print(ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_TICKER_SYMBOL);
  }

  public static final void askTickerSymbolAgain(PrintStream out) {
    out.print(ViewConstants.ASK_TICKER_SYMBOL_AGAIN);
  }

  public static final void askStockNumber(PrintStream out) {
    out.print(ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_STOCK_NUMBER);
  }

  public static final void askStockNumberAgain(PrintStream out) {
    out.print(ViewConstants.ASK_STOCK_NUMBER_AGAIN);
  }

  public static final void addStocksInPortfolioConfirmation(PrintStream out) {
    out.print(ViewConstants.ADD_STOCKS_IN_PORTFOLIO_CONFIRMATION);
  }

  public static final void stocksInPortfolioAddOrRemoveMenu(PrintStream out) {
    out.print(ViewConstants.STOCKS_IN_PORTFOLIO_ADD_OR_REMOVE_MENU);
  }

  public static final void removeStocksInPortfolioSuccessfulConfirmation(PrintStream out) {
    out.print(ViewConstants.REMOVE_STOCKS_IN_PORTFOLIO_SUCCESSFUL_CONFIRMATION);
  }

  public static final void removeStocksInPortfolioUnSuccessfulConfirmation(PrintStream out) {
    out.print(ViewConstants.REMOVE_STOCKS_IN_PORTFOLIO_UNSUCCESSFUL_CONFIRMATION);
  }

  public static final void askconfirmation(PrintStream out) {
    out.print(ViewConstants.ASK_CONFIRMATION);
  }

  public static final void waitMessage(PrintStream out) {
    out.println(ViewConstants.WAIT_MESSAGE);
  }

  public static final void addStocksInPortfolioConfirmationLoading(String name, PrintStream out) {
    out.println("\n" + name + " Portfolio has been added to your list of portfolios");
  }

  public static final void addStocksInPortfolioErrorNode(PrintStream out) {
    out.print(ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ERROR_NOTE);
  }


  /**
   * print portfolios.
   * @param portfolioNames portfolio names
   * @param out out stream.
   */
  public static final void printPortfolios(Set<String> portfolioNames, PrintStream out) {
    out.println();
    out.println("Your portfolio lists : ");
    for (String portfolio : portfolioNames) {
      out.println("* " + portfolio);
    }
  }

  public static final void loadPortfolioMenu(PrintStream out) {
    out.print(ViewConstants.LOAD_PORTFOLIO_MENU);
  }

  public static final void askChoice(PrintStream out) {
    out.print("Enter your choice: ");
  }

  public static final void askNameOfPortfolio(PrintStream out) {
    out.print(ViewConstants.ASK_NAME_OF_PORTFOLIO);
  }

  public static final void askPortfolioNameAgainUnique(PrintStream out) {
    out.print(ViewConstants.ASK_PORTFOLIO_NAME_AGAIN_UNIQUE);
  }

  public static final void portfolioDetailWelcomeNote(String name, PrintStream out) {
    out.println("For the portfolio: " + name);
  }

  public static final void loadPortfolioDetailMenu(PrintStream out) {
    out.print(ViewConstants.LOAD_PORTFOLIO_DETAIL_MENU);
  }

  public static final void loadFlexPortfolioDetailMenu(PrintStream out){
    System.out.println("1. Summary of the portfolio");
    System.out.println("2. Current value of the portfolio");
    System.out.println("3. Historical value of the portfolio");
    System.out.println("4. Add stocks to the portfolio");
    System.out.println("5. Sell stocks from the portfolio");
    System.out.println("6. Cost basis of the portfolio");
    System.out.println("7. Go back to list view menu");
    System.out.println("8. Go to main menu");
    System.out.print("Enter your choice: ");
  }

  public static final void waitLoadMessage(PrintStream out) {
    out.print(ViewConstants.WAIT_LOAD_MESSAGE);
  }

  public static final void askDate(PrintStream out) {
    out.print(ViewConstants.ASK_DATE);
  }

  public static final void askDateAgain(PrintStream out) {
    out.print(ViewConstants.ASK_DATE_AGAIN);
  }

  public static final void printInCompatiblePortfolio(PrintStream out) {
    out.print(ViewConstants.PRINT_INCOMPATIBLE_PORTFOLIO);
  }

  /**
   * print portfolio summary.
   * @param stockMap map of stocks
   * @param out out stream.
   */
  public static final void printPortfolioSummary(Map<String, Double> stockMap, PrintStream out) {
    out.printf("%40s", "Table for portfolio summary");
    out.println();
    out.println("----------------------------------------------------------");
    out.printf("%20s %20s", "Ticker Symbol", "Quantity");
    out.println();
    out.println("----------------------------------------------------------");
    for (String key : stockMap.keySet()) {
      out.format("%15s %23s ", key, stockMap.get(key));
      out.println();
    }
    out.println("----------------------------------------------------------");
    out.println();
  }

  /**
   * print portfolio detail.
   * @param mapDetail map detail.
   * @param portfolioValue portfolio value
   * @param out out stream.
   */
  public static final void printPortfolioDetail(Map<String, List<Double>> mapDetail,
      double portfolioValue, PrintStream out) {
    final DecimalFormat df = new DecimalFormat("0.00");
    out.printf("%60s", "Table for portfolio details");
    out.println();
    out.println(
        "------------------------------------------------------------------------------------------"
    );
    out.printf("%15s %15s %17s %12s %15s", "Ticker Symbol", "Quantity", "Buy price",
        " Price on asked Date", "Performance");
    out.println();
    out.println(
        "------------------------------------------------------------------------------------------"
    );
    for (String key : mapDetail.keySet()) {
      out.format("%12s", key);
      for (Double val : mapDetail.get(key)) {
        out.format("%17s", df.format(val));
      }
      out.println();
    }
    out.println(
        "-------------------------------------------------------------------------------------"
            + "-----");
    out.printf("%60s", "Value of the portfolio on that day: ");
    out.println(df.format(portfolioValue));

  }

  /**
   * print portfolio performance.
   * @param portfolioPnL pnl of portfolio.
   * @param out out stream.
   */
  public static final void printPortfolioPerformance(Double portfolioPnL, PrintStream out) {
    final DecimalFormat df = new DecimalFormat("0.00");
    portfolioPnL = Double.parseDouble(df.format(portfolioPnL));
    if (portfolioPnL > 0) {
      out.println(
          "Your portfolio has earned a profit of " + df.format(portfolioPnL) + " on that day.");
    } else if (portfolioPnL < 0) {
      out.println(
          "Your portfolio has been in loss of " + df.format(portfolioPnL) + " on that day.");
    } else {
      out.println("Your portfolio has the same value as that of buying day");
    }
  }

  /**
   * print portfolio value.
   * @param portfolioValue portfolio value.
   * @param out out stream.
   */
  public static final void printPortfolioValue(Double portfolioValue, PrintStream out) {

    out.println(
        "Value of the portfolio on that day: "
            + new DecimalFormat("0.00").format(portfolioValue));
  }

  public static final void loadPortfolioErrorNote(PrintStream out) {
    out.print(ViewConstants.LOAD_PORTFOLIO_ERROR_NOTE);
  }

}
