package model;

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
  public Double getCurrentPrice();

  /**
   * Returns the PNL of this stock at currentDate
   *
   * @return currPrice - initPrice
   */
  public Double getPnL();

  /**
   * Get the price on date of purchase
   *
   * @return Init Stock price
   */
  public Double getBuyPrice();
}
