package controller.Commands;

import java.io.IOException;
import java.io.PrintStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import model.UserFlex;
import view.ViewPrint;

public class BuyStock {

  public static void buyStockToPortfolio(String portfolioName, Scanner scan, UserFlex user,PrintStream out)  {
    ViewPrint.waitLoadMessage(out);
    String portfolioCreationDate = user.getPortfolioCreationDate(portfolioName);
    Map<String, SimpleEntry<String, Double>> portfolioState = user.getPortfolioState(portfolioName);
    System.out.println("\nPortfolio creation date "+portfolioCreationDate);
    System.out.println("    Stock     Quantity      Last Transaction Date");
    for(String ticker : portfolioState.keySet()){
      System.out.println("    "+ticker+"       "+portfolioState.get(ticker).getValue()+"         "+portfolioState.get(ticker).getKey());
    }

    System.out.println("\n");
    Set<String> stockList = user.getStockList();
    if (stockList != null && stockList.size() != 0) {
      ViewPrint.printAvailableStocks(stockList);
    }

    String ticker = AskTicker.addStocksAskTicker(scan, user, out);
    if (ticker == null) {
      return;
    }
    Double stockQuanDouble = AskStockNumber.addStocksAskStockNumber(scan, out);
    if (stockQuanDouble == null) {
      return;
    }

    Double commFee = AskCommissionFees.AskCommissionFees(scan, out);
    if(commFee==null){
      return;
    }

    // ticker vs date vs quantity vs commission fee
    SimpleEntry<String,SimpleEntry<String,SimpleEntry<Double, Double>>> newStock=null;
    String date;


    do{
      ViewPrint.askDate(out);
      date = scan.nextLine();
      if (date.equals("0")) {
        return ;
      }
      if(!user.dateChecker(date)){
        System.out.println("Please enter in the correct format in the given range(0 to return to list view) : ");
      } else if(portfolioState.containsKey(ticker)){
        if(user.isBeforeDate(date, portfolioState.get(ticker).getKey())){
          System.out.println("kindly enter date after latest transaction for this stock(0 to return to list view) : ");
        }
        else{
          newStock = new SimpleEntry<>(ticker, new SimpleEntry<>(date,new SimpleEntry<>(stockQuanDouble,commFee)));
          break;
        }
      }
      else{
        if(user.isBeforeDate(date, portfolioCreationDate)){
          System.out.println("kindly enter date after portfolio creation(0 to return to list view) : ");
        }
        else{
          newStock = new SimpleEntry<>(ticker, new SimpleEntry<>(date,new SimpleEntry<>(stockQuanDouble,commFee)));
          break;
        }
      }
    }
    while(!user.dateChecker(date) || newStock==null);

    boolean val = user.buyStockForPortfolio(portfolioName,newStock);
    if (val) {
      System.out.println("transaction successful for the portfolio");
    } else {
      System.out.println("transaction unsuccessful");
    }

  }

}
