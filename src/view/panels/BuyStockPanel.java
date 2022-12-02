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
 * BuyStock Panel.
 */
public class BuyStockPanel {

 /**
  * Constructor for BuyStock Panel.
  */
  BuyStockPanel() {

  }

  void createBuyView(JPanel contentPanel, String portfolioCreationDate, JPanel form,
      JTextField stockInput, JTextField quantityInput, JTextField dateInput,
      JTextField commFeeInput,
      JButton buyStockBtn, JLabel confirmationMsg) {

    contentPanel.setLayout(new BorderLayout());
    contentPanel.setBackground(Color.decode("#B8DEFF"));

    JPanel top = new JPanel(new GridLayout(2, 1));
    JLabel headerDate = new JLabel(
        "Portfolio creation date (yyyy-MM-dd) : " + portfolioCreationDate);
    headerDate.setFont(new Font("Arial", Font.ITALIC, 20));
    headerDate.setHorizontalAlignment(JLabel.CENTER);
    JLabel stockList = new JLabel("List of Available Stocks : NASDAQ 100");
    stockList.setFont(new Font("Arial", Font.ITALIC, 20));
    stockList.setHorizontalAlignment(JLabel.CENTER);
    top.add(headerDate);
    top.add(stockList);
    top.setBackground(Color.decode("#B8DEFF"));
    contentPanel.add(top, BorderLayout.PAGE_START);

    form.setLayout(new GridLayout(5, 2));
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

    JLabel date = new JLabel("Date (yyyy-MM-dd)");
    date.setHorizontalAlignment(JLabel.CENTER);
    date.setFont(new Font("Arial", Font.PLAIN, 15));
    form.add(date);

    dateInput.setText("");
    form.add(dateInput);

    JLabel commFee = new JLabel("Commission Fee");
    commFee.setHorizontalAlignment(JLabel.CENTER);
    commFee.setFont(new Font("Arial", Font.PLAIN, 15));
    form.add(commFee);

    commFeeInput.setText("");
    form.add(commFeeInput);

    form.add(buyStockBtn);

    confirmationMsg.setText("");
    confirmationMsg.setFont(new Font("Arial", Font.PLAIN, 10));
    confirmationMsg.setHorizontalAlignment(JLabel.CENTER);
    form.add(confirmationMsg);
    form.setBackground(Color.decode("#B8DEFF"));

    contentPanel.add(form, BorderLayout.CENTER);
  }


}
