package ija.ija2022.homework2.game;

import ija.ija2022.homework2.tool.common.CommonMaze;
import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMazeObject;

import java.util.ArrayList;
import java.util.List;

public class Maze implements CommonMaze {
    int cols;
    int rows;
    CommonField[][] fields;
  List<CommonMazeObject> listOfGhosts;
  List<CommonMazeObject> listOfKeys;

    public Maze(int col, int row) {
      this.cols = col;
      this.rows = row;
      this.fields = new CommonField[row][col];
      this.listOfGhosts = new ArrayList<>();
      this.listOfKeys = new ArrayList<>();
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
    public List<CommonMazeObject> ghosts() {
      return new ArrayList<>(this.listOfGhosts);
    }
    @Override
    // return copy of list of keys
    public  List<CommonMazeObject> keys() {
      return new ArrayList<>(this.listOfKeys);
    }
  public void setGhostList(List<CommonMazeObject> listOfGhosts) {
      this.listOfGhosts = listOfGhosts;
  }

  public void setKeysList(List<CommonMazeObject> listOfKeys) {
      this.listOfKeys = listOfKeys;
  }
}
