package view;

import constants.ViewConstants;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ViewPrint {

  public static final void welcomeNote(PrintStream out){
    out.println(ViewConstants.WelcomeMessage);
  }

  public static final void printMenu(PrintStream out){
    out.print(ViewConstants.MenuMessage);
  }

  public static final void errorNote(PrintStream out){
    out.print(ViewConstants.ErrorNote);
  }

  public static final void exitNote(PrintStream out){
    out.print(ViewConstants.ExitNote);
  }




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
    out.print(ViewConstants.AskTickerSymbolAgain);
  }

  public static void askStockNumber(PrintStream out){
    out.print(ViewConstants.AddStocksInPortfolioAskStockNumber);
  }

  public static void askStockNumberAgain(PrintStream out){
    out.print(ViewConstants.AskStockNumberAgain);
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




  public static void printPortfolios(Set<String> portfolioNames, PrintStream out){
    out.println();
    out.println("Your portfolio lists : ");
    for (String portfolio : portfolioNames) {
      out.println("* "+portfolio);
    }
  }

  public static void loadPortfolioMenu(PrintStream out){
    out.print(ViewConstants.LoadPortfolioMenu);
  }

  public static void askChoice(PrintStream out){
    out.print("Enter your choice: ");
  }

  public static void askNameOfPortfolio(PrintStream out){
    out.print(ViewConstants.AskNameOfPortfolio);
  }

  public static void askPortfolioNameAgainUnique(PrintStream out){
    out.print(ViewConstants.AskPortfolioNameAgainUnique);
  }

  public static void portfolioDetailWelcomeNote(String name, PrintStream out){
    out.println("For the portfolio: "+name);
  }

  public static void loadPortfolioDetailMenu(PrintStream out){
    out.print(ViewConstants.loadPortfolioDetailMenu);
  }

  public static void waitLoadMessage(PrintStream out){
    out.print(ViewConstants.WaitLoadMessage);
  }

  public static void askDate(PrintStream out){
    out.print(ViewConstants.AskDate);
  }

  public static void askDateAgain(PrintStream out){
    out.print(ViewConstants.AskDateAgain);
  }

  public static void printInCompatiblePortfolio(PrintStream out){
    out.print(ViewConstants.PrintInCompatiblePortfolio);
  }

  public static void printPortfolioSummary(Map<String, Double> stockMap, PrintStream out){
    out.printf("%40s","Table for portfolio summary");
    out.println();
    out.println("----------------------------------------------------------");
    out.printf("%20s %20s", "Ticker Symbol", "Quantity");
    out.println();
    out.println("----------------------------------------------------------");
    for(String key: stockMap.keySet()){
      out.format("%15s %23s ", key, stockMap.get(key));
      out.println();
    }
    out.println("----------------------------------------------------------");
    out.println();
  }

  public static void printPortfolioDetail(Map<String, List<Double>> mapDetail, double portfolioValue, PrintStream out){
    final DecimalFormat df = new DecimalFormat("0.00");
    out.printf("%60s","Table for portfolio details");
    out.println();
    out.println("------------------------------------------------------------------------------------------");
    out.printf("%15s %15s %17s %12s %15s", "Ticker Symbol", "Quantity","Buy price"," Price on asked Date","Performance");
    out.println();
    out.println("------------------------------------------------------------------------------------------");
    for(String key: mapDetail.keySet()){
      out.format("%12s", key);
      for(Double val : mapDetail.get(key)) out.format("%17s", df.format(val));
      out.println();
    }
    out.println("------------------------------------------------------------------------------------------");
    out.printf("%60s","Value of the portfolio on that day: ");
    out.println(df.format(portfolioValue));

  }

  public static void printPortfolioPerformance(Double portfolioPnL, PrintStream out){
    final DecimalFormat df = new DecimalFormat("0.00");
    if(portfolioPnL>0){
      out.println("Your portfolio has earned a profit of "+df.format(portfolioPnL)+" on that day.");
    }
    else if(portfolioPnL<0){
      out.println("Your portfolio has been in loss of "+df.format(portfolioPnL)+" on that day.");
    }
    else{
      out.println("Your portfolio has the same value as that of buying day");
    }
  }

  public static void printPortfolioValue(Double portfolioValue, PrintStream out){

    out.println("Value of the portfolio on that day: "+new DecimalFormat("0.00").format(portfolioValue));
  }

  public static void loadPortfolioErrorNote(PrintStream out){
    out.print(ViewConstants.LoadPortfolioErrorNote);
  }

}
