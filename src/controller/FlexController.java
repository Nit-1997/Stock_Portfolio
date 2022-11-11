package controller;

import controller.Commands.AddFlexPortfolio;
import controller.Commands.LoadFlexPortfolio;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;
import model.UserFlex;
import view.ViewPrint;

public class FlexController {

  final InputStream in;
  final PrintStream out;

  /**
   * Constructs Object of the controller.
   *
   * @param in  Input Object used to take input from user.
   * @param out output object used to print the result.
   */
  public FlexController(InputStream in, PrintStream out) {
    this.in = in;
    this.out = out;
  }

  public void start(UserFlex user) {
    Objects.requireNonNull(user);
    System.out.println("-------------Flexible Portfolio Menu-------------");
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
          AddFlexPortfolio.addStocksToPortfolioController(scan, user,this.out);
          break;
        case "2":
          LoadFlexPortfolio.loadPortfoliosController(scan, user, this.out);
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

}
