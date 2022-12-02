package view.panels;

import constants.Constants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * Investment in existing portfolio panel.
 */
public class InvestPanel {

  Double sum;

  JTextField stockInput, quantityInput;

  /**
   * Constructor.
   */
  InvestPanel() {
    this.sum = 0.0;
  }

  void createInvestView(JPanel contentPanel, JPanel form, JTextField dateInput,
      JTextField amountInput, JTextField commFeeInput, JTextField stockInput,
      JTextField quantityInput, JButton investBtn, JLabel confirmationMsg,
      Map<String, Double> stockMap) {

    this.stockInput = stockInput;
    this.quantityInput = quantityInput;

    this.sum = 0.0;

    contentPanel.setLayout(new BorderLayout());
    contentPanel.setBackground(Color.decode("#B8DEFF"));

    JPanel top = new JPanel();
    top.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 2;
    JLabel stockList = new JLabel("List of Available Stocks : NASDAQ 100");
    stockList.setFont(new Font("Arial", Font.ITALIC, 20));
    stockList.setHorizontalAlignment(JLabel.CENTER);

    top.add(stockList, c);

    c.gridy = 1;
    c.gridx = 1;
    c.gridwidth = 1;
    JLabel date = new JLabel("Date for investment (yyyy-MM-dd)");
    date.setFont(new Font("Arial", Font.PLAIN, 15));
    top.add(date, c);

    c.gridy = 1;
    c.gridx = 2;
    c.gridwidth = 1;
    dateInput.setText("");
    dateInput.setColumns(10);
    dateInput.setFont(new Font("Arial", Font.PLAIN, 15));
    top.add(dateInput, c);

    c.gridy = 2;
    c.gridx = 1;
    c.gridwidth = 1;
    JLabel amount = new JLabel("Amount to be invested($)");
    amount.setFont(new Font("Arial", Font.PLAIN, 15));
    top.add(amount, c);

    c.gridy = 2;
    c.gridx = 2;
    c.gridwidth = 1;
    amountInput.setText("");
    amountInput.setColumns(10);
    amountInput.setFont(new Font("Arial", Font.PLAIN, 15));
    top.add(amountInput, c);

    c.gridy = 3;
    c.gridx = 1;
    c.gridwidth = 1;
    JLabel commissionFee = new JLabel("Commission fee");
    commissionFee.setFont(new Font("Arial", Font.PLAIN, 15));
    top.add(commissionFee, c);

    c.gridy = 3;
    c.gridx = 2;
    c.gridwidth = 1;
    commFeeInput.setText("");
    commFeeInput.setColumns(10);
    commFeeInput.setFont(new Font("Arial", Font.PLAIN, 15));
    top.add(commFeeInput, c);

    top.setBackground(Color.decode("#B8DEFF"));

    contentPanel.add(top, BorderLayout.PAGE_START);

    form.setLayout(new BorderLayout());

    JPanel scrollPanel = new JPanel();
    scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
    JScrollPane scroll = new JScrollPane(scrollPanel);
    scroll.setBounds(45, 0, 440, 140);
    form.add(scroll);

    JPanel temp = new JPanel();

    JLabel stock = new JLabel("Stock");
    stock.setFont(new Font("Arial", Font.PLAIN, 15));
    stock.setSize(50, 20);
    stock.setLocation(10, 50);
    temp.add(stock);

    this.stockInput.setEditable(true);
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

    this.quantityInput.setEditable(true);
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
    moreAdd.addActionListener(e -> {
      this.printMoreLines(stockMap, confirmationMsg, y.get(), scrollPanel);
      y.addAndGet(30);
    });

    form.add(moreAdd);

    investBtn.setFont(new Font("Arial", Font.PLAIN, 15));
    investBtn.setSize(100, 20);
    investBtn.setLocation(50, 140);

    form.add(investBtn);

    confirmationMsg.setText("");
    confirmationMsg.setFont(new Font("Arial", Font.PLAIN, 15));
    confirmationMsg.setSize(350, 20);
    confirmationMsg.setLocation(180, 140);
    form.add(confirmationMsg);

    JButton resetBtn = new JButton("");
    resetBtn.setFont(new Font("Arial", Font.PLAIN, 15));
    resetBtn.setSize(0, 20);
    resetBtn.setLocation(260, 350);
    resetBtn.setVisible(false);
    form.add(resetBtn);

    form.setBackground(Color.decode("#B8DEFF"));

    contentPanel.add(form, BorderLayout.CENTER);

  }

  private void printMoreLines(Map<String, Double> stockMap, JLabel confirmationMsg, int y,
      JPanel scrollPane) {

    if (!this.inputValidation(stockMap, confirmationMsg)) {
      return;
    }

    this.stockInput.setEditable(false);
    this.quantityInput.setEditable(false);

    this.setInvestMsg("Added", confirmationMsg, true);

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

  boolean inputValidation(Map<String, Double> stockMap, JLabel confirmationMsg) {
    String stock = this.stockInput.getText();
    String percentage = this.quantityInput.getText();
    if (stock.equals("") || percentage.equals("")) {
      this.setInvestMsg("Empty record", confirmationMsg, false);
      return false;
    }

    if (!Constants.STOCK_NAMES.contains(stock.toUpperCase())) {
      this.setInvestMsg("Not a valid stock name", confirmationMsg, false);
      return false;
    }
    Double percent;
    try {
      percent = Double.parseDouble(percentage);
    } catch (NumberFormatException e) {
      this.setInvestMsg("Number not in numeric format", confirmationMsg, false);
      return false;
    }
    if (percent == 0) {
      this.setInvestMsg("Percentage 0", confirmationMsg, false);
      return false;
    }
    if (percent < 0 || percent > 100) {
      this.setInvestMsg("Percentage -ve or 100+", confirmationMsg, false);
      return false;
    }
    if (this.sum + percent > 100.0) {
      this.setInvestMsg("Weights sum more than 100", confirmationMsg, false);
      return false;
    }
    this.sum += percent;
    if (stockMap.containsKey(stock.toUpperCase())) {
      stockMap.put(stock.toUpperCase(), stockMap.get(stock.toUpperCase()) + percent);
    } else {
      stockMap.put(stock.toUpperCase(), percent);
    }
    System.out.println(stockMap);

    return true;
  }

  /**
   * Sets the investment msg.
   * @param str type.
   * @param confirmationMsg confirmation message.
   * @param isGood positive or negative message.
   */
  public void setInvestMsg(String str, JLabel confirmationMsg, boolean isGood) {
    confirmationMsg.setText(str);

    if (isGood) {
      confirmationMsg.setForeground(Color.GREEN);
    } else {
      confirmationMsg.setForeground(Color.RED);
    }
  }

}
