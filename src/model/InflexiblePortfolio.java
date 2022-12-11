package model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Representation of a portfolio.
 */
public class InflexiblePortfolio extends AbstractPortfolio {
  protected Map<String, Double> tickers;

  /**
   * Construct a new inflexible portfolio.
   *
   * @param portName Name of portfolio.
   * @param stocks   Map of tickers to number of stocks.
   */
  public InflexiblePortfolio(String portName, Map<String, Integer> stocks) {
    super(portName);
    Map<String, Double> converted = new HashMap<>();
    for (Map.Entry<String, Integer> entry : stocks.entrySet()) {
      converted.put(entry.getKey(), entry.getValue().doubleValue());
    }
    this.tickers = converted;

    if (Collections.min(stocks.values()) <= 0) {
      throw new IllegalArgumentException("Cannot buy zero or negative number of stocks!");
    }

    // Get the data for all the stocks.
    for (String ticker : stocks.keySet()) {
      // Will throw if invalid ticker.
      this.updateStock(ticker);
    }
  }

  @Override
  public Map<String, Double> getTickerMap(LocalDate date) {
    return this.tickers;
  }

  @Override
  public Double getShares(String ticker, LocalDate date) throws IllegalArgumentException {
    return this.tickers.get(ticker);
  }

  @Override
  public double getCostBasis(LocalDate date) throws IllegalArgumentException {
    return this.getPortValue(date);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("{\n");
    int count = 0;
    for (Map.Entry<String, Double> entry : tickers.entrySet()) {
      if (tickers.size() - 1 == count) {
        builder.append("    " + "\"" + entry.getKey() + "\"" + ": " + entry.getValue() + "\n");
      } else {
        builder.append("    " + "\"" + entry.getKey() + "\"" + ": " + entry.getValue() + ",\n");
        count = count + 1;
      }
    }
    builder.append("}");
    return builder.toString();
  }

  @Override
  public boolean isFlexible() {
    return false;
  }
}
