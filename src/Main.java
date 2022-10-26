import controller.StockController;
import java.io.IOException;
import java.io.InputStreamReader;
import model.UserImpl;

public class Main {

  public static void main(String[] args) {
    try {
      new StockController(new InputStreamReader(System.in), System.out).go(new UserImpl());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}