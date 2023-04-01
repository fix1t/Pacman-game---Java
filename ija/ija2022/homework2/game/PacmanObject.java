package ija.ija2022.homework2.game;

import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMazeObject;

public class PacmanObject implements CommonMazeObject {
    CommonField currentField;
    public PacmanObject(CommonField field) {
        this.currentField = field;
    }

    @Override
    public boolean canMove(CommonField.Direction direction) {
        return this.currentField.nextField(direction).canMove();
    }

    @Override
    public boolean move(CommonField.Direction direction) {
        if (this.canMove(direction)) {
            //place pacman to the next field
            this.currentField.nextField(direction).put(this.currentField.get());
            //remove pacman from this field
            this.currentField.remove(this.currentField.get());
            //change field
            this.currentField = this.currentField.nextField(direction);
            return true;
        } else {
            return false;
        }
    }

  @Override
  public boolean isPacman() {
    return CommonMazeObject.super.isPacman();
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
