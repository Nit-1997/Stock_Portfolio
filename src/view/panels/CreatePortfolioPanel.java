package view.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.*;
import java.awt.*;

public class CreatePortfolioPanel extends JPanel implements IPanel{

  JPanel form=null;

  JButton normalFormSubmitButton, DCAFormSubmitButton, normalFormAddMoreButton;

  JTextField portNameInput, dateInput, stockInput, quantityInput, commFeeInput;

  JTextField startDateInput, endDateInput, amountInput, percentageInput, intervalInput;
  JTextArea tout;

  JLabel confirmationMsg;

  Map<String, Double> dcaStockMap;


  public CreatePortfolioPanel(){

    this.normalFormSubmitButton = new JButton("Submit");
    this.normalFormSubmitButton.setActionCommand("Normal Portfolio Creation Submit");
    this.normalFormSubmitButton.setFont(new Font("Arial", Font.PLAIN, 15));
    this.normalFormSubmitButton.setSize(70, 20);
    this.normalFormSubmitButton.setLocation(50, 350);


    this.normalFormAddMoreButton = new JButton("Add more stocks");
    this.normalFormAddMoreButton.setActionCommand("Normal Form Add More Button");
    this.normalFormAddMoreButton.setFont(new Font("Arial", Font.PLAIN, 15));
    this.normalFormAddMoreButton.setSize(200, 20);
    this.normalFormAddMoreButton.setLocation(150, 350);

    this.DCAFormSubmitButton = new JButton("Submit");
    this.DCAFormSubmitButton.setActionCommand("DCA Form Submit");
    this.DCAFormSubmitButton.setFont(new Font("Arial", Font.PLAIN, 15));
    this.DCAFormSubmitButton.setSize(70, 20);
    this.DCAFormSubmitButton.setLocation(50, 350);

    this.setLayout(new BorderLayout());

    JPanel starter = new JPanel();
    starter.setLayout(new FlowLayout());
    JLabel header = new JLabel("Portfolio Creation");
    header.setHorizontalAlignment(JLabel.CENTER);
    starter.add(header,BorderLayout.NORTH);

    JPanel portfolioButtons = new JPanel();
    portfolioButtons.setLayout(new GridLayout(1,2));

    JButton normalPortfolioCreation = new JButton("Create Normal Portfolio");
    portfolioButtons.add(normalPortfolioCreation);
    normalPortfolioCreation.addActionListener(e->this.printNormalCreationMenu());

    JButton strategyPortfolioCreation = new JButton("<html>Create Portfolio using<br /> Dollar Cost Averaging</html>");
    strategyPortfolioCreation.addActionListener(e->this.printDCACreationMenu());
    portfolioButtons.add(strategyPortfolioCreation);

    starter.add(portfolioButtons);

    this.add(starter,BorderLayout.PAGE_START);
  }

  @Override
  public JPanel getJPanel(){
    return this;
  }

  private void printNormalCreationMenu(){

    this.setLayout(new BorderLayout());
    if(this.form!=null)this.remove(this.form);

    form = new JPanel();
    form.setLayout(new BorderLayout());

    JLabel title = new JLabel("Normal Portfolio Creation Form");
    title.setFont(new Font("Arial", Font.PLAIN, 15));
    title.setSize(300, 30);
    title.setLocation(50, 50);
    form.add(title);

    JLabel portfolioName = new JLabel("Portfolio Name");
    portfolioName.setFont(new Font("Arial", Font.PLAIN, 13));
    portfolioName.setSize(100, 20);
    portfolioName.setLocation(50, 100);
    form.add(portfolioName);

    this.portNameInput = new JTextField();
    this.portNameInput.setFont(new Font("Arial", Font.PLAIN, 13));
    this.portNameInput.setSize(190, 20);
    this.portNameInput.setLocation(140, 100);
    form.add(this.portNameInput);

    JLabel stock = new JLabel("Stock");
    stock.setFont(new Font("Arial", Font.PLAIN, 13));
    stock.setSize(100, 20);
    stock.setLocation(50, 150);
    form.add(stock);

    this.stockInput = new JTextField();
    this.stockInput.setFont(new Font("Arial", Font.PLAIN, 13));
    this.stockInput.setSize(190, 20);
    this.stockInput.setLocation(100, 150);
    this.form.add(stockInput);

    JLabel mno = new JLabel("date of transaction");
    mno.setFont(new Font("Arial", Font.PLAIN, 13));
    mno.setSize(100, 20);
    mno.setLocation(50, 200);
    form.add(mno);


    this.dateInput = new JTextField();
    this.dateInput.setFont(new Font("Arial", Font.PLAIN, 13));
    this.dateInput.setSize(150, 20);
    this.dateInput.setLocation(150, 200);
    form.add(this.dateInput);


    JLabel quantity = new JLabel("Number of stocks");
    quantity.setFont(new Font("Arial", Font.PLAIN, 13));
    quantity.setSize(100, 20);
    quantity.setLocation(50, 250);
    form.add(quantity);

    this.quantityInput = new JTextField();
    this.quantityInput.setFont(new Font("Arial", Font.PLAIN, 13));
    this.quantityInput.setSize(190, 20);
    this.quantityInput.setLocation(150, 250);
    form.add(this.quantityInput);

    JLabel commFee = new JLabel("Commission Fee");
    commFee.setFont(new Font("Arial", Font.PLAIN, 13));
    commFee.setSize(100, 20);
    commFee.setLocation(50, 300);
    form.add(commFee);

    this.commFeeInput = new JTextField();
    this.commFeeInput.setFont(new Font("Arial", Font.PLAIN, 13));
    this.commFeeInput.setSize(190, 20);
    this.commFeeInput.setLocation(150, 300);
    form.add(this.commFeeInput);

    this.tout = new JTextArea("");
    this.tout.setFont(new Font("Arial", Font.PLAIN, 15));
    this.tout.setBackground(Color.CYAN);
    this.tout.setSize(250, 200);
    this.tout.setLocation(350, 80);
    this.tout.setLineWrap(true);
    this.tout.setEditable(false);
    form.add(this.tout);

    this.confirmationMsg = new JLabel("");
    this.confirmationMsg.setFont(new Font("Arial", Font.PLAIN, 12));
    this.confirmationMsg.setSize(250, 20);
    this.confirmationMsg.setLocation(370, 300);
    form.add(this.confirmationMsg);

    form.add(this.normalFormSubmitButton);


    form.add(this.normalFormAddMoreButton);

    JButton resetBtn = new JButton("");
    resetBtn.setFont(new Font("Arial", Font.PLAIN, 15));
    resetBtn.setSize(0, 20);
    resetBtn.setLocation(260, 350);
    resetBtn.addActionListener(e->{
      String def = "";
      stockInput.setText(def);
      commFeeInput.setText(def);
      quantityInput.setText(def);
      dateInput.setText(def);
    });
    form.add(resetBtn);



    this.add(form, BorderLayout.CENTER);
    this.revalidate();
    this.repaint();
    form.setFocusable(false);
  }

  private void printDCACreationMenu(){

    this.dcaStockMap = new HashMap<>();

    this.setLayout(new BorderLayout());
    if(this.form!=null)this.remove(this.form);

    form = new JPanel();
    form.setLayout(new BorderLayout());

    JLabel title = new JLabel("Portfolio Creation with Dollar Cost Averaging");
    title.setFont(new Font("Arial", Font.PLAIN, 15));
    title.setSize(300, 30);
    title.setLocation(50, 50);
    form.add(title);

    JLabel portfolioName = new JLabel("Portfolio Name");
    portfolioName.setFont(new Font("Arial", Font.PLAIN, 15));
    portfolioName.setSize(100, 20);
    portfolioName.setLocation(40, 100);
    form.add(portfolioName);

    this.portNameInput = new JTextField();
    this.portNameInput.setFont(new Font("Arial", Font.PLAIN, 15));
    this.portNameInput.setSize(160, 20);
    this.portNameInput.setLocation(150, 100);
    form.add(this.portNameInput);

    JLabel amount = new JLabel("Amount to be invested($)");
    amount.setFont(new Font("Arial", Font.PLAIN, 15));
    amount.setSize(170, 20);
    amount.setLocation(320, 100);
    form.add(amount);

    this.amountInput = new JTextField(10);
    this.amountInput.setFont(new Font("Arial", Font.PLAIN, 15));
    this.amountInput.setSize(80, 20);
    this.amountInput.setLocation(500, 100);
    form.add(this.amountInput);

    JLabel startDate = new JLabel("Start Date (yyyy-MM-dd)");
    startDate.setFont(new Font("Arial", Font.PLAIN, 15));
    startDate.setSize(170, 20);
    startDate.setLocation(20, 130);
    form.add(startDate);

    this.startDateInput = new JTextField(10);
    this.startDateInput.setFont(new Font("Arial", Font.PLAIN, 15));
    this.startDateInput.setSize(100, 20);
    this.startDateInput.setLocation(200, 130);
    form.add(this.startDateInput);

    JLabel endDate = new JLabel("End Date (yyyy-MM-dd)");
    endDate.setFont(new Font("Arial", Font.PLAIN, 15));
    endDate.setSize(170, 20);
    endDate.setLocation(310, 130);
    form.add(endDate);

    this.endDateInput = new JTextField(10);
    this.endDateInput.setFont(new Font("Arial", Font.PLAIN, 15));
    this.endDateInput.setSize(100, 20);
    this.endDateInput.setLocation(480, 130);
    form.add(this.endDateInput);


    JLabel interval = new JLabel("Interval (in days)");
    interval.setFont(new Font("Arial", Font.PLAIN, 15));
    interval.setSize(130, 20);
    interval.setLocation(20, 160);
    form.add(interval);

    this.intervalInput = new JTextField(10);
    this.intervalInput.setFont(new Font("Arial", Font.PLAIN, 15));
    this.intervalInput.setSize(80, 20);
    this.intervalInput.setLocation(150, 160);
    form.add(this.intervalInput);

    JLabel commFee = new JLabel("Commission Fee($)");
    commFee.setFont(new Font("Arial", Font.PLAIN, 15));
    commFee.setSize(140, 20);
    commFee.setLocation(290, 160);
    form.add(commFee);

    this.commFeeInput = new JTextField(10);
    this.commFeeInput.setFont(new Font("Arial", Font.PLAIN, 15));
    this.commFeeInput.setSize(100, 20);
    this.commFeeInput.setLocation(430, 160);
    form.add(this.commFeeInput);

    JPanel scrollPanel = new JPanel();
    scrollPanel.setLayout(new BoxLayout(scrollPanel,BoxLayout.Y_AXIS));
    JScrollPane scroll = new JScrollPane(scrollPanel);
    scroll.setBounds(45,190,440,150);
    form.add(scroll);

    JPanel temp = new JPanel();

    JLabel stock = new JLabel("Stock");
    stock.setFont(new Font("Arial", Font.PLAIN, 15));
    stock.setSize(50, 20);
    stock.setLocation(10, 200);
    temp.add(stock);


    this.stockInput = new JTextField(10);
    this.stockInput.setFont(new Font("Arial", Font.PLAIN, 15));
    this.stockInput.setSize(100, 20);
    this.stockInput.setLocation(70, 200);
    temp.add(this.stockInput);

    JLabel quantity = new JLabel("Weight(%) ");
    quantity.setFont(new Font("Arial", Font.PLAIN, 15));
    quantity.setSize(80, 20);
    quantity.setLocation(180, 200);
    temp.add(quantity);

    this.percentageInput = new JTextField(10);
    this.percentageInput.setFont(new Font("Arial", Font.PLAIN, 15));
    this.percentageInput.setSize(100, 20);
    this.percentageInput.setLocation(270, 200);
    temp.add(this.percentageInput);

    scrollPanel.add(temp);

    JButton moreAdd = new JButton("Add");
    moreAdd.setFont(new Font("Arial", Font.PLAIN, 15));
    moreAdd.setSize(50, 20);
    moreAdd.setLocation(500, 200);


    AtomicInteger y = new AtomicInteger(230);
    moreAdd.addActionListener(e-> {
          this.printMoreLines(y.get(),scrollPanel,e);
          y.addAndGet(30);
    });
          form.add(moreAdd);

          form.add(this.DCAFormSubmitButton);

          this.confirmationMsg = new JLabel("");
          this.confirmationMsg.setFont(new Font("Arial", Font.PLAIN, 15));
          this.confirmationMsg.setSize(250, 20);
          this.confirmationMsg.setLocation(130, 350);
          form.add(this.confirmationMsg);

          JButton resetBtn = new JButton("");
          resetBtn.setFont(new Font("Arial", Font.PLAIN, 15));
          resetBtn.setSize(0, 20);
          resetBtn.setLocation(260, 350);
          form.add(resetBtn);



          this.add(form,BorderLayout.CENTER);
          this.revalidate();
          this.repaint();
        }

  private void printMoreLines(int y, JPanel scrollPane, ActionEvent evt) {

    if(!this.inputValidation()) return;
    System.out.println(this.dcaStockMap);

    this.printForDCAPortfolioCreation("Added",true);

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


    this.percentageInput = new JTextField(10);
    this.percentageInput.setFont(new Font("Arial", Font.PLAIN, 15));
    this.percentageInput.setSize(100, 20);
    this.percentageInput.setLocation(270, y);
    temp.add(this.percentageInput);


    scrollPane.add(temp);
    scrollPane.revalidate();
    scrollPane.repaint();

  }

  private boolean inputValidation(){
    String stock = this.stockInput.getText();
    String percentage = this.percentageInput.getText();
    if(stock.equals("") || percentage.equals("")){
      this.printForDCAPortfolioCreation("Empty record",false);
      return false;
    }
    Double percent;
    try{
      percent = Double.parseDouble(percentage);
    } catch(NumberFormatException e){
      this.printForDCAPortfolioCreation("Number not in numeric format",false);
      return false;
    }
    if(percent==0){
      this.printForDCAPortfolioCreation("Percentage 0",false);
      return false;
    }
    if(percent<0 || percent>100){
      this.printForDCAPortfolioCreation("Percentage -ve or 100+",false);
      return false;
    }
    if(this.dcaStockMap.containsKey(stock.toUpperCase()))
      this.dcaStockMap.put(stock.toUpperCase(),this.dcaStockMap.get(stock)+percent);
    else this.dcaStockMap.put(stock.toUpperCase(),percent);

    return true;
  }

  public List<String> getNormalFormData(){
    List<String> data = new ArrayList<>();
    data.add(this.portNameInput.getText().trim());
    data.add(this.stockInput.getText().trim());
    data.add(this.quantityInput.getText().trim());
    data.add(this.dateInput.getText().trim());
    data.add(this.commFeeInput.getText().trim());
    return data;
  }

  public String getAreaText(){
      return this.tout.getText();
  }

  public List<String> getDCAFormData(){
    List<String> data = new ArrayList<>();
    data.add(this.portNameInput.getText().trim());
    data.add(this.startDateInput.getText().trim());
    data.add(this.endDateInput.getText().trim());
    data.add(this.amountInput.getText().trim());
    data.add(this.intervalInput.getText().trim());
    data.add(this.commFeeInput.getText().trim());
    return data;
  }

  public Map<String, Double> getDcaStockMap(){
    this.inputValidation();
    this.dcaStockMap.remove("");
    return this.dcaStockMap;
  }

  @Override
  public void addActionListener(ActionListener listener) {
    normalFormSubmitButton.addActionListener(listener);
    DCAFormSubmitButton.addActionListener(listener);
    normalFormAddMoreButton.addActionListener(listener);
  }


  public void printForDCAPortfolioCreation(String str, boolean isGood){
    if(str.equals("Portfolio Successfully Saved")){
      this.printDCACreationMenu();
    }
    if(isGood) this.confirmationMsg.setForeground(Color.GREEN);
    else this.confirmationMsg.setForeground(Color.RED);
    this.confirmationMsg.setText(str);
  }

  public void printForNormalPortfolioCreation(String str){
    this.confirmationMsg.setText(str);

    if(str.equals("Stock Added successfully")){
      String text = this.getAreaText();
      text=text+"\n"+this.stockInput.getText()+","+this.dateInput.getText()+","+this.quantityInput.getText()+","
          +this.commFeeInput.getText();
      this.tout.setText(text);
    }

    if(str.equals("Portfolio Successfully Saved")){
      this.tout.setText("");
      this.portNameInput.setText("");
    }

    if(str.equals("Portfolio Successfully Saved") || str.equals("Stock Added successfully")){
        this.confirmationMsg.setForeground(Color.GREEN);
        this.stockInput.setText("");
        this.dateInput.setText("");
        this.quantityInput.setText("");
        this.commFeeInput.setText("");
      }
      else{
      this.confirmationMsg.setForeground(Color.RED);
      }


  }
}
