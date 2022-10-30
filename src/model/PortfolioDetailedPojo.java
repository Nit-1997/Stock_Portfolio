package model;

public final class PortfolioDetailedPojo {
  private final String ticker;
  private final double buyPrice;
  private final double qty;
  private final double currentPrice;
  private final double pnl;

  public PortfolioDetailedPojo(String ticker,
                               double buyPrice,
                               double qty,
                               double currentPrice,
                               double pnl) {
    this.ticker = ticker;
    this.buyPrice = buyPrice;
    this.qty = qty;
    this.currentPrice = currentPrice;
    this.pnl = pnl;
  }

  public double getBuyPrice() {
    return buyPrice;
  }

  public double getQty() {
    return qty;
  }

  public double getCurrentPrice() {
    return currentPrice;
  }

  public double getPnl() {
    return pnl;
  }

  public String getTicker() {
    return ticker;
  }
}
