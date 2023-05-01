package ija.ija2022.homework2.game;

import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMazeObject;

import java.util.List;


public class PacmanObject implements CommonMazeObject {
    PathField currentField;
    List<CommonMazeObject> listOfKeys;
    int livesRemaining;
    public PacmanObject(PathField field, List<CommonMazeObject> listOfKeys) {
        this.currentField = field;
        this.livesRemaining =3;
        this.listOfKeys = listOfKeys;
    }

    @Override
    public boolean canMove(PathField.Direction direction) {
        return this.currentField.nextField(direction).canMove();
    }

    @Override
    public boolean move(CommonField.Direction direction) {
      //check if it is walkable field = PathField
      if (!this.canMove(direction)) {
        return false;
      }
      PathField moveTo = (PathField) this.currentField.nextField(direction);
      // check if there is ghost in the field
      if (!moveTo.getGhosts().isEmpty()){
        //check if pacman will survive or its game over
        if (this.ghostCollision()){
          System.out.println("GAME OVER!");
        };
      }
      //remove pacman from this field
      this.currentField.remove(this);
      //change field
      moveTo.put(this);
      this.currentField = moveTo;
      return true;
    }

  @Override
  public boolean isPacman() { return true; }

  @Override
  public PathField getField() {
    return this.currentField;
  }

  @Override
  public int getLives() {
    return this.livesRemaining;
  }

  // return true if pacman has no more lives
  public boolean ghostCollision(){
    this.livesRemaining = this.livesRemaining - 1;
    //game over
    return this.livesRemaining <= 0;
  }
}
