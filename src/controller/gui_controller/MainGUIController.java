package controller.gui_controller;


import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.UserFlexInvest;
import view.ViewGUI;
import view.panels.CreatePortfolioPanel;
import view.panels.ListAllPortfoliosPanel;
import view.panels.MainMenuPanel;

public class MainGUIController {

  ViewGUI view;
  UserFlexInvest user;

  public MainGUIController(ViewGUI view, UserFlexInvest user){
    this.view=view;
    this.user=user;
    configureButtonListener();
  }

  private void configureButtonListener() {
    Map<String,Runnable> buttonClickedMap = new HashMap<>();
    ButtonListener buttonListener = new ButtonListener();

    buttonClickedMap.put("Create Portfolio",()->this.view.changePanel(new CreatePortfolioPanel()));
    buttonClickedMap.put("View Portfolios",()->this.view.changePanel(new ListAllPortfoliosPanel(user.getPortfolios())));
    buttonClickedMap.put("Main Menu",()->this.view.changePanel(new MainMenuPanel()));
    buttonClickedMap.put("Exit Button",()->System.exit(0));
    buttonClickedMap.put("Normal Portfolio Creation Submit",()->{
      String str = new NormalPortfolioCreationSubmit(this.view.getNormalPortfolioCreationData(),user).execute();
      this.view.printForNormalPortfolioCreation(str);
    });
    buttonClickedMap.put("Normal Form Add More Button",()->{
      String str = new NormalPortfolioCreationAddMore(this.view.getNormalPortfolioCreationFormDataAddMore(),user).execute();
      this.view.printForNormalPortfolioCreation(str);
    });
    buttonClickedMap.put("DCA Form Submit",()->{
      String str = new DCAPortfolioCreation(this.view.getDCAPortfolioCreationData(), this.view.getDCAPortfolioCreationMap(),user).execute();
      this.view.printForDCAPortfolioCreation(str);
    });
    buttonClickedMap.put("Get Value",()->{
      SimpleEntry<String,String> nameAndDate = this.view.getNameAndDate();
      String str = new PortfolioValue(nameAndDate.getKey(), nameAndDate.getValue(),user).execute();
      Double value=null;
      if(str.contains("Success")){
        String msg=str.split(",")[0];
        value=Double.parseDouble(str.split(",")[1]);
        str=msg;
      }
      this.view.setValue(str,value);
    });

    buttonClickedMap.put("Get CostBasis",()->{
      SimpleEntry<String,String> nameAndDate = this.view.getNameAndDate();
      String str = new PortfolioCostBasis(nameAndDate.getKey(), nameAndDate.getValue(),user).execute();
      Double value=null;
      if(str.contains("Success2")){
        String msg=str.split(",")[0];
        value=Double.parseDouble(str.split(",")[1]);
        str=msg;
      }
      this.view.setValue(str,value);
    });

    buttonClickedMap.put("Get Composition",()->{
      SimpleEntry<String,String> nameAndDate = this.view.getNameAndDate();
      PortfolioComposition obj = new PortfolioComposition(nameAndDate.getKey(), nameAndDate.getValue(),user);
      String str = obj.execute();
      Map<String, Double> stockMap = obj.getStockMap();
      this.view.setValue(str,null);
      if(str.equals("Success3"))this.view.setStockMap(stockMap);
    });

    buttonClickedMap.put("BuyStock Menu", ()->{
      SimpleEntry<String,String> nameAndDate = this.view.getNameAndDate();
      try {
        this.view.setPortfolioCreationDate(user.getPortfolioCreationDate(nameAndDate.getKey()));
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    });

    buttonClickedMap.put("BuyStock",()->{
      List<String> buySellData = this.view.getBuySellData();
      String str = new BuyStock(buySellData,user).execute();
      this.view.setBuySellMsg(str);
    });

    buttonClickedMap.put("Get Composition for Sell",()->{
      SimpleEntry<String,String> nameAndDate = this.view.getNameAndDate();
      PortfolioComposition obj = new PortfolioComposition(nameAndDate.getKey(), nameAndDate.getValue(),user);
      String str = obj.execute();
      Map<String, Double> stockMap = obj.getStockMap();
      this.view.setSellInterimMessage(str);
      if(str.equals("Success3"))this.view.setStockMap(stockMap);
    });

    buttonClickedMap.put("SellStock",()->{
      List<String> buySellData = this.view.getBuySellData();
      Map<String, Double> stockMap = this.view.getStockMap();
      String str = new SellStock(buySellData,stockMap,user).execute();
      this.view.setBuySellMsg(str);
    });


    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.view.addActionListener(buttonListener);
  }



}
