package controller.Commands;

import java.io.PrintStream;
import java.util.Scanner;
import view.ViewPrint;

public class AskCommissionFees {

  public static Double AskCommissionFees(Scanner scan, PrintStream out) {
    System.out.print("Commission fee for this transaction: ");
    String commFee = scan.nextLine();
    double commFeesDouble = 0;
    do {
      try {
        commFeesDouble = Double.parseDouble(commFee);
        if (commFeesDouble < 0) {
          throw new NumberFormatException();
        }

      } catch (NumberFormatException e) {
        System.out.print("Wrong format, again enter Commission fee for this transaction: ");
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
