package controller;

import static java.lang.System.exit;

import java.io.IOException;
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
    int input=-1;
    while (input!=0) {
      printer.printMenu();
      try{
        switch (scan.next()) {
          case "1":
            printer.addPortfolio();
            String name = scan.next();
            addStocksToPortfolioController(scan,name,printer);
            break;
          case "2":
            break;
          case "0": exit(0);
            return;
        }
      }
      catch(Exception e){
        System.out.println("Please enter an integer value between 0 and 2");
        scan.next();
      }
    }
  }

  private void addStocksToPortfolioController(Scanner scan, String name, Print printer){
    printer.addStocksInPortfolio(name);

  }
}
