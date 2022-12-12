package controller.gui_controller;

import java.util.List;
import model.UserFlexInvest;

/**
 * Helper class to check the form data.
 */
public class FormChecker {

  /**
   * Check the data in the form.
   *
   * @param data list of {portfolioName, stock, quantity, date, commFee}
   * @param user Model Object
   * @return string message
   */
  public static String formChecker(List<String> data, UserFlexInvest user) {

    String portfolioName = data.get(0);
    if (portfolioName.equals("")) {
      return "Empty Portfolio Name";
    }

    String stock = data.get(1).toUpperCase();
    if (stock.equals("")) {
      return "Empty Stock";
    } else if (!user.isValidStock(stock)) {
      return "Please enter a valid stock name";
    }

    String date = data.get(3);
    if (date.equals("")) {
      return "Empty Date";
    }
    if (!user.dateChecker(date)) {
      return "Wrong date format";
    }

    String quantity = data.get(2);
    int quan;
    if (quantity.equals("")) {
      return "Empty Quantity";
    }
    try {
      quan = Integer.parseInt(quantity);
      if (quan < 0) {
        return "Negative quantity passed";
      }
      if (quan == 0) {
        return "Cant buy/sell 0 stocks";
      }
    } catch (NumberFormatException e) {
      return "Wrong Quantity Format";
    }

    String commFee = data.get(4);
    Double commFeeDouble;
    if (commFee.equals("")) {
      commFeeDouble = 0.0;
    }
    try {
      commFeeDouble = Double.parseDouble(commFee);
      if (commFeeDouble < 0) {
        return "Negative Commission Fee";
      }
    } catch (NumberFormatException e) {
      return "Wrong Commission Fee Format";
    }

    return "Portfolio Successfully Saved";
  }

}
