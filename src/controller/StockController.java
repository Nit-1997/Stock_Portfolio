package controller;

import static java.lang.System.exit;

import java.io.IOException;
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

  final Readable in;
  final Appendable out;

  public StockController(Readable in, Appendable out) {
    this.in = in;
    this.out = out;
  }

  public void go(User user) throws Exception {
    Objects.requireNonNull(user);
    WelcomePrint.welcomeNote();
    Scanner scan = new Scanner(this.in);
    int input = -1;
    boolean comingFromDefault=false;
    while (input != 0) {
      if(!comingFromDefault)WelcomePrint.printMenu();
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
            WelcomePrint.errorNote();
            comingFromDefault=true;
        }
    }
  }

  private void addStocksToPortfolioController(Scanner scan, User user) throws Exception {
    AddPortfolioPrint.addPortfolio();
    String name = scan.next();
    HashMap<String, Double> stocksMap = new HashMap<>();
    String ticker;
    double stockQuantity;
    AddPortfolioPrint.addStocksInPortfolioWelcomeNote(name);
    String confirmation = null;
    int option = 1;
    boolean comingFromDefault=false;
    do {
      switch (option) {
        case 1:
          AddPortfolioPrint.askTickerSymbol();
          ticker = scan.next().toUpperCase();
          AddPortfolioPrint.askStockNumber();
          stockQuantity = scan.nextDouble();
          stocksMap.put(ticker, stocksMap.getOrDefault(ticker, 0.0) + stockQuantity);
          AddPortfolioPrint.addStocksInPortfolioConfirmation();
          confirmation = scan.next();
          break;
        case 2:
          AddPortfolioPrint.askTickerSymbol();
          ticker = scan.next().toUpperCase();
          AddPortfolioPrint.askStockNumber();
          stockQuantity = scan.nextDouble();
          if (stocksMap.containsKey(ticker) && stockQuantity <= stocksMap.get(ticker)) {
            if (stockQuantity < stocksMap.get(ticker)) {
              stocksMap.put(ticker, stocksMap.get(ticker) - stockQuantity);
            } else {
              stocksMap.remove(ticker);
            }
            AddPortfolioPrint.removeStocksInPortfolioSuccessfulConfirmation();
          } else {
            AddPortfolioPrint.removeStocksInPortfolioUnSuccessfulConfirmation();
          }
          confirmation = scan.next();
          break;
        case 3:
          confirmation = "n";
          break;
        default:
          AddPortfolioPrint.addStocksInPortfolioErrorNode();
          option = scan.nextInt();
          comingFromDefault=true;
      }

      if (!comingFromDefault && (confirmation.equals("y") || confirmation.equals("Y"))){
        AddPortfolioPrint.stocksInPortfolioAddOrRemoveMenu();
        option = scan.nextInt();
      }
      comingFromDefault=false;
    } while (confirmation.equals("y") || confirmation.equals("Y"));

    System.out.println(stocksMap);
    boolean val = user.addPortfolio(name, stocksMap);
    if (true) {
      AddPortfolioPrint.addStocksInPortfolioConfirmationLoading(name);
    }


  }

  private void loadPortfoliosController(Scanner scan, User user) {
    Set<String> portfolioNames = user.getPortfolios();

    LoadPortfolioPrint.printPortfolios(portfolioNames);
    LoadPortfolioPrint.loadPortfolioMenu();

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
          LoadPortfolioPrint.loadPortfolioErrorNote();
          option = scan.nextInt();
      }
    } while (option != 2);

  }

  private void loadSinglePortfolioDetailController(Scanner scan, User user) {
    LoadPortfolioPrint.askNameOfPortfolio();
    String name = scan.next();
    LoadPortfolioPrint.portfolioDetailWelcomeNote(name);
    LoadPortfolioPrint.loadPortfolioDetailMenu();
    int option = scan.nextInt();
    boolean comingFromDefault=false;
    do {
      switch (option) {
        case 1:
          String date = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(LocalDateTime.now());
          HashMap<String, Double> stockMap = user.getPortfolioSummary(name,date);
          LoadPortfolioPrint.printPortfolioSummary(stockMap);
          break;
        case 2:
          date = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(LocalDateTime.now());
          Map<String, List<Double>> detailedMap = user.getPortfolioDetailed(name,date);
          LoadPortfolioPrint.printPortfolioDetail(detailedMap);
          break;
        case 3:
          date = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(LocalDateTime.now());
          Double portfolioPnL = user.getPortfolioPnL(name,date);
          LoadPortfolioPrint.printPortfolioPerformance(portfolioPnL);
          break;
        case 4:
          LoadPortfolioPrint.askDate();
          date = scan.next();
          stockMap = user.getPortfolioSummary(name,date);
          LoadPortfolioPrint.printPortfolioSummary(stockMap);
          break;
        case 5:
          LoadPortfolioPrint.askDate();
          date = scan.next();
          detailedMap = user.getPortfolioDetailed(name,date);
          LoadPortfolioPrint.printPortfolioDetail(detailedMap);
          break;
        case 6:
          LoadPortfolioPrint.askDate();
          date = scan.next();
          portfolioPnL = user.getPortfolioPnL(name,date);
          LoadPortfolioPrint.printPortfolioPerformance(portfolioPnL);
          break;
        case 7:
          loadPortfoliosController(scan, user);
          return;
        case 8:
          return;
        default:
          LoadPortfolioPrint.loadPortfolioErrorNote();
          option = scan.nextInt();
          comingFromDefault=true;
      }
      if (!comingFromDefault) {
        LoadPortfolioPrint.loadPortfolioDetailMenu();
        option = scan.nextInt();
      }
      comingFromDefault=false;
    } while (option != 5);

  }


}
