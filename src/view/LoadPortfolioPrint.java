package view;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class LoadPortfolioPrint{

  public static void printPortfolios(Set<String> portfolioNames, PrintStream out){
    out.println();
    out.println("Your portfolio lists : ");
    for (String portfolio : portfolioNames) {
      out.println("* "+portfolio);
    }
  }

  public static void loadPortfolioMenu(PrintStream out){
    out.println();
    out.println("1. Details of any portfolio");
    out.println("2. Return to main menu ");
    out.print("Enter your choice: ");
  }

  public static void askChoice(PrintStream out){
    out.print("Enter your choice: ");
  }

  public static void askNameOfPortfolio(PrintStream out){
    out.print("Enter name of the portfolio ");
  }

  public static void portfolioDetailWelcomeNote(String name, PrintStream out){
    out.println("For the portfolio: "+name);
  }

  public static void loadPortfolioDetailMenu(PrintStream out){
    out.println();
    out.println("1. Current Summary of the portfolio");
    out.println("2. Current Detailed view of the portfolio");
    out.println("3. Current Performance of the portfolio");
    out.println("4. Historical Detailed view of the portfolio");
    out.println("5. Historical Performance of the portfolio");
    out.println("6. Return to the lists of portfolio");
    out.println("7. Return to the main menu");
    out.print("Enter your choice: ");
  }

  public static void askDate(PrintStream out){
    out.print("Please enter date (MM/DD/YYYY): ");
  }

  public static void printPortfolioSummary(HashMap<String, Double> stockMap, PrintStream out){
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

  public static void printPortfolioDetail(Map<String, List<Double>> mapDetail, PrintStream out){
    out.printf("%60s","Table for portfolio details");
    out.println();
    out.println("------------------------------------------------------------------------------------------");
    out.printf("%15s %15s %17s %15s %15s", "Ticker Symbol", "Buy price"," Current Price","Quantity","Performance");
    out.println();
    out.println("------------------------------------------------------------------------------------------");
    for(String key: mapDetail.keySet()){
      out.format("%12s", key);
      for(Double val : mapDetail.get(key)) out.format("%17s", val);
      out.println();
    }
    out.println("------------------------------------------------------------------------------------------");
    out.println();
  }

  public static void printPortfolioPerformance(Double portfolioPnL, PrintStream out){
    if(portfolioPnL>0){
      out.println("Your portfolio has earned a profit of "+portfolioPnL+" till today.");
    }
    else if(portfolioPnL<0){
      out.println("Your portfolio has been in loss of "+portfolioPnL+" till today.");
    }
    else{
      out.println("Your portfolio has the same value as that of buying day");
    }
  }

  public static void loadPortfolioErrorNote(PrintStream out){
    out.print("Please enter an integer value from above options: ");
  }


}
