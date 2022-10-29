package model;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
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
    String portfolioDirectory = Paths.get("portfolios").toAbsolutePath().toString();
    File f = new File(portfolioDirectory);
    String[] files = f.list((f1, name) -> name.endsWith(".xml"));
    for(int i=0;i<files.length;i++){
      files[i]=files[i].substring(0,files[i].indexOf('.'));
    }
    this.portfolios = new HashSet<>(Arrays.asList(files));
  }


  @Override
  public boolean addPortfolio(String name, Map<String, Double> stocks) {
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

  @Override
  public boolean isUniqueName(String name){
      return !this.portfolios.contains(name);
  }

  @Override
  public boolean ctrlCPressedChecker(String name){
    final boolean[] checker = {false};
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      killPortfolio(name);
      checker[0] =true;
    }));

    return checker[0];
  }

  private void killPortfolio(String name){

  }
}
