package view;

import constants.ViewConstants;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * This is the view class that contains all the printing function for the application.
 */
public class ViewPrint implements IView {

  @Override
  public void welcomeNote(PrintStream out) {
    out.println(ViewConstants.WELCOME_MESSAGE);
  }

  @Override
  public void welcomeMenu(PrintStream out) {
    out.print(ViewConstants.WELCOME_MENU);
  }

  @Override
  public void flexiblePortfolioHeader(PrintStream out) {
    out.print(ViewConstants.FLEXIBLE_PORTFOLIO_HEADER);
  }

  @Override
  public void flexPortfolioExitMsg(PrintStream out) {
    out.print(ViewConstants.FLEX_PORTFOLIO_EXIT_MSG);
  }

  @Override
  public void printMenu(PrintStream out) {
    out.print(ViewConstants.MENU_MESSAGE);
  }

  @Override
  public void errorNote(PrintStream out) {
    out.print(ViewConstants.ERROR_NOTE);
  }

  @Override
  public void exitNote(PrintStream out) {
    out.print(ViewConstants.EXIT_NOTE);
  }


  @Override
  public void addPortfolio(PrintStream out) {
    out.print(ViewConstants.ADD_PORTFOLIO);
  }

  @Override
  public void askPortfolioNameAgain(PrintStream out) {
    out.print(ViewConstants.ASK_PORTFOLIO_NAME_AGAIN);
  }

  @Override
  public void stockNotInPortfolio(PrintStream out) {
    out.print(ViewConstants.STOCK_NOT_IN_PORTFOLIO);
  }

  @Override
  public void addStocksInPortfolioWelcomeNote(String portfolioName, PrintStream out) {
    out.println("-------------For the Portfolio " + portfolioName + ": Add stocks------------");
  }

  @Override
  public void printAvailableStocks(Set<String> stockList) {
    System.out.print("\nList of available stocks : ");
    System.out.println("---------------NASDAQ 100---------------");
    //    int i = 0;
    //    for (String stockName : stockList) {
    //      System.out.print(stockName + "\t\t\t");
    //      i++;
    //      if (i == 10) {
    //        System.out.println();
    //      }
    //    }
    //    System.out.println();
  }

  @Override
  public void askTickerSymbol(PrintStream out) {
    out.print(ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_TICKER_SYMBOL);
  }

  @Override
  public void askTickerSymbolAgain(PrintStream out) {
    out.print(ViewConstants.ASK_TICKER_SYMBOL_AGAIN);
  }

  @Override
  public void askStockNumber(PrintStream out) {
    out.print(ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ASK_STOCK_NUMBER);
  }

  @Override
  public void askStockNumberAgain(PrintStream out) {
    out.print(ViewConstants.ASK_STOCK_NUMBER_AGAIN);
  }

  @Override
  public void askCommissionFee(PrintStream out) {
    out.print(ViewConstants.ASK_COMMISSION_FEE);
  }

  @Override
  public void askCommissionFeeAgain(PrintStream out) {
    out.print(ViewConstants.ASK_COMMISSION_FEE_AGAIN);
  }

  @Override
  public void addStocksInPortfolioConfirmation(PrintStream out) {
    out.print(ViewConstants.ADD_STOCKS_IN_PORTFOLIO_CONFIRMATION);
  }

  @Override
  public void stocksInPortfolioAddOrRemoveMenu(PrintStream out) {
    out.print(ViewConstants.STOCKS_IN_PORTFOLIO_ADD_OR_REMOVE_MENU);
  }

  @Override
  public void removeStocksInPortfolioSuccessfulConfirmation(PrintStream out) {
    out.print(ViewConstants.REMOVE_STOCKS_IN_PORTFOLIO_SUCCESSFUL_CONFIRMATION);
  }

  @Override
  public void removeStocksInPortfolioUnSuccessfulConfirmation(PrintStream out) {
    out.print(ViewConstants.REMOVE_STOCKS_IN_PORTFOLIO_UNSUCCESSFUL_CONFIRMATION);
  }

  @Override
  public void askconfirmation(PrintStream out) {
    out.print(ViewConstants.ASK_CONFIRMATION);
  }

  @Override
  public void waitMessage(PrintStream out) {
    out.println(ViewConstants.WAIT_MESSAGE);
  }

  @Override
  public void addStocksInPortfolioConfirmationLoading(String name, PrintStream out) {
    out.println("\n" + name + " Portfolio has been added to your list of portfolios");
  }

  @Override
  public void addStocksInPortfolioErrorNode(PrintStream out) {
    out.print(ViewConstants.ADD_STOCKS_IN_PORTFOLIO_ERROR_NOTE);
  }

  @Override
  public void unsuccessfulPortolioCreationMsg(PrintStream out) {
    out.print(ViewConstants.UNSUCCESSFUL_PORTFOLIO_CREATION_MSG);
  }


  @Override
  public void printPortfolios(Set<String> portfolioNames, PrintStream out) {
    out.println();
    out.println("Your portfolio lists : ");
    for (String portfolio : portfolioNames) {
      out.println("* " + portfolio);
    }
  }

  @Override
  public void loadPortfolioMenu(PrintStream out) {
    out.print(ViewConstants.LOAD_PORTFOLIO_MENU);
  }

  @Override
  public void askChoice(PrintStream out) {
    out.print("Enter your choice: ");
  }

  @Override
  public void askNameOfPortfolio(PrintStream out) {
    out.print(ViewConstants.ASK_NAME_OF_PORTFOLIO);
  }

  @Override
  public void askPortfolioNameAgainUnique(PrintStream out) {
    out.print(ViewConstants.ASK_PORTFOLIO_NAME_AGAIN_UNIQUE);
  }

  @Override
  public void portfolioDetailWelcomeNote(String name, PrintStream out) {
    out.println("For the portfolio: " + name);
  }

  @Override
  public void loadPortfolioDetailMenu(PrintStream out) {
    out.print(ViewConstants.LOAD_PORTFOLIO_DETAIL_MENU);
  }

  @Override
  public void loadFlexPortfolioDetailMenu(PrintStream out) {
    out.print(ViewConstants.LOAD_FLEX_PORTFOLIO_DETAIL_MENU);
  }

  @Override
  public void waitLoadMessage(PrintStream out) {
    out.print(ViewConstants.WAIT_LOAD_MESSAGE);
  }

  @Override
  public void askDate(PrintStream out) {
    out.print(ViewConstants.ASK_DATE);
  }

  @Override
  public void askDateAgain(PrintStream out) {
    out.print(ViewConstants.ASK_DATE_AGAIN);
  }

  @Override
  public void wrongDateMsg(PrintStream out) {
    out.print(ViewConstants.WRONG_DATE_MSG);
  }

  @Override
  public void wrongDateBeforeLastTx(PrintStream out) {
    out.print(ViewConstants.WRONG_DATE_BEFORE_LAST_TX);
  }

  @Override
  public void wrongDateBeforePortfolioCreation(PrintStream out) {
    out.print(ViewConstants.WRONG_DATE_BEFORE_PORTFOLIO_CREATION);
  }

  @Override
  public void successfulTransaction(PrintStream out) {
    out.print(ViewConstants.SUCCESSFUL_TRANSACTION);
  }

  @Override
  public void unSuccessfulTransaction(PrintStream out) {
    out.print(ViewConstants.UNSUCCESSFUL_TRANSACTION);
  }

  @Override
  public void printInCompatiblePortfolio(PrintStream out) {
    out.print(ViewConstants.PRINT_INCOMPATIBLE_PORTFOLIO);
  }

  @Override
  public void stockNotInPortfolioMsg(PrintStream out) {
    out.print(ViewConstants.STOCK_NOT_IN_PORTFOLIO_MSG);
  }

  @Override
  public void stockLessThanInPortfolio(PrintStream out) {
    out.print(ViewConstants.STOCK_LESS_THAN_IN_PORTFOLIO);
  }

  @Override
  public void printCostBasis(Double cost, PrintStream out) {
    out.println("\nCost basis of the portfolio till the given date : $" + cost);
  }

  @Override
  public void printPortfolioState(Map<String, SimpleEntry<String, Double>> map,
      String portfolioCreationDate, PrintStream out) {
    final DecimalFormat df = new DecimalFormat("0.00");
    if (portfolioCreationDate != null) {
      out.printf("%35s %15s", "Portfolio creation date", portfolioCreationDate);
    }
    out.println();
    out.println(
        "----------------------------------------------------------------------"
    );
    out.printf("%15s %15s %30s ", "Ticker Symbol", "Quantity", "Last Transaction Date\n");
    out.println(
        "----------------------------------------------------------------------"
    );
    for (String key : map.keySet()) {
      out.format("%12s", key);
      out.format("%23s", df.format(map.get(key).getValue()));
      out.format("%20s", map.get(key).getKey());
      out.println();
    }
    out.println(
        "------------------------------------------------------------------------");
  }

  @Override
  public void printPortfolioSummary(Map<String, Double> stockMap, PrintStream out) {
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

  @Override
  public void printPortfolioDetail(Map<String, List<Double>> mapDetail,
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
    out.println("$" + df.format(portfolioValue));

  }

  @Override
  public void printPortfolioPerformance(Double portfolioPnL, PrintStream out) {
    final DecimalFormat df = new DecimalFormat("0.00");
    portfolioPnL = Double.parseDouble(df.format(portfolioPnL));
    if (portfolioPnL > 0) {
      out.println(
          "Your portfolio has earned a profit of $" + df.format(portfolioPnL) + " on that day.");
    } else if (portfolioPnL < 0) {
      out.println(
          "Your portfolio has been in loss of $" + df.format(portfolioPnL) + " on that day.");
    } else {
      out.println("Your portfolio has the same value as that of buying day");
    }
  }

  @Override
  public void printPortfolioValue(Double portfolioValue, PrintStream out) {

    out.println(
        "Value of the portfolio on that day: $"
            + new DecimalFormat("0.00").format(portfolioValue));
  }

  @Override
  public void loadPortfolioErrorNote(PrintStream out) {
    out.print(ViewConstants.LOAD_PORTFOLIO_ERROR_NOTE);
  }

  @Override
  public void askStartDateForGraph(PrintStream out) {
    out.print(ViewConstants.ASK_START_DATE_FOR_GRAPH);
  }

  @Override
  public void askEndDateForGraph(PrintStream out) {
    out.print(ViewConstants.ASK_END_DATE_FOR_GRAPH);
  }

  @Override
  public void graphInvalidRange(PrintStream out) {
    out.print(ViewConstants.GRAPH_INVALID_RANGE);
  }

  @Override
  public void printGraph(PrintStream out, String date1, String date2,
      String portfolioName, List<String> labels, List<Integer> starPoints, int scale,
      double baseAmount) {
    out.println(
        "\n\t\tPerformance of portfolio " + portfolioName + " from " + date1 + " to " + date2);

    for (int i = 0; i < labels.size(); i++) {
      out.print("\t" + labels.get(i) + " : ");
      for (int star = 0; star < starPoints.get(i); star++) {
        out.print("* ");
      }
      out.println();
    }

    if (baseAmount == 0) {
      out.println("\n\t Scale : * = $" + scale + "\n");
    } else {
      out.println("\n\t\tone * is $" + scale + " more than a base amount of $" + new DecimalFormat(
          "0.00").format(baseAmount));
      out.println("\t\t(zero * approximates value to baseAmount)\n");
    }

  }

  @Override
  public void printError(Exception e, PrintStream out) {
    out.println("\n" + e.getMessage().substring(e.getMessage().indexOf(": ") + 1));
  }

}
