package view;


import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFrame;
import view.panels.CreatePortfolioPanel;
import view.panels.IPanel;
import view.panels.MainMenuPanel;
import view.panels.SideBarPanel;

public class MainFrameView extends JFrame implements ViewGUI{

  SideBarPanel sideBar;

  IPanel mainPanel;

  ActionListener listener;



  public MainFrameView(){
    super();


    this.setTitle("Stonks!");
    this.setSize(800,400);
    this.setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());

    this.sideBar = new SideBarPanel();
    this.add(this.sideBar,BorderLayout.WEST);

    this.mainPanel = new MainMenuPanel();
    this.add(this.mainPanel.getJPanel(),BorderLayout.CENTER);

    this.setVisible(true);
  }

  @Override
  public void changePanel(IPanel panel){
    this.remove(this.mainPanel.getJPanel());
    this.mainPanel=panel;
    this.mainPanel.addActionListener(this.listener);
    this.add(panel.getJPanel(),BorderLayout.CENTER);
    this.validate();
    this.setVisible(true);
  }

  @Override
  public List<String> getNormalPortfolioCreationData() {
    CreatePortfolioPanel obj = (CreatePortfolioPanel)this.mainPanel;
    List<String> data = obj.getNormalFormData();
    data.add(obj.getAreaText());
    return data;
  }

  @Override
  public List<String> getNormalPortfolioCreationFormDataAddMore(){
    CreatePortfolioPanel obj = (CreatePortfolioPanel)this.mainPanel;
    return obj.getNormalFormData();
  }

  @Override
  public void printForNormalPortfolioCreation(String str) {
    CreatePortfolioPanel obj = (CreatePortfolioPanel)this.mainPanel;
    obj.printForNormalPortfolioCreation(str);
  }

  @Override
  public void addActionListener(ActionListener listener) {
    this.listener=listener;
    this.mainPanel.addActionListener(listener);
    this.sideBar.addActionListener(listener);
  }



}
