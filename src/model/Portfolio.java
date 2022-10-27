package model;

import java.util.HashMap;

public interface Portfolio {
  public Double getPortfolioPnL();

  public void retrievePortfolioSummary(String name);

  public void retrievePortfolioDetailed(String name);

  public Double getCurrentPrice();

  public Double getInitialValue();
}
