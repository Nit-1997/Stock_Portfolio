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
    System.out.println();
    System.out.println();
    System.out.println("-------------Portfolio Creation-------------");
    System.out.print("  1. Add name of the portfolio : ");
  }

  public void addStocksInPortfolioWelcomeNote(String portfolioName){
    System.out.println("-------------For the Portfolio "+portfolioName+": Add stocks------------");
  }

  public void addStocksInPortfolioAskTickerSymbol(){
    System.out.println();
    System.out.print("Ticker symbol of the company: ");
  }

  public void addStocksInPortfolioAskStockNumber(){
    System.out.print("Number of stocks for the company: ");
  }

  public void addStocksInPortfolioConfirmation(){
    System.out.println();
    System.out.println("Stock added successfully to the portfolio!!");
    System.out.print("Do you want to add more stocks or remove any stocks (y/n)? ");
  }

  public void stocksInPortfolioAddOrRemoveMenu(){
    System.out.println("1. Add more stocks");
    System.out.println("2. Remove any stocks.");
    System.out.println("3. Save the portfolio. ");
    System.out.print("Enter your choice: ");
  }

  public void removeStocksInPortfolioAskTickerSymbol(){
    System.out.println();
    System.out.print("Ticker symbol of the company to be removed: ");
  }

  public void removeStocksInPortfolioAskStockNumber(){
    System.out.print("Number of stocks for the company: ");
  }

  public void removeStocksInPortfolioSuccessfulConfirmation(){
    System.out.println();
    System.out.println("Stock removed successfully to the portfolio!!");
    System.out.print("Do you want to add more stocks or remove any stocks (y/n)? ");
  }

  public void removeStocksInPortfolioUnSuccessfulConfirmation(){
    System.out.println();
    System.out.println("Stocks can't be removed!!");
    System.out.print("Do you want to add more stocks or remove any stocks (y/n)? ");
  }

  public void addStocksInPortfolioConfirmationLoading(String name){
    System.out.println();
    System.out.println(name+" Portfolio has been added to your list of portfolios");
  }

  public static void main(String[] args) {
    Print obj = new Print();
    obj.welcomeNote();
    obj.printMenu();
    obj.addPortfolio();

  }

}
