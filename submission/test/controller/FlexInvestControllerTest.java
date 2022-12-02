package controller;

import static org.junit.Assert.assertEquals;

import controller.gui_controller.DCAPortfolio;
import controller.gui_controller.GraphData;
import controller.gui_controller.InvestStock;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FlexInvestMockModel;
import model.UserFlexInvest;
import org.junit.Before;
import org.junit.Test;
import view.MockView;
import view.ViewGUI;

/**
 * Flexible Controller with DCA Strategy testing.
 */
public class FlexInvestControllerTest {

  UserFlexInvest model;
  StringBuilder log;
  ByteArrayOutputStream bytes;
  InputStream in;
  PrintStream out;

  ViewGUI view;

  @Before
  public void init() {
    bytes = new ByteArrayOutputStream();
    out = new PrintStream(bytes);
    log = new StringBuilder();
    model = new FlexInvestMockModel(log);
    view = new MockView();
  }

  @Test
  public void testDCAPortfolioCreation() {

    List<String> data = new ArrayList<>();
    data.add("controllerTest");
    data.add("2015-01-01");
    data.add("2016-01-01");
    data.add("100.0");
    data.add("30");
    data.add("2.9");

    Map<String, Double> map = new HashMap<>();
    map.put("Stock1", 50.0);
    map.put("Stock2", 20.0);
    map.put("Stock3", 30.0);

    String str = new DCAPortfolio(data, map, model, "create").execute();
    assertEquals("Portfolio Successfully Saved", str);
    assertEquals("unique name checkerchecking the date 2015-01-012016-01-01"
        + " is before date 2015-01-01Name of ticker is Stock1Name of ticker is Stock2Name of ticker"
        + " is Stock3controllerTest 100.0 2015-01-01 2016-01-01 30 2.9\n"
        + "Stock1 50.0\n"
        + "Stock2 20.0\n"
        + "Stock3 30.0\n", this.log.toString());

  }

  @Test
  public void testInvestStock() {
    List<String> data = new ArrayList<>();
    data.add("controllerTest");
    data.add("2015-01-01");
    data.add("100.0");
    data.add("2.9");

    Map<String, Double> map = new HashMap<>();
    map.put("Stock1", 50.0);
    map.put("Stock2", 20.0);
    map.put("Stock3", 30.0);

    String str = new InvestStock(data, map, model).execute();
    assertEquals("Success", str);
    assertEquals("checking the date 2015-01-01get portfolio creation date for "
        + "controllerTest2015-01-01 is before date 2011-02-15controllerTest 100.0 2015-01-01 2.9\n"
        + "Stock1 50.0\n"
        + "Stock2 20.0\n"
        + "Stock3 30.0\n", this.log.toString());

  }

  @Test
  public void testDCAInvest() {
    List<String> data = new ArrayList<>();
    data.add("controllerTest");
    data.add("2015-01-01");
    data.add("2016-01-01");
    data.add("100.0");
    data.add("30");
    data.add("2.9");

    Map<String, Double> map = new HashMap<>();
    map.put("Stock1", 50.0);
    map.put("Stock2", 20.0);
    map.put("Stock3", 30.0);

    String str = new DCAPortfolio(data, map, model, "exist").execute();
    assertEquals("Invested through DCA", str);
    assertEquals("checking the date 2015-01-01get portfolio creation date for "
        + "controllerTest2015-01-01 is before date 2011-02-152016-01-01 is before date "
        + "2015-01-01Name of ticker is Stock1Name of ticker is Stock2Name of ticker is "
        + "Stock3controllerTest 100.0 2015-01-01 2016-01-01 30 2.9\n"
        + "Stock1 50.0\n"
        + "Stock2 20.0\n"
        + "Stock3 30.0\n", this.log.toString());
  }

  @Test
  public void testGraph() {
    GraphData obj = new GraphData("2016-01-01", "2020-01-01",
        "controllerTest", model);
    String str = obj.execute();
    assertEquals("success", str);
    assertEquals("[label1, label2, label3, label4, label5]=[10.0, 11.0, 12.0, 13.0, 14.0]",
        obj.data.toString());
    assertEquals("checking the date 2016-01-01get portfolio creation date for controller"
        + "Test2016-01-01 is before date 2011-02-15checking the date 2020-01-01get portfolio "
        + "creation date for controllerTest2020-01-01 is before date 2011-02-15controllerTest "
        + "2016-01-01 2020-01-01", this.log.toString());

  }
}
