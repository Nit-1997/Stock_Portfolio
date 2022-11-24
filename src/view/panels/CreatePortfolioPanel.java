package view.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.*;
import java.awt.*;

public class CreatePortfolioPanel extends JPanel implements IPanel{

  private String dates[]
      = { "1", "2", "3", "4", "5",
      "6", "7", "8", "9", "10",
      "11", "12", "13", "14", "15",
      "16", "17", "18", "19", "20",
      "21", "22", "23", "24", "25",
      "26", "27", "28", "29", "30",
      "31" };
  private String months[]
      = { "Jan", "feb", "Mar", "Apr",
      "May", "Jun", "July", "Aug",
      "Sup", "Oct", "Nov", "Dec" };
  private String years[]
      = { "1995", "1996", "1997", "1998",
      "1999", "2000", "2001", "2002",
      "2003", "2004", "2005", "2006",
      "2007", "2008", "2009", "2010",
      "2011", "2012", "2013", "2014",
      "2015", "2016", "2017", "2018",
      "2019","2020","2021","2022" };

  JPanel form=null;

  JButton normalFormSubmitButton, DCAFormSubmitButton, normalFormAddMoreButton;

  JTextField portNameInput, stockInput, quantityInput, commFeeInput;
  
  JComboBox date, month, year;
  
  JTextArea tout;

  JLabel confirmationMsg;


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
    portfolioName.setFont(new Font("Arial", Font.PLAIN, 10));
    portfolioName.setSize(100, 20);
    portfolioName.setLocation(50, 100);
    form.add(portfolioName);

    this.portNameInput = new JTextField();
    this.portNameInput.setFont(new Font("Arial", Font.PLAIN, 10));
    this.portNameInput.setSize(190, 20);
    this.portNameInput.setLocation(130, 100);
    form.add(this.portNameInput);

    JLabel stock = new JLabel("Stock");
    stock.setFont(new Font("Arial", Font.PLAIN, 10));
    stock.setSize(100, 20);
    stock.setLocation(50, 150);
    form.add(stock);

    this.stockInput = new JTextField();
    this.stockInput.setFont(new Font("Arial", Font.PLAIN, 10));
    this.stockInput.setSize(190, 20);
    this.stockInput.setLocation(100, 150);
    this.form.add(stockInput);

    JLabel mno = new JLabel("date of transaction");
    mno.setFont(new Font("Arial", Font.PLAIN, 10));
    mno.setSize(100, 20);
    mno.setLocation(50, 200);
    form.add(mno);


    this.date = new JComboBox(dates);
    this.date.setFont(new Font("Arial", Font.PLAIN, 10));
    this.date.setSize(50, 20);
    this.date.setLocation(150, 200);
    form.add(this.date);

    this.month = new JComboBox(months);
    this.month.setFont(new Font("Arial", Font.PLAIN, 10));
    this.month.setSize(60, 20);
    this.month.setLocation(200, 200);
    form.add(this.month);

    this.year = new JComboBox(years);
    this.year.setFont(new Font("Arial", Font.PLAIN, 10));
    this.year.setSize(60, 20);
    this.year.setLocation(250, 200);
    form.add(this.year);

    JLabel quantity = new JLabel("Number of stocks");
    quantity.setFont(new Font("Arial", Font.PLAIN, 10));
    quantity.setSize(100, 20);
    quantity.setLocation(50, 250);
    form.add(quantity);

    this.quantityInput = new JTextField();
    this.quantityInput.setFont(new Font("Arial", Font.PLAIN, 10));
    this.quantityInput.setSize(190, 20);
    this.quantityInput.setLocation(150, 250);
    form.add(this.quantityInput);

    JLabel commFee = new JLabel("Commission Fee");
    commFee.setFont(new Font("Arial", Font.PLAIN, 10));
    commFee.setSize(100, 20);
    commFee.setLocation(50, 300);
    form.add(commFee);

    this.commFeeInput = new JTextField();
    this.commFeeInput.setFont(new Font("Arial", Font.PLAIN, 10));
    this.commFeeInput.setSize(190, 20);
    this.commFeeInput.setLocation(150, 300);
    form.add(this.commFeeInput);

    this.tout = new JTextArea("Portfolio stocks data");
    this.tout.setFont(new Font("Arial", Font.PLAIN, 15));
    this.tout.setBackground(Color.YELLOW);
    this.tout.setSize(250, 200);
    this.tout.setLocation(350, 80);
    this.tout.setLineWrap(true);
    this.tout.setEditable(false);
    form.add(this.tout);

    this.confirmationMsg = new JLabel("");
    this.confirmationMsg.setFont(new Font("Arial", Font.PLAIN, 10));
    this.confirmationMsg.setSize(200, 20);
    this.confirmationMsg.setLocation(350, 300);
    form.add(this.confirmationMsg);


    this.normalFormSubmitButton = new JButton("Submit");
    this.normalFormAddMoreButton.setActionCommand("Normal Portfolio Creation Submit");
    this.normalFormSubmitButton.setFont(new Font("Arial", Font.PLAIN, 15));
    this.normalFormSubmitButton.setSize(70, 20);
    this.normalFormSubmitButton.setLocation(50, 350);

    form.add(this.normalFormSubmitButton);

    this.normalFormAddMoreButton = new JButton("Add more stocks");
    this.normalFormAddMoreButton.setActionCommand("Normal Form Add More Button");
    this.normalFormAddMoreButton.setFont(new Font("Arial", Font.PLAIN, 15));
    this.normalFormAddMoreButton.setSize(200, 20);
    this.normalFormAddMoreButton.setLocation(150, 350);

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
      date.setSelectedIndex(0);
      month.setSelectedIndex(0);
      year.setSelectedIndex(0);
    });
    form.add(resetBtn);



    this.add(form);
    this.revalidate();
    this.repaint();
  }

  private void printDCACreationMenu(){


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
    portfolioName.setLocation(50, 100);
    form.add(portfolioName);

    JTextField portNameInput = new JTextField();
    portNameInput.setFont(new Font("Arial", Font.PLAIN, 15));
    portNameInput.setSize(160, 20);
    portNameInput.setLocation(180, 100);
    form.add(portNameInput);

    JLabel startDate = new JLabel("Start Date (MM/dd/YYYY)");
    startDate.setFont(new Font("Arial", Font.PLAIN, 15));
    startDate.setSize(170, 20);
    startDate.setLocation(20, 130);
    form.add(startDate);

    JTextField startDateInput = new JTextField(10);
    startDateInput.setFont(new Font("Arial", Font.PLAIN, 15));
    startDateInput.setSize(100, 20);
    startDateInput.setLocation(200, 130);
    form.add(startDateInput);

    JLabel endDate = new JLabel("End Date (MM/dd/YYYY)");
    endDate.setFont(new Font("Arial", Font.PLAIN, 15));
    endDate.setSize(170, 20);
    endDate.setLocation(310, 130);
    form.add(endDate);

    JTextField endDateInput = new JTextField(10);
    endDateInput.setFont(new Font("Arial", Font.PLAIN, 15));
    endDateInput.setSize(100, 20);
    endDateInput.setLocation(480, 130);
    form.add(endDateInput);

    JLabel amount = new JLabel("Amount to be invested($)");
    amount.setFont(new Font("Arial", Font.PLAIN, 15));
    amount.setSize(170, 20);
    amount.setLocation(20, 160);
    form.add(amount);

    JTextField amountInput = new JTextField(10);
    amountInput.setFont(new Font("Arial", Font.PLAIN, 15));
    amountInput.setSize(80, 20);
    amountInput.setLocation(200, 160);
    form.add(amountInput);

    JLabel commFee = new JLabel("Commission Fee($)");
    commFee.setFont(new Font("Arial", Font.PLAIN, 15));
    commFee.setSize(140, 20);
    commFee.setLocation(290, 160);
    form.add(commFee);

    JTextField commFeeInput = new JTextField(10);
    commFeeInput.setFont(new Font("Arial", Font.PLAIN, 15));
    commFeeInput.setSize(100, 20);
    commFeeInput.setLocation(430, 160);
    form.add(commFeeInput);

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


    JTextField stockInput = new JTextField(10);
    stockInput.setFont(new Font("Arial", Font.PLAIN, 15));
    stockInput.setSize(100, 20);
    stockInput.setLocation(70, 200);
    temp.add(stockInput);

    JLabel quantity = new JLabel("Weight(%) ");
    quantity.setFont(new Font("Arial", Font.PLAIN, 15));
    quantity.setSize(80, 20);
    quantity.setLocation(180, 200);
    temp.add(quantity);

    JTextField quantityInput = new JTextField(10);
    quantityInput.setFont(new Font("Arial", Font.PLAIN, 15));
    quantityInput.setSize(100, 20);
    quantityInput.setLocation(270, 200);
    temp.add(quantityInput);

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

          this.DCAFormSubmitButton = new JButton("Submit");
          this.DCAFormSubmitButton.setActionCommand("DCA Form Submit");
          this.DCAFormSubmitButton.setFont(new Font("Arial", Font.PLAIN, 15));
          this.DCAFormSubmitButton.setSize(70, 20);
          this.DCAFormSubmitButton.setLocation(50, 350);
          form.add(this.DCAFormSubmitButton);

          JButton resetBtn = new JButton("");
          resetBtn.setFont(new Font("Arial", Font.PLAIN, 15));
          resetBtn.setSize(0, 20);
          resetBtn.setLocation(260, 350);
          form.add(resetBtn);

          this.add(form);
          this.revalidate();
          this.repaint();
        }

  private void printMoreLines(int y, JPanel scrollPane, ActionEvent evt) {
    System.out.println(y);

    JPanel temp = new JPanel();

    JLabel stock = new JLabel("Stock");
    stock.setFont(new Font("Arial", Font.PLAIN, 15));
    stock.setSize(50, 20);
    stock.setLocation(10, y);
    temp.add(stock);


    JTextField stockInput = new JTextField(10);
    stockInput.setFont(new Font("Arial", Font.PLAIN, 15));
    stockInput.setSize(100, 20);
    stockInput.setLocation(70, y);
    temp.add(stockInput);


    JLabel quantity = new JLabel("Weight(%) ");
    quantity.setFont(new Font("Arial", Font.PLAIN, 15));
    quantity.setSize(80, 20);
    quantity.setLocation(180, y);
    temp.add(quantity);


    JTextField quantityInput = new JTextField(10);
    quantityInput.setFont(new Font("Arial", Font.PLAIN, 15));
    quantityInput.setSize(100, 20);
    quantityInput.setLocation(270, y);
    temp.add(quantityInput);


    scrollPane.add(temp);
    scrollPane.revalidate();
    scrollPane.repaint();


  }

  public List<String> getNormalFormData(){
    List<String> data = new ArrayList<>();
    data.add(this.portNameInput.getText().trim());
    data.add(this.stockInput.getText().trim());
    data.add(this.quantityInput.getText().trim());
    data.add(month.getSelectedItem()+"/"+date.getSelectedItem()+"/"+year.getSelectedItem());
    data.add(this.commFeeInput.getText().trim());
    return data;
  }

  public String getAreaText(){
      return this.tout.getText();
  }

  @Override
  public void addActionListener(ActionListener listener) {
    this.normalFormSubmitButton.addActionListener(listener);
//    this.DCAFormSubmitButton.addActionListener(listener);
    this.normalFormAddMoreButton.addActionListener(listener);
  }



  public void printForNormalPortfolioCreation(String str){
    this.confirmationMsg.setText(str);
    if(str.equals("Portfolio Successfully Saved")){
        this.confirmationMsg.setForeground(Color.GREEN);
      }
      else{
      this.confirmationMsg.setForeground(Color.RED);
      }
  }
}
