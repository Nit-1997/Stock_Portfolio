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
      if(!FormChecker.formChecker(data,user).equals("Portfolio Successfully Saved")) return FormChecker.formChecker(data,user);
      if(!user.isUniqueName(portfolioName)) return "Please enter a unique portfolio Name";
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
    if(!FormChecker.formChecker(data,user).equals("Portfolio Successfully Saved")) return FormChecker.formChecker(data,user);
    if(!user.isUniqueName(portfolioName)) return "Please enter a unique portfolio Name";
    Map<String, Map<String, SimpleEntry<Double, Double>>> stockMap2 = this.parseExtra();
    String stock = data.get(1).toUpperCase();
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
    boolean flag=false;
    Map<String, Map<String, SimpleEntry<Double, Double>>> stockMap = new HashMap<>();
    for (String line : arr) {
      if(!flag){
        flag=true;
        continue;
      }
      String[] stockData = line.split(",");
      String stock = stockData[0].toUpperCase();
      Double stockQuantity = Double.parseDouble(stockData[2]);
      String date = stockData[1];
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





}
