package controller.commands;

import java.io.PrintStream;
import java.util.Scanner;
import model.UserFlex;
import view.IView;

/**
 * Class for asking stock symbol.
 */
public class AskTicker {

  /**
   * This function takes valid ticker name for adding in portfolio.
   *
   * @param scan input object
   * @param user model object
   * @param out output object
   * @return a valid ticker name
   */
  public static String addStocksAskTicker(Scanner scan, UserFlex user, PrintStream out, IView view) {
    view.askTickerSymbol(out);
    String ticker;
    do {
      ticker = scan.nextLine().toUpperCase();
      if (ticker.equals("0")) {
        return null;
      }
      if (!user.isValidStock(ticker)) {
        view.askTickerSymbolAgain(out);
      }
    }
    while (!user.isValidStock(ticker));
    return ticker;
  }


}
