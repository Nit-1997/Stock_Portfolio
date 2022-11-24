package view.panels;

import java.awt.event.ActionListener;
import javax.swing.JPanel;

public interface IPanel {

  void addActionListener(ActionListener listener);

  JPanel getJPanel();


}
