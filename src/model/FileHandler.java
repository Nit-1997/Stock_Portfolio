package model;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * Interface for dealing with files.
 */
public interface FileHandler {

  /**
   * Read a file.
   *
   * @param fileName Name of the file, will be looking in data folder.
   * @return String contents of the file.
   * @throws FileNotFoundException If the file is not found.
   */
  String readFile(String fileName) throws FileNotFoundException;


  /**
   * Load all data from a given folder.
   *
   * @param folderName Name of folder to look in for files.
   * @return Map of file name to contents.
   * @throws IllegalArgumentException If the folder is not found.
   */
  Map<String, String> loadData(String folderName) throws FileNotFoundException;

  /**
   * Write a string to a file.
   *
   * @param contents      String of contents to be written.
   * @param fileExtension Extension to use.
   * @return True if success, false if fail.
   */
  Boolean writeFile(String filename, String contents, String fileExtension);
}
