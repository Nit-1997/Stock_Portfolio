package controller.Commands;

import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;
import model.UserFlex;
import view.ViewPrint;

public class PortfolioSummary {

  public static void getPortfolioSummary(Scanner scan, PrintStream out, UserFlex user, String portfolioName){
    ViewPrint.waitLoadMessage(out);
    String date = AskDate.addStocksAskDate(scan, out, user);
    if (date == null) {
      return;
    }
    if(user.isBeforeDate(date,user.getPortfolioCreationDate(portfolioName))){
      System.out.println("please enter a date after portfolio creation");
      return;
    }
    Map<String, Double> stockMap = user.getPortfolioSummary(portfolioName, date);
    if (stockMap == null) {
      ViewPrint.printInCompatiblePortfolio(out);
      return;
    }
    ViewPrint.printPortfolioSummary(stockMap, out);
  }

}
