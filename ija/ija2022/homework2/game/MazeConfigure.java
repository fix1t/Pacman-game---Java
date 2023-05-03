package ija.ija2022.homework2.game;

import ija.ija2022.homework2.tool.common.CommonMaze;
import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMazeObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MazeConfigure {
  private static final int BORDER = 2;
  boolean started;
  int rows;
  int cols;
  boolean errorFlag;
  int currentRow;
  CommonField[][] fields;
  Maze maze;
  List<CommonMazeObject> listOfGhosts;
  List<CommonMazeObject> listOfKeys;
  PacmanObject pacman;
  TargetObject target;
  Map<PathField, CommonMazeObject> initialObjectsLayout;
  public MazeConfigure() {
    this.rows = 0;
    this.cols = 0;
    this.currentRow = 0;
    this.started = false;
    this.errorFlag = false;
    this.listOfGhosts = new ArrayList<>();
    this.listOfKeys = new ArrayList<>();
    this.pacman = null;
    this.target = null;
    this.initialObjectsLayout = new HashMap<>();
  }

  public void startReading(int rows, int cols) {
    this.rows = rows + BORDER;
    this.cols = cols + BORDER;
    this.started = true;
    this.fields = new CommonField[rows + BORDER][cols + BORDER];
    this.maze = new Maze(this.cols, this.rows);
  }

  private PathField createPathField(int row, int col) {
    PathField pathField = new PathField(row, col);
    pathField.setMaze(this.maze);
    fields[row][col] = pathField;
    return pathField;
  }

  private void handleDotCase(int i) {
    createPathField(this.currentRow, i + 1);
  }

  private void handleWallCase(int i) {
    fields[this.currentRow][i + 1] = new WallField(this.currentRow, i + 1);
  }

  private boolean handlePacmanCase(int i) {
    // if pacman is already placed, return false
    if (this.pacman != null) {
      return false;
    } else {
      PathField pathField = createPathField(this.currentRow, i + 1);
      this.pacman = new PacmanObject(pathField, this.listOfKeys);
      pathField.put(this.pacman);
      // put pacman into initialObjectsLayout
      this.initialObjectsLayout.put(pathField, this.pacman);
      return true;
    }
  }

  private void handleGhostCase(int i) {
    PathField pathField = createPathField(this.currentRow, i + 1);
    GhostObject ghost = new GhostObject(pathField);
    pathField.put(ghost);
    listOfGhosts.add(ghost);
    // put ghost into initialObjectsLayout
    this.initialObjectsLayout.put(pathField, ghost);
  }

  private void handleKeyCase(int i) {
    PathField pathField = createPathField(this.currentRow, i + 1);
    KeyObject key = new KeyObject(pathField);
    pathField.put(key);
    this.listOfKeys.add(key);
    // put key into initialObjectsLayout
    this.initialObjectsLayout.put(pathField, key);
  }

  private boolean handleTargetCase(int i) {
    if (this.target != null) {
      return false;
    } else {
      PathField pathField = createPathField(this.currentRow, i + 1);
      this.target = new TargetObject(pathField);
      pathField.put(this.target);
      // put target into initialObjectsLayout
      this.initialObjectsLayout.put(pathField, this.target);
      return true;
    }
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

    for (int i = 0; i < line.length(); i++) {
      switch (line.charAt(i)) {
        case '.':
          handleDotCase(i);
          break;
        case 'X':
          handleWallCase(i);
          break;
        case 'S':
          // if pacman is already placed, return false = error
          if (!handlePacmanCase(i)) {
            return false;
          }
          break;
        case 'G':
          handleGhostCase(i);
          break;
        case 'K':
          handleKeyCase(i);
          break;
        case 'T':
          // if target is already placed, return false = error
          if (!handleTargetCase(i)){
            return false;
          }
          break;
        default:
          this.errorFlag = true;
          return false;
      }
    }
    return true;
  }


  public boolean stopReading() {
    // if maze is not started or errorFlag is true, return false
    // if stopped reading before all rows/more rows were read, return false
    return currentRow == this.rows  - BORDER && this.started && !this.errorFlag;
  }

  public CommonMaze loadMaze(InputStream inputStream) {
    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
      String[] dimensions = br.readLine().split(" ");
      int rows = Integer.parseInt(dimensions[0]);
      int cols = Integer.parseInt(dimensions[1]);
      this.startReading(rows, cols);
      String line;
      while ((line = br.readLine()) != null) {
        if (!this.processLine(line)) {
          return null;
        }
      }
      // check if maze is finished correctly
      if (this.stopReading()) {
        return this.createMaze();
      } else {
        return null;
      }
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void addBorder(){
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        if (i == 0 || i == this.rows - 1) {
          this.fields[i][j] = new WallField(i, j);
        } else if (j == 0 || j == this.cols - 1) {
          this.fields[i][j] = new WallField(i, j);
        }
      }
    }
  }

  public CommonMaze createMaze() {
    if (this.errorFlag)
      return null;

    //create border wall
    this.addBorder();
    //set fields
    this.maze.setFields(fields);
    this.maze.setGhostList(listOfGhosts);
    this.maze.setKeysList(listOfKeys);
    this.maze.setPacman(this.pacman);
    this.maze.setTarget(this.target);
    this.maze.setInitialObjectsLayout(this.initialObjectsLayout);
    return this.maze;
  }
}
