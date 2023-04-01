package ija.ija2022.homework2.game;

import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMaze;
import ija.ija2022.homework2.tool.common.CommonMazeObject;

public class WallField implements CommonField {
    int x;
    int y;

    public WallField(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
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
  public boolean contains(CommonMazeObject commonMazeObject) {
    return false;
  }

  @Override
  public void addObserver(Observer observer) {

  }

  @Override
  public void removeObserver(Observer observer) {

  }

  @Override
  public void notifyObservers() {

  }
}
