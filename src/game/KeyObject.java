package src.game;

import src.game.resources.ObjectType;
import src.tool.common.CommonField;
import src.tool.common.CommonMazeObject;

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
