package view.gui;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import control.gui.Features;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/**
 * View class.
 */
public class JFrameView extends JFrame implements IView {

  private JFrame frame;


  private JButton enterPortName;
  private JButton newPortfolio;
  private JButton viewPortConfirm;
  private JButton buyStockConfirm;
  private JButton sellStockConfirm;
  private JButton performanceConfirm;
  private JButton strategyConfirm;
  private JButton loadFileConfirm;
  private JButton browseFiles;
  private JButton newPercentage;
  private JButton dollarCostConfirm;
  private JButton addPercentageCost;

  private GridBagConstraints gridBagConstraintForLabel;
  private GridBagConstraints gridBagConstraintForTextField;
  private GridBagConstraints forButton;


  private ArrayList<JTextField> labelTickers = new ArrayList<JTextField>();
  private ArrayList<JTextField> percentageInputs = new ArrayList<JTextField>();

  private ArrayList<JTextField> costlabelTickers = new ArrayList<JTextField>();
  private ArrayList<JTextField> costpercentageInputs = new ArrayList<JTextField>();

  private String fileName = "";
  final CardLayout card = new CardLayout();
  final JPanel gui = new JPanel(card);

  private JTextField startDatePer;
  private JTextField endDatePer;
  private JTextField tickerBuy;
  private JTextField dateBuy;
  private JTextField shareBuy;
  private JTextField stockTickerSell;
  private JTextField dateSell;
  private JTextField sharesSell;
  private JTextField dateViewPort;
  private JTextField filePath;
  private JTextField commissionBuy;
  private JTextField comissionSell;
  private JTextField dateFixedStrat;
  private JTextField commissionFixStrat;
  private JTextField sharesFixStrat;
  private JTextField costDate;
  private JTextField costInterval;
  private JTextField costCommission;
  private JTextField costShares;
  private JTextField endDateCost;

  JPanel reBalancePane = new JPanel();
  JPanel portPerformance = new JPanel();
  JPanel buyStockPane = new JPanel();
  JPanel sellStockPane = new JPanel();
  JPanel strategiesPane = new JPanel();

  JPanel dollarCostPane = new JPanel();
  JPanel viewPortfolioPane = new JPanel();
  JPanel loadFilePane = new JPanel();

  private JComboBox portfolioSelector = new JComboBox();

  /**
   * New JFrameView, set up main screen and display.
   */
  public JFrameView() throws IOException {

    super("STONKS");
    setSize(1300, 500);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Creating the tabs

    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

    gridBagConstraintForLabel = new GridBagConstraints();
    gridBagConstraintForTextField = new GridBagConstraints();
    forButton = new GridBagConstraints();


    tabbedPane.addTab("Performance", null, portPerformance,
            "Shows performance of the selected portfolio");
    tabbedPane.setMnemonicAt(0, KeyEvent.VK_1); // Shortcut to go to this tab
    String[] labels = {"Start Date (YYYY-MM-DD): ", "End Date (YYYY-MM-DD): "};

    startDatePer = new JTextField();
    endDatePer = new JTextField();
    JTextField[] fieldList5 = {startDatePer, endDatePer};
    for (int i = 0; i < labels.length; i++) {
      addInputs(fieldList5[i], labels[i], 0, i, portPerformance);
    }
    performanceConfirm = new JButton("Enter");
    //performanceConfirm.setText("Enter");
    addConfirmButtons(performanceConfirm, 0, labels.length + 1, portPerformance);



    int numPairs = labels.length;


    tabbedPane.addTab("Buy Stock", null, buyStockPane,
            "Add stock to this portfolio");
    tabbedPane.setMnemonicAt(1, KeyEvent.VK_2); // Shortcut to go to this tab
    //JTextField tickerBuy = null, dateBuy = null, shareBuy = null;
    //tickerBuy = new JTextField();
    tickerBuy = new JTextField();
    dateBuy = new JTextField();
    shareBuy = new JTextField();
    commissionBuy = new JTextField();
    JTextField[] fieldList4 = {tickerBuy, dateBuy, shareBuy, commissionBuy};
    String[] labels2 = {"Stock Ticker: ", "Date (YYYY-MM-DD): ", "Amount of Shares: ",
        "Commission Fee"};
    for (int i = 0; i < labels2.length; i++) {
      addInputs(fieldList4[i], labels2[i], 0, i, buyStockPane);
    }
    buyStockConfirm = new JButton("Enter");
    addConfirmButtons(buyStockConfirm, 1, labels.length + 1, buyStockPane);


    tabbedPane.addTab("Sell Stock", null, sellStockPane,
            "Sell stock from this portfolio");
    tabbedPane.setMnemonicAt(2, KeyEvent.VK_3); // Shortcut to go to this tab
    //JTextField stockTickerSell = null, dateSell = null, sharesSell = null;

    stockTickerSell = new JTextField();
    dateSell = new JTextField();
    sharesSell = new JTextField();
    comissionSell = new JTextField();
    JTextField[] fieldList3 = {stockTickerSell, dateSell, sharesSell, comissionSell};
    String[] labels3 = {"Stock Ticker: ", "Date (YYYY-MM-DD): ", "Amount of Shares: ",
        "Commission Fee: "};
    for (int i = 0; i < labels3.length; i++) {
      addInputs(fieldList3[i], labels3[i], 0, i, sellStockPane);
    }
    sellStockConfirm = new JButton("Enter");
    addConfirmButtons(sellStockConfirm, 0, labels.length + 1, sellStockPane);

    strategiesPane.setLayout(new GridBagLayout());
    tabbedPane.addTab("Fixed Amount Strategy", null, strategiesPane,
            "Create a new Fixed Amount investment strategy");
    tabbedPane.setMnemonicAt(3, KeyEvent.VK_4); // Shortcut to go to this tab

    dateFixedStrat = new JTextField();
    commissionFixStrat = new JTextField();
    sharesFixStrat = new JTextField();

    JTextField[] fieldList = {dateFixedStrat, commissionFixStrat, sharesFixStrat};
    String[] labels4 = {"Date (YYYY-MM-DD): ", "Commission: ", "$ Amount: "};
    int xPos = 0;
    int x2Pos = 1;
    for (int i = 0; i < labels4.length; i++) {

      addInputs(fieldList[i], labels4[i], x2Pos, 0, xPos, 0, strategiesPane);
      xPos = xPos + 2;
      x2Pos = x2Pos + 2;
    }
    newPercentage = new JButton("Add Percentage");

    strategyConfirm = new JButton("Enter");
    strategyConfirm.setText("Enter");
    addConfirmButtons(strategyConfirm, xPos + 1, 0, strategiesPane);
    addConfirmButtons(newPercentage, x2Pos + 2, 0, strategiesPane);


    //costDate, costInterval, costCommission, costShares

    dollarCostPane.setLayout(new GridBagLayout());
    tabbedPane.addTab("Dollar Cost Strategy", null, dollarCostPane,
            "Create a new Dollar Cost investment strategy");
    tabbedPane.setMnemonicAt(3, KeyEvent.VK_4); // Shortcut to go to this tab

    costDate = new JTextField();
    costDate.setMinimumSize(new Dimension(60, 20));
    costInterval = new JTextField();
    costInterval.setMinimumSize(new Dimension(60, 20));
    costShares = new JTextField();
    costShares.setMinimumSize(new Dimension(60, 20));
    costCommission = new JTextField();
    costCommission.setMinimumSize(new Dimension(60, 20));
    endDateCost = new JTextField();
    endDateCost.setMinimumSize(new Dimension(60, 20));


    JTextField[] fieldList7 = {costDate, costInterval, costCommission, costShares, endDateCost};
    String[] labels7 = {"Start Date (YYYY-MM-DD): ", "Interval to invest", "Commission: ",
        "$ Amount: ", "End Date (YYYY-MM-DD): "};
    int xCostPos = 0;
    int x2CostPos = 1;
    for (int i = 0; i < labels7.length; i++) {

      addInputs(fieldList7[i], labels7[i], x2CostPos, 0, xCostPos, 0, dollarCostPane);
      xCostPos = xCostPos + 2;
      x2CostPos = x2CostPos + 2;
    }
    addPercentageCost = new JButton("Add Percentage");

    dollarCostConfirm = new JButton("Enter");
    addConfirmButtons(dollarCostConfirm, xCostPos + 1, 0, dollarCostPane);
    addConfirmButtons(addPercentageCost, x2CostPos + 2, 0, dollarCostPane);


    tabbedPane.addTab("Rebalance",null,reBalancePane,"ReBalance the portfolio");


    tabbedPane.addTab("View Portfolio", null, viewPortfolioPane,
            "Create a new investment strategy");
    //JTextField dateViewPort = null;
    dateViewPort = new JTextField();
    JTextField[] fieldList2 = {dateViewPort};
    String[] labels5 = {"Date(YYYY-MM-DD)"};
    for (int i = 0; i < labels5.length; i++) {
      addInputs(fieldList2[i], labels5[i], 0, i, viewPortfolioPane);
    }
    viewPortConfirm = new JButton("Enter");
    addConfirmButtons(viewPortConfirm, 0, 1, viewPortfolioPane);


    tabbedPane.addTab("Load Data from File", null, loadFilePane,
            "Load local files to a portfolio");
    tabbedPane.setMnemonicAt(1, KeyEvent.VK_2); // Shortcut to go to this tab
    //JTextField tickerBuy = null, dateBuy = null, shareBuy = null;
    //tickerBuy = new JTextField();
    filePath = new JTextField();
    JTextField[] fieldList6 = {filePath};
    String[] labels6 = {"File Path: "};
    for (int i = 0; i < labels6.length; i++) {
      addInputs(fieldList6[i], labels6[i], 0, i, loadFilePane);
    }
    loadFileConfirm = new JButton("Load");
    browseFiles = new JButton("Browse");
    addConfirmButtons(loadFileConfirm, 0, labels6.length + 1, loadFilePane);
    addConfirmButtons(browseFiles, 0, labels6.length + 2, loadFilePane);

    JPanel portPane = new JPanel(new BorderLayout());
    String[] portfolioOptions = {};
    ((JLabel) portfolioSelector.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
    ((JLabel) portfolioSelector.getRenderer()).setVerticalAlignment(SwingConstants.CENTER);
    portfolioSelector.addItem("-- please select or create a portfolio --");
    portPane.add(portfolioSelector, BorderLayout.NORTH);
    this.newPortfolio = new JButton("New Portfolio");
    newPortfolio.setActionCommand("New Portfolio");
    portPane.add(newPortfolio, BorderLayout.EAST);


    enterPortName = new JButton("Enter");
    enterPortName.setActionCommand("Enter Portfolio Button");

    this.setLayout(new BorderLayout());
    this.add(portPane, BorderLayout.NORTH);
    this.add(tabbedPane, BorderLayout.CENTER);

    pack();

    setVisible(true);
  }

  private void addInputs(JTextField textField, String labelText, int xPos, int yPos,
                         Container containingPanel) {

    //textField = new JTextField();

    JLabel finLabel = new JLabel(labelText);
    finLabel.setText(labelText);

    //GridBagConstraints gridBagConstraintForLabel = new GridBagConstraints();
    gridBagConstraintForLabel.fill = GridBagConstraints.BOTH;

    gridBagConstraintForLabel.insets = new Insets(0, 0, 5, 5);


    gridBagConstraintForLabel.gridx = xPos;
    gridBagConstraintForLabel.gridy = yPos;
    //containingPanel.add(finLabel, gridBagConstraintForLabel);
    containingPanel.add(finLabel, gridBagConstraintForLabel);

    //GridBagConstraints gridBagConstraintForTextField = new GridBagConstraints();
    gridBagConstraintForTextField.fill = GridBagConstraints.BOTH;
    gridBagConstraintForTextField.insets = new Insets(0, 0, 5, 0);
    gridBagConstraintForTextField.gridx = xPos + 1;
    gridBagConstraintForTextField.gridy = yPos;
    //containingPanel.add(textField, gridBagConstraintForTextField);
    containingPanel.add(textField, gridBagConstraintForTextField);
    textField.setColumns(10);

  }

  private void addInputs(JTextField textField, String labelText, int xPos, int yPos, int xLabelPos,
                         int yLabelPos, Container containingPanel) {


    //textField = new JTextField();

    JLabel finLabel = new JLabel(labelText);
    finLabel.setText(labelText);

    //GridBagConstraints gridBagConstraintForLabel = new GridBagConstraints();
    gridBagConstraintForLabel.fill = GridBagConstraints.NONE;

    gridBagConstraintForLabel.insets = new Insets(0, 0, 5, 5);


    gridBagConstraintForLabel.gridx = xLabelPos;
    gridBagConstraintForLabel.gridy = yLabelPos;
    gridBagConstraintForLabel.anchor = GridBagConstraints.NORTH;
    gridBagConstraintForLabel.weighty = 1;
    //containingPanel.add(finLabel, gridBagConstraintForLabel);
    //finLabel.setHorizontalAlignment(JLabel.TOP);
    containingPanel.add(finLabel, gridBagConstraintForLabel);

    //GridBagConstraints gridBagConstraintForTextField = new GridBagConstraints();
    gridBagConstraintForTextField.fill = GridBagConstraints.NONE;
    gridBagConstraintForTextField.insets = new Insets(0, 0, 5, 0);
    gridBagConstraintForTextField.gridx = xPos;
    gridBagConstraintForTextField.gridy = yPos;
    gridBagConstraintForTextField.weighty = 1;
    gridBagConstraintForTextField.anchor = GridBagConstraints.NORTH;

    //textField.setHorizontalAlignment(JTextField.TOP);
    //containingPanel.add(textField, gridBagConstraintForTextField);
    textField.setColumns(10);
    textField.setMinimumSize(new Dimension(80, 20));
    containingPanel.add(textField, gridBagConstraintForTextField);
    //textField.setColumns(10);
  }

  private void addConfirmButtons(JButton button, int xPos, int yPos,
                                 Container containingPanel) {
    //GridBagConstraints forButton = new GridBagConstraints();
    //forButton.fill = GridBagConstraints.BOTH;

    JButton newButton = button;
    forButton.fill = GridBagConstraints.NONE;
    //forButton.insets = new Insets(0, 0, 5, 0);
    forButton.gridx = xPos;
    forButton.gridy = yPos;
    forButton.anchor = GridBagConstraints.NORTH;
    forButton.weighty = 1;
    containingPanel.add(newButton, forButton);


  }


  @Override
  public void showOutput(String output) {
    JOptionPane.showMessageDialog(null, output);

  }

  @Override
  public void showError(String error) {
    JOptionPane.showMessageDialog(null, error);
  }

  @Override
  public String setUpNewPort(Features features) {
    JPanel newFlexiblePort;
    JTextField portName;
    newFlexiblePort = new JPanel();
    System.out.print("setUpNewPort");

    portName = new JTextField(10);
    portName.setText("Portfoio Name");
    newFlexiblePort.add(portName);

    int result = JOptionPane.showConfirmDialog(this, newFlexiblePort, "New Portfolio",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      System.out.println("Ok");
      portfolioSelector.addItem(portName.getText());
      features.newFlexiblePort(portName.getText());
      enterPortName.doClick();
    } else {
      System.out.println("Cancelled");
    }
    return portName.getText();

  }


  @Override
  public void gatherFile() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    int option = fileChooser.showOpenDialog(frame);
    if (option == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      System.out.println("Selected: " + file.getName());
      fileName = file.getName();
      filePath.setText(file.getName());
    }
  }


  @Override
  public void addPercentageInputsCost() {
    JTextField input = new JTextField();
    JTextField percentIn = new JTextField();


    costlabelTickers.add(input);
    costpercentageInputs.add(percentIn);
    //strategiesPane.repaint();
    addInputs(input, "Ticker:", 1, costlabelTickers.size() + 2, 0, costlabelTickers.size()
            + 2, dollarCostPane);
    addInputs(percentIn, "Percentage:", 3, costpercentageInputs.size() + 2, 2,
            costpercentageInputs.size() + 2, dollarCostPane);
    dollarCostPane.revalidate();


  }

  @Override
  public void addPercentageInputs() {

    JTextField input = new JTextField();
    JTextField percentIn = new JTextField();
    labelTickers.add(input);
    percentageInputs.add(percentIn);
    //strategiesPane.repaint();
    addInputs(input, "Ticker", 1, labelTickers.size() + 2, 0, labelTickers.size() + 2,
            strategiesPane);
    addInputs(percentIn, "Percentage", 3, percentageInputs.size() + 2, 2,
            percentageInputs.size() + 2, strategiesPane);
    strategiesPane.revalidate();
  }

  public void switchPanel(String s) {
    card.show(gui, s);
  }

  @Override
  public void checkViewInputs(Features features) {
    boolean dateCheck = features.checkDate(dateViewPort.getText());
    if (!dateCheck) {
      highlightFunction(dateViewPort);
    }
  }

  private void highlightFunction(JTextField field) {
    Highlighter highlighter = field.getHighlighter();
    Highlighter.HighlightPainter painter = new DefaultHighlighter
            .DefaultHighlightPainter(Color.red);
    int p1 = field.getText().length();
    try {
      highlighter.removeAllHighlights();
      highlighter.addHighlight(0, p1, painter);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Override
  public void checkBuyInputs(Features features) {
    boolean dateCheck = features.checkDate(dateBuy.getText());
    boolean commissCheck = features.checkCommission(commissionBuy.getText());
    boolean shareCheck = features.checkShares(shareBuy.getText());
    System.out.println("Share check is " + shareCheck);
    if (!dateCheck) {
      highlightFunction(dateBuy);
    }
    if (!commissCheck) {
      highlightFunction(commissionBuy);
    }
    if (!shareCheck) {
      highlightFunction(shareBuy);
    }

  }

  @Override
  public void checkSellInputs(Features features) {
    boolean dateCheck = features.checkDate(dateSell.getText());
    boolean commissCheck = features.checkCommission(comissionSell.getText());
    boolean shareCheck = features.checkShares(sharesSell.getText());

    if (!dateCheck) {
      highlightFunction(dateSell);
    }
    if (!commissCheck) {
      highlightFunction(comissionSell);
    }
    if (!shareCheck) {
      highlightFunction(sharesSell);
    }

  }

  /**
   * Gathers the made and calls model.
   *
   * @param features LIsteners to call back.
   */
  public void gatherFixedStrategy(Features features) {
    ArrayList<String> tickers = new ArrayList<String>();
    ArrayList<String> percentages = new ArrayList<String>();
    for (int i = 0; i < labelTickers.size(); i++) {
      tickers.add(labelTickers.get(i).getText());
      percentages.add(percentageInputs.get(i).getText());
    }

    features.newFixedStrategy((String) portfolioSelector.getSelectedItem(),
            dateFixedStrat.getText(), percentages, tickers,
            commissionFixStrat.getText(), sharesFixStrat.getText());

  }

  /**
   * Same as above for dollar strategy.
   *
   * @param features Listeners to call back.
   */
  public void gatherDollarStrategy(Features features) {
    ArrayList<String> tickers = new ArrayList<String>();
    ArrayList<String> percentages = new ArrayList<String>();
    for (int i = 0; i < costlabelTickers.size(); i++) {
      tickers.add(costlabelTickers.get(i).getText());
      percentages.add(costpercentageInputs.get(i).getText());
    }
    features.newDollarCostStrategy((String) portfolioSelector.getSelectedItem(), costDate.getText()
            , costInterval.getText(), percentages, tickers, costCommission.getText(),
            costShares.getText(), endDateCost.getText());

  }

  @Override
  public void addFeatures(Features features) {
    newPortfolio.addActionListener(evt -> features.newFlexiblePortInit());
    viewPortConfirm.addActionListener(evt -> checkViewInputs(features));
    viewPortConfirm.addActionListener(evt -> features.viewPortfolio((String)
            portfolioSelector.getSelectedItem(), dateViewPort.getText()));

    buyStockConfirm.addActionListener(evt -> checkBuyInputs(features));
    buyStockConfirm.addActionListener(evt -> features.buyStock((String)
                    portfolioSelector.getSelectedItem(),
            tickerBuy.getText(), shareBuy.getText(), dateBuy.getText(), commissionBuy.getText()));


    sellStockConfirm.addActionListener(evt -> checkSellInputs(features));
    sellStockConfirm.addActionListener(evt -> features.sellStock((String)
                    portfolioSelector.getSelectedItem(), stockTickerSell.getText(),
            sharesSell.getText(), dateSell.getText(), comissionSell.getText()));

    performanceConfirm.addActionListener(evt -> features.getPerformance((String)
            portfolioSelector.getSelectedItem(), startDatePer.getText(), endDatePer.getText()));
    loadFileConfirm.addActionListener(evt -> features.loadFileData(fileName, (String)
            portfolioSelector.getSelectedItem()));
    browseFiles.addActionListener(evt -> gatherFile());
    newPercentage.addActionListener(evt -> addPercentageInputs());
    addPercentageCost.addActionListener(evt -> addPercentageInputsCost());
    strategyConfirm.addActionListener(evt -> gatherFixedStrategy(features));
    dollarCostConfirm.addActionListener(evt -> gatherDollarStrategy(features));
  }
}