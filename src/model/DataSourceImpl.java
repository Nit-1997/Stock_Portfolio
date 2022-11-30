package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class DataSourceImpl implements DataSource {

  @Override
  public File getFileByName(String fileName, String dirName) throws IOException {
    if (fileName == null || dirName == null) {
      throw new IOException("arguments passed are null");
    }
    String fileDirectory = Paths.get(dirName).toAbsolutePath().toString();

    File[] files = new File(fileDirectory).listFiles((f1, name) -> name.equals(fileName + ".csv"));
    if (files == null) {
      throw new IOException("Cannot find the directory");
    } else {
      if (files.length == 0) {
        throw new IOException("File does not exist");
      } else {
        return files[0];
      }
    }
  }

  @Override
  public void saveToFile(String name, List<StockOrder> orders, String dirName)
          throws IOException {
    if (name == null || orders == null) {
      throw new IOException("passed null args");
    }
    File portfolioFile = createFileIfNotExists(name, dirName);
    if (dirName.endsWith("flex")) {
      writePortfolioToFileFlex(portfolioFile, orders);
    } else {
      writePortfolioToFile(portfolioFile, orders);
    }
  }

  @Override
  public File createFileIfNotExists(String name, String dirName) throws IOException {
    String path = getFilePath(name, dirName);
    return new File(path);
  }

  private static String getFilePath(String name, String dirName) throws IOException {
    if (name == null || dirName == null) {
      throw new IOException("passed null args");
    }
    String path = Paths.get(dirName).toAbsolutePath() + File.separator + name + ".csv";
    return path;
  }


  private void writePortfolioToFile(File portfolioFile, List<StockOrder> orders)
          throws IOException {
    if (portfolioFile == null || orders == null) {
      throw new IOException("passed null args");
    }
    StringBuilder sb = new StringBuilder();
    for (StockOrder order : orders) {
      sb.append("")
              .append(order.getStock().getStockTickerName())
              .append(",")
              .append(order.getStock().getBuyPrice())
              .append(",")
              .append(order.getQuantity())
              .append(",")
              .append(order.getStock().getBuyDate())
              .append("\n");
    }
    this.writeToFile(portfolioFile,sb.toString());
  }

  private void writePortfolioToFileFlex(File portfolioFile, List<StockOrder> orders)
          throws IOException {
    if (portfolioFile == null || orders == null) {
      throw new IOException("passed null args");
    }
    StringBuilder sb = new StringBuilder();
    for (StockOrder order : orders) {

      sb.append("")
              .append(order.getStock().getStockTickerName())
              .append(",")
              .append(order.getStock().getBuyPrice())
              .append(",")
              .append(order.getQuantity())
              .append(",")
              .append(order.getStock().getBuyDate())
              .append(",")
              .append(order.getCommFee())
              .append("\n");
    }
    this.writeToFile(portfolioFile,sb.toString());
  }


  @Override
  public void writeToFile(File stockFile, String data) throws IOException {
    FileWriter myWriter = new FileWriter(stockFile);
    myWriter.write(data);
    myWriter.close();
  }
}
