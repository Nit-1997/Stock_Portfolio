package controller.Commands;

import java.io.PrintStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Scanner;
import model.UserFlex;
import view.ViewPrint;

public class SellStock {

  public static void sellStockFromPortfolio(String portfolioName, Scanner scan, UserFlex user,
      PrintStream out) {

    ViewPrint.waitLoadMessage(out);
    String portfolioCreationDate = user.getPortfolioCreationDate(portfolioName);
    Map<String, SimpleEntry<String, Double>> portfolioState = user.getPortfolioState(portfolioName);
    System.out.println("    Stock     Quantity      Last Transaction Date");
    for (String ticker : portfolioState.keySet()) {
      System.out.println(
          ticker + "   " + portfolioState.get(ticker).getValue() + "     " + portfolioState.get(
              ticker).getKey());
    }

    ViewPrint.askTickerSymbol(out);
    String ticker;
    do {
      ticker = scan.nextLine().toUpperCase();
      if (ticker.equals("0")) {
        return;
      }
      if (!user.isValidStock(ticker)) {
        ViewPrint.askTickerSymbolAgain(out);
        continue;
      }
      if (!portfolioState.containsKey(ticker)) {
        System.out.print(
            "Portfolio doesn't contain this stock, enter again(0 to return to main menu): ");
      }
    }
    while (!user.isValidStock(ticker) || !portfolioState.containsKey(ticker));

    Double stockQuanDouble = null;
    do{
      stockQuanDouble = AskStockNumber.addStocksAskStockNumber(scan, out);
      if (stockQuanDouble == null) {
        return;
      }
      if(portfolioState.get(ticker).getValue() < stockQuanDouble) {
        System.out.println("Shares to be sold are more than the number of shares existing");
      }
    }
    while(portfolioState.get(ticker).getValue() < stockQuanDouble);

    SimpleEntry<String, SimpleEntry<String, Double>> newStock = null;
    String date=null;
    while (newStock == null || !user.dateChecker(date)) {
      System.out.print("enter date (yyyy-mm-dd) : ");
      date = scan.nextLine();
      if (date.equals("0")) {
        return;
      }
      if (!user.dateChecker(date)) {
        System.out.println("Please enter in the correct format in the given range(0 to return to list view) : ");
      } else if (user.isBeforeDate(date, portfolioState.get(ticker).getKey())) {
        System.out.println("kindly enter date after latest transaction for this stock(0 to return to list view) : ");
      } else {
        newStock = new SimpleEntry<>(ticker, new SimpleEntry<>(date, -stockQuanDouble));
        break;
      }
    }


    boolean val = user.sellStockFromPortfolio(portfolioName, newStock);
    if (val) {
      System.out.println("transaction successful for the portfolio\n");
    } else {
      System.out.println("transaction unsuccessful\n");
    }


  }

}
