package controller.gui_controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import model.UserFlexInvest;

public class DCAPortfolioCreation {

  List<String> data;
  UserFlexInvest user;

  Map<String, Double> map;

  DCAPortfolioCreation(List<String> data, Map<String, Double> map, UserFlexInvest user){
    this.data=data;
    this.user=user;
    this.map=map;
  }

  public String execute(){

    String portfolioName = data.get(0);
    if(portfolioName.equals("")) return "Empty Portfolio Name";
    else if(!user.isUniqueName(portfolioName)) return "Please enter a unique portfolio Name";

    String startDate = data.get(1);
    if(!user.dateChecker(startDate)) return "Wrong date format for start Date";

    String endDate = data.get(2);
    if(endDate.equals("")) endDate=null;

    if(endDate!=null){
      DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      sdf.setLenient(false);
      try{
        Date date = sdf.parse(endDate);
        Date firstDate = sdf.parse("2010-01-04");
        if(date.before(firstDate)) return "End date before 1st Jan 2010";
      } catch(ParseException e){
        return "Wrong date format";
      }
      if (user.isBeforeDate(endDate,startDate))  return "end Date before start date";
    }

    String amount = data.get(3);
    Double amountDouble;
    try{
      amountDouble = Double.parseDouble(amount);
    } catch (NumberFormatException e){
      return "amount not in numeric format";
    }
    if(amountDouble<=0) return "amount less than or equal to 0";

    String interval = data.get(4);
    Integer intervalInt;
    if(interval.equals("")) return "Empty Interval";
    try{
      intervalInt=Integer.parseInt(interval);
      if(intervalInt<0) return "Negative interval";
    }catch(NumberFormatException e){
      return "Wrong Interval Format";
    }

    String commFee = data.get(5);
    Double commFeeDouble;
    if(commFee.equals("")) commFeeDouble=0.0;
    try{
      commFeeDouble = Double.parseDouble(commFee);
      if(commFeeDouble<0) return "Negative Commission Fee";
    }catch(NumberFormatException e){
      return "Wrong Commission Fee Format";
    }


    Double sum=0.0;
    for(String stock : map.keySet()){
      if(stock.equals("")) return "Empty Stock";
      else if(!user.isValidStock(stock)) return "Please enter a valid stock name for "+stock;
      sum+=map.get(stock);
    }

    if(sum!=100.0){
      return "Weights don't add upto 100";
    }

    try {
      user.addPortfolio(portfolioName,amountDouble,map,startDate,endDate,intervalInt,commFeeDouble);
    } catch (Exception e) {
      return e.getMessage();
    }

    return "Portfolio Successfully Saved";
  }

}