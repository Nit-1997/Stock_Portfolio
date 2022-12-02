package controller.commands;

import java.io.PrintStream;
import java.util.Scanner;

public class AskWeightOfStock {

  public static Double askWeight(PrintStream out, String ticker, Scanner scan){
    out.print(ticker+"(e to return to main menu) : ");
    String percent = scan.nextLine();
    if(percent.equals("e")) return -1.0;
    Double percentDouble;
    while(true){
      try{
        percentDouble = Double.parseDouble(percent);
        if(percentDouble>=100 || percentDouble<=0) throw new NumberFormatException();
        break;
      }catch(NumberFormatException e){
        out.print("Kindly enter a valid weight number from 0 to 100 (e to return to main menu) : ");
        percent = scan.nextLine();
        if(percent.equals("e")) return -1.0;
      }
    }
    return percentDouble;
  }

}
