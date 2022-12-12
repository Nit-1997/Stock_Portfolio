package model;

import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import org.junit.Test;



/**
 * Junit class to test StockOrderImpl.
 */
public class StockOrderImplTest {

  @Test
  public void testConstructor1() throws IOException {
    StockOrder so = new StockOrderImpl("CSCO",20.0);
    assertNotEquals(-1,so.getStock().getBuyPrice());
    assertNotEquals(-1,so.getStock().getCurrentPrice());
  }
}