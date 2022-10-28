package view;

import constants.Constants;

public class WelcomePrint {

  public static void welcomeNote(){
    System.out.println(Constants.WelcomeMessage);
  }

  public static void printMenu(){
    System.out.print(Constants.MenuMessage);
  }

  public static void errorNote(){
    System.out.print(Constants.ErrorNote);
  }
}
