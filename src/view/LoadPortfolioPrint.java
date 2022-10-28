package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class LoadPortfolioPrint{

  public static void printPortfolios(Set<String> portfolioNames){
    System.out.println();
    System.out.println("Your portfolio lists : ");
    for (String portfolio : portfolioNames) {
      System.out.println("* "+portfolio);
    }
  }

  public static void loadPortfolioMenu(){
    System.out.println();
    System.out.println("1. Details of any portfolio");
    System.out.println("2. Return to main menu ");
    System.out.print("Enter your choice: ");
  }

  public static void askChoice(){
    System.out.print("Enter your choice: ");
  }

  public static void askNameOfPortfolio(){
    System.out.print("Enter name of the portfolio ");
  }

  public static void portfolioDetailWelcomeNote(String name){
    System.out.println("For the portfolio: "+name);
  }

  public static void loadPortfolioDetailMenu(){
    System.out.println();
    System.out.println("1. Summary of the portfolio");
    System.out.println("2. Detailed view of the portfolio");
    System.out.println("3. Performance of the portfolio");
    System.out.println("4. Return to the lists of portfolio");
    System.out.println("5. Return to the main menu");
    System.out.print("Enter your choice: ");
  }

  public static void printPortfolioSummary(HashMap<String, Double> stockMap){
    System.out.printf("%40s","Table for portfolio summary");
    System.out.println();
    System.out.println("----------------------------------------------------------");
    System.out.printf("%20s %20s", "Ticker Symbol", "Quantity");
    System.out.println();
    System.out.println("----------------------------------------------------------");
    for(String key: stockMap.keySet()){
      System.out.format("%15s %23s ", key, stockMap.get(key));
      System.out.println();
    }
    System.out.println("----------------------------------------------------------");
    System.out.println();
  }

  public static void printPortfolioDetail(Map<String, List<Double>> mapDetail){
    System.out.printf("%60s","Table for portfolio details");
    System.out.println();
    System.out.println("------------------------------------------------------------------------------------------");
    System.out.printf("%15s %15s %17s %15s %15s", "Ticker Symbol", "Buy price"," Current Price","Quantity","Performance");
    System.out.println();
    System.out.println("------------------------------------------------------------------------------------------");
    for(String key: mapDetail.keySet()){
      System.out.format("%12s", key);
      for(Double val : mapDetail.get(key)) System.out.format("%17s", val);
      System.out.println();
    }
    System.out.println("------------------------------------------------------------------------------------------");
    System.out.println();
  }

  public static void printPortfolioPerformance(Double portfolioPnL){
    if(portfolioPnL>0){
      System.out.println("Your portfolio has earned a profit of "+portfolioPnL+" till today.");
    }
    else if(portfolioPnL<0){
      System.out.println("Your portfolio has been in loss of "+portfolioPnL+" till today.");
    }
    else{
      System.out.println("Your portfolio has the same value as that of buying day");
    }
  }


}
