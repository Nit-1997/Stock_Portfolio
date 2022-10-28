package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of User and it's functionality.
 */
public class UserImpl implements User {
  Set<String> portfolios;

  /**
   * Constructor to initialize User Object with portfolios.
   */
  public UserImpl() {
    this.portfolios = UserImpl.getCurrentFileNames();
  }


  @Override
  public boolean addPortfolio(String name, HashMap<String, Double> stocks) {
    try {
      this.portfolios.add(name);
      new PortfolioImpl(stocks, name);
      return true;
    } catch (Exception e) {
      return false;
    }
  }


  @Override
  public Set<String> getPortfolios() {
    //TODO fetch names of portfolio files in the local directory
    return this.portfolios;
  }


  @Override
  public HashMap<String, Double> getPortfolioSummary(String name, String date) {
//    PortfolioImpl portfolio = (PortfolioImpl) portfolios.
    return null;
  }

  @Override
  public Map< String , List<Double>> getPortfolioDetailed(String name, String date) {
    return null;
  }

  @Override
  public Double getPortfolioPnL(String name, String date) {
    //TODO fix this
    //Portfolio port = (PortfolioImpl) portfolios.get();
    //return port.getPortfolioPnL();
    return 0d;
  }

  /**
   * Fetches the current names of the portfolio files
   *
   * @return list of portfolio names
   */
  private static Set<String> getCurrentFileNames() {
    return new HashSet<>();
  }
}
