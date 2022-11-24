package view.panels;

import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ListAllPortfoliosPanel extends JPanel implements IPanel{

  public ListAllPortfoliosPanel(){
    this.setBackground(Color.GREEN);
    this.add(new JLabel("List all portfolios"));
  }

  @Override
  public JPanel getJPanel(){
    return this;
  }

  @Override
  public void addActionListener(ActionListener listener) {

  }
}
