package model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a flexible portfolio.
 */
public class FlexiblePortfolio extends AbstractPortfolio {

  List<Transaction> transactions;

  public FlexiblePortfolio(String portfolioName) {
    super(portfolioName);
    this.transactions = new ArrayList<>();
  }

  /**
   * Sell a stock from the portfolio, removing it.
   *
   * @param ticker     Ticker of stock to sell.
   * @param numStocks  Number of that stock to sell.
   * @param date       Date to sell the stocks on.
   * @param commission Commission for this sale.
   * @throws IllegalArgumentException If the date is invalid, not enough stocks are owned at that
   *                                  date, or that ticker is not found.
   */
  void sellStock(String ticker, int numStocks, LocalDate date, double commission)
      throws IllegalArgumentException {
    if (!super.validDate(date)) {
      throw new IllegalArgumentException("Invalid date! " + date +
          " must not be a weekend, or in the future.");
    }
    if (numStocks <= 0 || commission < 0) {
      throw new IllegalArgumentException(
          "Cannot sell 0 or negative stocks, and commission fee cannot be negative!");
    }
    // Sale represents a negative amount of stocks added
    Transaction proposedTransaction = new Transaction(ticker, date, -1 * numStocks, commission);
    if (!this.validSale(proposedTransaction)) {
      throw new IllegalArgumentException("Not enough stocks owned at " + date +
          "! Buy more before then, or sell less.");
    } else {
      // Will catch holidays and update cache.
      super.updateStock(ticker);
      this.transactions.add(proposedTransaction);
    }
  }


  /**
   * Add a stock to this portfolio.
   *
   * @param ticker     Ticker of stock to add.
   * @param numStocks  Number of stocks to add.
   * @param date       Date to purchase stocks on, must be most recent date for inflexible
   *                   portfolios.
   * @param commission Commission amount, for the cost basis of the portfolio.
   * @throws IllegalArgumentException Ticker is not found, or invalid date (in the future, or not
   *                                  today for inflexible portfolio.) For inflexible. if date is
   *                                  today but no data is found for today, most recent date will be
   *                                  taken.
   */
  public void addStock(String ticker, int numStocks, LocalDate date, double commission)
      throws IllegalArgumentException {
    if (numStocks <= 0 || commission < 0) {
      throw new IllegalArgumentException(
          "Cannot buy 0 or negative stocks, and commission fee cannot be negative!");
    }
    if (!super.validDate(date)) {
      throw new IllegalArgumentException("Invalid date! " + date +
          " must not be a weekend, or in the future.");
    }
    // Update the cache. This will catch holidays as well.
    super.updateStock(ticker);
    transactions.add(new Transaction(ticker, date, numStocks, commission));
  }

  @Override
  public Map<String, Double> getTickerMap(LocalDate date) {
    Map<String, Double> tickers = new HashMap<>();

    if (!super.validDate(date)) {
      throw new IllegalArgumentException("Invalid date! " + date +
          " must not be a weekend, or in the future.");
    }
    List<Transaction> validTrans = getTransactions(date);

    for (Transaction trans : validTrans) {
      Double oldVal = 0.0;
      if (tickers.containsKey(trans.ticker)) {
        oldVal = tickers.get(trans.ticker);
      }
      Double newVal = oldVal + trans.stocks;
      if (newVal == 0) {
        tickers.remove(trans.ticker); //If we go down to zero stocks owned,
        // remove it from map.
      } else {
        tickers.put(trans.ticker, (oldVal + trans.stocks));
      }
    }

    return tickers;
  }

  @Override
  public Double getShares(String ticker, LocalDate date) throws IllegalArgumentException {
    Map<String, Double> tickers = this.getTickerMap(date);
    if (tickers.containsKey(ticker)) {
      return tickers.get(ticker);
    } else {
      return 0.0;
    }
  }

  @Override
  public double getCostBasis(LocalDate date) {

    Double totalBasis = 0.0;
    List<Transaction> validTrans = getTransactions(date);

    for (Transaction trans : validTrans) {
      // If this transaction is a sale
      if (trans.stocks != null && trans.stocks < 0) {
        continue; // skip it
      }
      totalBasis += trans.value + trans.commission;
    }

    return totalBasis;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("{\n");

    for (Transaction trans : this.transactions) {

      if (trans.stocks == null) {
        continue;
      }

      builder.append("\t{\n");
      builder.append("\tticker: " + trans.ticker + ",\n");
      builder.append("\tstocks: " + trans.stocks.intValue() + ",\n");
      builder.append("\tdate: " + trans.date + ",\n");
      builder.append("\tcommission: " + trans.commission + "\n");
      builder.append("\t}\n");
    }

    builder.append("}");
    return builder.toString();
  }

  @Override
  public boolean isFlexible() {
    return true;
  }

  //---------------- Helpers ----------------

  /**
   * Determine if this transaction is valid (we have enough stocks to sell at that date).
   *
   * @param proposedTransaction Transaction to validate.
   * @return Boolean if valid.
   */
  private boolean validSale(Transaction proposedTransaction) {
    Double totalStocks = this.getShares(proposedTransaction.ticker, proposedTransaction.date);

    return totalStocks >= Math.abs(proposedTransaction.stocks);
  }

  /**
   * Get all the transactions that are valid up to a date. Should also update ongoing strategies if
   * there is valid data. Will apply recurring strategies, so that they are normal transactions in
   * the returned list.
   *
   * @param date Date to look at.
   * @return List of the transactions that are valid up to that date.
   */
  private List<Transaction> getTransactions(LocalDate date) {
    List<Transaction> validTrans = new ArrayList<>();
    List<String> tickersSeen = new ArrayList<>();

    // Go through all transaction in this portfolio
    for (Transaction trans : this.transactions) {
      // If this transaction is after the query date, skip it
      if (trans.date.isAfter(date)) {
        continue;
      }

      // If this transaction is in the future (and not recurring), we don't need to update any
      // information, so just
      // add it to the list of valid transactions
      if (trans.date.isAfter(LocalDate.now(ZoneId.systemDefault())) && !trans.isRecurring()) {
        validTrans.add(trans);
        continue;
      }

      // If this is a past transaction, and we haven't updated the cache yet for that stock, do
      // so now
      if (!trans.date.isAfter(LocalDate.now(ZoneId.systemDefault())) &&
          !tickersSeen.contains(trans.ticker)) {
        try {
          super.stocks.get(trans.ticker).getValue(trans.date);
        } catch (IllegalArgumentException e) {
          tickersSeen.add(trans.ticker);
          super.updateStock(trans.ticker);
        }
      }

      // If recurring, add all transactions up to this date
      // This will also update any of these individual transactions that are before the current
      // date, with the number of stocks to be bought.
      if (trans.isRecurring()) {
        // Move forward with each transaction in this recurring transaction
        LocalDate transDate = trans.date;
        RecurringTransaction recur = (RecurringTransaction) trans;
        // Days to jump forward for next transaction
        int interval = recur.interval;

        // As long as that date is before, or equal to the date we are querying
        while (transDate.isBefore(date) || transDate.isEqual(date)) {
          // If this recurring transaction has an end date, and we are past it, do not add
          // anymore transactions based on this recurring transaction
          if (recur.endDate != null && transDate.isAfter(recur.endDate)) {
            break;
          }
          // Create a single occurrence of the recurring transaction to add to the list
          Transaction newTrans = new Transaction(trans.ticker, transDate, trans.value,
              trans.commission);

          // If we can, get the stock amount for this transaction
          // If the date has no data (weekend or holiday) move forward a day and try again
          // Repeat until date is in the future, or data is found
          Double stockValue = 0.0;
          while (newTrans.date.isBefore(LocalDate.now(ZoneId.systemDefault()))) {
            try {
              stockValue = super.stocks.get(newTrans.ticker).getValue(newTrans.date).get("close");
              break;
            } catch (IllegalArgumentException e) {
              // That was an invalid date, so move forward a day and try again
              newTrans = new Transaction(newTrans.ticker, newTrans.date.plusDays(1),
                  newTrans.value,
                  newTrans.commission);
            }
          }
          newTrans.updateTransaction(stockValue);

          validTrans.add(newTrans);

          // If, in the process of finding a valid date for this transaction, we ran into the
          // next iteration, then skip that iteration
          if (newTrans.date.isAfter(transDate.plusDays(interval)) ||
              newTrans.date.isEqual(transDate.plusDays(interval))) {
            transDate = transDate.plusDays(2L * interval);
          } else {
            transDate = transDate.plusDays(interval);
          }
        }
      }

      // For non-recurring transactions that are in the past, get their stock value and update
      // that transaction
      if (!trans.isRecurring() && !validTrans.contains(trans)) {
        Double stockValue = 0.0;
        Transaction newTrans = trans;
        LocalDate tempDate = newTrans.date;

        // Try to move to a date with valid data
        while (tempDate.isBefore(LocalDate.now(ZoneId.systemDefault()))) {
          try {
            stockValue = super.stocks.get(newTrans.ticker).getValue(newTrans.date).get("close");
          } catch (IllegalArgumentException e) {
            // That was an invalid date, so move forward a day and try again
            newTrans = new Transaction(trans.ticker, newTrans.date.plusDays(1),
                trans.stocks.intValue(),
                trans.commission);
            tempDate = tempDate.plusDays(1);
            continue;
          }
          // At this point we have a new date for this transaction, and it is valid
          // We also have the value of this stock at this date
          // So, we need to figure out whether this is a normal buy/sell and set the total value
          // for the transaction, or if it is a fixed cost purchase, and we need to update the
          // number of stocks being bought
          if (newTrans.stocks == null) {
            // This is fixed cost, call updateTransaction
            newTrans.updateTransaction(stockValue);
          } else {
            // This is a normal purchase, set its value directly based on the number of stocks
            // to buy
            newTrans.value = newTrans.stocks * stockValue;
          }
          break;
        }
        if (!newTrans.date.isAfter(date)) {
          validTrans.add(newTrans);
        }
      }
    }
    return validTrans;
  }


  static class RecurringTransaction extends Transaction {

    int interval;
    LocalDate endDate;

    /**
     * Create a new recurring transaction.
     *
     * @param ticker      Ticker to buy/sell.
     * @param startDate   Date to start the transactions.
     * @param amountToBuy Amount of money worth of stock to buy.
     * @param commission  Commission fee for each transaction.
     * @param interval    Interval of days between purchases.
     */
    RecurringTransaction(String ticker, LocalDate startDate, Double amountToBuy,
        Double commission, int interval) {
      super(ticker, startDate, amountToBuy, commission);
      this.interval = interval;
    }

    RecurringTransaction(String ticker, LocalDate startDate, LocalDate endDate,
        Double amountToBuy, Double commission, int interval) {
      super(ticker, startDate, amountToBuy, commission);
      this.endDate = endDate;
      this.interval = interval;
    }

    @Override
    boolean isRecurring() {
      return true;
    }

  }

  /**
   * Storage class to keep track of buying and selling stocks at specific dates.
   */
  static class Transaction {

    String ticker;
    LocalDate date;
    Double stocks; // Number of stocks buying in this transaction
    double commission;
    Double value; // Total value for this transaction

    /**
     * Constructor for buying or selling stocks.
     *
     * @param ticker     Ticker to buy or sell.
     * @param date       Date for the transaction.
     * @param stocks     Int number of stocks to buy/sell.
     * @param commission Commission fee.
     */
    Transaction(String ticker, LocalDate date, int stocks, double commission) {
      this.date = date;
      this.stocks = Double.valueOf(stocks);
      this.ticker = ticker;
      this.commission = commission;
    }

    /**
     * Constructor for investment strategies.
     *
     * @param ticker      Ticker for transaction.
     * @param date        Date for transaction.
     * @param amountToBuy Double amount to spend on this stock.
     * @param commission  Commission fee.
     */
    Transaction(String ticker, LocalDate date, Double amountToBuy, Double commission) {
      this.date = date;
      this.ticker = ticker;
      this.value = amountToBuy;
      this.commission = commission;
    }

    /**
     * Update this transaction, for strategies will calculate the number of stocks this transaction
     * should have.
     *
     * @param stockValue Value of the stock on the day of this transaction.
     */
    void updateTransaction(Double stockValue) {
      this.stocks = this.value / stockValue;
    }

    /**
     * Get whether this transaction is recurring.
     *
     * @return Whether this transaction is recurring.
     */
    boolean isRecurring() {
      return false;
    }

    @Override
    public String toString() {
      return "" + this.ticker + " " + this.stocks + " " + this.value + "\n";
    }
  }
}
