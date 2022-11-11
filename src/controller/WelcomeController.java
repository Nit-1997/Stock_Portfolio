package controller;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import model.UserFlexImpl;
import model.UserInflexImpl;
import view.ViewPrint;

public class WelcomeController implements StockController{

  final InputStream in;
  final PrintStream out;

  /**
   * Constructs Object of the controller.
   *
   * @param in  Input Object used to take input from user.
   * @param out output object used to print the result.
   */
  public WelcomeController(InputStream in, PrintStream out) {
    this.in = in;
    this.out = out;
  }

  @Override
  public void start() throws Exception {
    Scanner scan = new Scanner(this.in);
    ViewPrint.welcomeNote(this.out);

    boolean comingFromDefault = true;

    System.out.println("1. flex portfolio");
    System.out.println("2. inflex portfolio");
    System.out.println("0. Exit");
    System.out.print("Enter your choice: ");

    String option;
    while (true) {
      if (!comingFromDefault) {
        System.out.println();
        ViewPrint.welcomeNote(this.out);
        System.out.println("************************************************************");
        System.out.println("1. flex portfolio");
        System.out.println("2. inflex portfolio");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
      }
      comingFromDefault = false;
      option = scan.nextLine();
      switch (option) {
        case "1":
          new FlexController(System.in, System.out).start(new UserFlexImpl());
          break;
        case "2":
          new InflexController(System.in, System.out).start(new UserInflexImpl());
          break;
        case "0":
          new UserInflexImpl().cleanStockDirectory();
          ViewPrint.exitNote(this.out);
          return;
        default:
          ViewPrint.errorNote(this.out);
          comingFromDefault = true;
      }
    }
  }
}
