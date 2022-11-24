package controller.commands;

import java.io.PrintStream;
import java.util.Scanner;
import model.UserFlex;
import view.IView;

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
      String portfolioName, IView view) {
    view.waitLoadMessage(out);
    String creationDate = user.getPortfolioCreationDate(portfolioName);
    if (creationDate == null) {
      view.printInCompatiblePortfolio(out);
      return;
    }
    String date;
    do {
      view.askDate(out);
      date = scan.nextLine();
      if (date.equals("0")) {
        return;
      }
      if (!user.dateChecker(date)) {
        view.wrongDateMsg(out);
      } else if (user.isBeforeDate(date, creationDate)) {
        view.wrongDateBeforePortfolioCreation(out);
      }
    }
    while (!user.dateChecker(date) || user.isBeforeDate(date,
        user.getPortfolioCreationDate(portfolioName)));

    Double cost = user.getCostBasis(portfolioName, date);
    if (cost == null) {
      view.printInCompatiblePortfolio(out);
      return;
    }
    view.printCostBasis(cost, out);
  }

}
