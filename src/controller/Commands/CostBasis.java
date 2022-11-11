package controller.Commands;

import java.io.PrintStream;
import java.util.Scanner;
import model.UserFlex;
import view.ViewPrint;

public class CostBasis {

  public static void calculateCostBasis(Scanner scan, PrintStream out, UserFlex user, String portfolioName){
    ViewPrint.waitLoadMessage(out);
    ViewPrint.askDate(out);
    String date = scan.nextLine();
    while (!user.dateChecker(date) || user.isBeforeDate(date,user.getPortfolioCreationDate(portfolioName))) {
      ViewPrint.askDateAgain(out);
      date = scan.nextLine();
      if (date.equals("0")) {
        return;
      }
    }
    Double cost = user.getCostBasis(portfolioName,date);
    System.out.println("\nCost basis of the portfolio till the given date : "+cost);
  }

}
