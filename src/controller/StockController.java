package controller;

import static java.lang.System.exit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import model.User;
import view.AddPortfolioPrint;
import view.WelcomePrint;

public class StockController {

  final Readable in;
  final Appendable out;

  public StockController(Readable in, Appendable out) {
    this.in = in;
    this.out = out;
  }

  public void go(User user) throws IOException {
    Objects.requireNonNull(user);
    WelcomePrint.welcomeNote();
    Scanner scan = new Scanner(this.in);
    int input = -1;
    while (input != 0) {
      WelcomePrint.printMenu();
      try {
        switch (scan.next()) {
          case "1":
            addStocksToPortfolioController(scan,user);
            break;
          case "2":
            break;
          case "0":
            exit(0);
            return;
        }
      } catch (Exception e) {
        WelcomePrint.errorNote();
        scan.next();
      }
    }
  }

  private void addStocksToPortfolioController(Scanner scan, User user) throws Exception {
    AddPortfolioPrint.addPortfolio();
    String name = scan.next();
    HashMap<String, Double> stocks = new HashMap<>();
    String ticker;
    double stockQuantity;
    AddPortfolioPrint.addStocksInPortfolioWelcomeNote(name);
    AddPortfolioPrint.askTickerSymbol();
    ticker = scan.next().toUpperCase();
    AddPortfolioPrint.askStockNumber();
    stockQuantity = scan.nextDouble();
    stocks.put(ticker, stockQuantity);
    AddPortfolioPrint.addStocksInPortfolioConfirmation();
    String confirmation = "";
    confirmation = scan.next();
    while (confirmation.equals("y") || confirmation.equals("Y")) {
      AddPortfolioPrint.stocksInPortfolioAddOrRemoveMenu();
      int option = scan.nextInt();
      try {
        switch (option) {
          case 1:
            AddPortfolioPrint.askTickerSymbol();
            ticker = scan.next().toUpperCase();
            AddPortfolioPrint.askStockNumber();
            stockQuantity = scan.nextDouble();
            stocks.put(ticker,stocks.getOrDefault(ticker,0.0)+stockQuantity);
            AddPortfolioPrint.addStocksInPortfolioConfirmation();
            confirmation = scan.next();
            break;
          case 2:
            AddPortfolioPrint.askTickerSymbol();
            ticker = scan.next().toUpperCase();
            AddPortfolioPrint.askStockNumber();
            stockQuantity = scan.nextDouble();
            if (stocks.containsKey(ticker) && stockQuantity<=stocks.get(ticker)) {
              if(stockQuantity<stocks.get(ticker)) stocks.put(ticker, stocks.get(ticker) - stockQuantity);
              else stocks.remove(ticker);
              AddPortfolioPrint.removeStocksInPortfolioSuccessfulConfirmation();
            } else {
              AddPortfolioPrint.removeStocksInPortfolioUnSuccessfulConfirmation();
            }
            confirmation = scan.next();
            break;
          case 3:
            confirmation="n";
            break;
        }
      } catch (Exception e) {
        AddPortfolioPrint.addStocksInPortfolioErrorNode();
        scan.next();
      }
    }
    //    System.out.println(stocks);
    boolean val = user.addPortfolio(name,stocks);
    if(val) AddPortfolioPrint.addStocksInPortfolioConfirmationLoading(name);


  }
}
