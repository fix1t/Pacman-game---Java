package src.game;

import src.game.resources.Coordinate;
import src.tool.common.CommonField;
import src.tool.common.CommonMaze;
import src.tool.common.CommonMazeObject;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a field in the maze that cannot contain objects.
 * The field is part of the playing field of the game.
 * @author Gabriel Biel
 * @author Jakub Mikysek
 */
public class WallField implements CommonField {
  private final Coordinate coordinate;
  private final Set<Observer> observers = new HashSet();
  private CommonMaze maze;

  /**
   * Constructs a new WallField with the specified coordinates.
   *
   * @param x the x-coordinate of the WallField.
   * @param y the y-coordinate of the WallField.
   */
  public WallField(int x, int y) {
    this.coordinate = new Coordinate(x, y);
  }

  @Override
  public Coordinate getCoordinate() {
    return this.coordinate;
  }

  /**
   * Sets the CommonMaze object that contains this WallField.
   *
   * @param maze the CommonMaze object to set.
   */
  public void setMaze(CommonMaze maze) {
    this.maze = maze;
  }

  @Override
  public CommonField nextField(Direction dirs) {
    throw new UnsupportedOperationException("Cannot move to the next field from a wall.");
  }

  /**
   * Attempts to put a CommonMazeObject on the WallField. Since it's a wall, it always returns false.
   *
   * @param object the CommonMazeObject to put.
   * @return false, as putting an object on a wall is not allowed.
   */
  public boolean put(CommonMazeObject object) {
    throw new UnsupportedOperationException("Cannot put an object on a wall.");
  }

  @Override
  public boolean isEmpty() {
    return true;
  }

  /**
   * Attempts to remove a CommonMazeObject from the WallField. Since it's a wall, it always returns false.
   *
   * @param object the CommonMazeObject to remove.
   * @return false, as removing an object from a wall is not allowed.
   */
  public boolean remove(CommonMazeObject object) {
    throw new UnsupportedOperationException("Cannot remove an object from a wall.");
  }

  @Override
  public CommonMazeObject get() {
    return null;
  }

  @Override
  public boolean canMove() {
    return false;
  }

  @Override
  public void clearField() {
    // No action needed as a wall field cannot be cleared.
    return;
  }

  @Override
  public boolean contains(CommonMazeObject commonMazeObject) {
    return false;
  }

  @Override
  public void addObserver(Observer observer) {
    // No observers can be added to a wall field.
  }

  @Override
  public void removeObserver(Observer observer) {
    // No observers can be removed from a wall field.
  }

  @Override
  public void notifyObservers() {
    // No observers to notify for a wall field.
  }
}
