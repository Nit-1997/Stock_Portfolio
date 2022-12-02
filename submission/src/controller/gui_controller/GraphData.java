package controller.gui_controller;

import java.time.LocalDate;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import model.UserFlexInvest;

/**
 * Class for creating graph.
 */
public class GraphData implements GUISubController {

  public SimpleEntry<List<String>, List<Double>> data;
  String startDate;
  String endDate;
  String portfolioName;
  UserFlexInvest user;


  /**
   * Constructor for graph class.
   *
   * @param startDate     start date.
   * @param endDate       end date.
   * @param portfolioName portfolio name.
   * @param user          model object.
   */
  public GraphData(String startDate, String endDate, String portfolioName, UserFlexInvest user) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.portfolioName = portfolioName;
    this.user = user;
    this.data = null;
  }

  @Override
  public String execute() {

    if (!DateChecker.checkDate(startDate, user, portfolioName).equals("done")) {
      return DateChecker.checkDate(startDate, user, portfolioName);
    }

    if (!DateChecker.checkDate(endDate, user, portfolioName).equals("done")) {
      return DateChecker.checkDate(endDate, user, portfolioName);
    }

    LocalDate start = LocalDate.parse(startDate);
    LocalDate end = LocalDate.parse(endDate);

    if (end.isBefore(start)) {
      return "End date before start date";
    }

    if (end.isAfter(LocalDate.now())) {
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
