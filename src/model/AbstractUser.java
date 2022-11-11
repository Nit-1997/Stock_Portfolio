package model;

import constants.Constants;
import java.util.Set;
import utils.Utils;

public abstract class AbstractUser implements User {

  @Override
  public boolean dateChecker(String dateStr) {
    return Utils.dateChecker(dateStr);
  }

  @Override
  public Set<String> getStockList() {
    return Constants.STOCK_NAMES;
  }

  @Override
  public boolean isValidStock(String name) {
    return Constants.STOCK_NAMES.contains(name);
  }

  @Override
  public void cleanStockDirectory() {
    Utils.clearStockDirectory();
  }
}
