package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import utils.Utils;

public class PortfolioFlexInvestImpl extends PortfolioFlexImpl{

  /**
   * Creator constructor.
   *
   * @param portfolioName name of the portfolio.
   * @param amount amount to be invested regularly.
   * @param weightage map of stock vs the percentage
   * @param startDate start date of investment.
   * @param endDate end date of investment.
   * @param interval interval of investment.
   * @param commFee commission fee of each investment.
   * @throws Exception
   */
  public PortfolioFlexInvestImpl(String portfolioName, Double amount, Map<String, Double> weightage,
      String startDate, String endDate,Integer interval, Double commFee) throws Exception {
    this.name=portfolioName;
    this.creationDate=startDate;

    if (weightage == null || portfolioName == null || amount == null || startDate == null ||
        interval == null || commFee == null) {
      throw new IllegalArgumentException("Null arguments to portfolio constructor");
    }

    this.stockOrders = new ArrayList<>();
    for(String ticker : weightage.keySet()){
      if (!Utils.dataExists(ticker, "stock_data")) {
        Utils.loadStockData(ticker, "stock_data");
      }
    }

    this.stockOrders=Utils.updatePortfolioFromDCA(portfolioName,startDate,endDate,weightage,
        interval,amount,commFee,this.stockOrders);
    DataSource ds = new DataSourceImpl();
    ds.saveToFile(this.name, this.stockOrders, "portfolios" + File.separator + "flex");
  }

  /**
   * Retriever Constructor.
   *
   * @param portfolioName name of the portfolio
   * @throws Exception while reading/writing data dump
   */
  public PortfolioFlexInvestImpl(String portfolioName) throws Exception {
    DataSource ds = new DataSourceImpl();
    if (portfolioName == null) {
      throw new IllegalArgumentException("Null arguments to portfolio constructor");
    }
    List<StockOrder> tempStockOrders = Utils.loadPortfolioData(portfolioName,
        "portfolios" + File.separator + "flex");
    if (tempStockOrders == null || !Utils.flexPortfolioValidator(tempStockOrders)) {
      this.stockOrders = null;
    } else {
      this.stockOrders = tempStockOrders;
    }
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


    if (Utils.dataExists(portfolioName+"_DCA", "portfolios" + File.separator + "flex")){
      File helper = ds.getFileByName(portfolioName+"_DCA","portfolios" + File.separator + "flex");
      this.stockOrders = Utils.loadPortfolioWithDCA(portfolioName , helper , this.stockOrders);
      //this.stockOrders=Utils.DCAFileValidator(portfolioName,helper,this.stockOrders);

    }
  }

  @Override
  public void addDCAInvestment(Double amount, Map<String, Double> weightage, String startDate,
      String endDate, int interval, Double commFee) throws Exception {

    this.stockOrders=Utils.updatePortfolioFromDCA(this.name,startDate,endDate,weightage,
        interval,amount,commFee,this.stockOrders);

  }




}
