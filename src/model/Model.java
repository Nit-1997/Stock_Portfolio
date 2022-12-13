package model;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

/**
 * Interface that represents the model for the stock program. Holds portfolios.
 */
public interface Model {

  /**
   * Get the portfolio names for this session.
   *
   * @return Map of portfolio names to flexible boolean.
   */
  Map<String, Boolean> getPortfolios();

  // -------------- Portfolios ------------------

  /**
   * Make a new inflexible portfolio.
   *
   * @param name    Name for the portfolio.
   * @param tickers Map of tickers to number to add.
   * @throws IllegalArgumentException If a portfolio with that name already exists.
   */
  void newInflexiblePortfolio(String name, Map<String, Integer> tickers)
      throws IllegalArgumentException;

  /**
   * Create a new flexible portfolio.
   *
   * @param name Name for the portfolio.
   * @throws IllegalArgumentException If there is a portfolio with that name already.
   */
  void newFlexiblePortfolio(String name) throws IllegalArgumentException;

  /**
   * Get if the portfolio allows sales and buying of stocks.
   *
   * @param portfolioName Name of portfolio.
   * @return If the portfolio is flexible.
   * @throws IllegalArgumentException If the portfolio is not found.
   */
  boolean isFlexible(String portfolioName) throws IllegalArgumentException;

  /**
   * Add a stock to a portfolio.
   *
   * @param portfolioName Name of portfolio to add the stock to.
   * @param ticker        Ticker of the stock.
   * @param numStocks     Number of stocks to add.
   * @param date          Date to buy the stocks on. Will default to most recent date if not
   *                      flexible.
   * @param commission    Commission fee for this transaction.
   * @throws IllegalArgumentException Ticker not found, or portfolio not found, or invalid date, or
   *                                  inflexible portfolio, or negative commission/numstocks.
   */
  void addStock(String portfolioName, String ticker, int numStocks, LocalDate date,
      double commission) throws IllegalArgumentException;

  /**
   * Sell a given number of stocks.
   *
   * @param portfolioName Name of portfolio.
   * @param ticker        Ticker of stock to sell.
   * @param numStocks     Number of stocks to sell.
   * @param date          Date to sell the stocks on.
   * @param commission    Commission fee for this transaction.
   * @throws IllegalArgumentException Portfolio not found, or not enough stocks to sell, or invalid
   *                                  date, or not allowed (inflexible), or negative
   *                                  commission/numstocks.
   */
  void sellStock(String portfolioName, String ticker, int numStocks, LocalDate date,
      double commission) throws IllegalArgumentException;

  /**
   * Create a new fixed amount strategy, invest a fixed amount in multiple companies on a specific
   * date, splitting the total investment amount by percentages.
   *
   * @param portfolioName Name of portfolio to add this strategy to.
   * @param percentages   String tickers to Double percentage of the amount to invest in this
   *                      ticker's stock.
   * @param date          Date to buy this stock on.
   * @param amount        Amount to buy in total.
   * @param commission    Commission to be used for every transaction.
   * @throws IllegalArgumentException Portfolio not found or inflexible, negative/zero amount of
   *                                  negative commission, percentages do not sum to 100 (or contain
   *                                  a negative value, zeros will be added as transactions but
   *                                  ultimately ignored. Same applies to dollar cost averaging).
   */
  void newFixedAmountStrategy(String portfolioName, Map<String, Double> percentages,
      LocalDate date, double amount, double commission)
      throws IllegalArgumentException;

  /**
   * Create a new dollar cost average investment strategy. Specify an amount to be invested in a set
   * of stocks in a portfolio at every interval over a set period of time.
   *
   * @param portfolioName Name of the portfolio to add this investment strategy to. If there is no
   *                      portfolio with that name, a new one will be created with this strategy.
   * @param percentages   Map of string ticker to Double percentage to invest in that stock.
   *                      Percentages MUST sum to 100 or an IllegalArgumentException will be
   *                      thrown.
   * @param start         Start date for this strategy.
   * @param end           End date for this strategy (Optional, see below copy for overloaded
   *                      function).
   * @param days          Interval to invest on, ex invest this amount every 30 days.
   * @param amount        Amount to be invested every interval.
   * @param commission    Commission amount to be used for every transaction.
   * @throws IllegalArgumentException Percentages do not sum to 100, or invalid ticker is found in
   *                                  the map, or commission, percentages, or days is negative.
   */
  void newDollarCostAverageStrategy(String portfolioName, Map<String, Double> percentages,
      LocalDate start, LocalDate end, int days, double amount,
      double commission) throws
      IllegalArgumentException;

  /**
   * Create a new dollar cost average investment strategy. Specify an amount to be invested in a set
   * of stocks in a portfolio at every interval over a set period of time.
   *
   * @param portfolioName Name of the portfolio to add this investment strategy to. If there is no
   *                      portfolio with that name, a new one will be created with this strategy.
   * @param percentages   Map of string ticker to Double percentage to invest in that stock.
   *                      Percentages MUST sum to 100 or an IllegalArgumentException will be
   *                      thrown.
   * @param start         Start date for this strategy.
   * @param days          Interval to invest on, ex invest this amount every 30 days.
   * @param amount        Amount to be invested every interval.
   * @param commission    Commission amount to be used for every transaction.
   * @throws IllegalArgumentException Percentages do not sum to 100, or invalid ticker is found in
   *                                  the map, or commission, percentages, or days is negative.
   */
  void newDollarCostAverageStrategy(String portfolioName, Map<String, Double> percentages,
      LocalDate start, int days, double amount, double commission)
      throws IllegalArgumentException;

  /**
   * Get the map of tickers to stocks owned.
   *
   * @param portfolioName Name of portfolio
   * @param date          Date to get the ticker map for.
   * @return Map of tickers to number of stocks owned.
   * @throws IllegalArgumentException If the portfolio is not found, or that date is invalid. For
   *                                  inflexible portfolios, only today will be accepted, and values
   *                                  for date with most recent data will be returned.
   */
  Map<String, Double> getTickerMap(String portfolioName, LocalDate date)
      throws IllegalArgumentException;

  /**
   * Get the closing values of the stocks at a specific date.
   *
   * @param portfolioName Name of the portfolio.
   * @param date          Date to get values for.
   * @return Map of tickers to closing values.
   * @throws IllegalArgumentException If we don't have data for this date, or the portfolio is not
   *                                  found.
   */
  Map<String, Double> getValues(String portfolioName, LocalDate date)
      throws IllegalArgumentException;

  /**
   * Get the overall value for the portfolio.
   *
   * @param portfolioName Name of the portfolio.
   * @param date          Date to evaluate at.
   * @return Value of the portfolio.
   * @throws IllegalArgumentException If we don't have data for this date, or the portfolio is not
   *                                  found.
   */
  double getPortValue(String portfolioName, LocalDate date) throws IllegalArgumentException;

  /**
   * Get the cost basis for a portfolio for a given date. Only valid for flexible portfolios.
   *
   * @param portfolioName Name of portfolio.
   * @param date          Date for valuation.
   * @return Cost basis of portfolio.
   * @throws IllegalArgumentException Portfolio not found, or date not valid.
   */
  double getCostBasis(String portfolioName, LocalDate date) throws IllegalArgumentException;

  /**
   * Get the value of the portfolio over an interval.
   *
   * @param portfolioName Name of the portfolio.
   * @param start         Starting date.
   * @param end           Ending date.
   * @return Map of dates to values for that timespan.
   * @throws IllegalArgumentException If there is no portfolio, or date is invalid, or start is
   *                                  after end.
   */
  Map<String, Double> getPerformance(String portfolioName, LocalDate start, LocalDate end)
      throws IllegalArgumentException;

  /**
   * Get the portfolio's string representation.
   *
   * @param portfolioName Portfolio name.
   * @return String representation of portfolio.
   * @throws IllegalArgumentException Portfolio not found.
   */
  String toString(String portfolioName) throws IllegalArgumentException;

  // ---------------- Other ------------------------

  /**
   * Get whether a ticker is found in the API or not.
   *
   * @param ticker Ticker to find.
   * @return If ticker is found.
   */
  boolean validTicker(String ticker);

  /**
   * Get the API to use for the data collection.
   *
   * @return API.
   * @deprecated Adding stock automatically calls API for data.
   */
  @Deprecated
  StockAPI getAPI();

  /**
   * Return the fileIO object for the model.
   *
   * @return IO files.
   */
  FileHandler getFileHandler();

  /**
   * Returns the set of stocks in the portfolio on a given date.
   *
   * @param portfolioName name of the portfolio.
   * @param date          Date for reBalancing.
   * @return list of stock names.
   * @throws IllegalArgumentException if the date is weekend or in the future.
   */
  Set<String> getStocksOnDate(String portfolioName, LocalDate date) throws IllegalArgumentException;

  /**
   * rebalances the portfolio on a give date.
   *
   * @param stockMap      Map of ticker symbol vs the percentage
   * @param portfolioName name of the portfolio.
   * @param date          date for reBalance.
   * @throws Exception if there is any error.
   */
  void reBalance(Map<String, Double> stockMap, String portfolioName, LocalDate date)
      throws Exception;
}
