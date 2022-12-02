package controller.gui_controller;

import model.UserFlexInvest;

/**
 * Portfolio cost basis class.
 */
public class PortfolioCostBasis implements GUISubController {


  String portfolioName;
  String date;
  UserFlexInvest user;

  /**
   * Constructor for portfolio cost basis.
   * @param portfolioName portfolio name.
   * @param date date for cost basis.
   * @param user model object.
   */
  public PortfolioCostBasis(String portfolioName, String date, UserFlexInvest user) {
    this.portfolioName = portfolioName;
    this.date = date;
    this.user = user;
  }

  @Override
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
