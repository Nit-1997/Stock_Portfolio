package model;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

public interface UserFlex extends User{

  boolean addPortfolio(String name, Map<String, Map<String, Double>> stocksMap);

  Map<String, Double> getPortfolioSummary(String name, String date);

  String getPortfolioCreationDate(String name);

  Map<String, SimpleEntry<String, Double> > getPortfolioState(String name);

  boolean isBeforeDate(String firstDate, String secondDate);

  boolean buyStockForPortfolio(String portfolioName, SimpleEntry<String,SimpleEntry<String,Double>> newStock);

  boolean sellStockFromPortfolio(String portfolioName, SimpleEntry<String,SimpleEntry<String,Double>> newStock);

}
