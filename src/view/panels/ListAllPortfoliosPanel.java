package view.panels;

import com.sun.tools.javac.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import view.MainFrameView;

public class ListAllPortfoliosPanel extends JPanel implements IPanel{

  Set<String> portfolios;
  public ListAllPortfoliosPanel(Set<String> portfolios){
    this.portfolios=portfolios;
    this.setBackground(Color.GREEN);
    this.setLayout(new BorderLayout());
    JLabel header = new JLabel("List all portfolios");
    header.setFont(new Font("Arial", Font.ITALIC, 20));
    header.setHorizontalAlignment(JLabel.CENTER);
    header.setBorder(BorderFactory.createBevelBorder(1));
    this.add(header,BorderLayout.PAGE_START);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.Y_AXIS));
    buttonPanel.setBackground(Color.decode("#C9FFB8"));

    buttonPanel.setForeground(Color.CYAN);
    JButton[] buttons = new JButton[this.portfolios.size()];
    int i=0;
    for(String portfolio : this.portfolios){
      buttons[i] = new JButton(portfolio);
      buttons[i].setHorizontalAlignment(JButton.CENTER);
      buttons[i].setSize(100,50);
      buttons[i].addActionListener(e -> this.router(portfolio));
      buttonPanel.add(buttons[i]);
      i++;
    }
    this.add(buttonPanel,BorderLayout.CENTER);
  }

  private void router(String name){
    MainFrameView obj = (MainFrameView) SwingUtilities.getWindowAncestor(this);
    obj.changePanel(new SinglePortfolioDetailPanel(name));
  }

  @Override
  public JPanel getJPanel(){
    return this;
  }

  @Override
  public void addActionListener(ActionListener listener) {

  }


}
