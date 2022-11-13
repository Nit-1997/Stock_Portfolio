package controller.Commands;

import java.io.PrintStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Scanner;
import model.UserFlex;

public class PerformanceGraph {

  public static void plotGraph(Scanner scan, PrintStream out, UserFlex user, String portfolioName){
    System.out.println("Starting date");
    String date1 = AskDate.addStocksAskDate(scan,out,user);
    if(date1==null) return;
    System.out.println("End date");
    String date2 = AskDate.addStocksAskDate(scan,out,user);
    if(date2==null) return;

    SimpleEntry<List<String>, SimpleEntry<List<Integer>,Integer>> data = user.getGraphData(date1,date2,portfolioName);

    if (data == null) {
      System.out.println("\nInvalid date range");
      return;
    }

    List<String> labels = data.getKey();
    List<Integer> starPoints = data.getValue().getKey();
    int scale = data.getValue().getValue();


    System.out.println("\n\t\tPerformance of portfolio "+portfolioName+" from "+date1+" to "+date2);
    for(int i=0;i<labels.size();i++){
      System.out.print("\t"+labels.get(i)+" : ");
      for(int star=0;star<starPoints.get(i);star++)System.out.print("* ");
      System.out.println();
    }

    System.out.println("\n\t Scale : * = "+scale);

  }

}
