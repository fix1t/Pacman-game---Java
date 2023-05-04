package ija.ija2022.homework2.game;

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
  private Map<PathField,CommonMazeObject> initialObjectsLayout;

  /**
   * Constructs a new Maze with the specified number of columns and rows.
   *
   * @param col  the number of columns in the Maze.
   * @param row  the number of rows in the Maze.
   */
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

  /**
   * Sets the CommonField array representing the Maze.
   *
   * @param fields  the CommonField array representing the Maze.
   */
    public void setFields(CommonField[][] fields) {
        this.fields = fields;
    }

  /**
   * Returns the number of columns in the Maze.
   *
   * @return the number of columns in the Maze.
   */
    @Override
    public int numCols() {
        return this.cols;
    }

  /**
   * Returns the number of rows in the Maze.
   *
   * @return the number of rows in the Maze.
   */
    @Override
    public int numRows() {
          return this.rows;
      }


  /**
   * Returns the CommonField object at the specified coordinates in the Maze.
   *
   * @param row  the row coordinate of the CommonField.
   * @param col  the column coordinate of the CommonField.
   * @return the CommonField object at the specified coordinates in the Maze, or null if the coordinates are out of bounds.
   */
    @Override
    public CommonField getField(int row, int col) {
        if (row < 0 || row > this.numRows())
            return null;
        if (col < 0 || col > this.numCols())
            return null;
        return fields[row][col];
    }

  /**
   * Returns a copy of the list of ghosts in the Maze.
   *
   * @return a copy of the list of ghosts in the Maze.
   */
    @Override
    public List<CommonMazeObject> getGhosts() {
      return new ArrayList<>(this.listOfGhosts);
    }

  /**
   * Returns a copy of the list of keys in the Maze.
   *
   * @return a copy of the list of keys in the Maze.
   */
    @Override
    public  List<CommonMazeObject> getKeys() {
      return new ArrayList<>(this.listOfKeys);
    }

  /**
   * Returns the target object in the Maze.
   *
   * @return the target object in the Maze, or null if there is no target object.
   */
  @Override
  public CommonMazeObject getTarget() {
    return this.target;
  }

  /**
   * Returns the Pacman object in the Maze.
   *
   * @return the Pacman object in the Maze, or null if there is no Pacman object.
   */
  @Override
  public PacmanObject getPacman() {
    return this.pacman;
  }

  /**
   * Restores the initial state of the Maze by clearing all fields and objects and then restoring the initial
   * objects' layout.
   */
  @Override
  public void restore() {
    // clear all fields
    clearAllFields();
    // clear all lists and objects but pacman
    this.listOfKeys.clear();
    this.listOfGhosts.clear();
    this.target = null;
    // restore initial objects layout
    for (Map.Entry<PathField,CommonMazeObject> entry : this.initialObjectsLayout.entrySet()) {
      PathField field = entry.getKey();
      CommonMazeObject object = entry.getValue();
      field.put(object);
      // add objects to lists
      switch (object.getType()) {
        case GHOST:
          this.listOfGhosts.add(object);
          break;
        case KEY:
          this.listOfKeys.add(object);
          break;
        case TARGET:
          this.target = (TargetObject) object;
          break;
        case PACMAN:
          this.pacman = (PacmanObject) object;
          break;
        default:
          break;
      }
    }
  }

  /**
   * Clears all fields in the Maze.
   */
  private void clearAllFields() {
    for (int i = 0; i < this.numRows(); i++) {
      for (int j = 0; j < this.numCols(); j++) {
        this.fields[i][j].clearField();
      }
    }
  }

  /**
   * Sets the list of ghosts in the Maze to the specified list.
   *
   * @param listOfGhosts  the list of ghosts to set in the Maze.
   */
  public void setGhostList(List<CommonMazeObject> listOfGhosts) {
      this.listOfGhosts = listOfGhosts;
  }

  /**
   * Sets the list of keys in the Maze to the specified list.
   *
   * @param listOfKeys  the list of keys to set in the Maze.
   */
  public void setKeysList(List<CommonMazeObject> listOfKeys) {
      this.listOfKeys = listOfKeys;
  }

  /**
   * Sets the Pacman object in the Maze to the specified object.
   *
   * @param pacman  the Pacman object to set in the Maze.
   */
  public void setPacman(PacmanObject pacman) {
      this.pacman = pacman;
  }

  /**
   * Sets the target object in the Maze to the specified object.
   *
   * @param target  the target object to set in the Maze.
   */
  public void setTarget(TargetObject target) {
      this.target = target;
  }

  /**
   * Sets the initial objects layout of the Maze to the specified map.
   *
   * @param initialObjectsLayout  the initial objects layout of the Maze.
   */
  public void setInitialObjectsLayout(Map<PathField, CommonMazeObject> initialObjectsLayout) {
    this.initialObjectsLayout = initialObjectsLayout;
  }
}
