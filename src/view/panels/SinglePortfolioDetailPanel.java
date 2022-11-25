package view.panels;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import view.MainFrameView;

public class SinglePortfolioDetailPanel extends JPanel implements IPanel{

  String name, portfolioCreationDate;

  JButton costBasisBtn, valueBtn, compositionBtn, buyStockBtn, sellStockBtn, investBtn;

  JButton buyStockBtnMenu;

  JTextField dateInput;

  JLabel confirmationMsg, valueMsg;

  JPanel contentPanel = null;

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
    investBtn = new JButton("Invest in portfolio");
    investBtn.setActionCommand("Invest");

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
//    sellStockBtnMenu.addActionListener(e -> this.datePanel.setVisible(false));
    JButton investBtnMenu = new JButton("Invest in portfolio");
    investBtnMenu.setActionCommand("Invest");
//    investBtnMenu.addActionListener(e -> this.datePanel.setVisible(false));


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
    headerDate.setHorizontalAlignment(JLabel.CENTER);
    JLabel stockList = new JLabel("List of Available Stock : NASDAQ 100");
    stockList.setHorizontalAlignment(JLabel.CENTER);
    top.add(headerDate);
    top.add(stockList);
    top.setBackground(Color.decode("#B8DEFF"));
    this.contentPanel.add(top,BorderLayout.PAGE_START);

    JPanel form = new JPanel(new GridLayout(5,2));
    form.add(new JLabel("Stock Name"));
    form.add(new JTextField(10));
    form.add(new JLabel("Quantity"));
    form.add(new JTextField(10));
    form.add(new JLabel("Date"));
    form.add(new JTextField(10));
    form.add(new JLabel("Commission Fee"));
    form.add(new JTextField(10));
    form.add(this.buyStockBtn);
    form.setBackground(Color.decode("#B8DEFF"));

    this.contentPanel.add(form,BorderLayout.CENTER);

    this.add(this.contentPanel,BorderLayout.CENTER);
    this.revalidate();
    this.repaint();


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
  }
  @Override
  public JPanel getJPanel() {
    return this;
  }
}
