package model;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.Utils;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Junit tests for data source.
 */
public class DataSourceImplTest {

  DataSource ds;

  @Before
  public void createDSObject() {
    ds = new DataSourceImpl();
  }

  @Test(expected = IOException.class)
  public void testGetFileByNameInvalidDirectory() throws IOException {
    File f = ds.getFileByName("a.csv", "binaryHero");
  }

  @Test(expected = IOException.class)
  public void testGetFileByNameInvalidFile() throws IOException {
    File f = ds.getFileByName("cutie.csv", "portfolios");
  }

  @Test(expected = IOException.class)
  public void testGetFileByNameNullDirectory() throws IOException {
    File f = ds.getFileByName("a.csv", null);
  }

  @Test(expected = IOException.class)
  public void testGetFileByNameNullFile() throws IOException {
    File f = ds.getFileByName(null, "portfolios");
  }

  @Test
  public void testGetFileByName() throws IOException {
    File f = ds.getFileByName("testFile", "testingArtifacts");
    assertNotEquals(null, f);
  }

  @Test(expected = IOException.class)
  public void testSaveToFileNameNull() throws IOException {
    List<StockOrder> o = new ArrayList<>();
    ds.saveToFile(null, o, "testingArtifacts");
  }

  @Test(expected = IOException.class)
  public void testSaveToStockOrdersNull() throws IOException {
    ds.saveToFile("nitin", null, "testingArtifacts");
  }

  @Test
  public void testSaveToFile() throws Exception {
    List<StockOrder> o = new ArrayList<>();
    String ticker = "CSCO";
    Utils.clearStockDirectory();
    if (!Utils.dataExists(ticker, "stock_data")) {
      Utils.loadStockData(ticker, "stock_data");
    }
    StockOrder so = new StockOrderImpl(ticker, 20.0);
    o.add(so);
    ds.saveToFile("testing2", o, "testingArtifacts");
    assertNotNull(so);
  }

}