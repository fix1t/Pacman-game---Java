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
    //check if it is walkable field = PathField
    if (!this.canMove(direction)) {
      return false;
    }
    PathField moveTo = (PathField) this.currentField.nextField(direction);
    // check if there is pacman in the field
    PacmanObject pacman = (PacmanObject) moveTo.getPacman();
    if (pacman != null){
      //check if pacman will survive or its game over
      if (pacman.ghostCollision()){
        System.out.println("GAME OVER!");
      };
    }
    //remove ghost from this field
    this.currentField.remove(this);
    //change field
    moveTo.put(this);
    this.currentField = moveTo;
    return true;
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
