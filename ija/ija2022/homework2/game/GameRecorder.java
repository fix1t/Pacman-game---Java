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
  int moveCount;

  /**
   * Creates a new game recorder.
   */
  public GameRecorder() {
    this.stateMap = new HashMap<>();
    this.moveCount = 0;
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

  public void captureState(List<CommonMazeObject> allMazeObjects, boolean writeToFile) {
    if (!writeToFile) {
      this.captureState(allMazeObjects);
      return;
    }
    for (int i = 0; i < allMazeObjects.size(); i++) {
      CommonMazeObject mazeObject = allMazeObjects.get(i);
      CommonField field = mazeObject.getField();
      String mazeObjectType = mazeObject.getType().toString();
      if (field == null) {
        // If the maze object is not on any field, skip it
        continue;
      }
      // [ORD]: [moveCount] [OBJ]: [mazeObjectType] [i] [ON]: ([x],[y])
      String message = "ORD: " + (this.moveCount) + " OBJ: " + mazeObjectType + " " + i
        +  " ON: (" + field.getCoordinate().getX() + "," + field.getCoordinate().getY() + ")";
      this.writer.println(message);
    }
    this.moveCount++;
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
