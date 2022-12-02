package controller;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import model.UserFlexImpl;
import model.UserInflexImpl;
import view.ViewText;

/**
 * Text Controller for the application.
 */
public class TextController implements StockController {

  final InputStream in;
  final PrintStream out;
  final ViewText view;

  /**
   * Constructs Object of the controller.
   *
   * @param in  Input Object used to take input from user.
   * @param out output object used to print the result.
   * @param view view object.
   */
  public TextController(InputStream in, PrintStream out, ViewText view) {
    this.in = in;
    this.out = out;
    this.view = view;
  }

  @Override
  public void start() {

    Scanner scan = new Scanner(this.in);
    view.welcomeNote(this.out);

    boolean comingFromDefault = true;

    view.welcomeMenu(this.out);

    String option;
    while (true) {
      if (!comingFromDefault) {
        System.out.println();
        view.welcomeNote(this.out);
        view.welcomeMenu(this.out);
      }
      comingFromDefault = false;
      CategoryControllerInterface obj;
      option = scan.nextLine();
      switch (option) {
        case "1":
          obj = new FlexController(System.in, System.out, view);
          obj.start(new UserFlexImpl());
          break;
        case "2":
          obj = new InflexController(System.in, System.out, view);
          obj.start(new UserInflexImpl());
          break;
        case "0":
          new UserInflexImpl().cleanStockDirectory();
          view.exitNote(this.out);
          return;
        default:
          view.errorNote(this.out);
          comingFromDefault = true;
      }
    }
  }
}
