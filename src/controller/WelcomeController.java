package controller;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import model.UserFlexImpl;
import model.UserInflexImpl;
import view.ViewPrint;

/**
 * Starting class for the application.
 */
public class WelcomeController implements StockController {

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
  public void start() {
    Scanner scan = new Scanner(this.in);
    ViewPrint.welcomeNote(this.out);

    boolean comingFromDefault = true;

    ViewPrint.welcomeMenu(this.out);

    String option;
    while (true) {
      if (!comingFromDefault) {
        System.out.println();
        ViewPrint.welcomeNote(this.out);
        ViewPrint.welcomeMenu(this.out);
      }
      comingFromDefault = false;
      CategoryControllerInterface obj;
      option = scan.nextLine();
      switch (option) {
        case "1":
          obj = new FlexController(System.in, System.out);
          obj.start(new UserFlexImpl());
          break;
        case "2":
          obj = new InflexController(System.in, System.out);
          obj.start(new UserInflexImpl());
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
