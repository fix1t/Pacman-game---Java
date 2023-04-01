package ija.ija2022.homework2.game;

import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMazeObject;

public class GhostObject implements CommonMazeObject {
  PathField currentField;
    public GhostObject(PathField field) {
      this.currentField = field;
  }
  @Override
  public boolean isPacman() {
    return false;
  }
  @Override
  public boolean canMove(PathField.Direction direction) {
    CommonField nextField = this.currentField.nextField(direction);
    return nextField.canMove();
  }
  @Override
  public boolean move(CommonField.Direction direction) {
    if (this.canMove(direction)) {
      //place pacman to the next field
      PathField field = (PathField) this.currentField.nextField(direction);
      field.put(this.currentField.get());
      //remove pacman from this field
      this.currentField.remove(this.currentField.get());
      //change field
      this.currentField = (PathField) this.currentField.nextField(direction);
      return true;
    } else {
      return false;
    }
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
