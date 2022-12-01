package view.panels;

import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 * Interface for all the panels.
 */
public interface IPanel {

  /**
   * Adds action listeners to the buttons.
   *
   * @param listener action listener.
   */
  void addActionListener(ActionListener listener);

  /**
   * Gets the JPanel.
   *
   * @return JPanel.
   */
  JPanel getJPanel();


}
