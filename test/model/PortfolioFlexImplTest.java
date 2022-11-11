package model;

import org.junit.Test;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import utils.Utils;

import static org.junit.Assert.*;

public class PortfolioFlexImplTest {

  @Test
  public void testPortfolioFlexImplConstructor1() throws Exception {
    Utils.clearStockDirectory();
    Map<String, Map<String, Double>> order = new HashMap<>();
    Map<String , Double> ciscoTuple = new HashMap<>();
    ciscoTuple.put("2021-11-28" , 10.0);
    ciscoTuple.put("2021-11-29" , 12.0);
    ciscoTuple.put("2021-11-31" , -10.0);
    order.put("CSCO" , ciscoTuple);

    // test constructor creator
    PortfolioFlex p = new PortfolioFlexImpl(order , "flexTest");

    //test get summary
    Map<String , Double> s = p.getPortfolioSummary("2021-11-31");
    for(String key : s.keySet()){
      System.out.println(key+" : "+s.get(key));
    }

    System.out.println("*********");
    //test get val on date
    System.out.println(p.getValueOnDate("2021-11-31"));

    // test cost basis
    System.out.println(p.getCostBasis("2021-11-31"));

    System.out.println("*********");

    // test getState
    Map<String , AbstractMap.SimpleEntry<String , Double>> stateMap = p.getLatestState();
    for(String key : stateMap.keySet()){
      System.out.println(key+" : "+stateMap.get(key).getKey() + " , " + stateMap.get(key).getValue());
    }

    System.out.println("*********");
    //test buy
    AbstractMap.SimpleEntry<String, Double> updatedEntry = new AbstractMap.SimpleEntry<>("2021-11-31",20.0);
    AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<String, Double>> finalEntry = new AbstractMap.SimpleEntry<>("NFLX",updatedEntry);
    p.addStock(finalEntry);

    s = p.getPortfolioSummary("2021-11-31");
    for(String key : s.keySet()){
      System.out.println(key+" : "+s.get(key));
    }

    System.out.println("*********");
    //test sell

    AbstractMap.SimpleEntry<String, Double> updatedEntry2 = new AbstractMap.SimpleEntry<>("2021-11-31",-2.0);
    AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<String, Double>> finalEntry2 = new AbstractMap.SimpleEntry<>("CSCO",updatedEntry2);
    p.sellStock(finalEntry2);

    s = p.getPortfolioSummary("2021-11-31");
    for(String key : s.keySet()){
      System.out.println(key+" : "+s.get(key));
    }

  }

}