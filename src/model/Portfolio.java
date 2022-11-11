package model;

import java.io.IOException;
import java.util.List;

/**
 * Interface to specify Portfolio specs.
 * spec includes the name ,{stock , qty} map while creating the portfolio,
 * or just name while fetching already created portfolio
 */
public interface Portfolio {

  /**
   * Gets the current Price of the entire Portfolio.
   *
   * @return currentPrice of Portfolio
   * @throws IOException if asked date is invalid or no data for this date.
   */
  public Double getCurrentValue() throws Exception;


  /**
   * Fetches the value of the Portfolio for a particular date.
   *
   * @param date for which portfolio value needs to be fetched.
   * @return total Initial Buy value.
   * @throws IOException if there is no value on this date or date is invalid.
   */
  public Double getValueOnDate(String date) throws Exception;

}
