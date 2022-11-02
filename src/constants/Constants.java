package constants;

import java.util.Set;

/**
 * This class contains application constants that will be used by multiple modules.
 */
public class Constants {

  //  public static String getDailyDataTimeSeriesApi = "https://www.alphavantage"
  //          + ".co/query?function=TIME_SERIES_DAILY"
  //          + "&outputsize=full";
  //  public static String ApiKey = "ZCVYQOK98OIWK0E6";
  //  public static String getCurrentValueApi = "https://www.alphavantage"
  //          + ".co/query?function=GLOBAL_QUOTE";

  public static final String YAHOO_API_BASE_URL = "https://query1.finance.yahoo.com/v7/finance/download/";
  public static final int TOTAL_HANDLED_STOCKS = 20;
  public static Set<String> STOCK_NAMES;

  //  public static final String[] keys = {
  //          "RSNOZCM1AIHGILQZ",
  //          "9G8A1X5VH7W7QNPH",
  //          "K9V4Z34GR9UPOPZ5",
  //          "ZCVYQOK98OIWK0E6"
  //  };
  //  public static final List<String> apiKeys = new ArrayList<>(Arrays.asList(keys));
}
