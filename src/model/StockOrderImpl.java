package model;

import java.io.IOException;

public class StockOrderImpl implements StockOrder {

  private final Stock stock;
  private final double quantity;

  public StockOrderImpl(String ticker, double qty) throws IOException {
    this.stock = new StockImpl(ticker);
    this.quantity = qty;
  }

  public StockOrderImpl(String ticker, double buyPrice, String date, double qty) {
    this.stock = new StockImpl(ticker, buyPrice, date);
    this.quantity = qty;
  }


  @Override
  public Double getCurrentOrderValue() throws IOException {
    if (this.stock.getCurrentPrice() == null) {
      return null;
    }
    return this.stock.getCurrentPrice() * this.quantity;
  }

  @Override
  public Double getInitialOrderValue() {
    return this.stock.getBuyPrice() * this.quantity;
  }

  @Override
  public Double getOrderValueOnDate(String date) throws IOException {
    if (this.stock.getPriceOnDate(date) == null) {
      return null;
    }
    return this.stock.getPriceOnDate(date) * this.quantity;
  }

  @Override
  public Double getOrderPnL() throws IOException {
    if (this.stock.getPnL() == null) {
      return null;
    }
    return this.stock.getPnL() * this.quantity;
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
