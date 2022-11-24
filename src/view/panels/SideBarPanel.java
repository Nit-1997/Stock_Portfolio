package view.panels;

import java.awt.Color;
import java.awt.GridLayout;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;


public class SideBarPanel extends JPanel implements IPanel{

  JButton goToCreatePortfolio;
  JButton goToListAllPortfolio;
  JButton goToMainMenu;
  JButton exit;

  public SideBarPanel(){
    this.goToCreatePortfolio = new JButton("Create new portfolio");
    this.goToCreatePortfolio.setActionCommand("Create Portfolio");
    this.goToListAllPortfolio = new JButton("List all portfolios");
    this.goToListAllPortfolio.setActionCommand("View Portfolios");
    this.goToMainMenu = new JButton("Return to main menu");
    this.goToMainMenu.setActionCommand("Main Menu");
    this.exit = new JButton("Exit");
    this.exit.setActionCommand("Exit Button");

    this.setLayout(new GridLayout(7, 1));
    this.setBackground(Color.pink);

    this.goToCreatePortfolio.setHorizontalAlignment(JButton.CENTER);
    this.add(goToCreatePortfolio);


    this.goToListAllPortfolio.setHorizontalAlignment(JButton.CENTER);
    this.add(goToListAllPortfolio);


    this.goToMainMenu.setHorizontalAlignment(JButton.CENTER);
    this.add(goToMainMenu);


    this.exit.setHorizontalAlignment(JButton.CENTER);
    this.add(exit);
  }

  @Override
  public JPanel getJPanel(){
    return this;
  }

  @Override
  public void addActionListener(ActionListener listener) {
    this.goToCreatePortfolio.addActionListener(listener);
    this.goToListAllPortfolio.addActionListener(listener);
    this.goToMainMenu.addActionListener(listener);
    this.exit.addActionListener(listener);
  }

}
