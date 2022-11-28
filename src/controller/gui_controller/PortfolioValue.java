package controller.gui_controller;

import model.UserFlexInvest;
import view.ViewGUI;

public class PortfolioValue {

  String portfolioName;
  String date;
  UserFlexInvest user;

  public PortfolioValue(String portfolioName, String date, UserFlexInvest user) {
    this.portfolioName = portfolioName;
    this.date = date;
    this.user = user;
  }

  public String execute() {

    if (date.equals(""))
      return "Empty Date";
    if (!user.dateChecker(date))
      return "Wrong Date Format";
    Double value;
    try {
      value = user.getPortfolioValue(portfolioName, date);
    } catch (Exception e) {
      return e.getMessage();
    }
    return "Success,"+value;
  }
}