package model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Handles the alphavantage API for getting stock info.
 */
public class AlphaVantage implements StockAPI {

  /**
   * Get the stock info for a particular ticker.
   *
   * @param stockSymbol Ticker to search for.
   * @return String representation of stock data for given company.
   */
  public String[][] getStock(String stockSymbol) throws IllegalArgumentException {
    //the API key needed to use this web service.
    //Please get your own free API key here: https://www.alphavantage.co/
    //Please look at documentation here: https://www.alphavantage.co/documentation/
    String apiKey = "KTFKSXGNFXDBMF6M";
    URL url = null;

    try {
      /*
      create the URL. This is the query to the web service. The query string
      includes the type of query (DAILY stock prices), stock symbol to be
      looked up, the API key and the format of the returned
      data (comma-separated values:csv). This service also supports JSON
      which you are welcome to use.
       */
      url = new URL("https://www.alphavantage"
          + ".co/query?function=TIME_SERIES_DAILY"
          + "&outputsize=full"
          + "&symbol"
          + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      /*
      Execute this query. This returns an InputStream object.
      In the csv format, it returns several lines, each line being separated
      by commas. Each line contains the date, price at opening time, highest
      price for that date, lowest price for that date, price at closing time
      and the volume of trade (no. of shares bought/sold) on that date.

      This is printed below.
       */
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + stockSymbol);
    }

    String[] lines = output.toString().split("\n");
    String outString = output.toString();

    if (outString.contains("Error")) {
      throw new IllegalArgumentException("Invalid Ticker");
    }

    // If we hit our max of 5 calls per minute, wait 5 seconds and try again.
    if (outString.contains("5 calls per minute")) {
      try {
        TimeUnit.SECONDS.sleep(5);
        return getStock(stockSymbol);
      } catch (InterruptedException e) {
        //Continue normal execution
      }
    }

    List<String[]> data = new ArrayList<>();

    for (String line : lines) {
      data.add(line.split(","));
    }
    return data.toArray(new String[0][]);
  }
}
