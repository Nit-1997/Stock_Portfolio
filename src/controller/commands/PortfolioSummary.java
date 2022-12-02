package controller.commands;

import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;
import model.UserFlex;
import view.ViewText;

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
   * @param view          view object.
   */
  public static void getPortfolioSummary(Scanner scan, PrintStream out, UserFlex user,
      String portfolioName, ViewText view) {
    view.waitLoadMessage(out);
    String date = AskDate.addStocksAskDate(scan, out, user, view);
    if (date == null) {
      return;
    }
    Map<String, Double> stockMap;
    try {
      stockMap = user.getPortfolioSummary(portfolioName, date);
    } catch (Exception e) {
      stockMap = null;
    }
    if (stockMap == null) {
      view.printInCompatiblePortfolio(out);
      return;
    }

    String creationDate = null;
    try {
      creationDate = user.getPortfolioCreationDate(portfolioName);
    } catch (Exception e) {
      creationDate = null;
    }
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
