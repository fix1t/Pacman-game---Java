package src.game;

import src.game.resources.Coordinate;
import src.game.resources.ObjectType;
import src.tool.common.CommonField;
import src.tool.common.CommonMazeObject;
import src.tool.common.CommonMaze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a field in the maze that can contain objects.
 */
public class PathField implements CommonField {
  private final Coordinate coordinate;
  private List<GhostObject> ghostOnField;
  private PacmanObject pacman;
  private KeyObject key;
  private BoostObject boost;
  private TargetObject target;
  private CommonMaze maze;
  private final Set<Observer> observers = new HashSet<>();

  /**
   * Constructs a PathField with the specified coordinates.
   *
   * @param x the x-coordinate of the field.
   * @param y the y-coordinate of the field.
   */
  public PathField(int x, int y) {
    this.coordinate = new Coordinate(x, y);
    this.ghostOnField = new ArrayList<>();
    this.pacman = null;
    this.key = null;
    this.target = null;
  }

  /**
   * Sets the maze that contains this field.
   *
   * @param maze the maze containing this field.
   */
  public void setMaze(CommonMaze maze) {
    this.maze = maze;
  }

  /**
   * Returns the coordinate of the field.
   *
   * @return the coordinate of the field.
   */
  @Override
  public Coordinate getCoordinate() {
    return this.coordinate;
  }

  /**
   * Clears the field by removing all objects from it.
   */
  @Override
  public void clearField() {
    this.pacman = null;
    this.ghostOnField.clear();
    this.key = null;
    this.boost = null;
    this.target = null;
    this.notifyObservers();
  }

  /**
   * Puts the specified object on the field.
   *
   * @param object the object to put on the field.
   */
  public void put(CommonMazeObject object) {
    if (object == null) {
      return;
    }
    // Set this field to object
    object.setField(this);
    // If object is pacman
    if (object.isPacman() && this.pacman == null) {
      this.pacman = (PacmanObject) object;
    }
    // If object is ghost
    else if (object.getType() == ObjectType.GHOST) {
      this.ghostOnField.add((GhostObject) object);
    }
    // If object is key
    else if (object.getType() == ObjectType.KEY) {
      this.key = (KeyObject) object;
    }
    // If object is target
    else if (object.getType() == ObjectType.TARGET) {
      this.target = (TargetObject) object;
    }
    // If object is boost
    else if (object.getType() == ObjectType.BOOST) {
      this.boost = (BoostObject) object;
    }
    this.notifyObservers();
  }

  /**
   * Checks if the field is empty, i.e., it contains no objects.
   *
   * @return true if the field is empty, false otherwise.
   */
  public boolean isEmpty() {
    List<CommonMazeObject> objects = new ArrayList<>(this.ghostOnField);
    objects.add(this.pacman);
    objects.add(this.key);
    objects.add(this.target);
    objects.add(this.boost);
    objects.removeIf(item -> item == null);
    return objects.isEmpty();
  }

  /**
   * Removes the specified object from the field.
   *
   * @param object the object to remove from the field.
   */
  public void remove(CommonMazeObject object) {
    if (object == null)
      return;
    // If object is pacman
    if (object.getType() == ObjectType.PACMAN) {
      this.pacman = null;
      this.notifyObservers();
    }
    // If object is ghost
    else if (object.getType() == ObjectType.GHOST && this.ghostOnField.contains(object)) {
      this.ghostOnField.remove(object);
      this.notifyObservers();
    }
    // If object is key
    else if (object.getType() == ObjectType.KEY && this.key == object) {
      this.key = null;
      this.notifyObservers();
    }
    // If object is target
    else if (object.getType() == ObjectType.TARGET && this.target == object) {
      this.target = null;
      this.notifyObservers();
    }
    // If object is boost
    else if (object.getType() == ObjectType.BOOST && this.boost == object) {
      this.boost = null;
      this.notifyObservers();
    }
    // Object not found
  }

  /**
   * Returns the next field in the specified direction.
   *
   * @param dirs the direction in which to move.
   * @return the next field in the specified direction, or this field if the movement is stopped.
   */
  @Override
  public CommonField nextField(Direction dirs) {
    switch (dirs) {
      case DOWN -> {
        return this.maze.getField(this.coordinate.getX() + 1, this.coordinate.getY());
      }
      case LEFT -> {
        return this.maze.getField(this.coordinate.getX(), this.coordinate.getY() - 1);
      }
      case RIGHT -> {
        return this.maze.getField(this.coordinate.getX(), this.coordinate.getY() + 1);
      }
      case UP -> {
        return this.maze.getField(this.coordinate.getX() - 1, this.coordinate.getY());
      }
      case STOP -> {
        return this;
      }
      default -> throw new UnsupportedOperationException("Unexpected value: " + dirs);
    }
  }

  /**
   * Returns the object on the field, if any.
   *
   * @return the object on the field, or null if the field is empty.
   */
  @Override
  public CommonMazeObject get() {
    if (this.pacman != null)
      return this.pacman;
    if (!this.ghostOnField.isEmpty())
      return this.ghostOnField.get(0);
    if (this.key != null)
      return this.key;
    if (this.boost != null)
      return this.boost;
    if (this.target != null)
      return this.target;
    return null;
  }

  /**
   * Returns the Pacman object on the field, if any.
   *
   * @return the Pacman object on the field, or null if there is no Pacman object.
   */
  public CommonMazeObject getPacman() {
    return this.pacman;
  }

  /**
   * Returns the Key object on the field, if any.
   *
   * @return the Key object on the field, or null if there is no Key object.
   */
  public CommonMazeObject getKey() {
    return this.key;
  }

  /**
   * Returns the Target object on the field, if any.
   *
   * @return the Target object on the field, or null if there is no Target object.
   */
  public CommonMazeObject getTarget() {
    return this.target;
  }

  /**
   * Returns the Boost object on the field, if any.
   *
   * @return the Boost object on the field, or null if there is no Boost object.
   */
  public CommonMazeObject getBoost() {
    return this.boost;
  }

  /**
   * Returns a list of Ghost objects on the field.
   *
   * @return a list of Ghost objects on the field.
   */
  public List<GhostObject> getGhosts() {
    return this.ghostOnField;
  }

  /**
   * Checks if the field allows movement.
   *
   * @return true if movement is allowed, false otherwise.
   */
  @Override
  public boolean canMove() {
    return true;
  }

  /**
   * Checks if the field contains the specified object.
   *
   * @param object the object to check.
   * @return true if the field contains the object, false otherwise.
   */
  @Override
  public boolean contains(CommonMazeObject object) {
    if (object == null)
      return false;
    List<CommonMazeObject> objects = new ArrayList<>(this.ghostOnField);
    objects.add(this.pacman);
    objects.add(this.key);
    objects.add(this.target);
    return objects.contains(object);
  }

  /**
   * Adds an observer to the field.
   *
   * @param observer the observer to add.
   */
  @Override
  public void addObserver(Observer observer) {
    this.observers.add(observer);
  }

  /**
   * Removes an observer from the field.
   *
   * @param observer the observer to remove.
   */
  @Override
  public void removeObserver(Observer observer) {
    this.observers.remove(observer);
  }

  /**
   * Notifies all observers of the field.
   */
  @Override
  public void notifyObservers() {
    this.observers.forEach((observer) -> {
      observer.update(this);
    });
  }

  /**
   * Returns the maze that contains this field.
   *
   * @return the maze that contains this field.
   */
  public CommonMaze getMaze() {
    return this.maze;
  }
}
