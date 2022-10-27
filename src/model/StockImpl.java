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
    this.buyPrice = this.getCurrentPrice();
  }

  //TODO : Handle Saturdays and Sundays
  @Override
  public Double getCurrentPrice() {
    //TODO use this.ticker to fetch data from api
    try{
      String res = ApiDataFetcher.fetchData(this.ticker);
      return Double.parseDouble(res);
    }catch (IOException e){
      return -1.0;
    }
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
