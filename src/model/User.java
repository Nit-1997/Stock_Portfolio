package model;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface User {

  boolean addPortfolio(String name, HashMap<String, Double> stocks) throws Exception;

  Set<String> retrieveListOfPortfolios();

  HashMap<String, Double> getPortfolioSummary(String name);

  List<int[] > detailedViewOfPortfolio(String name);

  Double profitOrLossOnPortfolio(String name);





}
