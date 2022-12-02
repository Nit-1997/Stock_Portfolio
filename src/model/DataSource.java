package model;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Data source interface to encapsulate all data fetch operations.
 * Contains all the methods to deal with data
 */
public interface DataSource {

  /**
   * Searches and Returns file inside a particular directory.
   *
   * @param fileName name of the file needs to be searched.
   * @param dirName  directory in which file needs to be searched.
   * @return File object of that found file.
   * @throws IOException if the directory doesn't exist or file not found.
   */
  public File getFileByName(String fileName, String dirName) throws IOException;

  /**
   * Saves the current file in the directory.
   *
   * @param name    name of the file to be saved.
   * @param orders  data that needs to be saved.
   * @param dirName folder in which file will be saved.
   * @throws IOException if directory doesn't exist or any argument exception.
   */
  public void saveToFile(String name, List<StockOrder> orders, String dirName)
          throws IOException;


  /**
   * Function to create a file if it does not exist.
   *
   * @param name    name of the file
   * @param dirName directory name
   * @return file
   * @throws IOException while reading/writing
   */
  public File createFileIfNotExists(String name, String dirName) throws IOException;


  /**
   * Function to write data dump to a file
   *
   * @param stockFile    file
   * @param data         data
   * @param isAppendable is file appendable
   * @throws IOException can occur during IO operations.
   */
  public void writeToFile(File stockFile, String data, boolean isAppendable) throws IOException;
}
