package controller.gui_controller;

import java.util.List;
import java.util.Map;
import model.UserFlexInvest;

public class InvestStock {

  List<String> investData;

  Map<String,Double> stockMap;

  UserFlexInvest user;

  public InvestStock(List<String> investData, Map<String,Double> stockMap, UserFlexInvest user){
    this.investData=investData;
    this.stockMap=stockMap;
    this.user=user;
  }

  public String execute(){

    String portfolioName = investData.get(0);

    String date = investData.get(1);
    if(date.equals("")) return "Empty Date";
    if(!user.dateChecker(date)) return "Wrong date format";
    try {
      String portfolioCreationDate = user.getPortfolioCreationDate(portfolioName);
      if(user.isBeforeDate(date,portfolioCreationDate))
        return "Given date before portfolio creation date "+portfolioCreationDate;
    } catch (Exception e) {
      return e.getMessage();
    }

    String amount = investData.get(2);
    Double amountDouble;
    if(amount.equals("")) return "Empty Amount";
    try{
      amountDouble = Double.parseDouble(amount);
      if(amountDouble<0) return "Negative amount passed";
      if(amountDouble==0) return "0 amount passed";

    }catch(NumberFormatException e){
      return "Wrong Amount Format";
    }

    String commFee = investData.get(3);
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

    Double sum=0.0;
    for(String stock : stockMap.keySet()){
      sum+=stockMap.get(stock);
    }

    if(sum!=100.0){
      return "Weights don't add upto 100";
    }

    try {
      user.investMoney(portfolioName,amountDouble,stockMap,date,commFeeDouble);
    } catch (Exception e) {
      return e.getMessage();
    }

    return "Success";
  }

}
