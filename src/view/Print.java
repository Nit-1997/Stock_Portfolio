package view;

import java.io.IOException;

import constants.Constants;

public class Print {

  public void welcomeNote(){
    System.out.println(Constants.WelcomeMessage);
  }

  public void printMenu(){
    System.out.println(Constants.MenuMessage);
  }
  public void addPortfolio() {
    System.out.print(Constants.AddPortfolio);
  }

  public void addStocksInPortfolioWelcomeNote(String portfolioName){
    System.out.println("-------------For the Portfolio "+portfolioName+": Add stocks------------");
  }

  public void askTickerSymbol(){
    System.out.print(Constants.AddStocksInPortfolioAskTickerSymbol);
  }

  public void askStockNumber(){
    System.out.print(Constants.AddStocksInPortfolioAskStockNumber);
  }

  public void addStocksInPortfolioConfirmation(){
    System.out.print(Constants.AddStocksInPortfolioConfirmation);
  }

  public void stocksInPortfolioAddOrRemoveMenu(){
    System.out.print(Constants.StocksInPortfolioAddOrRemoveMenu);
  }


  public void removeStocksInPortfolioSuccessfulConfirmation(){
    System.out.print(Constants.RemoveStocksInPortfolioSuccessfulConfirmation);
  }

  public void removeStocksInPortfolioUnSuccessfulConfirmation(){
    System.out.print(Constants.RemoveStocksInPortfolioUnSuccessfulConfirmation);
  }

  public void addStocksInPortfolioConfirmationLoading(String name){
    System.out.println("\n"+name+" Portfolio has been added to your list of portfolios");
  }

  public static void main(String[] args) {
    Print obj = new Print();
    obj.welcomeNote();
    obj.printMenu();
    obj.addPortfolio();

  }

}
