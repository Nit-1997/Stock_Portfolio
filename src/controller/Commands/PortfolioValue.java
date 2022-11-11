package controller.Commands;

import java.io.PrintStream;
import model.UserFlex;
import view.ViewPrint;

public class PortfolioValue {

  public static void getPortfolioValue(String portfolioName, String date, PrintStream out, UserFlex user){
    ViewPrint.waitLoadMessage(out);
    Double portfolioValue;
    if(user.isBeforeDate(date,user.getPortfolioCreationDate(portfolioName))) portfolioValue=0.0;
    else portfolioValue = user.getPortfolioValue(portfolioName, date);

    if (portfolioValue == null) {
      ViewPrint.printInCompatiblePortfolio(out);
      return;
    }
    ViewPrint.printPortfolioValue(portfolioValue, out);
  }

}
