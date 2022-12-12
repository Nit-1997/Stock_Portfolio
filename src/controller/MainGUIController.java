package controller;


import controller.guicontroller.ButtonListener;
import controller.guicontroller.BuyStock;
import controller.guicontroller.DCAPortfolio;
import controller.guicontroller.FormChecker;
import controller.guicontroller.GUISubController;
import controller.guicontroller.GraphData;
import controller.guicontroller.InvestStock;
import controller.guicontroller.NormalPortfolioCreationSubmit;
import controller.guicontroller.PortfolioComposition;
import controller.guicontroller.PortfolioCostBasis;
import controller.guicontroller.PortfolioValue;
import controller.guicontroller.SellStock;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.UserFlexInvest;
import view.ViewGUI;
import view.panels.CreatePortfolioPanel;
import view.panels.ListAllPortfoliosPanel;
import view.panels.MainMenuPanel;

/**
 * Main Controller for GUI.
 */
public class MainGUIController implements StockController {

  ViewGUI view;
  UserFlexInvest user;

  /**
   * Constructor for GUI controller.
   *
   * @param view view object.
   * @param user model object.
   */
  public MainGUIController(ViewGUI view, UserFlexInvest user) {
    this.view = view;
    this.user = user;
  }

  private void configureButtonListener() {

    GUISubController subController;
    Map<String, Runnable> buttonClickedMap = new HashMap<>();
    ButtonListener buttonListener = new ButtonListener();

    buttonClickedMap.put("Create Portfolio",
        () -> this.view.changePanel(new CreatePortfolioPanel()));
    buttonClickedMap.put("View Portfolios",
        () -> this.view.changePanel(new ListAllPortfoliosPanel(user.getPortfolios())));
    buttonClickedMap.put("Main Menu", () -> this.view.changePanel(new MainMenuPanel()));
    buttonClickedMap.put("Exit Button", () -> System.exit(0));

    buttonClickedMap.put("Normal Portfolio Creation Submit", () -> {
      String str = new NormalPortfolioCreationSubmit(this.view.getNormalPortfolioCreationData(),
          user).execute();
      this.view.printForNormalPortfolioCreation(str);
    });
    buttonClickedMap.put("Normal Form Add More Button", () -> {
      String str = FormChecker.formChecker(this.view.getNormalPortfolioCreationFormDataAddMore(),
          user);
      if (str.equals("Portfolio Successfully Saved")) {
        str = "Stock Added successfully";
      }
      this.view.printForNormalPortfolioCreation(str);
    });
    buttonClickedMap.put("DCA Form Submit", () -> {

      String str = new DCAPortfolio(this.view.getDCAPortfolioCreationData(),
          this.view.getDCAPortfolioCreationMap(), user, "create").execute();
      this.view.printForDCAPortfolioCreation(str);
    });
    buttonClickedMap.put("Get Value", () -> {
      SimpleEntry<String, String> nameAndDate = this.view.getNameAndDate();
      String str = new PortfolioValue(nameAndDate.getKey(), nameAndDate.getValue(), user).execute();
      Double value = null;
      if (str.contains("Success")) {
        String msg = str.split(",")[0];
        value = Double.parseDouble(str.split(",")[1]);
        str = msg;
      }
      this.view.setValue(str, value);
    });

    buttonClickedMap.put("Get CostBasis", () -> {
      SimpleEntry<String, String> nameAndDate = this.view.getNameAndDate();
      String str = new PortfolioCostBasis(nameAndDate.getKey(), nameAndDate.getValue(),
          user).execute();
      Double value = null;
      if (str.contains("Success2")) {
        String msg = str.split(",")[0];
        value = Double.parseDouble(str.split(",")[1]);
        str = msg;
      }
      this.view.setValue(str, value);
    });

    buttonClickedMap.put("Get Composition", () -> {
      SimpleEntry<String, String> nameAndDate = this.view.getNameAndDate();
      GUISubController obj = new PortfolioComposition(nameAndDate.getKey(), nameAndDate.getValue(),
          user);
      String str = obj.execute();
      Map<String, Double> stockMap = ((PortfolioComposition) obj).getStockMap();
      this.view.setValue(str, null);
      if (str.equals("Success3")) {
        this.view.setStockMap(stockMap);
      }
    });

    buttonClickedMap.put("BuyStock Menu", () -> {
      SimpleEntry<String, String> nameAndDate = this.view.getNameAndDate();
      try {
        this.view.setPortfolioCreationDate(user.getPortfolioCreationDate(nameAndDate.getKey()));
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    });

    buttonClickedMap.put("BuyStock", () -> {
      List<String> buySellData = this.view.getBuySellData();
      String str = new BuyStock(buySellData, user).execute();
      this.view.setBuySellMsg(str);
    });

    buttonClickedMap.put("Get Composition for Sell", () -> {
      SimpleEntry<String, String> nameAndDate = this.view.getNameAndDate();
      GUISubController obj = new PortfolioComposition(nameAndDate.getKey(), nameAndDate.getValue(),
          user);
      String str = obj.execute();
      Map<String, Double> stockMap = ((PortfolioComposition) obj).getStockMap();
      this.view.setSellInterimMessage(str);
      if (str.equals("Success3")) {
        this.view.setStockMap(stockMap);
      }
    });

    buttonClickedMap.put("SellStock", () -> {
      List<String> buySellData = this.view.getBuySellData();
      Map<String, Double> stockMap = this.view.getStockMap();
      String str = new SellStock(buySellData, stockMap, user).execute();
      this.view.setBuySellMsg(str);
    });

    buttonClickedMap.put("InvestStock", () -> {
      List<String> investData = this.view.getInvestData();
      Map<String, Double> stockMap = this.view.getInvestStockMap();
      String str = new InvestStock(investData, stockMap, user).execute();
      this.view.setInvestMsg(str);
    });

    buttonClickedMap.put("Graph Data", () -> {
      List<String> investData = this.view.getGraphData();
      GUISubController obj = new GraphData(investData.get(0), investData.get(1), investData.get(2),
          user);
      String str = obj.execute();
      this.view.setGraphMsg(str);
      if (str.equals("success")) {
        this.view.startGraph(((GraphData) obj).data);
      }
    });

    buttonClickedMap.put("Invest DCA", () -> {
      String str = new DCAPortfolio(this.view.getDCAInvestmentData(),
          this.view.getInvestStockMap(), user, "exist").execute();
      this.view.setInvestMsg(str);
    });

    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.view.addActionListener(buttonListener);
  }


  @Override
  public void start() {
    configureButtonListener();
  }
}
