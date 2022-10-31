package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ApiDataFetcherTest {

  @Test
  public void testYahooApi() {
    try {
      System.out.println(ApiDataFetcher.fetchStockDataBySymbolYahoo("AAPL"));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}