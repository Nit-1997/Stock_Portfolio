package model;

import java.io.IOException;
import java.util.List;

public interface PortfolioInflex extends Portfolio{


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
   * Fetches the initial value of the Portfolio.
   *
   * @return total Initial Buy value.
   */
  public double getInitialValue();
}
