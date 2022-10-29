package model;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Implementation of User and it's functionality.
 */
public class UserImpl implements User {
  Map<String, Portfolio> portfolioMap;

  /**
   * Constructor to initialize User Object with portfolios.
   */
  public UserImpl() {
    String portfolioDirectory = Paths.get("portfolios").toAbsolutePath().toString();
    File f = new File(portfolioDirectory);
    String[] files = f.list((f1, name) -> name.endsWith(".csv"));
    for(int i=0;i<files.length;i++){
      files[i]=files[i].substring(0,files[i].indexOf('.'));
    }
    for(String file : files) portfolioMap.put(file,null);
  }


  @Override
  public boolean addPortfolio(String name, Map<String, Double> stocks) {
    try {
      this.portfolioMap.put(name,null);
      new PortfolioImpl(stocks, name);
      return true;
    } catch (Exception e) {
      return false;
    }
  }




  @Override
  public Set<String> getPortfolios() {
    return this.portfolioMap.keySet();
  }


  @Override
  public Map<String, Double> getPortfolioSummary(String name) {
    portfolioMap.put(name,new PortfolioImpl(name));
    List<StockOrderImpl> list = portfolioMap.get(name).getPortfolioSummary();
    Map<String, Double> resMap = new HashMap<>();
    for(StockOrderImpl soi : list ) resMap.put(soi.getStock().getStockTickerName(),soi.getQuantity());
    return resMap;
  }

  @Override
  public Map< String , List<Double>> getPortfolioDetailed(String name, String date) {
    Map<String, List<Double>> resMap = new HashMap<>();
    String currentDate = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(LocalDateTime.now());
    Map<StockOrderImpl, List<Double>> map = new HashMap<>();
    if (date.equals(currentDate) && portfolioMap.keySet().contains(name)) {
      map = portfolioMap.get(name).getCurrentPortfolioDetailed();
    } else if (!date.equals(currentDate) && portfolioMap.keySet().contains(name)) {
      map = portfolioMap.get(name).getPortfolioDetailedOnDate(date);
      }
    portfolioMap.put(name, new PortfolioImpl(name));
    for (StockOrderImpl soi : map.keySet()) {
      String ticker_symbol = soi.getStock().getStockTickerName();
      List<Double> listVals = new ArrayList<>();
      listVals.add(soi.getQuantity());
      listVals.addAll(map.get(soi));
      resMap.put(ticker_symbol, listVals);
    }
    return resMap;
  }

  @Override
  public double getPortfolioValue(String name, String date){
    double portfolioValue = 0;
    String currentDate = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(LocalDateTime.now());
    if (date.equals(currentDate) && portfolioMap.keySet().contains(name)) {
      portfolioValue=portfolioMap.get(name).getCurrentValue();
    } else if (!date.equals(currentDate) && portfolioMap.keySet().contains(name)) {
      portfolioValue=portfolioMap.get(name).getValueOnDate(date);
    }
    return portfolioValue;
  }

  @Override
  public double getPortfolioPnL(String name, String date) {
    //TODO fix this
    //Portfolio port = (PortfolioImpl) portfolios.get();
    //return port.getPortfolioPnL();
    return 0d;
  }

  @Override
  public boolean isUniqueName(String name){
      return !this.portfolioMap.keySet().contains(name);
  }

  @Override
  public boolean ctrlCPressedChecker(String name){
    final boolean[] checker = {false};
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      killPortfolio(name);
      checker[0] =true;
    }));

    return checker[0];
  }

  private void killPortfolio(String name){

  }
}
