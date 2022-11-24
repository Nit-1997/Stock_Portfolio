package controller;

import controller.commands.AddFlexPortfolio;
import controller.commands.LoadFlexPortfolio;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;
import model.UserFlex;
import view.IView;

/**
 * Controller class for flexible portfolio model.
 */
public class FlexController implements CategoryControllerInterface<UserFlex> {

  final InputStream in;
  final PrintStream out;

  IView view;

  /**
   * Constructs Object of the controller.
   *
   * @param in  Input Object used to take input from user.
   * @param out output object used to print the result.
   */
  public FlexController(InputStream in, PrintStream out, IView view) {
    this.in = in;
    this.out = out;
    this.view=view;
  }

  @Override
  public void start(UserFlex user) {
    Objects.requireNonNull(user);
    this.view.flexiblePortfolioHeader(this.out);
    this.view.printMenu(this.out);
    Scanner scan = new Scanner(this.in);
    boolean comingFromDefault = true;
    String option;
    while (true) {
      if (!comingFromDefault) {
        this.view.flexiblePortfolioHeader(this.out);
        this.view.printMenu(this.out);
      }
      comingFromDefault = false;
      option = scan.nextLine();
      switch (option) {
        case "1":
          AddFlexPortfolio.addStocksToPortfolioController(scan, user, this.out,this.view);
          break;
        case "2":
          LoadFlexPortfolio.loadPortfoliosController(scan, user, this.out,this.view);
          break;
        case "0":
          this.view.flexPortfolioExitMsg(this.out);
          return;
        default:
          this.view.errorNote(this.out);
          comingFromDefault = true;
      }
    }
  }
}
