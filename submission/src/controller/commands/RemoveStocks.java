package controller.commands;

import java.io.PrintStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import view.ViewText;

/**
 * Class to remove stocks for the portfolio while creation of portfolio.
 */
public class RemoveStocks {

  /**
   * Remove stocks for the portfolio while creation of portfolio.
   *
   * @param ticker    name of the stock
   * @param date      date
   * @param stockQuan number of stocks.
   * @param stocksMap current portfolio
   * @param out       output object.
   * @param view view object.
   */
  public static void addPortfolioRemoveStocks(String ticker, String date, Double stockQuan,
      Map<String, Map<String, SimpleEntry<Double, Double>>> stocksMap, PrintStream out, ViewText view) {

    if (!stocksMap.containsKey(ticker) || !stocksMap.get(ticker).containsKey(date)
        || stocksMap.get(ticker).get(date).getKey() < stockQuan) {
      view.removeStocksInPortfolioUnSuccessfulConfirmation(out);
    } else if (stocksMap.get(ticker).get(date).getKey() == stockQuan) {
      stocksMap.get(ticker).remove(date);
      if (stocksMap.get(ticker).size() == 0) {
        stocksMap.remove(ticker);
      }
      view.removeStocksInPortfolioSuccessfulConfirmation(out);
    } else if (stocksMap.get(ticker).get(date).getKey() > stockQuan) {
      Double prevQuan = stocksMap.get(ticker).get(date).getKey();
      Double commFee = stocksMap.get(ticker).get(date).getValue();
      stocksMap.get(ticker).put(date, new SimpleEntry<>(prevQuan - stockQuan, commFee));
      view.removeStocksInPortfolioSuccessfulConfirmation(out);
    }
  }

}
