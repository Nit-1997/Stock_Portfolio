package view.panels;


import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import view.ViewGUI;
import view.panels.CreatePortfolioPanel;
import view.panels.IPanel;
import view.panels.MainMenuPanel;
import view.panels.SideBarPanel;
import view.panels.SinglePortfolioDetailPanel;

/**
 * Main GUI View class.
 */
public class MainFrameGUIView extends JFrame implements ViewGUI {

  SideBarPanel sideBar;

  IPanel mainPanel;

  ActionListener listener;


  /**
   * Constructor for GUI view class.
   */
  public MainFrameGUIView() {
    super();

    this.setTitle("Stonks!");
    this.setSize(800, 420);
    this.setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());

    this.sideBar = new SideBarPanel();
    this.add(this.sideBar, BorderLayout.WEST);

    this.mainPanel = new MainMenuPanel();
    this.add(this.mainPanel.getJPanel(), BorderLayout.CENTER);

    this.setVisible(true);
  }

  @Override
  public void changePanel(IPanel panel) {
    this.remove(this.mainPanel.getJPanel());
    this.mainPanel = panel;
    this.mainPanel.addActionListener(this.listener);
    this.add(panel.getJPanel(), BorderLayout.CENTER);
    this.validate();
    this.setVisible(true);
  }

  @Override
  public List<String> getNormalPortfolioCreationData() {
    CreatePortfolioPanel obj = (CreatePortfolioPanel) this.mainPanel;
    List<String> data = obj.getNormalFormData();
    data.add(obj.getAreaText());
    return data;
  }

  @Override
  public List<String> getNormalPortfolioCreationFormDataAddMore() {
    CreatePortfolioPanel obj = (CreatePortfolioPanel) this.mainPanel;
    return obj.getNormalFormData();
  }

  @Override
  public List<String> getDCAPortfolioCreationData() {
    CreatePortfolioPanel obj = (CreatePortfolioPanel) this.mainPanel;
    return obj.getDCAFormData();
  }

  @Override
  public Map<String, Double> getDCAPortfolioCreationMap() {
    CreatePortfolioPanel obj = (CreatePortfolioPanel) this.mainPanel;
    return obj.getDcaStockMap();
  }

  @Override
  public void printForNormalPortfolioCreation(String str) {
    CreatePortfolioPanel obj = (CreatePortfolioPanel) this.mainPanel;
    obj.printForNormalPortfolioCreation(str);
  }

  @Override
  public void printForDCAPortfolioCreation(String str) {
    CreatePortfolioPanel obj = (CreatePortfolioPanel) this.mainPanel;
    obj.printForDCAPortfolioCreation(str, str.equals("Portfolio Successfully Saved"));
  }

  @Override
  public SimpleEntry<String, String> getNameAndDate() {
    return ((SinglePortfolioDetailPanel) this.mainPanel).getNameAndDate();
  }

  @Override
  public void setValue(String type, Double value) {
    ((SinglePortfolioDetailPanel) this.mainPanel).setValue(type, value);
  }

  @Override
  public void setPortfolioCreationDate(String date) {
    ((SinglePortfolioDetailPanel) this.mainPanel).setPortfolioCreationDate(date);
  }

  @Override
  public List<String> getBuySellData() {
    return ((SinglePortfolioDetailPanel) this.mainPanel).getBuySellData();
  }

  @Override
  public void setBuySellMsg(String msg) {
    ((SinglePortfolioDetailPanel) this.mainPanel).setMessage(msg);
  }

  @Override
  public void setSellInterimMessage(String msg) {
    ((SinglePortfolioDetailPanel) this.mainPanel).setSellInterimMessage(msg);
  }

  @Override
  public Map<String, Double> getStockMap() {
    return ((SinglePortfolioDetailPanel) this.mainPanel).getStockMap();
  }

  @Override
  public void setStockMap(Map<String, Double> stockMap) {
    ((SinglePortfolioDetailPanel) this.mainPanel).setStockMap(stockMap);
  }

  @Override
  public List<String> getInvestData() {
    return ((SinglePortfolioDetailPanel) this.mainPanel).getInvestData();
  }

  @Override
  public Map<String, Double> getInvestStockMap() {
    return ((SinglePortfolioDetailPanel) this.mainPanel).getInvestStockMap();
  }

  @Override
  public void setInvestMsg(String str) {
    ((SinglePortfolioDetailPanel) this.mainPanel).setInvestMsg(str,
        str.equals("Success") || str.equals("Invested through DCA"));
  }

  @Override
  public void addActionListener(ActionListener listener) {
    this.listener = listener;
    this.mainPanel.addActionListener(listener);
    this.sideBar.addActionListener(listener);
  }

  @Override
  public List<String> getGraphData() {
    return ((SinglePortfolioDetailPanel) this.mainPanel).getGraphData();
  }

  @Override
  public void setGraphMsg(String msg) {
    ((SinglePortfolioDetailPanel) this.mainPanel).setMessage(msg);
  }

  @Override
  public void startGraph(SimpleEntry<List<String>, List<Double>> data) {
    ((SinglePortfolioDetailPanel) this.mainPanel).startGraph(data.getKey(), data.getValue());
  }

  @Override
  public List<String> getDCAInvestmentData() {
    return ((SinglePortfolioDetailPanel) this.mainPanel).getDCAFormData();
  }


}
