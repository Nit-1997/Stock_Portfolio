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

    String str=DateChecker.checkDate(date,user,portfolioName);
    if(!str.equals("done")) return str;

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
