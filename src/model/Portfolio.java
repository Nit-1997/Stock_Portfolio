package model;

import java.io.IOException;

/**
 * Interface to specify Portfolio specs.
 */
public interface Portfolio {

  /**
   * Fetches the PnL at portfolio level.
   *
   * @return Portfolio PnL
   */
  public double getPortfolioPnL() throws IOException;

  /**
   * Fetches basic portfolio data for a given name from local file.
   *
   * @param name name of the portfolio
   */
  public void getPortfolioSummary(String name);


  /**
   * Fetches complete portfolio data for a given name from local file.
   *
   * @param name name of the portfolio
   */
  public void getPortfolioDetailed(String name);

  /**
   * Gets the current Price of the entire Portfolio.
   *
   * @return currentPrice of Portfolio
   */
  public Double getCurrentPrice();

  /**
   * Fetches the initial value of the Portfolio.
   *
   * @return total Initial Buy value.
   */
  public Double getInitialValue();
}
