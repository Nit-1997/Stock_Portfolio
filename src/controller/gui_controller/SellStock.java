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
    String portfolioName = buySellData.get(0);

    String stock = buySellData.get(1).toUpperCase();
    if (stock.equals(""))
      return "Empty Stock";
    else if (!this.stockMap.containsKey(stock))
      return "<html>Please enter a valid stock<br /> name from the list</html>";

    String quantity = buySellData.get(2);
    if (quantity.equals(""))
      return "Empty Quantity";
    int quan = 0;
    try {
      quan = Integer.parseInt(quantity);
      if (quan < 0)
        return "Negative quantity passed";
    } catch (NumberFormatException e) {
      return "Wrong Quantity Format";
    }
    if (this.stockMap.get(stock) < quan) {
      return "<html>Entered stock number less than <br />existing shares for " + stock + "</html>";
    }

    String date = buySellData.get(3);

    String commFee = buySellData.get(4);
    Double commFeeDouble = null;
    if(commFee.equals("")) commFeeDouble=0.0;
    if(!commFee.equals("")){
      try{
        commFeeDouble = Double.parseDouble(commFee);
        if(commFeeDouble<0) return "Negative Commission Fee";
      }catch(NumberFormatException e){
        return "Wrong Commission Fee Format";
      }
    }

    double quanDouble = -quan;

    try {
      user.transactionForPortfolio(portfolioName, new SimpleEntry<>(stock,
          new SimpleEntry<>(date, new SimpleEntry<>(quanDouble, commFeeDouble))));
      return "Successful transaction";
    } catch (Exception e) {
      return e.getMessage();
    }

  }
}
