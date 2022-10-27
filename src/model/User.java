package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Interface to encapsulate user spec.
 */

public interface User {

  /**
   * Adds a given Portfolio.
   *
   * @param name   name of portfolio
   * @param stocks map of {tickerSym , qty}
   * @return true if added , false otherwise
   */
  boolean addPortfolio(String name, HashMap<String, Double> stocks) throws Exception;

  /**
   * Fetch List of Portfolios
   *
   * @return list of portfolis
   */
  Set<String> getPortfolios();

  /**
   * Fetches the portfolio summary for a given portfolio.
   *
   * @param name name of the portfolio
   * @return {ticker_symbl , qty}
   */
  HashMap<String, Double> getPortfolioSummary(String name);

  /**
   * Fetches detailed portfolio data.
   * eg response : [
   * "aapl" : [ 20 , 15 ,10 , 50],
   * "googl" : [ 20 , 15 ,10 , 50]
   * ]
   *
   * @param name name of the portfolio
   * @return Detailed Portfolio.
   */
  Map<String, List<Double>> getPortfolioDetailed(String name);

  /**
   * Fetches total PnL of a portfolio.
   *
   * @param name name of the portfolio
   * @return total PnL
   */
  Double getPortfolioPnL(String name);

}
