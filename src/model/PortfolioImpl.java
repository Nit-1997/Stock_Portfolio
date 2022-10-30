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
    this.stockOrder =  Utils.loadPortfolioData(name);
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
    return this.stockOrder;
  }

  @Override
  public List<PortfolioDetailedPojo> getCurrentPortfolioDetailed() throws IOException {
    List<PortfolioDetailedPojo> parsedResponse = new ArrayList<>();
    for(StockOrder order : this.stockOrder){
      String ticker = order.getStock().getStockTickerName();
      double buyPrice = order.getStock().getBuyPrice();
      double qty = order.getQuantity();
      double currentPrice = order.getStock().getCurrentPrice();
      double pnl = order.getStock().getPnL();
      parsedResponse.add(new PortfolioDetailedPojo(ticker,buyPrice,qty,currentPrice,pnl));
    }
    return parsedResponse;
  }

  @Override
  public List<PortfolioDetailedPojo> getPortfolioDetailedOnDate(String date) throws IOException {
    List<PortfolioDetailedPojo> parsedResponse = new ArrayList<>();
    for(StockOrder order : this.stockOrder){
      String ticker = order.getStock().getStockTickerName();
      double buyPrice = order.getStock().getBuyPrice();
      double qty = order.getQuantity();
      double priceOnDate = order.getStock().getPriceOnDate(date);
      double pnlOnDate = order.getStock().getPnLByDate(date);
      parsedResponse.add(new PortfolioDetailedPojo(ticker,buyPrice,qty,priceOnDate,pnlOnDate));
    }
    return parsedResponse;
  }


  @Override
  public void deletePortfolio(){

  }


}
