package view;

import java.awt.event.ActionListener;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import view.panels.IPanel;

public interface ViewGUI {

  void addActionListener(ActionListener listener);


  void changePanel(IPanel panel);

  List<String> getNormalPortfolioCreationData();

  void printForNormalPortfolioCreation(String str);

  List<String> getNormalPortfolioCreationFormDataAddMore();

}
