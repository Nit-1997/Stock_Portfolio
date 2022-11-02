package controller;


import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import model.User;
import view.ViewPrint;

/**
 * This class contains the functionalities of the controller of the application and takes input from
 * the user and delegates appropriate tasks to model and view based on input.
 */
public class StockController {

  final InputStream in;
  final PrintStream out;

  /**
   * Constructs Object of the controller.
   *
   * @param in  Input Object used to take input from user.
   * @param out output object used to print the result.
   */
  public StockController(InputStream in, PrintStream out) {
    this.in = in;
    this.out = out;
  }

  /**
   * This is the first function that will get executed on starting the application.
   *
   * @param user Object of the model
   * @throws Exception Exception is thrown for unwanted scenarios.
   */
  public void start(User user) throws Exception {
    Objects.requireNonNull(user);
    ViewPrint.welcomeNote(this.out);
    Scanner scan = new Scanner(this.in);
    boolean comingFromDefault = true;
    ViewPrint.printMenu(this.out);
    String option;
    while (true) {
      if (!comingFromDefault) {
        ViewPrint.printMenu(this.out);
      }
      comingFromDefault = false;
      option = scan.nextLine();
      switch (option) {
        case "1":
          addStocksToPortfolioController(scan, user);
          break;
        case "2":
          loadPortfoliosController(scan, user);
          break;
        case "0":
          user.cleanStockDirectory();
          ViewPrint.exitNote(this.out);
          return;
        default:
          ViewPrint.errorNote(this.out);
          comingFromDefault = true;
      }
    }
  }

  /**
   * This function takes valid ticker name for adding in portfolio.
   *
   * @param scan input object
   * @param user model object
   * @return a valid ticker name
   */
  String addStocksAskTicker(Scanner scan, User user) {
    ViewPrint.askTickerSymbol(this.out);
    String ticker;
    do {
      ticker = scan.nextLine().toUpperCase();
      if (ticker.equals("0")) {
        return null;
      }
      if (!user.isValidStock(ticker)) {
        ViewPrint.askTickerSymbolAgain(this.out);
      }
    }
    while (!user.isValidStock(ticker));
    return ticker;
  }

  /**
   * This function takes valid stock number for adding in portfolio.
   *
   * @param scan input object
   * @return a valid stock number
   */
  Double addStocksAskStockNumber(Scanner scan) {
    ViewPrint.askStockNumber(this.out);
    String stockQuantity = scan.nextLine();
    double stockQuanDouble = 0;
    do {
      try {
        stockQuanDouble = Double.parseDouble(stockQuantity);
        if (stockQuanDouble <= 0 || stockQuanDouble != (int) stockQuanDouble) {
          throw new NumberFormatException();
        }
        break;
      } catch (NumberFormatException e) {
        ViewPrint.askStockNumberAgain(this.out);
        stockQuantity = scan.nextLine();
        if (stockQuantity.equals("0")) {
          return null;
        }
      }
    }
    while (stockQuanDouble <= 0 || stockQuanDouble != (int) stockQuanDouble);

    return stockQuanDouble;
  }

  /**
   * This function creates a new portfolio for the user.
   *
   * @param scan input object
   * @param user model object
   * @return a valid ticker name
   */
  void addStocksToPortfolioController(Scanner scan, User user) throws Exception {
    ViewPrint.addPortfolio(this.out);
    String name = scan.nextLine();
    while (!user.isUniqueName(name)) {
      ViewPrint.askPortfolioNameAgain(this.out);
      name = scan.nextLine();
      if (name.equals("0")) {
        return;
      }
    }
    HashMap<String, Double> stocksMap = new HashMap<>();
    String ticker = null;

    ViewPrint.addStocksInPortfolioWelcomeNote(name, this.out);
    Set<String> stockList = user.getStockList();
    if (stockList != null && stockList.size() != 0) {
      ViewPrint.printAvailableStocks(stockList);
    }
    String confirmation = null;
    String option = "1";
    boolean comingFromDefault = false;

    do {
      switch (option) {
        case "1":
          ticker = addStocksAskTicker(scan, user);
          if (ticker == null) {
            return;
          }
          Double stockQuanDouble = addStocksAskStockNumber(scan);
          if (stockQuanDouble == null) {
            return;
          }
          stocksMap.put(ticker, stocksMap.getOrDefault(ticker, 0.0) + stockQuanDouble);
          ViewPrint.addStocksInPortfolioConfirmation(this.out);
          confirmation = scan.nextLine().trim();
          while (!confirmation.equals("y") && !confirmation.equals("Y") && !confirmation.equals("n")
              && !confirmation.equals("N")) {
            ViewPrint.askconfirmation(this.out);
            confirmation = scan.nextLine().trim();
          }
          break;
        case "2":
          ticker = addStocksAskTicker(scan, user);
          if (ticker == null) {
            break;
          }
          if (!stocksMap.containsKey(ticker)) {
            ViewPrint.stockNotInPortfolio(this.out);
            break;
          }
          stockQuanDouble = addStocksAskStockNumber(scan);
          if (stockQuanDouble == null) {
            break;
          }

          if (stocksMap.containsKey(ticker) && stockQuanDouble < stocksMap.get(ticker)) {
            stocksMap.put(ticker, stocksMap.get(ticker) - stockQuanDouble);
            ViewPrint.removeStocksInPortfolioSuccessfulConfirmation(this.out);
          } else if (stocksMap.containsKey(ticker) && stockQuanDouble.equals(
              stocksMap.get(ticker))) {
            stocksMap.remove(ticker);
            ViewPrint.removeStocksInPortfolioSuccessfulConfirmation(this.out);
          } else {
            ViewPrint.removeStocksInPortfolioUnSuccessfulConfirmation(this.out);
          }
          confirmation = scan.nextLine();
          while (!confirmation.equals("y") && !confirmation.equals("Y") && !confirmation.equals("n")
              && !confirmation.equals("N")) {
            ViewPrint.askconfirmation(this.out);
            confirmation = scan.nextLine();
          }
          break;
        case "3":
          confirmation = "n";
          break;
        default:
          ViewPrint.addStocksInPortfolioErrorNode(this.out);
          option = scan.nextLine().trim();

          comingFromDefault = true;
      }

      if (!comingFromDefault && (confirmation.equals("y") || confirmation.equals("Y"))) {
        ViewPrint.stocksInPortfolioAddOrRemoveMenu(this.out);
        option = scan.nextLine().trim();
      }
      comingFromDefault = false;
    }
    while (confirmation.equals("y") || confirmation.equals("Y"));

    ViewPrint.waitMessage(this.out);
    boolean val = user.addPortfolio(name, stocksMap);
    if (val) {
      ViewPrint.addStocksInPortfolioConfirmationLoading(name, this.out);
    } else {
      System.out.println("portfolio cant be added");
    }
  }

  /**
   * This function displays the menu of viewing all the existing portfolios and let the user select
   * for particular portfolio details.
   *
   * @param scan input object
   * @param user model object
   * @return a valid ticker name
   */
  void loadPortfoliosController(Scanner scan, User user) throws Exception {
    Set<String> portfolioNames = user.getPortfolios();
    if (portfolioNames != null) {
      ViewPrint.printPortfolios(portfolioNames, this.out);
    }
    ViewPrint.loadPortfolioMenu(this.out);

    String option = scan.nextLine().trim();

    do {
      switch (option) {
        case "1":
          loadSinglePortfolioDetailController(scan, user);
          option = "2";
          break;
        case "2":
          return;
        default:
          ViewPrint.loadPortfolioErrorNote(this.out);
          option = scan.nextLine().trim();
      }
    }
    while (!option.equals("2"));

  }

  /**
   * This function displays all the functionalities for retrieving data for a particular portfolio.
   *
   * @param scan input object
   * @param user model object
   * @return a valid ticker name
   */
  void loadSinglePortfolioDetailController(Scanner scan, User user) throws Exception {
    ViewPrint.askNameOfPortfolio(this.out);
    String name = scan.nextLine();
    while (user.isUniqueName(name)) {
      ViewPrint.askPortfolioNameAgainUnique(this.out);
      name = scan.nextLine();
      if (name.equals("0")) {
        loadPortfoliosController(scan, user);
        return;
      }
    }
    ViewPrint.portfolioDetailWelcomeNote(name, this.out);
    ViewPrint.loadPortfolioDetailMenu(this.out);
    String option = scan.nextLine().trim();
    boolean comingFromDefault = false;
    do {
      switch (option) {
        case "1":
          ViewPrint.waitLoadMessage(this.out);
          Map<String, Double> stockMap = user.getPortfolioSummary(name);
          if (stockMap == null) {
            ViewPrint.printInCompatiblePortfolio(this.out);
            return;
          }
          ViewPrint.printPortfolioSummary(stockMap, this.out);
          break;
        case "2":
          String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
          ViewPrint.waitLoadMessage(this.out);
          Map<String, List<Double>> detailedMap = user.getPortfolioDetailed(name, date);
          if (detailedMap == null) {
            ViewPrint.printInCompatiblePortfolio(this.out);
            return;
          }
          Double portfolioValue = user.getPortfolioValue(name, date);
          Double portfolioPerformance = user.getPortfolioPnL(name, date);
          if (portfolioValue == null || portfolioPerformance == null) {
            System.out.println("portfolio doesnt exist on this day");
            return;
          }
          ViewPrint.printPortfolioDetail(detailedMap, portfolioValue, this.out);
          ViewPrint.printPortfolioPerformance(portfolioPerformance, this.out);
          break;
        case "3":
          date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
          ViewPrint.waitLoadMessage(this.out);
          portfolioValue = user.getPortfolioValue(name, date);
          if (portfolioValue == null) {
            ViewPrint.printInCompatiblePortfolio(this.out);
            return;
          }
          ViewPrint.printPortfolioValue(portfolioValue, this.out);
          break;
        case "4":
          ViewPrint.askDate(this.out);
          date = scan.nextLine();
          while (!user.dateChecker(date)) {
            ViewPrint.askDateAgain(this.out);
            date = scan.nextLine();
            if (date.equals("0")) {
              loadPortfoliosController(scan, user);
              return;
            }
          }
          ViewPrint.waitLoadMessage(this.out);
          detailedMap = user.getPortfolioDetailed(name, date);
          if (detailedMap == null) {
            ViewPrint.printInCompatiblePortfolio(this.out);
            return;
          }
          portfolioValue = user.getPortfolioValue(name, date);
          portfolioPerformance = user.getPortfolioPnL(name, date);
          ViewPrint.printPortfolioDetail(detailedMap, portfolioValue, this.out);
          ViewPrint.printPortfolioPerformance(portfolioPerformance, this.out);
          break;
        case "5":
          ViewPrint.askDate(this.out);
          date = scan.nextLine();
          while (!user.dateChecker(date)) {
            ViewPrint.askDateAgain(this.out);
            date = scan.nextLine();
            if (date.equals("0")) {
              loadPortfoliosController(scan, user);
              return;
            }
          }
          ViewPrint.waitLoadMessage(this.out);
          portfolioValue = user.getPortfolioValue(name, date);
          if (portfolioValue == null) {
            ViewPrint.printInCompatiblePortfolio(this.out);
            return;
          }
          ViewPrint.printPortfolioValue(portfolioValue, this.out);
          break;
        case "6":
          loadPortfoliosController(scan, user);
          return;
        case "7":
          return;
        default:
          ViewPrint.loadPortfolioErrorNote(this.out);
          option = scan.nextLine();
          comingFromDefault = true;
      }
      if (!comingFromDefault) {
        ViewPrint.loadPortfolioDetailMenu(this.out);
        option = scan.nextLine().trim();
      }
      comingFromDefault = false;
    }
    while (!option.equals("7"));

  }


}
