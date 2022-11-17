package controller;

/**
 * Interface for each flex or inflex portfolio model.
 *
 * @param <T> Type of the model object required for controller's implementation.
 */
public interface CategoryControllerInterface<T> {

  /**
   * Starter function for controller.
   *
   * @param user
   */
  void start(T user);
}
