package view;

import constants.Constants;
import java.io.PrintStream;

public class WelcomePrint {

  public static void welcomeNote(PrintStream out){
    out.println(Constants.WelcomeMessage);
  }

  public static void printMenu(PrintStream out){
    out.print(Constants.MenuMessage);
  }

  public static void errorNote(PrintStream out){
    out.print(Constants.ErrorNote);
  }
}
