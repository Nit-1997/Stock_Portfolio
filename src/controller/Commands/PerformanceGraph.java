package controller.Commands;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Scanner;
import model.UserFlex;
import view.ViewPrint;

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
  public static void plotGraph(Scanner scan, PrintStream out, UserFlex user, String portfolioName) {
    ViewPrint.waitLoadMessage(out);
    String creationDate = user.getPortfolioCreationDate(portfolioName);
    if (creationDate == null) {
      ViewPrint.printInCompatiblePortfolio(out);
      return;
    }
    String currentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
    System.out.println(
        "Valid date range for performance graphs : - ( " + creationDate + ") - (" + currentDate
            + ")");
    ViewPrint.askStartDateForGraph(out);
    String date1 = AskDate.addStocksAskDate(scan, out, user);
    if (date1 == null) {
      return;
    }
    ViewPrint.askEndDateForGraph(out);
    String date2 = AskDate.addStocksAskDate(scan, out, user);
    if (date2 == null) {
      return;
    }

    if (!user.graphDateChecker(date1, date2, portfolioName)) {
      ViewPrint.graphInvalidRange(out);
      return;
    }
    ViewPrint.waitLoadMessage(out);

    SimpleEntry<SimpleEntry<List<String>, List<Integer>>, SimpleEntry<Integer, Double>> data = user.getGraphData(
        date1,
        date2, portfolioName);

    if (data == null) {
      ViewPrint.printInCompatiblePortfolio(out);
      return;
    }

    List<String> labels = data.getKey().getKey();
    List<Integer> starPoints = data.getKey().getValue();
    int scale = data.getValue().getKey();
    double baseAmount = data.getValue().getValue();

    ViewPrint.printGraph(out, date1, date2, portfolioName, labels, starPoints, scale, baseAmount);

  }

}
