package constants;

import java.io.PrintStream;

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
      "\nStock List doesn't contain this stock, enter"
          + " again:  (0 to return to main menu): ";

  public static final String ADD_STOCKS_IN_PORTFOLIO_ASK_STOCK_NUMBER =
      "Number of stocks for the company"
          + ": ";

  public static final String ASK_STOCK_NUMBER_AGAIN = "Number entered is not in correct format, "
      + "please enter again (e to return to main menu): ";
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

  public static final String WELCOME_MENU = "************************************************************\n"+
      "1. Flexible portfolio\n"+
      "2. Inflexible portfolio\n"+
      "0. Exit\n"+
      "Enter your choice: ";

  public static final String FLEXIBLE_PORTFOLIO_HEADER = "-------------Flexible Portfolio Menu-------------\n";

  public static final String FLEX_PORTFOLIO_EXIT_MSG = "\nthank you for using flexible portfolio wizard\n";

  public static final String ASK_COMMISSION_FEE = "\nCommission fee for this transaction: ";

  public static final String ASK_COMMISSION_FEE_AGAIN = "\nWrong format, again enter Commission fee for this transaction: ";

  public static final String UNSUCCESSFUL_PORTFOLIO_CREATION_MSG="portfolio cant be added\n";

  public static final String LOAD_FLEX_PORTFOLIO_DETAIL_MENU = "1. Summary of the portfolio\n"+
      "2. Current value of the portfolio\n"+
      "3. Historical value of the portfolio\n"+
      "4. Add stocks to the portfolio\n"+
      "5. Sell stocks from the portfolio\n"+
      "6. Cost basis of the portfolio\n"+
      "7. Print Performance Graph\n"+
      "8. Go back to list view menu\n"+
      "9. Go to main menu\n"+
      "Enter your choice: ";

  public static final String WRONG_DATE_MSG = "Please enter in the correct format in the given range(0 to return to list view)\n";

  public static final String WRONG_DATE_BEFORE_LAST_TX ="kindly enter date after latest transaction for this stock(0 to return to list view)\n";

  public static final String WRONG_DATE_BEFORE_PORTFOLIO_CREATION = "kindly enter date after portfolio creation(0 to return to list view)\n";

  public static final String SUCCESSFUL_TRANSACTION = "\ntransaction successful for the portfolio\n\n";

  public static final String UNSUCCESSFUL_TRANSACTION = "\ntransaction unsuccessful\n\n";

  public static final String STOCK_NOT_IN_PORTFOLIO_MSG = "Portfolio doesn't contain this stock, enter again(0 to return to main menu): ";

  public static final String STOCK_LESS_THAN_IN_PORTFOLIO = "Shares to be sold are more than the number of shares existing\n";

  public static final String ASK_START_DATE_FOR_GRAPH = "Starting date\n";

  public static final String ASK_END_DATE_FOR_GRAPH = "Ending date\n";

  public static final String GRAPH_INVALID_RANGE = "\nInvalid date range\n";


  }
