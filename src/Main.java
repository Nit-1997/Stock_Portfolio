import controller.gui_controller.MainGUIController;
import javax.swing.text.View;
import model.PortfolioFlexInvestImpl;
import model.UserFlexInvest;
import model.UserFlexInvestImpl;
import view.MainFrameView;
import view.ViewGUI;


/**
 * This is the starter class that needs to be run to start the application.
 */
public class Main {

  /**
   * Main function of the program, starter function of the application.
   *
   * @param args standard arguments
   */
  public static void main(String[] args) throws Exception {
//    StockController controller = new WelcomeController(System.in, System.out);
//    controller.start();
//    UserFlexInvest user = new UserFlexInvestImpl();

//    ViewGUI obj = new MainFrameView();

    MainGUIController obj = new MainGUIController(new MainFrameView(),new UserFlexInvestImpl());

//    ViewGUI obj = new MainFrameView();
//    Map<String, Double> investMap = new HashMap<>();
//    investMap.put("MSFT",45.0);
//    investMap.put("AAPL",11.0);
//    investMap.put("GOOG",37.0);
//    investMap.put("NVDA",7.0);
//    user.addPortfolio("investor",2000.0,investMap,"2015-01-01","2015-03-16",30,4.5);

//    user.transactionForPortfolio("investor",new SimpleEntry<>("INTU",new SimpleEntry<>("2015-02-15",new SimpleEntry<>(8.0,2.8))));
//    user.transactionForPortfolio("investor",new SimpleEntry<>("GOOG",new SimpleEntry<>("2016-02-11",new SimpleEntry<>(-15.0,2.8))));
  }





}