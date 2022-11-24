package controller.commands;

import java.io.PrintStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Scanner;
import model.UserFlex;
import view.IView;

/**
 * Class for selling stock.
 */
public class SellStock {

  /**
   * Stock sell.
   *
   * @param scan          input object.
   * @param user          model object.
   * @param out           output object.
   * @param portfolioName name of the portfolio.
   */
  public static void sellStockFromPortfolio(String portfolioName, Scanner scan, UserFlex user,
      PrintStream out, IView view) {

    view.waitLoadMessage(out);
    Map<String, SimpleEntry<String, Double>> portfolioState = user.getPortfolioState(portfolioName);
    if (portfolioState == null) {
      view.printInCompatiblePortfolio(out);
      return;
    }
    view.printPortfolioState(portfolioState, null, out);

    view.askTickerSymbol(out);
    String ticker;
    do {
      ticker = scan.nextLine().toUpperCase();
      if (ticker.equals("0")) {
        return;
      }
      if (!user.isValidStock(ticker)) {
        view.askTickerSymbolAgain(out);
        continue;
      }
      if (!portfolioState.containsKey(ticker)) {
        view.stockNotInPortfolioMsg(out);
      }
    }
    while (!user.isValidStock(ticker) || !portfolioState.containsKey(ticker));

    Double stockQuanDouble = null;
    do {
      stockQuanDouble = AskStockNumber.addStocksAskStockNumber(scan, out,view);
      if (stockQuanDouble == null) {
        return;
      }
      if (portfolioState.get(ticker).getValue() < stockQuanDouble) {
        view.stockLessThanInPortfolio(out);
      }
    }
    while (portfolioState.get(ticker).getValue() < stockQuanDouble);

    Double commFee = AskCommissionFees.askCommissionFees(scan, out,view);
    if (commFee == null) {
      return;
    }

    SimpleEntry<String, SimpleEntry<String, SimpleEntry<Double, Double>>> newStock = null;
    String date = null;
    while (newStock == null || !user.dateChecker(date)) {
      view.askDate(out);
      date = scan.nextLine();
      if (date.equals("0")) {
        return;
      }
      if (!user.dateChecker(date)) {
        view.wrongDateMsg(out);
      } else if (user.isBeforeDate(date, portfolioState.get(ticker).getKey())) {
        view.wrongDateBeforeLastTx(out);
      } else {
        newStock = new SimpleEntry<>(ticker,
            new SimpleEntry<>(date, new SimpleEntry<>(-stockQuanDouble, commFee)));
        break;
      }
    }

    boolean val = false;
    try {
      val = user.transactionForPortfolio(portfolioName, newStock);
    } catch (Exception e) {
      view.printError(e, out);
      view.unSuccessfulTransaction(out);
    }
    if (val) {
      view.successfulTransaction(out);
    } else {
      view.unSuccessfulTransaction(out);
    }


  }
}
