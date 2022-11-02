package model;

import java.io.IOException;

/**
 * Interface to encapsulate Stock spec.
 */
public interface Stock {

  /**
   * Fetch the current price of the ticker name.
   *
   * @return Stock Price Use the close value from api response
   */
  Double getCurrentPrice() throws IOException;

  /**
   * Returns the PNL of this stock at currentDate.
   *
   * @return currPrice - initPrice
   */
  Double getPnL() throws IOException;

  /**
   * Returns the PNL of this stock at given Date.
   *
   * @return priceOnDate - initPrice
   */
  Double getPnLByDate(String date) throws IOException;

  /**
   * Get the price on date of purchase.
   *
   * @return Init Stock price
   */
  double getBuyPrice();

  /**
   * Get stock price on a certain date.
   *
   * @param date date on which we need the price
   * @return stock price on that day
   */
  Double getPriceOnDate(String date) throws IOException;

  /**
   * Fetches the ticker name.
   *
   * @return ticker name
   */
  String getStockTickerName();

  /**
   * Gets the buy date for this stock.
   *
   * @return price at the date of purchase
   */
  String getBuyDate();
}
