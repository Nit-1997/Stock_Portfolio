package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

/**
 * This class creates the Portfolio.
 * Portfolio consists of stocks and it's quantity.
 */
final class PortfolioImpl implements Portfolio {

  private final StockImpl[] stocks;
  private final Double[] quantity;


  /**
   * Creates a new portfolio.
   *
   * @param stocksMap map of {ticker , qty}
   */
  public PortfolioImpl(HashMap<String, Double> stocksMap, String name) throws Exception {
    int n = stocksMap.size();
    this.stocks = new StockImpl[n];
    this.quantity = new Double[n];
    int i = 0;
    for (String key : stocksMap.keySet()) {
      stocks[i] = new StockImpl(key);
      quantity[i] = stocksMap.get(key);
      i++;
    }
    if (!this.saveToFile(name)) {
      throw new FileNotFoundException("Not found");
    }
  }


  //TODO handle and throw exception for file handling

  /**
   * saves the current portfolio to the file.
   *
   * @return true / false based on operation
   */
  private boolean saveToFile(String name) {
    return false;
  }

  @Override
  public Double getCurrentPrice() {
    Double val = 0.0;
    //TODO fetch portfolio data from local file
    for (int i = 0; i < stocks.length; i++) {
      val += stocks[i].getCurrentPrice() * quantity[i];
    }
    return val;
  }

  @Override
  public Double getInitialValue() {
    Double val = 0.0;
    //TODO fetch portfolio data from local file
    for (int i = 0; i < stocks.length; i++) {
      val += stocks[i].getBuyPrice() * quantity[i];
    }
    return val;
  }

  @Override
  public double getPortfolioPnL() throws IOException {
    try{
      double val = 0.0;
      //TODO fetch portfolio data from local file
      for (int i = 0; i < stocks.length; i++) {
        val += stocks[i].getPnL() * quantity[i];
      }
      return val;
    }catch(IOException e){
      throw e;
    }
  }

  @Override
  public void getPortfolioSummary(String name) {
    //TODO : fetch local file data
    //TODO : parse data send back
    /**
     *  { ticker_symbl , qty}
     */
  }

  @Override
  public void getPortfolioDetailed(String name) {
    //TODO : fetch local file data
    //TODO : parse data send back
    //TODO : make a PortfolioDetails pojo class
    /**
     * Pair{[{ticker_name , buy_price , curr_price , qty , PnL},...] , totalPnL}
     */
  }


}
