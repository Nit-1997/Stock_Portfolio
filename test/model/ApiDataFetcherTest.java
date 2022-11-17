package model;

import static org.junit.Assert.assertNotEquals;

import constants.Constants;
import org.junit.Test;


/**
 * Junit class to test ApiDataFetcher.
 */
public class ApiDataFetcherTest {

  @Test
  public void testYahooApi() {
    String ans = (ApiDataFetcher.fetchStockDataBySymbolYahoo("AAPL",
        Constants.YAHOO_API_BASE_URL));
    assertNotEquals(0, ans.length());
  }


  @Test(expected = RuntimeException.class)
  public void testYahooApiMalformedURL() throws Exception {
    String ans = (ApiDataFetcher.fetchStockDataBySymbolYahoo("AAPL", "abc.com"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testYahooApiInvalidTicker() throws Exception {
    String ans = (ApiDataFetcher.fetchStockDataBySymbolYahoo("someRandom",
        Constants.YAHOO_API_BASE_URL));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testYahooApiNullTicker() throws Exception {
    String ans = (ApiDataFetcher.fetchStockDataBySymbolYahoo(null,
        Constants.YAHOO_API_BASE_URL));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testYahooApiNullUrl() throws Exception {
    String ans = (ApiDataFetcher.fetchStockDataBySymbolYahoo("AAPL", null));
  }


  @Test
  public void testAlphaVantageApi() throws Exception {
    String data = ApiDataFetcher.fetchStockDataBySymbolAlphaVantage("AAPL");
    assertNotEquals(0, data.length());
  }


  @Test
  public void testAlphaVantageApiHighFrequency() throws Exception {
    String[] tickers = {"AAPL", "CSCO", "AAPL", "CSCO", "AAPL", "CSCO", "AAPL", "CSCO", "AAPL",
        "CSCO", "AAPL", "CSCO"};
    for (String ticker : tickers) {
      String data = ApiDataFetcher.fetchStockDataBySymbolAlphaVantage(ticker);
      assertNotEquals(0, data.length());
    }
  }
}