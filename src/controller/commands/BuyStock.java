package controller.commands;

import java.io.PrintStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import model.UserFlex;
import view.ViewText;

/**
 * Class for buying stocks.
 */
public class BuyStock {
  /**
   * Stock purchase.
   * @param scan input object.
   * @param user model object.
   * @param out output object.
   * @param portfolioName name of the portfolio.
   */
  public static void buyStockToPortfolio(String portfolioName, Scanner scan, UserFlex user,
      PrintStream out, ViewText view) {
    view.waitLoadMessage(out);
    String portfolioCreationDate = null;
    try {
      portfolioCreationDate = user.getPortfolioCreationDate(portfolioName);
    } catch (Exception e) {
      portfolioCreationDate = null;
    }

    if (portfolioCreationDate == null) {
      view.printInCompatiblePortfolio(out);
      return;
    }
    Map<String, SimpleEntry<String, Double>> portfolioState = user.getPortfolioState(portfolioName);
    if (portfolioState == null) {
      view.printInCompatiblePortfolio(out);
      return;
    }
    view.printPortfolioState(portfolioState, portfolioCreationDate, out);

    Set<String> stockList = user.getStockList();
    if (stockList != null && stockList.size() != 0) {
      view.printAvailableStocks(stockList);
    }

    String ticker = AskTicker.addStocksAskTicker(scan, user, out, view);
    if (ticker == null) {
      return;
    }
    Double stockQuanDouble = AskStockNumber.addStocksAskStockNumber(scan, out, view);
    if (stockQuanDouble == null) {
      return;
    }

    Double commFee = AskCommissionFees.askCommissionFees(scan, out, view);
    if (commFee == null) {
      return;
    }

    // ticker vs date vs quantity vs commission fee
    SimpleEntry<String, SimpleEntry<String, SimpleEntry<Double, Double>>> newStock = null;
    String date;

    do {
      view.askDate(out);
      date = scan.nextLine();
      if (date.equals("0")) {
        return;
      }
      if (!user.dateChecker(date)) {
        view.wrongDateMsg(out);
      } else if (portfolioState.containsKey(ticker)) {
        if (user.isBeforeDate(date, portfolioState.get(ticker).getKey())) {
          view.wrongDateBeforeLastTx(out);
        } else {
          newStock = new SimpleEntry<>(ticker,
              new SimpleEntry<>(date, new SimpleEntry<>(stockQuanDouble, commFee)));
          break;
        }
      } else {
        if (user.isBeforeDate(date, portfolioCreationDate)) {
          view.wrongDateBeforePortfolioCreation(out);
        } else {
          newStock = new SimpleEntry<>(ticker,
              new SimpleEntry<>(date, new SimpleEntry<>(stockQuanDouble, commFee)));
          break;
        }
      }
    }
    while (!user.dateChecker(date) || newStock == null);

    boolean val;
    try {
      val = user.transactionForPortfolio(portfolioName, newStock);
      if (val) {
        view.successfulTransaction(out);
      } else {
        view.unSuccessfulTransaction(out);
      }
    } catch (Exception e) {
      view.printError(e, out);
      view.unSuccessfulTransaction(out);
    }


  }

}
