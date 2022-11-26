package controller.gui_controller;

import model.UserFlexInvest;
import view.ViewGUI;

public class PortfolioCostBasis {


  String portfolioName;
  String date;
  UserFlexInvest user;

  public PortfolioCostBasis(String portfolioName, String date, UserFlexInvest user) {
    this.portfolioName = portfolioName;
    this.date = date;
    this.user = user;
  }

  public String execute() {

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
    if(user.isBeforeDate(date,portfolioCreationDate)) return "Given date before portfolio creation date "+portfolioCreationDate;
    Double value;
    try {
      value = user.getCostBasis(portfolioName, date);
    } catch (Exception e) {
      return e.getMessage();
    }
    return "Success2,"+value;
  }

}
