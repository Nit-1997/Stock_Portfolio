package model;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Flex Invest Mock model to test GUI controller.
 */
public class FlexInvestMockModel extends FlexMockModel implements UserFlexInvest {

  private final StringBuilder log;

  public FlexInvestMockModel(StringBuilder log) {
    super(log);
    this.log = log;
  }

  @Override
  public void investMoney(String portfolioName, Double amount, Map<String, Double> percentage,
      String date, Double commFee) throws Exception {
    log.append(portfolioName + " " + amount + " " + date + " " + commFee + "\n");
    for (String stock : percentage.keySet()) {
      log.append(stock + " " + percentage.get(stock) + "\n");
    }
  }

  @Override
  public void addPortfolio(String portfolioName, Double amount, Map<String, Double> weightage,
      String startDate, String endDate, int interval, Double commFee) throws Exception {
    log.append(portfolioName + " " + amount + " " + startDate + " " + endDate + " " + interval + " "
        + commFee + "\n");
    for (String stock : weightage.keySet()) {
      log.append(stock + " " + weightage.get(stock) + "\n");
    }
  }

  @Override
  public void InvestThroughDCA(String portfolioName, Double amount, Map<String, Double> weightage,
      String startDate, String endDate, int interval, Double commFee) throws Exception {
    log.append(portfolioName + " " + amount + " " + startDate + " " + endDate + " " + interval + " "
        + commFee + "\n");
    for (String stock : weightage.keySet()) {
      log.append(stock + " " + weightage.get(stock) + "\n");
    }
  }

  @Override
  public SimpleEntry<List<String>, List<Double>> getGraphDataGUI(String date1, String date2,
      String portfolioName) throws Exception {
    log.append(portfolioName + " " + date1 + " " + date2);
    List<String> labels = new ArrayList<>();
    labels.add("label1");
    labels.add("label2");
    labels.add("label3");
    labels.add("label4");
    labels.add("label5");

    List<Double> dataPoints = new ArrayList<>();
    dataPoints.add(10.0);
    dataPoints.add(11.0);
    dataPoints.add(12.0);
    dataPoints.add(13.0);
    dataPoints.add(14.0);

    return new SimpleEntry<>(labels, dataPoints);
  }
}
