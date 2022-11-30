package controller.gui_controller;

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
      System.out.println("here");
      return "Given date before portfolio creation date "+portfolioCreationDate;
    }

    return "done";
  }

}
