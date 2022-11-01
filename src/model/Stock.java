package model;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Interface to encapsulate Stock spec.
 */
public interface Stock {

  /**
   * Fetch the current price of the ticker name.
   *
   * @return Stock Price
   * Use the close value from api response
   */
  public Double getCurrentPrice() throws IOException;

  /**
   * Returns the PNL of this stock at currentDate
   *
   * @return currPrice - initPrice
   */
  public Double getPnL() throws IOException;

  /**
   * Returns the PNL of this stock at given Date
   *
   * @return priceOnDate - initPrice
   */
  public Double getPnLByDate(String date) throws IOException;

  /**
   * Get the price on date of purchase
   *
   * @return Init Stock price
   */
  public double getBuyPrice();

  /**
   * Get stock price on a certain date.
   *
   * @param date date on which we need the price
   * @return stock price on that day
   */
  public Double getPriceOnDate(String date) throws IOException;

  /**
   * Fetches the ticker name
   *
   * @return ticker name
   */
  public String getStockTickerName();

  /**
   * Gets the buy date for this stock.
   *
   * @return price at the date of purchase
   */
  String getBuyDate();
}
