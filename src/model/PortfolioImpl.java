package model;

import java.io.FileNotFoundException;
import java.util.HashMap;

final class PortfolioImpl implements Portfolio {

  private final Stock[] stocks;
  private final Double[] quantity;


  /**
   * Creates a new portfolio.
   *
   * @param stocksMap map of {ticker , qty}
   */
  public PortfolioImpl(HashMap<String, Double> stocksMap, String name) throws Exception {
    int n = stocksMap.size();
    this.stocks = new Stock[n];
    this.quantity = new Double[n];
    int i = 0;
    for (String key : stocksMap.keySet()) {
      stocks[i] = new Stock(key);
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


  /**
   * Gets the current Price of the entire Portfolio.
   *
   * @return currentPrice of Portfolio
   */
  public Double getCurrentPrice() {
    Double val = 0.0;
    //TODO fetch portfolio data from local file
    for (int i = 0; i < stocks.length; i++) {
      val += stocks[i].getCurrentPrice() * quantity[i];
    }
    return val;
  }

  /**
   * Fetches the initial
   *
   * @return
   */
  public Double getInitialValue() {
    Double val = 0.0;
    //TODO fetch portfolio data from local file
    for (int i = 0; i < stocks.length; i++) {
      val += stocks[i].getBuyPrice() * quantity[i];
    }
    return val;
  }

  /**
   * Fetches the PnL at portfolio level.
   *
   * @return Portfolio PnL
   */
  public Double getPortfolioPnL() {
    Double val = 0.0;
    //TODO fetch portfolio data from local file
    for (int i = 0; i < stocks.length; i++) {
      val += stocks[i].getPnL() * quantity[i];
    }
    return val;
  }

  /**
   * Fetches basic portfolio data for a given name from local file.
   *
   * @param name name of the portfolio
   */
  public void retrievePortfolioSummary(String name) {
    //TODO : fetch local file data
    //TODO : parse data send back
    /**
     *  { ticker_symbl , qty}
     */
  }

  /**
   * Fetches complete portfolio data for a given name from local file.
   *
   * @param name name of the portfolio
   */
  public void retrievePortfolioDetailed(String name) {
    //TODO : fetch local file data
    //TODO : parse data send back
    //TODO : make a PortfolioDetails pojo class
    /**
     * Pair{[{ticker_name , buy_price , curr_price , qty , PnL},...] , totalPnL}
     */
  }


}
