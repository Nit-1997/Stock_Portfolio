package controller.commands;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import model.UserFlex;
import view.IView;

/**
 * Class for loading and taking input for single portfolio menu.
 */
public class LoadSingleFlexPortfolioDetail {
  /**
   * loading and taking input for single portfolio menu.
   *
   * @param scan input object.
   * @param user model object.
   * @param out  output object.
   */
  public static void loadSinglePortfolioDetailController(Scanner scan, UserFlex user,
      PrintStream out, IView view) {
    view.askNameOfPortfolio(out);
    String portfolioName = scan.nextLine();
    while (user.isUniqueName(portfolioName)) {
      view.askPortfolioNameAgainUnique(out);
      portfolioName = scan.nextLine();
      if (portfolioName.equals("0")) {
        LoadFlexPortfolio.loadPortfoliosController(scan, user, out, view);
        return;
      }
    }
    view.portfolioDetailWelcomeNote(portfolioName, out);
    view.loadFlexPortfolioDetailMenu(out);
    String option = scan.nextLine().trim();
    boolean comingFromDefault = false;
    do {
      switch (option) {
        case "1":
          PortfolioSummary.getPortfolioSummary(scan, out, user, portfolioName,view);
          break;
        case "2":
          String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
          PortfolioValue.getPortfolioValue(portfolioName, date, out, user,view);
          break;
        case "3":
          date = AskDate.addStocksAskDate(scan, out, user, view);
          if (date == null) {
            return;
          }
          PortfolioValue.getPortfolioValue(portfolioName, date, out, user,view);
          break;
        case "4":
          BuyStock.buyStockToPortfolio(portfolioName, scan, user, out, view);
          break;
        case "5":
          SellStock.sellStockFromPortfolio(portfolioName, scan, user, out,view);
          break;
        case "6":
          CostBasis.calculateCostBasis(scan, out, user, portfolioName, view);
          break;
        case "7":
          PerformanceGraph.plotGraph(scan, out, user, portfolioName, view);
          break;
        case "8":
          LoadFlexPortfolio.loadPortfoliosController(scan, user, out, view);
          return;
        case "9":
          return;
        default:
          view.loadPortfolioErrorNote(out);
          option = scan.nextLine();
          comingFromDefault = true;
      }
      if (!comingFromDefault) {
        view.loadFlexPortfolioDetailMenu(out);
        option = scan.nextLine().trim();
      }
      comingFromDefault = false;
    }
    while (!option.equals("9"));

  }

}
