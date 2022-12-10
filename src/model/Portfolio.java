package model;

import java.time.LocalDate;
import java.util.Map;

/**
 * Interface that represents a portfolio.
 */
public interface Portfolio {
  /**
   * Get the portfolio name.
   *
   * @return String portfolio name.
   */
  String getPortName();

  /**
   * Get the map of tickers to number of stocks owned at a date.
   *
   * @param date Date to get the ticker map for.
   * @return Map of tickers to stocks.
   */
  Map<String, Double> getTickerMap(LocalDate date);

  /**
   * Get the number of shares for a stock.
   *
   * @param ticker Ticker to look for.
   * @param date   Date to get the shares for.
   * @return Number of shares for that stock.
   * @throws IllegalArgumentException If the date is invalid. For inflexible portfolios, this
   *                                  must be today, and will return most recent data.
   */
  Double getShares(String ticker, LocalDate date) throws IllegalArgumentException;

  /**
   * Get the number of different stocks.
   *
   * @return Number of tickers in portfolio.
   */
  int getNumStocks();

  /**
   * Get the closing values of the stocks at a specific date.
   *
   * @param date Date to get values for.
   * @return Map of tickers to closing values.
   * @throws IllegalArgumentException If we don't have data for this date.
   */
  Map<String, Double> getValues(LocalDate date) throws IllegalArgumentException;

  /**
   * Get the cost basis for the portfolio. For inflexible, will be same as value.
   *
   * @param date Date to get cost basis at.
   * @return Cost basis at the date.
   * @throws IllegalArgumentException If the date is invalid for inflexible portfolios.
   */
  double getCostBasis(LocalDate date) throws IllegalArgumentException;

  /**
   * Get the overall value for the portfolio.
   *
   * @param date Date to evaluate at.
   * @return Value of the portfolio.
   * @throws IllegalArgumentException If we don't have data for this date.
   */
  double getPortValue(LocalDate date) throws IllegalArgumentException;

  /**
   * Get the string representation of this portfolio.
   *
   * @return String of portfolio. For JSON.
   */
  @Override
  String toString();

  /**
   * Is this portfolio flexible. You tell me.
   *
   * @return Flexible or not.
   */
  boolean isFlexible();
}
