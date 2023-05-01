package ija.ija2022.homework2.game;

import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMazeObject;

import java.security.Key;

public class KeyObject implements CommonMazeObject {
  PathField currentField;
  public KeyObject(PathField pathField) {
    this.currentField = pathField;
  }

  @Override
  public boolean canMove(CommonField.Direction var1) {
    return false;
  }

  @Override
  public boolean move(CommonField.Direction var1) {
    return false;
  }

  @Override
  public CommonField getField() {
    return this.currentField;
  }

  @Override
  public int getLives() {
    return 0;
  }
}
