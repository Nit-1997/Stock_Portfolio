package model;

import java.io.IOException;

public interface StockOrder {

  public Double getCurrentOrderValue() throws IOException;

  public Double getInitialOrderValue();

  public Double getOrderValueOnDate(String date) throws IOException;

  public Double getOrderPnL() throws IOException;


  public Stock getStock();

  public double getQuantity();
}
