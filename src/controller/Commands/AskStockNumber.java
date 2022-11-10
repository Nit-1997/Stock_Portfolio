package controller.Commands;

import java.io.PrintStream;
import java.util.Scanner;
import view.ViewPrint;

public class AskStockNumber {


  /**
   * This function takes valid stock number for adding in portfolio.
   *
   * @param scan input object
   * @return a valid stock number
   */
  public static Double addStocksAskStockNumber(Scanner scan, PrintStream out) {
    ViewPrint.askStockNumber(out);
    String stockQuantity = scan.nextLine();
    double stockQuanDouble = 0;
    do {
      try {
        stockQuanDouble = Double.parseDouble(stockQuantity);
        if (stockQuanDouble <= 0 || stockQuanDouble != (int) stockQuanDouble) {
          throw new NumberFormatException();
        }
        break;
      } catch (NumberFormatException e) {
        ViewPrint.askStockNumberAgain(out);
        stockQuantity = scan.nextLine();
        if (stockQuantity.equals("0")) {
          return null;
        }
      }
    }
    while (stockQuanDouble <= 0 || stockQuanDouble != (int) stockQuanDouble);

    return stockQuanDouble;
  }

}
