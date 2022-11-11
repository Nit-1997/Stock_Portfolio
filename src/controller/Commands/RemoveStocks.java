package controller.Commands;

import java.io.PrintStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.List;
import view.ViewPrint;

public class RemoveStocks {

  public static void addPortfolioRemoveStocks(String ticker, String date, Double stockQuan,
      Map<String, Map<String, Double>> stocksMap, PrintStream out) {

    if (!stocksMap.containsKey(ticker) || !stocksMap.get(ticker).containsKey(date) || stocksMap.get(ticker).get(date) < stockQuan) {
      ViewPrint.removeStocksInPortfolioUnSuccessfulConfirmation(out);
    } else if (stocksMap.get(ticker).get(date) == stockQuan) {
      stocksMap.get(ticker).remove(date);
      if (stocksMap.get(ticker).size() == 0) {
        stocksMap.remove(ticker);
      }
      ViewPrint.removeStocksInPortfolioSuccessfulConfirmation(out);
    } else if (stocksMap.get(ticker).get(date) > stockQuan) {
      stocksMap.get(ticker).put(date, stocksMap.get(ticker).get(date) - stockQuan);
      ViewPrint.removeStocksInPortfolioUnSuccessfulConfirmation(out);
    }
  }

}
