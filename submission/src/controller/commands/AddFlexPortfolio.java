package controller.commands;

import java.io.PrintStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import model.UserFlex;
import view.ViewText;


/**
 * Controller Class for creating flexible portfolio.
 */
public class AddFlexPortfolio {

  /**
   * Adds stocks to the portfolio for creation.
   *
   * @param scan input object.
   * @param user model object.
   * @param out  output object.
   * @param view view object.
   */
  public static void addStocksToPortfolioController(Scanner scan, UserFlex user, PrintStream out, ViewText view) {
    view.addPortfolio(out);
    String name = scan.nextLine();
    while (!user.isUniqueName(name)) {
      view.askPortfolioNameAgain(out);
      name = scan.nextLine();
      if (name.equals("0")) {
        return;
      }
    }
    Map<String, Map<String, SimpleEntry<Double, Double>>> stocksMap = new HashMap<>();
    String ticker = null;

    view.addStocksInPortfolioWelcomeNote(name, out);
    Set<String> stockList = user.getStockList();
    if (stockList != null && stockList.size() != 0) {
      view.printAvailableStocks(stockList);
    }
    String confirmation = null;
    String option = "1";
    boolean comingFromDefault = false;

    do {
      switch (option) {
        case "1":
          ticker = AskTicker.addStocksAskTicker(scan, user, out, view);
          if (ticker == null) {
            return;
          }
          Double stockQuanDouble = AskStockNumber.addStocksAskStockNumber(scan, out, view);
          if (stockQuanDouble == null) {
            return;
          }
          String date = AskDate.addStocksAskDate(scan, out, user, view);
          if (date == null) {
            return;
          }
          Double commFee = AskCommissionFees.askCommissionFees(scan, out, view);
          if (commFee == null) {
            return;
          }
          Map<String, SimpleEntry<Double, Double>> map;
          if (stocksMap.containsKey(ticker)) {
            map = stocksMap.get(ticker);
            if (stocksMap.get(ticker).containsKey(date)) {
              Double prevQuan = stocksMap.get(ticker).get(date).getKey();
              map.put(date, new SimpleEntry<>(prevQuan + stockQuanDouble, commFee));
            } else {
              map.put(date, new SimpleEntry<>(stockQuanDouble, commFee));
            }
          } else {
            map = new HashMap<>();
            map.put(date, new SimpleEntry<>(stockQuanDouble, commFee));
          }
          stocksMap.put(ticker, map);

          view.addStocksInPortfolioConfirmation(out);
          confirmation = scan.nextLine();
          while (!confirmation.equals("y") && !confirmation.equals("Y") && !confirmation.equals("n")
              && !confirmation.equals("N")) {
            view.askconfirmation(out);
            confirmation = scan.nextLine();
          }
          break;
        case "2":
          ticker = AskTicker.addStocksAskTicker(scan, user, out, view);
          if (ticker == null) {
            break;
          }
          if (!stocksMap.containsKey(ticker)) {
            view.stockNotInPortfolio(out);
            break;
          }
          stockQuanDouble = AskStockNumber.addStocksAskStockNumber(scan, out, view);
          if (stockQuanDouble == null) {
            break;
          }
          date = AskDate.addStocksAskDate(scan, out, user, view);
          if (date == null) {
            break;
          }

          RemoveStocks.addPortfolioRemoveStocks(ticker, date, stockQuanDouble, stocksMap, out, view);

          confirmation = scan.nextLine();
          while (!confirmation.equals("y") && !confirmation.equals("Y") && !confirmation.equals("n")
              && !confirmation.equals("N")) {
            view.askconfirmation(out);
            confirmation = scan.nextLine();
          }
          break;
        case "3":
          confirmation = "n";
          break;
        default:
          view.addStocksInPortfolioErrorNode(out);
          option = scan.nextLine();
          comingFromDefault = true;
      }

      if (!comingFromDefault && (confirmation.equals("y") || confirmation.equals("Y"))) {
        view.stocksInPortfolioAddOrRemoveMenu(out);
        option = scan.nextLine().trim();
      }
      comingFromDefault = false;
    }
    while (confirmation.equals("y") || confirmation.equals("Y"));

    view.waitMessage(out);
    boolean val;

    try {
      val = user.addPortfolio(name, stocksMap);
      if (val) {
        view.addStocksInPortfolioConfirmationLoading(name, out);
      } else {
        view.unsuccessfulPortolioCreationMsg(out);
      }
    } catch (Exception e) {
      view.printError(e, out);
      view.unsuccessfulPortolioCreationMsg(out);
    }
  }

}
