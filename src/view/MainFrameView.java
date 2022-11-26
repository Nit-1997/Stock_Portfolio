package view;


import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import view.panels.CreatePortfolioPanel;
import view.panels.IPanel;
import view.panels.MainMenuPanel;
import view.panels.SideBarPanel;
import view.panels.SinglePortfolioDetailPanel;

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
  public List<String> getDCAPortfolioCreationData(){
    CreatePortfolioPanel obj = (CreatePortfolioPanel)this.mainPanel;
    return obj.getDCAFormData();
  }

  @Override
  public Map<String, Double> getDCAPortfolioCreationMap(){
    CreatePortfolioPanel obj = (CreatePortfolioPanel)this.mainPanel;
    return obj.getDcaStockMap();
  }

  @Override
  public void printForNormalPortfolioCreation(String str) {
    CreatePortfolioPanel obj = (CreatePortfolioPanel)this.mainPanel;
    obj.printForNormalPortfolioCreation(str);
  }

  @Override
  public void printForDCAPortfolioCreation(String str){
    CreatePortfolioPanel obj = (CreatePortfolioPanel)this.mainPanel;
    obj.printForDCAPortfolioCreation(str,str.equals("Portfolio Successfully Saved"));
  }

  @Override
  public SimpleEntry<String,String> getNameAndDate(){
    return ((SinglePortfolioDetailPanel)this.mainPanel).getNameAndDate();
  }

  @Override
  public void setValue(String type, Double value){
    ((SinglePortfolioDetailPanel)this.mainPanel).setValue(type, value);
  }

  @Override
  public void setPortfolioCreationDate(String date){
    ((SinglePortfolioDetailPanel)this.mainPanel).setPortfolioCreationDate(date);
  }

  @Override
  public void setStockMap(Map<String, Double> stockMap){
    ((SinglePortfolioDetailPanel)this.mainPanel).setStockMap(stockMap);
  }

  @Override
  public List<String> getBuySellData(){
    return ((SinglePortfolioDetailPanel)this.mainPanel).getBuySellData();
  }

  @Override
  public void setBuySellMsg(String msg){
    ((SinglePortfolioDetailPanel)this.mainPanel).setBuySellMessage(msg);
  }

  @Override
  public void setSellInterimMessage(String msg){
    ((SinglePortfolioDetailPanel)this.mainPanel).setSellInterimMessage(msg);
  }

  @Override
  public Map<String, Double> getStockMap(){
    return ((SinglePortfolioDetailPanel)this.mainPanel).getStockMap();
  }

  @Override
  public void addActionListener(ActionListener listener) {
    this.listener=listener;
    this.mainPanel.addActionListener(listener);
    this.sideBar.addActionListener(listener);
  }



}
