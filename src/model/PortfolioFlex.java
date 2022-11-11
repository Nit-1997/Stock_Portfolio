package model;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;

public interface PortfolioFlex extends Portfolio{

  /**
   * Returns a new list of stock order that contains stock symbol and the total quantity till the date.
   * @param date date for which summary is asked.
   * @return List of StockOrder.
   */
  Map<String , Double> getPortfolioSummary(String date) throws Exception;

  /**
   * Gets the creation date of the portfolio.
   * @return portfolio creation date.
   */
  String getCreationDate();

  /**
   * Returns a new list of stock order that contains stock symbol, latest transaction date and the
   * total quantity for each stock.
   * @return List of StockOrder.
   */
  Map<String , SimpleEntry<String , Double>> getLatestState() throws Exception;

  /**
   * Add a new stock addition to the portfolio.
   * @param newEntry Stock transaction {Ticker Symbol, Transaction Date, Quantity} to be added
   */
  void addStock(SimpleEntry<String, SimpleEntry<String, Double>> newEntry) throws Exception;

  /**
   * Add a new stock sell to the portfolio.
   * @param newEntry Stock transaction {Ticker Symbol, Transaction Date, Quantity} to be added
   */
  void sellStock(SimpleEntry<String, SimpleEntry<String, Double>> newEntry) throws Exception;

}
