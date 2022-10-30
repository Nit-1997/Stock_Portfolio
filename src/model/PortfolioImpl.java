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


/**
 * This class creates the Portfolio.
 * Portfolio consists of stocks and it's quantity.
 */
final public class PortfolioImpl implements Portfolio {

  private final String name;
  private final List<StockOrderImpl> stockOrder;


  public PortfolioImpl(String name) throws FileNotFoundException {
    Map<String, List<Double>> stocksMap = this.loadPortfolioData(name);
    this.stockOrder = new ArrayList<>();
    this.name = name;
    for (String key : stocksMap.keySet()) {
      double qty = stocksMap.get(key).get(1);
      double buyPrice = stocksMap.get(key).get(0);
      String date = this.fetchDateFromPortfolioFile(name);
      this.stockOrder.add(new StockOrderImpl(key, buyPrice, date, qty));
    }
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
    this.saveToFile();
  }


  /**
   * saves the current portfolio to the file.
   */
  private void saveToFile() throws IOException {
    this.createFileIfNotExists();
    this.writePortfolioToFile();
  }


  private void writePortfolioToFile() throws IOException {
    /*
          File_name : this.name
         { stock_name , buy_price ,qty }
     */
    String path = Paths.get("portfolios")
            .toAbsolutePath() + "\\" + this.name + ".csv";
    FileWriter myWriter = new FileWriter(path);
    for (StockOrderImpl order : this.stockOrder) {
      myWriter.write("" + order.getStock().getStockTickerName()
              + "," + order.getStock().getBuyPrice()
              + "," + order.getQuantity()
              + "," + order.getStock().getBuyDate()+"\n"
      );
    }
    myWriter.close();
    System.out.println("Successfully wrote to the file.");
  }

  private void createFileIfNotExists() throws IOException {
    String path = Paths.get("portfolios")
            .toAbsolutePath() + "\\" + this.name + ".csv";
    File portfolioFile = new File(path);
    if (portfolioFile.createNewFile()) {
      System.out.println("Portfolio created to file : " + portfolioFile.getName());

    } else {
      System.out.println("Portfolio already exists reading from it ...");
    }
  }

  private String fetchDateFromPortfolioFile(String portfolioName) throws FileNotFoundException {
    String path = Paths.get("portfolios")
            .toAbsolutePath() + "\\" + portfolioName + ".csv";
    File portfolioFile = new File(path);
    Scanner myReader = new Scanner(portfolioFile);
    String date = "";
    if(myReader.hasNextLine()){
      String input = myReader.nextLine();
      String[] splitInput = input.split(",");
      date = splitInput[3];
    }else{
      throw new FileNotFoundException("Content empty");
    }
    myReader.close();
    return date;
  }

  /**
   * Fetches data for the portfolio from local directory
   *
   * @return HashMap of Ticker Symbol vs {buy price, quantity}
   */
  private Map<String, List<Double>> loadPortfolioData(String portfolioName) throws FileNotFoundException {
    String path = Paths.get("portfolios")
            .toAbsolutePath() + "\\" + portfolioName + ".csv";
    File portfolioFile = new File(path);
    Scanner myReader = new Scanner(portfolioFile);

    Map<String, List<Double>> parsedFileInput = new HashMap<>();
    while (myReader.hasNextLine()) {
      List<Double> currentList = new ArrayList<>();
      String input = myReader.nextLine();
      String[] splitInput = input.split(",");
      currentList.add(Double.parseDouble(splitInput[1]));
      currentList.add(Double.parseDouble(splitInput[2]));
      parsedFileInput.put(splitInput[0], currentList);
    }
    myReader.close();
    return parsedFileInput;
  }


  @Override
  public double getCurrentValue() {
    double val = 0.0;
    for (StockOrderImpl order : this.stockOrder) {
      val += order.getCurrentOrderValue();
    }
    return val;
  }

  @Override
  public double getInitialValue() {
    double val = 0.0;
    for (StockOrderImpl order : this.stockOrder) {
      val += order.getInitialOrderValue();
    }
    return val;
  }

  @Override
  public double getValueOnDate(String date) {
    double val = 0.0;
    for (StockOrderImpl order : this.stockOrder) {
      val += order.getOrderValueOnDate(date);
    }
    return val;
  }


  @Override
  public double getPortfolioPnL() throws IOException {
    double val = 0.0;
    for (StockOrderImpl order : this.stockOrder) {
      val += order.getOrderPnL();
    }
    return val;
  }

  @Override
  public List<StockOrderImpl> getPortfolioSummary() {
    //TODO : fetch local file data
    //TODO : parse data send back
    /**
     *  { ticker_symbl , qty}
     */
    return this.stockOrder;
  }

  @Override
  public Map<StockOrderImpl, List<Double>> getCurrentPortfolioDetailed() {

    //TODO : fetch local file data
    //TODO : parse data send back
    //TODO : make a PortfolioDetails pojo class
    /**
     * Pair{[{ticker_name , buy_price , curr_price , qty , PnL},...] , totalPnL}
     */
    return null;
  }

  @Override
  public Map<StockOrderImpl, List<Double>> getPortfolioDetailedOnDate(String date) {
    return null;
  }


}
