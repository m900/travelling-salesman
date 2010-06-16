package org.hm.dataStructures;

public class City {

  /**
   * The city's x position.
   */
  private double xpos;

  /**
   * The city's y position.
   */
  private double ypos;

  /**
   * Constructor.
   *
   * @param x The city's x position
   * @param y The city's y position.
   */
  public City(double x, double y) {
    xpos = x;
    ypos = y;
  }

  public double getx() {
    return xpos;
  }

  public double gety() {
    return ypos;
  }

  /**
   * Returns how close the city is to another city.
   *
   * @param cother The other city.
   * @return A distance.
   */
  public double proximity(City cother) {
    return proximity(cother.getx(),cother.gety());
  } 

  /**
   * Returns how far this city is from a a specific point.
   * This method uses the Pythagorean theorem to calculate
   * the distance.
   *
   * @param x The x coordinate
   * @param y The y coordinate
   * @return The distance.
   */
  public double proximity(double x, double y) {
    double xdiff = xpos - x;
    double ydiff = ypos - y;
    return(int)Math.sqrt( xdiff*xdiff + ydiff*ydiff );
  }
}
