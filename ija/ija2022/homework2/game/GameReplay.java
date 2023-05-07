package ija.ija2022.homework2.game;
import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMaze;
import ija.ija2022.homework2.tool.common.CommonMazeObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameReplay {
  int currentState;
  int totalStates;
  GameRecorder gameRecorder;
  CommonMaze maze;
  Map<CommonMazeObject, List<CommonField>> stateMap;

  public GameReplay() {
    this.gameRecorder = null;
    this.stateMap = new HashMap<>();
    this.currentState = 0;
    this.totalStates = 0;
    this.maze = null;
  }

  public GameReplay(GameRecorder gameRecorder) {
    this.gameRecorder = gameRecorder;
    this.stateMap = gameRecorder.stateMap;
    this.currentState = 0;
    this.totalStates = stateMap.values().stream().mapToInt(List::size).max().orElse(0);
  }

  public CommonMaze getMaze() {
    return maze;
  }

  public void setMaze(CommonMaze maze) {
    this.maze = maze;
  }

  public void ReplayGameFromStart() {
    currentState = 0;
    presentState(currentState);
  }

  public void ReplayGameFromEnd() {
    currentState = totalStates - 1;
    presentState(currentState);
  }

  public boolean loadGameFromFile(Path pathToMaze) {
    boolean success = false;
    InputStream inputStream = null;

    // Loading maze from file
    try {
        inputStream = Files.newInputStream(pathToMaze);
        success =  this.loadMazeFromFile(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    // Loading successful/unsuccessful
    if (!success) {
      return false;
    }
    // Loading steps from file
    success = this.loadStepsToMapFromFile(inputStream);
    return success;
  }

  private boolean loadStepsToMapFromFile(InputStream inputStream) {
    return true;
  }

  public boolean loadMazeFromFile(InputStream inputStream){
      MazeConfigure mazeConfigure = new MazeConfigure();
      this.maze = mazeConfigure.loadMaze(inputStream);
      // Loading successful/unsuccessful
      if (this.maze == null) {
        System.out.println("Failed to load maze from file.");
        return false;
      }else {
        return true;
      }

  }

  public boolean loadGameFromRecorder(GameRecorder gameRecorder) {
    this.gameRecorder = gameRecorder;
    this.stateMap = gameRecorder.stateMap;
    this.totalStates = stateMap.values().stream().mapToInt(List::size).max().orElse(0);
    return true;
  }

  public void presentState(int state) {
    if (state < 0 || state >= totalStates) {
      System.out.println("Invalid state.");
      return;
    }

    for (Map.Entry<CommonMazeObject, List<CommonField>> entry : stateMap.entrySet()) {
      CommonMazeObject mazeObject = entry.getKey();
      List<CommonField> fieldsList = entry.getValue();
      if (state < fieldsList.size()) {
        CommonField field = fieldsList.get(state);
        System.out.println("Maze Object: " + mazeObject.getType() + " at Position: " + field.getCoordinate());
      }
    }
  }

  public void presentNextState() {
    if (currentState + 1 < totalStates) {
      currentState++;
      presentState(currentState);
    } else {
      System.out.println("Already at the last state.");
    }
  }

  public void presentPreviousState() {
    if (currentState - 1 >= 0) {
      currentState--;
      presentState(currentState);
    } else {
      System.out.println("Already at the first state.");
    }
  }

  public void continueForward() {
  }

  public void continueBackward() {
  }
}
