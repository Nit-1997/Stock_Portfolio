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
import model.UserInflex;
import view.IView;

/**
 * This class contains the functionalities of the controller of the application and takes input from
 * the user and delegates appropriate tasks to model and view based on input.
 */
public class InflexController implements CategoryControllerInterface<UserInflex> {

  final InputStream in;
  final PrintStream out;

  IView view;

  /**
   * Constructs Object of the controller.
   *
   * @param in  Input Object used to take input from user.
   * @param out output object used to print the result.
   */
  public InflexController(InputStream in, PrintStream out, IView view) {
    this.in = in;
    this.out = out;
    this.view = view;
  }

  @Override
  public void start(UserInflex user) {
    Objects.requireNonNull(user);
    System.out.println("-------------Inflexible Portfolio Menu-------------");
    Scanner scan = new Scanner(this.in);
    boolean comingFromDefault = true;
    this.view.printMenu(this.out);
    String option;
    while (true) {
      if (!comingFromDefault) {
        this.view.printMenu(this.out);
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
          System.out.println("\nthank you for using inflexible portfolio wizard");
          return;
        default:
          this.view.errorNote(this.out);
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
  String addStocksAskTicker(Scanner scan, UserInflex user) {
    this.view.askTickerSymbol(this.out);
    String ticker;
    do {
      ticker = scan.nextLine().toUpperCase();
      if (ticker.equals("0")) {
        return null;
      }
      if (!user.isValidStock(ticker)) {
        this.view.askTickerSymbolAgain(this.out);
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
    this.view.askStockNumber(this.out);
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
        this.view.askStockNumberAgain(this.out);
        stockQuantity = scan.nextLine();
        if (stockQuantity.equals("e")) {
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
  void addStocksToPortfolioController(Scanner scan, UserInflex user) {
    this.view.addPortfolio(this.out);
    String name = scan.nextLine();
    while (!user.isUniqueName(name)) {
      this.view.askPortfolioNameAgain(this.out);
      name = scan.nextLine();
      if (name.equals("0")) {
        return;
      }
    }
    HashMap<String, Double> stocksMap = new HashMap<>();
    String ticker = null;

    this.view.addStocksInPortfolioWelcomeNote(name, this.out);
    Set<String> stockList = user.getStockList();
    if (stockList != null && stockList.size() != 0) {
      this.view.printAvailableStocks(stockList);
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
          this.view.addStocksInPortfolioConfirmation(this.out);
          confirmation = scan.nextLine().trim();
          while (!confirmation.equals("y") && !confirmation.equals("Y") && !confirmation.equals("n")
              && !confirmation.equals("N")) {
            this.view.askconfirmation(this.out);
            confirmation = scan.nextLine().trim();
          }
          break;
        case "2":
          ticker = addStocksAskTicker(scan, user);
          if (ticker == null) {
            break;
          }
          if (!stocksMap.containsKey(ticker)) {
            this.view.stockNotInPortfolio(this.out);
            break;
          }
          stockQuanDouble = addStocksAskStockNumber(scan);
          if (stockQuanDouble == null) {
            break;
          }

          if (stocksMap.containsKey(ticker) && stockQuanDouble < stocksMap.get(ticker)) {
            stocksMap.put(ticker, stocksMap.get(ticker) - stockQuanDouble);
            this.view.removeStocksInPortfolioSuccessfulConfirmation(this.out);
          } else if (stocksMap.containsKey(ticker) && stockQuanDouble.equals(
              stocksMap.get(ticker))) {
            stocksMap.remove(ticker);
            this.view.removeStocksInPortfolioSuccessfulConfirmation(this.out);
          } else {
            this.view.removeStocksInPortfolioUnSuccessfulConfirmation(this.out);
          }
          confirmation = scan.nextLine();
          while (!confirmation.equals("y") && !confirmation.equals("Y") && !confirmation.equals("n")
              && !confirmation.equals("N")) {
            this.view.askconfirmation(this.out);
            confirmation = scan.nextLine();
          }
          break;
        case "3":
          confirmation = "n";
          break;
        default:
          this.view.addStocksInPortfolioErrorNode(this.out);
          option = scan.nextLine().trim();

          comingFromDefault = true;
      }

      if (!comingFromDefault && (confirmation.equals("y") || confirmation.equals("Y"))) {
        this.view.stocksInPortfolioAddOrRemoveMenu(this.out);
        option = scan.nextLine().trim();
      }
      comingFromDefault = false;
    }
    while (confirmation.equals("y") || confirmation.equals("Y"));

    this.view.waitMessage(this.out);
    boolean val = user.addPortfolio(name, stocksMap);
    if (val) {
      this.view.addStocksInPortfolioConfirmationLoading(name, this.out);
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
  void loadPortfoliosController(Scanner scan, UserInflex user) {
    Set<String> portfolioNames = user.getPortfolios();
    if (portfolioNames != null) {
      this.view.printPortfolios(portfolioNames, this.out);
    }
    this.view.loadPortfolioMenu(this.out);

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
          this.view.loadPortfolioErrorNote(this.out);
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
  void loadSinglePortfolioDetailController(Scanner scan, UserInflex user) {
    this.view.askNameOfPortfolio(this.out);
    String name = scan.nextLine();
    while (user.isUniqueName(name)) {
      this.view.askPortfolioNameAgainUnique(this.out);
      name = scan.nextLine();
      if (name.equals("0")) {
        loadPortfoliosController(scan, user);
        return;
      }
    }
    this.view.portfolioDetailWelcomeNote(name, this.out);
    this.view.loadPortfolioDetailMenu(this.out);
    String option = scan.nextLine().trim();
    boolean comingFromDefault = false;
    do {
      switch (option) {
        case "1":
          this.view.waitLoadMessage(this.out);
          Map<String, Double> stockMap = user.getPortfolioSummary(name);
          if (stockMap == null) {
            this.view.printInCompatiblePortfolio(this.out);
            return;
          }
          this.view.printPortfolioSummary(stockMap, this.out);
          break;
        case "2":
          String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
          this.view.waitLoadMessage(this.out);
          Map<String, List<Double>> detailedMap = user.getPortfolioDetailed(name, date);
          if (detailedMap == null) {
            this.view.printInCompatiblePortfolio(this.out);
            return;
          }
          Double portfolioValue = user.getPortfolioValue(name, date);
          Double portfolioPerformance = user.getPortfolioPnL(name, date);
          if (portfolioValue == null || portfolioPerformance == null) {
            System.out.println("portfolio doesnt exist on this day");
            return;
          }
          this.view.printPortfolioDetail(detailedMap, portfolioValue, this.out);
          this.view.printPortfolioPerformance(portfolioPerformance, this.out);
          break;
        case "3":
          date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
          this.view.waitLoadMessage(this.out);
          portfolioValue = user.getPortfolioValue(name, date);
          if (portfolioValue == null) {
            this.view.printInCompatiblePortfolio(this.out);
            return;
          }
          this.view.printPortfolioValue(portfolioValue, this.out);
          break;
        case "4":
          this.view.askDate(this.out);
          date = scan.nextLine();
          while (!user.dateChecker(date)) {
            this.view.askDateAgain(this.out);
            date = scan.nextLine();
            if (date.equals("0")) {
              loadPortfoliosController(scan, user);
              return;
            }
          }
          this.view.waitLoadMessage(this.out);
          detailedMap = user.getPortfolioDetailed(name, date);
          if (detailedMap == null) {
            this.view.printInCompatiblePortfolio(this.out);
            return;
          }
          portfolioValue = user.getPortfolioValue(name, date);
          portfolioPerformance = user.getPortfolioPnL(name, date);
          this.view.printPortfolioDetail(detailedMap, portfolioValue, this.out);
          this.view.printPortfolioPerformance(portfolioPerformance, this.out);
          break;
        case "5":
          this.view.askDate(this.out);
          date = scan.nextLine();
          while (!user.dateChecker(date)) {
            this.view.askDateAgain(this.out);
            date = scan.nextLine();
            if (date.equals("0")) {
              loadPortfoliosController(scan, user);
              return;
            }
          }
          this.view.waitLoadMessage(this.out);
          portfolioValue = user.getPortfolioValue(name, date);
          if (portfolioValue == null) {
            this.view.printInCompatiblePortfolio(this.out);
            return;
          }
          this.view.printPortfolioValue(portfolioValue, this.out);
          break;
        case "6":
          loadPortfoliosController(scan, user);
          return;
        case "7":
          return;
        default:
          this.view.loadPortfolioErrorNote(this.out);
          option = scan.nextLine();
          comingFromDefault = true;
      }
      if (!comingFromDefault) {
        this.view.loadPortfolioDetailMenu(this.out);
        option = scan.nextLine().trim();
      }
      comingFromDefault = false;
    }
    while (!option.equals("7"));

  }


}
