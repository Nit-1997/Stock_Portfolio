package control.gui;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Map;
import java.util.Set;
import view.gui.IView;

/**
 * Outlines the methods that become the actions listeners for the interactions in the view.
 */
public interface Features {


  /**
   * Gathers information from the view (the portfolio name)
   * to then send to the model to create a new flexible portfolio.
   *
   * @param portfolioName A string containing the portfolio Name
   */
  void newFlexiblePort(String portfolioName);

  void setView(IView v);

  /**
   * Prompts the view to get information for a new portfolio.
   *
   * @return the Portfolio Name that the user typed in to the view
   */
  String newFlexiblePortInit();

  /**
   * Gathers information from the view to then call the get performance
   * method within the model to create the performance.
   *
   * @param portfolioName Portfolio Name for which performance.
   * @param startDate     Start Date for the performance graph.
   * @param endDate       End date for the performance graph
   */
  void getPerformance(String portfolioName, String startDate, String endDate);

  /**
   * Checks if the string is a local date or not.
   *
   * @param date takes in a date string for checking.
   * @return a boolean indicating whether it is a valid local date.
   */
  boolean checkDate(String date);

  /**
   * Checks if the shares are a valid integer.
   *
   * @param shares takes in shares from the view class.
   * @return a boolean indicating whether if it is a valid amount of shares.
   */
  boolean checkShares(String shares);

  /**
   * Checks the commission value if it is a valid double.
   *
   * @param commission Commission that is taken in from the view
   * @return a boolean indicating whether it is a valid commission or not
   */
  boolean checkCommission(String commission);

  /**
   * Takes in all the information from the view class and sends all the information to the model
   * for a new strategy.
   *
   * @param portfolioName Portfolio Name from the view class
   * @param date          date from the view class
   * @param percent       percent of ticker symbol that the user wants to buy
   * @param ticker        ticker pertaining to each percent that the user would like to buy
   * @param commission    commission from the view
   * @param shares        The dollar amount to buy over the amount of time
   */
  void newFixedStrategy(String portfolioName, String date, ArrayList<String> percent,
                        ArrayList<String> ticker, String commission, String shares);

  /**
   * Takes in all the info from the view class to create a new dollar cost strategy.
   *
   * @param portfolioName Portfolio Name from the view class
   * @param date          date from the view class
   * @param interval      interval of time in days that the user would like to buy the stocks
   * @param percent       percent of ticker symbol that the user wants to buy
   * @param ticker        ticker pertaining to each percent that the user would like to buy
   * @param commission    commission from the view
   * @param shares        $ Amount of what the user would like to buy
   * @param endDate       End date of the interval time period
   */
  void newDollarCostStrategy(String portfolioName, String date, String interval,
                             ArrayList<String> percent, ArrayList<String> ticker,
                             String commission, String shares, String endDate);

  /**
   * Takes in a file name and is able to gather all file data and send it to the model.
   *
   * @param fileIn        file path of the file we would like to load
   * @param portfolioName Name of the portfolio
   */
  void loadFileData(String fileIn, String portfolioName);

  /**
   * Gives the portfolio Name to the view.
   *
   * @param text Portfolio Name
   */
  void relayPortName(String text);

  /**
   * Views the portfolio based on the view.
   *
   * @param portChoice gathered from the view as to which choice
   * @param date       gathered from the view as to which date the user wants to view.
   */
  void viewPortfolio(String portChoice, String date);

  /**
   * Buys the stocks based on the views input.
   *
   * @param portfolioName portfolio name gathered from the view to specify which portfolio to buy.
   * @param ticker        Ticker from the view to buy.
   * @param numStocks     Number of shares or stocks user would like to buy.
   * @param date          date from the view that the user would like to buy on.
   * @param commission    commission fee for the transaction
   */
  void buyStock(String portfolioName, String ticker, String numStocks, String date,
                String commission);

  /**
   * Sells the stock given the information from the view.
   *
   * @param portfolioName portfolio name gathered from the view to specify which portfolio to sell.
   * @param ticker        Ticker from the view to sell.
   * @param numStocks     Number of shares or stocks user would like to sell.
   * @param date          date from the view that the user would like to sell on.
   * @param commission    commission fee for the transaction
   */
  void sellStock(String portfolioName, String ticker, String numStocks, String date,
                 String commission);

  /**
   * Returns the set of stocks in the portfolio on a given date.
   *
   * @param portfolioName name of the portfolio.
   * @param date Date for rebalancing.
   * @return list of stock names.
   */
  Set<String> getStockNamesForReBalancing(String portfolioName, LocalDate date);

  /**
   *
   * reBalances the portfolio on a give date.
   * @param stockMap Map of ticker symbol vs the percentage
   * @param portfolioName name of the portfolio.
   * @param date date for reBalance.
   * @return confirmation message.
   */
  String reBalance(Map<String, Double> stockMap, String portfolioName, LocalDate date);
}
