package ija.ija2022.homework2.game;

import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMazeObject;

public class TargetObject implements CommonMazeObject {
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
    return null;
  }

  @Override
  public int getLives() {
    return 0;
  }
}

