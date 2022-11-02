package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import constants.Constants;
import java.io.File;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;


public class UserImplTest {

  User user;
  @Before
  public void init(){
    user = new UserImpl();
  }

  @Test
  public void testUserConstructor() {
    String folder = "portfolios";
    String portfolioDirectory = Paths.get(folder).toAbsolutePath().toString();
    File f = new File(portfolioDirectory);
    assertTrue(f.exists());
  }

  @Test
  public void testUserConstructor2() {
    String folder = "stock_data";
    String portfolioDirectory = Paths.get(folder).toAbsolutePath().toString();
    File f = new File(portfolioDirectory);
    assertTrue(f.exists());
  }

  @Test
  public void testUserConstructor3() {
    assertEquals(20, Constants.stockNames.size());
  }


}