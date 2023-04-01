package ija.ija2022.homework2.game;

import ija.ija2022.homework2.tool.common.CommonMaze;
import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMazeObject;

import java.util.ArrayList;
import java.util.List;

public class PacmanMaze implements CommonMaze {
    int cols;
    int rows;
    CommonField[][] fields;

    public PacmanMaze(int col, int row) {
        this.cols = col;
        this.rows = row;
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
    public List<CommonMazeObject> ghosts() {
      List<CommonMazeObject> listOfGhosts = new ArrayList<>();
      // find all ghosts in the maze
      for (int i = 0; i < this.rows; i++) {
        for (int j = 0; j < this.cols; j++) {
          // add ghost to the list
          if (fields[i][j].get() != null && !fields[i][j].get().isPacman()){
            listOfGhosts.add(fields[i][j].get());
          }
        }
      }
      return listOfGhosts;
    }
}
