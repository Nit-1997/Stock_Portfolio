package controller.gui_controller;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import model.UserFlexInvest;

public class SellStock {

  List<String> buySellData;

  Map<String, Double> stockMap;
  UserFlexInvest user;


  public SellStock(List<String> buySellData, Map<String, Double> stockMap, UserFlexInvest user){
    this.buySellData=buySellData;
    this.user=user;
    this.stockMap=stockMap;
  }

  public String execute() {
    if(!FormChecker.formChecker(buySellData,user).equals("Portfolio Successfully Saved")) {
      return FormChecker.formChecker(buySellData,user);
    }


    String portfolioName = buySellData.get(0);

    String stock = buySellData.get(1).toUpperCase();

    String quantity = buySellData.get(2);
    double quanDouble = -Double.parseDouble(quantity);

    String date = buySellData.get(3);

    String commFee = buySellData.get(4);
    Double commFeeDouble = Double.parseDouble(commFee);

    if (!this.stockMap.containsKey(stock))
      return "<html>Please enter a valid stock<br /> name from the list</html>";

    if (this.stockMap.get(stock) < quanDouble) {
      return "<html>Entered stock number less than <br />existing shares for " + stock + "</html>";
    }



    try {
      user.transactionForPortfolio(portfolioName, new SimpleEntry<>(stock,
          new SimpleEntry<>(date, new SimpleEntry<>(quanDouble, commFeeDouble))));
      return "Successful transaction";
    } catch (Exception e) {
      return e.getMessage();
    }

  }
}
