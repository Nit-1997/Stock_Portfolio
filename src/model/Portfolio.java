package model;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Interface to specify Portfolio specs.
 */
public interface Portfolio {

  /**
   * Fetches the PnL at portfolio level.
   *
   * @return Portfolio PnL
   */
  double getPortfolioPnL() throws IOException;

  /**
   * Fetches from local file and provides summary of the portfolio {ticker name, quantity} .
   *
   * @return List of stocks and their quantities in a portfolio.
   */
  List<StockOrder> getPortfolioSummary();


  /**
   * Fetches from local file and provides current detailed portfolio data
   * {(ticker_symbol, quantity) vs (buying price, currentPrice)} .
   *
   * @return list of maps containing ticker symbol, quantity vs buying price
   */
  List<PortfolioDetailedPojo> getCurrentPortfolioDetailed() throws IOException;

  /**
   * Fetches from local file and provides detailed portfolio data for a particular date
   * {(ticker_symbol, quantity) vs (buying price, price on that day)} .
   *
   * @param date for which details need to be fetched.
   *
   * @return list of maps containing ticker symbol, quantity vs buying price
   */
  List<PortfolioDetailedPojo> getPortfolioDetailedOnDate(String date) throws IOException;

  /**
   * Gets the current Price of the entire Portfolio.
   *
   * @return currentPrice of Portfolio
   */
  public double getCurrentValue();

  /**
   * Fetches the initial value of the Portfolio.
   *
   * @return total Initial Buy value.
   */
  public double getInitialValue();


  /**
   * Fetches the value of the Portfolio for a particular date.
   *
   * @param date for which portfolio value needs to be fetched.
   *
   * @return total Initial Buy value.
   */
  public double getValueOnDate(String date);

  /**
   * This function deletes the portfolio if the user terminates the session before the portfolio gets saved.
   */
  void deletePortfolio();
}
