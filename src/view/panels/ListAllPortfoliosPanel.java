package view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import view.MainFrameGUIView;

/**
 * Listing all portfolios panel.
 */
public class ListAllPortfoliosPanel extends JPanel implements IPanel {

  Set<String> portfolios;

  /**
   * Constructor for listing all portfolios.
   * @param portfolios list of all portfolioNames.
   */
  public ListAllPortfoliosPanel(Set<String> portfolios) {
    this.portfolios = portfolios;
    this.setBackground(Color.decode("#7AFFA0"));
    this.setLayout(new BorderLayout());
    JLabel header = new JLabel("<html>List all portfolios<br />"
        + " Select any one to get specific menu</html>");
    header.setFont(new Font("Arial", Font.ITALIC, 20));
    header.setHorizontalAlignment(JLabel.CENTER);
    header.setBorder(BorderFactory.createBevelBorder(1));
    this.add(header, BorderLayout.PAGE_START);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(this.portfolios.size() / 2 + 1, 2));
    buttonPanel.setBackground(Color.decode("#C9FFB8"));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
    buttonPanel.setForeground(Color.CYAN);
    JButton[] buttons = new JButton[this.portfolios.size()];
    int i = 0;
    for (String portfolio : this.portfolios) {
      buttons[i] = new JButton(portfolio);
      buttons[i].setFont(new Font("Arial", Font.ITALIC, 20));
      buttons[i].setHorizontalAlignment(JButton.CENTER);
      buttons[i].setSize(200, 100);
      buttons[i].addActionListener(e -> this.router(portfolio));
      buttonPanel.add(buttons[i]);
      i++;
    }
    this.add(buttonPanel, BorderLayout.CENTER);
  }

  private void router(String name) {
    MainFrameGUIView obj = (MainFrameGUIView) SwingUtilities.getWindowAncestor(this);
    obj.changePanel(new SinglePortfolioDetailPanel(name));
  }

  @Override
  public JPanel getJPanel() {
    return this;
  }

  @Override
  public void addActionListener(ActionListener listener) {

  }


}
