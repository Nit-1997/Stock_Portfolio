package model;

/**
 * Interface to encapsulate all api related operations.
 */
public interface ApiDataFetcher {

  /**
   * Fetches the historic stock data using symbol from yahoo.
   *
   * @param ticker  ticker symbol
   * @param baseUrl base url of the api
   * @return historic timeseries data in string format
   * @throws RuntimeException can occur due to incorrect url/params
   */
  public String fetchStockDataBySymbolYahoo(String ticker, String baseUrl) throws
          RuntimeException;


  /**
   * Fetches stock data by symbol from alpha vantage API.
   *
   * @param ticker stock symbol
   * @return stock data.
   * @throws Exception if ticker is not valid.
   */
  public String fetchStockDataBySymbolAlphaVantage(String ticker) throws Exception;

}
