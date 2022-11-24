package view;

import java.io.PrintStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IView {

  void welcomeNote(PrintStream out);

  void welcomeMenu(PrintStream out);

  void flexiblePortfolioHeader(PrintStream out);

  void flexPortfolioExitMsg(PrintStream out);

  void printMenu(PrintStream out);

  void errorNote(PrintStream out);

  void exitNote(PrintStream out);

  void addPortfolio(PrintStream out);

  void askPortfolioNameAgain(PrintStream out);

  void stockNotInPortfolio(PrintStream out);

  void addStocksInPortfolioWelcomeNote(String portfolioName, PrintStream out);

  /**
   * print available stocks.
   *
   * @param stockList list of stocks.
   */
  void printAvailableStocks(Set<String> stockList);

  void askTickerSymbol(PrintStream out);

  void askTickerSymbolAgain(PrintStream out);

  void askStockNumber(PrintStream out);

  void askStockNumberAgain(PrintStream out);

  void askCommissionFee(PrintStream out);

  void askCommissionFeeAgain(PrintStream out);

  void addStocksInPortfolioConfirmation(PrintStream out);

  void stocksInPortfolioAddOrRemoveMenu(PrintStream out);

  void removeStocksInPortfolioSuccessfulConfirmation(PrintStream out);

  void removeStocksInPortfolioUnSuccessfulConfirmation(PrintStream out);

  void askconfirmation(PrintStream out);

  void waitMessage(PrintStream out);

  void addStocksInPortfolioConfirmationLoading(String name, PrintStream out);

  void addStocksInPortfolioErrorNode(PrintStream out);

  void unsuccessfulPortolioCreationMsg(PrintStream out);

  /**
   * print portfolios.
   *
   * @param portfolioNames portfolio names
   * @param out            out stream.
   */
  void printPortfolios(Set<String> portfolioNames, PrintStream out);

  void loadPortfolioMenu(PrintStream out);

  void askChoice(PrintStream out);

  void askNameOfPortfolio(PrintStream out);

  void askPortfolioNameAgainUnique(PrintStream out);

  void portfolioDetailWelcomeNote(String name, PrintStream out);

  void loadPortfolioDetailMenu(PrintStream out);

  void loadFlexPortfolioDetailMenu(PrintStream out);

  void waitLoadMessage(PrintStream out);

  void askDate(PrintStream out);

  void askDateAgain(PrintStream out);

  void wrongDateMsg(PrintStream out);

  void wrongDateBeforeLastTx(PrintStream out);

  void wrongDateBeforePortfolioCreation(PrintStream out);

  void successfulTransaction(PrintStream out);

  void unSuccessfulTransaction(PrintStream out);

  void printInCompatiblePortfolio(PrintStream out);

  void stockNotInPortfolioMsg(PrintStream out);

  void stockLessThanInPortfolio(PrintStream out);

  void printCostBasis(Double cost, PrintStream out);

  /**
   * Print the latest state of the portfolio.
   *
   * @param map                   {ticker vs latest date and quantity}
   * @param portfolioCreationDate creation date of the portfolio.
   * @param out                   output object.
   */
  void printPortfolioState(Map<String, SimpleEntry<String, Double>> map,
      String portfolioCreationDate, PrintStream out);

  /**
   * print portfolio summary.
   *
   * @param stockMap map of stocks
   * @param out      out stream.
   */
  void printPortfolioSummary(Map<String, Double> stockMap, PrintStream out);

  /**
   * print portfolio detail.
   *
   * @param mapDetail      map detail.
   * @param portfolioValue portfolio value
   * @param out            out stream.
   */
  void printPortfolioDetail(Map<String, List<Double>> mapDetail,
      double portfolioValue, PrintStream out);

  /**
   * print portfolio performance.
   *
   * @param portfolioPnL pnl of portfolio.
   * @param out          out stream.
   */
  void printPortfolioPerformance(Double portfolioPnL, PrintStream out);

  /**
   * print portfolio value.
   *
   * @param portfolioValue portfolio value.
   * @param out            out stream.
   */
  void printPortfolioValue(Double portfolioValue, PrintStream out);

  void loadPortfolioErrorNote(PrintStream out);

  void askStartDateForGraph(PrintStream out);

  void askEndDateForGraph(PrintStream out);

  void graphInvalidRange(PrintStream out);

  /**
   * Prints the performance graph.
   *
   * @param out           output object.
   * @param date1         starting date.
   * @param date2         ending date.
   * @param portfolioName name of the portfolio.
   * @param labels        label list.
   * @param starPoints    starPoint list.
   * @param scale         scale of graph.
   * @param baseAmount    starting point of x-axis.
   */
  void printGraph(PrintStream out, String date1, String date2,
      String portfolioName, List<String> labels, List<Integer> starPoints, int scale,
      double baseAmount);

  void printError(Exception e, PrintStream out);
}
