package view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Sell stock panel.
 */
public class SellStockPanel {

 /**
  * Sell stock panel constructor.
  */
  SellStockPanel() {

  }

  void createSellView(JPanel contentPanel, JPanel form,
      JTextField stockInput, JTextField quantityInput, JTextField dateInput,
      JTextField commFeeInput,
      JButton sellStockBtn, JLabel confirmationMsg, JButton sellInterimBtn,
      JLabel sellDateCheckerMsg,
      JLabel valueMsg) {

    contentPanel.setLayout(new BorderLayout());
    contentPanel.setBackground(Color.decode("#B8DEFF"));

    JPanel datePanel = new JPanel();
    datePanel.setBackground(Color.decode("#B8DEFF"));

    JLabel date = new JLabel("Date for sell (yyyy-MM-dd) :");
    date.setFont(new Font("Arial", Font.PLAIN, 13));
    date.setHorizontalAlignment(JLabel.CENTER);
    datePanel.add(date);

    dateInput.setColumns(10);
    dateInput.setText("");
    dateInput.setFont(new Font("Arial", Font.PLAIN, 13));
    dateInput.setSize(190, 20);
    dateInput.setHorizontalAlignment(JLabel.CENTER);
    datePanel.add(dateInput);

    sellInterimBtn.setHorizontalAlignment(JButton.CENTER);
    datePanel.add(sellInterimBtn);

    sellDateCheckerMsg.setText("");
    sellDateCheckerMsg.setFont(new Font("Arial", Font.PLAIN, 10));
    datePanel.add(sellDateCheckerMsg);

    contentPanel.add(datePanel, BorderLayout.NORTH);

    valueMsg.setText("");
    valueMsg.setFont(new Font("Arial", Font.ITALIC, 25));
    valueMsg.setHorizontalAlignment(JLabel.CENTER);

    JLabel mapHead = new JLabel("Shares of each Stock");
    mapHead.setFont(new Font("Arial", Font.PLAIN, 15));
    mapHead.setHorizontalAlignment(JLabel.CENTER);
    contentPanel.add(valueMsg, BorderLayout.LINE_START);

    form.setLayout(new GridLayout(4, 2));
    form.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 20));

    JLabel stock = new JLabel("Stock Name");
    stock.setFont(new Font("Arial", Font.PLAIN, 15));
    stock.setHorizontalAlignment(JLabel.CENTER);
    form.add(stock);

    stockInput.setText("");
    form.add(stockInput);

    JLabel quantity = new JLabel("Quantity");
    quantity.setFont(new Font("Arial", Font.PLAIN, 15));
    quantity.setHorizontalAlignment(JLabel.CENTER);
    form.add(quantity);

    quantityInput.setText("");
    form.add(quantityInput);

    JLabel commFee = new JLabel("Commission Fee");
    commFee.setHorizontalAlignment(JLabel.CENTER);
    commFee.setFont(new Font("Arial", Font.PLAIN, 15));
    form.add(commFee);

    commFeeInput.setText("");
    form.add(commFeeInput);

    form.add(sellStockBtn);

    confirmationMsg.setFont(new Font("Arial", Font.PLAIN, 15));
    confirmationMsg.setText("");
    confirmationMsg.setHorizontalAlignment(JLabel.CENTER);
    form.add(confirmationMsg);
    form.setBackground(Color.decode("#B8DEFF"));

    form.setVisible(false);
    contentPanel.add(form, BorderLayout.CENTER);

  }

}
