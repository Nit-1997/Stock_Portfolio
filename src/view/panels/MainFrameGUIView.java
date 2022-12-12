package view.panels;


import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import view.ViewGUI;

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
    if (this.mainPanel instanceof CreatePortfolioPanel) {
      CreatePortfolioPanel obj = (CreatePortfolioPanel) this.mainPanel;
      List<String> data = obj.getNormalFormData();
      data.add(obj.getAreaText());
      return data;
    }
    return null;
  }

  @Override
  public List<String> getNormalPortfolioCreationFormDataAddMore() {
    if (this.mainPanel instanceof CreatePortfolioPanel) {
      CreatePortfolioPanel obj = (CreatePortfolioPanel) this.mainPanel;
      return obj.getNormalFormData();
    }
    return null;
  }

  @Override
  public List<String> getDCAPortfolioCreationData() {
    if (this.mainPanel instanceof CreatePortfolioPanel) {
      CreatePortfolioPanel obj = (CreatePortfolioPanel) this.mainPanel;
      return obj.getDCAFormData();
    }
    return null;
  }

  @Override
  public Map<String, Double> getDCAPortfolioCreationMap() {
    if (this.mainPanel instanceof CreatePortfolioPanel) {
      CreatePortfolioPanel obj = (CreatePortfolioPanel) this.mainPanel;
      return obj.getDcaStockMap();
    }
    return null;
  }

  @Override
  public void printForNormalPortfolioCreation(String str) {
    if (this.mainPanel instanceof CreatePortfolioPanel) {
      CreatePortfolioPanel obj = (CreatePortfolioPanel) this.mainPanel;
      obj.printForNormalPortfolioCreation(str);
    }
  }

  @Override
  public void printForDCAPortfolioCreation(String str) {
    if (this.mainPanel instanceof CreatePortfolioPanel) {
      CreatePortfolioPanel obj = (CreatePortfolioPanel) this.mainPanel;
      obj.printForDCAPortfolioCreation(str, str.equals("Portfolio Successfully Saved"));
    }
  }

  @Override
  public SimpleEntry<String, String> getNameAndDate() {
    if (this.mainPanel instanceof SinglePortfolioDetailPanel) {
      return ((SinglePortfolioDetailPanel) this.mainPanel).getNameAndDate();
    }
    return null;
  }

  @Override
  public void setValue(String type, Double value) {
    if (this.mainPanel instanceof SinglePortfolioDetailPanel) {
      ((SinglePortfolioDetailPanel) this.mainPanel).setValue(type, value);
    }
  }

  @Override
  public void setPortfolioCreationDate(String date) {
    if (this.mainPanel instanceof SinglePortfolioDetailPanel) {
      ((SinglePortfolioDetailPanel) this.mainPanel).setPortfolioCreationDate(date);
    }
  }

  @Override
  public List<String> getBuySellData() {
    if (this.mainPanel instanceof SinglePortfolioDetailPanel) {
      return ((SinglePortfolioDetailPanel) this.mainPanel).getBuySellData();
    }
    return null;
  }

  @Override
  public void setBuySellMsg(String msg) {
    if (this.mainPanel instanceof SinglePortfolioDetailPanel) {
      ((SinglePortfolioDetailPanel) this.mainPanel).setMessage(msg);
    }
  }

  @Override
  public void setSellInterimMessage(String msg) {
    if (this.mainPanel instanceof SinglePortfolioDetailPanel) {
      ((SinglePortfolioDetailPanel) this.mainPanel).setSellInterimMessage(msg);
    }
  }

  @Override
  public Map<String, Double> getStockMap() {
    if (this.mainPanel instanceof SinglePortfolioDetailPanel) {
      return ((SinglePortfolioDetailPanel) this.mainPanel).getStockMap();
    }
    return null;
  }

  @Override
  public void setStockMap(Map<String, Double> stockMap) {
    if (this.mainPanel instanceof SinglePortfolioDetailPanel) {
      ((SinglePortfolioDetailPanel) this.mainPanel).setStockMap(stockMap);
    }
  }

  @Override
  public List<String> getInvestData() {
    if (this.mainPanel instanceof SinglePortfolioDetailPanel) {
      return ((SinglePortfolioDetailPanel) this.mainPanel).getInvestData();
    }
    return null;
  }

  @Override
  public Map<String, Double> getInvestStockMap() {
    if (this.mainPanel instanceof SinglePortfolioDetailPanel) {
      return ((SinglePortfolioDetailPanel) this.mainPanel).getInvestStockMap();
    }
    return null;
  }

  @Override
  public void setInvestMsg(String str) {
    if (this.mainPanel instanceof SinglePortfolioDetailPanel) {
      ((SinglePortfolioDetailPanel) this.mainPanel).setInvestMsg(str,
          str.equals("Success") || str.equals("Invested through DCA"));
    }
  }

  @Override
  public void addActionListener(ActionListener listener) {
    this.listener = listener;
    this.mainPanel.addActionListener(listener);
    this.sideBar.addActionListener(listener);
  }

  @Override
  public List<String> getGraphData() {
    if (this.mainPanel instanceof SinglePortfolioDetailPanel) {
      return ((SinglePortfolioDetailPanel) this.mainPanel).getGraphData();
    }
    return null;
  }

  @Override
  public void setGraphMsg(String msg) {
    if (this.mainPanel instanceof SinglePortfolioDetailPanel) {
      ((SinglePortfolioDetailPanel) this.mainPanel).setMessage(msg);
    }
  }

  @Override
  public void startGraph(SimpleEntry<List<String>, List<Double>> data) {
    if (this.mainPanel instanceof SinglePortfolioDetailPanel) {
      ((SinglePortfolioDetailPanel) this.mainPanel).startGraph(data.getKey(), data.getValue());
    }
  }

  @Override
  public List<String> getDCAInvestmentData() {
    if (this.mainPanel instanceof SinglePortfolioDetailPanel) {
      return ((SinglePortfolioDetailPanel) this.mainPanel).getDCAFormData();
    }
    return null;
  }


}
