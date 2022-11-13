package controller.Commands;

import java.io.PrintStream;
import java.util.Scanner;
import model.UserFlex;
import view.ViewPrint;

public class CostBasis {

  public static void calculateCostBasis(Scanner scan, PrintStream out, UserFlex user, String portfolioName){
    ViewPrint.waitLoadMessage(out);
    String date;
    do{
      ViewPrint.askDate(out);
      date = scan.nextLine();
      if (date.equals("0")) {
        return;
      }
      if(!user.dateChecker(date)){
        ViewPrint.wrongDateMsg(out);
      }else if(user.isBeforeDate(date,user.getPortfolioCreationDate(portfolioName))) {
        ViewPrint.wrongDateBeforePortfolioCreation(out);
      }
    }
    while (!user.dateChecker(date) || user.isBeforeDate(date,user.getPortfolioCreationDate(portfolioName)));

    Double cost = user.getCostBasis(portfolioName,date);
    ViewPrint.printCostBasis(cost,out);
  }

}
