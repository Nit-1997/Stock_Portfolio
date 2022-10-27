package model;

import java.util.HashMap;
import java.util.List;
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

  List<int[]> detailedViewOfPortfolio(String name);

  Double profitOrLossOnPortfolio(String name);

}
