package model;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Implementation for the model.
 */
public class ModelImpl implements Model {

  private final Map<String, Portfolio> portfolios;
  private final Map<String, Boolean> portFlexibilities;
  // Will store tickers we have checked before, to prevent unnecessary api calls.
  // For use with validTicker.
  private final Map<String, Boolean> validTickers;

  /**
   * Construct a new model.
   */
  public ModelImpl() {
    this.portfolios = new HashMap<>();
    this.portFlexibilities = new HashMap<>();
    this.validTickers = new HashMap<>();
  }

  @Override
  public Map<String, Boolean> getPortfolios() {
    return this.portFlexibilities;
  }

  @Override
  public void newInflexiblePortfolio(String name, Map<String, Integer> tickers)
      throws IllegalArgumentException {
    if (this.portFlexibilities.containsKey(name)) {
      throw new IllegalArgumentException("Portfolio with that name already exists!");
    }
    Portfolio port = new InflexiblePortfolio(name, tickers);
    portfolios.put(name, port);
    portFlexibilities.put(name, false);
  }


  @Override
  public void newFlexiblePortfolio(String name) throws IllegalArgumentException {
    if (this.portFlexibilities.containsKey(name)) {
      throw new IllegalArgumentException("Portfolio with that name already exists!");
    }
    Portfolio port = new FlexiblePortfolio(name);
    portfolios.put(name, port);
    portFlexibilities.put(name, true);
  }

  @Override
  public boolean isFlexible(String portfolioName) throws IllegalArgumentException {
    this.checkValidPort(portfolioName);
    return this.portFlexibilities.get(portfolioName);
  }

  @Override
  public void addStock(String portfolioName, String ticker, int numStocks, LocalDate date,
      double commission) throws IllegalArgumentException {
    this.checkValidPort(portfolioName);
    if (!this.portFlexibilities.get(portfolioName)) {
      throw new IllegalArgumentException("Portfolio not flexible, cannot add stock!");
    }
    // At this point we know this portfolio is flexible.
    FlexiblePortfolio port = (FlexiblePortfolio) this.portfolios.get(portfolioName);
    port.addStock(ticker, numStocks, date, commission);
  }

  @Override
  public void sellStock(String portfolioName, String ticker, int numStocks, LocalDate date,
      double commission) throws IllegalArgumentException {
    this.checkValidPort(portfolioName);
    if (!this.portFlexibilities.get(portfolioName)) {
      throw new IllegalArgumentException("Portfolio not flexible, cannot sell stock!");
    }
    // At this point we know this portfolio is flexible.
    FlexiblePortfolio port = (FlexiblePortfolio) this.portfolios.get(portfolioName);
    port.sellStock(ticker, numStocks, date, commission);
  }


  @Override
  public void newFixedAmountStrategy(String portfolioName, Map<String, Double> percentages,
      LocalDate date, double amount, double commission)
      throws IllegalArgumentException {
    // Invalid portfolio check
    this.checkValidPort(portfolioName);
    if (!this.portFlexibilities.get(portfolioName)) {
      throw new IllegalArgumentException("Portfolio not flexible, " +
          "cannot create fixed amount strategy!");
    }
    // Check other inputs
    this.checkValidPercentagesCommission(percentages, commission);

    // Must be flexible at this point.
    FlexiblePortfolio port = (FlexiblePortfolio) this.portfolios.get(portfolioName);
    Double totalToInvest = amount - (commission * percentages.size());
    List<FlexiblePortfolio.Transaction> transactions = new ArrayList<>();

    for (Map.Entry<String, Double> entry : percentages.entrySet()) {
      DecimalFormat df = new DecimalFormat("0.00");
      String ticker = entry.getKey();
      Double percentage = Double.valueOf(df.format(entry.getValue()));
      Double dollarAmount = (percentage / 100.0) * totalToInvest;
      port.updateStock(ticker); // Make sure to update cache

      FlexiblePortfolio.Transaction trans = new FlexiblePortfolio.Transaction(ticker, date,
          dollarAmount, commission);
      transactions.add(trans);
    }

    // Doing this prevents any transaction from being added if there is a single invalid ticker
    // Resetting this portfolio's transactions to be the transactions it had plus these new ones
    port.transactions = Stream.concat(port.transactions.stream(), transactions.stream())
        .collect(Collectors.toList());
  }

  @Override
  public void newDollarCostAverageStrategy(String portfolioName, Map<String, Double> percentages,
      LocalDate start, LocalDate end, int days, double amount,
      double commission) throws IllegalArgumentException {
    try {
      // See if we have a portfolio by that name, and if we do make sure it's flexible.
      this.checkValidPort(portfolioName);
    } catch (IllegalArgumentException e) {
      // There was no portfolio with that name, so make one and add this strategy to it.
      this.newFlexiblePortfolio(portfolioName);
    }
    // If the portfolio we found is inflexible
    if (!this.portFlexibilities.get(portfolioName)) {
      throw new IllegalArgumentException("Portfolio not flexible, cannot create fixed amount " +
          "strategy!");
    }
    // Check the input.
    this.checkValidPercentagesCommission(percentages, commission);

    // Must be flexible at this point.
    FlexiblePortfolio port = (FlexiblePortfolio) this.portfolios.get(portfolioName);
    Double totalToInvest = amount - (commission * percentages.size());
    List<FlexiblePortfolio.Transaction> transactions = new ArrayList<>();

    for (Map.Entry<String, Double> entry : percentages.entrySet()) {
      DecimalFormat df = new DecimalFormat("0.00");
      String ticker = entry.getKey();
      Double percentage = Double.valueOf(df.format(entry.getValue()));
      Double dollarAmount = (percentage / 100.0) * totalToInvest;
      port.updateStock(ticker); //Make sure to update cache for this stock

      // If there is an end date, use that constructor, otherwise use the constructor without an
      // end date so that it will recur indefinitely.
      FlexiblePortfolio.Transaction trans;
      if (end != null) {
        trans = new FlexiblePortfolio.RecurringTransaction(ticker,
            start, end,
            dollarAmount, commission, days);
      } else {
        trans = new FlexiblePortfolio.RecurringTransaction(ticker,
            start,
            dollarAmount, commission, days);
      }
      transactions.add(trans);
    }

    // Doing this prevents any transaction from being added if there is a single invalid ticker
    // Resetting this portfolio's transactions to be the transactions it had plus these new ones
    port.transactions = Stream.concat(port.transactions.stream(), transactions.stream())
        .collect(Collectors.toList());
  }

  // Overload the above method to allow for no end date to be specified. Just calls above with a
  // null end date, signalling this recurring strategy should have no end.
  @Override
  public void newDollarCostAverageStrategy(String portfolioName, Map<String, Double> percentages,
      LocalDate start, int days, double amount,
      double commission) throws IllegalArgumentException {
    // Calling the other function with a null end date will trigger the use of the indefinitely
    // recurring transaction.
    this.newDollarCostAverageStrategy(portfolioName, percentages, start, null, days, amount,
        commission);

  }

  @Override
  public Map<String, Double> getTickerMap(String portfolioName, LocalDate date)
      throws IllegalArgumentException {
    this.checkValidPort(portfolioName);
    Portfolio port = this.portfolios.get(portfolioName);
    return port.getTickerMap(date);
  }

  @Override
  public Map<String, Double> getValues(String portfolioName, LocalDate date)
      throws IllegalArgumentException {
    this.checkValidPort(portfolioName);
    Portfolio port = this.portfolios.get(portfolioName);
    return port.getValues(date);
  }

  @Override
  public double getPortValue(String portfolioName, LocalDate date) throws IllegalArgumentException {
    this.checkValidPort(portfolioName);
    Portfolio port = this.portfolios.get(portfolioName);
    return port.getPortValue(date);
  }

  @Override
  public double getCostBasis(String portfolioName, LocalDate date) throws IllegalArgumentException {
    this.checkValidPort(portfolioName);
    Portfolio port = this.portfolios.get(portfolioName);
    // Let's just round this to make sure we're not getting too precise here.
    DecimalFormat df = new DecimalFormat("0.00");
    return Double.parseDouble(df.format(port.getCostBasis(date)));
  }

  @Override
  public Map<String, Double> getPerformance(String portfolioName, LocalDate start, LocalDate end)
      throws IllegalArgumentException {
    this.checkValidPort(portfolioName);
    if (end.isBefore(start)) {
      throw new IllegalArgumentException("End date cannot be before start date!");
    }
    Map<String, Double> values = new LinkedHashMap<>();
    List<LocalDate> dateRange = start.datesUntil(end).collect(Collectors.toList());
    dateRange.add(end);
    Collections.sort(dateRange);

    Portfolio port = this.portfolios.get(portfolioName);

    // Range is less than or equal to thirty days, just return those dates
    if (dateRange.size() <= 30) {
      for (LocalDate date : dateRange) {
        Double value;
        try {
          value = port.getPortValue(date);
          values.put(date.toString(), port.getPortValue(date));
        } catch (IllegalArgumentException e) {
          // If that date is invalid, just skip it.
          continue;
        }
      }
      if (values.size() < 1) {
        throw new IllegalArgumentException("Date not valid!");
      }
      return values;
    }
    // Range is less than a year
    else if (dateRange.size() < 365 && dateRange.size() > 30) {
      int partitionSize = dateRange.size() / 20;
      List<LocalDate> filtered = IntStream.range(0, dateRange.size())
          .filter(n -> n % partitionSize == 0)
          .mapToObj(dateRange::get)
          .collect(Collectors.toList());
      for (LocalDate date : filtered) {
        Double value;
        // Try to get the value of portfolio at the date
        // If it's invalid, move back one day and try again
        // After four attempts just move on
        for (int i = 0; i < 4; ++i) {
          try {
            value = port.getPortValue(date);
            values.put(date.toString(), value);
            break;
          } catch (IllegalArgumentException e) {
            // If that date is invalid, subtract one day from it and try again
            filtered.set(filtered.indexOf(date), date.minusDays(1));
            date = date.minusDays(1);
          }
        }
      }
      return values;
    }
    // Over a year, going by month
    // All distinct month - year dates in the specified range
    List<YearMonth> months = dateRange.stream()
        .map(n -> YearMonth.from(n))
        .distinct()
        .collect(Collectors.toList());
    if (months.size() < 30) {
      for (YearMonth month : months) {
        LocalDate date = month.atEndOfMonth();

        Double value;

        // Try to get the value of portoflio at the date
        // If it's invalid, move back one day and try again
        // After four attempts just move on
        for (int i = 0; i < 4; ++i) {
          try {
            value = port.getPortValue(date);
            values.put(date.toString(), port.getPortValue(date));
            break;
          } catch (IllegalArgumentException e) {
            // If that date is invalid, subtract one day from it and try again
            date = date.minusDays(1);
          }
        }

        values.put(month.toString(), port.getPortValue(date));
      }
      return values;
    }
    // Final catch all, go by years
    List<Year> years = months.stream()
        .map(n -> Year.from(n))
        .distinct()
        .collect(Collectors.toList());
    int partitionSize = dateRange.size() / 30;
    List<Year> filtered = IntStream.range(0, years.size())
        .filter(n -> n % partitionSize == 0)
        .mapToObj(years::get)
        .collect(Collectors.toList());
    List<LocalDate> outputDates = new ArrayList<>();
    for (Year year : filtered) {
      Double value;
      LocalDate date = year.atDay(365);
      outputDates.add(date);
      // Try to get the value of portoflio at the date
      // If it's invalid, move backward one day and try again
      // After 30 attempts just move on
      for (int i = 0; i < 31; ++i) {
        try {
          value = port.getPortValue(date);
          values.put(year.toString(), port.getPortValue(date));
          break;
        } catch (IllegalArgumentException e) {
          // If that date is invalid, add one day to it and try again
          outputDates.set(filtered.indexOf(date), date.minusDays(1));
          date = date.plusDays(1);
        }
      }
    }
    return values;
  }

  @Override
  public String toString(String portfolioName) throws IllegalArgumentException {
    this.checkValidPort(portfolioName);
    return this.portfolios.get(portfolioName).toString();
  }

  @Override
  public boolean validTicker(String ticker) {
    if (this.validTickers.containsKey(ticker)) {
      return this.validTickers.get(ticker);
    }

    AlphaVantage api = new AlphaVantage();
    try {
      api.getStock(ticker);
    } catch (IllegalArgumentException e) {
      if (e.getMessage().equals("No price data found for " + ticker) ||
          e.getMessage().equals("Invalid Ticker")) {
        this.validTickers.put(ticker, false);
        return false;
      } else {
        throw new IllegalArgumentException(e.getMessage());
      }
    }
    this.validTickers.put(ticker, true);
    return true;
  }

  @Override
  public StockAPI getAPI() {
    return new AlphaVantage();
  }

  @Override
  public FileHandler getFileHandler() {
    return new StockIO();
  }

  // ------------------- Helpers --------------------

  /**
   * Check if we have this portfolio, throw exception if we don't.
   *
   * @param name Name of portfolio.
   * @throws IllegalArgumentException If we do not have a portfolio with that name.
   */
  private void checkValidPort(String name) throws IllegalArgumentException {
    if (!this.portFlexibilities.containsKey(name)) {
      throw new IllegalArgumentException("Portfolio with that name does not exist!");
    }
  }

  /**
   * For use with the strategies, checks the percentages for negative values, or a total not equal
   * to 100. Looks for invalid tickers, and checks commission fees to ensure they are non-negative.
   * Will throw the correct exception if any of these cases occur.
   *
   * @param percentages Map of tickers to percentage values.
   * @param commission  Commission fee.
   * @throws IllegalArgumentException If any of the cases above are detected.
   */
  private void checkValidPercentagesCommission(Map<String, Double> percentages,
      Double commission) throws IllegalArgumentException {
    // If there are any negative percentages
    if (!percentages.entrySet().stream().filter(entry -> entry.getValue() < 0.0)
        .collect(Collectors.toList())
        .isEmpty()) {
      throw new IllegalArgumentException("No percentage can be negative!");
    }

    Double totalPercentage = percentages.values().stream().mapToDouble(Double::doubleValue).sum();
    // Will cut off precision at two decimal places, rounding up at 5
    if (Math.abs(100.00 - totalPercentage) > .004) {
      throw new IllegalArgumentException("Total percentage must be equal to 100! Is " +
          totalPercentage);
    }
    if (commission < 0) {
      throw new IllegalArgumentException("Commission fee cannot be negative! Is " + commission);
    }
    for (String ticker : percentages.keySet()) {
      if (!this.validTicker(ticker)) {
        throw new IllegalArgumentException("Invalid ticker: " + ticker);
      }
    }
  }


  @Override
  public Set<String> getStocksOnDate(String portfolioName, LocalDate date)
      throws IllegalArgumentException {
    return this.getTickerMap(portfolioName, date).keySet();
  }

  @Override
  public void reBalance(Map<String, Double> stockMap, String portfolioName, LocalDate date)
      throws IllegalArgumentException {
    this.checkValidPort(portfolioName);
    if (!this.portFlexibilities.get(portfolioName)) {
      throw new IllegalArgumentException("Portfolio not flexible, " +
          "cannot create fixed amount strategy!");
    }
    // Check other inputs
    this.checkValidPercentagesCommission(stockMap, 0.0);

    FlexiblePortfolio port = (FlexiblePortfolio) this.portfolios.get(portfolioName);

    Map<String, Double> stockQuan = this.getTickerMap(portfolioName, date);
    Map<String, Double> stockPrice = port.getValues(date);

    double portfolioValue = port.getPortValue(date);

    List<FlexiblePortfolio.Transaction> transactions = new ArrayList<>();

    for (String ticker : stockMap.keySet()) {
      double percentage = stockMap.get(ticker);

      double amount = percentage * portfolioValue / 100;

      double remainderAmount = amount - stockPrice.get(ticker) * stockQuan.get(ticker);

      port.updateStock(ticker); // Make sure to update cache

      if (remainderAmount != 0) {
        FlexiblePortfolio.Transaction trans = new FlexiblePortfolio.Transaction(ticker, date,
            remainderAmount, 0.0);
        trans.updateTransaction(stockPrice.get(ticker));
        transactions.add(trans);
      }
    }

    // Doing this prevents any transaction from being added if there is a single invalid ticker
    // Resetting this portfolio's transactions to be the transactions it had plus these new ones
    port.transactions = Stream.concat(port.transactions.stream(), transactions.stream())
        .collect(Collectors.toList());


  }


}
