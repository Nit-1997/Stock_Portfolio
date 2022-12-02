package controller.guicontroller;

import java.time.LocalDate;
import model.UserFlexInvest;

/**
 * Helper class for checking date.
 */
public class DateChecker {

  /**
   * Checks the date.
   *
   * @param date          date to be checked.
   * @param user          model object.
   * @param portfolioName portfolio name.
   * @return boolean if date is correct.
   */
  public static String checkDate(String date, UserFlexInvest user, String portfolioName) {
    if (date.equals("")) {
      return "Empty Date";
    }
    if (!user.dateChecker(date)) {
      return "Wrong Date Format";
    }

    LocalDate dateFormat;
    try{
      dateFormat = LocalDate.parse(date);
    } catch(Exception e) {
      return "Wrong Date Format";
    }

    String portfolioCreationDate = null;
    try {
      portfolioCreationDate = user.getPortfolioCreationDate(portfolioName);
    } catch (Exception e) {
      return e.getMessage();
    }
    if (user.isBeforeDate(date, portfolioCreationDate)) {
      return "Given date before portfolio creation date " + portfolioCreationDate;
    }

    if (dateFormat.isAfter(LocalDate.now())) {
      return "Date after today's date";
    }

    return "done";
  }

}
