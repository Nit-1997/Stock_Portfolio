package model;

import constants.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import utils.Utils;

public class UserFlexImpl extends AbstractUser implements UserFlex{

  private final Map<String, PortfolioFlex> portfolioMap;

  public UserFlexImpl() {
    portfolioMap = new HashMap<>();
    Utils.clearStockDirectory();
    try {
      Constants.STOCK_NAMES = Utils.loadStockNames("stocks", "stocks_list.csv");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    String portfolioDirectory = Paths.get("portfolios" + File.separator + "flex").toAbsolutePath().toString();
    File f = new File(portfolioDirectory);
    if (!f.exists()) {
      f.mkdirs();
      return;
    }
    String[] files = f.list((f1, name) -> name.endsWith(".csv"));

    for (int i = 0; i < files.length; i++) {
      files[i] = files[i].substring(0, files[i].indexOf(".csv"));
    }

    for (String file : files) {
      portfolioMap.put(file, null);
    }
  }

  @Override
  public Set<String> getPortfolios() {
    return this.portfolioMap.keySet();
  }

  @Override
  public Double getPortfolioValue(String name, String date) {
    try {
      if (!Utils.dateChecker(date)) {
        throw new Exception("wrong arguments");
      }
      if (portfolioMap.get(name) == null) {
        portfolioMap.put(name, new PortfolioFlexImpl(name));
      }
      Double portfolioValue;
      String currentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
      if (date.equals(currentDate)) {
        portfolioValue = portfolioMap.get(name).getCurrentValue();
      } else {
        portfolioValue = portfolioMap.get(name).getValueOnDate(date);
      }
      return portfolioValue;
    } catch (IOException e) {
      return null;
    } catch (Exception e) {
      return null;
    }
  }


  @Override
  public boolean isUniqueName(String name) {
    return !this.portfolioMap.containsKey(name);
  }



  @Override
  public boolean addPortfolio(String name, Map<String, Map<String, SimpleEntry<Double, Double>>> stocksMap) {
    try {
      this.portfolioMap.put(name, new PortfolioFlexImpl(stocksMap, name));
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public Map<String, Double> getPortfolioSummary(String name, String date) {
    try {
      if (portfolioMap.get(name) == null) {
        portfolioMap.put(name, new PortfolioFlexImpl(name));
      }
      return portfolioMap.get(name).getPortfolioSummary(date);
    } catch (FileNotFoundException e) {
      return null;
    } catch (IOException e) {
      return null;
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public String getPortfolioCreationDate(String portfolioName) {
    try{
      if (portfolioMap.get(portfolioName) == null) {
        portfolioMap.put(portfolioName, new PortfolioFlexImpl(portfolioName));
      }
      return this.portfolioMap.get(portfolioName).getCreationDate();
    }catch(Exception e){
      System.out.println(e.getMessage());
      return null;
    }

  }

  @Override
  public Map<String, SimpleEntry<String, Double>> getPortfolioState(String portfolioName) {
    try {
      if (portfolioMap.get(portfolioName) == null) {
        portfolioMap.put(portfolioName, new PortfolioFlexImpl(portfolioName));
      }
      return portfolioMap.get(portfolioName).getLatestState();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  @Override
  public boolean isBeforeDate(String firstDate, String secondDate) {
    try {
      return Utils.compareDates(firstDate,secondDate)<0;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean transactionForPortfolio(String portfolioName,
                                      SimpleEntry<String, SimpleEntry<String, SimpleEntry<Double, Double>>> newStock) {
    try {
      if (portfolioMap.get(portfolioName) == null) {
        portfolioMap.put(portfolioName, new PortfolioFlexImpl(portfolioName));
      }
      portfolioMap.get(portfolioName).addStock(newStock);
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }


  @Override
  public Double getCostBasis(String portfolioName, String date){
    try {
      return this.portfolioMap.get(portfolioName).getCostBasis(date);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  @Override
  public SimpleEntry<List<String>,SimpleEntry<List<Integer>,Integer>> getGraphData(String date1, String date2, String portfolioName){
    SimpleEntry<List<String>,List<Double>> data = null;
    try {
      if (portfolioMap.get(portfolioName) == null) {
        portfolioMap.put(portfolioName, new PortfolioFlexImpl(portfolioName));
      }
      data = portfolioMap.get(portfolioName).getPerfDataOverTime(date1,date2);
    } catch (Exception e) {
      System.out.println("error from portfolio");
      return null;
    }
    if(data==null){
      System.out.println("null from portfolio");
      return null;
    }
    List<String> labels = data.getKey();
    List<Double> dataPoints = data.getValue();
    Double max=Collections.max(dataPoints);

    int scale = (int)(max/50);
    List<Integer> starPoints = new ArrayList<>();
    for(Double dataPoint : dataPoints) starPoints.add((int) (dataPoint/scale));

    return new SimpleEntry<>(labels,new SimpleEntry<>(starPoints,scale));


  }





}
