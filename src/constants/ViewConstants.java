package constants;

/**
 * This class contains all the constant printing statements for the view.
 */
public class ViewConstants {

  public static final String WELCOME_MESSAGE = "-------------Welcome to stock portfolio app-------"
      + "------";

  public static final String MENU_MESSAGE =
      "************************************************************\n" + "  1. Add new portfolio \n"
          + "  2. View all portfolios \n" + "  0. Exit \n" + "  Enter your choice: ";

  public static final String ERROR_NOTE = "Please enter an integer value between 0 and 2 ";

  public static final String EXIT_NOTE = "\n" + "Thank you for using our Stock Application";


  public static final String ADD_PORTFOLIO =
      "\n\n-------------Portfolio Creation-------------\n" + "  1. Add name of the portfolio : ";

  public static final String ASK_PORTFOLIO_NAME_AGAIN = "Portfolio with this name already exists,"
      + " kindly enter another name (0 to return to the main menu): ";

  public static final String STOCK_NOT_IN_PORTFOLIO = "stock not in portfolio\n";

  public static final String ADD_STOCKS_IN_PORTFOLIO_ASK_TICKER_SYMBOL =
      "\nTicker symbol of the company"
          + " (0 to return to the main menu): ";

  public static final String ASK_TICKER_SYMBOL_AGAIN =
      "\nPortfolio doesn't contain this stock, enter"
          + " again:  (0 to return to main menu): ";

  public static final String ADD_STOCKS_IN_PORTFOLIO_ASK_STOCK_NUMBER =
      "Number of stocks for the company"
          + ": ";

  public static final String ASK_STOCK_NUMBER_AGAIN = "Number entered is not in correct format, "
      + "please enter again (0 to return to main menu): ";
  public static final String ADD_STOCKS_IN_PORTFOLIO_CONFIRMATION =
      "\n" + "Stock added successfully to the portfolio!!\n"
          + "Do you want to add more stocks or remove any stocks (y/n)? ";

  public static final String STOCKS_IN_PORTFOLIO_ADD_OR_REMOVE_MENU =
      "1. Add more stocks\n" + "2. Remove any stocks.\n" + "3. Save the portfolio. \n"
          + "Enter your choice: ";

  public static final String REMOVE_STOCKS_IN_PORTFOLIO_SUCCESSFUL_CONFIRMATION =
      "\n" + "Stock removed successfully from the portfolio!!\n"
          + "Do you want to add more stocks or remove any stocks (y/n)? ";

  public static final String REMOVE_STOCKS_IN_PORTFOLIO_UNSUCCESSFUL_CONFIRMATION =
      "\n" + "Stocks can't be removed!!\n"
          + "Do you want to add more stocks or remove any stocks (y/n)? ";

  public static final String ASK_CONFIRMATION = "Do you want to add more stocks or remove any "
      + "stocks (y/n)? ";

  public static final String WAIT_MESSAGE = "\n Saving your portfolio, please wait...";

  public static final String ADD_STOCKS_IN_PORTFOLIO_ERROR_NOTE = "Please enter an integer value "
      + "between 1 and 3: ";


  public static final String LOAD_PORTFOLIO_MENU =
      "\n" + "1. Select any portfolio\n" + "2. Return to main menu \n" + "Enter your choice: ";

  public static final String ASK_NAME_OF_PORTFOLIO = "Enter name of the portfolio ";

  public static final String ASK_PORTFOLIO_NAME_AGAIN_UNIQUE =
      "No Portfolio with this name , kindly "
          + "enter another name (0 to return to list view): ";

  public static final String LOAD_PORTFOLIO_DETAIL_MENU =
      "\n" + "1. Summary of the portfolio\n" + "2. Current Detailed view of the portfolio\n"
          + "3. Current Value of the portfolio\n" + "4. Historical Detailed view of the portfolio\n"
          + "5. Historical Value of the portfolio\n" + "6. Return to the lists of portfolio\n"
          + "7. Return to the main menu\n" + "Enter your choice: ";

  public static final String WAIT_LOAD_MESSAGE = "Please wait while we are loading data...\n";

  public static final String ASK_DATE = "Please enter date (yyyy-MM-dd): ";

  public static final String ASK_DATE_AGAIN =
      "Please enter date in the correct format (yyyy-MM-dd) "
          + "in the given range (0 to return to list view): ";

  public static final String PRINT_INCOMPATIBLE_PORTFOLIO =
      "Error in the asked portfolio, Possible scenarios:\n" + "1. File not exist\n"
          + "2. Incompatible data in file\n"
          + "3. Any stock of the portfolio didnt exist at that time \n";

  public static final String LOAD_PORTFOLIO_ERROR_NOTE = "Please enter an integer value from above"
      + " options: ";


}
