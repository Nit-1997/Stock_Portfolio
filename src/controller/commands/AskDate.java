package controller.commands;

import java.io.PrintStream;
import java.util.Scanner;
import model.UserFlex;
import view.IView;

/**
 * Asks date.
 */
public class AskDate {

  /**
   * Asks dates for stocks.
   *
   * @param scan input object.
   * @param user model object.
   * @param out  output object.
   * @return date.
   */
  public static String addStocksAskDate(Scanner scan, PrintStream out, UserFlex user, IView view) {
    view.askDate(out);
    String date = scan.nextLine();
    while (!user.dateChecker(date)) {
      view.askDateAgain(out);
      date = scan.nextLine();
      if (date.equals("0")) {
        return null;
      }
    }
    return date;
  }

}
