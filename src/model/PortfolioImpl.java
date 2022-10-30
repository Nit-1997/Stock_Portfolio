package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Utils.Utils;


/**
 * This class creates the Portfolio.
 * Portfolio consists of stocks and it's quantity.
 */
final public class PortfolioImpl implements Portfolio {

  private final String name;
  private final List<StockOrder> stockOrder;


  public PortfolioImpl(String name) throws Exception {
    this.stockOrder =  this.loadPortfolioData(name);
    this.name = name;
  }

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
    Utils.saveToFile(this.name,this.stockOrder);
  }



  /**
   * Fetches data for the portfolio from local directory
   *
   * @return List of stock orders
   */
  private List<StockOrder> loadPortfolioData(String portfolioName) throws Exception {
//    String path = Paths.get("portfolios")
//            .toAbsolutePath() + "\\" + portfolioName + ".csv";
//    File portfolioFile = new File(path);
    File portfolioFile = Utils.getPortfolioFileByName(portfolioName);
    Scanner myReader = new Scanner(portfolioFile);

    List<StockOrder> parsedFileInput = new ArrayList<>();
    while (myReader.hasNextLine()) {
      List<Double> currentList = new ArrayList<>();
      String input = myReader.nextLine();
      String[] splitInput = input.split(",");
      String ticker = splitInput[0];
      String date = splitInput[3];
      double price = Double.parseDouble(splitInput[1]);
      double qty = Double.parseDouble(splitInput[2]);
      StockOrder currentStockOrder = new StockOrderImpl(ticker,price,date,qty);
      parsedFileInput.add(currentStockOrder);
    }
    myReader.close();
    return parsedFileInput;
  }


  @Override
  public double getCurrentValue() {
    double val = 0.0;
    for (StockOrder order : this.stockOrder) {
      val += order.getCurrentOrderValue();
    }
    return val;
  }

  @Override
  public double getInitialValue() {
    double val = 0.0;
    for (StockOrder order : this.stockOrder) {
      val += order.getInitialOrderValue();
    }
    return val;
  }

  @Override
  public double getValueOnDate(String date) {
    double val = 0.0;
    for (StockOrder order : this.stockOrder) {
      val += order.getOrderValueOnDate(date);
    }
    return val;
  }


  @Override
  public double getPortfolioPnL() throws IOException {
    double val = 0.0;
    for (StockOrder order : this.stockOrder) {
      val += order.getOrderPnL();
    }
    return val;
  }

  @Override
  public List<StockOrder> getPortfolioSummary() {
    //TODO : fetch local file data
    //TODO : parse data send back
    /**
     *  { ticker_symbl , qty}
     */
    return this.stockOrder;
  }

  @Override
  public Map<StockOrder, List<Double>> getCurrentPortfolioDetailed() {

    //TODO : fetch local file data
    //TODO : parse data send back
    //TODO : make a PortfolioDetails pojo class
    /**
     * Pair{[{ticker_name , buy_price , curr_price , qty , PnL},...] , totalPnL}
     */
    return null;
  }

  @Override
  public Map<StockOrder, List<Double>> getPortfolioDetailedOnDate(String date) {
    return null;
  }


}
