package model;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import Utils.Utils;

final class StockImpl implements Stock {

  private final String ticker;
  private final double buyPrice;
  private final String buyDate;

  public StockImpl(String ticker, double buyPrice, String date) {
    this.ticker = ticker;
    this.buyPrice = buyPrice;
    this.buyDate = date;
  }

  /**
   * Constructor that creates a Stock object.
   *
   * @param ticker ticker name
   */
  public StockImpl(String ticker) throws IOException {
    this.ticker = ticker;
    this.buyPrice = this.getCurrentPrice();
    this.buyDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
  }

  @Override
  public Double getCurrentPrice() throws IOException {
    String res = Utils.fetchCurrentStockValue(this.ticker);
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
