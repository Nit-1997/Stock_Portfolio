package controller.commands;

import java.io.PrintStream;
import java.util.Scanner;
import view.ViewText;

/**
 * Class for asking stock quantity.
 */
public class AskStockNumber {


  /**
   * This function takes valid stock number for adding in portfolio.
   *
   * @param scan input object
   * @param out output object.
   * @return a valid stock number
   */
  public static Double addStocksAskStockNumber(Scanner scan, PrintStream out, ViewText view) {
    view.askStockNumber(out);
    String stockQuantity = scan.nextLine();
    double stockQuanDouble = 0;
    do {
      try {
        stockQuanDouble = Double.parseDouble(stockQuantity);
        if (stockQuanDouble <= 0 || stockQuanDouble != (int) stockQuanDouble) {
          throw new NumberFormatException();
        }
      } catch (NumberFormatException e) {
        view.askStockNumberAgain(out);
        stockQuantity = scan.nextLine();
        if (stockQuantity.equals("e")) {
          return null;
        }
      }
    }
    while (stockQuanDouble <= 0 || stockQuanDouble != (int) stockQuanDouble);

    return stockQuanDouble;
  }

}
