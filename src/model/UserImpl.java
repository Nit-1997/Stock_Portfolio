package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class UserImpl implements User {


  Set<String> portfolios;

  public UserImpl() {
    this.portfolios = UserImpl.getCurrentFileNames();
  }

  /**
   * Adds a given Portfolio.
   *
   * @param name   name of portfolio
   * @param stocks map of {tickerSym , qty}
   * @return true if added , false otherwise
   */
  @Override
  public boolean addPortfolio(String name, HashMap<String, Double> stocks) {
    try {
      portfolios.add(name);
      new PortfolioImpl(stocks, name);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Fetch List of Portfolios
   * @return list of portfolis
   */
  @Override
  public Set<String> retrieveListOfPortfolios() {
    return this.portfolios;
  }

  /**
   * Fetches the portfolio summary for a given portfolio.
   * @param name name of the portfolio
   * @return {ticker_symbl , qty}
   */
  @Override
  public HashMap<String, Double> getPortfolioSummary(String name) {
    PortfolioImpl portfolio = (PortfolioImpl) portfolios.
    return null;
  }

  @Override
  public List<int[]> detailedViewOfPortfolio(String name) {
    return null;
  }

  @Override
  public Double profitOrLossOnPortfolio(String name) {
    //TODO fix this
    Portfolio port = (PortfolioImpl) portfolios.get();
    return port.getPortfolioPnL();
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
