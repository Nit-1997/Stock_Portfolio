package utils;

import constants.Constants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import model.ApiDataFetcher;
import model.ApiDataFetcherImpl;
import model.DataSource;
import model.DataSourceImpl;
import model.PortfolioFlex;
import model.PortfolioInvestmentPlanPojo;
import model.StockOrder;
import model.StockOrderImpl;

/**
 * Helper class which contains common and universal functions which are not specific to any class.
 */
public class Utils {

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
      System.out.println(parsedStocks.size());
      throw new IOException("Stock Name file corrupted");
    }
    return parsedStocks;
  }

  private static boolean loadPortfolioValidator(String ticker, String date, String price,
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
        if (!type.equals("flex") && parsedQty <= 0) {
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
    DataSource ds = new DataSourceImpl();
    File portfolioFile = ds.getFileByName(portfolioName, dirName);
    if (portfolioFile == null) {
      return null;
    }

    Scanner myReader = new Scanner(portfolioFile);

    List<StockOrder> parsedFileInput = new ArrayList<>();
    while (myReader.hasNextLine()) {
      String input = myReader.nextLine();
      String[] splitInput = input.split(",");
      if (splitInput.length != 4 && !dirName.endsWith("flex")) {
        return null;
      }
      if (dirName.endsWith("flex") && splitInput.length != 5) {
        return null;
      }
      String ticker = splitInput[0];
      String date = splitInput[3];

      String type = dirName.endsWith("flex") ? "flex" : "inflex";
      if (!loadPortfolioValidator(ticker, date, splitInput[1], splitInput[2], type)) {
        return null;
      }
      double price = Double.parseDouble(splitInput[1]);
      double qty = Double.parseDouble(splitInput[2]);
      StockOrder currentStockOrder;
      if (dirName.endsWith("flex")) {
        Double commFee = Double.parseDouble(splitInput[4]);
        currentStockOrder = new StockOrderImpl(ticker.toUpperCase(), price, date, qty, commFee);
      } else {
        currentStockOrder = new StockOrderImpl(ticker.toUpperCase(), price, date, qty);
      }
      parsedFileInput.add(currentStockOrder);
    }
    myReader.close();
    return parsedFileInput;
  }


  /**
   * Fetched data for the stock from the API and loads into local directory.
   *
   * @param ticker       name of the stock for which data needs to be fetched.
   * @param stockDataDir folder where the stock data needs to be saved.
   * @throws Exception if directory not found or any issue in API.
   */
  public static void loadStockData(String ticker, String stockDataDir) throws Exception {
    DataSource ds = new DataSourceImpl();
    ApiDataFetcher client = new ApiDataFetcherImpl();
    String output = client.fetchStockDataBySymbolAlphaVantage(ticker);
    File stockFile = ds.createFileIfNotExists(ticker, stockDataDir);
    ds.writeToFile(stockFile, output, false);
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
    DataSource ds = new DataSourceImpl();
    File stockFile = ds.getFileByName(ticker, stockDir);
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
    DataSource ds = new DataSourceImpl();
    File stockFile = ds.getFileByName(ticker, dirName);

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

    if (res == null) {
      throw new IOException(ticker + " stock didn't exist on " + date);
    }
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
   * Returns -1 if date1 less tha date2. 1 if date1 greater than date2 0 if date1 = date2
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

  /**
   * Flexible portfolio validator.
   *
   * @param stockOrders Portfolio date
   * @return true is portfolio data is valid.
   */
  public static boolean flexPortfolioValidator(List<StockOrder> stockOrders) {
    Collections.sort(stockOrders, (s1, s2) -> {
      if (s1.getStock().getStockTickerName() != s2.getStock().getStockTickerName()) {
        return s1.getStock().getStockTickerName().compareTo(s2.getStock().getStockTickerName());
      } else {
        try {
          return compareDates(s1.getStock().getBuyDate(), s2.getStock().getBuyDate());
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    });

    Map<String, Double> checkerMap = new HashMap<>();
    for (StockOrder s : stockOrders) {
      if (s.getCommFee() < 0) {
        return false;
      }
      if (checkerMap.containsKey(s.getStock().getStockTickerName())) {
        Double quantity = s.getQuantity();
        if (quantity > 0) {
          quantity = checkerMap.get(s.getStock().getStockTickerName()) + quantity;
          checkerMap.put(s.getStock().getStockTickerName(), quantity);
        } else {
          quantity = Math.abs(quantity);
          if (checkerMap.get(s.getStock().getStockTickerName()) < quantity) {
            return false;
          } else {
            quantity = checkerMap.get(s.getStock().getStockTickerName()) - quantity;
            checkerMap.put(s.getStock().getStockTickerName(), quantity);
          }
        }
      } else {
        checkerMap.put(s.getStock().getStockTickerName(), s.getQuantity());
      }
    }
    return true;
  }

  /**
   * Computes number of days between 2 dates.
   *
   * @param date1 starting date
   * @param date2 ending date
   * @return difference of dates.
   * @throws Exception if dates are not in proper format.
   */
  public static long computeDaysBetweenDates(String date1, String date2) throws Exception {
    SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
    Date d1 = sdformat.parse(date1);
    Date d2 = sdformat.parse(date2);
    long differenceInTime = d2.getTime() - d1.getTime();
    return (differenceInTime / (1000 * 60 * 60 * 24));
  }

  /**
   * Shifts the date to last of that quarter.
   *
   * @param date given date
   * @return last date of the quarter.
   */
  public static LocalDate getQuarterDate(LocalDate date) {
    int month = date.getMonthValue();
    if (month < 3) {
      date = date.plusMonths(3 - date.getMonthValue());
    } else if (month > 3 && month < 6) {
      date = date.plusMonths(6 - date.getMonthValue());
    } else if (month > 6 && month < 9) {
      date = date.plusMonths(9 - date.getMonthValue());
    } else if (month > 9 && month < 12) {
      date = date.plusMonths(12 - date.getMonthValue());
    }
    return date;
  }

  /**
   * Shifts the date to last of the given range.
   *
   * @param shiftType type of range
   * @param date      date to be shifted.
   * @return shifted date.
   */
  public static String shiftDateToValidStartPoint(String shiftType, String date) {
    String shiftedDate = null;
    LocalDate dateObj = LocalDate.parse(date);
    switch (shiftType) {
      case "weekly": {
        dateObj = dateObj.plusDays(5 - dateObj.getDayOfWeek().getValue());
        shiftedDate = dateObj.toString();
        break;
      }
      case "monthly": {
        dateObj = dateObj.plusDays(dateObj.lengthOfMonth() - dateObj.getDayOfMonth());
        shiftedDate = dateObj.toString();
        break;
      }
      case "quarterly": {
        dateObj = getQuarterDate(dateObj);
        dateObj = dateObj.plusDays(dateObj.lengthOfMonth() - dateObj.getDayOfMonth());
        shiftedDate = dateObj.toString();
        break;
      }
      default: {
        //yearly
        dateObj = dateObj.plusDays(dateObj.lengthOfYear() - dateObj.getDayOfYear());
        shiftedDate = dateObj.toString();
        break;
      }
    }
    return shiftedDate;
  }


  /**
   * date validation for graph.
   *
   * @param date1        starting date
   * @param date2        ending date.
   * @param creationDate creation date of portfolio.
   * @return true if dates are valid
   */
  public static boolean datesValidationForGraph(String date1, String date2, String creationDate) {
    LocalDate start = LocalDate.parse(date1);
    LocalDate end = LocalDate.parse(date2);
    return !end.isBefore(start) && !start.isBefore(LocalDate.parse(creationDate)) && !end.isAfter(
        LocalDate.now());
  }

  /**
   * Scales the date to the asked range.
   *
   * @param date1 starting date.
   * @param date2 ending date.
   * @param type  type of the range.
   * @param p     object of portfolio.
   * @return Abstract map of labels and dataPoints.
   * @throws Exception if dates are not valid.
   */
  public static AbstractMap.SimpleEntry<List<String>, List<Double>> getScaledPerfData(String date1,
      String date2, String type, PortfolioFlex p) throws Exception {
    List<Double> datapoints = new ArrayList<>();
    List<String> labels = new ArrayList<>();
    LocalDate start = LocalDate.parse(date1);
    LocalDate end = LocalDate.parse(date2);
    switch (type) {
      case "daily":
        while (!start.isAfter(end)) {
          labels.add(start.toString());
          datapoints.add(p.getValueOnDate(start.toString()));
          start = start.plusDays(1);
        }
        break;
      case "weekly":
        while (!start.isAfter(end)) {
          String week =
              start.getMonth().toString().substring(0, 3) + " Week " + (start.getDayOfMonth() / 7
                  + 1);
          labels.add(week);
          datapoints.add(p.getValueOnDate(start.toString()));
          start = start.plusWeeks(1);
        }
        labels.add(
            end.getMonth().toString().substring(0, 3) + " Week " + (end.getDayOfMonth() / 7 + 1));
        datapoints.add(p.getValueOnDate(end.toString()));
        break;
      case "monthly":
        while (!start.isAfter(end)) {
          String month = start.getMonth().toString().substring(0, 3) + " " + start.getYear();
          labels.add(month);
          datapoints.add(p.getValueOnDate(start.toString()));
          start = start.plusMonths(1);
        }
        labels.add(end.getMonth().toString().substring(0, 3) + " " + end.getYear());
        datapoints.add(p.getValueOnDate(end.toString()));
        break;
      case "quarterly":
        start = Utils.getQuarterDate(start);
        while (!start.isAfter(end)) {
          String qtr = "Qtr" + (start.getMonthValue() / 3) + " " + start.getYear();
          labels.add(qtr);
          datapoints.add(p.getValueOnDate(start.toString()));
          start = start.plusMonths(3);
        }
        labels.add("Qtr" + (int) Math.ceil((double) end.getMonthValue() / 3) + " " + end.getYear());
        datapoints.add(p.getValueOnDate(end.toString()));
        break;
      case "yearly":
        while (!start.isAfter(end)) {
          int year = start.getYear();
          labels.add(String.valueOf(year));
          datapoints.add(p.getValueOnDate(start.toString()));
          start = start.plusYears(1);
        }
        labels.add(String.valueOf(end.getYear()));
        datapoints.add(p.getValueOnDate(end.toString()));
        break;
      default:
    }
    return new AbstractMap.SimpleEntry<>(labels, datapoints);
  }


  /**
   * Fetches the stock value , if no match fetches the future value.
   *
   * @param ticker  ticker symbol
   * @param date    date
   * @param dirName stock_dir
   * @return SimpleEntry of {trans_Date , value}
   * @throws Exception can occur in IO operations
   */
  public static AbstractMap.SimpleEntry<String, Double> fetchStockValueByDateFuture(String ticker,
                                                                                    String date,
                                                                                    String dirName)
          throws Exception {


    if (ticker == null || date == null) {
      throw new IOException("passed null args");
    }
    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    DataSource ds = new DataSourceImpl();
    File stockFile = ds.getFileByName(ticker, dirName);

    Scanner myReader = new Scanner(stockFile);
    int lineNo = 0;
    String res = null;
    String prevDateRes = null;
    String prevDate = null;
    while (myReader.hasNextLine()) {
      String input = myReader.nextLine();
      if (lineNo != 0) {
        String[] out = input.split(",");
        if (sdf.parse(date).compareTo(sdf.parse(out[0])) == 0) {
          prevDate = out[0];
          res = out[4];
          break;
        } else if (sdf.parse(date).compareTo(sdf.parse(out[0])) > 0) {
          res = prevDateRes;
          break;
        }
        prevDateRes = out[4];
        prevDate = out[0];
      }
      lineNo++;
    }
    myReader.close();

    if (res == null) {
      return null;
    } else {
      return new AbstractMap.SimpleEntry<String, Double>(prevDate, Double.parseDouble(res));
    }
  }


  /**
   * Generates the pojo for the portfolio investment plans.
   *
   * @param portfolioDCAFile dca file for the portfolio
   * @return List of investment plans
   * @throws FileNotFoundException if file is missing
   */

  public static List<PortfolioInvestmentPlanPojo> generatePortfolioInvestmentPojoList(
          File portfolioDCAFile) throws FileNotFoundException {

    List<PortfolioInvestmentPlanPojo> dcaList = new ArrayList<>();
    Scanner myReader = new Scanner(portfolioDCAFile);
    PortfolioInvestmentPlanPojo currentPlan = null;
    Map<String, Double> currCompostion = null;

    //read the file and create a list of portfolio Investment plans
    while (myReader.hasNextLine()) {
      String[] data = myReader.nextLine().split(",");
      if (data.length == 6) {
        //found strategy definition line
        if (currentPlan != null) {
          currentPlan.stockComposition = currCompostion;
          dcaList.add(currentPlan);
        }
        currentPlan = new PortfolioInvestmentPlanPojo();
        currentPlan.start = data[0];
        currentPlan.interval = Integer.parseInt(data[1]);
        if (Objects.equals(data[2], "null")) {
          currentPlan.end = null;
        } else {
          currentPlan.end = data[2];
        }
        currentPlan.amount = Double.parseDouble(data[3]);
        currentPlan.commFee = Double.parseDouble(data[4]);
        currentPlan.numStocks = Integer.parseInt(data[5]);
        currCompostion = new HashMap<>();
      } else {
        if (currCompostion != null && !currCompostion.containsKey(data[0])) {
          currCompostion.put(data[0], Double.parseDouble(data[1]));
        }
      }
      if (!myReader.hasNextLine()) {
        currentPlan.stockComposition = currCompostion;
        dcaList.add(currentPlan);
      }
    }
    return dcaList;
  }

  /**
   * builds the string to be dumped in the dca file for a given investmentPlan.
   *
   * @param investmentPlan current investment plan
   * @return parsed entry
   */
  private static String buildDcaEntry(PortfolioInvestmentPlanPojo investmentPlan) {
    StringBuilder sb = new StringBuilder();

    sb.append(investmentPlan.start).append(",").append(investmentPlan.interval).append(",")
        .append(investmentPlan.end).append(",")
        .append(investmentPlan.amount).append(",")
        .append(investmentPlan.commFee).append(",")
        .append(investmentPlan.stockComposition.size())
        .append("\n");

    for (String ticker : investmentPlan.stockComposition.keySet()) {
      sb.append(ticker).append(",").append(investmentPlan.stockComposition.get(ticker))
          .append("\n");
    }
    return sb.toString();
  }

  /**
   * If the stock orders in the investmentPlan are in executable range. execute them and send the
   * updated entry for this dca.
   *
   * @param investmentPlan current investment plan
   * @param stockOrders    stock orders witout dca transactions
   * @return updated dca entry
   * @throws Exception can occur due to IO issues.
   */
  private static String executeOrdersIfValid(PortfolioInvestmentPlanPojo investmentPlan
      , List<StockOrder> stockOrders) throws Exception {
    LocalDate start = LocalDate.parse(investmentPlan.start);
    LocalDate now = LocalDate.now();

    LocalDate lastDate = (investmentPlan.end == null
        || LocalDate.parse(investmentPlan.end).isAfter(now))
        ? now : LocalDate.parse(investmentPlan.end);

    AbstractMap.SimpleEntry<String, Double> priceObj = new SimpleEntry<>("", 0.0);

    while (start.isBefore(lastDate)) {
      boolean flag = false;
      for (String ticker : investmentPlan.stockComposition.keySet()) {
        Double tempCommFee = !flag ? investmentPlan.commFee : 0;
        if (!flag) {
          flag = true;
        }

        double specificAmount = investmentPlan.amount
                * investmentPlan.stockComposition.get(ticker) / 100;
        if (!Utils.dataExists(ticker, "stock_data")) {
          Utils.loadStockData(ticker, "stock_data");
        }
        priceObj = Utils.fetchStockValueByDateFuture(ticker,
            start.toString(), "stock_data");

        if (priceObj == null) {
          break;
        }
        double price = priceObj.getValue();
        double qty = specificAmount / price;
        StockOrder newOrder = new StockOrderImpl(ticker,
            price, priceObj.getKey(), qty, tempCommFee);
        stockOrders.add(newOrder);
      }
      start = start.plusDays(investmentPlan.interval);
    }
    if (investmentPlan.end != null && lastDate == LocalDate.parse(investmentPlan.end)) {
      if (priceObj == null) {
        // did not find data
        // update the stringBuilder and return
        return buildDcaEntry(investmentPlan);
      } else {
        return null;
      }
    } else if (lastDate == now) {
      return buildDcaEntry(investmentPlan);
    }
    return null;
  }


  /**
   * Should read the DCA file for the portfolio. generate the StockOrders execute the ones that need
   * to be executed. dump it into the file in non appendable mode
   *
   * @param portfolioDCAFile dca file to be edited.
   * @param stockOrders      list of stock orders
   * @return List of updated Stock Orders after dca transactions.
   */

  public static List<StockOrder> loadPortfolioWithDCA(File portfolioDCAFile
          , List<StockOrder> stockOrders) throws Exception {
    //convert into pojos
    List<PortfolioInvestmentPlanPojo> dcaList = generatePortfolioInvestmentPojoList(
            portfolioDCAFile);
    DataSource ds = new DataSourceImpl();
    StringBuilder sb = new StringBuilder();
    //iterate through the list of investment plans and execute any old ones
    for (PortfolioInvestmentPlanPojo investmentPlan : dcaList) {
      String response = executeOrdersIfValid(investmentPlan, stockOrders);
      if (response != null) {
        sb.append(response);
      }
    }
    if (sb.toString().length() > 0) {
      ds.writeToFile(portfolioDCAFile, sb.toString(), false);
    } else {
      portfolioDCAFile.delete();
    }
    return stockOrders;
  }

  /**
   * Helper method to create a portfolio DCA and execute valid transactions.
   *
   * @param portfolioName portfolio name
   * @param startDate     start date
   * @param endDate       end date
   * @param weightage     weightage map
   * @param interval      interval of dca strategy
   * @param amount        amount to be invested in 1 transaction
   * @param commFee       commission fee
   * @param stockOrders   stockOrder list
   * @return List of updated stockOrders
   * @throws Exception can occur during I/O operations.
   */
  public static List<StockOrder> updatePortfolioFromDCA(String portfolioName,
                                                        String startDate, String endDate,
                                                        Map<String, Double> weightage, int interval,
                                                        Double amount, Double commFee,
                                                        List<StockOrder> stockOrders)
          throws Exception {

    DataSource ds = new DataSourceImpl();
    LocalDate start = LocalDate.parse(startDate);
    LocalDate now = LocalDate.now();
    LocalDate lastDate = (endDate == null
            || LocalDate.parse(endDate).isAfter(now)) ? now : LocalDate.parse(endDate);

    AbstractMap.SimpleEntry<String, Double> priceObj = new SimpleEntry<>("", 0.0);
    while (start.isBefore(lastDate)) {
      boolean flag = false;
      for (String ticker : weightage.keySet()) {
        Double tempCommFee = !flag ? commFee : 0;
        if (!flag) {
          flag = true;
        }

        double specificAmount = amount * weightage.get(ticker) / 100;
        if (!Utils.dataExists(ticker, "stock_data")) {
          Utils.loadStockData(ticker, "stock_data");
        }
        priceObj = Utils.fetchStockValueByDateFuture(ticker,
                start.toString(), "stock_data");

        if (priceObj == null) {
          break;
        }
        double price = priceObj.getValue();
        double qty = specificAmount / price;
        StockOrder newOrder = new StockOrderImpl(ticker, price,
            priceObj.getKey(), qty, tempCommFee);
        stockOrders.add(newOrder);
      }
      start = start.plusDays(interval);
    }
    if (endDate != null && lastDate == LocalDate.parse(endDate)) {
      if (priceObj == null) {
        dcaUpdateHelper(true, ds, portfolioName, startDate, endDate,
                amount, commFee, interval, weightage);

      }
    } else if (lastDate == now) {
      dcaUpdateHelper(true, ds, portfolioName, startDate, endDate,
              amount, commFee, interval, weightage);
    }
    return stockOrders;
  }

  private static void dcaUpdateHelper(boolean isAppendable, DataSource ds, String portfolioName,
                                      String startDate, String endDate, double amount,
                                      double commFee, int interval
          , Map<String, Double> weightage) throws IOException {
    File portfolioDCA = ds.createFileIfNotExists(portfolioName
            + "_DCA", "portfolios"
            + File.separator + "flex");
    PortfolioInvestmentPlanPojo p = new PortfolioInvestmentPlanPojo(startDate,
            endDate, interval, amount, commFee, weightage.size(), weightage);
    ds.writeToFile(portfolioDCA, buildDcaEntry(p), isAppendable);
  }

}

