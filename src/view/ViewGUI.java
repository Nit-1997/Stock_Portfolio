package view;

import java.awt.event.ActionListener;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import view.panels.IPanel;

/**
 * Interface for the GUI.
 */
public interface ViewGUI {

  /**
   * Adds action listeners to buttons.
   * @param listener action listener.
   */
  void addActionListener(ActionListener listener);

  /**
   * Changes panel.
   * @param panel new panel to be changed.
   */
  void changePanel(IPanel panel);

  /**
   * Get Normal Portfolio Creation Data.
   * @return List of String data.
   */
  List<String> getNormalPortfolioCreationData();

  /**
   * Prints the confirmation message for normal portfolio creation.
   * @param str message.
   */
  void printForNormalPortfolioCreation(String str);

  /**
   * Gets the normal portfolio creation data for addMore button.
   * @return list of string data.
   */
  List<String> getNormalPortfolioCreationFormDataAddMore();

  /**
   * Get DCA Portfolio Creation Data.
   * @return List of String data.
   */
  List<String> getDCAPortfolioCreationData();

  /**
   * Get DCA portfolio creation stockMap.
   * @return Map of stock vs weightage.
   */
  Map<String, Double> getDCAPortfolioCreationMap();

  /**
   * Prints the confirmation message for DCA portfolio creation.
   * @param str message.
   */
  void printForDCAPortfolioCreation(String str);

  /**
   * Gets the portfolio name and the date for portfolio data.
   * @return Simple Entry of portfolioName and the date.
   */
  SimpleEntry<String,String> getNameAndDate();

  /**
   * Sets the value or costBasis or the composition for the portfolio.
   * @param type differentiator between value or costBasis or composition.
   * @param value the value to be printed.
   */
  void setValue(String type, Double value);

  /**
   * Gets the StockMap.
   * @return Map of stock vs weightage.
   */
  Map<String, Double> getStockMap();

  /**
   * Sets the stock map.
   * @param stockMap Map of Stock vs weightage.
   */
  void setStockMap(Map<String, Double> stockMap);

  /**
   * Sets the portfolio creation date.
   * @param date portfolio creation date.
   */
  void setPortfolioCreationDate(String date);

  /**
   * Gets the data for buying or selling stock.
   * @return list of portfolioName, stock, quantity, date, commissionFee.
   */
  List<String> getBuySellData();

  /**
   * Sets the data for buying or selling stock.
   * @param msg confirmation message.
   */
  void setBuySellMsg(String msg);

  /**
   * Sets the message for date check.
   * @param msg confirmation message.
   */
  void setSellInterimMessage(String msg);

  /**
   * Get the data for investment in portfolio.
   * @return list of portfolio name, date, amount, commissionFee.
   */
  List<String> getInvestData();

  /**
   * Gets the investment stockMap.
   * @return stockMap.
   */
  Map<String, Double> getInvestStockMap();

  /**
   * Sets the confirmation message for investment.
   * @param str confirmation message.
   */
  void setInvestMsg(String str);

  /**
   * Gets the graph data.
   * @return list fo startDate, endDate, portfolioName.
   */
  List<String> getGraphData();

  /**
   * Sets the confirmation message for graph.
   * @param str confirmation message.
   */
  void setGraphMsg(String str);

  /**
   * Initialise the graph.
   * @param data entry of label data and dataPoints list.
   */
  void startGraph(SimpleEntry<List<String>,List<Double>> data);

  /**
   * Gets the data for investment through DCA strategy.
   * @return list if string data.
   */
  List<String> getDCAInvestmentData();

}
