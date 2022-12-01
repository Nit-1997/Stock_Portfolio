package controller.gui_controller;

import java.time.LocalDate;
import model.UserFlexInvest;

public class DateChecker {

  public static String checkDate(String date, UserFlexInvest user, String portfolioName){
    if (date.equals(""))
      return "Empty Date";
    if (!user.dateChecker(date))
      return "Wrong Date Format";

    String portfolioCreationDate = null;
    try {
      portfolioCreationDate = user.getPortfolioCreationDate(portfolioName);
    } catch (Exception e) {
      return e.getMessage();
    }
    if(user.isBeforeDate(date,portfolioCreationDate)){
      return "Given date before portfolio creation date "+portfolioCreationDate;
    }

    if(LocalDate.parse(date).isAfter(LocalDate.now())) {
      return "Date after today's date";
    }

    return "done";
  }

}
