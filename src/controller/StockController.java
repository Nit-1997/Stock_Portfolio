package controller;

import static java.lang.System.exit;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
    boolean comingFromDefault=false;
    String option;
    while (true) {
      if(!comingFromDefault)WelcomePrint.printMenu(this.out);
      comingFromDefault=false;
      option = scan.next().trim();
        switch (option) {
          case "1":
            addStocksToPortfolioController(scan, user);
            break;
          case "2":
            loadPortfoliosController(scan, user);
            break;
          case "0":
            exit(0);
            return;
          default:
            WelcomePrint.errorNote(this.out);
            comingFromDefault=true;
        }
    }
  }

  private void addStocksToPortfolioController(Scanner scan, User user) throws Exception {
    AddPortfolioPrint.addPortfolio(this.out);
    String name = scan.next();
    while(!user.isUniqueName(name)){
      AddPortfolioPrint.askPortfolioNameAgain(this.out);
      name = scan.next();
      if(name.equals("0")) return;
    }
    HashMap<String, Double> stocksMap = new HashMap<>();
    String ticker;
    String stockQuantity;
    AddPortfolioPrint.addStocksInPortfolioWelcomeNote(name,this.out);
    AddPortfolioPrint.printAvailableStocks(user.getStockList());
    String confirmation = null;
    String option = "1";
    boolean comingFromDefault=false;
    do {
      switch (option) {
        case "1":
          AddPortfolioPrint.askTickerSymbol(this.out);
          do{
            ticker = scan.next().toUpperCase();
            if(ticker.equals("0")) return;
            if(!user.isValidStock(ticker))System.out.print("Not a valid stock name, kindly enter correct stock name from above list (0 to return to main menu): ");
          }while(!user.isValidStock(ticker));
          AddPortfolioPrint.askStockNumber(this.out);
          stockQuantity = scan.next().trim();
          double stockQuanDouble;
          while(true){
            try{
              stockQuanDouble=Double.parseDouble(stockQuantity);
              if(stockQuanDouble<=0) throw new NumberFormatException();
                break;
            } catch(NumberFormatException e){
              System.out.println();
              System.out.print("Number entered is not in correct format, please enter again: ");
              stockQuantity = scan.next().trim();
            }
          }
          stocksMap.put(ticker, stocksMap.getOrDefault(ticker, 0.0) + stockQuanDouble);
          AddPortfolioPrint.addStocksInPortfolioConfirmation(this.out);
          confirmation = scan.next();
          while(!confirmation.equals("y") && !confirmation.equals("Y") && !confirmation.equals("n") && !confirmation.equals("N")){
            AddPortfolioPrint.askconfirmation(this.out);
            confirmation = scan.next();
          }
          break;
        case "2":
          AddPortfolioPrint.askTickerSymbol(this.out);
          do{
            ticker = scan.next().toUpperCase();
            if(ticker.equals("0")) return;
            if(!stocksMap.containsKey(ticker))System.out.print("Portfolio doesn't contain this stock, enter again:  (0 to return to main menu): ");
          }while(!stocksMap.containsKey(ticker));
          AddPortfolioPrint.askStockNumber(this.out);
          stockQuantity = scan.next().trim();

          while(true){
            try{
              stockQuanDouble=Double.parseDouble(stockQuantity);
              if(stockQuanDouble<=0) throw new NumberFormatException();
              break;
            } catch(NumberFormatException e){
              System.out.println();
              System.out.print("Number entered is not in correct format, please enter again: ");
              stockQuantity = scan.nextLine();
            }
          }
          if (stocksMap.containsKey(ticker) && stockQuanDouble <= stocksMap.get(ticker)) {
            if (stockQuanDouble < stocksMap.get(ticker)) {
              stocksMap.put(ticker, stocksMap.get(ticker) - stockQuanDouble);
            } else {
              stocksMap.remove(ticker);
            }
            AddPortfolioPrint.removeStocksInPortfolioSuccessfulConfirmation(this.out);
          } else {
            AddPortfolioPrint.removeStocksInPortfolioUnSuccessfulConfirmation(this.out);
          }
          confirmation = scan.next();
          while(!confirmation.equals("y") && !confirmation.equals("Y") && !confirmation.equals("n") && !confirmation.equals("N")){
            AddPortfolioPrint.askconfirmation(this.out);
            confirmation = scan.next();
          }
          break;
        case "3":
          confirmation = "n";
          break;
        default:
          AddPortfolioPrint.addStocksInPortfolioErrorNode(this.out);
          option = scan.next().trim();
          comingFromDefault=true;
      }

      if (!comingFromDefault && (confirmation.equals("y") || confirmation.equals("Y"))){
        AddPortfolioPrint.stocksInPortfolioAddOrRemoveMenu(this.out);
        option = scan.next().trim();
      }
      comingFromDefault=false;
    } while (confirmation.equals("y") || confirmation.equals("Y"));

    System.out.println(stocksMap);
    AddPortfolioPrint.waitMessage(this.out);
    if(user.ctrlCPressedChecker(name)){
      AddPortfolioPrint.ctrlCPressedMessage(this.out);
    }
    boolean val = user.addPortfolio(name, stocksMap);
    if (val) {
      AddPortfolioPrint.addStocksInPortfolioConfirmationLoading(name, this.out);
    }


  }

  private void loadPortfoliosController(Scanner scan, User user) throws Exception {
    Set<String> portfolioNames = user.getPortfolios();

    LoadPortfolioPrint.printPortfolios(portfolioNames, this.out);
    LoadPortfolioPrint.loadPortfolioMenu(this.out);

    String option = scan.next().trim();

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
          option = scan.next().trim();
      }
    } while (option != "2");

  }

  private void loadSinglePortfolioDetailController(Scanner scan, User user) throws Exception {
    LoadPortfolioPrint.askNameOfPortfolio(this.out);
    String name = scan.next();
    while(user.isUniqueName(name)){
      LoadPortfolioPrint.askPortfolioNameAgainUnique(this.out);
      name = scan.next();
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
          Map<String, Double> stockMap = user.getPortfolioSummary(name);
          LoadPortfolioPrint.printPortfolioSummary(stockMap, this.out);
          break;
        case "2":
          String date = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(LocalDateTime.now());
          System.out.println(date);
          Map<String, List<Double>> detailedMap = user.getPortfolioDetailed(name,date);
          double portfolioValue = user.getPortfolioValue(name,date);
          double portfolioPerformance = user.getPortfolioPnL(name, date);
          LoadPortfolioPrint.printPortfolioDetail(detailedMap,portfolioValue, this.out);
          LoadPortfolioPrint.printPortfolioPerformance(portfolioPerformance, this.out);
          break;
        case "3":
          date = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(LocalDateTime.now());
          portfolioValue = user.getPortfolioValue(name,date);
          LoadPortfolioPrint.printPortfolioValue(portfolioValue, this.out);
          break;
        case "4":
          LoadPortfolioPrint.askDate(this.out);
          date = scan.next();
          while(!user.dateChecker(date)){
            LoadPortfolioPrint.askDateAgain(this.out);
            date = scan.next();
            if(date.equals("0")){
              loadPortfoliosController(scan, user);
              return;
            }
          }
          detailedMap = user.getPortfolioDetailed(name,date);
          portfolioValue = user.getPortfolioValue(name,date);
          portfolioPerformance = user.getPortfolioPnL(name, date);
          LoadPortfolioPrint.printPortfolioDetail(detailedMap,portfolioValue, this.out);
          LoadPortfolioPrint.printPortfolioPerformance(portfolioPerformance, this.out);
          break;
        case "5":
          LoadPortfolioPrint.askDate(this.out);
          date = scan.next();
          while(!user.dateChecker(date)){
            LoadPortfolioPrint.askDateAgain(this.out);
            date = scan.next();
            if(date.equals("0")){
              loadPortfoliosController(scan, user);
              return;
            }
          }
          portfolioValue = user.getPortfolioValue(name,date);
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
        option = scan.nextLine();
      }
      comingFromDefault=false;
    } while (option != "7");

  }


}
