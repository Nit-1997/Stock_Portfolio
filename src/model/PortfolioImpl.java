package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class creates the Portfolio.
 * Portfolio consists of stocks and it's quantity.
 */
final class PortfolioImpl implements Portfolio {

  private final String name;
  private final Stock[] stocks;
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
    this.name=name;
    int i = 0;
    for (String key : stocksMap.keySet()) {
      stocks[i] = new StockImpl(key);
      quantity[i] = stocksMap.get(key);
      i++;
    }
    if (!this.saveToFile()) {
      throw new FileNotFoundException("Not found");
    }
  }


  //TODO handle and throw exception for file handling

  /**
   * saves the current portfolio to the file.
   *
   * @return true / false based on operation
   */
  private boolean saveToFile() {
    return false;
  }


  private boolean writePortfolioToFile(){
    /*
          File_name : this.name
         { stock_name , buy_price ,qty , buy_date }
     */
    try {
      FileWriter myWriter = new FileWriter(this.name+".txt");
      for(int i=0 ; i<this.stocks.length;i++){
        myWriter.write(""+stocks[i].getStockTickerName()+","+stocks[i].getBuyPrice()+","+);
      }
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    return false;
  }

  private boolean createFileIfNotExists() {
    try {
      File portfolioFile = new File(this.name+".txt");
      if (portfolioFile.createNewFile()) {
        System.out.println("Portfolio created to file : " + portfolioFile.getName());

      } else {
        System.out.println("Portfolio already exists reading from it ...");
      }
      return true;
    } catch (IOException e) {
      System.out.println("An error occurred.");
      return false;
    }
  }

  /**
   * Fetches data for the portfolio from local directory
   * @return HashMap of Ticker Symbol vs {buy price, quantity}
   */
  private void fetchPortfolioData(){
    // TODO if reference variables are null, then fetch data from local directory and fill the variables
    // else just return
  }


  @Override
  public Double getCurrentPrice() {
    Double val = 0.0;
    //TODO fetch portfolio data from API data
    this.fetchPortfolioData();
    for (int i = 0; i < this.stocks.length; i++) {
      val += this.stocks[i].getCurrentPrice() * this.quantity[i];
    }
    return val;
  }

  @Override
  public Double getInitialValue() {
    Double val = 0.0;
    //TODO fetch portfolio data from local file
    this.fetchPortfolioData();
    for (int i = 0; i < this.stocks.length; i++) {
      val += this.stocks[i].getBuyPrice() * this.quantity[i];
    }
    return val;
  }

  @Override
  public double getPortfolioPnL() throws IOException {
    try{
      double val = 0.0;
      //TODO fetch portfolio data from local file
      this.fetchPortfolioData();
      for (int i = 0; i < this.stocks.length; i++) {
        val += this.stocks[i].getPnL() * this.quantity[i];
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
