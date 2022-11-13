package model;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FlexMockModel implements UserFlex{

  private final StringBuilder log;

  public FlexMockModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public boolean addPortfolio(String name,
      Map<String, Map<String, SimpleEntry<Double, Double>>> stocksMap) throws IOException {
    log.append("\nInput " + name);
    log.append("\n stock map: \n");
    for (String ticker : stocksMap.keySet()) {
      for(String date : stocksMap.get(ticker).keySet()){
        log.append(ticker+"->"+date+"->"+stocksMap.get(ticker).get(date).getKey()+","+stocksMap.get(ticker).get(date).getValue());
      }
      log.append("\n");
      if(ticker.equals("META")) throw new IOException("Asked stock didn't exist on that date");
    }

    return true;
  }

  @Override
  public Map<String, Double> getPortfolioSummary(String name, String date) {
    log.append("\ngetting portfolio summary for " + name + " for the date: "+date);
    Map<String, Double> map = new HashMap<>();
    map.put("MSFT", 10.0);
    map.put("AAPL", 12.0);
    return map;
  }

  @Override
  public String getPortfolioCreationDate(String name) {
    log.append("get portfolio creation date for "+name);
    return "2011-02-15";
  }

  @Override
  public Map<String, SimpleEntry<String, Double>> getPortfolioState(String name) {
    log.append("get portfolio state for "+name);
    Map<String, SimpleEntry<String, Double>> latestState= new HashMap<>();
    latestState.put("MSFT",new SimpleEntry<>("2013-06-18",10.0));
    latestState.put("AAPL",new SimpleEntry<>("2016-03-29",17.0));
    return latestState;
  }

  @Override
  public boolean isBeforeDate(String firstDate, String secondDate) {
    log.append(firstDate+" is before date "+secondDate);
    return false;
  }

  @Override
  public boolean transactionForPortfolio(String portfolioName,
      SimpleEntry<String, SimpleEntry<String, SimpleEntry<Double, Double>>> newStock)
      throws IOException {
    log.append("transaction for portfolio "+portfolioName+":\n");
    log.append(newStock.getKey()+" "+newStock.getValue().getKey()+" "
        +newStock.getValue().getValue().getKey()+" "+newStock.getValue().getValue().getValue());

    return true;
  }

  @Override
  public Double getCostBasis(String portfolioName, String date) {
    log.append("Cost basis of portfolio "+portfolioName+" for the date "+date);
    return 0.0;
  }

  @Override
  public SimpleEntry<List<String>, SimpleEntry<List<Integer>, Integer>> getGraphData(String date1,
      String date2, String portfolioName) {
    log.append("get graph data for "+portfolioName+" from "+date1+" to date "+date2);

    List<String> labels = new ArrayList<>(Arrays.asList(
        new String[]{"JAN 2022", "FEB 2022", "MAR 2022", "APR 2022", "MAY 2022"}));

    List<Integer> starPoints = new ArrayList<>(Arrays.asList(new Integer[]{7,9,16,28,5}));
    return new SimpleEntry<>(labels,new SimpleEntry<>(starPoints,235));
  }

  @Override
  public Set<String> getPortfolios() {
    log.append("get Portfolios list");
    Set<String> pList = new HashSet<>();
    pList.add("flexTest");
    return pList;
  }

  public Double getPortfolioValue(String name, String date) {
    log.append("\ngetting portfolio value for " + name + " for the date " + date);
    return 0.0;
  }

  @Override
  public boolean isUniqueName(String name) {
    log.append("unique name checker");
    return name.equals("controllerTest");
  }

  @Override
  public boolean dateChecker(String dateStr) {
    log.append("checking the date " + dateStr);
    return true;
  }

  @Override
  public Set<String> getStockList() {
    log.append("\nget stock list");
    return null;
  }

  @Override
  public boolean isValidStock(String name) {
    log.append("Name of ticker is " + name);
    return true;
  }

  @Override
  public void cleanStockDirectory() {
    log.append("clean stock directory");
  }
}
