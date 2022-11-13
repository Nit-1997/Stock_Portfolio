package controller.Commands;

import java.io.PrintStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Scanner;
import model.UserFlex;
import view.ViewPrint;

public class PerformanceGraph {

  public static void plotGraph(Scanner scan, PrintStream out, UserFlex user, String portfolioName){
    ViewPrint.askStartDateForGraph(out);
    String date1 = AskDate.addStocksAskDate(scan,out,user);
    if(date1==null) return;
    ViewPrint.askEndDateForGraph(out);
    String date2 = AskDate.addStocksAskDate(scan,out,user);
    if(date2==null) return;

    SimpleEntry<List<String>, SimpleEntry<List<Integer>,Integer>> data = user.getGraphData(date1,
        date2,portfolioName);

    if (data == null) {
      ViewPrint.graphInvalidRange(out);
      return;
    }

    List<String> labels = data.getKey();
    List<Integer> starPoints = data.getValue().getKey();
    int scale = data.getValue().getValue();


    ViewPrint.printGraph(out,date1,date2,portfolioName, labels, starPoints,scale);

  }

}
