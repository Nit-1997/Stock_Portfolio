package controller.commands;

import java.io.PrintStream;
import java.util.Scanner;
import view.ViewText;

/**
 * Asks commission fee for each transaction.
 */
public class AskCommissionFees {

  /**
   * Adds commission fee for each transction.w
   *
   * @param scan input object.
   * @param out  output object.
   * @param view view object.
   * @return commission fee.
   */
  public static Double askCommissionFees(Scanner scan, PrintStream out, ViewText view) {
    view.askCommissionFee(out);
    String commFee = scan.nextLine();
    double commFeesDouble = 0;
    do {
      try {
        commFeesDouble = Double.parseDouble(commFee);
        if (commFeesDouble < 0) {
          throw new NumberFormatException();
        }

      } catch (NumberFormatException e) {
        view.askCommissionFeeAgain(out);
        commFee = scan.nextLine();
        if (commFee.equals("e")) {
          return null;
        }
      }
    }

    while (commFeesDouble <= 0);

    return commFeesDouble;
  }

}
