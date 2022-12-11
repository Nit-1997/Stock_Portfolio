package model;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * test the stock IO operations.
 */
public class StockIOTest {

  /**
   * Test reading a file.
   */
  @Test
  public void readFile() {
    String data;
    FileHandler fileWriter = new StockIO();

    try {
      data = fileWriter.readFile("Stocks/data/myPortfolio.JSON");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    assertEquals("{\n"
            + "    \"AAPL\":1234,\n"
            + "    \"GOOGL\":6969,\n"
            + "    \"MSFT\":420\n"
            + "}", data);
  }

  /**
   * Test loading an entire folder of portfolios.
   */
  @Test
  public void loadData() {
    FileHandler fileWriter = new StockIO();
    String myPortfolio = "{\n"
            + "    \"AAPL\":1234,\n"
            + "    \"GOOGL\":6969,\n"
            + "    \"MSFT\":420\n"
            + "}";
    String myPortfolio2 = "{\n"
            + "    \"BUTTA\":543,\n"
            + "    \"FOO\":98,\n"
            + "    \"FOSS\":99,\n"
            + "    \"UNIX\":69420\n"
            + "}";
    Map<String, String> load = null;
    try {
      load = fileWriter.loadData("Stocks/data");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    Map<String, String> actual = new HashMap<>();
    actual.put("myPortfolio2.JSON", myPortfolio2);
    actual.put("myPortfolio.JSON", myPortfolio);
    assertTrue(actual.equals(load));
  }

  /**
   * Test writing a file.
   */
  @Test
  public void writeFile() {
    FileHandler fileWriter = new StockIO();
    String myPortfolio = "{\n"
            + "    \"AAPL\":1234,\n"
            + "    \"GOOGL\":6969,\n"
            + "    \"MSFT\":420\n"
            + "}";
    fileWriter.writeFile("myPortfolio", myPortfolio, ".JSON");

    System.out.print("Working Directory = " + System.getProperty("user.dir"));

    StringBuilder output = new StringBuilder();
    File file = new File("Stocks/data/myPortfolio.JSON");
    Scanner scanner = null;
    try {
      scanner = new Scanner(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    while (scanner.hasNextLine()) {
      output.append(scanner.nextLine());
      if (scanner.hasNextLine()) {
        output.append(System.lineSeparator());
      }
    }
    String readData = output.toString();
    assertEquals(readData, myPortfolio);

  }
}