package controller;

import static java.lang.System.exit;

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
import view.AddPortfolioPrint;
import view.LoadPortfolioPrint;
import view.WelcomePrint;

public class StockController {

  final InputStream in;
  final PrintStream out;

  public StockController(InputStream in, PrintStream out) {
    this.in = in;
    this.out = out;
  }

  public void go(User user) throws Exception {
    Objects.requireNonNull(user);
    WelcomePrint.welcomeNote(this.out);
    Scanner scan = new Scanner(this.in);
    boolean comingFromDefault=true;
    WelcomePrint.printMenu(this.out);
    String option;
    while (true) {
      if(!comingFromDefault){
        WelcomePrint.printMenu(this.out);
      }
      comingFromDefault=false;
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
            exit(0);
            return;
          default:
            WelcomePrint.errorNote(this.out);
            comingFromDefault=true;
        }
    }
  }

  private String addStocksAskTicker(Scanner scan, User user){
    AddPortfolioPrint.askTickerSymbol(this.out);
    String ticker;
    do{
      ticker = scan.nextLine().toUpperCase();
      if(ticker.equals("0")) return null;
      if(!user.isValidStock(ticker))AddPortfolioPrint.askTickerSymbolAgain(this.out);
    }while(!user.isValidStock(ticker));
    return ticker;
  }

  private Double addStocksAskStockNumber(Scanner scan){
    AddPortfolioPrint.askStockNumber(this.out);
    String stockQuantity = scan.nextLine();
    double stockQuanDouble;
    do{
      try{
        stockQuanDouble=Double.parseDouble(stockQuantity);
        if(stockQuanDouble<=0 || stockQuanDouble!=(int)stockQuanDouble) throw new NumberFormatException();
        break;
      } catch(NumberFormatException e){
        AddPortfolioPrint.askStockNumberAgain(this.out);
        stockQuantity = scan.nextLine();
        if(stockQuantity.equals("0")) return null;
      }
    }while(true);

    return stockQuanDouble;
  }

  private void addStocksToPortfolioController(Scanner scan, User user) throws Exception {
    AddPortfolioPrint.addPortfolio(this.out);
    String name = scan.nextLine();
    while(!user.isUniqueName(name)){
      AddPortfolioPrint.askPortfolioNameAgain(this.out);
      name = scan.nextLine();
      if(name.equals("0")) return;
    }
    HashMap<String, Double> stocksMap = new HashMap<>();
    String ticker = null;

    AddPortfolioPrint.addStocksInPortfolioWelcomeNote(name,this.out);
    AddPortfolioPrint.printAvailableStocks(user.getStockList());
    String confirmation = null;
    String option = "1";
    boolean comingFromDefault=false;

    do {
      switch (option) {
        case "1":
          ticker = addStocksAskTicker(scan, user);
          if(ticker==null) return;
          Double stockQuanDouble = addStocksAskStockNumber( scan);
          if(stockQuanDouble==null) return;
          stocksMap.put(ticker, stocksMap.getOrDefault(ticker, 0.0) + stockQuanDouble);
          AddPortfolioPrint.addStocksInPortfolioConfirmation(this.out);
          confirmation = scan.nextLine().trim();
          while(!confirmation.equals("y") && !confirmation.equals("Y") && !confirmation.equals("n") && !confirmation.equals("N")){
            AddPortfolioPrint.askconfirmation(this.out);
            confirmation = scan.nextLine().trim();
          }
          break;
        case "2":
          ticker = addStocksAskTicker(scan, user);
          if(ticker==null) break;
          stockQuanDouble = addStocksAskStockNumber(scan);
          if(stockQuanDouble==null) break;
          if (stocksMap.containsKey(ticker) && stockQuanDouble < stocksMap.get(ticker)) {
              stocksMap.put(ticker, stocksMap.get(ticker) - stockQuanDouble);
              AddPortfolioPrint.removeStocksInPortfolioSuccessfulConfirmation(this.out);
            } else if(stocksMap.containsKey(ticker) && stockQuanDouble == stocksMap.get(ticker)) {
              stocksMap.remove(ticker);
              AddPortfolioPrint.removeStocksInPortfolioSuccessfulConfirmation(this.out);
            } else {
              AddPortfolioPrint.removeStocksInPortfolioUnSuccessfulConfirmation(this.out);
          }
          confirmation = scan.nextLine();
          while(!confirmation.equals("y") && !confirmation.equals("Y") && !confirmation.equals("n") && !confirmation.equals("N")){
            AddPortfolioPrint.askconfirmation(this.out);
            confirmation = scan.nextLine();
          }
          break;
        case "3":
          confirmation = "n";
          break;
        default:
          AddPortfolioPrint.addStocksInPortfolioErrorNode(this.out);
          option = scan.nextLine().trim();
          comingFromDefault=true;
      }

      if (!comingFromDefault && (confirmation.equals("y") || confirmation.equals("Y"))){
        AddPortfolioPrint.stocksInPortfolioAddOrRemoveMenu(this.out);
        option = scan.nextLine().trim();
      }
      comingFromDefault=false;
    } while (confirmation.equals("y") || confirmation.equals("Y"));

//    System.out.println(stocksMap);
    AddPortfolioPrint.waitMessage(this.out);
    boolean val = user.addPortfolio(name, stocksMap);
    if (val) {
      AddPortfolioPrint.addStocksInPortfolioConfirmationLoading(name, this.out);
    }
  }

  private void loadPortfoliosController(Scanner scan, User user) throws Exception {
    Set<String> portfolioNames = user.getPortfolios();

    LoadPortfolioPrint.printPortfolios(portfolioNames, this.out);
    LoadPortfolioPrint.loadPortfolioMenu(this.out);

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
          LoadPortfolioPrint.loadPortfolioErrorNote(this.out);
          option = scan.nextLine().trim();
      }
    } while (option != "2");

  }

  private void loadSinglePortfolioDetailController(Scanner scan, User user) throws Exception {
    LoadPortfolioPrint.askNameOfPortfolio(this.out);
    String name = scan.nextLine();
    while(user.isUniqueName(name)){
      LoadPortfolioPrint.askPortfolioNameAgainUnique(this.out);
      name = scan.nextLine();
      if(name.equals("0")){
        loadPortfoliosController(scan, user);
        return;
      }
    }
    LoadPortfolioPrint.portfolioDetailWelcomeNote(name, this.out);
    LoadPortfolioPrint.loadPortfolioDetailMenu(this.out);
    String option = scan.nextLine().trim();
    boolean comingFromDefault=false;
    do {
      switch (option) {
        case "1":
          LoadPortfolioPrint.waitMessage(this.out);
          Map<String, Double> stockMap = user.getPortfolioSummary(name);
          if(stockMap==null){
            LoadPortfolioPrint.printInCompatiblePortfolio(this.out);
            return;
          }
          LoadPortfolioPrint.printPortfolioSummary(stockMap, this.out);
          break;
        case "2":
          String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
          LoadPortfolioPrint.waitMessage(this.out);
          Map<String, List<Double>> detailedMap = user.getPortfolioDetailed(name,date);
          if(detailedMap==null){
            LoadPortfolioPrint.printInCompatiblePortfolio(this.out);
            return;
          }
          Double portfolioValue = user.getPortfolioValue(name,date);
          double portfolioPerformance = user.getPortfolioPnL(name, date);
          LoadPortfolioPrint.printPortfolioDetail(detailedMap,portfolioValue, this.out);
          LoadPortfolioPrint.printPortfolioPerformance(portfolioPerformance, this.out);
          break;
        case "3":
          date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
          LoadPortfolioPrint.waitMessage(this.out);
          portfolioValue = user.getPortfolioValue(name,date);
          if(portfolioValue==null){
            LoadPortfolioPrint.printInCompatiblePortfolio(this.out);
            return;
          }
          LoadPortfolioPrint.printPortfolioValue(portfolioValue, this.out);
          break;
        case "4":
          LoadPortfolioPrint.askDate(this.out);
          date = scan.nextLine();
          while(!user.dateChecker(date)){
            LoadPortfolioPrint.askDateAgain(this.out);
            date = scan.nextLine();
            if(date.equals("0")){
              loadPortfoliosController(scan, user);
              return;
            }
          }
          LoadPortfolioPrint.waitMessage(this.out);
          detailedMap = user.getPortfolioDetailed(name,date);
          if(detailedMap==null){
            LoadPortfolioPrint.printInCompatiblePortfolio(this.out);
            return;
          }
          portfolioValue = user.getPortfolioValue(name,date);
          portfolioPerformance = user.getPortfolioPnL(name, date);
          LoadPortfolioPrint.printPortfolioDetail(detailedMap,portfolioValue, this.out);
          LoadPortfolioPrint.printPortfolioPerformance(portfolioPerformance, this.out);
          break;
        case "5":
          LoadPortfolioPrint.askDate(this.out);
          date = scan.nextLine();
          while(!user.dateChecker(date)){
            LoadPortfolioPrint.askDateAgain(this.out);
            date = scan.nextLine();
            if(date.equals("0")){
              loadPortfoliosController(scan, user);
              return;
            }
          }
          LoadPortfolioPrint.waitMessage(this.out);
          portfolioValue = user.getPortfolioValue(name,date);
          if(portfolioValue==null){
            LoadPortfolioPrint.printInCompatiblePortfolio(this.out);
            return;
          }
          LoadPortfolioPrint.printPortfolioValue(portfolioValue, this.out);
          break;
        case "6":
          loadPortfoliosController(scan, user);
          return;
        case "7":
          return;
        default:
          LoadPortfolioPrint.loadPortfolioErrorNote(this.out);
          option = scan.nextLine();
          comingFromDefault=true;
      }
      if (!comingFromDefault) {
        LoadPortfolioPrint.loadPortfolioDetailMenu(this.out);
        option = scan.nextLine().trim();
      }
      comingFromDefault=false;
    } while (option != "7");

  }


}
