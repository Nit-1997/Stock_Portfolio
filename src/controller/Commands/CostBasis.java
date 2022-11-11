package controller.Commands;

import java.io.PrintStream;
import java.util.Scanner;
import model.UserFlex;
import view.ViewPrint;

public class CostBasis {

  public static void calculateCostBasis(Scanner scan, PrintStream out, UserFlex user, String portfolioName){
    ViewPrint.waitLoadMessage(out);
    ViewPrint.askDate(out);
    String date;
    do{
      ViewPrint.askDate(out);
      date = scan.nextLine();
      if (date.equals("0")) {
        return;
      }
      if(!user.dateChecker(date)){
        System.out.println("Please enter in the correct format in the given range(0 to return to list view): ");
      }else if(user.isBeforeDate(date,user.getPortfolioCreationDate(portfolioName))) {
        System.out.println("please enter a date after portfolio creation(0 to return to list view) :");
      }
    }
    while (!user.dateChecker(date) || user.isBeforeDate(date,user.getPortfolioCreationDate(portfolioName)));

    Double cost = user.getCostBasis(portfolioName,date);
    System.out.println("\nCost basis of the portfolio till the given date : "+cost);
  }

}
