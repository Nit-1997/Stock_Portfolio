package controller.Commands;

import java.io.IOException;
import java.io.PrintStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Scanner;
import model.UserFlex;
import view.ViewPrint;

public class BuyStock {

  public static void buyStockToPortfolio(String portfolioName, Scanner scan, UserFlex user,PrintStream out)  {
    ViewPrint.waitLoadMessage(out);
    String portfolioCreationDate = user.getPortfolioCreationDate(portfolioName);
    Map<String, SimpleEntry<String, Double>> portfolioState = user.getPortfolioState(portfolioName);
    // print portfolioState

    String ticker = AskTicker.addStocksAskTicker(scan, user, out);
    if (ticker == null) {
      return;
    }
    Double stockQuanDouble = AskStockNumber.addStocksAskStockNumber(scan, out);
    if (stockQuanDouble == null) {
      return;
    }
    SimpleEntry<String,SimpleEntry<String,Double>> newStock=null;
    String date;
    do{
      ViewPrint.askDate(out);
      date = scan.nextLine();
      if(!user.dateChecker(date)) continue;
      if(portfolioState.containsKey(ticker)){
        if(user.isBeforeDate(portfolioState.get(ticker).getKey(),date)){
          System.out.println("kindly enter date after latest transaction for this stock");
        }
        else{
          newStock = new SimpleEntry<>(ticker, new SimpleEntry<>(date,stockQuanDouble));
          break;
        }
      }
      else{
        if(user.isBeforeDate(portfolioCreationDate,date)){
          System.out.println("kindly enter date after portfolio creation");
        }
        else{
          newStock = new SimpleEntry<>(ticker, new SimpleEntry<>(date,stockQuanDouble));
          break;
        }
      }
    }
    while(!user.dateChecker(date));

    user.buyStockForPortfolio(portfolioName,newStock);

  }

}
