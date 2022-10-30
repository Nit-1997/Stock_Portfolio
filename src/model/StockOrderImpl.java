package model;

import java.io.IOException;

public class StockOrderImpl {
  private final Stock stock;
  private final double quantity;

  public StockOrderImpl(String ticker, double qty) {
    this.stock = new StockImpl(ticker);
    this.quantity = qty;
  }

  public StockOrderImpl(String ticker, double buyPrice, String date, double qty) {
    this.stock = new StockImpl(ticker, buyPrice, date);
    this.quantity = qty;
  }


  public double getCurrentOrderValue() {
    return this.stock.getCurrentPrice() * this.quantity;
  }

  public double getInitialOrderValue() {
    return this.stock.getBuyPrice() * this.quantity;
  }

  public double getOrderValueOnDate(String date) {
    return this.stock.getPriceOnDate(date) * this.quantity;
  }

  public double getOrderPnL() throws IOException {
    return this.stock.getPnL() * this.quantity;
  }

  public double getOrderValueByDate(String date) {
    return this.stock.getPriceOnDate(date) * this.quantity;
  }


  public Stock getStock() {
    return this.stock;
  }

  public double getQuantity() {
    return this.quantity;
  }
}
