package model;

import constants.Constants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
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

/**
 * Class for Flexible user model.
 */
public class UserFlexImpl extends AbstractUser implements UserFlex {

  protected final Map<String, PortfolioFlex> portfolioMap;

  /**
   * Constructor of UserFlexImpl.
   */
  public UserFlexImpl() {
    this.portfolioMap = new HashMap<>();
    Utils.clearStockDirectory();
    try {
      Constants.STOCK_NAMES = Utils.loadStockNames("stocks",
              "stocks_list.csv");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    String portfolioDirectory = Paths.get("portfolios").toAbsolutePath().toString();

    File f = new File(portfolioDirectory);
    if (!f.exists()) {
      f.mkdirs();
    }
    portfolioDirectory = Paths.get("portfolios" + File.separator + "flex").toAbsolutePath()
            .toString();
    f = new File(portfolioDirectory);
    if (!f.exists()) {
      f.mkdirs();
      return;
    }
    String[] files = f.list((f1, name) -> name.endsWith(".csv") && !name.endsWith("_DCA.csv"));

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

  protected void loadPortfolio(String name) throws Exception {
    if (this.portfolioMap.get(name) == null) {
      this.portfolioMap.put(name, new PortfolioFlexInvestImpl(name));
    }
  }

  @Override
  public Double getPortfolioValue(String name, String date) throws Exception {

    if (!Utils.dateChecker(date)) {
      throw new Exception("wrong arguments");
    }

    this.loadPortfolio(name);
    if (this.isBeforeDate(date, this.portfolioMap.get(name).getCreationDate())) {
      return 0.0;
    }
    Double portfolioValue;
    String currentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
    if (date.equals(currentDate)) {
      portfolioValue = this.portfolioMap.get(name).getCurrentValue();
    } else {

      portfolioValue = this.portfolioMap.get(name).getValueOnDate(date);
    }
    return portfolioValue;
  }


  @Override
  public boolean isUniqueName(String name) {
    return !this.portfolioMap.containsKey(name);
  }


  @Override
  public boolean addPortfolio(String name,
                              Map<String, Map<String, SimpleEntry<Double, Double>>> stocksMap)
          throws Exception {
    try {
      if (!this.isUniqueName(name)) {
        throw new IOException("Portfolio with the asked name already exist");
      }
      this.portfolioMap.put(name, new PortfolioFlexImpl(stocksMap, name));
      return true;
    } catch (Exception e) {
      if (e.getMessage().equals("Asked stock didn't exist on that date") || e.getMessage()
              .equals("Portfolio with the asked name already exist")) {
        throw new IOException(e);
      }
      throw e;
    }
  }

  @Override
  public Map<String, Double> getPortfolioSummary(String name, String date) throws Exception {
    if (!Utils.dateChecker(date) || this.isBeforeDate(date,
            this.getPortfolioCreationDate(name))) {
      throw new Exception("wrong arguments");
    }
    this.loadPortfolio(name);
    return this.portfolioMap.get(name).getPortfolioSummary(date);
  }

  @Override
  public String getPortfolioCreationDate(String portfolioName) throws Exception {

    this.loadPortfolio(portfolioName);
    return this.portfolioMap.get(portfolioName).getCreationDate();

  }

  @Override
  public Map<String, SimpleEntry<String, Double>> getPortfolioState(String portfolioName) {
    try {
      this.loadPortfolio(portfolioName);
      return this.portfolioMap.get(portfolioName).getLatestState();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  @Override
  public boolean isBeforeDate(String firstDate, String secondDate) {
    try {
      return Utils.compareDates(firstDate, secondDate) < 0;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean transactionForPortfolio(String portfolioName,
                                         SimpleEntry<String, SimpleEntry<String, SimpleEntry<Double,
                                                 Double>>> newStock)
          throws Exception {
    this.loadPortfolio(portfolioName);
    this.portfolioMap.get(portfolioName).addTransaction(newStock, LocalDate.now().toString());
    return true;
  }


  @Override
  public Double getCostBasis(String portfolioName, String date) throws Exception {
    this.loadPortfolio(portfolioName);
    return this.portfolioMap.get(portfolioName).getCostBasis(date);

  }

  @Override
  public boolean graphDateChecker(String date1, String date2, String portfolioName) {
    return Utils.datesValidationForGraph(date1, date2,
            this.portfolioMap.get(portfolioName).getCreationDate());
  }

  @Override
  public SimpleEntry<SimpleEntry<List<String>, List<Integer>>, SimpleEntry<Integer, Double>>
      getGraphData(String date1, String date2, String portfolioName) {
    SimpleEntry<List<String>, List<Double>> data;
    try {
      this.loadPortfolio(portfolioName);
      if (!this.graphDateChecker(date1, date2, portfolioName)) {
        return null;
      }
      data = this.portfolioMap.get(portfolioName).getPerfDataOverTime(date1, date2);
    } catch (Exception e) {
      return null;
    }
    if (data == null) {
      return null;
    }
    List<String> labels = data.getKey();
    List<Double> dataPoints = data.getValue();

    Double min = Collections.min(dataPoints);

    double baseAmount = 0.0;
    if (min > 50) {
      baseAmount = min;
    }
    for (int i = 0; i < dataPoints.size(); i++) {
      dataPoints.set(i, dataPoints.get(i) - baseAmount);
    }

    Double max = Collections.max(dataPoints);

    int scale = (int) (max / 50);
    List<Integer> starPoints = new ArrayList<>();
    for (Double dataPoint : dataPoints) {
      starPoints.add((int) (dataPoint / scale));
    }

    return new SimpleEntry<>(new SimpleEntry<>(labels, starPoints),
            new SimpleEntry<>(scale, baseAmount));


  }


}
