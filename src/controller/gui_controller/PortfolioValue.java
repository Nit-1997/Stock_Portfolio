package controller.gui_controller;

import model.UserFlexInvest;

/**
 * Portfolio value class.
 */
public class PortfolioValue implements GUISubController {

  String portfolioName;
  String date;
  UserFlexInvest user;

  /**
   * Constructor for portfolio value.
   *
   * @param portfolioName portfolio name.
   * @param date          date for value.
   * @param user          model object.
   */
  public PortfolioValue(String portfolioName, String date, UserFlexInvest user) {
    this.portfolioName = portfolioName;
    this.date = date;
    this.user = user;
  }

  @Override
  public String execute() {

    if (date.equals("")) {
      return "Empty Date";
    }
    if (!user.dateChecker(date)) {
      return "Wrong Date Format";
    }
    Double value;
    try {
      value = user.getPortfolioValue(portfolioName, date);
    } catch (Exception e) {
      return e.getMessage();
    }
    return "Success," + value;
  }
}