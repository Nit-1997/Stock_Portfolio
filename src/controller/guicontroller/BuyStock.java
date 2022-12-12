package controller.guicontroller;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import model.UserFlexInvest;

/**
 * Buy stock class.
 */
public class BuyStock implements GUISubController {

  List<String> buySellData;
  UserFlexInvest user;


  /**
   * Constructor for buy stock.
   *
   * @param buySellData buy sell data.
   * @param user        model object.
   */
  public BuyStock(List<String> buySellData, UserFlexInvest user) {
    this.buySellData = buySellData;
    this.user = user;
  }

  @Override
  public String execute() {

    if (!FormChecker.formChecker(buySellData, user).equals("Portfolio Successfully Saved")) {
      return FormChecker.formChecker(buySellData, user);
    }

    String portfolioName = buySellData.get(0);

    String stock = buySellData.get(1).toUpperCase();

    String quantity = buySellData.get(2);
    double quanDouble = Double.parseDouble(quantity);

    String date = buySellData.get(3);

    String commFee = buySellData.get(4);
    Double commFeeDouble = Double.parseDouble(commFee);

    try {
      if (user.isBeforeDate(date, user.getPortfolioCreationDate(portfolioName))) {
        return "Given date before portfolio creation";
      }
    } catch (Exception e) {
      return e.getMessage();
    }

    try {
      boolean val = user.transactionForPortfolio(
          portfolioName, new SimpleEntry<>(stock, new SimpleEntry<>(date,
              new SimpleEntry<>(quanDouble, commFeeDouble))));
    } catch (Exception e) {
      return e.getMessage();
    }

    return "Successful transaction";
  }

}
