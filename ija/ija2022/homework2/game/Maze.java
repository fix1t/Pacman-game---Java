package ija.ija2022.homework2.game;

import ija.ija2022.homework2.game.resources.Coordinate;
import ija.ija2022.homework2.tool.common.CommonMaze;
import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMazeObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Maze implements CommonMaze {
    int cols;
    int rows;
    CommonField[][] fields;
  List<CommonMazeObject> listOfGhosts;
  List<CommonMazeObject> listOfKeys;
  PacmanObject pacman;
  TargetObject target;
  private Map<Coordinate, String> initialObjectsLayout;

  public Maze(int col, int row) {
      this.cols = col;
      this.rows = row;
      this.fields = new CommonField[row][col];
      this.listOfGhosts = new ArrayList<>();
      this.listOfKeys = new ArrayList<>();
      this.pacman = null;
      this.target = null;
      this.initialObjectsLayout = null;
    }

    public void setFields(CommonField[][] fields) {
        this.fields = fields;
    }

    @Override
    public int numCols() {
        return this.cols;
    }

    @Override
    public int numRows() {
          return this.rows;
      }

    @Override
    public CommonField getField(int row, int col) {
        if (row < 0 || row > this.numRows())
            return null;
        if (col < 0 || col > this.numCols())
            return null;
        return fields[row][col];
    }

    @Override
    // return copy of list of ghosts
    public List<CommonMazeObject> getGhosts() {
      return new ArrayList<>(this.listOfGhosts);
    }
    @Override
    // return copy of list of keys
    public  List<CommonMazeObject> getKeys() {
      return new ArrayList<>(this.listOfKeys);
    }

  @Override
  public CommonMazeObject getTarget() {
    return this.target;
  }

  @Override
  public CommonMazeObject getPacman() {
    return this.pacman;
  }

  @Override
  public void restore() {
    // clear all fields
    clearAllFields();
    // clear all lists and objects but pacman
    this.listOfKeys.clear();
    this.listOfGhosts.clear();
    this.target = null;
    // restore initial objects layout
    for (Map.Entry<Coordinate, String> entry : this.initialObjectsLayout.entrySet()) {
      Coordinate coordinate = entry.getKey();
      String objectType = entry.getValue();
      this.configureField(coordinate, objectType);
    }
  }

  private void configureField(Coordinate coordinate, String objectType) {
      PathField field = (PathField) this.getField(coordinate.getX(), coordinate.getY());
      switch (objectType) {
        case "S":
          this.pacman.resetPosition(field);
          break;
        case "G":
          GhostObject ghost = new GhostObject((PathField) field);
          field.put(ghost);
          this.listOfGhosts.add(ghost);
          break;
        case "K":
          KeyObject key = new KeyObject((PathField) field);
          field.put(key);
          this.listOfKeys.add(key);
          break;
        case "T":
          this.target = new TargetObject((PathField) field);
          field.put(this.target);
          break;
      }
      field.notifyObservers();
  }

  private void clearAllFields() {
    for (int i = 0; i < this.numRows(); i++) {
      for (int j = 0; j < this.numCols(); j++) {
        this.fields[i][j].clearField();
      }
    }
  }

  public void setGhostList(List<CommonMazeObject> listOfGhosts) {
      this.listOfGhosts = listOfGhosts;
  }

  public void setKeysList(List<CommonMazeObject> listOfKeys) {
      this.listOfKeys = listOfKeys;
  }

  public void setPacman(PacmanObject pacman) {
      this.pacman = pacman;
  }

  public void setTarget(TargetObject target) {
      this.target = target;
  }

  public void setInitialObjectsLayout(Map<Coordinate, String> initialObjectsLayout) {
    this.initialObjectsLayout = initialObjectsLayout;
  }
}
