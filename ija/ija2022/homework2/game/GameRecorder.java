package ija.ija2022.homework2.game;

import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMazeObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameRecorder {
  /**
   * Captures the game state for each maze object.
   */
  Map<CommonMazeObject, List<CommonField>> stateMap;
  PrintWriter writer;

  /**
   * Creates a new game recorder.
   */
  public GameRecorder() {
    this.stateMap = new HashMap<>();
    try {
      // Overwrite the file if it already exists
      this.writer = new PrintWriter(new FileWriter("game.log", false));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void closeWriter() {
    this.writer.close();
  }

  public void captureState(List<CommonMazeObject> allMazeObjects) {
    for (CommonMazeObject mazeObject : allMazeObjects) {
      if (!this.stateMap.containsKey(mazeObject)) {
        // If not present, create a new empty list and add it to the map
        this.stateMap.put(mazeObject, new ArrayList<>());
      }
      List<CommonField> fieldsList = this.stateMap.get(mazeObject);
      fieldsList.add(mazeObject.getField());
    }
  }

  /**
   * Creates a game log file.
   * For each maze object, print its type and all fields it has been on
   * Start -> End ordered.
   */
  public void createGameLog() {
    for (Map.Entry<CommonMazeObject, List<CommonField>> entry : stateMap.entrySet()) {
      CommonMazeObject mazeObject = entry.getKey();
      List<CommonField> fieldsList = entry.getValue();
      for (int i = 0; i < fieldsList.size(); i++) {
        if (i == 0) {
          // Print the type of the maze object only once at the beginning
          this.writer.println("NEW: " + mazeObject.getType().toString());
        }
        CommonField field = fieldsList.get(i);
        this.writer.println("ORD: " + (i + 1) + " | (" + field.getCoordinate().getX() + "," + field.getCoordinate().getY() + ")");
      }
    }
  }
}
