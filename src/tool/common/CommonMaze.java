package src.tool.common;

import src.game.PacmanObject;
import src.game.PathField;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CommonMaze {
  CommonField getField(int var1, int var2);

  int numRows();

  int numCols();

  List<CommonMazeObject> getGhosts();

  List<CommonMazeObject> getKeys();
  List<CommonMazeObject> getBoosts();

  CommonMazeObject getTarget();

  PacmanObject getPacman();

  void restore();

  void restoreGame();

  void setObjectLayoutTo(Map<CommonMazeObject, PathField> objectsLayout);
}
