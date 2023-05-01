package ija.ija2022.homework2.game;

import ija.ija2022.homework2.tool.common.CommonMaze;
import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMazeObject;

import java.util.List;

public class MazeConfigure {
  private static final int BORDER = 2;
  boolean pacmanPlaced;
  boolean started;
  int rows;
  int cols;
  boolean errorFlag;
  int currentRow;
  CommonField[][] fields;
  Maze maze;
  List<CommonMazeObject> listOfGhosts;
  List<CommonMazeObject> listOfKeys;

  //constructor
  public MazeConfigure() {
    this.pacmanPlaced = false;
    this.rows = 0;
    this.cols = 0;
    this.currentRow = 0;
    this.started = false;
    this.errorFlag = false;
  }

  public void startReading(int rows, int cols) {
    this.rows = rows + BORDER;
    this.cols = cols + BORDER;
    this.started = true;
    this.fields = new CommonField[rows + BORDER][cols + BORDER];
    this.maze = new Maze(this.cols, this.rows);
    this.listOfGhosts = maze.ghosts();
    this.listOfKeys = maze.keys();
  }

  public boolean processLine(String line) {
    if (!this.started || this.cols - BORDER != line.length()) {
      this.errorFlag = true;
      return false;
    }

    if (this.currentRow >= this.rows - BORDER) {
      this.errorFlag = true;
      return false;
    }
    this.currentRow++;
    PathField pathField;
    for (int i = 0; i < line.length(); i++) {
      switch (line.charAt(i)) {
        case '.':
          pathField =  new PathField(this.currentRow, i + 1);
          pathField.setMaze(this.maze);
          fields[this.currentRow][i + 1] = pathField;

          break;
        // wall
        case 'X':
          fields[this.currentRow][i + 1] = new WallField(this.currentRow, i + 1);
          break;
        // pacman
        case 'S':
          if (this.pacmanPlaced) {
            this.errorFlag = true;
            return false;
          } else {
            this.pacmanPlaced = true;
            pathField =  new PathField(this.currentRow, i + 1);
            pathField.setMaze(this.maze);
            fields[this.currentRow][i + 1] = pathField;
            pathField.put(new PacmanObject((PathField) fields[this.currentRow][i + 1]));
          }
          break;
        // ghost
        case 'G':
          // create path field
          pathField =  new PathField(this.currentRow, i + 1);
          pathField.setMaze(this.maze);
          fields[this.currentRow][i + 1] = pathField;
          // create ghost
          GhostObject ghost = new GhostObject((PathField) fields[this.currentRow][i + 1]);
          pathField.put(ghost);
          listOfGhosts.add(ghost);
          break;
        // key
        case 'K':
          // create path field
          pathField=  new PathField(this.currentRow, i + 1);
          pathField.setMaze(this.maze);
          fields[this.currentRow][i + 1] = pathField;
          // create key
          KeyObject key = new KeyObject(pathField);
          pathField.put(key);
          this.listOfKeys.add(key);
          break;
        // target
        case 'T':
          // create path field
          pathField =  new PathField(this.currentRow, i + 1);
          pathField.setMaze(this.maze);
          fields[this.currentRow][i + 1] = pathField;
          // create target
          TargetObject target = new TargetObject(pathField);
          pathField.put(target);
          break;
        default:
          this.errorFlag = true;
          return false;
      }
    }
    return true;
  }

  public boolean stopReading() {
    return currentRow + 1 == this.rows && this.started && !this.errorFlag;
  }

  public CommonMaze createMaze() {
    if (this.errorFlag)
      return null;

    //create border wall
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        if (i == 0 || i == this.rows - 1) {
          this.fields[i][j] = new WallField(i, j);
        } else if (j == 0 || j == this.cols - 1) {
          this.fields[i][j] = new WallField(i, j);
        }
      }
    }
    this.maze.setFields(fields);
    this.maze.setGhostList(listOfGhosts);
    this.maze.setKeysList(listOfKeys);
    return this.maze;
  }
}
