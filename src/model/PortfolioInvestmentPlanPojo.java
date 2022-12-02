package model;

import java.util.Map;

/**
 * Class to encapsulate java object for a DCA plan.
 */
public class PortfolioInvestmentPlanPojo {

  public String start;
  public String end;
  public int interval;
  public Double amount;
  public Double commFee;
  public int numStocks;
  public Map<String, Double> stockComposition;

  /**
   * constructor to initialize the pojo
   *
   * @param start            start date
   * @param end              end date
   * @param interval         time interval
   * @param amount           amount to be invested
   * @param commFee          comm fee
   * @param numStocks        number of stocks
   * @param stockComposition composition of those stocks
   */
  public PortfolioInvestmentPlanPojo(String start,
                                     String end,
                                     int interval,
                                     Double amount,
                                     Double commFee,
                                     int numStocks,
                                     Map<String, Double> stockComposition) {
    this.start = start;
    this.end = end;
    this.interval = interval;
    this.amount = amount;
    this.commFee = commFee;
    this.numStocks = numStocks;
    this.stockComposition = stockComposition;
  }

  /**
   * Default Constructor.
   */
  public PortfolioInvestmentPlanPojo(){

  }

}
