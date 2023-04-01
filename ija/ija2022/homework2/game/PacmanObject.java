package ija.ija2022.homework2.game;

import ija.ija2022.homework2.tool.common.CommonMazeObject;
import ija.ija2022.homework2.game.PathField;

import java.nio.file.Path;

public class PacmanObject implements CommonMazeObject {
    PathField currentField;
    public PacmanObject(PathField field) {
        this.currentField = field;
    }

    @Override
    public boolean canMove(PathField.Direction direction) {
        return this.currentField.nextField(direction).canMove();
    }

    @Override
    public boolean move(PathField.Direction direction) {
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
  public boolean isPacman() { return true; }

  @Override
  public PathField getField() {
    return null;
  }

  @Override
  public int getLives() {
    return 0;
  }
}
