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

    String str=DateChecker.checkDate(date,user,portfolioName);
    if(!str.equals("done")) return str;

    Double value;
    try {
      value = user.getCostBasis(portfolioName, date);
    } catch (Exception e) {
      return e.getMessage();
    }
    return "Success2,"+value;
  }

}
