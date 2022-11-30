package view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class NormalPortfolioCreationPanel {

  NormalPortfolioCreationPanel(){

  }

  JPanel printNormalCreationMenu( JPanel form, JTextField portNameInput,
      JTextField stockInput, JTextField dateInput, JTextField quantityInput, JTextField commFeeInput,
      JTextArea tout, JLabel confirmationMsg, JButton normalFormSubmitButton, JButton normalFormAddMoreButton){

//    if(form!=null)main.remove(form);

    form = new JPanel();
    form.setLayout(new BorderLayout());

    JLabel title = new JLabel("Normal Portfolio Creation Form");
    title.setFont(new Font("Arial", Font.PLAIN, 15));
    title.setSize(300, 30);
    title.setLocation(50, 10);
    form.add(title);

    JLabel portfolioName = new JLabel("Portfolio Name");
    portfolioName.setFont(new Font("Arial", Font.PLAIN, 13));
    portfolioName.setSize(100, 20);
    portfolioName.setLocation(50, 60);
    form.add(portfolioName);

    portNameInput.setFont(new Font("Arial", Font.PLAIN, 13));
    portNameInput.setSize(190, 20);
    portNameInput.setLocation(140, 60);
    form.add(portNameInput);

    JLabel stock = new JLabel("Stock");
    stock.setFont(new Font("Arial", Font.PLAIN, 13));
    stock.setSize(100, 20);
    stock.setLocation(50, 110);
    form.add(stock);

    stockInput.setFont(new Font("Arial", Font.PLAIN, 13));
    stockInput.setSize(190, 20);
    stockInput.setLocation(100, 110);
    form.add(stockInput);

    JLabel mno = new JLabel("<html>date of transaction <br /> (yyyy-MM-dd)</html>");
    mno.setFont(new Font("Arial", Font.PLAIN, 13));
    mno.setSize(200, 50);
    mno.setLocation(20, 140);
    form.add(mno);

    dateInput.setFont(new Font("Arial", Font.PLAIN, 13));
    dateInput.setSize(150, 20);
    dateInput.setLocation(150, 160);
    form.add(dateInput);


    JLabel quantity = new JLabel("Number of stocks");
    quantity.setFont(new Font("Arial", Font.PLAIN, 13));
    quantity.setSize(150, 20);
    quantity.setLocation(20, 210);
    form.add(quantity);


    quantityInput.setFont(new Font("Arial", Font.PLAIN, 13));
    quantityInput.setSize(170, 20);
    quantityInput.setLocation(150, 210);
    form.add(quantityInput);

    JLabel commFee = new JLabel("Commission Fee");
    commFee.setFont(new Font("Arial", Font.PLAIN, 13));
    commFee.setSize(100, 20);
    commFee.setLocation(50, 260);
    form.add(commFee);


    commFeeInput.setFont(new Font("Arial", Font.PLAIN, 13));
    commFeeInput.setSize(190, 20);
    commFeeInput.setLocation(150, 260);
    form.add(commFeeInput);


    tout.setText("");
    tout.setFont(new Font("Arial", Font.PLAIN, 15));
    tout.setBackground(Color.CYAN);
    tout.setSize(250, 200);
    tout.setLocation(350, 40);
    tout.setLineWrap(true);
    tout.setEditable(false);
    form.add(tout);


    confirmationMsg.setText("");
    confirmationMsg.setFont(new Font("Arial", Font.PLAIN, 12));
    confirmationMsg.setSize(250, 20);
    confirmationMsg.setLocation(370, 260);
    form.add(confirmationMsg);

    normalFormSubmitButton.setFont(new Font("Arial", Font.PLAIN, 15));
    normalFormSubmitButton.setSize(100, 20);
    normalFormSubmitButton.setLocation(50, 310);

    form.add(normalFormSubmitButton);


    normalFormAddMoreButton.setFont(new Font("Arial", Font.PLAIN, 15));
    normalFormAddMoreButton.setSize(160, 20);
    normalFormAddMoreButton.setLocation(160, 310);
    form.add(normalFormAddMoreButton);

    JButton resetBtn = new JButton("");
    resetBtn.setFont(new Font("Arial", Font.PLAIN, 15));
    resetBtn.setSize(10, 20);
    resetBtn.setLocation(260, 310);
    resetBtn.addActionListener(e -> System.out.println("Clicked"));
    resetBtn.setVisible(false);
    form.add(resetBtn);



//    main.add(form, BorderLayout.CENTER);
//    main.revalidate();
    return form;

  }

}
