package control.terminal;

import java.util.Set;
import model.Model;
import view.terminal.StockView;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Used for running the stock program in a terminal.
 */
public class  TerminalController implements StockController {
  private final Scanner in;
  private final StockView view;

  private final Model model;
  private boolean running;


  /**
   * Create a bew TerminalController for running the program in a terminal.
   * .
   *
   * @param in   InputStream to take user input.
   * @param view StockView to output the program.
   */
  public TerminalController(Model model, InputStream in, StockView view) {
    this.model = model;
    this.in = new Scanner(in);
    this.view = view;
    this.running = true;
  }

  @Override
  public void gogogadget() {

    while (running) {

      view.showMainMenu();
      String command = in.nextLine().toUpperCase();
      switch (command) {
        case "Q":
          view.clearScreen();
          view.quit();
          this.running = false;
          wait(1);
          break;

        case "F":

          newFlexiblePort();
          break;
        case "N":

          newPortfolio();
          break;
        case "V":

          viewPortfolio();
          break;

        case "L":
          //Load from main menu the file containing
          //prompt for portfolio name
          //prompt for file name
          //load the data
          // add Stock
          loadFileData();
          break;

        case "P":
          viewPerformance();
          break;

        case "B":
          //buy method
          buyStock();
          break;
        case "S":
          //sell method

          sellStock();
          break;
        case "R":
          // rebalance
          reBalance();
          break;
        default:
          view.showError(new String[]{"Command not recognized: "
                  + command, "Press any key to continue"});
          command = in.nextLine();
          break;


      }

    }
  }

  /**
   * Pause execution for a number of seconds.
   *
   * @param seconds Seconds to pause for.
   */
  private void wait(int seconds) {
    try {
      TimeUnit.SECONDS.sleep(seconds);
    } catch (InterruptedException e) {
      //Continue normal execution
    }
  }

  public void reBalance() {
    if (model.getPortfolios().size() == 0) {
      view.showError(new String[]{"You must create a portfolio before viewing"});
      return;
    }

    view.enterNamePort();
    Map<String, Boolean> map = model.getPortfolios();
    for (Map.Entry<String, Boolean> entry : map.entrySet()) {
      view.printPortfolios(entry.getKey());
    }
    String portChoice = "";
    while (true) {
      try {

        portChoice = in.nextLine();
        model.isFlexible(portChoice);
        break;
      } catch (Exception e) {
        view.showError(new String[]{e.getMessage()});
        view.promptWhichPort();
      }
    }

    LocalDate date = gatherDate(portChoice);

    Set<String> stockNames = model.getStocksOnDate(portChoice,date);

    while(stockNames.size()==0){
      System.out.println("No stocks in portfolio on that date, kindly enter again");
      date = gatherDate(portChoice);
      stockNames = model.getStocksOnDate(portChoice,date);
    }

    Map<String, Double> stockMap = new HashMap<>();

    System.out.println("Enter percentages : ");
    for(String stock : stockNames){
      System.out.print(stock+" : ");
      String weight = in.nextLine();
      while(!checkValidInteger(weight)){
        System.out.print("kindly enter percentage in integer format : ");
        weight = in.nextLine();
        if(checkValidInteger(weight)) break;
      }
      stockMap.put(stock,Double.parseDouble(weight));
    }

    try {
      model.reBalance(stockMap,portChoice,date);
      System.out.println("Portfolio rebalanced");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }


  private boolean checkNumber(String val) {
    return val.matches("-?\\d+");
  }

  @Override
  public void viewPerformance() {
    Map<String, Boolean> map = model.getPortfolios();
    view.promptWhichPort();

    for (Map.Entry<String, Boolean> entry : map.entrySet()) {
        view.printPortfolios(entry.getKey());
    }

    String portName = in.nextLine();

    LocalDate startFin = null;
    LocalDate endFin = null;
    String startDate;
    String endDate;
    while (true) {
      try {
        view.promptStartDate();

        startDate = in.nextLine();
        startFin = LocalDate.parse(startDate);
        view.promptEndDate();

        endDate = in.nextLine();
        endFin = LocalDate.parse(endDate);

        break;
      } catch (Exception e) {
        view.showError(new String[]{"Please enter a valid date"});
      }
    }
    try {
      Map<String, Double> map1;
      //50,000 > 1000*50
      map1 = model.getPerformance(portName,startFin,endFin);
      double maxValue = Collections.max(map1.values());
      int scale = 1000;
      while ((int)maxValue > scale*50) {
        scale*=10;
      }
      view.printGraphTitle(portName,startDate,endDate);
      for (Map.Entry<String, Double> entry : map1.entrySet()) {
        String asterisks = "";
        int scaled = (int) Math.round(entry.getValue() / scale);

        //greater than or equal to a number to fix the scale
        for (int i = 0; i < scaled; i++) {

          asterisks = asterisks + "*";

        }

        view.printGraph(entry.getKey(), asterisks);
      }
      view.printGraphScale(String.valueOf(scale));
    } catch (Exception e) {
      view.showError(new String[] {e.getMessage()});
    }

  }

  @Override
  public double getCommission() {
    double finCommiss = 0.00;
    while (true) {
      view.promptCommission();
      String commission = in.nextLine();

      try {
        finCommiss = Double.parseDouble(commission);

        break;
      } catch (Exception e) {
        view.showError(new String[] {"Please enter a valid double"});
      }

    }
    return finCommiss;


  }

  @Override
  public void buyStock() {
    Map<String, Boolean> map = model.getPortfolios();
    view.promptWhichPort();
    String tickerN = "";
    for (Map.Entry<String, Boolean> entry : map.entrySet()) {
      if (entry.getValue() == true) {

        view.printPortfolios(entry.getKey());
      }
    }
    //Error handling for no Portfolio
    String portName = "";
    while (true) {
      try {
        portName = in.nextLine();
        model.isFlexible(portName);
        break;
      } catch (Exception e) {
        view.showError(new String[]{e.getMessage()});
        view.promptWhichPort();
      }
    }


    while (true) {
      view.promptBuyStock();
      tickerN = in.nextLine();

          if (!model.validTicker(tickerN)) {
//            System.out.println(!model.validTicker(tickerN));
//            System.out.println(tickerN);
            view.showError(new String[] {"Invalid Ticker"});
          } else {
          //view.showError(new String[]{"Invalid Ticker please try again"});
          break;
        }
      }

    LocalDate date;

    while (true) {
      try {
        view.promptDateBuyStock();

        String dateIn = in.nextLine();
        date = LocalDate.parse(dateIn);

        model.addStock(portName,tickerN,gatherShares(),date,getCommission());
        break;
      } catch (Exception e) {
        view.showError(new String[]{e.getMessage()});
      }
    }
    //model.addStock(portName,tickerN,gatherShares(),date,getCommission());
    model.getFileHandler().writeFile(portName, model.toString(portName),".JSON");
    //System.out.println("Stock Successfully bought");
  }

  @Override
  public void sellStock() {
    Map<String, Boolean> map = model.getPortfolios();
    view.promptWhichPort();

    int count;
    for (Map.Entry<String, Boolean> entry : map.entrySet()) {
      if (entry.getValue() == true) {
        view.printPortfolios(entry.getKey());
      }
    }
    //Error handling for no Portfolio


    String portName ="";
    while (true) {
      try {

        portName = in.nextLine();
        model.isFlexible(portName);
        break;
      } catch (Exception e) {
        view.showError(new String[]{e.getMessage()});
        view.promptWhichPort();
      }
    }


    String tickerN = "";
    while (true) {
        view.promptSellStock();
        tickerN = in.nextLine();
        if (!model.validTicker(tickerN)) {
          view.showError(new String[]{"Invalid Ticker please try again"});
        } else {
          break;
        }
    }

    LocalDate date = null;
    while (true) {
      try {
        view.promptDateSellStock();

        String dateIn = in.nextLine();

        date = LocalDate.parse(dateIn);

        model.sellStock(portName,tickerN,gatherShares(),date,getCommission());
        break;
      } catch (Exception e) {
        view.showError(new String[]{e.getMessage()});
      }
    }
    model.getFileHandler().writeFile(portName, model.toString(portName),".JSON");
  }


  @Override
  public void loadFileData() {


    String global;
    String globalOutput = "";
    while (true) {
      view.promptLoadData();
      String fileIn = in.nextLine();
      global = fileIn;
      if (fileIn.equals("D")) {
        break;
      }
      String output;
      try {

        output = this.model.getFileHandler().readFile(fileIn);
        globalOutput = output;

        break;
      } catch (Exception e) {
        view.showError(new String[]{"Please enter a valid file path or press D to exit"});
      }

    }
    String[] ticker2 = globalOutput.split("\n");

    boolean flex = false;
    for (int i = 0; i < ticker2.length; i ++){

      if (ticker2[i].contains("commission")) {
        flex = true;

      }
    }


    if (!flex) {
      view.promptPortName();
      String portfolioName = in.nextLine();

      String[] tickers = globalOutput.split("\n");

      String[][] finOutput;
      List<String[]> data = new ArrayList<>();
      for (String line : tickers) {

        if (line.contains(":")) {
          data.add(line.split(":"));
        }

      }
      finOutput = data.toArray(new String[0][]);
      view.loadingData();
      Map<String, Integer> map1 = new HashMap<String, Integer>();
      for (int i = 0; i < finOutput.length; i++) {
        //String[][] dataOut;
        String finalTicker = finOutput[i][0].replace("\"", "").replace(" ", "");
        String finalShares = finOutput[i][1].replace(",", "").replace(" ", "").replace("\r", "");
        //dataOut = model.getAPI().getStock(finalTicker);
        if (checkNumber(finalShares) && checkValidInteger(finalShares)) {
          int finalShares2 = Integer.parseInt(finalShares);
          if (finalShares2 > 0) {
            //DO NOTHING
          } else {
            view.showError(new String[]{"You must enter a valid number of shares please try again"});
          }
          //port.addStock(finalTicker, dataOut, finalShares2);
          //model.newInflexiblePortfolio(portfolioName,);
          map1.put(finalTicker, finalShares2);

        }

      }
      model.newInflexiblePortfolio(portfolioName, map1);
    } else {
      view.promptPortName();
      String portfolioName = in.nextLine();
      model.newFlexiblePortfolio(portfolioName);
      String[] tickers = globalOutput.split("\n");

      String[][] finOutput;
      List<String[]> data = new ArrayList<>();
      for (String line : tickers) {

        if (line.contains(":")) {
          data.add(line.split(":"));
        }

      }
      finOutput = data.toArray(new String[0][]);
      view.loadingData();
      Map<String, Integer> map1 = new HashMap<String, Integer>();

      String finalTicker = "";
      String finalShares = "";
      String finalDate = "";
      String finalCommission = "";

      for (int i = 0; i < finOutput.length; i++) {

        if (finOutput[i][0].contains("ticker") || finOutput[i][0].contains("stocks")
                || finOutput[i][0].contains("date") || finOutput[i][0].contains("commission")) {
          if (finOutput[i][0].contains("ticker")) {

            finalTicker = finOutput[i][1].replace(",", "").replace(" ", "").replace("\r", "").replace("\"","");

          } else if (finOutput[i][0].contains("stocks")) {
            finalShares = finOutput[i][1].replace(",", "").replace(" ", "").replace("\r", "").replace("\"","");
          } else if (finOutput[i][0].contains("date")) {
            finalDate = finOutput[i][1].replace(",", "").replace(" ", "").replace("\r", "").replace("\"","");
          } else if (finOutput[i][0].contains("commission")) {
            finalCommission  = finOutput[i][1].replace(",", "").replace(" ", "").replace("\r", "").replace("\"","");
            try {
              model.addStock(portfolioName, finalTicker, Integer.valueOf(finalShares), LocalDate.parse(finalDate), Double.parseDouble(finalCommission));
            } catch (Exception e) {
              System.out.println(e.getMessage());
            }
          }
        }
      }

    }
  }

  @Override
  public LocalDate gatherFlexDate(String ticker) {
    LocalDate date;
    Map<String, Double> ret;
    String dateIn = null;
    while (true) {
      try {
        view.promptDatePurchased();
        dateIn = in.nextLine();
        date = LocalDate.parse(dateIn);
        //ret = model.getValues(ticker, date);
        break;

      } catch (Exception e) {
        view.showError(new String[]{e.getMessage()});
      }
    }
    return date;
  }


  @Override
  public void newFlexiblePort() {
    view.promptPortName();
    String portfolioName = in.nextLine();
    model.newFlexiblePortfolio(portfolioName);





        //Create Map of tickers to shares within the controller
        /*while (true) {
          if (!model.validTicker(tickerN)) {

            view.showError(new String[]{"Invalid Ticker please try again"});
          } else {
            //tickerMap.put(tickerN,gatherShares());
            //model.addStock(portfolioName, tickerN, gatherShares(), gatherFlexDate(tickerN), 0.00);
            break;
          }
        }*/
        //view.loadingData();


    }



  @Override
  public void newPortfolio() {

    view.promptPortName();
    String portfolioName = in.nextLine();
    String tickerN = "";
    String[][] data;

    Map<String, Integer> tickerMap = new HashMap<String, Integer>();

    //Error handling for invalid ticker
    while (true) {
      view.promptAddStock();
      tickerN = in.nextLine();
      if (tickerN.equals("D")) {
        break;
      }
      while (true) {
        try {
          if (!model.validTicker(tickerN)) {
            view.showError(new String[]{"Invalid Ticker please try again"});
            tickerN = in.nextLine();
          } else {
            tickerMap.put(tickerN, gatherShares());
            break;
          }
        } catch (Exception e) {
          view.showError(new String[]{e.getMessage()});
        }

        tickerN = in.nextLine();
      }
    }

        try {
          model.newInflexiblePortfolio(portfolioName,tickerMap);
        } catch (IllegalArgumentException e) {
          view.showError(new String[]{"\n Invalid Ticker Please enter a valid ticker"});
        }
    model.getFileHandler().writeFile(portfolioName, model.toString(portfolioName),".JSON");
    //model.getFileHandler().writeFile(portfolioName, model.toString(), ".JSON");
    view.portfolioCreationConfirmation(portfolioName);

  }



  @Override
  public int gatherShares() {
    view.promptShares();
    int shares = 0;

    while (true) {
      String share_Str = in.nextLine();
      if (checkNumber(share_Str) && checkValidInteger(share_Str)) {
        shares = Integer.valueOf(share_Str);
        if (shares > 0) {
          break;
        } else {
          view.showError(new String[]{"You must enter a valid number of shares please try again"});
        }
      } else {
        view.showError(new String[]{"You must enter a valid number of shares please try again"});
      }

    }
    return shares;
  }

  @Override
  public void viewPortfolio() {

    //CALLED HERE
    if (model.getPortfolios().size() == 0) {
      view.showError(new String[]{"You must create a portfolio before viewing"});
    } else {

      view.enterNamePort();

      /*for (int i = 0; i < model.getPortfolios().size(); i++) {

        view.promptPortfolioChoices(i, "Placeholder");
      }*/
      Map<String, Boolean> map = model.getPortfolios();
      for (Map.Entry<String, Boolean> entry : map.entrySet()) {
          view.printPortfolios(entry.getKey());
      }
      String portChoice = "";
      while (true) {
        try {

          portChoice = in.nextLine();
          model.isFlexible(portChoice);
          break;
        } catch (Exception e) {
          view.showError(new String[]{e.getMessage()});
          view.promptWhichPort();
        }
      }
      //String portChoice = in.nextLine();

      LocalDate date = null;
      String dateIn = "";

      while (true) {
        while (true) {
          view.promptDate();

          dateIn = in.nextLine();
          try {
            date = LocalDate.parse(dateIn);
            break;
          } catch (Exception e) {
            view.showError(new String[]{e.getMessage()});
          }
        }
        try {
          Map<String, Double> map1 = model.getValues(portChoice, date);
          Map<String, Double> map2 = model.getTickerMap(portChoice, date);
          double totValue = model.getPortValue(portChoice, date);
          view.showPortfolio(portChoice, totValue, dateIn);
          for (Map.Entry<String, Double> entry : map1.entrySet()) {
            if (!model.isFlexible(portChoice)) {
              view.showIndividualStocks(new String[]{entry.getKey(), String.valueOf(map2.get(entry.getKey())), String.valueOf(entry.getValue())});
            } else {
              view.showIndividualStocksFlex(new String[]{entry.getKey(), String.valueOf(map2.get(entry.getKey())), String.valueOf(entry.getValue()), String.valueOf(model.getCostBasis(portChoice,date))});
            }
          }
          //view.showIndividualStocks(new String[] {model.getValues(portChoice,date)});
          //NEEDS TO BE FIXED
          break;
        } catch (Exception e) {
          view.showError(new String[]{e.getMessage()});
        }
      }

      //CALLED HERE
      //tempPort = model.getPortfolios().get(portChoiceInt);
      /*Map<String, Boolean> map = model.getPortfolios();
      int count = 0;
      for (Map.Entry<String, Boolean> entry : map.entrySet()) {
        if (count == portChoiceInt) {
          model.getValues(entry.getKey(),gatherDate(entry.getKey()));
          break;
        }
        count = count + 1;
      }*/

    }
  }

  @Override
  //CALLED HERE
  public LocalDate gatherDate(String portName) {
    int finVal = 0;
    String dateIn = null;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
    LocalDate date = null;
    Map<String, Double> ret;
    while (true) {
      try {
        view.promptDate();
        dateIn = in.nextLine();
        //Date date = dateFormat.parse(dateIn);
        date = LocalDate.parse(dateIn);
        //ret = tempPort.getValues(date);
        ret = model.getValues(portName,date);
        break;

      } catch (Exception e) {
        view.showError(new String[]{"Invalid Date Please Try Again"});

      }

    }

    //double d = tempPort.getPortValue(date);
    //view.showPortfolio(tempPort.getPortName(), d, dateIn);
    /*for (Map.Entry<String, Double> entry : ret.entrySet()) {
      //CALLED HERE

      view.showIndividualStocks(new String[] {entry.getKey(), String.valueOf(entry.getValue())});

    }*/
    return date;

  }


  //CALLED HERE
  /*private boolean checkTickDuplicate(String s, Portfolio port) {
    Map<String, Integer> ret;
    ret = model.getTickerMap(s);
    for (Map.Entry<String, Integer> entry : ret.entrySet()) {
      if (s.equals(entry.getKey())) {
        return true;
      }
    }
    return false;
  }*/

  private boolean checkValidInteger(String s) {
    try {
      int temp = Integer.parseInt(s);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

}
