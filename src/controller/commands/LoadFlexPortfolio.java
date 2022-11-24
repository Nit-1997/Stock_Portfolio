package controller.commands;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.Set;
import model.UserFlex;
import view.IView;


/**
 * Class for main menu of the load controller.
 */
public class LoadFlexPortfolio {

  /**
   * main menu of the view controller.
   *
   * @param scan input object.
   * @param user model object.
   * @param out  output object.
   */
  public static void loadPortfoliosController(Scanner scan, UserFlex user, PrintStream out, IView view) {
    Set<String> portfolioNames = user.getPortfolios();
    if (portfolioNames != null) {
      view.printPortfolios(portfolioNames, out);
    }
    view.loadPortfolioMenu(out);

    String option = scan.nextLine().trim();

    do {
      switch (option) {
        case "1":
          LoadSingleFlexPortfolioDetail.loadSinglePortfolioDetailController(scan, user, out, view);
          option = "2";
          break;
        case "2":
          return;
        default:
          view.loadPortfolioErrorNote(out);
          option = scan.nextLine().trim();
      }
    }
    while (!option.equals("2"));

  }

}
