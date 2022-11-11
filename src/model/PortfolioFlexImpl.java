package model;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import constants.Constants;
import utils.Utils;

public class PortfolioFlexImpl implements PortfolioFlex {

  private final String name;
  private final List<StockOrder> stockOrders;
  private String creationDate;

  /**
   * Creator constructor.
   *
   * @param stocksMap { ticker : { date : qty} }
   * @param name      name of the portfolio
   * @throws Exception while reading/writing data dump
   */
  public PortfolioFlexImpl(Map<String, Map<String, Double>> stocksMap, String name) throws Exception {
    if (stocksMap == null || name == null) {
      throw new IllegalArgumentException("Null arguments to portfolio constructor");
    }
    this.stockOrders = new ArrayList<>();
    this.name = name;

    String portfolioCreationDate = null;

    for (String key : stocksMap.keySet()) {
      if (!Utils.dataExists(key, "stock_data")) {
        Utils.loadStockData(key, "stock_data");
      }
      for (String k : stocksMap.get(key).keySet()) {
        if (portfolioCreationDate == null) {
          portfolioCreationDate = k;
        } else {
          int comparison = Utils.compareDates(portfolioCreationDate, k);
          if (comparison > 0) {
            portfolioCreationDate = k;
          }
        }
        this.stockOrders.add(new StockOrderImpl(key, stocksMap.get(key).get(k), k));
      }
    }
    this.creationDate = portfolioCreationDate;

    Utils.saveToFile(this.name, this.stockOrders, "portfolios" + File.separator + "flex");

  }

  /**
   * Retreiver Constructor.
   *
   * @param portfolioName name of the portfolio
   * @throws Exception while reading/writing data dump
   */
  public PortfolioFlexImpl(String portfolioName) throws Exception {
    this.stockOrders = Utils.loadPortfolioData(portfolioName, "portfolios" + File.separator + "flex");
    this.name = portfolioName;
    if (this.stockOrders == null) {
      return;
    }
    String portfolioCreationDate = null;
    for (StockOrder s : this.stockOrders) {
      if (!Utils.dataExists(s.getStock().getStockTickerName().toUpperCase(), "stock_data")) {
        Utils.loadStockData(s.getStock().getStockTickerName().toUpperCase(), "stock_data");
      }
      if (portfolioCreationDate == null) {
        portfolioCreationDate = s.getStock().getBuyDate();
      } else {
        int comparison = Utils.compareDates(portfolioCreationDate, s.getStock().getBuyDate());
        if (comparison > 0) {
          portfolioCreationDate = s.getStock().getBuyDate();
        }
      }
    }
    this.creationDate = portfolioCreationDate;
  }

  @Override
  public String getCreationDate() {
    return this.creationDate;
  }

  @Override
  public Map<String, SimpleEntry<String, Double>> getLatestState() throws Exception {

    Map<String, SimpleEntry<String, Double>> stateMap = new HashMap<>();

    for (StockOrder s : this.stockOrders) {
      String tickerName = s.getStock().getStockTickerName();
      String date = s.getStock().getBuyDate();
      double qty = s.getQuantity();
      if (stateMap.containsKey(tickerName)) {
        String lastTransactionDate = stateMap.get(tickerName).getKey();
        double newQty = qty + stateMap.get(tickerName).getValue();
        int comparison = Utils.compareDates(lastTransactionDate, date);
        if (comparison < 0) {
          lastTransactionDate = date;
        }
        SimpleEntry<String, Double> updatedEntry = new SimpleEntry<>(lastTransactionDate, newQty);
        stateMap.put(tickerName, updatedEntry);
      } else {
        stateMap.put(tickerName, new SimpleEntry<>(date, qty));
      }
    }

    return stateMap;
  }

  @Override
  public void addStock(SimpleEntry<String, SimpleEntry<String, Double>> newEntry) throws Exception {
    if (!Utils.dataExists(newEntry.getKey().toUpperCase(), "stock_data")) {
      Utils.loadStockData(newEntry.getKey().toUpperCase(), "stock_data");
    }
    StockOrder newOrder = new StockOrderImpl(newEntry.getKey(), newEntry.getValue().getValue(), newEntry.getValue().getKey());
    this.stockOrders.add(newOrder);
    Utils.saveToFile(this.name, this.stockOrders, "portfolios" + File.separator + "flex");
  }

  @Override
  public void sellStock(SimpleEntry<String, SimpleEntry<String, Double>> newEntry) throws Exception {
    if (!Utils.dataExists(newEntry.getKey().toUpperCase(), "stock_data")) {
      Utils.loadStockData(newEntry.getKey().toUpperCase(), "stock_data");
    }
    StockOrder newOrder = new StockOrderImpl(newEntry.getKey(), newEntry.getValue().getValue(), newEntry.getValue().getKey());
    this.stockOrders.add(newOrder);
    Utils.saveToFile(this.name, this.stockOrders, "portfolios" + File.separator + "flex");
  }


  @Override
  public Double getCurrentValue() throws Exception {
    String currentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
    return getValueOnDate(currentDate);
  }

  @Override
  public Double getValueOnDate(String date) throws Exception {
    double totalVal = 0;
    for (StockOrder s : this.stockOrders) {
      int comparison = Utils.compareDates(s.getStock().getBuyDate(), date);
      if (comparison <= 0) {
        totalVal += s.getQuantity() * s.getStock().getBuyPrice();
      }
    }
    return totalVal;
  }

  @Override
  public Map<String, Double> getPortfolioSummary(String date) throws Exception {
    Map<String, Double> stateMap = new HashMap<>();

    for (StockOrder s : this.stockOrders) {
      String tickerName = s.getStock().getStockTickerName();
      String currentDate = s.getStock().getBuyDate();
      int comparison = Utils.compareDates(currentDate, date);
      if (comparison > 0) {
        continue;
      }
      double qty = s.getQuantity();
      if (stateMap.containsKey(tickerName)) {
        double newQty = qty + stateMap.get(tickerName);
        stateMap.put(tickerName, newQty);
      } else {
        stateMap.put(tickerName, qty);
      }
    }

    return stateMap;
  }

  @Override
  public double getCostBasis(String date) throws Exception {
    double buyTransVal = 0;
    double totalTrans = 0;
    for (StockOrder s : this.stockOrders) {
      String currentDate = s.getStock().getBuyDate();
      int comparison = Utils.compareDates(currentDate, date);
      if (comparison > 0) {
        continue;
      }
      if (s.getQuantity() > 0) {
        buyTransVal += s.getQuantity() * s.getStock().getBuyPrice();
      }
      totalTrans++;
    }
    return buyTransVal + (totalTrans * Constants.COMMISSION_FEE);
  }
}
