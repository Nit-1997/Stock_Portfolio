package model;

import java.util.List;
import java.util.Map;

/**
 * Inflexible interface for user.
 */
public interface UserInflex extends User {

  /**
   * Adds a given Portfolio.
   *
   * @param name   name of portfolio
   * @param stocks map of {tickerSym , qty}
   * @return true if added , false otherwise
   */
  boolean addPortfolio(String name, Map<String, Double> stocks);

  /**
   * Fetches total PnL of a portfolio.
   *
   * @param name name of the portfolio
   * @param date date for which PnL is required.
   * @return total PnL
   */
  Double getPortfolioPnL(String name, String date);

  /**
   * Fetches the portfolio summary for a given portfolio.
   *
   * @param name name of the portfolio
   * @return {ticker_symbl , qty}
   */
  Map<String, Double> getPortfolioSummary(String name);

  /**
   * Fetches detailed portfolio data. eg response : [ "aapl" : [ 20 , 15 ,10 , 50], "googl" : [ 20 ,
   * 15 ,10 , 50] ]
   *
   * @param name name of the portfolio
   * @param date date for which portfolio details are required.
   * @return Detailed Portfolio.
   */
  Map<String, List<Double>> getPortfolioDetailed(String name, String date);

}
