package model;

import org.junit.Test;

import constants.Constants;

import static org.junit.Assert.*;

/**
 * Junit class to test ApiDataFetcher
 */
public class ApiDataFetcherTest {

  @Test
  public void testYahooApi() {
      String ans = (ApiDataFetcher.fetchStockDataBySymbolYahoo("AAPL", Constants.yahooApiBaseUrl));
      assertNotEquals(0,ans.length());
  }


  @Test(expected = RuntimeException.class)
  public void testYahooApiMalformedURL() throws Exception {
    String ans = (ApiDataFetcher.fetchStockDataBySymbolYahoo("AAPL", "abc.com"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testYahooApiInvalidTicker() throws Exception {
    String ans = (ApiDataFetcher.fetchStockDataBySymbolYahoo("someRandom", Constants.yahooApiBaseUrl));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testYahooApiNullTicker() throws Exception {
    String ans = (ApiDataFetcher.fetchStockDataBySymbolYahoo(null, Constants.yahooApiBaseUrl));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testYahooApiNullUrl() throws Exception {
    String ans = (ApiDataFetcher.fetchStockDataBySymbolYahoo("AAPL", null));
  }
}