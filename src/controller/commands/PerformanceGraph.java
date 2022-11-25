package controller.commands;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Scanner;
import model.UserFlex;
import view.IView;

/**
 * Class for printing performance graph.
 */
public class PerformanceGraph {

  /**
   * takes dates and prints performance graph for portfolio.
   *
   * @param scan          input object.
   * @param user          model object.
   * @param out           output object.
   * @param portfolioName name of the portfolio.
   */
  public static void plotGraph(Scanner scan, PrintStream out, UserFlex user, String portfolioName, IView view) {
    view.waitLoadMessage(out);
    String creationDate;
    try {
      creationDate = user.getPortfolioCreationDate(portfolioName);
    } catch (Exception e) {
      creationDate = null;
    }
    if (creationDate == null) {
      view.printInCompatiblePortfolio(out);
      return;
    }
    String currentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
    System.out.println(
        "Valid date range for performance graphs : - ( " + creationDate + ") - (" + currentDate
            + ")");
    view.askStartDateForGraph(out);
    String date1 = AskDate.addStocksAskDate(scan, out, user,view);
    if (date1 == null) {
      return;
    }
    view.askEndDateForGraph(out);
    String date2 = AskDate.addStocksAskDate(scan, out, user, view);
    if (date2 == null) {
      return;
    }

    if (!user.graphDateChecker(date1, date2, portfolioName)) {
      view.graphInvalidRange(out);
      return;
    }
    view.waitLoadMessage(out);

    SimpleEntry<SimpleEntry<List<String>, List<Integer>>, SimpleEntry<Integer, Double>> data =
        user.getGraphData(
        date1,
        date2, portfolioName);

    if (data == null) {
      view.printInCompatiblePortfolio(out);
      return;
    }

    List<String> labels = data.getKey().getKey();
    List<Integer> starPoints = data.getKey().getValue();
    int scale = data.getValue().getKey();
    double baseAmount = data.getValue().getValue();

    view.printGraph(out, date1, date2, portfolioName, labels, starPoints, scale, baseAmount);

  }

}
