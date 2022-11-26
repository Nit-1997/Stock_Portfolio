package controller.gui_controller;

import java.util.Map;
import model.UserFlexInvest;

public class PortfolioComposition {

  String portfolioName;
  String date;
  UserFlexInvest user;

  Map<String, Double> stockMap;

  public PortfolioComposition(String portfolioName, String date, UserFlexInvest user) {
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
    if(user.isBeforeDate(date,portfolioCreationDate)){
      System.out.println("here");
      return "Given date before portfolio creation date "+portfolioCreationDate;
    }

    try {
      this.stockMap = user.getPortfolioSummary(portfolioName, date);
    } catch (Exception e) {
      return e.getMessage();
    }
    return "Success3";
  }

  public Map<String, Double> getStockMap(){
    return this.stockMap;
  }

}
