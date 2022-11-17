package controller;

import controller.commands.AddFlexPortfolio;
import controller.commands.LoadFlexPortfolio;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;
import model.UserFlex;
import view.ViewPrint;

/**
 * Controller class for flexible portfolio model.
 */
public class FlexController implements CategoryControllerInterface<UserFlex> {

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

  @Override
  public void start(UserFlex user) {
    Objects.requireNonNull(user);
    ViewPrint.flexiblePortfolioHeader(this.out);
    ViewPrint.printMenu(this.out);
    Scanner scan = new Scanner(this.in);
    boolean comingFromDefault = true;
    String option;
    while (true) {
      if (!comingFromDefault) {
        ViewPrint.flexiblePortfolioHeader(this.out);
        ViewPrint.printMenu(this.out);
      }
      comingFromDefault = false;
      option = scan.nextLine();
      switch (option) {
        case "1":
          AddFlexPortfolio.addStocksToPortfolioController(scan, user, this.out);
          break;
        case "2":
          LoadFlexPortfolio.loadPortfoliosController(scan, user, this.out);
          break;
        case "0":
          ViewPrint.flexPortfolioExitMsg(this.out);
          return;
        default:
          ViewPrint.errorNote(this.out);
          comingFromDefault = true;
      }
    }
  }
}
