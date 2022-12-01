package view;

import static org.junit.Assert.*;

import java.awt.event.ActionListener;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import view.panels.IPanel;

public class MockView implements ViewGUI{

  @Override
  public void addActionListener(ActionListener listener) {

  }

  @Override
  public void changePanel(IPanel panel) {

  }

  @Override
  public List<String> getNormalPortfolioCreationData() {
    return null;
  }

  @Override
  public void printForNormalPortfolioCreation(String str) {

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

  }

  @Override
  public SimpleEntry<String, String> getNameAndDate() {
    return null;
  }

  @Override
  public void setValue(String type, Double value) {

  }

  @Override
  public Map<String, Double> getStockMap() {
    return null;
  }

  @Override
  public void setStockMap(Map<String, Double> stockMap) {

  }

  @Override
  public void setPortfolioCreationDate(String date) {

  }

  @Override
  public List<String> getBuySellData() {
    return null;
  }

  @Override
  public void setBuySellMsg(String msg) {

  }

  @Override
  public void setSellInterimMessage(String msg) {

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

  }

  @Override
  public List<String> getGraphData() {
    return null;
  }

  @Override
  public void setGraphMsg(String str) {

  }

  @Override
  public void startGraph(SimpleEntry<List<String>, List<Double>> data) {

  }

  @Override
  public List<String> getDCAInvestmentData() {
    return null;
  }
}