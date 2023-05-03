package ija.ija2022.homework2.game;

import ija.ija2022.homework2.game.resources.Coordinate;
import ija.ija2022.homework2.game.resources.ObjectType;
import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMazeObject;
import ija.ija2022.homework2.tool.common.CommonMaze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class PathField implements CommonField {
    private final Coordinate coordinate;
    private List<GhostObject> ghostOnField;
    private PacmanObject pacman;
    private KeyObject key;
    private TargetObject target;
    private CommonMaze maze;
    private final Set<Observer> observers = new HashSet<>();

    public PathField(int x, int y) {
        this.coordinate = new Coordinate(x, y);
        this.ghostOnField = new ArrayList<>();
        this.pacman = null;
        this.key = null;
        this.target = null;
    }

    public void setMaze(CommonMaze maze) {
        this.maze = maze;
    }

    @Override
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    @Override
    public void clearField() {
        this.pacman = null;
        this.ghostOnField.clear();
        this.key = null;
        this.target = null;
        this.notifyObservers();
    }

    public void put(CommonMazeObject object) {
      if (object == null) {
        return;
      }
      // set this field to object
      object.setField(this);
      // object is pacman
      if (object.isPacman() && this.pacman == null) {
          this.pacman = (PacmanObject) object;
          this.notifyObservers();
      }
      // object is ghost
      else if (object.getType() == ObjectType.GHOST){
          this.ghostOnField.add((GhostObject) object);
          this.notifyObservers();
      }
      // object is key
      else if (object.getType() == ObjectType.KEY){
        this.key = (KeyObject) object;
        this.notifyObservers();
      }
      // object is target
      else if (object.getType() == ObjectType.TARGET){
        this.target = (TargetObject) object;
        this.notifyObservers();
      }
    }

    public boolean isEmpty() {
      List<CommonMazeObject> objects = new ArrayList<>(this.ghostOnField);
      objects.add(this.pacman);
      objects.add(this.key);
      objects.add(this.target);
      objects.removeIf(item -> item == null);
      return objects.isEmpty();
    }

    public boolean remove(CommonMazeObject object) {
      if (object == null)
        return false;
      // object is pacman
      if (object.getType() == ObjectType.PACMAN) {
        this.pacman = null;
        this.notifyObservers();
        return true;
      }
      // object is ghost
      else if (object.getType() == ObjectType.GHOST && this.ghostOnField.contains(object)){
        this.ghostOnField.remove(object);
        this.notifyObservers();
        return true;
      }
      // object is key
      else if (object.getType() == ObjectType.KEY && this.key == object){
        this.key = null;
        this.notifyObservers();
        return true;
      }
      // object is target
      else if (object.getType() == ObjectType.TARGET && this.target == object){
        this.target = null;
        this.notifyObservers();
        return true;
      }
      // object not found
      return false;
    }

  @Override
    public CommonField nextField(Direction dirs) {
      switch (dirs){
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

    @Override
  public CommonMazeObject get() {
    if (this.pacman != null)
      return this.pacman;
    if (!this.ghostOnField.isEmpty())
      return this.ghostOnField.get(0);
    if (this.key != null)
      return this.key;
    if (this.target != null)
      return this.target;
    return null;
    }

  public CommonMazeObject getPacman() {
      return this.pacman;
    }

  public CommonMazeObject getKey() { return this.key; }

  public CommonMazeObject getTarget() { return this.target; }

  public List<GhostObject> getGhosts() { return this.ghostOnField; }

    @Override
  public boolean canMove() {
        return true;
    }

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
  @Override
  public void addObserver(Observer observer) { this.observers.add(observer); }

  @Override
  public void removeObserver(Observer observer) { this.observers.remove(observer); }

  @Override
  public void notifyObservers() {
     this.observers.forEach((observer) -> {
        observer.update(this);
      });
    }

  public CommonMaze getMaze() {
      return this.maze;
  }
}
