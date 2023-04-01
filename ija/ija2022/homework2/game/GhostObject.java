package ija.ija2022.homework2.game;

import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMazeObject;

public class GhostObject implements CommonMazeObject {
  CommonField currentField;
    public GhostObject(CommonField field) {
      this.currentField = field;
  }

  @Override
  public boolean canMove(CommonField.Direction direction) {
    return false;
  }

  @Override
  public boolean move(CommonField.Direction direction) {
    return false;
  }

  @Override
  public CommonField getField() {
    return null;
  }

  @Override
  public int getLives() {
    return 0;
  }
}
