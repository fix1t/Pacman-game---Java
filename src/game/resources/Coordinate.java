package src.game.resources;

import java.util.Objects;

/**
 * Represents a coordinate in a two-dimensional space.
 * @author Gabriel Biel
 */
public class Coordinate {
  private int x;
  private int y;

  /**
   * Constructs a Coordinate object with the specified x and y values.
   *
   * @param x the x-coordinate value
   * @param y the y-coordinate value
   */
  public Coordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Retrieves the x-coordinate value.
   *
   * @return the x-coordinate value
   */
  public int getX() {
    return x;
  }

  /**
   * Retrieves the y-coordinate value.
   *
   * @return the y-coordinate value
   */
  public int getY() {
    return y;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Coordinate that = (Coordinate) obj;
    return x == that.x && y == that.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
