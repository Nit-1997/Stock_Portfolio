package model;

import java.io.IOException;

/**
 * Interface to abstract Stock Order functionality.
 * Stock Order contains {Stock , qty}
 */
public interface StockOrder {

  /**
   * gets the current market value of the stock order.
   *
   * @return current market price of the order
   * @throws IOException can occur while reading data dump
   */
  public Double getCurrentOrderValue() throws IOException;

  /**
   * gets the buy value of this stock order.
   *
   * @return buy price of the order
   */
  public Double getInitialOrderValue();

  /**
   * gets the value of this stock order on a given date.
   *
   * @param date date on which we want the order value
   * @return value of the order on  a given date
   * @throws IOException can occur while reading data dump
   */
  public Double getOrderValueOnDate(String date) throws IOException;

  /**
   * Gets the total PnL(profit/loss) of a given stock order.
   *
   * @return profit / loss ( +ve means profit , -ve means loss)
   * @throws IOException can occur while reading data dump
   */
  public Double getOrderPnL() throws IOException;


  /**
   * Used to fetch the stock object of each order.
   *
   * @return stock object inside the order
   */
  public Stock getStock();

  /**
   * Used to fetch the quantity of each order.
   *
   * @return quantity inside the order
   */
  public double getQuantity();
}
