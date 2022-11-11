package controller.Commands;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;
import model.UserFlex;
import view.ViewPrint;

public class LoadSingleFlexPortfolioDetail {

  public static void loadSinglePortfolioDetailController(Scanner scan, UserFlex user, PrintStream out) {
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
    System.out.println("1. Summary of the portfolio");
    System.out.println("2. Current value of the portfolio");
    System.out.println("3. Historical value of the portfolio");
    System.out.println("4. Add stocks to the portfolio");
    System.out.println("5. Sell stocks from the portfolio");
    System.out.println("6. Cost basis of the portfolio");
    System.out.println("7. Go back to list view menu");
    System.out.println("8. Go to main menu");
    System.out.print("Enter your choice: ");
    String option = scan.nextLine().trim();
    boolean comingFromDefault = false;
    do {
      switch (option) {
        case "1":
          ViewPrint.waitLoadMessage(out);
          String date = AskDate.addStocksAskDate(scan, out, user);
          if (date == null) {
            return;
          }
          Map<String, Double> stockMap = user.getPortfolioSummary(portfolioName, date);
          if (stockMap == null) {
            ViewPrint.printInCompatiblePortfolio(out);
            return;
          }
          ViewPrint.printPortfolioSummary(stockMap, out);
          break;
        case "2":
          date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
          ViewPrint.waitLoadMessage(out);
          Double portfolioValue = user.getPortfolioValue(portfolioName, date);
          if (portfolioValue == null) {
            ViewPrint.printInCompatiblePortfolio(out);
            return;
          }
          ViewPrint.printPortfolioValue(portfolioValue, out);
          break;
        case "3":
          ViewPrint.waitLoadMessage(out);
          date = AskDate.addStocksAskDate(scan, out, user);
          if (date == null) {
            return;
          }
          if(user.isBeforeDate(date,user.getPortfolioCreationDate(portfolioName))) portfolioValue=0.0;
          else portfolioValue = user.getPortfolioValue(portfolioName, date);
          if (portfolioValue == null) {
            ViewPrint.printInCompatiblePortfolio(out);
            return;
          }
          ViewPrint.printPortfolioValue(portfolioValue, out);
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
          LoadSingleFlexPortfolioDetail.loadSinglePortfolioDetailController(scan, user,out);
          return;
        case "8":
          return;
        default:
          ViewPrint.loadPortfolioErrorNote(out);
          option = scan.nextLine();
          comingFromDefault = true;
      }
      if (!comingFromDefault) {
        ViewPrint.loadPortfolioDetailMenu(out);
        option = scan.nextLine().trim();
      }
      comingFromDefault = false;
    }
    while (!option.equals("8"));

  }

}
