package model;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Interface to encapsulate user spec.
 */

public interface User {



  /**
   * Returns List of Portfolios.
   *
   * @return list of portfolis
   */
  public Set<String> getPortfolios();



  /**
   * Fetches value of a portfolio.
   *
   * @param name name of the portfolio
   * @param date date for which portfolio value is required
   * @return total value of portfolio
   */
  public Double getPortfolioValue(String name, String date);

  /**
   * Fetches total PnL of a portfolio.
   *
   * @param name name of the portfolio
   * @param date date for which PnL is required.
   * @return total PnL
   */
  public Double getPortfolioPnL(String name, String date);

  /**
   * Checks whether the name of portfolio already exists in the names of portfolios.
   *
   * @param name the name of portfolio that needs to be checked.
   * @return true/false based on whether the name already exists or not.
   */
  public boolean isUniqueName(String name);


  /**
   * Checks whether the date entered by user is in correct format.
   *
   * @param dateStr the string entered by user
   * @return check on the string
   */
  public boolean dateChecker(String dateStr);


  /**
   * Returns a list of stocks that ara available for user to create portfolio.
   *
   * @return A list of stock names.
   */
  public Set<String> getStockList();

  /**
   * Returns whether the stock name entered by user is a valid stock name or not.
   *
   * @param name stock name entered by user.
   * @return if the stock name is valid or not.
   */
  public boolean isValidStock(String name);

  /**
   * cleans stock directory on program termination.
   */
  public void cleanStockDirectory();

}
