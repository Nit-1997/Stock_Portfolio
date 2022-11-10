package controller.Commands;

import java.io.PrintStream;
import java.util.Scanner;
import model.UserFlex;
import view.ViewPrint;

public class AskDate {

  public static String addStocksAskDate(Scanner scan, PrintStream out, UserFlex user){
    ViewPrint.askDate(out);
    String date = scan.nextLine();
    while (!user.dateChecker(date)) {
      ViewPrint.askDateAgain(out);
      date = scan.nextLine();
      if (date.equals("0")) {
        return null;
      }
    }
    return date;
  }

}
