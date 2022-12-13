package model;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract representation of a portfolio. Has both flexible and inflexible extensions.
 */
public abstract class AbstractPortfolio implements Portfolio {

  protected String portfolioName;
  protected Map<String, Stock> stocks;

  public AbstractPortfolio(String portfolioName) {
    this.portfolioName = portfolioName;
    this.stocks = new HashMap<>();
  }

  @Override
  public String getPortName() {
    return this.portfolioName;
  }

  @Override
  public int getNumStocks() {
    return this.stocks.size();
  }

  @Override
  public double getPortValue(LocalDate date) throws IllegalArgumentException {
    DecimalFormat format = new DecimalFormat("#.##");
    format.setRoundingMode(RoundingMode.HALF_UP);
    Map<String, Double> values = this.getValues(date);
    Double sum = 0.0;

    for (String ticker : values.keySet()) {
      sum += this.getShares(ticker, date) * values.get(ticker);
    }

    return Double.parseDouble(format.format(sum));
  }

  @Override
  public Map<String, Double> getValues(LocalDate date) throws IllegalArgumentException {
    Map<String, Double> stocksOwned = this.getTickerMap(date);
    Map<String, Double> stockValues = new HashMap<>();
    for (String ticker : stocksOwned.keySet()) {
      Stock stock = stocks.get(ticker);
      stockValues.put(ticker, stock.getValue(date).get("close"));
    }
    return stockValues;
  }

  /**
   * Update our cached data for a given stock. Calls the API, and updates the stocks map.
   *
   * @param ticker Ticker to update.
   * @throws IllegalArgumentException Stock not found, or API has changed.
   */
  protected void updateStock(String ticker) throws IllegalArgumentException {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
      int daysToSub = 1;
      // If it's after 5pm, we should have data for today, otherwise most up to date is
      // yesterday
      if (LocalTime.now().isAfter(LocalTime.from(LocalDate.now().atTime(5, 0)))) {
        daysToSub = 0;
      }

      LocalDate yesterday = LocalDate.parse(
          formatter.format(LocalDate.now(ZoneId.systemDefault()).minusDays(daysToSub)));
      this.stocks.get(ticker).getValue(yesterday);
      // If this goes through, we have the most recent data already so no need to call the API
      return;
    } catch (IllegalArgumentException | NullPointerException e) {
      // We don't have data, so continue
    }

    AlphaVantage api = new AlphaVantage();
    String[][] data;

    try {
      data = api.getStock(ticker);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }

    Stock newStock = new StockImpl(ticker, data[0], Arrays.copyOfRange(data, 1, data.length));
    this.stocks.put(ticker, newStock);
  }

  /**
   * Determine if date is valid, ie is not future and not weekend.
   *
   * @param date Date to check.
   * @return Whether this date is valid.
   */
  protected boolean validDate(LocalDate date) {
    return !(date.getDayOfWeek().equals(SUNDAY) ||
        date.getDayOfWeek().equals(SATURDAY) ||
        date.isAfter(LocalDate.now(ZoneId.systemDefault())));
  }

  /**
   * Determine if a date is a weekend.
   *
   * @param date Date to check.
   * @return Whether date is weekend.
   */
  protected boolean isWeekend(LocalDate date) {
    return !(date.getDayOfWeek().equals(SUNDAY) ||
        date.getDayOfWeek().equals(SATURDAY));
  }


}
