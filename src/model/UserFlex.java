package model;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;

/**
 * Interface for all the new functions required for flexible portfolio.
 */
public interface UserFlex extends User {

  /**
   * Adds a given Portfolio.
   *
   * @param name      name of portfolio
   * @param stocksMap map of {tickerSymbol , map of {date, quantity, commFee}}
   * @return true if added , false otherwise
   * @throws Exception if the date is not valid.
   */
  boolean addPortfolio(String name, Map<String, Map<String, SimpleEntry<Double, Double>>> stocksMap)
      throws Exception;

  /**
   * Fetches the portfolio summary for a given portfolio.
   *
   * @param name name of the portfolio
   * @param date date for which portfolio summary is required.
   * @return {ticker_symbol , qty}
   * @throws Exception  if there is any error.
   */
  Map<String, Double> getPortfolioSummary(String name, String date) throws Exception;

  /**
   * Returns portfolio creation date.
   *
   * @param name name of the portfolio
   * @return portfolio creation date
   * @throws Exception  if there is any error.
   *
   */
  String getPortfolioCreationDate(String name) throws Exception;

  /**
   * Gets the latest state for each stock in the portfolio.
   *
   * @param name name of the portfolio
   * @return Map of {Ticker Symbol, Latest Date, Quantity} for each stock.
   */
  Map<String, SimpleEntry<String, Double>> getPortfolioState(String name);

  /**
   * This function checks if the firstDate is before secondDate or not.
   *
   * @param firstDate  first date
   * @param secondDate second date
   * @return if firstDate is before secondDate, true otherwise false.
   */
  boolean isBeforeDate(String firstDate, String secondDate);

  /**
   * Transaction of a stock for a portfolio on a given date.
   *
   * @param portfolioName name of the portfolio
   * @param newStock      stock that needs to be added {ticker symbol, date, quantity, commission
   *                      fee}
   * @return true if successfully added.
   * @throws Exception is input is not valid.
   */
  boolean transactionForPortfolio(String portfolioName,
      SimpleEntry<String, SimpleEntry<String, SimpleEntry<Double, Double>>> newStock)
      throws Exception;


  /**
   * Determine total money invested in portfolio (all purchases + total transactions*commission fee)
   * till the given date.
   *
   * @param date          date for which getBasis required.
   * @param portfolioName name of the portfolio.
   * @return Total Money Invested.
   * @throws Exception  if there is any error.
   */
  Double getCostBasis(String portfolioName, String date) throws Exception;

  /**
   * Checks the starting and ending dates for the performance graph.
   *
   * @param date1         starting date.
   * @param date2         ending date.
   * @param portfolioName name of the portfolio.
   * @return true if the range is valid else false.
   */
  boolean graphDateChecker(String date1, String date2, String portfolioName);

  /**
   * Returns the data for plotting the performance graph.
   *
   * @param date1         starting date for graph.
   * @param date2         ending date for graph.
   * @param portfolioName name of the portfolio for which graph to be printed.
   * @return {Labels List, DataPoints List, Scale, baseAmount}
   */
  SimpleEntry<SimpleEntry<List<String>, List<Integer>>, SimpleEntry<Integer, Double>> getGraphData(
      String date1, String date2, String portfolioName);
}
