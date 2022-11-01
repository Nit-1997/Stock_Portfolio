import controller.StockController;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
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