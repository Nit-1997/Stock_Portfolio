package model;

import java.time.LocalDate;
import java.util.Map;

/**
 * MOdels a stock for the program.
 */
public interface Stock {

  /**
   * Get the ticker for this stock.
   *
   * @return String of the ticker.
   */
  String getTicker();

  /**
   * Get the value for this stock on a given date.
   *
   * @param date Date for the value (expecting YYY-MM-DD).
   * @return Map of value key to value. Ex: low : 95.234
   * @throws IllegalArgumentException If the data does not exist for that date.
   */
  Map<String, Double> getValue(LocalDate date) throws IllegalArgumentException;
}
