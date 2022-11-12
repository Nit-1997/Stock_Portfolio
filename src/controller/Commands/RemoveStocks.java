package controller.Commands;

import java.io.PrintStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.List;
import view.ViewPrint;

public class RemoveStocks {

  public static void addPortfolioRemoveStocks(String ticker, String date, Double stockQuan,
      Map<String, Map<String, SimpleEntry<Double,Double>>> stocksMap, PrintStream out) {

    if (!stocksMap.containsKey(ticker) || !stocksMap.get(ticker).containsKey(date) || stocksMap.get(ticker).get(date).getKey() < stockQuan) {
      ViewPrint.removeStocksInPortfolioUnSuccessfulConfirmation(out);
    } else if (stocksMap.get(ticker).get(date).getKey() == stockQuan) {
      stocksMap.get(ticker).remove(date);
      if (stocksMap.get(ticker).size() == 0) {
        stocksMap.remove(ticker);
      }
      ViewPrint.removeStocksInPortfolioSuccessfulConfirmation(out);
    } else if (stocksMap.get(ticker).get(date).getKey() > stockQuan) {
      Double prevQuan = stocksMap.get(ticker).get(date).getKey();
      Double commFee = stocksMap.get(ticker).get(date).getValue();
      stocksMap.get(ticker).put(date, new SimpleEntry<>(prevQuan-stockQuan,commFee));
      ViewPrint.removeStocksInPortfolioSuccessfulConfirmation(out);
    }
  }

}
