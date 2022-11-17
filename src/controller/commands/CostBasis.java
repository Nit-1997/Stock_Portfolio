package controller.commands;

import java.io.PrintStream;
import java.util.Scanner;
import model.UserFlex;
import view.ViewPrint;

/**
 * Calculates cost basis for portfolio.
 */
public class CostBasis {

  /**
   * Calculates cost basis of portfolio.
   *
   * @param scan          input object.
   * @param user          model object.
   * @param out           output object.
   * @param portfolioName name of the portfolio.
   */
  public static void calculateCostBasis(Scanner scan, PrintStream out, UserFlex user,
      String portfolioName) {
    ViewPrint.waitLoadMessage(out);
    String creationDate = user.getPortfolioCreationDate(portfolioName);
    if (creationDate == null) {
      ViewPrint.printInCompatiblePortfolio(out);
      return;
    }
    String date;
    do {
      ViewPrint.askDate(out);
      date = scan.nextLine();
      if (date.equals("0")) {
        return;
      }
      if (!user.dateChecker(date)) {
        ViewPrint.wrongDateMsg(out);
      } else if (user.isBeforeDate(date, creationDate)) {
        ViewPrint.wrongDateBeforePortfolioCreation(out);
      }
    }
    while (!user.dateChecker(date) || user.isBeforeDate(date,
        user.getPortfolioCreationDate(portfolioName)));

    Double cost = user.getCostBasis(portfolioName, date);
    if (cost == null) {
      ViewPrint.printInCompatiblePortfolio(out);
      return;
    }
    ViewPrint.printCostBasis(cost, out);
  }

}
