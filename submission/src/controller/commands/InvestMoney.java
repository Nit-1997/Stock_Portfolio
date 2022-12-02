package controller.commands;

import java.io.PrintStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import model.UserFlexInvest;
import view.ViewText;

public class InvestMoney {

  public static void investMoneyInExistingPortfolio(Scanner scan,  PrintStream out, UserFlexInvest user,
      String portfolioName, ViewText view) {

    view.waitLoadMessage(out);

    String portfolioCreationDate = null;
    try {
      portfolioCreationDate = user.getPortfolioCreationDate(portfolioName);
    } catch (Exception e) {
      portfolioCreationDate = null;
    }
    if (portfolioCreationDate == null) {
      view.printInCompatiblePortfolio(out);
      return;
    }

    System.out.println("Valid input range : ("+portfolioCreationDate+") -> ("+ LocalDate.now()+")");

    String date;
    do{
      view.askDate(out);
      date = scan.nextLine();
      if (date.equals("0")) {
        return;
      }
      if (!user.dateChecker(date)) {
        view.wrongDateMsg(out);
      } else if (user.isBeforeDate(date, portfolioCreationDate)) {
        System.out.println("kindly enter date after portfolio creation date");
      }
    }
    while (!user.dateChecker(date) || user.isBeforeDate(date, portfolioCreationDate));

    Map<String, Double> portfolioComposition;
    try {
      portfolioComposition = user.getPortfolioSummary(portfolioName,date);
    } catch (Exception e) {
      portfolioComposition=null;
    }
    if (portfolioComposition == null) {
      view.printInCompatiblePortfolio(out);
      return;
    }
    view.printPortfolioSummary(portfolioComposition, out);



    Map<String, Double> investMap = new HashMap<>();

    System.out.println("Enter percentages for each stock");

    double sum=0.0;
    for(String ticker : portfolioComposition.keySet()){
      Double percent = AskWeightOfStock.askWeight(out,ticker,scan);
      if(percent == -1) return;
      sum+=percent;
      investMap.put(ticker,percent);
    }

    if(sum!=100.0){
      System.out.println("shares weight doesnt add upto 100.");
      return;
    }

    System.out.print("Amount to be invested : ");
    String amount = scan.nextLine();
    Double amountDouble;
    while(true){
      try{
        amountDouble=Double.parseDouble(amount);
        break;
      } catch (NumberFormatException e){
        out.print("Kindly enter a valid amount for investment : ");
        amount= scan.nextLine();
      }
    }

    Double commFee = AskCommissionFees.askCommissionFees(scan, out, view);
    if (commFee == null) {
      return;
    }

    try {
      user.investMoney(portfolioName,amountDouble, investMap, date, commFee);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }


  }

}
