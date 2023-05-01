package ija.ija2022.homework2.tool.common;

import java.util.List;

public interface CommonMaze {
  CommonField getField(int var1, int var2);

  int numRows();

  int numCols();

  List<CommonMazeObject> ghosts();
}
