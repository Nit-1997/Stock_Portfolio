package view.terminal;

/**
 * View interface for the stock project.
 */
public interface StockView {

  /**
   * Clear the screen and reset the cursor.
   */
  void clearScreen();

  /**
   * Show the main menu.
   */
  void showMainMenu();

  /**
   * Display the given command at the bottom of the screen.
   *
   * @param command Command to display.
   */
  void showCommand(String command);

  /**
   * Show a portfolio's value on a given date.
   *
   * @param portfolioName Name of portfolio.
   * @param totalValue    Value of portfolio.
   * @param date          Date that this value is accurate for.
   */
  void showPortfolio(String portfolioName, double totalValue, String date);

  /**
   * Loading screen to stall while API gets data.
   */
  void loadingData();

  /**
   * Show the ticker, number of shares, and value of the stock.
   *
   * @param info List of ticker, number of shares, share value.
   */
  void showIndividualStocks(String[] info);

  /**
   * Display an error box with a given error.
   *
   * @param error Error to display, line by line.
   */
  void showError(String[] error);

  /**
   * Show a confirmation that a portfolio was successfully made.
   *
   * @param s Name of the portfolio.
   */
  void portfolioCreationConfirmation(String s);

  /**
   * Prompt for entering a portfolio name when creating a new portfolio.
   */
  void promptPortName();

  /**
   * Prompt when adding a stock to a portfolio, asks for ticker.
   */
  void promptAddStock();

  /**
   * Prompt the user for the number of shares to buy of a given stock.
   */
  void promptShares();

  /**
   * Show all the portfolios you can view.
   *
   * @param i        Portfolio Number.
   * @param portName portfolio name.
   */
  void promptPortfolioChoices(int i, String portName);

  /**
   * Prompt the user to enter a date in the accepted format.
   */
  void promptDate();

  /**
   * Prompts the user to enter the number of the portfolio that they would like to view.
   */
  void enterNamePort();

  /**
   * Show the quit screen.
   */
  void quit();

  /**
   * Prompts user to enter name of file to load data.
   */
  void promptLoadData();

  /**
   * Prompts the user for the date purchased for shares.
   */
  void promptDatePurchased();

  /**
   * prompts the Date sold for a security.
   */
  void promptDateSold();


  /**
   * Prints the performance graph.
   * @param date takes in a local date as a string.
   * @param astericks amount of asterisks based on the scale
   */
  void printGraph(String date, String astericks);


  /**
   * Prompts the user to buy a stock.
   */
  void promptBuyStock();

  /**
   * Prompts the user to enter in a date to buy a stock.
   */
  void promptDateBuyStock();

  /**
   * Prompts the user to enter in a date to sell a stock.
   */
  void promptDateSellStock();

  /**
   * Prompts the user to sell a stock.
   */
  void promptSellStock();

  /**
   * Prints the stocks to sell.
   * @param ticker user enters in a ticker.
   * @param shares user enters in amount of shares.
   */
  void printStocksToSell(String ticker, String shares);

  /**
   * Prompts the user to choose a portfolio to choose from.
   */
  void promptWhichPort();


  /**
   * Prints all the possible portfolios.
   * @param portfolioName prints the name of the portfolios
   */
  void printPortfolios(String portfolioName);

  /**
   * Prompts the user to enter in the commission value.
   */
  void promptCommission();

  /**
   * Prompts the user for a start date.
   */
  void promptStartDate();

  /**
   * Prompts the user for an end date.
   */
  void promptEndDate();

  /**
   * Prints the graph scale at the bottom of the performance graph.
   * @param scale the number to scale
   */
  void printGraphScale(String scale);

  /**
   * Prints the title of graph for X portfolio and between dates.
   * @param portfolioName Takes in portfolio name.
   * @param startDate Takes in the start date for the performance.
   * @param endDate Takes in the end date for the performance.
   */
  void printGraphTitle(String portfolioName, String startDate, String endDate);

  /**
   * Shows the individual stocks for a Flexible Portfolio.
   * @param info takes in the info containing ticker, date, shares, commission
   */
  void showIndividualStocksFlex(String[]info);
}
