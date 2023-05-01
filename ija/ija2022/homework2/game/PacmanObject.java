package ija.ija2022.homework2.game;

import ija.ija2022.homework2.tool.common.CommonMazeObject;


public class PacmanObject implements CommonMazeObject {
    PathField currentField;
    int livesRemaining;
    public PacmanObject(PathField field) {
        this.currentField = field;
        this.livesRemaining =3;
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
    return this.livesRemaining;
  }

  public boolean ghostCollision(){
      this.livesRemaining = this.livesRemaining - 1;
    //game over
    return this.livesRemaining <= 0;
  }
}
