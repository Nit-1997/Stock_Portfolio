package utils;

import constants.Constants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import model.ApiDataFetcher;
import model.StockOrder;
import model.StockOrderImpl;

/**
 * Helper class which contains common and universal functions which are not specific to any class.
 */
public class Utils {

  /**
   * Searches and Returns file inside a particular directory.
   *
   * @param fileName name of the file needs to be searched.
   * @param dirName  directory in which file needs to be searched.
   * @return File object of that found file.
   * @throws IOException if the directory doesn't exist or file not found.
   */
  public static File getFileByName(String fileName, String dirName) throws IOException {
    if (fileName == null || dirName == null) {
      throw new IOException("arguments passed are null");
    }
    String fileDirectory = Paths.get(dirName).toAbsolutePath().toString();

    File[] files = new File(fileDirectory).listFiles((f1, name) -> name.equals(fileName + ".csv"));
    if (files == null) {
      throw new IOException("Cannot find the directory");
    } else {
      if (files.length == 0) {
        throw new IOException("File does not exist");
      } else {
        return files[0];
      }
    }
  }

  /**
   * Saves the current file in the directory.
   *
   * @param name    name of the file to be saved.
   * @param orders  data that needs to be saved.
   * @param dirName folder in which file will be saved.
   * @throws IOException if directory doesn't exist or any argument exception.
   */
  public static void saveToFile(String name, List<StockOrder> orders, String dirName)
          throws IOException {
    if (name == null || orders == null) {
      throw new IOException("passed null args");
    }
   // System.out.println(dirName);
    File portfolioFile = createFileIfNotExists(name, dirName);
    if(dirName.endsWith("flex")){
      writePortfolioToFileFlex(portfolioFile,orders);
    }
    else{
      writePortfolioToFile(portfolioFile, orders);
    }
  }


  private static void writePortfolioToFile(File portfolioFile, List<StockOrder> orders)
          throws IOException {
    if (portfolioFile == null || orders == null) {
      throw new IOException("passed null args");
    }
    FileWriter myWriter = new FileWriter(portfolioFile);
    for (StockOrder order : orders) {
      myWriter.write("" + order.getStock().getStockTickerName()
              + "," + order.getStock().getBuyPrice()
              + "," + order.getQuantity()
              + "," + order.getStock().getBuyDate() + "\n"
      );
    }
    myWriter.close();
  }

  private static void writePortfolioToFileFlex(File portfolioFile, List<StockOrder> orders)
      throws IOException {
    if (portfolioFile == null || orders == null) {
      throw new IOException("passed null args");
    }
    FileWriter myWriter = new FileWriter(portfolioFile);
    for (StockOrder order : orders) {
      myWriter.write("" + order.getStock().getStockTickerName()
          + "," + order.getStock().getBuyPrice()
          + "," + order.getQuantity()
          + "," + order.getStock().getBuyDate()
          + "," + order.getCommFee() + "\n"
      );
    }
    myWriter.close();
  }

  private static String getFilePath(String name, String dirName) throws IOException {
    if (name == null || dirName == null) {
      throw new IOException("passed null args");
    }
    String path = Paths.get(dirName).toAbsolutePath() + File.separator + name + ".csv";
    return path;
  }

  private static File createFileIfNotExists(String name, String dirName) throws IOException {
    String path = getFilePath(name, dirName);
    return new File(path);
  }

  /**
   * Load names of available stocks from the local file.
   *
   * @param stockRepoName folder where file of stocks is present
   * @param stockFileName name of file containing stocks.
   * @return set of stock names.
   * @throws IOException if directory or file not present.
   */
  public static Set<String> loadStockNames(String stockRepoName, String stockFileName)
          throws IOException {
    String portfolioDirectory = Paths.get(stockRepoName).toAbsolutePath().toString();
    File[] stockFiles = new File(portfolioDirectory).listFiles(
            (f1, name) -> name.equals(stockFileName));
    if (stockFiles == null) {
      throw new IOException("Could not find the directory");
    }
    if (stockFiles.length == 0) {
      throw new IOException("File not found");
    }
    File stockFile = stockFiles[0];
    Scanner myReader = new Scanner(stockFile);
    Set<String> parsedStocks = new HashSet<>();
    while (myReader.hasNextLine()) {
      String input = myReader.nextLine().split(",")[0];
      parsedStocks.add(input);
    }
    myReader.close();
    if (parsedStocks.size() != Constants.TOTAL_HANDLED_STOCKS) {
      throw new IOException("Stock Name file corrupted");
    }
    return parsedStocks;
  }

  private static boolean  loadPortfolioValidator(String ticker, String date, String price,
                                                String qty, String type) {
    try {
      if (!Constants.STOCK_NAMES.contains(ticker.toUpperCase())) {
        return false;
      } else if (!dateChecker(date)) {
        return false;
      } else {
        double parsedPrice = Double.parseDouble(price);
        if (parsedPrice <= 0) {
          return false;
        }
        double parsedQty = Double.parseDouble(qty);
        if (type.equals("inflex") && parsedQty <= 0) {
          return false;
        }
      }
    } catch (NumberFormatException e) {
      return false;
    }
    return true;

  }

  /**
   * Fetches data for the portfolio from local directory.
   *
   * @param portfolioName name of the portfolio to be fetched and loaded.
   * @param dirName       folder in which portfolios are present.
   * @return List of stock orders
   * @throws IOException if the portfolioName or the dirName are null.
   */
  public static List<StockOrder> loadPortfolioData(String portfolioName, String dirName)
          throws IOException {
    File portfolioFile = Utils.getFileByName(portfolioName, dirName);
    if (portfolioFile == null) {
      return null;
    }

    Scanner myReader = new Scanner(portfolioFile);

    List<StockOrder> parsedFileInput = new ArrayList<>();
    while (myReader.hasNextLine()) {
      String input = myReader.nextLine();
      String[] splitInput = input.split(",");
      if (splitInput.length != 4  && !dirName.endsWith("flex")) {
        return null;
      }
      if(dirName.endsWith("flex") && splitInput.length!=5){
        return null;
      }
      String ticker = splitInput[0];
      String date = splitInput[3];

      String type = dirName.endsWith("flex")?"flex":"inflex";
      if (!loadPortfolioValidator(ticker, date, splitInput[1], splitInput[2],type)) {
        return null;
      }
      double price = Double.parseDouble(splitInput[1]);
      double qty = Double.parseDouble(splitInput[2]);
      StockOrder currentStockOrder;
      if(dirName.endsWith("flex")){
        Double commFee = Double.parseDouble(splitInput[4]);
        currentStockOrder = new StockOrderImpl(ticker.toUpperCase(), price, date, qty, commFee);
      }
      else{
        currentStockOrder = new StockOrderImpl(ticker.toUpperCase(), price, date, qty);
      }
      parsedFileInput.add(currentStockOrder);
    }
    myReader.close();
    return parsedFileInput;
  }

  private static void writeStockDataDumpToFile(File stockFile, String data) throws IOException {
    FileWriter myWriter = new FileWriter(stockFile);
    myWriter.write(data);
    myWriter.close();
  }

  /**
   * Fetched data for the stock from the API and loads into local directory.
   *
   * @param ticker       name of the stock for which data needs to be fetched.
   * @param stockDataDir folder where the stock data needs to be saved.
   * @throws Exception if directory not found or any issue in API.
   */
  public static void loadStockData(String ticker, String stockDataDir) throws Exception {
//    String output = ApiDataFetcher.fetchStockDataBySymbolYahoo(ticker,
//        Constants.YAHOO_API_BASE_URL);
    String output = ApiDataFetcher.fetchStockDataBySymbolAlphaVantage(ticker);
    File stockFile = createFileIfNotExists(ticker, stockDataDir);
    writeStockDataDumpToFile(stockFile, output);
  }

  /**
   * Checks if a file exist in a directory or not.
   *
   * @param fileName name of the file to be checked.
   * @param dirName  directory in which file needs to be checked.
   * @return if file exists or not.
   */
  public static boolean dataExists(String fileName, String dirName) {
    String stockDir = Paths.get(dirName).toAbsolutePath().toString();
    File f = new File(stockDir);
    String[] files = f.list((f1, name) -> name.equals(fileName + ".csv"));
    return files != null && files.length != 0;
  }

  /**
   * Loads current stock price from the file in local folder.
   *
   * @param ticker   name of the stock
   * @param stockDir directory which contains the stocks.
   * @return current price of the stock.
   * @throws IOException if file or directory doesn't exist
   */
  public static String fetchCurrentStockValue(String ticker, String stockDir) throws IOException {
    File stockFile = Utils.getFileByName(ticker, stockDir);
    Scanner myReader = new Scanner(stockFile);
    int lineNo = 0;
    String out = null;
    while (myReader.hasNextLine()) {
      String input = myReader.nextLine();
      if (lineNo == 1) {
        out = input.split(",")[4];
        break;
      }
      lineNo++;
    }
    myReader.close();
    return out;
  }

  /**
   * Loads stock price for a date from the file in local folder.
   *
   * @param ticker  name of the stock
   * @param date    date for which stock price is required
   * @param dirName directory which contains the stocks.
   * @return price of the stock on that date.
   * @throws IOException if file or directory doesn't exist
   */
  public static String fetchStockValueByDate(String ticker, String date, String dirName)
          throws IOException {
    if (ticker == null || date == null) {
      throw new IOException("passed null args");
    }
    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    File stockFile = Utils.getFileByName(ticker, dirName);

    Scanner myReader = new Scanner(stockFile);
    int lineNo = 0;
    String res = null;
    while (myReader.hasNextLine()) {
      String input = myReader.nextLine();
      if (lineNo != 0) {
        String[] out = input.split(",");
        try {
          if (sdf.parse(date).compareTo(sdf.parse(out[0])) >= 0) {
            res = out[4];
            break;
          }
        } catch (ParseException e) {
          System.out.println("wrong date");
        }
      }
      lineNo++;
    }
    myReader.close();

    return res;
  }

  /**
   * Validates the date for range and format.
   *
   * @param dateStr date that needs to be validated.
   * @return if date is valid or not
   */
  public static boolean dateChecker(String dateStr) {
    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    sdf.setLenient(false);
    try {
      Date date = sdf.parse(dateStr);
      Date firstDate = sdf.parse("2010-01-04");
      Date currentDate = sdf.parse(
              DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
      if (date.before(firstDate) || date.after(currentDate)) {
        return false;
      }
    } catch (ParseException e) {
      return false;
    }
    return true;
  }

  /**
   * Clears the folder containing all the stocks after program termination.
   */
  public static void clearStockDirectory() {
    String portfolioDirectory = Paths.get("stock_data").toAbsolutePath().toString();
    File directory = new File(portfolioDirectory);
    if (directory.listFiles() == null) {
      directory.mkdirs();
      return;
    }
    if (directory.listFiles().length == 0) {
      return;
    }
    for (File file : directory.listFiles()) {
      file.delete();
    }
  }

  /**
   * Returns the range for data fetch from API.
   *
   * @return Array containing first and last day for api data.
   */
  public static String[] yahooApiDateFetcher() {
    DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    simpleDateFormat.setLenient(false);
    Date initialDate = null;
    Date currentDate = null;
    try {
      initialDate = simpleDateFormat.parse("2010-01-01");
      currentDate = simpleDateFormat
              .parse(DateTimeFormatter
                      .ofPattern("yyyy-MM-dd")
                      .format(LocalDateTime.now()));
    } catch (ParseException e) {
      System.out.println("wrong date");
    }

    long timestamp2 = currentDate.getTime() / 1000;
    long timestamp1 = initialDate.getTime() / 1000;

    String[] periods = new String[2];
    periods[0] = timestamp1 + "";
    periods[1] = timestamp2 + "";
    return periods;
  }

  /**
   * Returns -1 if date1 < date2.
   * 1 if date1 > date2
   * 0 if date1 = date2
   *
   * @param date1 date1
   * @param date2 date2
   * @return {-1,0,1}
   * @throws ParseException if invalid date
   */
  public static int compareDates(String date1, String date2) throws Exception {
    SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
    Date d1 = sdformat.parse(date1);
    Date d2 = sdformat.parse(date2);
    return d1.compareTo(d2);
  }

  public static boolean FlexPortfolioValidator(List<StockOrder> stockOrders) {
    Collections.sort(stockOrders,(s1,s2)->{
      if(s1.getStock().getStockTickerName()!=s2.getStock().getStockTickerName())
       return s1.getStock().getStockTickerName().compareTo(s2.getStock().getStockTickerName());
       else {
        try {
          return compareDates(s1.getStock().getBuyDate(),s2.getStock().getBuyDate());
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    });

    Map<String, Double> checkerMap = new HashMap<>();
    for(StockOrder s : stockOrders){
      if(s.getCommFee()<0) return false;
      if(checkerMap.containsKey(s.getStock().getStockTickerName())){
        Double quantity = s.getQuantity();
        if(quantity>0){
          quantity=checkerMap.get(s.getStock().getStockTickerName())+quantity;
          checkerMap.put(s.getStock().getStockTickerName(),quantity);
        }
        else{
          quantity=Math.abs(quantity);
          if(checkerMap.get(s.getStock().getStockTickerName())<quantity) return false;
          else{
            quantity=checkerMap.get(s.getStock().getStockTickerName())-quantity;
            checkerMap.put(s.getStock().getStockTickerName(),quantity);
          }
        }
      }else {
        checkerMap.put(s.getStock().getStockTickerName(),s.getQuantity());
      }
    }
    return true;
  }

  public static long computeDaysBetweenDates(String date1 , String date2) throws Exception{
    SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
    Date d1 = sdformat.parse(date1);
    Date d2 = sdformat.parse(date2);
    long differenceInTime = d2.getTime() - d1.getTime();
    return (differenceInTime / (1000 * 60 * 60 * 24));
  }

}
