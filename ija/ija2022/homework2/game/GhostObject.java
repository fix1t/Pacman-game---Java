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
      if (this.canMove(direction)) {
      PathField field = (PathField) this.currentField.nextField(direction);
      // check if there is an object in the next field
      if(!field.put(this.currentField.get())){
        if (field.obj.isPacman()){
          PacmanObject pacman = (PacmanObject) field.obj;
          if (pacman.ghostCollision()){
            //GAME OVER
            System.out.println("GAME OVER!");
          };
        } else {
          //remember 2 ghosts
        }
      }
      //remove ghost from this field
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
