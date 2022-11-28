package model;

import constants.Constants;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import utils.Utils;

/**
 * This class is used to fetch historic stock data. The source can either be alpha vantage / yahoo.
 * As part of this implementation we are using yahoo's finance api. as yahoo api does not rate limit
 * users after 5 requests
 */
public class ApiDataFetcherImpl  implements  ApiDataFetcher{


  @Override
  public  String fetchStockDataBySymbolYahoo(String ticker, String baseUrl) throws
          RuntimeException {
    if (ticker == null || baseUrl == null) {
      throw new IllegalArgumentException("passed null args");
    }
    URL url;
    try {
      String[] periods = Utils.yahooApiDateFetcher();
      String apiUrl = baseUrl
              + ticker
              + "?metrics=high?&interval=1d&period1="
              + periods[0] + "&period2="
              + periods[1];
      url = new URL(apiUrl);
    } catch (MalformedURLException e) {
      throw new RuntimeException("the yahoo API has either changed or " + "no longer works");
    }
    InputStream in;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      LinkedList<String> o = new LinkedList<>();
      StringBuilder ans = new StringBuilder();
      int line = 0;
      while ((b = in.read()) != -1) {
        if (b == '\n') {
          if (line == 0) {
            ans.append(output);
          } else {
            o.addFirst(output.toString());
          }
          output.setLength(0);
          line++;
        }
        output.append((char) b);
      }
      o.addFirst(output.toString());
      for (String s : o) {
        ans.append(s);
      }
      return ans.toString();
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for this symbol.");
    }
  }


  @Override
  public  String fetchStockDataBySymbolAlphaVantage(String ticker) throws Exception {
    URL url = null;
    try {
      url = new URL(Constants.GET_DAILY_DATA_TIME_SERIES_API
              + "&symbol"
              + "=" + ticker + "&apikey=" + Constants.API_KEY);
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphaVantage API has either changed or "
              + "no longer works");
    }
    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
      return output.toString();
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for this symbol.");
    }
  }
}
