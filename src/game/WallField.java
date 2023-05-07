package src.game;

import src.game.resources.Coordinate;
import src.tool.common.CommonField;
import src.tool.common.CommonMaze;
import src.tool.common.CommonMazeObject;

import java.util.HashSet;
import java.util.Set;

public class WallField implements CommonField {
  private final Coordinate coordinate;
  private final Set<Observer> observers = new HashSet();
  private CommonMaze maze;

  public WallField(int x, int y) {
    this.coordinate = new Coordinate(x, y);
  }

  @Override
  public Coordinate getCoordinate() {
    return this.coordinate;
  }

  public void setMaze(CommonMaze maze) {
    this.maze = maze;
  }

  @Override
  public CommonField nextField(Direction dirs) {
      throw new UnsupportedOperationException();
  }

  public boolean put(CommonMazeObject object) {
      throw new UnsupportedOperationException();
  }

  @Override
  public boolean isEmpty() {
      return true;
  }

  public boolean remove(CommonMazeObject object) {
      throw new UnsupportedOperationException();
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
    return;
  }

  @Override
  public boolean contains(CommonMazeObject commonMazeObject) {
    return false;
  }

  @Override
  public void addObserver(Observer observer) { }

  @Override
  public void removeObserver(Observer observer) { }

  @Override
  public void notifyObservers() { }
}
