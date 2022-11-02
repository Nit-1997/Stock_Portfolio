import controller.StockController;
import java.io.IOException;
import model.UserImpl;

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

    try {
      new StockController(System.in, System.out).start(new UserImpl());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}