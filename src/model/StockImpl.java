package model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of a stock.
 */
public class StockImpl implements Stock {
  private String ticker;
  private Map<LocalDate, double[]> data;
  private String[] keys;

  /**
   * Construct a new StockImpl.
   *
   * @param ticker Ticker for this stock.
   * @param keys   String description of the values being stored by this stockImpl.
   *               Represents the labels open, close, volume etc.
   * @param values Values for this stock as list, seperated into lists by date.
   * @throws IllegalArgumentException If the keys does not contain a date, throw an exception.
   */
  public StockImpl(String ticker, String[] keys, String[][] values)
          throws IllegalArgumentException {
    this.ticker = ticker;
    this.keys = keys;
    boolean hasDate = Arrays.stream(keys).anyMatch("timestamp"::equals);
    if (!hasDate) {
      throw new IllegalArgumentException("Keys must contain a timestamp!");
    }

    this.data = new HashMap<>();

    try {
      for (String[] entry : values) {
        LocalDate date = LocalDate.parse(entry[0]);
        double[] dataEntry = Arrays.stream(Arrays
                        .copyOfRange(entry, 1, entry.length))
                .mapToDouble(Double::parseDouble)//Go to doubles.
                .toArray();
        data.put(date, dataEntry);
      }
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Date in wrong format! Use YYYY-MM-DD");
    }
  }

  @Override
  public String getTicker() {
    return this.ticker;
  }

  @Override
  public Map<String, Double> getValue(LocalDate date) throws IllegalArgumentException {
    Map<String, Double> values = new HashMap<>();
    double[] dateValues = this.data.get(date);
    if (dateValues == null) {
      throw new IllegalArgumentException("Date not found");
    }

    for (int index = 1; index < keys.length; ++index) {
      values.put(keys[index], dateValues[index - 1]);
    }
    return values;
  }
}
