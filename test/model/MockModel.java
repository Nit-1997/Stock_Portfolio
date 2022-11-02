package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MockModel implements User{

  private final StringBuilder log;

  public MockModel(StringBuilder log){
    this.log=log;
  }

  @Override
  public boolean addPortfolio(String name, Map<String, Double> stocks){
    log.append("\nInput "+name);
    log.append("\n stock map:");
    for(String ticker : stocks.keySet()){
      log.append(ticker+"->"+stocks.get(ticker));
    }
    return true;
  }

  @Override
  public Set<String> getPortfolios() {
    log.append("get Portfolios list");
    Set<String> pList = new HashSet<>();
    pList.add("tech");
    return pList;
  }

  @Override
  public Map<String, Double> getPortfolioSummary(String name) {
    log.append("\ngetting portfolio summary for "+name);
    Map<String, Double> map = new HashMap<>();
    map.put("MSFT",10.0);
    map.put("AAPL",12.0);
    return map;
  }

  @Override
  public Map<String, List<Double>> getPortfolioDetailed(String name, String date) {
    log.append("\ngetting portfolio detail for "+name+" for the date "+date);
    Map<String, List<Double>> map = new HashMap<>();
    List<Double> list = new ArrayList<>();
    list.add(5.0);
    list.add(93.5);
    list.add(95.0);
    list.add(1.5);
    map.put("AAPL",list);
    return map;
  }

  @Override
  public Double getPortfolioValue(String name, String date){
    log.append("\ngetting portfolio value for "+name+" for the date "+date);
    return 0.0;
  }

  @Override
  public Double getPortfolioPnL(String name, String date)  {
    log.append("\ngetting portfolio performance for "+name+" for the date "+date);
    return 0.0;
  }

  @Override
  public boolean isUniqueName(String name) {
    log.append("unique name checker");
    return (name.equals("techLoad"))?false: true;
  }

  @Override
  public boolean dateChecker(String dateStr) {
    log.append("checking the date "+dateStr);
    return true;
  }

  @Override
  public Set<String> getStockList() {
    log.append("\nget stock list");
    return null;
  }

  @Override
  public boolean isValidStock(String name) {
    log.append("Name of ticker is "+name);
    return name.equals("AAPL");
  }

  @Override
  public void cleanStockDirectory() {
    log.append("clean stock directory");
  }
}
