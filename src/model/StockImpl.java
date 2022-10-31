package model;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import Utils.Utils;

final class StockImpl implements Stock {
  private final String ticker;
  private final double buyPrice;
  private final String buyDate;

  public StockImpl(String ticker , double buyPrice, String date){
    this.ticker = ticker;
    this.buyPrice = buyPrice;
    this.buyDate =  date;
  }

  /**
   * Constructor that creates a Stock object.
   *
   * @param ticker ticker name
   */
  public StockImpl(String ticker) {
    this.ticker = ticker;
    this.buyPrice = this.getCurrentPrice();
    this.buyDate =  DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
  }

  @Override
  public double getCurrentPrice() {
    try {
      String res = Utils.fetchCurrentStockValue(this.ticker);
      return Double.parseDouble(res);
    } catch (Exception e) {
      return -1.0;
    }
  }

  @Override
  public String getStockTickerName() {
    return this.ticker;
  }

  @Override
  public double getPnL() throws IOException {
    double currPrice = this.getCurrentPrice();
    if (currPrice == -1.0) {
      throw new IOException("Could not fetch data for the ticker");
    } else {
      return currPrice - this.buyPrice;
    }
  }

  @Override
  public double getPnLByDate(String date) throws IOException {
    double priceOnGivenDate = this.getPriceOnDate(date);
    if (priceOnGivenDate == -1.0) {
      throw new IOException("Could not fetch data for the ticker");
    } else {
      return priceOnGivenDate - this.buyPrice;
    }
  }


  @Override
  public double getBuyPrice() {
    return this.buyPrice;
  }

  @Override
  public double getPriceOnDate(String date) {
    try {
      Map<String , List<String>> res = Utils.fetchStockValueByDate(this.ticker, date);
      return Double.parseDouble(res.get(date).get(1));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return -1;
    }
  }

  @Override
  public String getBuyDate() {
    return this.buyDate;
  }
}
