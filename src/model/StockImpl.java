package model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

final class StockImpl implements Stock {
  private final String ticker;
  private final Double buyPrice;

  /**
   * Constructor that creates a Stock object.
   *
   * @param ticker ticker name
   */
  public StockImpl(String ticker) {
    this.ticker = ticker;
    this.buyPrice = getCurrentPrice();
  }

  //TODO : Handle Saturdays and Sundays
  @Override
  public Double getCurrentPrice() {
    //TODO use this.ticker to fetch data from api
    Map<String,String> res = ApiDataFetcher.fetchData("Googl");
    System.out.println(res.get("date"));
    System.out.println(res.get("price"));
    return -1.1;
  }

  @Override
  public Double getPnL() {
    return null;
  }

  @Override
  public Double getBuyPrice() {
    return buyPrice;
  }
}
