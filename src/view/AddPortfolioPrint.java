package view;

import constants.Constants;

public class AddPortfolioPrint{


  public static void addPortfolio() {
    System.out.print(Constants.AddPortfolio);
  }

  public static void addStocksInPortfolioWelcomeNote(String portfolioName){
    System.out.println("-------------For the Portfolio "+portfolioName+": Add stocks------------");
  }

  public static void askTickerSymbol(){
    System.out.print(Constants.AddStocksInPortfolioAskTickerSymbol);
  }

  public static void askStockNumber(){
    System.out.print(Constants.AddStocksInPortfolioAskStockNumber);
  }

  public static void addStocksInPortfolioConfirmation(){
    System.out.print(Constants.AddStocksInPortfolioConfirmation);
  }

  public static void stocksInPortfolioAddOrRemoveMenu(){
    System.out.print(Constants.StocksInPortfolioAddOrRemoveMenu);
  }

  public static void removeStocksInPortfolioSuccessfulConfirmation(){
    System.out.print(Constants.RemoveStocksInPortfolioSuccessfulConfirmation);
  }

  public static void removeStocksInPortfolioUnSuccessfulConfirmation(){
    System.out.print(Constants.RemoveStocksInPortfolioUnSuccessfulConfirmation);
  }

  public static void addStocksInPortfolioConfirmationLoading(String name){
    System.out.println("\n"+name+" Portfolio has been added to your list of portfolios");
  }

  public static void addStocksInPortfolioErrorNode(){
    System.out.print(Constants.AddStocksInPortfolioErrorNote);
  }


}
