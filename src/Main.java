import controller.StockController;
import controller.WelcomeController;


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
  public static void main(String[] args) {

    StockController controller = new WelcomeController(System.in, System.out);
    controller.start();


  }


}