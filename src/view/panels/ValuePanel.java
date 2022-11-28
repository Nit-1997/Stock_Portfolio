package view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ValuePanel {

  ValuePanel(){

  }

  public void createSimpleView(String str, JPanel contentPanel, JTextField dateInput,
      JButton valueBtn, JButton costBasisBtn, JButton compositionBtn, JLabel confirmationMsg,
      JLabel valueMsg){
//    contentPanel = new JPanel();
    contentPanel.setLayout(new BorderLayout());
    contentPanel.setBackground(Color.decode("#B8DEFF"));

    JPanel datePanel = new JPanel();
    datePanel.setLayout(new FlowLayout());
    datePanel.setBackground(Color.decode("#B8DEFF"));

    JLabel date = new JLabel("Date (yyyy-MM-dd) :");
    date.setFont(new Font("Arial", Font.PLAIN, 13));
    date.setHorizontalAlignment(JLabel.CENTER);
    datePanel.add(date);

    dateInput.setColumns(10);
    dateInput.setFont(new Font("Arial", Font.PLAIN, 13));
    dateInput.setSize(190, 20);
    dateInput.setHorizontalAlignment(JLabel.CENTER);
    datePanel.add(dateInput);

    switch (str){
      case "value" :
        datePanel.add(valueBtn);
        break;
      case "costBasis" :
        datePanel.add(costBasisBtn);
        break;
      case "composition" :
        datePanel.add(compositionBtn);
        break;
      default:
        return;
    }

    contentPanel.add(datePanel,BorderLayout.NORTH);

    JPanel message = new JPanel();

    confirmationMsg.setText("");
    confirmationMsg.setFont(new Font("Arial", Font.PLAIN, 25));
    confirmationMsg.setHorizontalAlignment(JLabel.CENTER);

    valueMsg.setText("");
    valueMsg.setFont(new Font("Arial", Font.ITALIC, 25));
    valueMsg.setHorizontalAlignment(JLabel.CENTER);

    message.add(confirmationMsg);
    message.add(valueMsg);
    message.setSize(250,20);

    message.setBackground(Color.decode("#B8DEFF"));
    contentPanel.add(message,BorderLayout.CENTER);
  }

}
