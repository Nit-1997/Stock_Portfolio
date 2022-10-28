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

  @Override
  public Double getCurrentPrice() {
    // TODO need to keep a check, if data once fetched, dont fetch again
    try {
      String res = ApiDataFetcher.fetchData(this.ticker);
      return Double.parseDouble(res);
    } catch (Exception e) {
      return -1.0;
    }
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
}
