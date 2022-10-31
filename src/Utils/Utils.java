package Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import constants.Constants;
import model.ApiDataFetcher;
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

  public static File getFileByName(String fileName , String dirName) throws IOException {
    String fileDirectory = Paths.get(dirName).toAbsolutePath().toString();

    File[] files = new File(fileDirectory).listFiles((f1, name) -> name.equals(fileName + ".csv"));
    if (files == null) {
      createFileIfNotExists(fileName, dirName);
      files = new File(fileDirectory).listFiles((f1, name) -> name.equals(fileName + ".csv"));
      assert files != null;
    }
    return files[0];
  }


  /**
   * saves the current portfolio to the file.
   */
  public static void saveToFile(String name, List<StockOrder> orders) throws IOException {
    File portfolioFile = createFileIfNotExists(name, "portfolios");
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

  private static String getFilePath(String name, String dirName) {
    String os = System.getProperty("os.name");
    String path = "";
    if (Objects.equals(os.split(" ")[0], "Windows")) {
      path = Paths.get(dirName)
              .toAbsolutePath() + "\\" + name + ".csv";
    } else {
      path = Paths.get(dirName)
              .toAbsolutePath() + "/" + name + ".csv";
    }
    return path;
  }

  private static File createFileIfNotExists(String name, String dirName) throws IOException {
    String path = getFilePath(name, dirName);
    File createdFile = new File(path);
    if (createdFile.createNewFile()) {
      System.out.println(dirName+" created to file : " + createdFile.getName());
    } else {
      System.out.println(dirName+" already exists reading from it ...");
    }

    return createdFile;
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
    File portfolioFile = Utils.getFileByName(portfolioName,"portfolios");
    Scanner myReader = new Scanner(portfolioFile);

    List<StockOrder> parsedFileInput = new ArrayList<>();
    while (myReader.hasNextLine()) {
      String input = myReader.nextLine();
      String[] splitInput = input.split(",");
      String ticker = splitInput[0];
      String date = splitInput[3];
      double price = Double.parseDouble(splitInput[1]);
      double qty = Double.parseDouble(splitInput[2]);
      StockOrder currentStockOrder = new StockOrderImpl(ticker, price, date, qty);
      parsedFileInput.add(currentStockOrder);
    }
    myReader.close();
    return parsedFileInput;
  }

  public static void writeStockDataDumpToFile(File stockFile, String data) throws IOException {
    FileWriter myWriter = new FileWriter(stockFile);
    myWriter.write(data);
    myWriter.close();
    System.out.println("Successfully wrote to the file.");
  }

  public static void loadStockData(String ticker, String apiKey) throws Exception {
    String output = ApiDataFetcher.fetchStockDataBySymbol(ticker, apiKey);
    File stockFile = createFileIfNotExists(ticker, "stock_data");
    writeStockDataDumpToFile(stockFile, output);
  }

  public static boolean dataExists(String ticker) {
    String stockDir = Paths.get("stock_data").toAbsolutePath().toString();
    File f = new File(stockDir);
    String[] files = f.list((f1, name) -> name.equals(ticker + ".csv"));
    return files != null && files.length != 0;
  }

  public static String fetchCurrentStockValue(String ticker) throws IOException {
    File stockFile = Utils.getFileByName(ticker,"stock_data");
    Scanner myReader = new Scanner(stockFile);
    int lineNo = 0;
    String out = "";
    while (myReader.hasNextLine()) {
      String input = myReader.nextLine();
      if(lineNo == 1){
        out = input.split(",")[4];
        break;
      }
      lineNo++;
      myReader.nextLine();
    }
    myReader.close();
    return out;
  }

  public static String fetchStockValueByDate(String ticker, String date) throws IOException {
    File stockFile = Utils.getFileByName(ticker,"stock_data");
    Scanner myReader = new Scanner(stockFile);
    int lineNo = 0;
    String res = "-1";
    while (myReader.hasNextLine()) {
      String input = myReader.nextLine();
      if(lineNo != 0){
        String[] out = input.split(",");
        if(Objects.equals(out[0], date)){
            res = out[4];
        }
      }
      lineNo++;
    }
    myReader.close();
    return res;
  }

  public static boolean dateChecker(String dateStr){
    DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    sdf.setLenient(false);
    try {
      Date date = sdf.parse(dateStr);
      Date firstDate = sdf.parse("11/01/1999");
      Date currentDate = sdf.parse(DateTimeFormatter.ofPattern("MM/dd/yyyy").format(LocalDateTime.now()));
      if(date.before(firstDate) || date.after(currentDate)) return false;
    } catch (ParseException e) {
      return false;
    }
    return true;
  }

  public static String dateSaturdaySundayChecker(String dateStr){
    final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
    DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    sdf.setLenient(false);

    DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

    try {
      Date date = sdf.parse(dateStr);
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      int day = cal.get(Calendar.DAY_OF_WEEK);
      if(day==7){
        return sdf2.format(new Date(date.getTime()-MILLIS_IN_A_DAY));
      }
      else if(day==1){
        return sdf2.format(new Date(date.getTime()-2*MILLIS_IN_A_DAY));
      }
      else return sdf2.format(date);
    } catch (ParseException e) {
      System.out.println("wrong format");
    }
    return dateStr;
  }
}
