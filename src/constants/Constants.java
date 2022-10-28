package constants;

public class Constants {
  public static String WelcomeMessage
          = "-------------Welcome to stock portfolio app-------------";

  public static String MenuMessage
          = "************************************************************\n" +
          "  1. Add new portfolio \n" +
          "  2. View all portfolios \n" +
          "  0. Exit \n" +
          "  Enter your choice: ";

  public static String ErrorNote="Please enter an integer value between 0 and 2 ";



//  public static String ApiKey = "9G8A1X5VH7W7QNPH";

  public static String getDailyDataTimeSeriesApi = "https://www.alphavantage"
          + ".co/query?function=TIME_SERIES_DAILY"
          + "&outputsize=full";

  public static String ApiKey = "ZCVYQOK98OIWK0E6";
  public static String getCurrentValueApi = "https://www.alphavantage"
          + ".co/query?function=GLOBAL_QUOTE";

  public static String AddPortfolio="\n\n-------------Portfolio Creation-------------\n"+
      "  1. Add name of the portfolio : ";

  public static String AddStocksInPortfolioAskTickerSymbol="\nTicker symbol of the company: ";

  public static String AddStocksInPortfolioAskStockNumber="Number of stocks for the company: ";

  public static String AddStocksInPortfolioConfirmation="\n"+
      "Stock added successfully to the portfolio!!\n"+
      "Do you want to add more stocks or remove any stocks (y/n)? ";

  public static String StocksInPortfolioAddOrRemoveMenu="1. Add more stocks\n"+
      "2. Remove any stocks.\n"+
      "3. Save the portfolio. \n"+
      "Enter your choice: ";

  public static String RemoveStocksInPortfolioSuccessfulConfirmation="\n"+
      "Stock removed successfully to the portfolio!!\n"+
      "Do you want to add more stocks or remove any stocks (y/n)? ";

  public static String RemoveStocksInPortfolioUnSuccessfulConfirmation="\n"+
      "Stocks can't be removed!!\n"+
      "Do you want to add more stocks or remove any stocks (y/n)? ";

  public static String AddStocksInPortfolioErrorNote="Please enter an integer value between 1 and 3: ";




}
