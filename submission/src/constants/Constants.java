package constants;

import java.util.Set;

/**
 * This class contains application constants that will be used by multiple modules.
 */
public class Constants {

  public static String GET_DAILY_DATA_TIME_SERIES_API = "https://www.alphavantage"
          + ".co/query?function=TIME_SERIES_DAILY"
          + "&outputsize=full" + "&datatype=csv";
  public static String API_KEY = "2ZLBEVS8SUFF6UBC";
  //  public static String getCurrentValueApi = "https://www.alphavantage"
  //          + ".co/query?function=GLOBAL_QUOTE";

  public static final String YAHOO_API_BASE_URL = "https://query1.finance.yahoo.com/v7/finance/download/";
  public static final int TOTAL_HANDLED_STOCKS = 102;
  public static Set<String> STOCK_NAMES;

  public static final int COMMISSION_FEE = 2;

  //  public static final String[] keys = {
  //          "RSNOZCM1AIHGILQZ",
  //          "9G8A1X5VH7W7QNPH",
  //          "K9V4Z34GR9UPOPZ5",
  //          "ZCVYQOK98OIWK0E6"
  //  };
  //  public static final List<String> apiKeys = new ArrayList<>(Arrays.asList(keys));
}
