package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Utils.Utils;
import constants.Constants;


/**
 * Implementation of User and it's functionality.
 */
public class UserImpl implements User {
  Map<String, Portfolio> portfolioMap;

  /**
   * Constructor to initialize User Object with portfolios.
   */
  public UserImpl() throws Exception {
    Constants.stockNames = Utils.loadStockNames();
    String portfolioDirectory = Paths.get("portfolios").toAbsolutePath().toString();
    File f = new File(portfolioDirectory);
    String[] files = f.list((f1, name) -> name.endsWith(".csv"));

    for (int i = 0; i < files.length; i++) {
      files[i] = files[i].substring(0, files[i].indexOf('.'));
    }
    portfolioMap = new HashMap<>();
    for (String file : files) portfolioMap.put(file, null);
  }


  @Override
  public boolean addPortfolio(String name, Map<String, Double> stocks) {
    try {
      this.portfolioMap.put(name, new PortfolioImpl(stocks, name));
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
    try {
      if (portfolioMap.get(name) == null) portfolioMap.put(name, new PortfolioImpl(name));
      List<StockOrder> list = portfolioMap.get(name).getPortfolioSummary();
      Map<String, Double> resMap = new HashMap<>();
      for (StockOrder soi : list)
        resMap.put(soi.getStock().getStockTickerName(), soi.getQuantity());
      return resMap;
    } catch (FileNotFoundException e) {
      return null;
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public Map<String, List<Double>> getPortfolioDetailed(String name, String date) {
    date = Utils.dateSaturdaySundayChecker(date);
    try {
      if (portfolioMap.get(name) == null) portfolioMap.put(name, new PortfolioImpl(name));
      Map<String, List<Double>> resMap = new HashMap<>();
      String currentDate = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(LocalDateTime.now());
      List<PortfolioDetailedPojo> res = new ArrayList<>();
      if (date.equals(currentDate) && portfolioMap.containsKey(name)) {
        res = portfolioMap.get(name).getCurrentPortfolioDetailed();
      } else if (!date.equals(currentDate) && portfolioMap.containsKey(name)) {
        res = portfolioMap.get(name).getPortfolioDetailedOnDate(date);
      }
//      portfolioMap.put(name, new PortfolioImpl(name));
      for (PortfolioDetailedPojo pojo : res) {
        String ticker_symbol = pojo.getTicker();
        List<Double> listVals = new ArrayList<>();
        listVals.add(pojo.getQty());
        listVals.add(pojo.getBuyPrice());
        listVals.add(pojo.getCurrentPrice());
        listVals.add(pojo.getPnl());
        resMap.put(ticker_symbol, listVals);
      }
      return resMap;
    } catch (FileNotFoundException e) {
      System.out.println("in file not found exception");
      return null;
    } catch (Exception e) {
      System.out.println("in exception");
      return null;
    }
  }

  @Override
  public double getPortfolioValue(String name, String date) throws Exception {
    date = Utils.dateSaturdaySundayChecker(date);
    try {
      if (portfolioMap.get(name) == null) portfolioMap.put(name, new PortfolioImpl(name));
      double portfolioValue = 0;
      String currentDate = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(LocalDateTime.now());
      if (date.equals(currentDate)) {
        portfolioValue = portfolioMap.get(name).getCurrentValue();
      } else {
        portfolioValue = portfolioMap.get(name).getValueOnDate(date);
      }
      return portfolioValue;
    } catch (FileNotFoundException e) {
      return -1.0;
    }
  }

  @Override
  public double getPortfolioPnL(String name, String date) throws IOException {
    date = Utils.dateSaturdaySundayChecker(date);
    double portfolioPnL = 0;
    String currentDate = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(LocalDateTime.now());
    if (date.equals(currentDate)) {
      portfolioPnL = portfolioMap.get(name).getPortfolioPnL();
    } else {
      portfolioPnL = portfolioMap.get(name).getValueOnDate(date) - portfolioMap.get(name).getInitialValue();
    }
    return portfolioPnL;
  }

  @Override
  public boolean isUniqueName(String name) {
    return !this.portfolioMap.keySet().contains(name);
  }

  @Override
  public boolean ctrlCPressedChecker(String name) {
    final boolean[] checker = {false};
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      portfolioMap.get(name).deletePortfolio();
      portfolioMap.remove(name);
      checker[0] = true;
    }));

    return checker[0];
  }

  @Override
  public boolean dateChecker(String dateStr) {
    return Utils.dateChecker(dateStr);
  }


  @Override
  public Set<String> getStockList() {
    return Constants.stockNames;
  }

  @Override
  public boolean isValidStock(String name) {
    return Constants.stockNames.contains(name);
  }

}
