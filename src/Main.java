import controller.WelcomeController;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;
import model.UserFlexImpl;


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