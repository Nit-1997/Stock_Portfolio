package model;

import java.io.IOException;
import java.util.List;

/**
 * Interface to specify Portfolio specs.
 * spec includes the name ,{stock , qty} map while creating the portfolio,
 * or just name while fetching already created portfolio
 */
public interface Portfolio {

  /**
   * Fetches the PnL at portfolio level.
   *
   * @return Portfolio PnL
   * @throws IOException if data not found.
   */
  Double getPortfolioPnL() throws IOException;

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
   * @throws IOException if data not found.
   */
  List<PortfolioDetailedPojo> getCurrentPortfolioDetailed() throws IOException;

  /**
   * Fetches from local file and provides detailed portfolio data for a particular date
   * {(ticker_symbol, quantity) vs (buying price, price on that day)} .
   *
   * @param date for which details need to be fetched.
   * @return list of maps containing ticker symbol, quantity vs buying price
   * @throws IOException if data for date not found or date is invalid.
   */
  List<PortfolioDetailedPojo> getPortfolioDetailedOnDate(String date) throws Exception;

  /**
   * Gets the current Price of the entire Portfolio.
   *
   * @return currentPrice of Portfolio
   * @throws IOException if asked date is invalid or no data for this date.
   */
  public Double getCurrentValue() throws IOException;

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
   * @return total Initial Buy value.
   * @throws IOException if there is no value on this date or date is invalid.
   */
  public Double getValueOnDate(String date) throws IOException;

}
