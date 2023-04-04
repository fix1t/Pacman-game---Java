package ija.ija2022.homework2.game;

import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMazeObject;
import ija.ija2022.homework2.tool.common.CommonMaze;

import java.util.HashSet;
import java.util.Set;


public class PathField implements CommonField {
    int x;
    int y;
    CommonMazeObject obj;
    CommonMaze maze;
    private final Set<Observer> observers = new HashSet();

    public PathField(int x, int y) {
        this.x = x;
        this.y = y;
        this.obj = null;
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
        if (this.obj == null) {
            this.obj = object;
            this.notifyObservers();
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return this.obj == null;
    }

    public boolean remove(CommonMazeObject object) {
        if (this.obj == null)
            return false;
        if (object.getClass() == this.obj.getClass()){
            this.notifyObservers();
            this.obj = null;
            return true;
        }else{
            return false;
        }
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
        return this.obj;
    }

    @Override
    public boolean canMove() {
        return true;
    }

  @Override
  public boolean contains(CommonMazeObject commonMazeObject) {
      if (this.obj == null)
        return false;
      else if (this.obj.isPacman() && commonMazeObject.isPacman() || !this.obj.isPacman() && !commonMazeObject.isPacman())
        return true;
      else
        return false;
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
