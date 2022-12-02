package view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Main menu panel.
 */
public class MainMenuPanel extends JPanel implements IPanel {


  public JButton goToCreatePortfolio;
  public JButton goToListAllPortfolio;
  public JButton exit;

  /**
   * Main menu panel constructor.
   */
  public MainMenuPanel() {
    this.goToCreatePortfolio = new JButton("Create new portfolio");
    this.goToCreatePortfolio.setActionCommand("Create Portfolio");
    this.goToListAllPortfolio = new JButton("List all portfolios");
    this.goToListAllPortfolio.setActionCommand("View Portfolios");
    this.exit = new JButton("Exit");
    this.exit.setActionCommand("Exit Button");

    this.setLayout(new BorderLayout());
    this.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
    JLabel header = new JLabel("Welcome to Stock Application");
    header.setHorizontalAlignment(JLabel.CENTER);
    header.setVerticalAlignment(JLabel.CENTER);
    header.setFont(new Font("Serif", Font.BOLD, 30));
    header.setVerticalAlignment(JLabel.CENTER);

    this.add(header, BorderLayout.NORTH);

    JPanel buttonPanel = new JPanel();
    this.add(buttonPanel, BorderLayout.CENTER);

    buttonPanel.setLayout(new FlowLayout());
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
    buttonPanel.setBackground(Color.decode("#e1d3ed"));

    buttonPanel.add(this.goToCreatePortfolio);
    buttonPanel.add(this.goToListAllPortfolio);
    buttonPanel.add(this.exit);

    this.add(buttonPanel);

    this.setBackground(Color.decode("#e1d3ed"));
  }

  @Override
  public JPanel getJPanel() {
    return this;
  }


  @Override
  public void addActionListener(ActionListener listener) {
    this.goToCreatePortfolio.addActionListener(listener);
    this.goToListAllPortfolio.addActionListener(listener);
    this.exit.addActionListener(listener);
  }

}
