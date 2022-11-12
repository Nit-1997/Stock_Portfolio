package model;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Utils;

public class PortfolioFlexImpl implements PortfolioFlex {

  private final String name;
  private final List<StockOrder> stockOrders;
  private String creationDate;

  /**
   * Creator constructor.
   *
   * @param stocksMap { ticker : { date : qty, commission fee} }
   * @param name      name of the portfolio
   * @throws Exception while reading/writing data dump
   */
  public PortfolioFlexImpl(Map<String, Map<String, SimpleEntry<Double, Double>>> stocksMap, String name) throws Exception {
    if (stocksMap == null || name == null) {
      throw new IllegalArgumentException("Null arguments to portfolio constructor");
    }
    this.stockOrders = new ArrayList<>();
    this.name = name;

    String portfolioCreationDate = null;

    for (String key : stocksMap.keySet()) {
      if (!Utils.dataExists(key, "stock_data")) {
        Utils.loadStockData(key, "stock_data");
      }
      for (String k : stocksMap.get(key).keySet()) {
        if (portfolioCreationDate == null) {
          portfolioCreationDate = k;
        } else {
          int comparison = Utils.compareDates(portfolioCreationDate, k);
          if (comparison > 0) {
            portfolioCreationDate = k;
          }
        }
        this.stockOrders.add(new StockOrderImpl(key, stocksMap.get(key).get(k).getKey(), k,stocksMap.get(key).get(k).getValue()));
      }
    }
    this.creationDate = portfolioCreationDate;

    Utils.saveToFile(this.name, this.stockOrders, "portfolios" + File.separator + "flex");

  }

  /**
   * Retreiver Constructor.
   *
   * @param portfolioName name of the portfolio
   * @throws Exception while reading/writing data dump
   */
  public PortfolioFlexImpl(String portfolioName) throws Exception {
    List<StockOrder> tempStockOrders =  Utils.loadPortfolioData(portfolioName, "portfolios" + File.separator + "flex");
    if(!Utils.FlexPortfolioValidator(tempStockOrders)) this.stockOrders=null;
    else this.stockOrders=tempStockOrders;
    this.name = portfolioName;
    if (this.stockOrders == null) {
      return;
    }
    String portfolioCreationDate = null;
    for (StockOrder s : this.stockOrders) {
      if (!Utils.dataExists(s.getStock().getStockTickerName().toUpperCase(), "stock_data")) {
        Utils.loadStockData(s.getStock().getStockTickerName().toUpperCase(), "stock_data");
      }
      if (portfolioCreationDate == null) {
        portfolioCreationDate = s.getStock().getBuyDate();
      } else {
        int comparison = Utils.compareDates(portfolioCreationDate, s.getStock().getBuyDate());
        if (comparison > 0) {
          portfolioCreationDate = s.getStock().getBuyDate();
        }
      }
    }
    this.creationDate = portfolioCreationDate;
  }

  @Override
  public String getCreationDate() {
    return this.creationDate;
  }

  @Override
  public Map<String, SimpleEntry<String, Double>> getLatestState() throws Exception {

    Map<String, SimpleEntry<String, Double>> stateMap = new HashMap<>();

    for (StockOrder s : this.stockOrders) {
      String tickerName = s.getStock().getStockTickerName();
      String date = s.getStock().getBuyDate();
      double qty = s.getQuantity();
      if (stateMap.containsKey(tickerName)) {
        String lastTransactionDate = stateMap.get(tickerName).getKey();
        double newQty = qty + stateMap.get(tickerName).getValue();
        int comparison = Utils.compareDates(lastTransactionDate, date);
        if (comparison < 0) {
          lastTransactionDate = date;
        }
        SimpleEntry<String, Double> updatedEntry = new SimpleEntry<>(lastTransactionDate, newQty);
        stateMap.put(tickerName, updatedEntry);
      } else {
        stateMap.put(tickerName, new SimpleEntry<>(date, qty));
      }
    }

    return stateMap;
  }

  @Override
  public void addStock(SimpleEntry<String, SimpleEntry<String, SimpleEntry<Double, Double>>> newEntry) throws Exception {
    if (!Utils.dataExists(newEntry.getKey().toUpperCase(), "stock_data")) {
      Utils.loadStockData(newEntry.getKey().toUpperCase(), "stock_data");
    }
    StockOrder newOrder = new StockOrderImpl(newEntry.getKey(), newEntry.getValue().getValue().getKey(), newEntry.getValue().getKey(),newEntry.getValue().getValue().getValue());
    this.stockOrders.add(newOrder);
    Utils.saveToFile(this.name, this.stockOrders, "portfolios" + File.separator + "flex");
  }

  @Override
  public void sellStock(SimpleEntry<String, SimpleEntry<String, SimpleEntry<Double, Double>>> newEntry) throws Exception {
    if (!Utils.dataExists(newEntry.getKey().toUpperCase(), "stock_data")) {
      Utils.loadStockData(newEntry.getKey().toUpperCase(), "stock_data");
    }
    StockOrder newOrder = new StockOrderImpl(newEntry.getKey(), newEntry.getValue().getValue().getKey(), newEntry.getValue().getKey(),newEntry.getValue().getValue().getValue());
    this.stockOrders.add(newOrder);
    Utils.saveToFile(this.name, this.stockOrders, "portfolios" + File.separator + "flex");
  }


  @Override
  public Double getCurrentValue() throws Exception {
    String currentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
    return getValueOnDate(currentDate);
  }

  @Override
  public Double getValueOnDate(String date) throws Exception {
    double totalVal = 0;
    Map<String , Double > summary = this.getPortfolioSummary(date);
    for(String ticker : summary.keySet()){
      totalVal += Double.parseDouble(Utils.fetchStockValueByDate(ticker,date,"stock_data"))*summary.get(ticker);
    }
    return totalVal;
  }

  @Override
  public Map<String, Double> getPortfolioSummary(String date) throws Exception {
    Map<String, Double> stateMap = new HashMap<>();

    for (StockOrder s : this.stockOrders) {
      String tickerName = s.getStock().getStockTickerName();
      String currentDate = s.getStock().getBuyDate();
      int comparison = Utils.compareDates(currentDate, date);
      if (comparison > 0) {
        continue;
      }
      double qty = s.getQuantity();
      if (stateMap.containsKey(tickerName)) {
        double newQty = qty + stateMap.get(tickerName);
        stateMap.put(tickerName, newQty);
      } else {
        stateMap.put(tickerName, qty);
      }
    }

    return stateMap;
  }

  @Override
  public double getCostBasis(String date) throws Exception {

    double totalTrans = 0;
    for (StockOrder s : this.stockOrders) {
      String currentDate = s.getStock().getBuyDate();
      int comparison = Utils.compareDates(currentDate, date);
      if (comparison > 0) {
        continue;
      }
      if (s.getQuantity() > 0) {
        totalTrans += s.getQuantity() * s.getStock().getBuyPrice();
      }
      totalTrans+=s.getCommFee();

    }
    return totalTrans;
  }

  private boolean datesValidationForGraph(String date1, String date2){
    LocalDate start = LocalDate.parse(date1);
    LocalDate end = LocalDate.parse(date2);
    if(end.isBefore(start) || start.isBefore(LocalDate.parse(this.getCreationDate())) || end.isAfter(LocalDate.now())){
      return false;
    }
    return true;
  }

  @Override
  public  SimpleEntry<List<String>,List<Double>> getPerfDataOverTime(String date1, String date2) throws Exception {
    if(!datesValidationForGraph(date1,date2)) return null;
    //check date2 > date1
    //map date1 / date2 based on portfolio creation date
    // calculate total days difference
    //write if else , call scaledData fun with correct separator
    long dayDiff = Utils.computeDaysBetweenDates(date1 , date2);
    String type="";
    if(dayDiff <= 30 ) {
      type="daily";
    }else if(dayDiff > 30 && dayDiff <= 210){
      type="weekly";
      // TODO : shift to the friday of that week
    }else if(dayDiff >210 && dayDiff <= 900){
      type="monthly";
      // TODO : shift to the last day of the month for start
      //    LocalDate convertedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      //    convertedDate = convertedDate.withDayOfMonth(convertedDate.getMonth().length(convertedDate.isLeapYear()));
    } else if(dayDiff > 900 && dayDiff<1461){
      type="quarterly";
      // TODO : shift start date to last date of that quarter (March,June,September,December)
    }
    else{
      type="yearly";
      // TODO : shift start date to last date of the year
      //convertedDate = convertedDate.with(lastDayOfYear());
    }
    return getScaledPerfData(date1,date2,type);
  }

  private String shiftToLast(String date){
    LocalDate convertedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    convertedDate = convertedDate.withDayOfMonth(convertedDate.getMonth().length(convertedDate.isLeapYear()));
    return convertedDate.toString();
  }

  private SimpleEntry<List<String>,List<Double>> getScaledPerfData(String date1, String date2 , String type) throws Exception {
    System.out.println(type);
    List<Double> datapoints = new ArrayList<>();
    List<String> labels = new ArrayList<>();
    LocalDate start = LocalDate.parse(date1);
    LocalDate end = LocalDate.parse(date2);
    switch(type){
      case "daily":
        while (!start.isAfter(end)) {
          labels.add(start.toString());
          datapoints.add(this.getValueOnDate(start.toString()));
          start = start.plusDays(1);
        }
        break;
      case "weekly":
        while (!start.isAfter(end)) {
          String week = start.getMonth().toString().substring(0,3)+" Week "+(start.getDayOfMonth()/7+1);
          labels.add(week);
          datapoints.add(this.getValueOnDate(start.toString()));
          start=start.plusWeeks(1);
        }
        break;
      case "monthly":
        while (!start.isAfter(end)) {
          String month = start.getMonth().toString().substring(0,3)+" "+start.getYear();
          labels.add(month);
          datapoints.add(this.getValueOnDate(start.toString()));
          start=start.plusMonths(1);


        }
        break;
      case "quarterly":
        start=getQuarterDate(start);
        while (!start.isAfter(end)) {
          String qtr = "Qtr"+(start.getMonthValue()/3)+" "+start.getYear();
          labels.add(qtr);
          datapoints.add(this.getValueOnDate(start.toString()));
          start = start.plusMonths(3);
        }

      case "yearly":
        while (!start.isAfter(end)) {
          int year = start.getYear();
          labels.add(String.valueOf(year));
          datapoints.add(this.getValueOnDate(start.toString()));
          start=start.plusYears(1);
        }
      default:
    }
    return new SimpleEntry<>(labels,datapoints);
  }

  private LocalDate getQuarterDate(LocalDate date){
    int month = date.getMonthValue();
    if(month<3)date=date.plusMonths(3-date.getMonthValue());
    else if(month>3 && month<6) date=date.plusMonths(6-date.getMonthValue());
    else if(month>6 && month<9) date=date.plusMonths(9-date.getMonthValue());
    else if(month>9 && month<12) date=date.plusMonths(12-date.getMonthValue());
    return date;
  }
}
