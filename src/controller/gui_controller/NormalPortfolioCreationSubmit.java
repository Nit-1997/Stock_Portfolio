package controller.gui_controller;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.UserFlexInvest;
import model.UserFlexInvestImpl;

public class NormalPortfolioCreationSubmit {

  List<String> data;
  UserFlexInvest user;
  public NormalPortfolioCreationSubmit(List<String> data, UserFlexInvest user){
    this.data=data;
    this.user=user;
  }

  public String execute()  {
    String portfolioName = data.get(0);
    if(portfolioName.equals("")) return "Empty Portfolio Name";
    else if(!user.isUniqueName(portfolioName)) return "Please enter a unique portfolio Name";

    // if nothing in form but in textArea
    if(data.get(1).equals("") && data.get(2).equals("") && data.get(3).equals("") && data.get(4).equals("") && !data.get(5).equals("")){
      try {
        user.addPortfolio(portfolioName,this.parseExtra());
      } catch (Exception e) {
        return e.getMessage();
      }
      return "Portfolio Successfully Saved";
    }

    //nothing in textArea but in form

    if(data.get(5).equals("")){
      if(!this.formChecker().equals("Portfolio Successfully Saved")) return this.formChecker();
      Map<String, Map<String, SimpleEntry<Double, Double>>> stockMap = new HashMap<>();
      Map<String, SimpleEntry<Double,Double>> dateMap = new HashMap<>();
      dateMap.put(data.get(3), new SimpleEntry<>(Double.parseDouble(data.get(2)),Double.parseDouble(data.get(4))));
      stockMap.put(data.get(1),dateMap);
      try {
        user.addPortfolio(portfolioName,stockMap);
      } catch (Exception e) {
        return e.getMessage();
      }
      return "Portfolio Successfully Saved";
    }

    // data in both textArea and in form
    if(!this.formChecker().equals("Portfolio Successfully Saved")) return this.formChecker();
    Map<String, Map<String, SimpleEntry<Double, Double>>> stockMap2 = this.parseExtra();
    String stock = data.get(1);
    String date = data.get(3);
    Double quantity = Double.parseDouble(data.get(2));
    Double commFee = Double.parseDouble(data.get(4));
    if(stockMap2.containsKey(stock)){
      if(stockMap2.get(stock).containsKey(date)) stockMap2.get(stock).put(date,new SimpleEntry<>(stockMap2.get(stock).get(date).getKey()+quantity,commFee));
      else stockMap2.get(stock).put(date,new SimpleEntry<>(quantity,commFee));
    }
    else{
      Map<String, SimpleEntry<Double, Double>> newStockMap = new HashMap<>();
      newStockMap.put(date, new SimpleEntry<>(quantity, commFee));
      stockMap2.put(stock, newStockMap);
    }

    try {
      user.addPortfolio(portfolioName,stockMap2);
    } catch (Exception e) {
      return e.getMessage();
    }
    return "Portfolio Successfully Saved";
  }

  public Map<String, Map<String, SimpleEntry<Double, Double>>> parseExtra() {
    String[] arr = data.get(5).split("\n");
    Map<String, Map<String, SimpleEntry<Double, Double>>> stockMap = new HashMap<>();
    for (String line : arr) {
      String[] stockData = line.split(",");
      String stock = stockData[0];
      Double stockQuantity = Double.parseDouble(stockData[1]);
      String date = stockData[2];
      Double commFee = Double.parseDouble(stockData[3]);
      if (stockMap.containsKey(stock)) {
        // if stock also exist and that date also exist, just add in prev date
        if (stockMap.get(stock).containsKey(date)) {
          stockMap.get(stock).put(date,
              new SimpleEntry<>(stockMap.get(stock).get(date).getKey() + stockQuantity,
                  commFee));
        } else {
          stockMap.get(stock).put(date, new SimpleEntry<>(stockQuantity, commFee));
        }
      } else {
        Map<String, SimpleEntry<Double, Double>> newStockMap = new HashMap<>();
        newStockMap.put(date, new SimpleEntry<>(stockQuantity, commFee));
        stockMap.put(stock, newStockMap);
      }
    }

    return stockMap;
  }



  private String formChecker(){


    String stock = data.get(1);
    if(stock.equals("")) return "Empty Stock";
    else if(!user.isValidStock(stock)) return "Please enter a valid stock name";

    String quantity = data.get(2);
    int quan;
    if(quantity.equals("")) return "Empty Quantity";
    try{
      quan = Integer.parseInt(quantity);
      if(quan<0) return "Negative quantity passed";
    }catch(NumberFormatException e){
      return "Wrong Quantity Format";
    }

    String date = data.get(3);
    if(!user.dateChecker(date)) return "Wrong date format";

    String commFee = data.get(4);
    Double commFeeDouble;
    if(commFee.equals("")) commFeeDouble=0.0;
    try{
      commFeeDouble = Double.parseDouble(commFee);
      if(commFeeDouble<0) return "Negative Commission Fee";
    }catch(NumberFormatException e){
      return "Wrong Commission Fee Format";
    }


    return "Portfolio Successfully Saved";
  }

}
