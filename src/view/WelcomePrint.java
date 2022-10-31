package view;

import constants.Constants;
import constants.ViewConstants;
import java.io.PrintStream;

public class WelcomePrint {

  public static void welcomeNote(PrintStream out){
    out.println(ViewConstants.WelcomeMessage);
  }

  public static void printMenu(PrintStream out){
    out.print(ViewConstants.MenuMessage);
  }

  public static void errorNote(PrintStream out){
    out.print(ViewConstants.ErrorNote);
  }
}
