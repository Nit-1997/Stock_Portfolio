package view.terminal;

import java.io.PrintStream;
import java.util.Set;

/**
 * Implemented stock viewer class, outputs the stock information.
 */
public class StockViewer implements StockView {
  private final PrintStream out;

  /**
   * Create new StockViewer.
   *
   * @param out PrintStream to output results to.
   */
  public StockViewer(PrintStream out) {
    this.out = out;
  }

  @Override
  public void clearScreen() {
    out.print(controlSequence("2J"));
    out.print(controlSequence("1;1H"));
  }

  @Override
  public void showCommand(String command) {
    out.print("\033[1mCommand: " + command);
  }

  @Override
  public void showMainMenu() {
    out.print(
            "      ::::::::   :::::::::::       ::::::::       ::::    :::       :::    :::\n"

                    +
                    "    :+:    :+:      :+:          :+:    :+:      :+:+:   :+:       :+:   :+:\n"

                    +
                    "   +:+             +:+          +:+    +:+      :+:+:+  +:+       +:+  +:+\n"

                    +
                    "  +#++:++#++      +#+          +#+    +:+      +#+ +:+ +#+       +#++:++\n"

                    +
                    "        +#+      +#+          +#+    +#+      +#+  +#+#+#       +#+  +#+\n"

                    +
                    "#+#    #+#      #+#          #+#    #+#      #+#   #+#+#       #+#   #+#\n"

                    +
                    "########       ###           ########       ###    ####       ###    ###\n"

                    +
                    "                                           V1.0\n"

                    +
                    "                                     \033[6;1mN\033[0m New Infleixble " +
                    "Portfolio\n"
                    +
                    "                                     \033[6;1mF\033[0m New Flexible " +
                    "Portfolio\n"

                    +
                    "                                   \033[6;1mV\033[0m View Portfolios\n"

                    +
                    "                                         \033[6;1mQ\033[0m Quit\n"

                    +
                    "                                   \033[6;1mL\033[0m Load Data from File\n" +
                    "                                         \033[6;1mB\033[0m Buy Stock\n" +


                    "                                         \033[6;1mS\033[0m Sell Stock\n" +
                    "                                         \033[6;1mR\033[0m Rebalance\n" +
                    "                                         \033[6;1mP\033[0m View Performance " +
                    "Portfolio\n");
  }

  @Override
  public void showPortfolio(String portfolioName, double totalValue, String date) {
    out.print("                  Portfolio Name:" + portfolioName + "\n");
    out.print("                  Total Value on Date " + date + "   " + totalValue + "\n");
  }

  @Override
  public void showIndividualStocks(String[] info) {
    out.print("                  Stock Ticker:  " + info[0] + "\n");
    out.print("                  Stock Shares Owned:  " + info[1] + "\n");
    out.print("                  Stock Value:  " + info[2] + "\n");

  }

  @Override
  public void promptCommission() {
    out.print("                  Please enter in the commission for this transaction: ");

  }

  @Override
  public void showError(String[] error) {
    out.print("                " + error[0] + "\n \n");
  }

  @Override
  public void portfolioCreationConfirmation(String portName) {
    out.print("\n                  The portfolio  " + portName + " has been created \n \n");
  }

  @Override
  public void promptPortName() {
    out.print("                                                    \n"
            +
            "                        Please Enter Name for Portfolio\n"

            +
            "                                                                         \n ");
  }

  @Override
  public void promptAddStock() {
    out.print("                                                    \n"
            +
            "              Please Enter Ticker Symbol of Stock Enter 'D' to no longer add stocks\n"

            +
            "       \n ");
  }

  @Override
  public void promptShares() {
    out.print("                                                    \n"
            +
            "            Please Enter Number of Shares you would like to purchase\n"

            +
            "                                                                         \n ");
  }

  @Override
  public void enterNamePort() {
    out.print("                                        \n"
            +
            "                      Please enter the name of the portfolio you would like to view\n"

            +
            "                                         The possible choices are \n ");
  }

  @Override
  public void promptPortfolioChoices(int i, String portName) {
    out.print("                                  " + i + ")" + portName + "\n");
  }

  @Override
  public void promptDate() {
    out.print("      Please type in a date in the format of YYYY-MM-DD\n ");
  }

  @Override
  public void loadingData() {
    out.print("                        Loading Data....        ");
  }

  @Override
  public void quit() {
    out.print("\n"

            +
            "\n"

            +
            "\n"

            +
            "\n"

            +
            "                                          \033[51;1mGoodbye!\n");
  }

  @Override
  public void promptLoadData() {
    out.println("                Please enter the filename you would like to load from");

  }

  @Override
  public void promptDatePurchased() {
    out.println("               Please enter the date you purchased" +
            " these shares in format YYYY-MM-DD");
  }

  @Override
  public void promptDateSold() {
    out.println("               Please enter the date you sold these shares in format YYYY-MM-DD");
  }

  @Override
  public void printGraph(String date, String astericks) {
    out.println(date + ":      " + astericks);
  }

  @Override
  public void printGraphScale(String scale) {
    out.println("Scale: " + scale + " per *");
  }

  @Override
  public void printGraphTitle(String portfolioName, String startDate, String endDate) {
    out.println("Performance of portfolio " + portfolioName + " from " + startDate + " to "
            + endDate + "\n\n");
  }

  @Override
  public void promptBuyStock() {
    out.println("             Please enter the ticker symbol of the " +
            "stock you would like to buy    ");
  }

  @Override
  public void promptDateBuyStock() {
    out.println("             Please enter the date of the stock you would " +
            "like to buy in format YYYY-MM-DD   ");
  }

  @Override
  public void promptDateSellStock() {
    out.println("             Please enter the date of the stock you " +
            "would like to sell in format YYYY-MM-DD   ");
  }

  @Override
  public void promptSellStock() {
    out.println("             Please enter the ticker symbol of the " +
            "stock you would like to sell   ");
  }

  @Override
  public void printStocksToSell(String ticker, String shares) {
    out.println("              Ticker: " + ticker + "  Shares   " + shares);
  }

  @Override
  public void printPortfolios(String portfolioName) {
    out.println("Portfolio " + portfolioName);
  }

  @Override
  public void promptWhichPort() {
    out.println("Please select the portfolio ");

  }

  @Override
  public void promptStartDate() {

    out.println("       Please enter a start date in the format of YYYY-MM-DD");

  }

  @Override
  public void promptEndDate() {

    out.println("       Please enter a end date in the format of YYYY-MM-DD   ");

  }

  @Override
  public void showIndividualStocksFlex(String[] info) {
    out.print("                  Stock Ticker:  " + info[0] + "\n");
    out.print("                  Stock Shares Owned:  " + info[1] + "\n");
    out.print("                  Stock Value:  " + info[2] + "\n");
    out.print("                  Cost Basis " + info[3] + "\n");
  }

  /**
   * Make a control sequence code.
   *
   * @param code Code to add to control sequence introducer.
   * @return Combined string.
   */
  private String controlSequence(String code) {
    return "\033[" + code;
  }

  @Override
  public void emptyPortfolioReBalance(){
    out.println("No stocks in portfolio on that date, kindly enter again");
  }

  @Override
  public void printAvailableStockReBalance(Set<String> stockNames) {
    out.println("Available stocks : "+stockNames);
  }

  @Override
  public void percentageErrorIntegerFormatReBalance(){
    out.print("kindly enter percentage in integer format : ");
  }

  @Override
  public void percentageErrorOutRangeReBalance() {
    out.print("kindly enter percentage in 0 - 100 range : ");
  }

  @Override
  public void reBalanceConfirmation(){
    out.println("Portfolio rebalanced");
  }

  @Override
  public void reBalanceErrorMsg(Exception e){
    out.println(e.getMessage());
  }

  @Override
  public void percentageHeaderReBalance(){
    out.println("Enter percentages : ");
  }

  @Override
  public void askPercentageReBalance(String stock){
    out.print(stock+" : ");
  }
}
