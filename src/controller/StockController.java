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
    int input = -1;
    boolean comingFromDefault=false;
    while (input != 0) {
      if(!comingFromDefault)WelcomePrint.printMenu(this.out);
      comingFromDefault=false;
        switch (scan.next()) {
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
    }
    HashMap<String, Double> stocksMap = new HashMap<>();
    String ticker;
    double stockQuantity;
    AddPortfolioPrint.addStocksInPortfolioWelcomeNote(name,this.out);
    String confirmation = null;
    int option = 1;
    boolean comingFromDefault=false;
    do {
      switch (option) {
        case 1:
          AddPortfolioPrint.askTickerSymbol(this.out);
          ticker = scan.next().toUpperCase();
          AddPortfolioPrint.askStockNumber(this.out);
          stockQuantity = scan.nextDouble();
          stocksMap.put(ticker, stocksMap.getOrDefault(ticker, 0.0) + stockQuantity);
          AddPortfolioPrint.addStocksInPortfolioConfirmation(this.out);
          confirmation = scan.next();
          break;
        case 2:
          AddPortfolioPrint.askTickerSymbol(this.out);
          ticker = scan.next().toUpperCase();
          AddPortfolioPrint.askStockNumber(this.out);
          stockQuantity = scan.nextDouble();
          if (stocksMap.containsKey(ticker) && stockQuantity <= stocksMap.get(ticker)) {
            if (stockQuantity < stocksMap.get(ticker)) {
              stocksMap.put(ticker, stocksMap.get(ticker) - stockQuantity);
            } else {
              stocksMap.remove(ticker);
            }
            AddPortfolioPrint.removeStocksInPortfolioSuccessfulConfirmation(this.out);
          } else {
            AddPortfolioPrint.removeStocksInPortfolioUnSuccessfulConfirmation(this.out);
          }
          confirmation = scan.next();
          break;
        case 3:
          confirmation = "n";
          break;
        default:
          AddPortfolioPrint.addStocksInPortfolioErrorNode(this.out);
          option = scan.nextInt();
          comingFromDefault=true;
      }

      if (!comingFromDefault && (confirmation.equals("y") || confirmation.equals("Y"))){
        AddPortfolioPrint.stocksInPortfolioAddOrRemoveMenu(this.out);
        option = scan.nextInt();
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

  private void loadPortfoliosController(Scanner scan, User user) {
    Set<String> portfolioNames = user.getPortfolios();

    LoadPortfolioPrint.printPortfolios(portfolioNames, this.out);
    LoadPortfolioPrint.loadPortfolioMenu(this.out);

    int option = scan.nextInt();

    do {
      switch (option) {
        case 1:
          loadSinglePortfolioDetailController(scan, user);
          option = 2;
          break;
        case 2:
          break;
        default:
          LoadPortfolioPrint.loadPortfolioErrorNote(this.out);
          option = scan.nextInt();
      }
    } while (option != 2);

  }

  private void loadSinglePortfolioDetailController(Scanner scan, User user) {
    LoadPortfolioPrint.askNameOfPortfolio(this.out);
    String name = scan.next();
    LoadPortfolioPrint.portfolioDetailWelcomeNote(name, this.out);
    LoadPortfolioPrint.loadPortfolioDetailMenu(this.out);
    int option = scan.nextInt();
    boolean comingFromDefault=false;
    do {
      switch (option) {
        case 1:
          Map<String, Double> stockMap = user.getPortfolioSummary(name);
          LoadPortfolioPrint.printPortfolioSummary(stockMap, this.out);
          break;
        case 2:
          String date = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(LocalDateTime.now());
          Map<String, List<Double>> detailedMap = user.getPortfolioDetailed(name,date);
          double portfolioValue = user.getPortfolioValue(name,date);
          LoadPortfolioPrint.printPortfolioDetail(detailedMap,portfolioValue, this.out);
          break;
        case 3:
          date = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(LocalDateTime.now());
          Double portfolioPnL = user.getPortfolioPnL(name,date);
          LoadPortfolioPrint.printPortfolioPerformance(portfolioPnL, this.out);
          break;
        case 4:
          LoadPortfolioPrint.askDate(this.out);
          date = scan.next();
          detailedMap = user.getPortfolioDetailed(name,date);
          portfolioValue = user.getPortfolioValue(name,date);
          LoadPortfolioPrint.printPortfolioDetail(detailedMap,portfolioValue, this.out);
          break;
        case 5:
          LoadPortfolioPrint.askDate(this.out);
          date = scan.next();
          portfolioPnL = user.getPortfolioPnL(name,date);
          LoadPortfolioPrint.printPortfolioPerformance(portfolioPnL, this.out);
          break;
        case 6:
          loadPortfoliosController(scan, user);
          return;
        case 7:
          return;
        default:
          LoadPortfolioPrint.loadPortfolioErrorNote(this.out);
          option = scan.nextInt();
          comingFromDefault=true;
      }
      if (!comingFromDefault) {
        LoadPortfolioPrint.loadPortfolioDetailMenu(this.out);
        option = scan.nextInt();
      }
      comingFromDefault=false;
    } while (option != 7);

  }


}
