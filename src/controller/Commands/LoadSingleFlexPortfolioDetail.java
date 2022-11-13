package controller.Commands;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import model.UserFlex;
import view.ViewPrint;

public class LoadSingleFlexPortfolioDetail {

  public static void loadSinglePortfolioDetailController(Scanner scan, UserFlex user, PrintStream out)  {
    ViewPrint.askNameOfPortfolio(out);
    String portfolioName = scan.nextLine();
    while (user.isUniqueName(portfolioName)) {
      ViewPrint.askPortfolioNameAgainUnique(out);
      portfolioName = scan.nextLine();
      if (portfolioName.equals("0")) {
        LoadFlexPortfolio.loadPortfoliosController(scan, user, out);
        return;
      }
    }
    ViewPrint.portfolioDetailWelcomeNote(portfolioName, out);
    ViewPrint.loadFlexPortfolioDetailMenu(out);
    String option = scan.nextLine().trim();
    boolean comingFromDefault = false;
    do {
      switch (option) {
        case "1":
          PortfolioSummary.getPortfolioSummary(scan,out,user,portfolioName);
          break;
        case "2":
          String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
          PortfolioValue.getPortfolioValue(portfolioName,date,out,user);
          break;
        case "3":
          date = AskDate.addStocksAskDate(scan, out, user);
          if (date == null) {
            return;
          }
          PortfolioValue.getPortfolioValue(portfolioName,date,out,user);
          break;
        case "4" :
          BuyStock.buyStockToPortfolio(portfolioName,scan,user,out);
          break;
        case "5" :
          SellStock.sellStockFromPortfolio(portfolioName,scan,user,out);
          break;
        case "6" :
          CostBasis.calculateCostBasis(scan, out, user, portfolioName);
          break;
        case "7" :
          PerformanceGraph.plotGraph(scan, out, user, portfolioName);
          break;
        case "8" :
          LoadFlexPortfolio.loadPortfoliosController(scan, user,out);
          return;
        case "9":
          return;
        default:
          ViewPrint.loadPortfolioErrorNote(out);
          option = scan.nextLine();
          comingFromDefault = true;
      }
      if (!comingFromDefault) {
        ViewPrint.loadFlexPortfolioDetailMenu(out);
        option = scan.nextLine().trim();
      }
      comingFromDefault = false;
    }
    while (!option.equals("9"));

  }

}
