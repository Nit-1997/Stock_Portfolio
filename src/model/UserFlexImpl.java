package model;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserFlexImpl implements UserFlex{

  private final Map<String, PortfolioFlexImpl> portfolioMap;

  public UserFlexImpl(){
    portfolioMap=new HashMap<>();
  }

  @Override
  public Set<String> getPortfolios() {
    return null;
  }

  @Override
  public Double getPortfolioValue(String name, String date) {
    return null;
  }

  @Override
  public Double getPortfolioPnL(String name, String date) {
    return null;
  }

  @Override
  public boolean isUniqueName(String name) {
    return false;
  }

  @Override
  public boolean dateChecker(String dateStr) {
    return false;
  }

  @Override
  public Set<String> getStockList() {
    return null;
  }

  @Override
  public boolean isValidStock(String name) {
    return false;
  }

  @Override
  public void cleanStockDirectory() {

  }

  @Override
  public boolean addPortfolio(String name, Map<String, Map<String, Double>> stocksMap) {
    return false;
  }

  @Override
  public Map<String, Double> getPortfolioSummary(String name, String date) {
    return null;
  }

  @Override
  public String getPortfolioCreationDate(String name) {
    return null;
  }

  @Override
  public Map<String, SimpleEntry<String, Double>> getPortfolioState(String name) {
    return null;
  }

  @Override
  public boolean isBeforeDate(String firstDate, String secondDate) {
    return false;
  }

  @Override
  public boolean buyStockForPortfolio(String portfolioName,
      SimpleEntry<String, SimpleEntry<String, Double>> newStock) {
    return false;
  }

  @Override
  public boolean sellStockFromPortfolio(String portfolioName,
      SimpleEntry<String, SimpleEntry<String, Double>> newStock) {
    return false;
  }
}
