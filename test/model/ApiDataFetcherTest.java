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
}