package src.game;

import src.game.resources.ObjectType;
import src.tool.common.CommonField;
import src.tool.common.CommonMazeObject;

public class TargetObject implements CommonMazeObject {
  PathField currentField;

  /**
   * Constructor for the TargetObject class.
   *
   * @param pathField the initial field of the target
   */
  public TargetObject(PathField pathField) {
    this.currentField = pathField;
  }

  @Override
  public boolean canMove(CommonField.Direction direction) {
    return false;
  }

  @Override
  public boolean move() {
    return false;
  }

  @Override
  public boolean move(CommonField.Direction direction) {
    return false;
  }

  @Override
  public boolean isPacman() {
    return false;
  }

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
    return ObjectType.TARGET;
  }

  @Override
  public void setDirection(CommonField.Direction direction) {
    // No action needed for the target object
  }
}
