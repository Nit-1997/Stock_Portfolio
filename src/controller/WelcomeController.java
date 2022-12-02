package controller;

import java.util.Scanner;
import model.UserFlexInvestImpl;
import view.panels.MainFrameGUIView;
import view.TextViewImpl;
import view.ViewText;

/**
 * Starting parent controller for the application.
 */
public class WelcomeController implements StockController {

  final ViewText view;

  /**
   * Constructor for the controller.
   */
  public WelcomeController() {
    view = new TextViewImpl();
  }

  @Override
  public void start() {

    this.view.printStarterMenu(System.out);

    Scanner scan;
    String choice;

    StockController controller = null;

    do {
      scan = new Scanner(System.in);
      choice = scan.nextLine();
      if (choice.equals("1")) {
        controller = new TextController(System.in, System.out, this.view);
      } else if (choice.equals("2")) {
        controller = new MainGUIController(new MainFrameGUIView(), new UserFlexInvestImpl());
      } else {
        System.out.print("Kindly enter again your choice : ");
      }
    }
    while (!choice.equals("1") && !choice.equals("2"));

    controller.start();

  }


}
