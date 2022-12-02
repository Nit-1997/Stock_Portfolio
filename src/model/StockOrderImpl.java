package model;

import java.io.IOException;

/**
 * Implementation for the stock orders. This implementation uses Stock interface, to implement stock
 * objects.
 */
final public class StockOrderImpl implements StockOrder {

  private final Stock stock;
  private final double quantity;

  private final Double commFee;

  /**
   * Creates a StockOrderImpl object using ticker and qty.
   *
   * @param ticker ticker symbol of the stock
   * @param qty    quantity of the stock
   * @throws IOException can occur while loading/ reading data dump
   */
  public StockOrderImpl(String ticker, double qty) throws IOException {
    this.stock = new StockImpl(ticker);
    this.quantity = qty;
    this.commFee = null;
  }

  /**
   * Creates a StockOrderImpl object using ticker, qty, date and commission fee.
   *
   * @param ticker ticker symbol of the stock
   * @param qty    quantity of the stock
   * @param date date for stock.
   * @param commFee commission fee.
   * @throws IOException can occur while loading/ reading data dump
   */
  public StockOrderImpl(String ticker, Double qty, String date, Double commFee) throws IOException {
    this.stock = new StockImpl(ticker, date);
    this.quantity = qty;
    this.commFee = commFee;
  }

  /**
   * Creates a StockOrderImpl object using ticker,buyPrice, date, qty and commission Fee.
   *
   * @param ticker ticker symbol of the stock
   * @param qty    quantity of the stock
   * @param buyPrice price of stock on that date.
   * @param date date of stock.
   * @param commFee commission fee.
   */
  public StockOrderImpl(String ticker, Double buyPrice, String date, Double qty, Double commFee) {
    this.stock = new StockImpl(ticker, buyPrice, date);
    this.quantity = qty;
    this.commFee = commFee;
  }

  /**
   * Creates a StockOrderImpl object using ticker,buyPrice,date and qty. This is used when we are
   * fetching already existing portfolios
   *
   * @param ticker   ticker symbol of the stock
   * @param buyPrice price at date of purchase
   * @param date     date of creation.
   * @param qty      quantity of the stock
   */
  public StockOrderImpl(String ticker, double buyPrice, String date, double qty) {
    this.stock = new StockImpl(ticker, buyPrice, date);
    this.quantity = qty;
    this.commFee = null;
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

  @Override
  public Double getCommFee() {
    return this.commFee;
  }


}
