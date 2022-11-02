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
   * @throws IOException if current date is invalid
   */
  Double getCurrentPrice() throws IOException;

  /**
   * Returns the PNL of this stock at currentDate.
   *
   * @return currPrice - initPrice
   * @throws IOException if the current date is invalid or data not found or file not found.
   */
  Double getPnL() throws IOException;

  /**
   * Returns the PNL of this stock at given Date.
   *
   * @param date date for which data needs to be loaded.
   * @return priceOnDate - initPrice
   * @throws IOException if the date not valid or data for this date not found.
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
   * @throws IOException if the date not valid or data for this date not found.
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
