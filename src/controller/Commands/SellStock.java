package controller.Commands;

import java.io.PrintStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Scanner;
import javax.swing.text.View;
import model.UserFlex;
import view.ViewPrint;

public class SellStock {

  public static void sellStockFromPortfolio(String portfolioName, Scanner scan, UserFlex user,
      PrintStream out) {

    ViewPrint.waitLoadMessage(out);
    Map<String, SimpleEntry<String, Double>> portfolioState = user.getPortfolioState(portfolioName);
    ViewPrint.printPortfolioState(portfolioState,null,out);

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
        ViewPrint.stockNotInPortfolioMsg(out);
      }
    }
      while (!user.isValidStock(ticker) || !portfolioState.containsKey(ticker));

      Double stockQuanDouble = null;
      do {
        stockQuanDouble = AskStockNumber.addStocksAskStockNumber(scan, out);
        if (stockQuanDouble == null) {
          return;
        }
        if (portfolioState.get(ticker).getValue() < stockQuanDouble) {
          ViewPrint.stockLessThanInPortfolio(out);
        }
      }
      while (portfolioState.get(ticker).getValue() < stockQuanDouble);

      Double commFee = AskCommissionFees.AskCommissionFees(scan, out);
      if (commFee == null) {
        return;
      }

      SimpleEntry<String, SimpleEntry<String, SimpleEntry<Double, Double>>> newStock = null;
      String date = null;
      while (newStock == null || !user.dateChecker(date)) {
        ViewPrint.askDate(out);
        date = scan.nextLine();
        if (date.equals("0")) {
          return;
        }
        if (!user.dateChecker(date)) {
          ViewPrint.wrongDateMsg(out);
        } else if (user.isBeforeDate(date, portfolioState.get(ticker).getKey())) {
          ViewPrint.wrongDateBeforeLastTx(out);
        } else {
          newStock = new SimpleEntry<>(ticker,
              new SimpleEntry<>(date, new SimpleEntry<>(-stockQuanDouble, commFee)));
          break;
        }
      }

      boolean val = user.transactionForPortfolio(portfolioName, newStock);
      if (val) {
        ViewPrint.successfulTransaction(out);
      } else {
        ViewPrint.unSuccessfulTransaction(out);
      }


    }}
