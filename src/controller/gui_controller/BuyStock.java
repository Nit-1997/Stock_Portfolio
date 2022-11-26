package controller.gui_controller;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import model.UserFlexInvest;

public class BuyStock {

  List<String> buySellData;
  UserFlexInvest user;


  public BuyStock(List<String> buySellData, UserFlexInvest user){
    this.buySellData=buySellData;
    this.user=user;
  }

  public String execute(){

    String portfolioName = buySellData.get(0);

    String stock = buySellData.get(1).toUpperCase();
    if(stock.equals("")) return "Empty Stock";
    else if(!user.isValidStock(stock)) return "Please enter a valid stock name";

    String quantity = buySellData.get(2);
    int quan;
    if(quantity.equals("")) return "Empty Quantity";
    try{
      quan = Integer.parseInt(quantity);
      if(quan<0) return "Negative quantity passed";
    }catch(NumberFormatException e){
      return "Wrong Quantity Format";
    }

    String date = buySellData.get(3);
    if(date.equals("")) return "Empty Date";
    if(!user.dateChecker(date)) return "Wrong date format";
    try {
      if(user.isBeforeDate(date,user.getPortfolioCreationDate(portfolioName))) return "Given date before portfolio creation";
    } catch (Exception e) {
      return e.getMessage();
    }

    String commFee = buySellData.get(4);
    Double commFeeDouble;
    if(commFee.equals("")) return "Please enter commission fee";
    try{
      commFeeDouble = Double.parseDouble(commFee);
      if(commFeeDouble<0) return "Negative Commission Fee";
    }catch(NumberFormatException e){
      return "Wrong Commission Fee Format";
    }

    double quanDouble = quan;

    try {
      boolean val = user.transactionForPortfolio(portfolioName,new SimpleEntry<>(stock,new SimpleEntry<>(date,new SimpleEntry<>(quanDouble,commFeeDouble))));
    } catch (Exception e) {
      return e.getMessage();
    }

    return "Successful transaction";
  }

}
