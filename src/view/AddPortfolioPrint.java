package view;

import constants.Constants;
import constants.ViewConstants;
import java.io.PrintStream;
import java.util.Set;

public class AddPortfolioPrint{


  public static void addPortfolio(PrintStream out) {
    out.print(ViewConstants.AddPortfolio);
  }

  public static void askPortfolioNameAgain(PrintStream out){
    out.print(ViewConstants.AskPortfolioNameAgain);
  }

  public static void addStocksInPortfolioWelcomeNote(String portfolioName, PrintStream out){
    out.println("-------------For the Portfolio "+portfolioName+": Add stocks------------");
  }

  public static void printAvailableStocks(Set<String> stockList){
    System.out.println("List of available stocks");
    int i=0;
    for(String stockName : stockList){
      System.out.print(stockName+"\t\t\t");
      i++;
      if (i == 10) {
        System.out.println();
      }
    }
    System.out.println();
  }

  public static void askTickerSymbol(PrintStream out){
    out.print(ViewConstants.AddStocksInPortfolioAskTickerSymbol);
  }

  public static void askTickerSymbolAgain(PrintStream out){
    out.print("Portfolio doesn't contain this stock, enter again:  (0 to return to main menu): ");
  }

  public static void askStockNumber(PrintStream out){
    out.print(ViewConstants.AddStocksInPortfolioAskStockNumber);
  }

  public static void askStockNumberAgain(PrintStream out){
    System.out.print("Number entered is not in correct format, please enter again (0 to return to main menu): ");
  }

  public static void addStocksInPortfolioConfirmation(PrintStream out){
    out.print(ViewConstants.AddStocksInPortfolioConfirmation);
  }

  public static void stocksInPortfolioAddOrRemoveMenu(PrintStream out){
    out.print(ViewConstants.StocksInPortfolioAddOrRemoveMenu);
  }

  public static void removeStocksInPortfolioSuccessfulConfirmation(PrintStream out){
    out.print(ViewConstants.RemoveStocksInPortfolioSuccessfulConfirmation);
  }

  public static void removeStocksInPortfolioUnSuccessfulConfirmation(PrintStream out){
    out.print(ViewConstants.RemoveStocksInPortfolioUnSuccessfulConfirmation);
  }

  public static void askconfirmation(PrintStream out){
    out.print(ViewConstants.AskConfirmation);
  }

  public static void waitMessage(PrintStream out){
    out.println(ViewConstants.WaitMessage);
  }

  public static void addStocksInPortfolioConfirmationLoading(String name, PrintStream out){
    out.println("\n"+name+" Portfolio has been added to your list of portfolios");
  }

  public static void addStocksInPortfolioErrorNode(PrintStream out){
    out.print(ViewConstants.AddStocksInPortfolioErrorNote);
  }


}
