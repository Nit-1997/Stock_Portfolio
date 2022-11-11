package model;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import utils.Utils;

/**
 * Implementation of the Stock interface. Used to store the data for each stock
 */
final class StockImpl implements Stock {

  private final String ticker;
  private final double buyPrice;
  private final String buyDate;

  /**
   * Constructor to create object of stock. This is used while fetching the data for already created
   * stock
   *
   * @param ticker   ticker symbol of the stock
   * @param buyPrice buy price of the stock
   * @param date     date of purchase
   */
  public StockImpl(String ticker, double buyPrice, String date) {
    this.ticker = ticker;
    this.buyPrice = buyPrice;
    this.buyDate = date;
  }

  public StockImpl(String ticker , String date) throws IOException {
    if(ticker == null || date == null){
      throw new IOException("Arguments cannot be Null");
    }
    this.ticker = ticker;
    this.buyDate = date;
    this.buyPrice = this.getPriceOnDate(date);
  }

  /**
   * Constructor to create object of stock. This is used when creating the portfolio for the first
   * time
   *
   * @param ticker ticker symbol for the given stock
   * @throws IOException can occur while reading/loading data dump
   */
  public StockImpl(String ticker) throws IOException {
    this.ticker = ticker;
    this.buyPrice = this.getCurrentPrice();
    this.buyDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
  }

  @Override
  public Double getCurrentPrice() throws IOException {
    String res = Utils.fetchCurrentStockValue(this.ticker, "stock_data");
    if (res == null) {
      return null;
    }
    return Double.parseDouble(res);
  }

  @Override
  public String getStockTickerName() {
    return this.ticker;
  }

  @Override
  public Double getPnL() throws IOException {
    Double currPrice = this.getCurrentPrice();
    if (currPrice == null) {
      return null;
    }
    return currPrice - this.buyPrice;
  }

  @Override
  public Double getPnLByDate(String date) throws IOException {
    Double priceOnGivenDate = this.getPriceOnDate(date);
    if (priceOnGivenDate == null) {
      return null;
    }
    return priceOnGivenDate - this.buyPrice;
  }


  @Override
  public double getBuyPrice() {
    return this.buyPrice;
  }

  @Override
  public Double getPriceOnDate(String date) throws IOException {
    if (date == null) {
      throw new IllegalArgumentException("passed null args");
    }
    String res = Utils.fetchStockValueByDate(this.ticker, date, "stock_data");
    if (res == null) {
      return null;
    }
    return Double.parseDouble(res);
  }

  @Override
  public String getBuyDate() {
    return this.buyDate;
  }
}
