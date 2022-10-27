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


  public static String ApiKey = "9G8A1X5VH7W7QNPH";
  public static String ApiURL = "https://www.alphavantage"
          + ".co/query?function=TIME_SERIES_DAILY"
          + "&outputsize=full";

}
