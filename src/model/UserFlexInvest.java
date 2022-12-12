package model;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;

/**
 * Interface for DCA Strategy flexible portfolios.
 */
public interface UserFlexInvest extends UserFlex {

  /**
   * Investing a certain amount in the portfolio with the specified percentage of each stock in the
   * portfolio.
   *
   * @param portfolioName name of the portfolio.
   * @param amount        amount to be invested in the portfolio.
   * @param percentage    map of {stock,percentage} to be purchased from the amount.
   * @param date          date on which investment done.
   * @param commFee       commission fee for this investment.
   * @throws Exception if there is any error.
   */
  void investMoney(String portfolioName, Double amount, Map<String, Double> percentage,
                   String date, Double commFee) throws Exception;

  /**
   * Create a portfolio using dollar-cost averaging.
   *
   * @param portfolioName name of the portfolio.
   * @param amount        amount to be invested in the portfolio.
   * @param weightage     Map of stock symbol vs its percentage.
   * @param startDate     start date of investment.
   * @param endDate       end date of investment.
   * @param interval      interval of investment.
   * @param commFee       commission fee for each investment.
   * @throws Exception if there is any error.
   */
  void addPortfolio(String portfolioName, Double amount, Map<String, Double> weightage,
                    String startDate, String endDate, int interval, Double commFee)
          throws Exception;

  /**
   * Applying DCA strategy on existing portfolio.
   *
   * @param portfolioName name of the portfolio.
   * @param amount        amount to be invested in the portfolio.
   * @param weightage     Map of stock symbol vs its percentage.
   * @param startDate     start date of investment.
   * @param endDate       end date of investment.
   * @param interval      interval of investment.
   * @param commFee       commission fee for each investment.
   * @throws Exception is there is any error.
   */
  void investThroughDCA(String portfolioName, Double amount, Map<String, Double> weightage,
                        String startDate, String endDate,
                        int interval, Double commFee) throws Exception;

  /**
   * Returns the data for plotting the performance graph in GUI.
   *
   * @param date1         starting date for graph.
   * @param date2         ending date for graph.
   * @param portfolioName name of the portfolio for which graph to be printed.
   * @return {Labels List, DataPoints List}
   * @throws Exception if there is any error.
   */
  SimpleEntry<List<String>, List<Double>> getGraphDataGUI(
          String date1, String date2, String portfolioName) throws Exception;

}
