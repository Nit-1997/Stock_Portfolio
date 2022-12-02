package controller.commands;

import java.io.PrintStream;
import model.UserFlex;
import view.ViewText;

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
   * @param view view object.
   */
  public static void getPortfolioValue(String portfolioName, String date, PrintStream out,
      UserFlex user, ViewText view) {
    view.waitLoadMessage(out);
    Double portfolioValue;
    String creationDate;
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
      portfolioValue = 0.0;
    } else {
      try{
        portfolioValue = user.getPortfolioValue(portfolioName, date);
      }catch(Exception e){
        portfolioValue=null;
      }

    }

    if (portfolioValue == null) {
      view.printInCompatiblePortfolio(out);
      return;
    }
    view.printPortfolioValue(portfolioValue, out);
  }

}
