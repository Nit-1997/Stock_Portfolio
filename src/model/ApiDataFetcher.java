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
  /**
   * Fetches data for a given API endpoint
   *
   * @param url api endpoint
   * @return api response
   */
  public static StringBuilder fetchApiData(URL url) {
    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
      return output;
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for this symbol.");
    }
  }

  /**
   * Fetches historic time series data for a given ticker.
   *
   * @param ticker stock symbol
   * @return parsed response
   * @throws Exception when api url does not respond as expected.
   */
  public static Map<String , List<String>> fetchDataDailyHistoricByTicker(String ticker) throws Exception {
    URL url = null;
    try {
      url = new URL(Constants.getDailyDataTimeSeriesApi
              + "&symbol"
              + "=" + ticker + "&apikey=" + Constants.ApiKey + "&outputsize=full" + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      int lineNo = 0;
      Map<String , List<String>> parsedResponse = new HashMap<>();
      while ((b = in.read()) != -1) {
        if((char) b == '\n'){
           lineNo++;
           if(lineNo == 1){
             output.setLength(0);
             continue;
           }else{
             String[] out = output.toString().split(",");
             String key = out[0];
             List<String> val = new ArrayList<>();
             val.add(ticker);
             val.add(out[4]);
             parsedResponse.put(key,val);
             output.setLength(0);
           }
        }
        output.append((char) b);
      }
      return parsedResponse;
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for this symbol.");
    }
  }

  /**
   * Fetches current data for a given ticker.
   *
   * @param ticker stock symbol
   * @return parsed response
   * @throws Exception when api url does not respond as expected.
   */
  public static String fetchCurrentValueApi(String ticker) throws Exception {
    URL url = null;
    try {
      url = new URL(Constants.getCurrentValueApi
              + "&symbol"
              + "=" + ticker + "&apikey=" + Constants.ApiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
    StringBuilder output = fetchApiData(url);
    String[] out = output.toString().split("\n");
    String[] out2 = out[1].split(",");
    return out2[7];
  }

  public static String fetchStockDataBySymbol(String ticker) throws Exception {
    URL url = null;
    try {
      url = new URL(Constants.getDailyDataTimeSeriesApi
              + "&symbol"
              + "=" + ticker + "&apikey=" + Constants.ApiKey + "&outputsize=full" + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
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
