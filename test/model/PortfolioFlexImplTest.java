package model;

import java.util.AbstractMap.SimpleEntry;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.Port;

import constants.Constants;
import utils.Utils;

import static org.junit.Assert.*;

public class PortfolioFlexImplTest {

  Map<String, Map<String, SimpleEntry<Double,Double>>> order;

  @Before
  public void beforeSuit() throws Exception {
    Constants.STOCK_NAMES =  Utils.loadStockNames("stocks", "stocks_list.csv");
    Utils.clearStockDirectory();
    order = new HashMap<>();
    Map<String , SimpleEntry<Double,Double>> ciscoTuple = new HashMap<>();
    ciscoTuple.put("2022-10-28" , new SimpleEntry<>(10.0,4.5));
    ciscoTuple.put("2022-10-29" , new SimpleEntry<>(12.0,4.0));
    ciscoTuple.put("2022-10-31" , new SimpleEntry<>(-10.0,3.5));
    Map<String , SimpleEntry<Double,Double>> netflixTuple = new HashMap<>();
    netflixTuple.put("2022-10-28" , new SimpleEntry<>(10.0,5.0));
    netflixTuple.put("2022-10-29" , new SimpleEntry<>(-2.0,4.5));
    netflixTuple.put("2022-10-31" , new SimpleEntry<>(2.0,4.0));
    Map<String , SimpleEntry<Double,Double>> appleTuple = new HashMap<>();
    appleTuple.put("2020-10-28" , new SimpleEntry<>(10.0,5.0));
    appleTuple.put("2021-10-29" , new SimpleEntry<>(-3.0,3.0));
    appleTuple.put("2022-10-31" , new SimpleEntry<>(2.0,5.5));
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
    PortfolioFlex p = new PortfolioFlexImpl("flexTest");
  }

  @Test(expected = Exception.class)
  public void testPortfolioFlexImplRetreiverConstFailure() throws Exception{
    PortfolioFlex p = new PortfolioFlexImpl(null);
  }


  @Test
  public void testGetLatestState() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl(order, "flexTest");
    Map<String, SimpleEntry<String, Double>> stateMap = p.getLatestState();

    Map<String, SimpleEntry<String, Double>> expected = new HashMap<>();
    SimpleEntry<String , Double> cscoEntry = new SimpleEntry<String , Double>("2022-10-31",12.0);
    expected.put("CSCO" , cscoEntry);
    SimpleEntry<String , Double> nflxEntry = new SimpleEntry<String , Double>("2022-10-31",10.0);
    expected.put("NFLX" , nflxEntry);
    SimpleEntry<String , Double> aaplEntry = new SimpleEntry<String , Double>("2022-10-31",9.0);
    expected.put("AAPL" , aaplEntry);

    for(String key : stateMap.keySet()){
      assertTrue(expected.containsKey(key));
      assertEquals( expected.get(key).getKey() , stateMap.get(key).getKey());
      assertEquals(expected.get(key).getValue() , stateMap.get(key).getValue());
    }
  }


  @Test
  public void testGetValueOnDateBeforePortfolioCreation() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl("flexTest");
    String date = "2012-10-01";
    double res = p.getValueOnDate(date);
    assertEquals(0.0 , res , 0);
  }


  @Test
  public void testGetValueOnDateAfterPortfolioCreationBeforeLastTrans() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl("flexTest");
    String date = "2022-10-31";
    double res = p.getValueOnDate(date);
    assertEquals(4844.02 , res , 0);
  }


  @Test
  public void testGetValueOnDateAfterPortfolioCreationAfterLastTrans() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl("flexTest");
    String date = "2022-11-01";
    double res = p.getValueOnDate(date);
    assertEquals(4768.870000000001 , res , 0);
  }

  @Test
  public void testGetPortfolioSummaryOnLastTransDate() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl("flexTest");
    String date = "2022-10-31";
    Map<String, Double> res = p.getPortfolioSummary(date);
    Map<String , Double> expected = new HashMap<>();
    expected.put("NFLX",10.0);
    expected.put("AAPL",9.0);
    expected.put("CSCO",12.0);

    for(String k : res.keySet()){
      assertTrue(expected.containsKey(k));
      assertEquals(expected.get(k) , res.get(k));
    }
  }

  @Test
  public void testGetPortfolioSummaryBeyondLastTransDate() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl("flexTest");
    String date = "2022-11-11";
    Map<String, Double> res = p.getPortfolioSummary(date);
    Map<String , Double> expected = new HashMap<>();
    expected.put("NFLX",10.0);
    expected.put("AAPL",9.0);
    expected.put("CSCO",12.0);

    for(String k : res.keySet()){
      assertTrue(expected.containsKey(k));
      assertEquals(expected.get(k) , res.get(k));
    }
  }

  @Test
  public void testGetPortfolioSummaryBeforeLastTransDate() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl("flexTest");
    String date = "2022-10-29";
    Map<String, Double> res = p.getPortfolioSummary(date);
    Map<String , Double> expected = new HashMap<>();
    expected.put("NFLX",8.0);
    expected.put("AAPL",7.0);
    expected.put("CSCO",22.0);

    for(String k : res.keySet()){
      assertTrue(expected.containsKey(k));
      assertEquals(expected.get(k) , res.get(k));
    }
  }

  @Test
  public void testGetCostBasisOnLastTransDate() throws Exception {
    PortfolioFlex p = new PortfolioFlexImpl("flexTest");

  }


}