package controller;

import static java.lang.System.exit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import model.User;
import view.Print;

public class StockController {

  final Readable in;
  final Appendable out;

  public StockController(Readable in, Appendable out) {
    this.in = in;
    this.out = out;
  }

  public void go(User user) throws IOException {
    Objects.requireNonNull(user);
    Print printer = new Print();
    printer.welcomeNote();
    Scanner scan = new Scanner(this.in);
    int input = -1;
    while (input != 0) {
      printer.printMenu();
      try {
        switch (scan.next()) {
          case "1":
            printer.addPortfolio();
            String name = scan.next();
            HashMap<String, Double> stocks = addStocksToPortfolioController(scan, printer,name);
            boolean val = user.addPortfolio(name,stocks);
            if(val) printer.addStocksInPortfolioConfirmationLoading(name);
            break;
          case "2":
            break;
          case "0":
            exit(0);
            return;
        }
      } catch (Exception e) {
        System.out.println("Please enter an integer value between 0 and 2");
        scan.next();
      }
    }
  }

  private HashMap addStocksToPortfolioController(Scanner scan, Print printer, String name) {
    HashMap<String, Double> stocks = new HashMap<>();
    String ticker;
    double stockQuantity;
    printer.addStocksInPortfolioWelcomeNote(name);
    printer.addStocksInPortfolioAskTickerSymbol();
    ticker = scan.next().toUpperCase();
    printer.addStocksInPortfolioAskStockNumber();
    stockQuantity = scan.nextDouble();
    stocks.put(ticker, stockQuantity);
    printer.addStocksInPortfolioConfirmation();
    String confirmation = "";
    confirmation = scan.next();
    while (confirmation.equals("y") || confirmation.equals("Y")) {
      printer.stocksInPortfolioAddOrRemoveMenu();
      int option = scan.nextInt();
      try {
        switch (option) {
          case 1:
            printer.addStocksInPortfolioAskTickerSymbol();
            ticker = scan.next().toUpperCase();
            printer.addStocksInPortfolioAskStockNumber();
            stockQuantity = scan.nextDouble();
            stocks.put(ticker,stocks.getOrDefault(ticker,0.0)+stockQuantity);
            printer.addStocksInPortfolioConfirmation();
            confirmation = scan.next();
            break;
          case 2:
            printer.removeStocksInPortfolioAskTickerSymbol();
            ticker = scan.next().toUpperCase();
            printer.removeStocksInPortfolioAskStockNumber();
            stockQuantity = scan.nextDouble();
            if (stocks.containsKey(ticker) && stockQuantity<=stocks.get(ticker)) {
              if(stockQuantity<stocks.get(ticker)) stocks.put(ticker, stocks.get(ticker) - stockQuantity);
              else stocks.remove(ticker);
              printer.removeStocksInPortfolioSuccessfulConfirmation();
            } else {
              printer.removeStocksInPortfolioUnSuccessfulConfirmation();
            }
            confirmation = scan.next();
            break;
          case 3:
            confirmation="n";
            break;
        }
      } catch (Exception e) {
        System.out.println("Please enter an integer value between 1 and 3");
        scan.next();
      }

    }

//    System.out.println(stocks);
    return stocks;
  }
}
