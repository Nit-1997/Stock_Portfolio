package view;

import java.awt.event.ActionListener;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import view.panels.IPanel;

/**
 * Mock view for controller testing.
 */
public class MockView implements ViewGUI{

  @Override
  public void addActionListener(ActionListener listener) {
      // only for constructor testing.
  }

  @Override
  public void changePanel(IPanel panel) {
    // only for constructor testing.
  }

  @Override
  public List<String> getNormalPortfolioCreationData() {
    return null;
  }

  @Override
  public void printForNormalPortfolioCreation(String str) {
    // only for constructor testing.
  }

  @Override
  public List<String> getNormalPortfolioCreationFormDataAddMore() {
    return null;
  }

  @Override
  public List<String> getDCAPortfolioCreationData() {
    return null;
  }

  @Override
  public Map<String, Double> getDCAPortfolioCreationMap() {
    return null;
  }

  @Override
  public void printForDCAPortfolioCreation(String str) {
    // only for constructor testing.
  }

  @Override
  public SimpleEntry<String, String> getNameAndDate() {
    return null;
  }

  @Override
  public void setValue(String type, Double value) {
    // only for constructor testing.
  }

  @Override
  public Map<String, Double> getStockMap() {
    return null;
  }

  @Override
  public void setStockMap(Map<String, Double> stockMap) {
    // only for constructor testing.
  }

  @Override
  public void setPortfolioCreationDate(String date) {
    // only for constructor testing.
  }

  @Override
  public List<String> getBuySellData() {
    return null;
  }

  @Override
  public void setBuySellMsg(String msg) {
    // only for constructor testing.
  }

  @Override
  public void setSellInterimMessage(String msg) {
    // only for constructor testing.
  }

  @Override
  public List<String> getInvestData() {
    return null;
  }

  @Override
  public Map<String, Double> getInvestStockMap() {
    return null;
  }

  @Override
  public void setInvestMsg(String str) {
    // only for constructor testing.
  }

  @Override
  public List<String> getGraphData() {
    return null;
  }

  @Override
  public void setGraphMsg(String str) {
    // only for constructor testing.
  }

  @Override
  public void startGraph(SimpleEntry<List<String>, List<Double>> data) {
    // only for constructor testing.
  }

  @Override
  public List<String> getDCAInvestmentData() {
    return null;
  }
}