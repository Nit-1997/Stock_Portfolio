package control.terminal;

import java.time.LocalDate;


/**
 * Interface for the controller of the stock program. Defines the methods available for use with the
 * controller.
 */
public interface StockController {

  /**
   * Starts the controller.
   */
  void gogogadget();

  /**
   * Loads the file data each time a new portfolio is created.
   */
  void loadFileData();

  /**
   * Method that gathers the stocks and shares and date for a new flexible portfolio.
   */
  void newFlexiblePort();

  /**
   * Gather flexible date.
   *
   * @param ticker the ticker that is needed when the date is obtained
   * @return the local date object
   */
  LocalDate gatherFlexDate(String ticker);

  /**
   * Creates a new portfolio object.
   */
  void newPortfolio();

  /**
   * Method that returns the number of shares and prompts the user to enter how many they would like
   * to buy.
   *
   * @return returns an int number of shares for the new stock
   */
  int gatherShares();

  /**
   * Method that gathers all the information regarding the portfolio and sends it to the stock
   * viewer.
   */
  void viewPortfolio();


  /**
   * Gathers the date that the user enters and returns a local date object.
   *
   * @param portName User passes the portfolio name
   * @return returns a date that the user enters
   */
  LocalDate gatherDate(String portName);


  /**
   * Views the performance and loads the performance graph between two dates.
   */
  void viewPerformance();

  /**
   * Gets the user information for buying a stock for a flexible portfolio.
   */
  void buyStock();

  /**
   * Gets the user information for selling a stock for a flexible portfolio.
   */
  void sellStock();

  /**
   * Prompts the user to type in a commission.
   *
   * @return a double that holds the commission value
   */
  double getCommission();

  /**
   * ReBalances the portfolio.
   */
  void reBalance();

}
