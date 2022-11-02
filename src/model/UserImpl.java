package model;

import constants.Constants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import utils.Utils;


/**
 * Implementation of User and it's functionality.
 */
public class UserImpl implements User {

  private final Map<String, Portfolio> portfolioMap;

  /**
   * Constructor to initialize User Object with portfolios.
   */
  public UserImpl() {
    Utils.clearStockDirectory();
    try {
      Constants.stockNames = Utils.loadStockNames("stocks", "stocks_list.csv");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    String portfolioDirectory = Paths.get("portfolios").toAbsolutePath().toString();
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
      if (portfolioMap.get(name) == null) {
        portfolioMap.put(name, new PortfolioImpl(name));
      }
      List<StockOrder> list = portfolioMap.get(name).getPortfolioSummary();
      if (list == null) {
        return null;
      }
      Map<String, Double> resMap = new HashMap<>();
      for (StockOrder soi : list) {
        resMap.put(soi.getStock().getStockTickerName(), soi.getQuantity());
      }
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
  public Map<String, List<Double>> getPortfolioDetailed(String name, String date) {
    try {
      if (!Utils.dateChecker(date)) {
        throw new Exception("wrong arguments");
      }
      if (portfolioMap.get(name) == null) {
        portfolioMap.put(name, new PortfolioImpl(name));
      }
      Map<String, List<Double>> resMap = new HashMap<>();
      String currentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
      List<PortfolioDetailedPojo> res = new ArrayList<>();
      if (date.equals(currentDate)) {
        res = portfolioMap.get(name).getCurrentPortfolioDetailed();
      } else if (!date.equals(currentDate)) {
        res = portfolioMap.get(name).getPortfolioDetailedOnDate(date);
      }
      if (res == null) {
        return null;
      }
      for (PortfolioDetailedPojo pojo : res) {
        List<Double> listVals = new ArrayList<>();
        String ticker_symbol = pojo.getTicker();
        listVals.add(pojo.getQty());
        listVals.add(pojo.getBuyPrice());
        listVals.add(pojo.getAskedPrice());
        listVals.add(pojo.getPnl());
        resMap.put(ticker_symbol, listVals);
      }
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
  public Double getPortfolioValue(String name, String date) {
    try {
      if (!Utils.dateChecker(date)) {
        throw new Exception("wrong arguments");
      }
      if (portfolioMap.get(name) == null) {
        portfolioMap.put(name, new PortfolioImpl(name));
      }
      Double portfolioValue;
      String currentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
      if (date.equals(currentDate)) {
        portfolioValue = portfolioMap.get(name).getCurrentValue();
      } else {
        portfolioValue = portfolioMap.get(name).getValueOnDate(date);
      }
      return portfolioValue;
    } catch (IOException e) {
      return null;
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public Double getPortfolioPnL(String name, String date) {
    try {
      if (!Utils.dateChecker(date)) {
        throw new Exception("wrong arguments");
      }
      if (portfolioMap.get(name) == null) {
        portfolioMap.put(name, new PortfolioImpl(name));
      }
      double portfolioPnL = 0;
      String currentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
      if (date.equals(currentDate)) {
        portfolioPnL = portfolioMap.get(name).getPortfolioPnL();
      } else {
        portfolioPnL =
            portfolioMap.get(name).getValueOnDate(date) - portfolioMap.get(name).getInitialValue();
      }
      return portfolioPnL;
    } catch (IOException e) {
      return null;
    } catch (Exception e){
      return null;
    }

  }

  @Override
  public boolean isUniqueName(String name) {
    return !this.portfolioMap.containsKey(name);
  }


  @Override
  public void cleanStockDirectory() {
    Utils.clearStockDirectory();
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
