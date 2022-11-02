package model;

/**
 * A POJO class for cleaner parsing of data.
 */
public final class PortfolioDetailedPojo {
  private final String ticker;
  private final double buyPrice;
  private final double qty;
  private final double askedPrice;
  private final double pnl;

  /**
   * Constructor to initialise variables of the class.
   *
   * @param ticker stock name
   * @param buyPrice stock buying price
   * @param qty stock quantity
   * @param askedPrice price of that stock
   * @param pnl performance of that stock for that asked date
   */
  public PortfolioDetailedPojo(String ticker,
                               double buyPrice,
                               double qty,
                               double askedPrice,
                               double pnl) {
    this.ticker = ticker;
    this.buyPrice = buyPrice;
    this.qty = qty;
    this.askedPrice = askedPrice;
    this.pnl = pnl;
  }

  public double getBuyPrice() {
    return buyPrice;
  }

  public double getQty() {
    return qty;
  }

  public double getAskedPrice() {
    return askedPrice;
  }

  public double getPnl() {
    return pnl;
  }

  public String getTicker() {
    return ticker;
  }
}
