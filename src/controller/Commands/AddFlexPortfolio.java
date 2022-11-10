package controller.Commands;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import model.UserFlex;
import view.ViewPrint;

public class AddFlexPortfolio {

  public static void addStocksToPortfolioController(Scanner scan, UserFlex user, PrintStream out) {
    ViewPrint.addPortfolio(out);
    String name = scan.nextLine();
    while (!user.isUniqueName(name)) {
      ViewPrint.askPortfolioNameAgain(out);
      name = scan.nextLine();
      if (name.equals("0")) {
        return;
      }
    }
    Map<String, Map<String, Double>> stocksMap = new HashMap<>();
    String ticker = null;

    ViewPrint.addStocksInPortfolioWelcomeNote(name, out);
    Set<String> stockList = user.getStockList();
    if (stockList != null && stockList.size() != 0) {
      ViewPrint.printAvailableStocks(stockList);
    }
    String confirmation = null;
    String option = "1";
    boolean comingFromDefault = false;

    do {
      switch (option) {
        case "1":
          ticker = AskTicker.addStocksAskTicker(scan, user, out);
          if (ticker == null) {
            return;
          }
          Double stockQuanDouble = AskStockNumber.addStocksAskStockNumber(scan, out);
          if (stockQuanDouble == null) {
            return;
          }
          String date = AskDate.addStocksAskDate(scan, out, user);
          if (date == null) {
            return;
          }
          Map<String, Double> map;
          if (stocksMap.containsKey(ticker)) {
            map = stocksMap.get(ticker);
            map.put(date, map.getOrDefault(date, 0.0) + stockQuanDouble);
          } else {
            map = new HashMap<>();
            map.put(date, stockQuanDouble);
          }
          stocksMap.put(ticker, map);

          ViewPrint.addStocksInPortfolioConfirmation(out);
          confirmation = scan.nextLine();
          while (!confirmation.equals("y") && !confirmation.equals("Y") && !confirmation.equals("n")
              && !confirmation.equals("N")) {
            ViewPrint.askconfirmation(out);
            confirmation = scan.nextLine();
          }
          break;
        case "2":
          ticker = AskTicker.addStocksAskTicker(scan, user, out);
          if (ticker == null) {
            break;
          }
          if (!stocksMap.containsKey(ticker)) {
            ViewPrint.stockNotInPortfolio(out);
            break;
          }
          stockQuanDouble = AskStockNumber.addStocksAskStockNumber(scan, out);
          if (stockQuanDouble == null) {
            break;
          }
          date = AskDate.addStocksAskDate(scan, out, user);
          if (date == null) {
            return;
          }

          RemoveStocks.addPortfolioRemoveStocks(ticker, date, stockQuanDouble, stocksMap, out);

          confirmation = scan.nextLine();
          while (!confirmation.equals("y") && !confirmation.equals("Y") && !confirmation.equals("n")
              && !confirmation.equals("N")) {
            ViewPrint.askconfirmation(out);
            confirmation = scan.nextLine();
          }
          break;
        case "3":
          confirmation = "n";
          break;
        default:
          ViewPrint.addStocksInPortfolioErrorNode(out);
          option = scan.nextLine().trim();

          comingFromDefault = true;
      }

      if (!comingFromDefault && (confirmation.equals("y") || confirmation.equals("Y"))) {
        ViewPrint.stocksInPortfolioAddOrRemoveMenu(out);
        option = scan.nextLine().trim();
      }
      comingFromDefault = false;
    }
    while (confirmation.equals("y") || confirmation.equals("Y"));

    ViewPrint.waitMessage(out);
    boolean val = user.addPortfolio(name, stocksMap);
    if (val) {
      ViewPrint.addStocksInPortfolioConfirmationLoading(name, out);
    } else {
      System.out.println("portfolio cant be added");
    }
  }

}
