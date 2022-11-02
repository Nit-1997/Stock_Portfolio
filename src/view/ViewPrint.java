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
    out.println(ViewConstants.WelcomeMessage);
  }

  public static final void printMenu(PrintStream out) {
    out.print(ViewConstants.MenuMessage);
  }

  public static final void errorNote(PrintStream out) {
    out.print(ViewConstants.ErrorNote);
  }

  public static final void exitNote(PrintStream out) {
    out.print(ViewConstants.ExitNote);
  }


  public static final void addPortfolio(PrintStream out) {
    out.print(ViewConstants.AddPortfolio);
  }

  public static final void askPortfolioNameAgain(PrintStream out) {
    out.print(ViewConstants.AskPortfolioNameAgain);
  }

  public static final void stockNotInPortfolio(PrintStream out) {
    out.print(ViewConstants.StockNotInPortfolio);
  }

  public static final void addStocksInPortfolioWelcomeNote(String portfolioName, PrintStream out) {
    out.println("-------------For the Portfolio " + portfolioName + ": Add stocks------------");
  }

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
    out.print(ViewConstants.AddStocksInPortfolioAskTickerSymbol);
  }

  public static final void askTickerSymbolAgain(PrintStream out) {
    out.print(ViewConstants.AskTickerSymbolAgain);
  }

  public static final void askStockNumber(PrintStream out) {
    out.print(ViewConstants.AddStocksInPortfolioAskStockNumber);
  }

  public static final void askStockNumberAgain(PrintStream out) {
    out.print(ViewConstants.AskStockNumberAgain);
  }

  public static final void addStocksInPortfolioConfirmation(PrintStream out) {
    out.print(ViewConstants.AddStocksInPortfolioConfirmation);
  }

  public static final void stocksInPortfolioAddOrRemoveMenu(PrintStream out) {
    out.print(ViewConstants.StocksInPortfolioAddOrRemoveMenu);
  }

  public static final void removeStocksInPortfolioSuccessfulConfirmation(PrintStream out) {
    out.print(ViewConstants.RemoveStocksInPortfolioSuccessfulConfirmation);
  }

  public static final void removeStocksInPortfolioUnSuccessfulConfirmation(PrintStream out) {
    out.print(ViewConstants.RemoveStocksInPortfolioUnSuccessfulConfirmation);
  }

  public static final void askconfirmation(PrintStream out) {
    out.print(ViewConstants.AskConfirmation);
  }

  public static final void waitMessage(PrintStream out) {
    out.println(ViewConstants.WaitMessage);
  }

  public static final void addStocksInPortfolioConfirmationLoading(String name, PrintStream out) {
    out.println("\n" + name + " Portfolio has been added to your list of portfolios");
  }

  public static final void addStocksInPortfolioErrorNode(PrintStream out) {
    out.print(ViewConstants.AddStocksInPortfolioErrorNote);
  }


  public static final void printPortfolios(Set<String> portfolioNames, PrintStream out) {
    out.println();
    out.println("Your portfolio lists : ");
    for (String portfolio : portfolioNames) {
      out.println("* " + portfolio);
    }
  }

  public static final void loadPortfolioMenu(PrintStream out) {
    out.print(ViewConstants.LoadPortfolioMenu);
  }

  public static final void askChoice(PrintStream out) {
    out.print("Enter your choice: ");
  }

  public static final void askNameOfPortfolio(PrintStream out) {
    out.print(ViewConstants.AskNameOfPortfolio);
  }

  public static final void askPortfolioNameAgainUnique(PrintStream out) {
    out.print(ViewConstants.AskPortfolioNameAgainUnique);
  }

  public static final void portfolioDetailWelcomeNote(String name, PrintStream out) {
    out.println("For the portfolio: " + name);
  }

  public static final void loadPortfolioDetailMenu(PrintStream out) {
    out.print(ViewConstants.loadPortfolioDetailMenu);
  }

  public static final void waitLoadMessage(PrintStream out) {
    out.print(ViewConstants.WaitLoadMessage);
  }

  public static final void askDate(PrintStream out) {
    out.print(ViewConstants.AskDate);
  }

  public static final void askDateAgain(PrintStream out) {
    out.print(ViewConstants.AskDateAgain);
  }

  public static final void printInCompatiblePortfolio(PrintStream out) {
    out.print(ViewConstants.PrintInCompatiblePortfolio);
  }

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

  public static final void printPortfolioValue(Double portfolioValue, PrintStream out) {

    out.println(
        "Value of the portfolio on that day: "
            + new DecimalFormat("0.00").format(portfolioValue));
  }

  public static final void loadPortfolioErrorNote(PrintStream out) {
    out.print(ViewConstants.LoadPortfolioErrorNote);
  }

}
