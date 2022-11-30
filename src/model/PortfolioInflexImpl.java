package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.Utils;


/**
 * This class implements the Portfolio. Portfolio consists of name, list of stock orders, i.e
 * {stock, quantity}
 */
final class PortfolioInflexImpl implements PortfolioInflex {

  private final String name;
  private final List<StockOrder> stockOrder;

  /**
   * Constructor to create Portfolio object. This is used when fetching already created portfolio
   *
   * @param name name of the portfolio
   * @throws Exception can occur while reading/loading data dump
   */
  public PortfolioInflexImpl(String name) throws Exception {
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
   * Constructor to create Portfolio object. This is used when creating a new Portfolio
   *
   * @param stocksMap map of {ticker , qty}
   * @param name      name of the portfolio
   * @throws Exception can occur while reading/loading data dump
   */
  public PortfolioInflexImpl(Map<String, Double> stocksMap, String name) throws Exception {
    DataSource ds = new DataSourceImpl();
    if (stocksMap == null || name == null) {
      throw new IllegalArgumentException("Null arguments to portfolio constructor");
    }
    this.stockOrder = new ArrayList<>();
    this.name = name;
    for (String key : stocksMap.keySet()) {
      if (!Utils.dataExists(key, "stock_data")) {
        Utils.loadStockData(key, "stock_data");
      }
      this.stockOrder.add(new StockOrderImpl(key, stocksMap.get(key)));
    }
    ds.saveToFile(this.name, this.stockOrder, "portfolios");
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
  public List<PortfolioDetailedPojo> getPortfolioDetailedOnDate(String date) throws Exception {
    if (date == null) {
      throw new IllegalArgumentException("Passed null args");
    }
    if (this.stockOrder == null) {
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
