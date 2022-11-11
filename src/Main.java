import controller.WelcomeController;
import java.util.ArrayList;
import java.util.List;
import model.StockOrder;
import model.StockOrderImpl;
import model.UserFlex;
import model.UserFlexImpl;
import utils.Utils;

/**
 * This is the starter class that needs to be run to start the application.
 */
public class Main {

  /**
   * Main function of the program, starter function of the application.
   *
   * @param args standard arguments
   * @throws Exception scanner exception.
   */
  public static void main(String[] args) throws Exception {

    new WelcomeController(System.in, System.out).start();
  }
}