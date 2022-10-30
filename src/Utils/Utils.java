package Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import model.StockOrder;

public class Utils {

  /**
   * takes csv data as input and returns the closing price as per current that date.
   *
   * @param data csv time series data
   * @param date date to be fetched
   * @return closing value
   */
  public static String parseAndFetchPriceByDate(StringBuilder data, String date) {
    return null;
  }

  public static File getPortfolioFileByName(String portfolioName) throws IOException {
    String portfolioDirectory = Paths.get("portfolios").toAbsolutePath().toString();

    File[] portfolioFiles = new File(portfolioDirectory).listFiles((f1, name) -> name.equals(portfolioName + ".csv"));
    if (portfolioFiles == null) {
      createFileIfNotExists(portfolioName);
      portfolioFiles = new File(portfolioDirectory).listFiles((f1, name) -> name.equals(portfolioName + ".csv"));
      assert portfolioFiles != null;
    }
    return portfolioFiles[0];
  }


  /**
   * saves the current portfolio to the file.
   */
  public static void saveToFile(String name, List<StockOrder> orders) throws IOException {
    createFileIfNotExists(name);
    writePortfolioToFile(name, orders);
  }


  private static void writePortfolioToFile(String name, List<StockOrder> orders) throws IOException {
    File portfolioFile = Utils.getPortfolioFileByName(name);
    FileWriter myWriter = new FileWriter(portfolioFile);
    for (StockOrder order : orders) {
      myWriter.write("" + order.getStock().getStockTickerName()
              + "," + order.getStock().getBuyPrice()
              + "," + order.getQuantity()
              + "," + order.getStock().getBuyDate() + "\n"
      );
    }
    myWriter.close();
    System.out.println("Successfully wrote to the file.");
  }

  private static void createFileIfNotExists(String name) throws IOException {
    String os = System.getProperty("os.name");
    String path = "";
    if (Objects.equals(os.split(" ")[0], "Windows")) {
      path = Paths.get("portfolios")
              .toAbsolutePath() + "\\" + name + ".csv";
    } else {
      path = Paths.get("portfolios")
              .toAbsolutePath() + "/" + name + ".csv";
    }
//    File portfolioFile = Utils.getPortfolioFileByName(name);
    File portfolioFile = new File(path);
    if (portfolioFile.createNewFile()) {
      System.out.println("Portfolio created to file : " + portfolioFile.getName());
    } else {
      System.out.println("Portfolio already exists reading from it ...");
    }
  }
}
