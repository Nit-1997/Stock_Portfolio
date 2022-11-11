package model;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.Utils;

public class PortfolioFlexImpl implements PortfolioFlex{

  private final String name;
  private final List<StockOrder> stockOrders;
  private String creationDate;

  // TODO : create a new constructor in stockOrderImpl that takes ticker symbol ,date and quantity
  // TODO : create a new constructor in stockImpl that takes ticker symbol and date, and compute price based on that date

  public PortfolioFlexImpl(Map<String, Map<String, Double>> stocksMap, String name) throws Exception{
    // creator constructor
    if (stocksMap == null || name == null) {
      throw new IllegalArgumentException("Null arguments to portfolio constructor");
    }
    this.stockOrders = new ArrayList<>();
    this.name = name;
    for (String key : stocksMap.keySet()) {
      if (!Utils.dataExists(key, "stock_data")) {
        Utils.loadStockData(key, "stock_data");
      }

//      fill stockOrder List  using new constructor
//      this.stockOrder.add(new StockOrderImpl(key, stocksMap.get(key)));
      // TODO : while traversing, find the smallest date and fill the creation date parameter
    }
    this.creationDate = null;

    Utils.saveToFile(this.name, this.stockOrders, "portfolios");

  }


  public PortfolioFlexImpl(String portfolioName) throws Exception {
    // retriever constructor
    this.stockOrders = Utils.loadPortfolioData(portfolioName, "portfolios"+ File.separator+"flex");
    this.name = portfolioName;
    if (this.stockOrders == null) {
      return;
    }
    for (StockOrder s : this.stockOrders) {
      if (!Utils.dataExists(s.getStock().getStockTickerName().toUpperCase(), "stock_data")) {
        Utils.loadStockData(s.getStock().getStockTickerName().toUpperCase(), "stock_data");
      }
    }
    // TODO : while traversing, find the smallest date and fill the creation date parameter
    this.creationDate = null;
  }

  @Override
  public String getCreationDate(){
    return this.creationDate;
  }

  @Override
  public List<StockOrder> getLatestState() {
    // TODO : find ticker symbol vs latest_transaction_date vs quantity
    //TODO : create another stockOrderList and for each stock, add the quantities till the given date, find the max date for each stock
    // TODO : add record for each stock

    return null;
  }

  @Override
  public void addStock(SimpleEntry<String, SimpleEntry<String, Double>> newEntry) {
      // TODO : add this line in stock order list
    // TODO : save this portfolio in local
  }

  @Override
  public void sellStock(SimpleEntry<String, SimpleEntry<String, Double>> newEntry) {
    // TODO : add this line in stock order list
    // TODO : save this portfolio in local
  }


  @Override
  public Double getCurrentValue() throws IOException {
    // TODO : 1. find today's date
    // TODO : 2. call getValueOnDate for that
    return null;
  }

  @Override
  public Double getValueOnDate(String date) throws IOException {
    // TODO : 1. compute value (quantity*price) for each stock till the given date
    // TODO : 2. add all the values
    return null;
  }

  @Override
  public List<StockOrder> getPortfolioSummary(String date) {
    // TODO : find ticker symbol vs quantity on that date

    //TODO : create another stockOrderList and for each stock, add the quantities till the given date
    // TODO : add record for each stock
    return null;
  }
}
