package Utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import constants.Constants;
import model.StockOrder;
import model.StockOrderImpl;

import static org.junit.Assert.*;

public class UtilsTest {

  @Test(expected = IOException.class)
  public void testGetFileByNameInvalidDirectory() throws IOException{
      File f = Utils.getFileByName("a.csv","binaryHero");
  }

  @Test(expected = IOException.class)
  public void testGetFileByNameInvalidFile() throws IOException{
    File f = Utils.getFileByName("cutie.csv","portfolios");
  }

  @Test(expected = IOException.class)
  public void testGetFileByNameNullDirectory() throws IOException{
    File f = Utils.getFileByName("a.csv",null);
  }

  @Test(expected = IOException.class)
  public void testGetFileByNameNullFile() throws IOException{
    File f = Utils.getFileByName(null,"portfolios");
  }

  @Test
  public void testGetFileByName() throws IOException{
    File f = Utils.getFileByName("testFile","testingArtifacts");
    assertNotEquals(null,f);
  }

  @Test(expected = IOException.class)
  public void testSaveToFileNameNull() throws IOException {
    List<StockOrder> o = new ArrayList<>();
    Utils.saveToFile(null,o ,"testingArtifacts");
  }

  @Test(expected = IOException.class)
  public void testSaveToStockOrdersNull() throws IOException {
    Utils.saveToFile("nitin",null ,"testingArtifacts");
  }

  @Test
  public void testSaveToFile() throws Exception {
    List<StockOrder> o = new ArrayList<>();
    String ticker = "CSCO";
    StockOrder so = new StockOrderImpl(ticker,20.0);
    if (!Utils.dataExists(ticker)) {
      Utils.loadStockData(ticker);
    }
    o.add(so);
    Utils.saveToFile("testing2",o ,"testingArtifacts");
  }

  @Test
  public void testLoadStockNamesCorrect() throws IOException {
    Set<String> s = Utils.loadStockNames("testingArtifacts" , "stocks_list_correct.csv");
    assertEquals(Constants.totalHandledStocks,s.size());
  }

  @Test(expected = IOException.class)
  public void testLoadStockNamesEmpty() throws IOException {
    Set<String> s = Utils.loadStockNames("testingArtifacts" , "stocks_list.csv");
  }

  @Test(expected = IOException.class)
  public void testLoadStockNamesFileNotFound() throws IOException {
    Set<String> s = Utils.loadStockNames("testingArtifacts" , "c.csv");
  }

  @Test(expected = IOException.class)
  public void testLoadStockNamesDirectoryNotFound() throws IOException {
    Set<String> s = Utils.loadStockNames("kela" , "c.csv");
  }

  @Test
  public void testDateCheckerPositive(){
    assertTrue(Utils.dateChecker("2022-01-01"));
  }

  @Test
  public void testDateCheckerBeforeStartingDate(){
    assertFalse(Utils.dateChecker("2009-01-01"));
  }

  @Test
  public void testDateCheckerBeforeAfterDate(){
    assertFalse(Utils.dateChecker("2009-11-04"));
  }

  @Test
  public void testDateCheckerBeforeWrongFormat(){
    assertFalse(Utils.dateChecker("02-26-2022"));
  }

  @Test
  public void testDateCheckerBeforeWrongFormat2(){
    assertFalse(Utils.dateChecker("02/26/2022"));
  }

  @Test
  public void testDateSaturdaySundayCheckerWrongFormat(){
    assertEquals("",Utils.dateSaturdaySundayChecker("02/26/2022"));
  }

  @Test
  public void testDateSaturdaySundayCheckerSaturday(){
    assertEquals("2021-04-30",Utils.dateSaturdaySundayChecker("2021-05-01"));
  }

  @Test
  public void testDateSaturdaySundayCheckerSunday(){
    assertEquals("2021-05-21",Utils.dateSaturdaySundayChecker("2021-05-23"));
  }

  @Test
  public void testDateSaturdaySundayCheckerWeekday(){
    assertEquals("2021-05-19",Utils.dateSaturdaySundayChecker("2021-05-19"));
  }

  @Test
  public void testClearStockDirectoryNotEmptyDirectory() throws IOException {
    Path portfolioDirectory = Paths.get("stock_data").toAbsolutePath();
    new File(portfolioDirectory+File.separator+"a.txt").createNewFile();
    new File(portfolioDirectory+File.separator+"b.txt").createNewFile();
    Utils.clearStockDirectory();
    File[] list = new File(portfolioDirectory.toString()).listFiles();
    assertEquals(0,list.length);
  }

  @Test
  public void testClearStockDirectoryEmptyDirectory() {
    Path portfolioDirectory = Paths.get("stock_data").toAbsolutePath();
    Utils.clearStockDirectory();
    File[] list = new File(portfolioDirectory.toString()).listFiles();
    assertEquals(0,list.length);
  }

  @Test
  public void testClearStockDirectoryNoDirectory() {
    Path portfolioDirectory = Paths.get("stock_data").toAbsolutePath();
    File directory = new File(portfolioDirectory.toString());
    directory.delete();
    assertFalse(directory.exists());
    Utils.clearStockDirectory();
    assertTrue(directory.exists());
  }



}