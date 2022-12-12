package view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Single portfolio detail panel.
 */
public class SinglePortfolioDetailPanel extends JPanel implements IPanel {

  String name;
  String portfolioCreationDate;

  JButton costBasisBtn;
  JButton valueBtn;
  JButton compositionBtn;
  JButton buyStockBtn;
  JButton sellStockBtn;
  JButton investBtn;
  JButton launchGraph;
  JButton investDCAButton;

  JButton buyStockBtnMenu;
  JButton sellInterimBtn;

  JTextField dateInput;

  JTextField startDateInput;
  JTextField endDateInput;
  JTextField intervalInput;

  JTextField stockInput;
  JTextField quantityInput;
  JTextField commFeeInput;
  JTextField amountInput;

  JLabel confirmationMsg;
  JLabel valueMsg;

  JLabel sellDateCheckerMsg;

  JPanel contentPanel = new JPanel();
  JPanel form = new JPanel();

  Map<String, Double> stockMap;

  Double sum;

  InvestPanel obj;

  DCAPortfolioPanel dcaPanelObj;

  /**
   * Single portfolio panel constructor.
   *
   * @param name name of the portfolio.
   */
  public SinglePortfolioDetailPanel(String name) {

    valueBtn = new JButton("Value");
    valueBtn.setActionCommand("Get Value");
    costBasisBtn = new JButton("Cost Basis");
    costBasisBtn.setActionCommand("Get CostBasis");
    compositionBtn = new JButton("Composition");
    compositionBtn.setActionCommand("Get Composition");
    buyStockBtn = new JButton("Purchase stocks");
    buyStockBtn.setActionCommand("BuyStock");
    sellStockBtn = new JButton("Sell stocks");
    sellStockBtn.setActionCommand("SellStock");
    investBtn = new JButton("InvestStock");
    investBtn.setActionCommand("InvestStock");

    this.sellInterimBtn = new JButton("Get Data");
    this.sellInterimBtn.setActionCommand("Get Composition for Sell");

    this.dateInput = new JTextField(10);
    this.confirmationMsg = new JLabel("");
    this.valueMsg = new JLabel("");

    this.stockInput = new JTextField();
    this.quantityInput = new JTextField();
    this.dateInput = new JTextField();
    this.commFeeInput = new JTextField();
    this.amountInput = new JTextField();

    this.sellDateCheckerMsg = new JLabel("");

    this.sum = 0.0;

    this.stockMap = new HashMap<>();

    this.launchGraph = new JButton("Launch Graph");
    this.launchGraph.setActionCommand("Graph Data");
    this.startDateInput = new JTextField(10);
    this.endDateInput = new JTextField(10);

    this.investDCAButton = new JButton("Invest");
    this.investDCAButton.setActionCommand("Invest DCA");
    this.intervalInput = new JTextField(10);

    this.name = name;
    this.setBackground(Color.decode("#B8DEFF"));
    this.setLayout(new BorderLayout());

    createTopMenu();

  }

  /**
   * Create top view menu.
   */
  public void createTopMenu() {

    JPanel top = new JPanel();
    top.setBackground(Color.decode("#B8DEFF"));
    top.setLayout(new BorderLayout());

    JLabel header = new JLabel("For the portfolio " + this.name);
    header.setFont(new Font("Arial", Font.ITALIC, 20));
    header.setHorizontalAlignment(JLabel.CENTER);
    header.setVerticalAlignment(JLabel.NORTH);
    top.add(header, BorderLayout.NORTH);

    JButton valueBtnMenu = new JButton("Get Value");
    valueBtnMenu.setPreferredSize(new Dimension(20, 45));
    valueBtnMenu.addActionListener(e -> this.createSimpleView("value"));
    JButton costBasisBtnMenu = new JButton("Get Cost Basis");
    costBasisBtnMenu.addActionListener(e -> this.createSimpleView("costBasis"));
    JButton compositionBtnMenu = new JButton("Get Composition");
    compositionBtnMenu.setActionCommand("Composition");
    compositionBtnMenu.addActionListener(e -> this.createSimpleView("composition"));
    buyStockBtnMenu = new JButton("Purchase stocks");
    buyStockBtnMenu.setActionCommand("BuyStock Menu");
    buyStockBtnMenu.addActionListener(e -> this.createBuyView());
    JButton sellStockBtnMenu = new JButton("Sell stocks");
    sellStockBtnMenu.setActionCommand("SellStock");
    sellStockBtnMenu.addActionListener(e -> this.createSellView());
    JButton investBtnMenu = new JButton("Invest in portfolio");
    investBtnMenu.setActionCommand("InvestStock");
    investBtnMenu.addActionListener(e -> this.createInvestView());

    JButton performanceGraph = new JButton("Performance Graph");
    performanceGraph.addActionListener(e -> this.createGraphView());

    JButton investDCA = new JButton("Invest through DCA");
    investDCA.addActionListener(e -> this.createInvestDCAView());

    JPanel buttonPanel = new JPanel();
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
    buttonPanel.setBackground(Color.decode("#B8DEFF"));
    buttonPanel.setLayout(new GridLayout(2, 4));

    buttonPanel.add(valueBtnMenu);
    buttonPanel.add(costBasisBtnMenu);
    buttonPanel.add(compositionBtnMenu);
    buttonPanel.add(buyStockBtnMenu);
    buttonPanel.add(sellStockBtnMenu);
    buttonPanel.add(investBtnMenu);
    buttonPanel.add(performanceGraph);
    buttonPanel.add(investDCA);

    top.add(buttonPanel, BorderLayout.CENTER);

    this.add(top, BorderLayout.NORTH);
  }

  private void createSimpleView(String str) {

    if (this.contentPanel != null) {
      this.remove(this.contentPanel);
    }

    this.contentPanel = new JPanel();

    new ValuePanel().createSimpleView(str, this.contentPanel, this.dateInput, this.valueBtn,
        this.costBasisBtn,
        this.compositionBtn, this.confirmationMsg, this.valueMsg);

    this.add(this.contentPanel, BorderLayout.CENTER);
    this.revalidate();
    this.repaint();
  }

  private void createBuyView() {
    if (this.contentPanel != null) {
      this.remove(this.contentPanel);
    }

    this.contentPanel = new JPanel();
    this.form = new JPanel();

    new BuyStockPanel().createBuyView(this.contentPanel, this.portfolioCreationDate, this.form,
        this.stockInput,
        this.quantityInput, this.dateInput, this.commFeeInput, this.buyStockBtn,
        this.confirmationMsg);

    this.add(this.contentPanel, BorderLayout.CENTER);
    this.revalidate();
  }

  private void createSellView() {
    if (this.contentPanel != null) {
      this.remove(this.contentPanel);
    }

    this.contentPanel = new JPanel();
    form = new JPanel();

    new SellStockPanel().createSellView(this.contentPanel, this.form, this.stockInput,
        this.quantityInput
        , this.dateInput, this.commFeeInput, this.sellStockBtn, this.confirmationMsg,
        this.sellInterimBtn,
        this.sellDateCheckerMsg, this.valueMsg);

    this.add(this.contentPanel, BorderLayout.CENTER);
    this.revalidate();
    this.repaint();
  }

  private void createInvestView() {

    this.stockMap = new HashMap<>();
    if (this.contentPanel != null) {
      this.remove(this.contentPanel);
    }

    this.contentPanel = new JPanel();
    this.form = new JPanel();
    obj = new InvestPanel();
    obj.createInvestView(this.contentPanel, this.form, this.dateInput, this.amountInput,
        this.commFeeInput, this.stockInput, this.quantityInput, this.investBtn,
        this.confirmationMsg, this.stockMap);

    this.add(this.contentPanel, BorderLayout.CENTER);
    this.revalidate();
    this.repaint();
  }

  private void createGraphView() {
    if (this.contentPanel != null) {
      this.remove(this.contentPanel);
    }


    this.contentPanel = new JPanel(new BorderLayout());

    JPanel datePanel = new JPanel(new FlowLayout());
    JPanel insider = new JPanel();
    insider.setLayout(new BoxLayout(insider,BoxLayout.Y_AXIS));
    insider.add(new JLabel("Start Date (yyyy-MM-dd)"));
    insider.add(this.startDateInput);
    insider.setBackground(Color.decode("#B8DEFF"));
    datePanel.add(insider);
    insider = new JPanel();
    insider.setLayout(new BoxLayout(insider,BoxLayout.Y_AXIS));
    insider.add(new JLabel("End Date(yyyy-MM-dd)"));
    insider.add(this.endDateInput);
    insider.setBackground(Color.decode("#B8DEFF"));
    datePanel.add(insider);
    datePanel.add(this.launchGraph);
    datePanel.setBackground(Color.decode("#B8DEFF"));

    this.contentPanel.add(datePanel, BorderLayout.NORTH);
    this.confirmationMsg.setHorizontalAlignment(JLabel.CENTER);
    this.confirmationMsg.setText("");
    this.contentPanel.add(this.confirmationMsg, BorderLayout.CENTER);
    this.confirmationMsg.setFont(new Font("Arial", Font.PLAIN, 25));
    this.contentPanel.setBackground(Color.decode("#B8DEFF"));

    this.add(this.contentPanel, BorderLayout.CENTER);
    this.revalidate();
    this.repaint();
  }

  private void createInvestDCAView() {

    this.stockMap = new HashMap<>();
    this.sum = 0.0;

    if (this.contentPanel != null) {
      this.remove(this.contentPanel);
    }

    this.contentPanel = new JPanel();

    this.dcaPanelObj = new DCAPortfolioPanel();
    this.contentPanel = this.dcaPanelObj.printDCACreationMenu(this.stockMap, this, this.form,
        null, this.amountInput, this.startDateInput, this.endDateInput,
        this.intervalInput, this.commFeeInput, this.stockInput, this.quantityInput,
        this.investDCAButton, this.confirmationMsg);

    this.contentPanel.setBackground(Color.decode("#B8DEFF"));
    MainFrameGUIView frameObj = (MainFrameGUIView) SwingUtilities.getWindowAncestor(this);
    int newHeight = this.investDCAButton.getY() + 180;
    if (newHeight > frameObj.getHeight()) {
      frameObj.setSize(800, newHeight);
    }
    this.add(this.contentPanel, BorderLayout.CENTER);

    this.revalidate();
    this.repaint();
  }

  void setInvestMsg(String str, boolean isGood) {
    if (str.equals("Success")) {
      this.createInvestView();
    }
    if (str.equals("Invested through DCA")) {
      this.createInvestDCAView();
    }

    this.confirmationMsg.setText(str);

    if (isGood) {
      this.confirmationMsg.setForeground(Color.GREEN);
    } else {
      this.confirmationMsg.setForeground(Color.RED);
    }
  }

  List<String> getInvestData() {
    List<String> list = new ArrayList<>();

    list.add(this.name);
    list.add(this.dateInput.getText());
    list.add(this.amountInput.getText());
    list.add(this.commFeeInput.getText());

    return list;
  }

  Map<String, Double> getInvestStockMap() {
    if (obj != null) {
      obj.inputValidation(this.stockMap, this.confirmationMsg);
    } else if (this.dcaPanelObj != null) {
      this.dcaPanelObj.inputValidation(this.stockMap, this.confirmationMsg);
    }
    this.stockMap.remove("");
    return this.stockMap;
  }

  SimpleEntry<String, String> getNameAndDate() {
    return new SimpleEntry<>(this.name, this.dateInput == null ? "" : this.dateInput.getText());
  }

  void setValue(String msg, Double val) {
    if (msg.equals("Success")) {
      this.confirmationMsg.setText("Value of the Portfolio on " + dateInput.getText() + " was $");
      this.valueMsg.setText(new DecimalFormat("0.00").format(val));
      this.confirmationMsg.setForeground(Color.BLACK);
      this.valueMsg.setForeground(Color.BLUE);
      dateInput.setText("");
    } else if (msg.equals("Success2")) {
      this.confirmationMsg.setText(
          "CostBasis of the Portfolio on " + dateInput.getText() + " was $");
      this.valueMsg.setText(new DecimalFormat("0.00").format(val));
      this.confirmationMsg.setForeground(Color.BLACK);
      this.valueMsg.setForeground(Color.BLUE);
      dateInput.setText("");
    } else if (msg.equals("Success3")) {
      this.confirmationMsg.setText("Composition of the Portfolio on " + dateInput.getText() + ":");
      this.confirmationMsg.setForeground(Color.BLACK);
      dateInput.setText("");
    } else {
      this.valueMsg.setText("");
      this.confirmationMsg.setText(msg);
      this.confirmationMsg.setForeground(Color.RED);
    }
  }

  Map<String, Double> getStockMap() {
    return this.stockMap;
  }

  void setStockMap(Map<String, Double> stockMap) {

    String str = "";
    if (!dateInput.getText().equals("")) {
      str = "Shares of each stock on asked Date";
    }

    this.stockMap = stockMap;

    this.valueMsg.setVisible(true);

    StringBuilder sb = new StringBuilder();
    for (String stock : stockMap.keySet()) {
      sb.append("  <tr>\n"
          + "    <td>" + stock + "</td>\n"
          + "    <td>" + new DecimalFormat("0.00").format(stockMap.get(stock)) + "</td>\n"
          + "  </tr>\n");
    }
    this.valueMsg.setFont(new Font("Arial", Font.ITALIC, 15));
    this.valueMsg.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
    this.valueMsg.setText("<html>\n"
        + "<style>\n"
        + "table, th, td {\n"
        + "  border:1px solid black;\n"
        + "}\n"
        + "</style>\n"
        + "<body>\n"
        + "\n"
        + "\n"
        + "<h4>" + str + "</h4>"
        + "<table style=\"width:100%\">\n"
        + "  <tr>\n"
        + "    <th>Stock</th>\n"
        + "    <th>Quantity</th>\n"
        + "  </tr>\n"
        + "  <tr>\n"
        + sb
        + "</table>\n"
        + "</body>\n"
        + "</html>");

    MainFrameGUIView obj = (MainFrameGUIView) SwingUtilities.getWindowAncestor(this);
    int newHeight = 400 + ((stockMap.size() - 4) * 50);
    if (newHeight > obj.getHeight()) {
      obj.setSize(800, newHeight);
    }
  }

  List<String> getBuySellData() {
    List<String> list = new ArrayList<>();

    list.add(this.name);
    list.add(this.stockInput.getText().toUpperCase());
    list.add(this.quantityInput.getText());
    list.add(this.dateInput.getText());
    list.add(this.commFeeInput.getText());

    return list;
  }

  void setMessage(String msg) {
    this.confirmationMsg.setText(msg);
    if (msg.equals("Successful transaction")) {
      this.confirmationMsg.setForeground(Color.BLUE);
      this.stockInput.setText("");
      this.quantityInput.setText("");
      this.dateInput.setText("");
      this.commFeeInput.setText("");
    } else if (msg.equals("success")) {
      this.confirmationMsg.setForeground(Color.BLUE);
      this.confirmationMsg.setText("Launching Performance Graph...");
      this.startDateInput.setText("");
      this.endDateInput.setText("");
    } else {
      this.confirmationMsg.setForeground(Color.RED);
    }
  }

  void setSellInterimMessage(String msg) {
    if (msg.equals("Success3")) {
      this.sellDateCheckerMsg.setText("");
      this.form.setVisible(true);
    } else {
      if (msg.contains("Given date before portfolio creation")) {
        msg = "<html>Given date before portfolio creation<br />" + msg.substring(
            msg.indexOf("date 20")) + "</html>";
      }
      this.sellDateCheckerMsg.setForeground(Color.RED);
      this.sellDateCheckerMsg.setFont(new Font("Arial", Font.ITALIC, 13));
      this.sellDateCheckerMsg.setText(msg);

      this.valueMsg.setVisible(false);
      this.form.setVisible(false);
    }
  }

  void setPortfolioCreationDate(String date) {
    this.portfolioCreationDate = date;
  }

  List<String> getGraphData() {
    List<String> list = new ArrayList<>();
    list.add(this.startDateInput.getText());
    list.add(this.endDateInput.getText());
    list.add(this.name);
    return list;
  }

  void startGraph(List<String> labels, List<Double> dataPoints) {
    SwingUtilities.invokeLater(() -> new GraphPanel("Line Chart Example", labels, dataPoints));
    this.confirmationMsg.setText("");
  }

  List<String> getDCAFormData() {
    List<String> data = new ArrayList<>();
    data.add(this.name);
    data.add(this.startDateInput.getText().trim());
    data.add(this.endDateInput.getText().trim());
    data.add(this.amountInput.getText().trim());
    data.add(this.intervalInput.getText().trim());
    data.add(this.commFeeInput.getText().trim());
    return data;
  }

  @Override
  public void addActionListener(ActionListener listener) {
    this.valueBtn.addActionListener(listener);
    this.costBasisBtn.addActionListener(listener);
    this.compositionBtn.addActionListener(listener);
    this.buyStockBtn.addActionListener(listener);
    this.sellStockBtn.addActionListener(listener);
    this.investBtn.addActionListener(listener);

    this.buyStockBtnMenu.addActionListener(listener);
    this.sellInterimBtn.addActionListener(listener);

    this.launchGraph.addActionListener(listener);
    this.investDCAButton.addActionListener(listener);
  }

  @Override
  public JPanel getJPanel() {
    return this;
  }

}
