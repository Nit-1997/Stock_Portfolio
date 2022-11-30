package view.panels;

import constants.Constants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class DCAPortfolioCreationPanel {

  String msg;
  boolean val;

  Double sum;

  JTextField stockInput, percentageInput;

  DCAPortfolioCreationPanel(){
    this.msg="";
    this.val=false;
    this.sum=0.0;
  }

  JPanel printDCACreationMenu(Map<String, Double> dcaStockMap, JPanel main,
      JPanel form, JTextField portNameInput, JTextField amountInput, JTextField startDateInput,
      JTextField endDateInput, JTextField intervalInput, JTextField commFeeInput,
      JTextField stockInput, JTextField percentageInput, JButton DCAFormSubmitButton,
      JLabel confirmationMsg){


    this.stockInput=stockInput;
    this.percentageInput=percentageInput;
//    dcaStockMap = new HashMap<>();

//    if(form!=null)main.remove(form);

    form = new JPanel();
    form.setLayout(new BorderLayout());

    JLabel title = new JLabel("Portfolio Creation with Dollar Cost Averaging");
    title.setFont(new Font("Arial", Font.PLAIN, 15));
    title.setSize(300, 30);
    title.setLocation(50, 10);
    form.add(title);

    JLabel portfolioName = new JLabel("Portfolio Name");
    portfolioName.setFont(new Font("Arial", Font.PLAIN, 15));
    portfolioName.setSize(100, 20);
    portfolioName.setLocation(40, 60);
    form.add(portfolioName);

    portNameInput.setText("");
    portNameInput.setFont(new Font("Arial", Font.PLAIN, 15));
    portNameInput.setSize(160, 20);
    portNameInput.setLocation(150, 60);
    form.add(portNameInput);

    JLabel amount = new JLabel("Amount to be invested($)");
    amount.setFont(new Font("Arial", Font.PLAIN, 15));
    amount.setSize(170, 20);
    amount.setLocation(320, 60);
    form.add(amount);

    amountInput.setText("");
    amountInput.setFont(new Font("Arial", Font.PLAIN, 15));
    amountInput.setSize(80, 20);
    amountInput.setLocation(500, 60);
    form.add(amountInput);

    JLabel startDate = new JLabel("Start Date (yyyy-MM-dd)");
    startDate.setFont(new Font("Arial", Font.PLAIN, 15));
    startDate.setSize(170, 20);
    startDate.setLocation(20, 90);
    form.add(startDate);

    startDateInput.setText("");
    startDateInput.setFont(new Font("Arial", Font.PLAIN, 15));
    startDateInput.setSize(100, 20);
    startDateInput.setLocation(200, 90);
    form.add(startDateInput);

    JLabel endDate = new JLabel("End Date (yyyy-MM-dd)");
    endDate.setFont(new Font("Arial", Font.PLAIN, 15));
    endDate.setSize(170, 20);
    endDate.setLocation(310, 90);
    form.add(endDate);

    endDateInput.setText("");
    endDateInput.setFont(new Font("Arial", Font.PLAIN, 15));
    endDateInput.setSize(100, 20);
    endDateInput.setLocation(480, 90);
    form.add(endDateInput);


    JLabel interval = new JLabel("Interval (in days)");
    interval.setFont(new Font("Arial", Font.PLAIN, 15));
    interval.setSize(130, 20);
    interval.setLocation(20, 120);
    form.add(interval);

    intervalInput.setText("");
    intervalInput.setFont(new Font("Arial", Font.PLAIN, 15));
    intervalInput.setSize(80, 20);
    intervalInput.setLocation(150, 120);
    form.add(intervalInput);

    JLabel commFee = new JLabel("Commission Fee($)");
    commFee.setFont(new Font("Arial", Font.PLAIN, 15));
    commFee.setSize(140, 20);
    commFee.setLocation(290, 120);
    form.add(commFee);

    commFeeInput.setText("");
    commFeeInput.setFont(new Font("Arial", Font.PLAIN, 15));
    commFeeInput.setSize(100, 20);
    commFeeInput.setLocation(430, 120);
    form.add(commFeeInput);

    JPanel scrollPanel = new JPanel();
    scrollPanel.setLayout(new BoxLayout(scrollPanel,BoxLayout.Y_AXIS));
    JScrollPane scroll = new JScrollPane(scrollPanel);
    scroll.setBounds(45,150,440,150);
    form.add(scroll);

    JPanel temp = new JPanel();

    JLabel stock = new JLabel("Stock");
    stock.setFont(new Font("Arial", Font.PLAIN, 15));
    stock.setSize(50, 20);
    stock.setLocation(10, 160);
    temp.add(stock);

    this.stockInput.setText("");
    this.stockInput.setEditable(true);
    this.stockInput.setFont(new Font("Arial", Font.PLAIN, 15));
    this.stockInput.setSize(100, 20);
    this.stockInput.setLocation(70, 160);
    temp.add(this.stockInput);

    JLabel quantity = new JLabel("Weight(%) ");
    quantity.setFont(new Font("Arial", Font.PLAIN, 15));
    quantity.setSize(80, 20);
    quantity.setLocation(180, 160);
    temp.add(quantity);

    this.percentageInput.setText("");
    this.percentageInput.setEditable(true);
    this.percentageInput.setFont(new Font("Arial", Font.PLAIN, 15));
    this.percentageInput.setSize(100, 20);
    this.percentageInput.setLocation(270, 160);
    temp.add(this.percentageInput);

    scrollPanel.add(temp);

    JButton moreAdd = new JButton("Add");
    moreAdd.setFont(new Font("Arial", Font.PLAIN, 15));
    moreAdd.setSize(80, 20);
    moreAdd.setLocation(500, 200);


    AtomicInteger y = new AtomicInteger(190);
    moreAdd.addActionListener(e-> {
      this.printMoreLines(dcaStockMap,confirmationMsg,y.get(),scrollPanel);
      y.addAndGet(30);
    });
    form.add(moreAdd);

    form.add(DCAFormSubmitButton);
    DCAFormSubmitButton.setFont(new Font("Arial", Font.PLAIN, 15));
    DCAFormSubmitButton.setSize(100, 20);
    DCAFormSubmitButton.setLocation(50, 310);

    confirmationMsg.setText("");
    confirmationMsg.setFont(new Font("Arial", Font.PLAIN, 15));
    confirmationMsg.setSize(250, 20);
    confirmationMsg.setLocation(170, 310);
    form.add(confirmationMsg);

    JButton resetBtn = new JButton("");
    resetBtn.setFont(new Font("Arial", Font.PLAIN, 15));
    resetBtn.setSize(0, 20);
    resetBtn.setLocation(260, 310);
    resetBtn.setVisible(false);
    form.add(resetBtn);

//    main.add(form,BorderLayout.CENTER);
//    main.revalidate();

    return form;

  }

  private void printMoreLines(Map<String, Double> dcaStockMap,
      JLabel confirmationMsg,int y, JPanel scrollPane) {

    if(!this.inputValidation(dcaStockMap,confirmationMsg)) return;

    stockInput.setEditable(false);
    percentageInput.setEditable(false);

    this.printForDCAPortfolioCreation(confirmationMsg,"Added",true);


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


    percentageInput = new JTextField(10);
    percentageInput.setFont(new Font("Arial", Font.PLAIN, 15));
    percentageInput.setSize(100, 20);
    percentageInput.setLocation(270, y);
    temp.add(percentageInput);


    scrollPane.add(temp);
    scrollPane.revalidate();
    scrollPane.repaint();

  }

   boolean inputValidation( Map<String, Double> dcaStockMap,JLabel confirmationMsg){
    String stock = this.stockInput.getText();
    String percentage = this.percentageInput.getText();
    if(stock.equals("") || percentage.equals("")){
      this.printForDCAPortfolioCreation(confirmationMsg,"Empty record",false);
      return false;
    }

    if(!Constants.STOCK_NAMES.contains(stock.toUpperCase())){
      this.printForDCAPortfolioCreation(confirmationMsg,"Not a valid stock name",false);
      return false;
    }
    Double percent;
    try{
      percent = Double.parseDouble(percentage);
    } catch(NumberFormatException e){
      this.printForDCAPortfolioCreation(confirmationMsg,"Number not in numeric format",false);
      return false;
    }
    if(percent==0){
      this.printForDCAPortfolioCreation(confirmationMsg,"Percentage 0",false);
      return false;
    }
    if(percent<0 || percent>100){
      this.printForDCAPortfolioCreation(confirmationMsg,"Percentage -ve or 100+",false);
      return false;
    }
    if(this.sum+percent>100.0){
      this.printForDCAPortfolioCreation(confirmationMsg,"Weights sum more than 100",false);
      return false;
    }
    this.sum+=percent;
    if(dcaStockMap.containsKey(stock.toUpperCase()))
      dcaStockMap.put(stock.toUpperCase(),dcaStockMap.get(stock.toUpperCase())+percent);
    else dcaStockMap.put(stock.toUpperCase(),percent);

     System.out.println(dcaStockMap);

    return true;
  }

  public void printForDCAPortfolioCreation(JLabel confirmationMsg,String str, boolean isGood){
    if(isGood) confirmationMsg.setForeground(Color.GREEN);
    else confirmationMsg.setForeground(Color.RED);
    confirmationMsg.setText(str);
  }

}
