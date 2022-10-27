package model;

final class Stock {
  private final String ticker;
  private final Double buyPrice;

  Stock(String ticker) {
    this.ticker = ticker;
    this.buyPrice = getCurrentPrice();
  }

  /**
   * Fetch the current price of the ticker name.
   *
   * @return Stock Price
   * Use the close value from api response
   */
  //TODO : Handle Saturdays and Sundays
  Double getCurrentPrice() {
    //TODO use this.ticker to fetch data from api
    return -1.1;
  }


  /**
   * Returns the PNL of this stock at currentDate
   * @return currPrice - initPrice
   */
  Double getPnL(){
     return null;
  }


  /**
   * Get the price on date of purchase
   * @return Init Stock price
   */
  Double getBuyPrice() {
    return buyPrice;
  }
}
