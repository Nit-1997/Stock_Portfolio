package controller.gui_controller;

import java.time.LocalDate;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import model.UserFlexInvest;

public class GraphData {

  String startDate;
  String endDate;
  String portfolioName;
  UserFlexInvest user;

  public SimpleEntry<List<String>, List<Double>> data;


  public GraphData(String startDate, String endDate, String portfolioName, UserFlexInvest user) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.portfolioName = portfolioName;
    this.user = user;
    this.data= null;
  }

  public String execute(){

    if(!DateChecker.checkDate(startDate,user,portfolioName).equals("done")){
      return DateChecker.checkDate(startDate,user,portfolioName);
    }

    if(!DateChecker.checkDate(endDate,user,portfolioName).equals("done")){
      return DateChecker.checkDate(endDate,user,portfolioName);
    }

    LocalDate start = LocalDate.parse(startDate);
    LocalDate end = LocalDate.parse(endDate);

    if(end.isBefore(start)) return "End date before start date";


    if(end.isAfter(LocalDate.now())) {
      return "End date after today's date";
    }

    try {
      this.data = user.getGraphDataGUI(startDate, endDate, portfolioName);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return "success";
  }

}
