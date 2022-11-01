package model;

import java.io.Console;
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
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import Utils.Utils;
import constants.Constants;


/**
 * This class creates the Portfolio. Portfolio consists of stocks and it's quantity.
 */
final public class PortfolioImpl implements Portfolio {

  private final String name;
  private final List<StockOrder> stockOrder;


  public PortfolioImpl(String name) throws IOException {
    this.stockOrder = Utils.loadPortfolioData(name, "portfolios");
    this.name = name;
    if (this.stockOrder == null) {
      return;
    }
    for (StockOrder s : this.stockOrder) {
      if (!Utils.dataExists(s.getStock().getStockTickerName().toUpperCase(), "stock_data")) {
        Utils.loadStockData(s.getStock().getStockTickerName().toUpperCase(), "stock_data");
      }
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
      if (!Utils.dataExists(key, "stock_data")) {
        Utils.loadStockData(key, "stock_data");
      }
      this.stockOrder.add(new StockOrderImpl(key, stocksMap.get(key)));
    }
    Utils.saveToFile(this.name, this.stockOrder, "portfolios");
  }


  @Override
  public Double getCurrentValue() throws IOException {
    if (this.stockOrder == null) {
      return null;
    }
    Double val = 0.0;
    for (StockOrder order : this.stockOrder) {
      if (order.getCurrentOrderValue() == null) {
        return null;
      }
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
  public Double getValueOnDate(String date) throws IOException {
    if (this.stockOrder == null) {
      return null;
    }
    Double val = 0.0;
    for (StockOrder order : this.stockOrder) {
      if (order.getOrderValueOnDate(date) == null) {
        return null;
      }
      val += order.getOrderValueOnDate(date);
    }
    return val;
  }


  @Override
  public Double getPortfolioPnL() throws IOException {
    double val = 0.0;
    for (StockOrder order : this.stockOrder) {
      if (order.getOrderPnL() == null) {
        return null;
      }
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
    if (this.stockOrder == null) {
      return null;
    }
    List<PortfolioDetailedPojo> parsedResponse = new ArrayList<>();
    for (StockOrder order : this.stockOrder) {
      String ticker = order.getStock().getStockTickerName();
      double buyPrice = order.getStock().getBuyPrice();
      double qty = order.getQuantity();
      Double currentPrice = order.getStock().getCurrentPrice();
      if (currentPrice == null) {
        return null;
      }
      Double pnl = order.getStock().getPnL();
      if (pnl == null) {
        return null;
      }
      parsedResponse.add(new PortfolioDetailedPojo(ticker, buyPrice, qty, currentPrice, pnl));
    }
    return parsedResponse;
  }

  @Override
  public List<PortfolioDetailedPojo> getPortfolioDetailedOnDate(String date) throws IOException {
    if (this.stockOrder == null) {
      System.out.println("stock order is null");
      return null;
    }
    List<PortfolioDetailedPojo> parsedResponse = new ArrayList<>();
    for (StockOrder order : this.stockOrder) {
      String ticker = order.getStock().getStockTickerName();
      double buyPrice = order.getStock().getBuyPrice();
      double qty = order.getQuantity();
      Double priceOnDate = order.getStock().getPriceOnDate(date);
      if (priceOnDate == null) {
        return null;
      }
      Double pnlOnDate = order.getStock().getPnLByDate(date);
      if (pnlOnDate == null) {
        return null;
      }
      parsedResponse.add(new PortfolioDetailedPojo(ticker, buyPrice, qty, priceOnDate, pnlOnDate));
    }
    return parsedResponse;
  }


}
