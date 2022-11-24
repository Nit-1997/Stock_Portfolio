package controller.gui_controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.UserFlexInvest;
import view.ViewGUI;
import view.panels.CreatePortfolioPanel;
import view.panels.IPanel;
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
    buttonClickedMap.put("View Portfolios",()->this.view.changePanel(new ListAllPortfoliosPanel()));
    buttonClickedMap.put("Main Menu",()->this.view.changePanel(new MainMenuPanel()));
    buttonClickedMap.put("Exit Button",()->System.exit(0));
    buttonClickedMap.put("Normal Portfolio Creation Submit",()->{
      System.out.println("here1");
      String str = new NormalPortfolioCreationSubmit(this.view.getNormalPortfolioCreationData(),user).execute();
      this.view.printForNormalPortfolioCreation(str);
    });
    buttonClickedMap.put("Normal Form Add More Button",()->{

      System.out.println("hello");
    });

    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.view.addActionListener(buttonListener);
  }



}
