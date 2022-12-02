package view.panels;

import java.util.List;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Graph panel.
 */
public class GraphPanel extends JFrame {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor for graph panel.
   *
   * @param title      name of the graph.
   * @param labels     x-axis labels.
   * @param dataPoints points to be plotted.
   */
  public GraphPanel(String title, List<String> labels, List<Double> dataPoints) {
    super(title);

    this.setAlwaysOnTop(true);
    this.pack();
    this.setSize(600, 400);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.setVisible(true);

    DefaultCategoryDataset dataset = createDataset(labels, dataPoints);

    JFreeChart chart = ChartFactory.createLineChart("Portfolio Performance",
        "Date", "Value of Portfolio($)", dataset,
        PlotOrientation.VERTICAL, true, true, false);
    chart.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);

    ChartPanel panel = new ChartPanel(chart);
    setContentPane(panel);
  }

  private DefaultCategoryDataset createDataset(List<String> labels, List<Double> dataPoints) {

    String series1 = "Performance";
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    for (int i = 0; i < labels.size(); i++) {
      dataset.addValue(dataPoints.get(i), series1, labels.get(i));
    }

    return dataset;
  }
}  