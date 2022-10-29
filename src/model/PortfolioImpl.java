package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class creates the Portfolio.
 * Portfolio consists of stocks and it's quantity.
 */
final class PortfolioImpl implements Portfolio {

  private final String name;
  private final List<StockOrderImpl> stockOrder;


  /**
   * Creates a new portfolio.
   *
   * @param stocksMap map of {ticker , qty}
   */
  public PortfolioImpl(Map<String, Double> stocksMap, String name) throws Exception {
    this.stockOrder = new ArrayList<>();
    this.name = name;
    for (String key : stocksMap.keySet()) {
      this.stockOrder.add(new StockOrderImpl(key, stocksMap.get(key)));
    }
    this.saveToFile();
  }


  //TODO handle and throw exception for file handling

  /**
   * saves the current portfolio to the file.
   *
   */
  private void saveToFile() throws IOException {

  }


  private void writePortfolioToFile() throws IOException {
    /*
          File_name : this.name
         { stock_name , buy_price ,qty , buy_date }
     */
    FileWriter myWriter = new FileWriter(this.name + ".txt");
    for (StockOrderImpl order : this.stockOrder) {
      myWriter.write("" + order.getStock().getStockTickerName()
              + "," + order.getStock().getBuyPrice()
              + "," + order.getQuantity()
              + "," + order.getStock().getBuyPrice() + "\n"
      );
    }
    myWriter.close();
    System.out.println("Successfully wrote to the file.");
  }

  private void createFileIfNotExists() throws IOException {
    File portfolioFile = new File(this.name + ".txt");
    if (portfolioFile.createNewFile()) {
      System.out.println("Portfolio created to file : " + portfolioFile.getName());

    } else {
      System.out.println("Portfolio already exists reading from it ...");
    }
  }

  /**
   * Fetches data for the portfolio from local directory
   *
   * @return HashMap of Ticker Symbol vs {buy price, quantity}
   */
  private void fetchPortfolioData() {
    // TODO if reference variables are null, then fetch data from local directory and fill the variables
    // else just return
  }


  @Override
  public double getCurrentPrice() {
    double val = 0.0;
    //TODO fetch portfolio data from API data
    this.fetchPortfolioData();
    for(StockOrderImpl order : this.stockOrder){
      val += order.getCurrentOrderValue();
    }
    return val;
  }

  @Override
  public double getInitialValue() {
    double val = 0.0;
    //TODO fetch portfolio data from local file
    this.fetchPortfolioData();
    for(StockOrderImpl order : this.stockOrder){
      val += order.getInitialOrderValue();
    }
    return val;
  }

  @Override
  public double getPortfolioPnL() throws IOException {
    double val = 0.0;
    //TODO fetch portfolio data from local file
    this.fetchPortfolioData();
    for(StockOrderImpl order : this.stockOrder){
      val += order.getOrderPnL();
    }
    return val;
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
