import controller.StockController;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import model.UserImpl;

public class Main {


  public static void main(String[] args) throws Exception {

    try {
      new StockController(System.in, System.out).go(new UserImpl());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}