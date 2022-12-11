package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Implementing the FileHandler to read JSON files for stocks.
 */
public class StockIO implements FileHandler {

  @Override
  public String readFile(String fileName) throws FileNotFoundException {
    StringBuilder output = new StringBuilder();
    File file = new File(fileName);
    Scanner scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      output.append(scanner.nextLine());
      if (scanner.hasNextLine()) {
        output.append(System.lineSeparator());
      }
    }
    return output.toString();
  }

  @Override
  public Map<String, String> loadData(String folderName) throws FileNotFoundException {
    Map<String, String> files = new HashMap<>();
    File folder = new File(folderName);
    for (File file : folder.listFiles()) {
      if (file.getName().contains(".JSON")) {
        files.put(file.getName(), readFile(folderName + "/" + file.getName()));
      }
    }
    return files;
  }

  @Override
  public Boolean writeFile(String filename, String contents, String fileExtension) {
    try {
      Files.writeString(Paths.get(filename + fileExtension), contents);
    } catch (IOException e) {
      return false;
    }
    return true;
  }
}
