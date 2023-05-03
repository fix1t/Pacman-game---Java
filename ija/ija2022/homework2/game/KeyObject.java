package ija.ija2022.homework2.game;

import ija.ija2022.homework2.game.resources.ObjectType;
import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMazeObject;

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
  public boolean move() { return false; }

  @Override
  public boolean move(CommonField.Direction var1) {
    return false;
  }

  @Override
  public boolean isPacman() { return false; }

  @Override
  public CommonField getField() {
    return this.currentField;
  }

  @Override
  public void setField(CommonField field) {
    this.currentField = (PathField) field;
  }

  @Override
  public int getLives() {
    return 0;
  }

  @Override
  public ObjectType getType() {
    return ObjectType.KEY;
  }

  @Override
  public void setDirection(CommonField.Direction l) {

  }

}
