package model;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;

/**
 * Interface for flexible portfolio.
 */
public interface PortfolioFlex extends Portfolio {

  /**
   * Returns a new list of stock order that contains stock symbol and the total quantity till the
   * date.
   *
   * @param date date for which summary is asked.
   * @return List of StockOrder.
   */
  Map<String, Double> getPortfolioSummary(String date) throws Exception;

  /**
   * Gets the creation date of the portfolio.
   *
   * @return portfolio creation date.
   */
  String getCreationDate();

  /**
   * Returns a new list of stock order that contains stock symbol, the latest transaction date and
   * the total quantity for each stock.
   *
   * @return List of StockOrder.
   */
  Map<String, SimpleEntry<String, Double>> getLatestState() throws Exception;

  /**
   * Add a new stock transaction to the portfolio.
   *
   * @param newEntry Stock transaction {Ticker Symbol, Transaction Date, Quantity} to be added
   */
  void addTransaction(
      SimpleEntry<String, SimpleEntry<String, SimpleEntry<Double, Double>>> newEntry)
      throws Exception;


  /**
   * Fetches the cost basis till a given date.
   *
   * @param date date till which you want cost basis
   * @return cost basis
   */
  double getCostBasis(String date) throws Exception;


  /**
   * Gives Portfolio value snapshots in range (date1,date2).
   *
   * @param date1 starting date
   * @param date2 ending date
   * @return List of portfolio values
   * @throws Exception can occur while reading/writing data dump
   */
  SimpleEntry<List<String>, List<Double>> getPerfDataOverTime(String date1, String date2)
      throws Exception;
}
