package model;

/**
 * Interface for the API to get stock data.
 */
public interface StockAPI {

  /**
   * Get the data for a stock.
   *
   * @param ticker String ticker for the stock.
   * @return 2D array, each array represents one line of data read in from API.
   * @throws IllegalArgumentException If ticker not found, will throw.
   */
  String[][] getStock(String ticker) throws IllegalArgumentException;
}
