package model;

import java.io.IOException;

public class StockOrderImpl implements StockOrder {
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


  @Override
  public double getCurrentOrderValue() {
    return this.stock.getCurrentPrice() * this.quantity;
  }

  @Override
  public double getInitialOrderValue() {
    return this.stock.getBuyPrice() * this.quantity;
  }

  @Override
  public double getOrderValueOnDate(String date) {
    return this.stock.getPriceOnDate(date) * this.quantity;
  }

  @Override
  public double getOrderPnL() throws IOException {
    return this.stock.getPnL() * this.quantity;
  }

  @Override
  public double getOrderValueByDate(String date) {
    return this.stock.getPriceOnDate(date) * this.quantity;
  }

  @Override
  public Stock getStock() {
    return this.stock;
  }

  @Override
  public double getQuantity() {
    return this.quantity;
  }
}
