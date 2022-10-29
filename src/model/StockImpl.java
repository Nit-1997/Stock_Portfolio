package model;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

final class StockImpl implements Stock {
  private final String ticker;
  private final Double buyPrice;
  private final String buyDate;

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
  public Double getCurrentPrice() {
    // TODO need to keep a check, if data once fetched, dont fetch again
    try {
      String res = ApiDataFetcher.fetchCurrentValueApi(this.ticker);
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
  public Double getPnL() throws IOException {
    double currPrice = this.getCurrentPrice();
    if (currPrice == -1.0) {
      throw new IOException("Could not fetch data for the ticker");
    } else {
      return currPrice - this.buyPrice;
    }
  }


  @Override
  public Double getBuyPrice() {
    return this.buyPrice;
  }

  @Override
  public double getPriceOnDate(String date) {
    try {
      Map<String , List<String>> res = ApiDataFetcher.fetchDataDailyHistoricByTicker(this.ticker);
      //TODO : validate the date
      return Double.parseDouble(res.get(date).get(1));
    } catch (Exception e) {
      return -1.0;
    }
  }
}
