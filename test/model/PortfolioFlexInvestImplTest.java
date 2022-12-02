package model;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import utils.Utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Junit tests for portfolioFlexInvestImpl
 */
public class PortfolioFlexInvestImplTest {
  String portfolioName = "investImplTesting";
  PortfolioFlex p;

  @Before
  public void init() throws Exception {
    DataSource ds = new DataSourceImpl();
    if (Utils.dataExists(portfolioName, "portfolios" + File.separator + "flex")) {
      //delete
      ds.getFileByName(portfolioName, "portfolios"
              + File.separator + "flex").delete();
    }
    if (Utils.dataExists(portfolioName + "_DCA", "portfolios"
            + File.separator + "flex")) {
      //delete
      ds.getFileByName(portfolioName + "_DCA", "portfolios"
              + File.separator + "flex").delete();
    }
  }

  public PortfolioInvestmentPlanPojo buildPojo() {
    PortfolioInvestmentPlanPojo p = new PortfolioInvestmentPlanPojo();
    Map<String, Double> weightage = new HashMap<>();
    weightage.put("AMZN", 50.0);
    weightage.put("CSCO", 50.0);
    p.stockComposition = weightage;
    p.start = "2012-08-10";
    p.end = null;
    p.amount = 20.0;
    p.interval = 365;
    p.commFee = 2.0;
    return p;
  }

  @Test
  public void testPortfolioInvestImplCreation() throws Exception {
    PortfolioInvestmentPlanPojo pojo = buildPojo();
    //create the portfolio with DCA strategy
    p = new PortfolioFlexInvestImpl(portfolioName, pojo.amount,
            pojo.stockComposition, pojo.start, pojo.end, pojo.interval, pojo.commFee);
    //check if portfolio file got created
    assertTrue(Utils.dataExists(portfolioName, "portfolios" + File.separator + "flex"));
    //check if dca file got created
    assertTrue(Utils.dataExists(portfolioName + "_DCA", "portfolios"
            + File.separator + "flex"));
  }

  @Test
  public void testPortfolioDCACostBasis() throws Exception {
    PortfolioInvestmentPlanPojo pojo = buildPojo();
    //create the portfolio with DCA strategy
    p = new PortfolioFlexInvestImpl(portfolioName, pojo.amount,
            pojo.stockComposition, pojo.start, pojo.end, pojo.interval, pojo.commFee);

    double costBasis = p.getCostBasis("2014-08-10");

    assertEquals(44.0, costBasis, 0);
  }


  @Test
  public void testPortfolioDCACostBasisFuzzy() throws Exception {
    PortfolioInvestmentPlanPojo pojo = buildPojo();
    //create the portfolio with DCA strategy
    p = new PortfolioFlexInvestImpl(portfolioName, pojo.amount,
            pojo.stockComposition, pojo.start, pojo.end, pojo.interval, pojo.commFee);

    String[] dates = {
            "2010-08-10",
            "2011-08-10",
            "2012-08-10",
            "2013-08-10",
            "2014-08-10",
            "2015-08-10",
            "2016-08-10",
            "2017-08-10",
            "2018-08-10",
            "2019-08-10",
    };

    double[] expectedCostBasis = {
            0.0,
            0.0,
            22.0,
            22.0,
            44.0,
            88.0,
            110.0,
            132.0,
            154.0,
            176.0
    };
    int i = 0;
    for (String date : dates) {
      double costBasis = p.getCostBasis(date);
      assertEquals(expectedCostBasis[i], costBasis, 0);
      i++;
    }
  }

  @Test
  public void testPortfolioValueFuzzy() throws Exception {
    PortfolioInvestmentPlanPojo pojo = buildPojo();
    //create the portfolio with DCA strategy
    p = new PortfolioFlexInvestImpl(portfolioName, pojo.amount,
            pojo.stockComposition, pojo.start, pojo.end, pojo.interval, pojo.commFee);
    String[] dates = {
            "2010-08-10",
            "2011-08-10",
            "2012-08-10",
            "2013-08-10",
            "2014-08-10",
            "2015-08-10",
            "2016-08-10",
            "2017-08-10",
            "2018-08-10",
            "2019-08-10",
    };

    double[] expectedValues = {
            0.0,
            0.0,
            20.0,
            27.62,
            48.06,
            115.12,
            170.02,
            216.28,
            405.81,
            435.73
    };
    int i = 0;
    for (String date : dates) {
      double value = p.getValueOnDate(date);
      assertEquals(expectedValues[i], value, 0.1);
      i++;
    }
  }

  @Test
  public void testPortfolioSummaryFuzzy() throws Exception {
    PortfolioInvestmentPlanPojo pojo = buildPojo();
    //create the portfolio with DCA strategy
    p = new PortfolioFlexInvestImpl(portfolioName, pojo.amount,
            pojo.stockComposition, pojo.start, pojo.end, pojo.interval, pojo.commFee);
    String[] dates = {
            "2010-08-10",
            "2011-08-10",
            "2012-08-10",
    };


    int i = 0;
    for (String date : dates) {
      Map<String, Double> summary = p.getPortfolioSummary(date);
      switch (i) {
        case 0: {
          assertTrue(summary.isEmpty());
        }
        ;
        case 1: {
          assertTrue(summary.isEmpty());
        }
        ;
        case 2: {
          for (String key : summary.keySet()) {
            if (Objects.equals(key, "CSCO")) {
              assertEquals(0.57, summary.get(key), 0.1);
            }
            if (Objects.equals(key, "AMZN")) {
              assertEquals(0.04, summary.get(key), 0.1);
            }
          }
        }
      }
      i++;
    }
  }


}