package control.gui;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import model.Model;
import view.gui.IView;


/**
 * Implements the features for callback from the view, and initializes the view when started.
 */
public class Controller implements Features {
  private Model model;
  private IView view;

  /**
   * New controller.
   *
   * @param m Model to use.
   */
  public Controller(Model m) {
    model = m;
  }

  /**
   * Provide the view with the callback functions.
   *
   * @param v View to give functions to.
   */
  @Override
  public void setView(IView v) {
    view = v;
    //provide view with all the callbacks
    view.addFeatures(this);
  }

  @Override
  public String newFlexiblePortInit() {
    String str = view.setUpNewPort(this);
    return str;
  }

  @Override
  public void getPerformance(String portfolioName, String startDate, String endDate) {
    Map<String, Boolean> map = model.getPortfolios();
    String output = "";

    output = "Performance of portfolio " + portfolioName + " from " + startDate + " to " + endDate
            + "\n\n";
    LocalDate startFin = null;
    LocalDate endFin = null;

    try {

      startFin = LocalDate.parse(startDate);
      endFin = LocalDate.parse(endDate);

    } catch (Exception e) {
      view.showError("Invalid Date");
    }
    try {
      Map<String, Double> map1;
      //50,000 > 1000*50
      map1 = model.getPerformance(portfolioName, startFin, endFin);
      double maxValue = Collections.max(map1.values());
      int scale = 1000;
      while ((int) maxValue > scale * 50) {
        scale *= 10;
      }
      //view.printGraphTitle(portName,startDate,endDate);


      for (Map.Entry<String, Double> entry : map1.entrySet()) {
        String asterisks = "";
        int scaled = (int) Math.round(entry.getValue() / scale);

        //greater than or equal to a number to fix the scale
        for (int i = 0; i < scaled; i++) {

          asterisks = asterisks + "*";

        }

        //view.printGraph(entry.getKey(), asterisks);
        output = output + entry.getKey() + ":          " + asterisks + "\n";
      }
      //view.printGraphScale(String.valueOf(scale));

      output = output + "Scale Per *: $" + String.valueOf(scale);
      view.showOutput(output);
    } catch (Exception e) {
      view.showError(e.getMessage());
    }
  }

  @Override
  public boolean checkDate(String date) {
    try {
      LocalDate.parse(date);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public boolean checkShares(String shares) {
    int val = 0;
    try {
      val = Integer.valueOf(shares);

    } catch (Exception e) {
      return false;
    }
    return checkNumber(shares) && checkValidInteger(shares) && val > 0;
  }

  @Override
  public boolean checkCommission(String commission) {
    double test;
    try {
      test = Double.parseDouble(commission);
      return test > 0.00;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public void newFixedStrategy(String portfolioName, String date, ArrayList<String> percent,
                               ArrayList<String> ticker, String commission, String shares) {
    LocalDate findate;
    double fincommission = 0.00;
    Map<String, Double> map1 = new HashMap<String, Double>();
    try {

      findate = LocalDate.parse(date);
      fincommission = Double.parseDouble(commission);

      for (int i = 0; i < percent.size(); i++) {
        map1.put(ticker.get(i), Double.valueOf(percent.get(i)));
      }

      model.newFixedAmountStrategy(portfolioName, map1, findate, Integer.valueOf(shares),
              fincommission);

    } catch (NumberFormatException e) {
      view.showError("Please enter information in all fields");
    } catch (DateTimeException e) {
      view.showError("Please Enter in a valid date");
    } catch (Exception e) {
      view.showError(e.getMessage());
    }
  }

  @Override
  public void newDollarCostStrategy(String portfolioName, String date, String interval,
                                    ArrayList<String> percent, ArrayList<String> ticker,
                                    String commission, String shares,
                                    String endDate) {
    String newPortName = portfolioName;
    if (portfolioName.equals("-- please select or create a portfolio --")) {
      newPortName = newFlexiblePortInit();

    }
    System.out.println("The new portName is " + newPortName);


    LocalDate findate;
    double fincommission = 0.00;
    Map<String, Double> map1 = new HashMap<String, Double>();
    System.out.println("We made it here to this class");
    try {

      findate = LocalDate.parse(date);
      fincommission = Double.parseDouble(commission);

      for (int i = 0; i < percent.size(); i++) {
        map1.put(ticker.get(i), Double.valueOf(percent.get(i)));
      }
      if (endDate.isEmpty()) {
        model.newDollarCostAverageStrategy(newPortName, map1, findate, Integer.valueOf(interval),
                Integer.valueOf(shares), fincommission);
      } else {
        model.newDollarCostAverageStrategy(newPortName, map1, findate, LocalDate.parse(endDate),
                Integer.valueOf(interval), Integer.valueOf(shares), fincommission);
      }

    } catch (NumberFormatException e) {
      view.showError("Please enter information in all fields");
    } catch (DateTimeException e) {
      view.showError("Please Enter in a valid date");
    } catch (Exception e) {
      view.showError(e.getMessage());
    }
  }


  @Override
  public void newFlexiblePort(String portfolioName) {
    //view.promptPortName();
    //String portfolioName = in.nextLine();

    try {
      model.newFlexiblePortfolio(portfolioName);
    } catch (Exception e) {
      view.showError(e.getMessage());
    }
    model.getFileHandler().writeFile(portfolioName, model.toString(portfolioName), ".JSON");
    view.showError("Successfully created " + portfolioName);
  }

  private boolean checkValidInteger(String s) {
    try {
      int temp = Integer.parseInt(s);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  private boolean checkNumber(String val) {
    return val.matches("-?\\d+");
  }

  @Override
  public void loadFileData(String fileIn, String portfolioName) {
    String global;
    String globalOutput = "";

    String output = "";
    global = fileIn;
    try {

      output = this.model.getFileHandler().readFile(fileIn);
      globalOutput = output;
    } catch (Exception e) {
      view.showError("Please enter in a valid file path");
    }

    String[] ticker2 = globalOutput.split("\n");
    //model.newFlexiblePortfolio(portfolioName);
    String[] tickers = globalOutput.split("\n");

    String[][] finOutput;
    List<String[]> data = new ArrayList<>();
    for (String line : tickers) {

      if (line.contains(":")) {
        data.add(line.split(":"));
      }

    }
    System.out.println("This is to test the tickers");

    for (int i = 0; i < tickers.length; i++) {

//      System.out.println(tickers[i]);
    }


    finOutput = data.toArray(new String[0][]);
    Map<String, Integer> map1 = new HashMap<String, Integer>();

    String finalTicker = "";
    String finalShares = "";
    String finalDate = "";
    String finalCommission = "";
    try {
      for (int i = 0; i < finOutput.length; i++) {

        if (finOutput[i][0].contains("ticker") || finOutput[i][0].contains("stocks")
                || finOutput[i][0].contains("date") || finOutput[i][0].contains("commission")) {
          if (finOutput[i][0].contains("ticker")) {

            finalTicker = finOutput[i][1].replace(",", "").replace(" ", "").replace("\r", "")
                    .replace("\"", "");

          } else if (finOutput[i][0].contains("stocks")) {
            finalShares = finOutput[i][1].replace(",", "").replace(" ", "").replace("\r", "")
                    .replace("\"", "");
          } else if (finOutput[i][0].contains("date")) {
            finalDate = finOutput[i][1].replace(",", "").replace(" ", "").replace("\r", "")
                    .replace("\"", "");
          } else if (finOutput[i][0].contains("commission")) {
            finalCommission = finOutput[i][1].replace(",", "").replace(" ", "").replace("\r", "")
                    .replace("\"", "");
            if (Integer.valueOf(finalShares) >= 0) {
              model.addStock(portfolioName, finalTicker, Integer.valueOf(finalShares),
                      LocalDate.parse(finalDate), Double.parseDouble(finalCommission));
            } else {
              model.sellStock(portfolioName, finalTicker, -1 * Integer.valueOf(finalShares),
                      LocalDate.parse(finalDate), Double.parseDouble(finalCommission));
            }
          }


        }
      }


      view.showOutput("Successfully Loaded Data");
    } catch (Exception e) {
      view.showError(e.getMessage());
    }

  }

  @Override
  public void relayPortName(String text) {
    try {
      System.out.print("Trying to make new portfolio");
      model.newFlexiblePortfolio(text);
      System.out.println("New Portfolio made: " + text);
    } catch (Exception e) {
      view.showError(e.getMessage());
    }
  }


  @Override
  public void viewPortfolio(String portChoice, String date) {
    System.out.println("We have reached view portfolio with " + portChoice + " and " + date);
    try {
      LocalDate finDate = LocalDate.parse(date);
      Map<String, Double> map1 = model.getValues(portChoice, finDate);
      Map<String, Double> map2 = model.getTickerMap(portChoice, finDate);
      double totValue = model.getPortValue(portChoice, finDate);
      //view.showPortfolio(portChoice, totValue, dateIn);
      //view.loadValues(portChoice,String.valueOf(totValue),date);
      String output = "";
      output = "Total Value of " + portChoice + " " + totValue + "\n\n";
      for (Map.Entry<String, Double> entry : map1.entrySet()) {
        output = output + "Ticker Symbol " + entry.getKey() + "\n Stock Shares Owned " +
                String.valueOf(map2.get(entry.getKey())) + "\n Stock Value: " +
                String.valueOf(entry.getValue()) + "\n Cost Basis " +
                String.valueOf(model.getCostBasis(portChoice, finDate)) + "\n";
      }
      view.showOutput(output);
    } catch (Exception e) {
      //Do Something
      System.out.println(e.getMessage());
      view.showError(e.getMessage());
    }
  }

  @Override
  public void buyStock(String portfolioName, String ticker, String numStocks, String date,
                       String commission) {

    try {
      LocalDate dateFin;
      Double finCommission;
      int finNumStocks;
      dateFin = LocalDate.parse(date);
      finCommission = Double.parseDouble(commission);
      finNumStocks = Integer.parseInt(numStocks);
      model.addStock(portfolioName, ticker, finNumStocks, dateFin, finCommission);
      view.showOutput("Successfully Bought Stock " + ticker + " on " + date);
    } catch (Exception e) {
      //Error handling pop up screen here
      //System.out.println(e.getMessage());
      view.showError(e.getMessage());
    }
    model.getFileHandler().writeFile(portfolioName, model.toString(portfolioName), ".JSON");

  }

  @Override
  public void sellStock(String portfolioName, String ticker, String numStocks, String date,
                        String commission) {
    try {
      LocalDate dateFin;
      Double finCommission;
      int finNumStocks;
      dateFin = LocalDate.parse(date);
      finCommission = Double.parseDouble(commission);
      finNumStocks = Integer.parseInt(numStocks);
      model.sellStock(portfolioName, ticker, finNumStocks, dateFin, finCommission);
      view.showOutput("Successfully Sold Stock " + ticker + " on " + date);
    } catch (Exception e) {
      //Error handling pop up screen here
      //System.out.println(e.getMessage());
      view.showError(e.getMessage());
    }
    model.getFileHandler().writeFile(portfolioName, model.toString(portfolioName), ".JSON");
  }

  @Override
  public Set<String> getStockNamesForReBalancing(String portfolioName, LocalDate date) {
    Set<String> stockList;
    try{
      stockList = model.getStocksOnDate(portfolioName,date);
    }catch (IllegalArgumentException e){
      return null;
    }
    return  stockList;
  }

  @Override
  public String reBalance(Map<String, Double> stockMap, String portfolioName, LocalDate date){
    try{
      model.reBalance(stockMap,portfolioName,date);
    } catch(Exception e){
      return e.getMessage();
    }
    return "Portfolio ReBalanced";
  }


  private void wait(int seconds) {
    try {
      TimeUnit.SECONDS.sleep(seconds);
    } catch (InterruptedException e) {
      //Continue normal execution
    }
  }
}
