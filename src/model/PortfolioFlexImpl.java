package model;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Utils;

public class PortfolioFlexImpl implements PortfolioFlex {

  private final String name;
  private final List<StockOrder> stockOrders;
  private String creationDate;

  /**
   * Creator constructor.
   *
   * @param stocksMap { ticker : { date : qty, commission fee} }
   * @param name      name of the portfolio
   * @throws Exception while reading/writing data dump
   */
  public PortfolioFlexImpl(Map<String, Map<String, SimpleEntry<Double, Double>>> stocksMap, String name) throws Exception {
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
        this.stockOrders.add(new StockOrderImpl(key, stocksMap.get(key).get(k).getKey(), k, stocksMap.get(key).get(k).getValue()));
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
    if (portfolioName == null) {
      throw new IllegalArgumentException("Null arguments to portfolio constructor");
    }
    List<StockOrder> tempStockOrders = Utils.loadPortfolioData(portfolioName, "portfolios" + File.separator + "flex");
    if (!Utils.FlexPortfolioValidator(tempStockOrders)) this.stockOrders = null;
    else this.stockOrders = tempStockOrders;
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
  public void addTransaction(SimpleEntry<String, SimpleEntry<String, SimpleEntry<Double, Double>>> newEntry) throws Exception {
    if(newEntry == null){
      throw new IllegalArgumentException("Null Args passed");
    }
    if (!Utils.dataExists(newEntry.getKey().toUpperCase(), "stock_data")) {
      Utils.loadStockData(newEntry.getKey().toUpperCase(), "stock_data");
    }
    if(!Utils.dateChecker(newEntry.getValue().getKey())){
      throw new IllegalArgumentException("Incorrect Date format");
    }
    if(newEntry.getValue().getValue().getValue() < 0){
      throw new IllegalArgumentException("Commission Fee cannot be negative");
    }
    StockOrder newOrder = new StockOrderImpl(newEntry.getKey(), newEntry.getValue().getValue().getKey(), newEntry.getValue().getKey(), newEntry.getValue().getValue().getValue());
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
    Map<String, Double> summary = this.getPortfolioSummary(date);
    for (String ticker : summary.keySet()) {
      totalVal += Double.parseDouble(Utils.fetchStockValueByDate(ticker, date, "stock_data")) * summary.get(ticker);
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
    if(date == null){
      throw new IllegalArgumentException("Null dates passed");
    }
    if(!Utils.dateChecker(date)){
      throw new IllegalArgumentException("Incorrect Date format");
    }
    double totalTrans = 0;
    for (StockOrder s : this.stockOrders) {
      String currentDate = s.getStock().getBuyDate();
      int comparison = Utils.compareDates(currentDate, date);
      if (comparison > 0) {
        continue;
      }
      if (s.getQuantity() > 0) {
        totalTrans += s.getQuantity() * s.getStock().getBuyPrice();
      }
      totalTrans += s.getCommFee();

    }
    return totalTrans;
  }

  @Override
  public SimpleEntry<List<String>, List<Double>> getPerfDataOverTime(String date1, String date2) throws Exception {
    if (!Utils.datesValidationForGraph(date1, date2,this.getCreationDate())) return null;
    long dayDiff = Utils.computeDaysBetweenDates(date1, date2);
    String type = "";
    if (dayDiff <= 30) {
      type = "daily";
    } else if (dayDiff > 30 && dayDiff <= 210) {
      type = "weekly";
      date1 = Utils.shiftDateToValidStartPoint(type, date1);
    } else if (dayDiff > 210 && dayDiff <= 900) {
      type = "monthly";
      date1 = Utils.shiftDateToValidStartPoint(type, date1);
    } else if (dayDiff > 900 && dayDiff < 1461) {
      type = "quarterly";
      date1 = Utils.shiftDateToValidStartPoint(type, date1);
    } else {
      type = "yearly";
      date1 = Utils.shiftDateToValidStartPoint(type, date1);
    }
    return Utils.getScaledPerfData(date1, date2, type , this);
  }

}
