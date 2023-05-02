package ija.ija2022.homework2.game;

import ija.ija2022.homework2.game.resources.ObjectType;
import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMazeObject;

public class GhostObject implements CommonMazeObject {
  PathField currentField;
  private CommonField.Direction direction;

  public GhostObject(PathField field) {
      this.currentField = field;
      this.direction = CommonField.Direction.STOP;
  }
  @Override
  public boolean isPacman() {
    return false;
  }

  public void setDirection(CommonField.Direction direction) {
    this.direction = direction;
  }

  @Override
  public boolean canMove(PathField.Direction direction) {
    CommonField nextField = this.currentField.nextField(direction);
    return nextField.canMove();
  }

  @Override
  public boolean move() {
    // check if pacman is moving
    if (this.direction == CommonField.Direction.STOP) {
      return true;
    }
    return move(this.direction);
  }

  @Override
  public boolean move(CommonField.Direction direction) {
    // check if it is walkable field = PathField
    if (!this.canMove(direction)) {
      return false;
    }
    PathField moveTo = (PathField) this.currentField.nextField(direction);
    return performMove(moveTo);
  }

  private boolean performMove(PathField moveTo) {
    // remove ghost from this field
    this.currentField.remove(this);
    // change field
    moveTo.put(this);

    // check if there is a pacman in the field
    if (moveTo.getPacman() != null) {
      // check if pacman will survive or if it's game over
      if (((PacmanObject) moveTo.getPacman()).ghostCollision()) {
        System.out.println("GAME OVER!");
      }
    }
    return true;
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
    return ObjectType.GHOST;
  }
}
