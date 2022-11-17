package controller.Commands;

import java.io.PrintStream;
import model.UserFlex;
import view.ViewPrint;

/**
 * Class for value of the portfolio.
 */
public class PortfolioValue {

  /**
   * value of the portfolio.
   *
   * @param portfolioName name of the portfolio.
   * @param user          model object.
   * @param out           output object.
   * @param date          date for which value is required.
   */
  public static void getPortfolioValue(String portfolioName, String date, PrintStream out,
      UserFlex user) {
    ViewPrint.waitLoadMessage(out);
    Double portfolioValue;
    String creationDate = user.getPortfolioCreationDate(portfolioName);
    if (creationDate == null) {
      ViewPrint.printInCompatiblePortfolio(out);
      return;
    }
    if (user.isBeforeDate(date, creationDate)) {
      portfolioValue = 0.0;
    } else {
      portfolioValue = user.getPortfolioValue(portfolioName, date);
    }

    if (portfolioValue == null) {
      ViewPrint.printInCompatiblePortfolio(out);
      return;
    }
    ViewPrint.printPortfolioValue(portfolioValue, out);
  }

}
