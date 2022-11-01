import controller.StockController;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import model.UserImpl;

public class Main {


  public static void main(String[] args) throws Exception {
    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String[] dates = {"2022-10-05","2022-10-04","2022-10-03","2022-09-30","2022-09-29","2022-09-28","2022-09-25"};
    String askedDate = "2022-09-26";
    for(String date : dates){
      if(sdf.parse(askedDate).compareTo(sdf.parse(date))>=0){
        System.out.println("required date "+date);
        break;
      }
    }

//    try {
//      new StockController(System.in, System.out).go(new UserImpl());
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
  }
}