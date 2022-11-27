package view.panels;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import view.MainFrameView;

public class SinglePortfolioDetailPanel extends JPanel implements IPanel{

  String name, portfolioCreationDate;

  JButton costBasisBtn, valueBtn, compositionBtn, buyStockBtn, sellStockBtn, investBtn;

  JButton buyStockBtnMenu, sellInterimBtn;

  JTextField dateInput;

  JTextField stockInput, quantityInput, commFeeInput, amountInput;

  JLabel confirmationMsg, valueMsg;

  JLabel sellDateCheckerMsg;

  JPanel contentPanel = null, form=null;

  Map<String, Double> stockMap;

  public SinglePortfolioDetailPanel(String name){

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
    investBtn = new JButton("Invest");
    investBtn.setActionCommand("Invest");


    this.sellInterimBtn = new JButton("Get Data");
    this.sellInterimBtn.setActionCommand("Get Composition for Sell");

    this.name=name;
    this.setBackground(Color.decode("#B8DEFF"));
    this.setLayout(new BorderLayout());

    createTopMenu();

  }

  public void createTopMenu(){

    JPanel top = new JPanel();
    top.setBackground(Color.decode("#B8DEFF"));
    top.setLayout(new BorderLayout());

    JLabel header = new JLabel("For the portfolio "+this.name);
    header.setFont(new Font("Arial", Font.ITALIC, 20));
    header.setHorizontalAlignment(JLabel.CENTER);
    header.setVerticalAlignment(JLabel.NORTH);
    top.add(header,BorderLayout.NORTH);


    JButton valueBtnMenu = new JButton("Get Value");
    valueBtnMenu.setPreferredSize(new Dimension(20,45));
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
    investBtnMenu.setActionCommand("Invest");
    investBtnMenu.addActionListener(e -> this.createInvestView());


    JPanel buttonPanel = new JPanel();
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,20,0,20));
    buttonPanel.setBackground(Color.decode("#B8DEFF"));
    buttonPanel.setLayout(new GridLayout(2,3));

    buttonPanel.add(valueBtnMenu);
    buttonPanel.add(costBasisBtnMenu);
    buttonPanel.add(compositionBtnMenu);
    buttonPanel.add(buyStockBtnMenu);
    buttonPanel.add(sellStockBtnMenu);
    buttonPanel.add(investBtnMenu);

    top.add(buttonPanel,BorderLayout.CENTER);

    this.add(top,BorderLayout.NORTH);
  }

  private void createSimpleView(String str){

    if(this.contentPanel!=null) this.remove(this.contentPanel);

    this.contentPanel = new JPanel();
    this.contentPanel.setLayout(new BorderLayout());
    this.contentPanel.setBackground(Color.decode("#B8DEFF"));

    JPanel datePanel = new JPanel();
    datePanel.setLayout(new FlowLayout());
    datePanel.setBackground(Color.decode("#B8DEFF"));

    JLabel date = new JLabel("Date (yyyy-MM-dd) :");
    date.setFont(new Font("Arial", Font.PLAIN, 13));
    date.setHorizontalAlignment(JLabel.CENTER);
    datePanel.add(date);

    this.dateInput = new JTextField(10);
    this.dateInput.setFont(new Font("Arial", Font.PLAIN, 13));
    this.dateInput.setSize(190, 20);
    this.dateInput.setHorizontalAlignment(JLabel.CENTER);
    datePanel.add(this.dateInput);

    switch (str){
      case "value" :
        datePanel.add(this.valueBtn);
        break;
      case "costBasis" :
        datePanel.add(this.costBasisBtn);
        break;
      case "composition" :
        datePanel.add(this.compositionBtn);
        break;
      default:
        return;
    }

    this.contentPanel.add(datePanel,BorderLayout.NORTH);

    JPanel message = new JPanel();

    this.confirmationMsg = new JLabel("");
    this.confirmationMsg.setFont(new Font("Arial", Font.PLAIN, 25));
    this.confirmationMsg.setHorizontalAlignment(JLabel.CENTER);

    this.valueMsg = new JLabel("");
    this.valueMsg.setFont(new Font("Arial", Font.ITALIC, 25));
    this.valueMsg.setHorizontalAlignment(JLabel.CENTER);

    message.add(confirmationMsg);
    message.add(valueMsg);
    message.setSize(250,20);

    message.setBackground(Color.decode("#B8DEFF"));
    this.contentPanel.add(message,BorderLayout.CENTER);

    this.add(this.contentPanel,BorderLayout.CENTER);
    this.revalidate();
    this.repaint();
  }

  private void createBuyView(){
    if(this.contentPanel!=null) this.remove(this.contentPanel);

    this.contentPanel = new JPanel();
    this.contentPanel.setLayout(new BorderLayout());
    this.contentPanel.setBackground(Color.decode("#B8DEFF"));

    JPanel top = new JPanel(new GridLayout(2,1));
    JLabel headerDate = new JLabel("Portfolio creation date (yyyy-MM-dd) : "+this.portfolioCreationDate);
    headerDate.setFont(new Font("Arial", Font.ITALIC, 20));
    headerDate.setHorizontalAlignment(JLabel.CENTER);
    JLabel stockList = new JLabel("List of Available Stocks : NASDAQ 100");
    stockList.setFont(new Font("Arial", Font.ITALIC, 20));
    stockList.setHorizontalAlignment(JLabel.CENTER);
    top.add(headerDate);
    top.add(stockList);
    top.setBackground(Color.decode("#B8DEFF"));
    this.contentPanel.add(top,BorderLayout.PAGE_START);

    form = new JPanel();
    form.setLayout(new GridLayout(5,2));
    form.setBorder(BorderFactory.createEmptyBorder(5,20,10,20));
    JLabel stock = new JLabel("Stock Name");
    stock.setFont(new Font("Arial", Font.PLAIN, 15));
    stock.setHorizontalAlignment(JLabel.CENTER);
    form.add(stock);
    this.stockInput = new JTextField();
    form.add(this.stockInput);
    JLabel quantity = new JLabel("Quantity");
    quantity.setFont(new Font("Arial", Font.PLAIN, 15));
    quantity.setHorizontalAlignment(JLabel.CENTER);
    form.add(quantity);
    this.quantityInput = new JTextField();
    form.add(this.quantityInput);
    JLabel date = new JLabel("Date (yyyy-MM-dd)");
    date.setHorizontalAlignment(JLabel.CENTER);
    date.setFont(new Font("Arial", Font.PLAIN, 15));
    form.add(date);
    this.dateInput = new JTextField();
    form.add(this.dateInput);
    JLabel commFee = new JLabel("Commission Fee");
    commFee.setHorizontalAlignment(JLabel.CENTER);
    commFee.setFont(new Font("Arial", Font.PLAIN, 15));
    form.add(commFee);
    this.commFeeInput = new JTextField();
    form.add(this.commFeeInput);
    form.add(this.buyStockBtn);
    this.confirmationMsg = new JLabel("");
    this.confirmationMsg.setHorizontalAlignment(JLabel.CENTER);
    form.add(this.confirmationMsg);
    form.setBackground(Color.decode("#B8DEFF"));

    this.contentPanel.add(form,BorderLayout.CENTER);

    this.add(this.contentPanel,BorderLayout.CENTER);
    this.revalidate();
    this.repaint();
  }

  private void createSellView(){
    if(this.contentPanel!=null) this.remove(this.contentPanel);

    this.contentPanel = new JPanel();
    this.contentPanel.setLayout(new BorderLayout());
    this.contentPanel.setBackground(Color.decode("#B8DEFF"));

    JPanel datePanel = new JPanel();
    datePanel.setBackground(Color.decode("#B8DEFF"));

    JLabel date = new JLabel("Date for sell (yyyy-MM-dd) :");
    date.setFont(new Font("Arial", Font.PLAIN, 13));
    date.setHorizontalAlignment(JLabel.CENTER);
    datePanel.add(date);

    this.dateInput = new JTextField(10);
    this.dateInput.setFont(new Font("Arial", Font.PLAIN, 13));
    this.dateInput.setSize(190, 20);
    this.dateInput.setHorizontalAlignment(JLabel.CENTER);
    datePanel.add(this.dateInput);

    this.sellInterimBtn.setHorizontalAlignment(JButton.CENTER);
    datePanel.add(this.sellInterimBtn);

    this.sellDateCheckerMsg = new JLabel("");
    this.sellDateCheckerMsg.setFont(new Font("Arial", Font.PLAIN, 10));
    datePanel.add(this.sellDateCheckerMsg);

    this.contentPanel.add(datePanel,BorderLayout.NORTH);

    this.valueMsg = new JLabel("");
    this.valueMsg.setFont(new Font("Arial", Font.ITALIC, 25));
    this.valueMsg.setHorizontalAlignment(JLabel.CENTER);


    JLabel mapHead = new JLabel("Shares of each Stock");
    mapHead.setFont(new Font("Arial", Font.PLAIN, 15));
    mapHead.setHorizontalAlignment(JLabel.CENTER);
    this.contentPanel.add(this.valueMsg,BorderLayout.LINE_START);

    form = new JPanel();
    form.setLayout(new GridLayout(4,2));
    form.setBorder(BorderFactory.createEmptyBorder(5,20,10,20));
    JLabel stock = new JLabel("Stock Name");
    stock.setFont(new Font("Arial", Font.PLAIN, 15));
    stock.setHorizontalAlignment(JLabel.CENTER);
    form.add(stock);
    this.stockInput = new JTextField();
    form.add(this.stockInput);
    JLabel quantity = new JLabel("Quantity");
    quantity.setFont(new Font("Arial", Font.PLAIN, 15));
    quantity.setHorizontalAlignment(JLabel.CENTER);
    form.add(quantity);
    this.quantityInput = new JTextField();
    form.add(this.quantityInput);
    JLabel commFee = new JLabel("Commission Fee");
    commFee.setHorizontalAlignment(JLabel.CENTER);
    commFee.setFont(new Font("Arial", Font.PLAIN, 15));
    form.add(commFee);
    this.commFeeInput = new JTextField();
    form.add(this.commFeeInput);

    form.add(this.sellStockBtn);

    this.confirmationMsg = new JLabel("");
    this.confirmationMsg.setHorizontalAlignment(JLabel.CENTER);
    form.add(this.confirmationMsg);
    form.setBackground(Color.decode("#B8DEFF"));

    this.form.setVisible(false);
    this.contentPanel.add(form,BorderLayout.CENTER);

    this.add(this.contentPanel,BorderLayout.CENTER);
    this.revalidate();
    this.repaint();
  }

  private void createInvestView(){
    if(this.contentPanel!=null) this.remove(this.contentPanel);

    this.contentPanel = new JPanel();
    this.contentPanel.setLayout(new BorderLayout());
    this.contentPanel.setBackground(Color.decode("#B8DEFF"));

    JPanel top = new JPanel();
    top.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx=0;
    c.gridy=0;
    c.gridwidth=2;
    JLabel stockList = new JLabel("List of Available Stocks : NASDAQ 100");
    stockList.setFont(new Font("Arial", Font.ITALIC, 20));
    stockList.setHorizontalAlignment(JLabel.CENTER);

    top.add(stockList,c);

    c.gridy=1;
    c.gridx=1;
    c.gridwidth=1;
    JLabel date = new JLabel("Date for investment");
    date.setFont(new Font("Arial", Font.PLAIN, 15));
    top.add(date,c);

    c.gridy=1;
    c.gridx=2;
    c.gridwidth=1;
    this.dateInput = new JTextField(10);
    this.dateInput.setFont(new Font("Arial", Font.PLAIN, 15));
    top.add(dateInput,c);

    c.gridy=2;
    c.gridx=1;
    c.gridwidth=1;
    JLabel amount = new JLabel("Amount to be invested($)");
    amount.setFont(new Font("Arial", Font.PLAIN, 15));
    top.add(amount,c);

    c.gridy=2;
    c.gridx=2;
    c.gridwidth=1;
    this.amountInput = new JTextField(10);
    this.amountInput.setFont(new Font("Arial", Font.PLAIN, 15));
    top.add(this.amountInput,c);

    c.gridy=3;
    c.gridx=1;
    c.gridwidth=1;
    JLabel commissionFee = new JLabel("Commission fee");
    commissionFee.setFont(new Font("Arial", Font.PLAIN, 15));
    top.add(commissionFee,c);

    c.gridy=3;
    c.gridx=2;
    c.gridwidth=1;
    commFeeInput = new JTextField(10);
    this.commFeeInput.setFont(new Font("Arial", Font.PLAIN, 15));
    top.add(commFeeInput,c);

    top.setBackground(Color.decode("#B8DEFF"));

    this.contentPanel.add(top,BorderLayout.PAGE_START);

    this.form = new JPanel();
    form.setLayout(new BorderLayout());

    JPanel scrollPanel = new JPanel();
    scrollPanel.setLayout(new BoxLayout(scrollPanel,BoxLayout.Y_AXIS));
    JScrollPane scroll = new JScrollPane(scrollPanel);
    scroll.setBounds(45,0,440,140);
    this.form.add(scroll);

    JPanel temp = new JPanel();

    JLabel stock = new JLabel("Stock");
    stock.setFont(new Font("Arial", Font.PLAIN, 15));
    stock.setSize(50, 20);
    stock.setLocation(10, 50);
    temp.add(stock);


    this.stockInput = new JTextField(10);
    this.stockInput.setFont(new Font("Arial", Font.PLAIN, 15));
    this.stockInput.setSize(100, 20);
    this.stockInput.setLocation(70, 50);
    temp.add(this.stockInput);

    JLabel quantity = new JLabel("Weight(%) ");
    quantity.setFont(new Font("Arial", Font.PLAIN, 15));
    quantity.setSize(80, 20);
    quantity.setLocation(180, 50);
    temp.add(quantity);

    this.quantityInput = new JTextField(10);
    this.quantityInput.setFont(new Font("Arial", Font.PLAIN, 15));
    this.quantityInput.setSize(100, 20);
    this.quantityInput.setLocation(270, 50);
    temp.add(this.quantityInput);

    scrollPanel.add(temp);

    JButton moreAdd = new JButton("Add");
    moreAdd.setFont(new Font("Arial", Font.PLAIN, 15));
    moreAdd.setSize(50, 20);
    moreAdd.setLocation(500, 50);

    AtomicInteger y = new AtomicInteger(80);
    moreAdd.addActionListener(e-> {
      this.printMoreLines(y.get(),scrollPanel);
      y.addAndGet(30);
    });

    this.form.add(moreAdd);

    this.investBtn.setFont(new Font("Arial", Font.PLAIN, 15));
    this.investBtn.setSize(70, 20);
    this.investBtn.setLocation(50, 140);

    this.form.add(this.investBtn);

    this.confirmationMsg = new JLabel("");
    this.confirmationMsg.setFont(new Font("Arial", Font.PLAIN, 15));
    this.confirmationMsg.setSize(250, 20);
    this.confirmationMsg.setLocation(130, 140);
    form.add(this.confirmationMsg);

    form.setBackground(Color.decode("#B8DEFF"));

    this.contentPanel.add(form,BorderLayout.CENTER);

    this.add(this.contentPanel,BorderLayout.CENTER);
    this.revalidate();
    this.repaint();
  }

  private void printMoreLines(int y, JPanel scrollPane) {

    this.stockInput.setEditable(false);
    this.quantityInput.setEditable(false);

//    this.printForDCAPortfolioCreation("Added",true);

    JPanel temp = new JPanel();

    JLabel stock = new JLabel("Stock");
    stock.setFont(new Font("Arial", Font.PLAIN, 15));
    stock.setSize(50, 20);
    stock.setLocation(10, y);
    temp.add(stock);

    this.stockInput = new JTextField(10);
    this.stockInput.setFont(new Font("Arial", Font.PLAIN, 15));
    this.stockInput.setSize(100, 20);
    this.stockInput.setLocation(70, y);
    temp.add(this.stockInput);

    JLabel quantity = new JLabel("Weight(%) ");
    quantity.setFont(new Font("Arial", Font.PLAIN, 15));
    quantity.setSize(80, 20);
    quantity.setLocation(180, y);
    temp.add(quantity);

    this.quantityInput = new JTextField(10);
    this.quantityInput.setFont(new Font("Arial", Font.PLAIN, 15));
    this.quantityInput.setSize(100, 20);
    this.quantityInput.setLocation(270, y);
    temp.add(this.quantityInput);

    scrollPane.add(temp);
    scrollPane.revalidate();
    scrollPane.repaint();
  }

  public SimpleEntry<String,String> getNameAndDate(){
    return new SimpleEntry<>(this.name,this.dateInput==null?"":this.dateInput.getText());
  }
  public void setValue(String msg, Double val){
    if(msg.equals("Success")){
      this.confirmationMsg.setText("Value of the Portfolio on "+dateInput.getText()+" was $");
      this.valueMsg.setText(new DecimalFormat("0.00").format(val));
      this.confirmationMsg.setForeground(Color.BLACK);
      this.valueMsg.setForeground(Color.BLUE);
      dateInput.setText("");
    }
    else if(msg.equals("Success2")){
      this.confirmationMsg.setText("CostBasis of the Portfolio on "+dateInput.getText()+" was $");
      this.valueMsg.setText(new DecimalFormat("0.00").format(val));
      this.confirmationMsg.setForeground(Color.BLACK);
      this.valueMsg.setForeground(Color.BLUE);
      dateInput.setText("");
    }
    else if(msg.equals("Success3")){
      this.confirmationMsg.setText("Composition of the Portfolio on "+dateInput.getText()+":");
      this.confirmationMsg.setForeground(Color.BLACK);
      dateInput.setText("");
    }
    else{
      this.valueMsg.setText("");
      this.confirmationMsg.setText(msg);
      this.confirmationMsg.setForeground(Color.RED);
    }
  }

  public void setStockMap(Map<String, Double> stockMap){

    String str="";
    if(!dateInput.getText().equals("")) str = "Shares of each stock on asked Date";

    this.stockMap=stockMap;

    this.valueMsg.setVisible(true);

    StringBuilder sb = new StringBuilder();
    for(String stock : stockMap.keySet()){
      sb.append("  <tr>\n"
          + "    <td>"+stock+"</td>\n"
          + "    <td>"+new DecimalFormat("0.00").format(stockMap.get(stock))+"</td>\n"
          + "  </tr>\n");
    }
    this.valueMsg.setFont(new Font("Arial", Font.ITALIC, 15));
    this.valueMsg.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));
    this.valueMsg.setText("<html>\n"
        + "<style>\n"
        + "table, th, td {\n"
        + "  border:1px solid black;\n"
        + "}\n"
        + "</style>\n"
        + "<body>\n"
        + "\n"
        + "\n"
        +"<h4>"+str+"</h4>"
        + "<table style=\"width:100%\">\n"
        + "  <tr>\n"
        + "    <th>Stock</th>\n"
        + "    <th>Quantity</th>\n"
        + "  </tr>\n"
        + "  <tr>\n"
        +sb
        + "</table>\n"
        + "</body>\n"
        + "</html>");

    MainFrameView obj = (MainFrameView) SwingUtilities.getWindowAncestor(this);
    int newHeight = 400+((stockMap.size()-4)*50);
    if(newHeight>obj.getHeight())  obj.setSize(800,newHeight);
  }

  public Map<String, Double> getStockMap(){
    return this.stockMap;
  }
  public List<String> getBuySellData(){
    List<String> list = new ArrayList<>();

    list.add(this.name);
    list.add(this.stockInput.getText().toUpperCase());
    list.add(this.quantityInput.getText());
    list.add(this.dateInput.getText());
    list.add(this.commFeeInput.getText());

    return list;
  }

  public void setBuySellMessage(String msg){
    this.confirmationMsg.setText(msg);
    if(msg.equals("Successful transaction")){
      this.confirmationMsg.setForeground(Color.BLUE);
      this.stockInput.setText("");
      this.quantityInput.setText("");
      this.dateInput.setText("");
      this.commFeeInput.setText("");
    }
    else this.confirmationMsg.setForeground(Color.RED);
  }

  public void setSellInterimMessage(String msg){
    if(msg.equals("Success3")){
      this.sellDateCheckerMsg.setText("");
      this.form.setVisible(true);
      // print form
    }
    else{
      if(msg.contains("Given date before portfolio creation"))
        msg="<html>Given date before portfolio creation<br />"+msg.substring(msg.indexOf("date 20"))+"</html>";
      this.sellDateCheckerMsg.setForeground(Color.RED);
      this.sellDateCheckerMsg.setFont(new Font("Arial", Font.ITALIC, 13));
      this.sellDateCheckerMsg.setText(msg);

      this.valueMsg.setVisible(false);
      this.form.setVisible(false);
    }
  }

  public void setPortfolioCreationDate(String date){
    this.portfolioCreationDate=date;
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
  }
  @Override
  public JPanel getJPanel() {
    return this;
  }
}
