package model;

import java.io.IOException;
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
  boolean addPortfolio(String name, Map<String, Double> stocks) throws Exception;

  /**
   * Returns List of Portfolios
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
  Map<String, Double> getPortfolioSummary(String name);

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
  Map<String, List<Double>> getPortfolioDetailed(String name, String date);

  /**
   * Fetches value of a portfolio.
   *
   * @param name name of the portfolio
   * @param date date for which portfolio value is required
   * @return total value of portfolio
   */
  double getPortfolioValue(String name, String date) throws Exception;
  /**
   * Fetches total PnL of a portfolio.
   *
   * @param name name of the portfolio
   * @param date date for which PnL is required.
   * @return total PnL
   */
  double getPortfolioPnL(String name, String date) throws IOException;

  /**
   * Checks whether the name of portfolio already exists in the names of portfolios.
   * @param name the name of portfolio that needs to be checked.
   * @return true/false based on whether the name already exists or not.
   */
  boolean isUniqueName(String name);

  /**
   * Checks if the user has pressed control C to exit the program while waiting for add confirmation.
   */
  boolean ctrlCPressedChecker(String name);

  /**
   * Checks whether the date entered by user is in correct format.
   * @param dateStr the string entered by user
   * @return check on the string
   */
  boolean dateChecker(String dateStr);

}
