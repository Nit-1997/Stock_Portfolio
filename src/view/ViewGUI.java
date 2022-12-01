package view;

import java.awt.event.ActionListener;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import view.panels.IPanel;

public interface ViewGUI {

  void addActionListener(ActionListener listener);


  void changePanel(IPanel panel);

  List<String> getNormalPortfolioCreationData();

  void printForNormalPortfolioCreation(String str);

  List<String> getNormalPortfolioCreationFormDataAddMore();

  List<String> getDCAPortfolioCreationData();

  Map<String, Double> getDCAPortfolioCreationMap();

  void printForDCAPortfolioCreation(String str);

  SimpleEntry<String,String> getNameAndDate();

  void setValue(String type, Double value);

  Map<String, Double> getStockMap();

  void setStockMap(Map<String, Double> stockMap);

  void setPortfolioCreationDate(String date);

  List<String> getBuySellData();

  void setBuySellMsg(String msg);

  void setSellInterimMessage(String msg);

  List<String> getInvestData();

  Map<String, Double> getInvestStockMap();

  void setInvestMsg(String str);

  List<String> getGraphData();

  void setGraphMsg(String str);

  void startGraph(SimpleEntry<List<String>,List<Double>> data);

}
