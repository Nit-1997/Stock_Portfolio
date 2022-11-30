package model;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;

public class UserFlexInvestImpl extends UserFlexImpl implements UserFlexInvest{

  public UserFlexInvestImpl(){
    super();
  }

  @Override
  public void investMoney(String portfolioName, Double amount,
      Map<String, Double> percentage, String date, Double commFee)
      throws Exception {
    this.loadPortfolio(portfolioName);
    // TODO : add a new function for addMultipleTransactions and add in one go to portfolio stockOrder List
    boolean flag=false;
    Map<String,SimpleEntry<String,SimpleEntry<Double,Double>>> map = new HashMap<>();
    for(String ticker : percentage.keySet()){
        if(!flag){
          flag=true;
        }
        else{
          commFee=0.0;
        }
        Double qty = amount*percentage.get(ticker)/100;
        map.put(ticker,new SimpleEntry<>(date,new SimpleEntry<>(qty,commFee)));

      }

    this.portfolioMap.get(portfolioName).addMultipleTransactions(map);
  }


  @Override
  public void addPortfolio(String portfolioName, Double amount, Map<String, Double> weightage,
      String startDate, String endDate,int interval, Double commFee) throws Exception {
    try {
      if (!this.isUniqueName(portfolioName)) {
        throw new IOException("Portfolio with the asked name already exist");
      }
      this.portfolioMap.put(portfolioName, new PortfolioFlexInvestImpl(portfolioName, amount, weightage,
          startDate, endDate, interval, commFee));
    } catch (Exception e) {
      if (e.getMessage().equals("Asked stock didn't exist on that date") || e.getMessage()
          .equals("Portfolio with the asked name already exist")) {
        throw new IOException(e);
      }
      throw e;
    }
  }

  @Override
  public void InvestThroughDCA(String portfolioName, Double amount, Map<String, Double> weightage,
      String startDate, String endDate, int interval, Double commFee) throws Exception {

    this.loadPortfolio(portfolioName);

    PortfolioFlexInvestImpl obj = ((PortfolioFlexInvestImpl) this.portfolioMap.get(portfolioName));


    obj.addDCAInvestment(amount,weightage,startDate,endDate,interval,commFee);


  }


  private String latestTransactionDateForStock(String portfolioName, String ticker){
    Map<String, SimpleEntry<String, Double>> map = this.getPortfolioState(portfolioName);
    for(String stock : map.keySet()){
      if(stock.equals(ticker)) return map.get(stock).getKey();
    }
    return null;
  }

  @Override
  public boolean transactionForPortfolio(String portfolioName,
      SimpleEntry<String, SimpleEntry<String, SimpleEntry<Double, Double>>> newStock)
      throws Exception {

    Double qty = newStock.getValue().getValue().getKey();
    String date = newStock.getValue().getKey();
    String portfolioCreationDate = this.getPortfolioCreationDate(portfolioName);
    String tempDate = qty>0 ? portfolioCreationDate : this.latestTransactionDateForStock(portfolioName,
        newStock.getKey());

    try {
      this.loadPortfolio(portfolioName);
      this.portfolioMap.get(portfolioName).addTransaction(newStock,tempDate);
      return true;
    } catch (Exception e) {
      throw e;
    }
  }

}
