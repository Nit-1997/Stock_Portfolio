package model;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

public interface UserFlex extends User{

  /**
   * Adds a given Portfolio.
   *
   * @param name   name of portfolio
   * @param stocksMap map of {tickerSymbol , map of {date, quantity}}
   * @return true if added , false otherwise
   */
  boolean addPortfolio(String name, Map<String, Map<String, Double>> stocksMap);

  /**
   * Fetches the portfolio summary for a given portfolio.
   *
   * @param name name of the portfolio
   * @param date date for which portfolio summary is required.
   * @return {ticker_symbl , qty}
   */
  Map<String, Double> getPortfolioSummary(String name, String date);

  /**
   * Returns portfolio creation date.
   * @param name name of the portfolio
   * @return portfolio creation date
   */
  String getPortfolioCreationDate(String name);

  /**
   * Gets the latest state for each stock in the portfolio.
   * @param name name of the portfolio
   * @return Map of {Ticker Symbol, Latest Date, Quantity} for each stock.
   */
  Map<String, SimpleEntry<String, Double> > getPortfolioState(String name);

  /**
   * This function checks if the firstDate is before secondDate or not.
   * @param firstDate first date
   * @param secondDate second date
   * @return if firstDate is before secondDate, true otherwise false.
   */
  boolean isBeforeDate(String firstDate, String secondDate);

  /**
   * Buys a stock for a portfolio on a given date.
   * @param portfolioName name of the portfolio
   * @param newStock stock that needs to be added {ticker symbol, date, quantity}
   * @return true if successfully added.
   */
  boolean buyStockForPortfolio(String portfolioName, SimpleEntry<String,SimpleEntry<String,Double>> newStock);

  /**
   * Sells a stock from a portfolio on a given date.
   * @param portfolioName name of the portfolio
   * @param newStock stock that needs to be sold {ticker symbol, date, quantity}
   * @return true if successfully sold.
   */
  boolean sellStockFromPortfolio(String portfolioName, SimpleEntry<String,SimpleEntry<String,Double>> newStock);

  /**
   * Determine total money invested in portfolio (all purchases + total transactions*commission fee) till the given date.
   * @param date date for which getBasis required.
   * @return Total Money Invested.
   */
  Double getCostBasis(String portfolioName, String date);

}
