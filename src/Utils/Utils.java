package Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import model.StockOrder;
import model.StockOrderImpl;

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
    File portfolioFile = createFileIfNotExists(name);
    writePortfolioToFile(portfolioFile, orders);
  }


  private static void writePortfolioToFile(File portfolioFile, List<StockOrder> orders) throws IOException {
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

  private static  String getFilePath(String name){
    String os = System.getProperty("os.name");
    String path = "";
    if (Objects.equals(os.split(" ")[0], "Windows")) {
      path = Paths.get("portfolios")
              .toAbsolutePath() + "\\" + name + ".csv";
    } else {
      path = Paths.get("portfolios")
              .toAbsolutePath() + "/" + name + ".csv";
    }
    return path;
  }
  private static File createFileIfNotExists(String name) throws IOException {
    String path = getFilePath(name);
    File portfolioFile = new File(path);
    if (portfolioFile.createNewFile()) {
      System.out.println("Portfolio created to file : " + portfolioFile.getName());
    } else {
      System.out.println("Portfolio already exists reading from it ...");
    }

    return portfolioFile;
  }

  public static Set<String> loadStockNames() throws IOException {
    String stockRepoName = "stocks";
    String portfolioDirectory = Paths.get(stockRepoName).toAbsolutePath().toString();
    File stockFile = Objects.requireNonNull(new File(portfolioDirectory).listFiles((f1, name) -> name.equals("stocks_list.csv")))[0];
    Scanner myReader = new Scanner(stockFile);
    Set<String> parsedStocks = new HashSet<>();
    while (myReader.hasNextLine()) {
      String input = myReader.nextLine().split(",")[0];
      parsedStocks.add(input);
    }
    myReader.close();
    return parsedStocks;
  }

  /**
   * Fetches data for the portfolio from local directory
   *
   * @return List of stock orders
   */
  public static List<StockOrder> loadPortfolioData(String portfolioName) throws Exception {
    File portfolioFile = Utils.getPortfolioFileByName(portfolioName);
    Scanner myReader = new Scanner(portfolioFile);

    List<StockOrder> parsedFileInput = new ArrayList<>();
    while (myReader.hasNextLine()) {
      String input = myReader.nextLine();
      String[] splitInput = input.split(",");
      String ticker = splitInput[0];
      String date = splitInput[3];
      double price = Double.parseDouble(splitInput[1]);
      double qty = Double.parseDouble(splitInput[2]);
      StockOrder currentStockOrder = new StockOrderImpl(ticker,price,date,qty);
      parsedFileInput.add(currentStockOrder);
    }
    myReader.close();
    return parsedFileInput;
  }

  public static void loadAndStoreStockData(){

  }
}
