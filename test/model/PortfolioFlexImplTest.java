package model;

import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.Port;

import utils.Utils;

import static org.junit.Assert.*;

public class PortfolioFlexImplTest {

  Map<String, Map<String, Double>> order;

  @Before
  public void beforeSuit() throws Exception {
    Utils.clearStockDirectory();
    order = new HashMap<>();
    Map<String , Double> ciscoTuple = new HashMap<>();
    ciscoTuple.put("2022-11-28" , 10.0);
    ciscoTuple.put("2022-11-29" , 12.0);
    ciscoTuple.put("2022-11-31" , -10.0);
    Map<String , Double> netflixTuple = new HashMap<>();
    netflixTuple.put("2022-11-28" , 10.0);
    netflixTuple.put("2022-11-29" , -2.0);
    netflixTuple.put("2022-11-31" , 2.0);
    Map<String , Double> appleTuple = new HashMap<>();
    appleTuple.put("2020-11-28" , 10.0);
    appleTuple.put("2021-11-29" , -2.0);
    appleTuple.put("2022-11-31" , 2.0);
    order.put("CSCO" , ciscoTuple);
    order.put("NFLX" , netflixTuple);
    order.put("AAPL" , appleTuple);
  }


 @Test
 public void testPortfolioFlexImplCreatorConst() throws Exception{
   PortfolioFlex p = new PortfolioFlexImpl(order , "flexTest");
 }

  @Test(expected = Exception.class)
  public void testPortfolioFlexImplCreatorConstFailure1() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(null , "flexTest");
  }

  @Test(expected = Exception.class)
  public void testPortfolioFlexImplCreatorConstFailure2() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(order , null);
  }

  @Test
  public void testPortfolioFlexImplRetreiverConst() throws Exception{

  }


  @Test
  public void something() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(order , "flexTest");
    List<Double> a = p.getScaledPerfData("2022-11-28" , "2022-12-20", 1);
    for(double d : a){
      System.out.println(d);
    }
    System.out.println(a.size());
  }



}