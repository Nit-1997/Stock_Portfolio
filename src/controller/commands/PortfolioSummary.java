package controller.commands;

import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;
import model.UserFlex;
import view.IView;

/**
 * Class for summary of the portfolio.
 */
public class PortfolioSummary {

  /**
   * prints the portfolio summary.
   *
   * @param scan          input object.
   * @param user          model object.
   * @param out           output object.
   * @param portfolioName name of the portfolio.
   */
  public static void getPortfolioSummary(Scanner scan, PrintStream out, UserFlex user,
      String portfolioName, IView view) {
    view.waitLoadMessage(out);
    String date = AskDate.addStocksAskDate(scan, out, user, view);
    if (date == null) {
      return;
    }
    Map<String, Double> stockMap = user.getPortfolioSummary(portfolioName, date);

    String creationDate = user.getPortfolioCreationDate(portfolioName);
    if (creationDate == null) {
      view.printInCompatiblePortfolio(out);
      return;
    }
    if (user.isBeforeDate(date, creationDate)) {
      System.out.println("Date entered is before portfolio creation date ( " + creationDate + " )");
      return;
    }

    if (stockMap == null) {
      view.printInCompatiblePortfolio(out);
      return;
    }
    view.printPortfolioSummary(stockMap, out);
  }

}
