package model;

import constants.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import utils.Utils;

public class UserFlexImpl implements UserFlex {

  private final Map<String, PortfolioFlex> portfolioMap;

  public UserFlexImpl() {
    Utils.clearStockDirectory();
    try {
      Constants.STOCK_NAMES = Utils.loadStockNames("stocks", "stocks_list.csv");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    String portfolioDirectory = Paths.get("portfolios" + File.separator + "flex").toAbsolutePath().toString();
    File f = new File(portfolioDirectory);
    if (!f.exists()) {
      f.mkdirs();
    }
    String[] files = f.list((f1, name) -> name.endsWith(".csv"));

    for (int i = 0; i < files.length; i++) {
      files[i] = files[i].substring(0, files[i].indexOf('.'));
    }
    portfolioMap = new HashMap<>();
    for (String file : files) {
      portfolioMap.put(file, null);
    }
  }

  @Override
  public Set<String> getPortfolios() {
    return this.portfolioMap.keySet();
  }

  @Override
  public Double getPortfolioValue(String name, String date) {
    // for each stock, multiply each quantity with stock price on that date and add till that date
    return null;
  }

  @Override
  public Double getPortfolioPnL(String name, String date) {
    return null;
  }

  @Override
  public boolean isUniqueName(String name) {
    return !this.portfolioMap.containsKey(name);
  }

  @Override
  public boolean dateChecker(String dateStr) {
    return Utils.dateChecker(dateStr);
  }

  @Override
  public Set<String> getStockList() {
    return Constants.STOCK_NAMES;
  }

  @Override
  public boolean isValidStock(String name) {
    return Constants.STOCK_NAMES.contains(name);
  }

  @Override
  public void cleanStockDirectory() {
    Utils.clearStockDirectory();
  }

  @Override
  public boolean addPortfolio(String name, Map<String, Map<String, Double>> stocksMap) {
    try {
      this.portfolioMap.put(name, new PortfolioFlexImpl(stocksMap, name));
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public Map<String, Double> getPortfolioSummary(String name, String date) {
    // ticker symbol vs quantity on that date
    try {
      if (portfolioMap.get(name) == null) {
        portfolioMap.put(name, new PortfolioFlexImpl(name));
      }
      Map<String , Double> list = portfolioMap.get(name).getPortfolioSummary(date);
      if (list == null) {
        return null;
      }
      Map<String, Double> resMap = new HashMap<>();
//      for (StockOrder soi : list) {
//        resMap.put(soi.getStock().getStockTickerName(), soi.getQuantity());
//      }
      return resMap;
    } catch (FileNotFoundException e) {
      return null;
    } catch (IOException e) {
      return null;
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public String getPortfolioCreationDate(String name) {
    return this.portfolioMap.get(name).getCreationDate();
  }

  @Override
  public Map<String, SimpleEntry<String, Double>> getPortfolioState(String portfolioName) {
    // ticker symbol vs latest_transaction_date vs quantity
    try {
      Map<String, SimpleEntry<String, Double>> portfolioState = portfolioMap.get(portfolioName).getLatestState();
      return null;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  @Override
  public boolean isBeforeDate(String firstDate, String secondDate) {
    return false;
  }

  @Override
  public boolean buyStockForPortfolio(String portfolioName,
                                      SimpleEntry<String, SimpleEntry<String, Double>> newStock) {
    try {
      portfolioMap.get(portfolioName).addStock(newStock);
      return false;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  @Override
  public boolean sellStockFromPortfolio(String portfolioName,
                                        SimpleEntry<String, SimpleEntry<String, Double>> newStock) {
    try {
      portfolioMap.get(portfolioName).sellStock(newStock);
      return false;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }
}
