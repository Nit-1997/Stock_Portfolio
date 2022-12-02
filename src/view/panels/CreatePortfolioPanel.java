package view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * PortfolioCreation Panel.
 */
public class CreatePortfolioPanel extends JPanel implements IPanel {

  JPanel form = null;

  JButton normalFormSubmitButton, DCAFormSubmitButton, normalFormAddMoreButton;

  JTextField portNameInput, dateInput, stockInput, quantityInput, commFeeInput;

  JTextField startDateInput, endDateInput, amountInput, percentageInput, intervalInput;
  JTextArea tout;

  JLabel confirmationMsg;

  Map<String, Double> dcaStockMap;

  Double sum;

  DCAPortfolioPanel DCAPanelObj;


  /**
   * Create PortfolioPanel Constructor.
   */
  public CreatePortfolioPanel() {

    this.normalFormSubmitButton = new JButton("Submit");
    this.normalFormSubmitButton.setActionCommand("Normal Portfolio Creation Submit");

    this.normalFormAddMoreButton = new JButton("Add more stocks");
    this.normalFormAddMoreButton.setActionCommand("Normal Form Add More Button");

    this.DCAFormSubmitButton = new JButton("Submit");
    this.DCAFormSubmitButton.setActionCommand("DCA Form Submit");

    this.portNameInput = new JTextField();
    this.stockInput = new JTextField(10);
    this.dateInput = new JTextField();
    this.quantityInput = new JTextField();
    this.commFeeInput = new JTextField();
    this.tout = new JTextArea("");
    confirmationMsg = new JLabel("");

    this.amountInput = new JTextField(10);
    this.startDateInput = new JTextField(10);
    this.endDateInput = new JTextField(10);
    this.intervalInput = new JTextField(10);
    this.percentageInput = new JTextField(10);

    this.sum = 0.0;
    this.dcaStockMap = new HashMap<>();

    this.setLayout(new BorderLayout());

    JPanel starter = new JPanel();
    starter.setLayout(new FlowLayout());
    JLabel header = new JLabel("Portfolio Creation");
    header.setHorizontalAlignment(JLabel.CENTER);
    starter.add(header, BorderLayout.NORTH);

    JPanel portfolioButtons = new JPanel();
    portfolioButtons.setLayout(new GridLayout(1, 2));

    JButton normalPortfolioCreation = new JButton("Create Normal Portfolio");
    portfolioButtons.add(normalPortfolioCreation);

    normalPortfolioCreation.addActionListener(e -> {
      if (this.form != null) {
        this.remove(this.form);
      }

      form = new NormalPortfolioCreationPanel()
          .printNormalCreationMenu(this.form, this.portNameInput, this.stockInput,
              this.dateInput, this.quantityInput, this.commFeeInput, this.tout,
              this.confirmationMsg,
              this.normalFormSubmitButton, this.normalFormAddMoreButton);

      this.add(form, BorderLayout.CENTER);
      this.revalidate();
    });

    JButton strategyPortfolioCreation = new JButton(
        "<html>Create Portfolio using<br /> Dollar Cost Averaging</html>");
    strategyPortfolioCreation.addActionListener(e -> {
      if (this.form != null) {
        this.remove(this.form);
      }

      this.DCAPanelObj = new DCAPortfolioPanel();
      form = this.DCAPanelObj.printDCACreationMenu(this.dcaStockMap, null, this.form,
          this.portNameInput, this.amountInput, this.startDateInput, this.endDateInput,
          this.intervalInput,
          this.commFeeInput, this.stockInput, this.percentageInput, this.DCAFormSubmitButton,
          this.confirmationMsg);

      this.add(form, BorderLayout.CENTER);
      this.revalidate();
    });
    portfolioButtons.add(strategyPortfolioCreation);

    starter.add(portfolioButtons);

    this.add(starter, BorderLayout.PAGE_START);
  }

  @Override
  public JPanel getJPanel() {
    return this;
  }


  public List<String> getNormalFormData() {
    List<String> data = new ArrayList<>();
    data.add(this.portNameInput.getText().trim());
    data.add(this.stockInput.getText().trim());
    data.add(this.quantityInput.getText().trim());
    data.add(this.dateInput.getText().trim());
    data.add(this.commFeeInput.getText().trim());
    return data;
  }

  public String getAreaText() {
    return this.tout.getText();
  }

  public List<String> getDCAFormData() {
    List<String> data = new ArrayList<>();
    data.add(this.portNameInput.getText().trim());
    data.add(this.startDateInput.getText().trim());
    data.add(this.endDateInput.getText().trim());
    data.add(this.amountInput.getText().trim());
    data.add(this.intervalInput.getText().trim());
    data.add(this.commFeeInput.getText().trim());
    return data;
  }

  public Map<String, Double> getDcaStockMap() {
    this.DCAPanelObj.inputValidation(this.dcaStockMap, this.confirmationMsg);
    this.dcaStockMap.remove("");
    return this.dcaStockMap;
  }

  @Override
  public void addActionListener(ActionListener listener) {
    normalFormSubmitButton.addActionListener(listener);
    DCAFormSubmitButton.addActionListener(listener);
    normalFormAddMoreButton.addActionListener(listener);
  }

  public void printForDCAPortfolioCreation(String str, boolean isGood) {
    if (str.equals("Portfolio Successfully Saved")) {
      if (this.form != null) {
        this.remove(this.form);
      }
      this.dcaStockMap = new HashMap<>();
      this.DCAPanelObj = new DCAPortfolioPanel();
      form = this.DCAPanelObj.printDCACreationMenu(this.dcaStockMap, null, this.form,
          this.portNameInput, this.amountInput, this.startDateInput, this.endDateInput,
          this.intervalInput,
          this.commFeeInput, this.stockInput, this.percentageInput, this.DCAFormSubmitButton,
          this.confirmationMsg);

      this.add(form, BorderLayout.CENTER);
      this.revalidate();
    }
    if (isGood) {
      this.confirmationMsg.setForeground(Color.GREEN);
    } else {
      this.confirmationMsg.setForeground(Color.RED);
    }
    this.confirmationMsg.setText(str);
  }

  public void printForNormalPortfolioCreation(String str) {
    this.confirmationMsg.setText(str);

    if (str.equals("Stock Added successfully")) {
      String text = this.getAreaText();
      text = text + "\n" + this.stockInput.getText() + "," + this.dateInput.getText() + ","
          + this.quantityInput.getText() + ","
          + this.commFeeInput.getText();
      this.tout.setText(text);
      this.portNameInput.setEditable(false);
    }

    if (str.equals("Portfolio Successfully Saved")) {
      this.tout.setText("");
      this.portNameInput.setText("");
      this.portNameInput.setEditable(true);
    }

    if (str.equals("Portfolio Successfully Saved") || str.equals("Stock Added successfully")) {
      this.confirmationMsg.setForeground(Color.GREEN);
      this.stockInput.setText("");
      this.dateInput.setText("");
      this.quantityInput.setText("");
      this.commFeeInput.setText("");
    } else {
      this.confirmationMsg.setForeground(Color.RED);
    }
  }
}
