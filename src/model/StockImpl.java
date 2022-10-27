package model;

final class StockImpl implements Stock {
  private final String ticker;
  private final Double buyPrice;

  /**
   * Constructor that creates a Stock object.
   *
   * @param ticker ticker name
   */
  public StockImpl(String ticker) {
    this.ticker = ticker;
    this.buyPrice = getCurrentPrice();
  }

  //TODO : Handle Saturdays and Sundays
  @Override
  public Double getCurrentPrice() {
    //TODO use this.ticker to fetch data from api
    return -1.1;
  }

  @Override
  public Double getPnL() {
    return null;
  }

  @Override
  public Double getBuyPrice() {
    return buyPrice;
  }
}
