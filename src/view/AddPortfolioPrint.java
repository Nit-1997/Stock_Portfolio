package view;

import constants.Constants;
import java.io.PrintStream;
import java.util.Set;

public class AddPortfolioPrint{


  public static void addPortfolio(PrintStream out) {
    out.print(Constants.AddPortfolio);
  }

  public static void askPortfolioNameAgain(PrintStream out){
    out.print("Portfolio with this name already exists, kindly enter another name (0 to return to the main menu): ");
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
    out.print(Constants.AddStocksInPortfolioAskTickerSymbol);
  }

  public static void askStockNumber(PrintStream out){
    out.print(Constants.AddStocksInPortfolioAskStockNumber);
  }

  public static void addStocksInPortfolioConfirmation(PrintStream out){
    out.print(Constants.AddStocksInPortfolioConfirmation);
  }

  public static void stocksInPortfolioAddOrRemoveMenu(PrintStream out){
    out.print(Constants.StocksInPortfolioAddOrRemoveMenu);
  }

  public static void removeStocksInPortfolioSuccessfulConfirmation(PrintStream out){
    out.print(Constants.RemoveStocksInPortfolioSuccessfulConfirmation);
  }

  public static void removeStocksInPortfolioUnSuccessfulConfirmation(PrintStream out){
    out.print(Constants.RemoveStocksInPortfolioUnSuccessfulConfirmation);
  }

  public static void askconfirmation(PrintStream out){
    out.print("Do you want to add more stocks or remove any stocks (y/n)? ");
  }

  public static void waitMessage(PrintStream out){
    out.println("Saving your portfolio, please wait...");
  }

  public static void ctrlCPressedMessage(PrintStream out){
    out.println("Exited the program before portfolio got stored, Portfolio not saved.");
  }

  public static void addStocksInPortfolioConfirmationLoading(String name, PrintStream out){
    out.println("\n"+name+" Portfolio has been added to your list of portfolios");
  }

  public static void addStocksInPortfolioErrorNode(PrintStream out){
    out.print(Constants.AddStocksInPortfolioErrorNote);
  }


}
