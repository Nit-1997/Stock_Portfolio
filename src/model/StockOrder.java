package model;

import java.io.IOException;

public interface StockOrder {

  public double getCurrentOrderValue();

  public double getInitialOrderValue();

  public double getOrderValueOnDate(String date);

  public double getOrderPnL() throws IOException;

  public double getOrderValueByDate(String date);

  public Stock getStock();

  public double getQuantity();
}
