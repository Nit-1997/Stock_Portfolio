import java.io.IOException;

import control.gui.Controller;
import control.terminal.StockController;
import control.terminal.TerminalController;
import model.Model;
import model.ModelImpl;
import view.gui.IView;
import view.gui.JFrameView;
import view.terminal.StockView;
import view.terminal.StockViewer;

/**
 * Class that runs the stock program.
 */
public class StockRunner {

  /**
   * Main class.
   *
   * @param args CLI arguments.
   */
  public static void main(String[] args) throws IOException {
    // If we get arguments to use the gui, do so.
    if (args.length > 0) {
      for (String arg : args) {
        if (arg.equals("GUI")) {
          Model model = new ModelImpl();
          IView view = new JFrameView();
          Controller controller = new Controller(model);
          controller.setView(view);
        } else {
          System.out.print("Argument not recognized: " + arg);
          System.exit(1);
        }
      }
    } else { // Default to CLI output
      Model model = new ModelImpl();
      StockView view = new StockViewer(System.out);
      StockController controller = new TerminalController(model, System.in, view);
      controller.gogogadget();
    }
  }
}
