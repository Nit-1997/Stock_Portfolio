package view.gui;

import control.gui.Features;

/**
 * Interface for the view, outlining what the view can do.
 */
public interface IView {

  /**
   * Call the dialogue for a new portfolio.
   *
   * @param features Listeners for callback.
   * @return String portfolio name.
   */
  String setUpNewPort(Features features);

  /**
   * Dialogue box of text.
   *
   * @param output Text to display.
   */
  void showOutput(String output);

  /**
   * Switch the current panel.
   *
   * @param s Panel to switch to.
   */
  void switchPanel(String s);

  /**
   * Browse for file to load.
   */
  void gatherFile();

  /**
   * Add labels for the percentages and tickers.
   */
  void addPercentageInputs();

  /**
   * Add labels for the percentages and tickers in dollar average cost.
   */
  void addPercentageInputsCost();

  /**
   * Dialogue box of an error.
   *
   * @param error Error to display.
   */
  void showError(String error);

  /**
   * Checks the input.
   *
   * @param features Listeners to add.
   */
  void checkViewInputs(Features features);

  /**
   * Highlights red incorrect inputs.
   *
   * @param features Listeners for this.
   */
  void checkBuyInputs(Features features);

  /**
   * Highlights red incorrect inputs.
   *
   * @param features Listeners for this.
   */
  void checkSellInputs(Features features);

  /**
   * Add the features to the view.
   * Normally the controller will implement these.
   *
   * @param features Features to add.
   */
  void addFeatures(Features features);
}
