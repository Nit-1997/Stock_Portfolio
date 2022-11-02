package constants;

import java.io.PrintStream;

/**
 * This class contains all the constant printing statements for the view.
 */
public class ViewConstants {

  public static final String WelcomeMessage = "-------------Welcome to stock portfolio app-------"
      + "------";

  public static final String MenuMessage =
      "************************************************************\n" + "  1. Add new portfolio \n"
          + "  2. View all portfolios \n" + "  0. Exit \n" + "  Enter your choice: ";

  public static final String ErrorNote = "Please enter an integer value between 0 and 2 ";

  public static final String ExitNote = "\n" + "Thank you for using our Stock Application";


  public static final String AddPortfolio =
      "\n\n-------------Portfolio Creation-------------\n" + "  1. Add name of the portfolio : ";

  public static final String AskPortfolioNameAgain = "Portfolio with this name already exists,"
      + " kindly enter another name (0 to return to the main menu): ";

  public static final String StockNotInPortfolio="stock not in portfolio\n";

  public static final String AddStocksInPortfolioAskTickerSymbol = "\nTicker symbol of the company"
      + " (0 to return to the main menu): ";

  public static final String AskTickerSymbolAgain = "\nPortfolio doesn't contain this stock, enter"
      + " again:  (0 to return to main menu): ";

  public static final String AddStocksInPortfolioAskStockNumber = "Number of stocks for the company"
      + ": ";

  public static final String AskStockNumberAgain = "Number entered is not in correct format, "
      + "please enter again (0 to return to main menu): ";
  public static final String AddStocksInPortfolioConfirmation =
      "\n" + "Stock added successfully to the portfolio!!\n"
          + "Do you want to add more stocks or remove any stocks (y/n)? ";

  public static final String StocksInPortfolioAddOrRemoveMenu =
      "1. Add more stocks\n" + "2. Remove any stocks.\n" + "3. Save the portfolio. \n"
          + "Enter your choice: ";

  public static final String RemoveStocksInPortfolioSuccessfulConfirmation =
      "\n" + "Stock removed successfully from the portfolio!!\n"
          + "Do you want to add more stocks or remove any stocks (y/n)? ";

  public static final String RemoveStocksInPortfolioUnSuccessfulConfirmation =
      "\n" + "Stocks can't be removed!!\n"
          + "Do you want to add more stocks or remove any stocks (y/n)? ";

  public static final String AskConfirmation = "Do you want to add more stocks or remove any "
      + "stocks (y/n)? ";

  public static final String WaitMessage = "\n Saving your portfolio, please wait...";
  public static final String AddStocksInPortfolioErrorNote = "Please enter an integer value "
      + "between 1 and 3: ";


  public static final String LoadPortfolioMenu =
      "\n" + "1. Details of any portfolio\n" + "2. Return to main menu \n" + "Enter your choice: ";

  public static final String AskNameOfPortfolio = "Enter name of the portfolio ";

  public static final String AskPortfolioNameAgainUnique = "No Portfolio with this name , kindly "
      + "enter another name (0 to return to list view): ";

  public static final String loadPortfolioDetailMenu =
      "\n" + "1. Summary of the portfolio\n" + "2. Current Detailed view of the portfolio\n"
          + "3. Current Value of the portfolio\n" + "4. Historical Detailed view of the portfolio\n"
          + "5. Historical Value of the portfolio\n" + "6. Return to the lists of portfolio\n"
          + "7. Return to the main menu\n" + "Enter your choice: ";

  public static final String WaitLoadMessage = "Please wait while we are loading data...\n";

  public static final String AskDate = "Please enter date (yyyy-MM-dd): ";

  public static final String AskDateAgain = "Please enter date in the correct format (yyyy-MM-dd) "
      + "in the given range (0 to return to list view): ";

  public static final String PrintInCompatiblePortfolio =
      "Error in the asked portfolio, Possible scenarios:\n" + "1. File not exist\n"
          + "2. Incompatible data in file\n"
          + "3. Any stock of the portfolio didnt exist at that time \n";

  public static final String LoadPortfolioErrorNote = "Please enter an integer value from above"
      + " options: ";


}
