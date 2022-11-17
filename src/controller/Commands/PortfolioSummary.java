package controller.Commands;

import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;
import model.UserFlex;
import view.ViewPrint;

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
      String portfolioName) {
    ViewPrint.waitLoadMessage(out);
    String date = AskDate.addStocksAskDate(scan, out, user);
    if (date == null) {
      return;
    }
    Map<String, Double> stockMap = user.getPortfolioSummary(portfolioName, date);

    String creationDate = user.getPortfolioCreationDate(portfolioName);
    if (creationDate == null) {
      ViewPrint.printInCompatiblePortfolio(out);
      return;
    }
    if (user.isBeforeDate(date, creationDate)) {
      System.out.println("Date entered is before portfolio creation date ( " + creationDate + " )");
      return;
    }

    if (stockMap == null) {
      ViewPrint.printInCompatiblePortfolio(out);
      return;
    }
    ViewPrint.printPortfolioSummary(stockMap, out);
  }

}
