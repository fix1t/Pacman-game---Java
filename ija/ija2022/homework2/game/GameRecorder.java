package ija.ija2022.homework2.game;

import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMazeObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameRecorder {
  /**
   * Captures the game state for each maze object.
   */
  Map<CommonMazeObject, List<CommonField>> stateMap;

  /**
   * Creates a new game recorder.
   */
  public GameRecorder() {
    this.stateMap = new HashMap<>();
  }

  public void captureState(List<CommonMazeObject> allMazeObjects) {
    for (CommonMazeObject mazeObject : allMazeObjects) {
      if (!stateMap.containsKey(mazeObject)) {
        // If not present, create a new empty list and add it to the map
        stateMap.put(mazeObject, new ArrayList<>());
      }
      List<CommonField> fieldsList = stateMap.get(mazeObject);
      fieldsList.add(mazeObject.getField());
    }
  }
}
