package ija.ija2022.homework2.game;

import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMazeObject;
import ija.ija2022.homework2.tool.common.CommonMaze;
import ija.ija2022.homework2.tool.view.FieldView;

import java.util.HashSet;
import java.util.Set;


public class PathField implements CommonField {
    int x;
    int y;
    private GhostObject ghost;
    private PacmanObject pacman;
    private CommonMaze maze;
    private final Set<Observer> observers = new HashSet();

    public PathField(int x, int y) {
        this.x = x;
        this.y = y;
        this.ghost = null;
        this.pacman = null;
    }

    private int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setMaze(CommonMaze maze) {
        this.maze = maze;
    }

    public boolean put(CommonMazeObject object) {
      if (object != null){
        if (object.isPacman()) {
          if (this.pacman == null) {
            this.pacman = (PacmanObject) object;
            this.notifyObservers();
            return true;
          }
        }
        else {
          if (this.ghost == null) {
            if (this.pacman != null) {
              this.notifyObservers();
              this.ghost = (GhostObject) object;
            }
            else {
              this.ghost = (GhostObject) object;
              this.notifyObservers();
            }
          }
        }
      }
        return false;
    }

    public boolean isEmpty() {
        return this.pacman == null && this.ghost == null;
    }

    public boolean remove(CommonMazeObject object) {
        if (this.pacman != null) {
          if (object.getClass() == this.pacman.getClass()){
            this.pacman = null;
            this.notifyObservers();
            return true;
          }
        }
        if (this.ghost != null) {
          if (object.getClass() == this.ghost.getClass()){
            this.ghost = null;
            this.notifyObservers();
            return true;
          }
        }
        return false;
    }

    @Override
    public CommonField nextField(Direction dirs) {
      switch (dirs){
        case D -> {
          return this.maze.getField(this.getX() + 1, this.getY());
        }
        case L -> {
          return this.maze.getField(this.getX(), this.getY() - 1);
        }
        case R -> {
          return this.maze.getField(this.getX(), this.getY() + 1);
        }
        case U -> {
          return this.maze.getField(this.getX() - 1, this.getY());
        }
        default -> throw new UnsupportedOperationException("Unexpected value: " + dirs);
      }
    }
    @Override
    public CommonMazeObject get() {
       if (this.ghost != null)
         return this.ghost;
       return this.pacman;
    }

    @Override
    public boolean canMove() {
        return true;
    }

  @Override
  public boolean contains(CommonMazeObject commonMazeObject) {
      if (this.pacman == null && this.ghost == null)
        return false;
      else if (commonMazeObject.isPacman() || !this.ghost.isPacman() && !commonMazeObject.isPacman())
        return true;
      else
        return false;
  }
  public PacmanObject getPacman() {
    return this.pacman;
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
}
