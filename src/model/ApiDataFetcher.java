package model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import constants.Constants;

public class ApiDataFetcher {


  public static String fetchStockDataBySymbol(String ticker , String apiKey) throws Exception {
    URL url = null;
    try {
      url = new URL(Constants.getDailyDataTimeSeriesApi
              + "&symbol"
              + "=" + ticker + "&apikey=" + apiKey + "&outputsize=full" + "&datatype=csv");
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
