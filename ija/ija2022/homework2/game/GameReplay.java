package ija.ija2022.homework2.game;

import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMaze;
import ija.ija2022.homework2.tool.common.CommonMazeObject;

import java.util.List;
import java.util.Map;

public class GameReplay {
  int currentState;
  int totalStates;
  GameRecorder gameRecorder;
  CommonMaze maze;
  Map<CommonMazeObject, List<CommonField>> stateMap;


  public void ReplayGameFromStart() {

  }

  public void ReplayGameFromEnd() {

  }

  public boolean loadGameFromFile() {
    return false;
  }

  public boolean loadGameFromGameRecorder() {
    return false;
  }

  public void presentState(int state) {

  }

  public void presentNextState() {

  }

  public void presentPreviousState() {

  }
}
