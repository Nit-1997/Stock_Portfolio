package view;

import java.io.IOException;

public class Print {

  public void welcomeNote(){
    System.out.println("-------------Welcome to stock portfolio app-------------");
  }

  public void printMenu(){
    System.out.println("  1. Add new portfolio");
    System.out.println("  2. View all portfolios");
    System.out.println("  0. Exit");

  }
  public void addPortfolio() {
    System.out.println();
    System.out.println();
    System.out.println("-------------Portfolio Creation-------------");
    System.out.print("  1. Add name of the portfolio : ");
  }


  public void addStocksInPortfolio(String portfolioName){
    System.out.println();
    System.out.println("-------------For the Portfolio "+portfolioName+": Add stocks------------");
    System.out.print("Ticker symbol of the company");
  }

  public static void main(String[] args) {
    Print obj = new Print();
    obj.welcomeNote();
    obj.printMenu();
    obj.addPortfolio();
    obj.addStocksInPortfolio("health");
  }

}
