import Utils.Utils;
import controller.StockController;
import java.io.IOException;
import model.UserImpl;

public class Main {


  public static void main(String[] args) throws Exception {

    System.out.println(Utils.getFileByName("abcd","portfolios"));

//    try {
//      new StockController(System.in, System.out).go(new UserImpl());
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
  }
}