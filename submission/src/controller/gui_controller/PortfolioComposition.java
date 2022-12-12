package controller.gui_controller;

import java.util.Map;
import model.UserFlexInvest;

/**
 * Portfolio composition class.
 */
public class PortfolioComposition implements GUISubController {

  String portfolioName;
  String date;
  UserFlexInvest user;

  Map<String, Double> stockMap;

  /**
   * Constructor for portfolio composition.
   *
   * @param portfolioName portfolio name.
   * @param date          date fro portfolio composition.
   * @param user          model object.
   */
  public PortfolioComposition(String portfolioName, String date, UserFlexInvest user) {
    this.portfolioName = portfolioName;
    this.date = date;
    this.user = user;
  }

  @Override
  public String execute() {

    String str = DateChecker.checkDate(date, user, portfolioName);
    if (!str.equals("done")) {
      return str;
    }

    try {
      this.stockMap = user.getPortfolioSummary(portfolioName, date);
    } catch (Exception e) {
      return e.getMessage();
    }
    return "Success3";
  }

  public Map<String, Double> getStockMap() {
    return this.stockMap;
  }

}
