package ija.ija2022.homework2.game;

import ija.ija2022.homework2.game.resources.Coordinate;
import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMazeObject;
import ija.ija2022.homework2.tool.common.CommonMaze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class PathField implements CommonField {
    private final Coordinate coordinate;
    private List<GhostObject> ghostList;
    private PacmanObject pacman;
    private KeyObject key;
    private TargetObject target;
    private CommonMaze maze;
    private final Set<Observer> observers = new HashSet();

    public PathField(int x, int y) {
        this.coordinate = new Coordinate(x, y);
        this.ghostList = new ArrayList<>();
        this.pacman = null;
        this.key = null;
        this.target = null;
    }

    public void setMaze(CommonMaze maze) {
        this.maze = maze;
    }

    @Override
    public void clearField() {
        this.pacman = null;
        this.ghostList.clear();
        this.key = null;
        this.target = null;
        this.notifyObservers();
    }

    public boolean put(CommonMazeObject object) {
      if (object == null) {
        return false;
      }
      // set this field to object
      object.setField(this);
      // object is pacman
      if (object.isPacman() && this.pacman == null) {
          this.pacman = (PacmanObject) object;
          this.notifyObservers();
          return true;
      }
      // object is ghost
      else if (object.getClass() == GhostObject.class){
          // if there is no pacman in the field
          // TODO check if there is pacman in the field - resolve game over
          this.ghostList.add((GhostObject) object);
          this.notifyObservers();
          return true;
        }
      else if (object.getClass() == KeyObject.class){
        this.key = (KeyObject) object;
        this.notifyObservers();
        return true;
      }
      else if (object.getClass() == TargetObject.class){
        this.target = (TargetObject) object;
        this.notifyObservers();
        return true;
      }
      // object not found
      return false;
    }

    public boolean isEmpty() {
        return this.pacman == null && this.ghostList.isEmpty();
    }

    public boolean remove(CommonMazeObject object) {
      if (object == null)
        return false;
      // object is pacman
      if (this.pacman != null && object.isPacman()) {
        this.pacman = null;
        this.notifyObservers();
        return true;
      }
      // object is ghost
      else if (object.getClass() == GhostObject.class && this.ghostList.contains(object)){
        this.ghostList.remove(object);
        this.notifyObservers();
        return true;
      }
      else if (object.getClass() == KeyObject.class && this.key == object){
        this.key = null;
        this.notifyObservers();
        return true;
      }
      else if (object.getClass() == TargetObject.class && this.target == object){
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
        case D -> {
          return this.maze.getField(this.coordinate.getX() + 1, this.coordinate.getY());
        }
        case L -> {
          return this.maze.getField(this.coordinate.getX(), this.coordinate.getY() - 1);
        }
        case R -> {
          return this.maze.getField(this.coordinate.getX(), this.coordinate.getY() + 1);
        }
        case U -> {
          return this.maze.getField(this.coordinate.getX() - 1, this.coordinate.getY());
        }
        default -> throw new UnsupportedOperationException("Unexpected value: " + dirs);
      }
    }
    @Override
  // TODO find me use case or delete me
  public CommonMazeObject get() {
   if (!this.ghostList.isEmpty())
     return this.ghostList.get(0);
   return this.pacman;
  }
  public CommonMazeObject getPacman() {
      return this.pacman;
    }

  public CommonMazeObject getKey() { return this.key; }

  public CommonMazeObject getTarget() { return this.target; }

  public List<GhostObject> getGhosts() { return this.ghostList; }

    @Override
    public boolean canMove() {
        return true;
    }

  @Override
  public boolean contains(CommonMazeObject object) {
    if (object == null)
      return false;
    List<CommonMazeObject> objects = new ArrayList<>(this.ghostList);
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
