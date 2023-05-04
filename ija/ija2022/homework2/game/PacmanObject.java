package ija.ija2022.homework2.game;

import ija.ija2022.homework2.game.resources.ObjectType;
import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMazeObject;

import java.util.*;


public class PacmanObject implements CommonMazeObject {
  private PathField currentField;
  private List<CommonMazeObject> listOfKeys;
  private int livesRemaining;
  private CommonField.Direction direction;
  private boolean victory;
  private CommonField goToField;

  public PacmanObject(PathField field, List<CommonMazeObject> listOfKeys) {
    this.currentField = field;
    this.livesRemaining = 3;
    this.listOfKeys = listOfKeys;
    this.direction = CommonField.Direction.STOP;
    this.victory = false;
    this.goToField = null;
  }

  @Override
  public void setDirection(CommonField.Direction direction) {
    this.direction = direction;
  }

  public CommonField.Direction getDirection() {
    return this.direction;
  }

  @Override
  public boolean canMove(PathField.Direction direction) {
    return this.currentField.nextField(direction).canMove();
  }

  @Override
  public boolean move() {
    // check if pacman is pointed by mouse click
    if (this.isGoToSet()) {
      this.direction = this.searchDirection(this.getGoToField());
    } else if
    (this.direction == CommonField.Direction.STOP) {
      return true;
    }
    // try to move to the field in the direction
    return move(this.direction);
  }

  private CommonField.Direction searchDirection(CommonField goToField) {
    // find shortest path to the field using class PathFinder
    PathFinder pathFinder = new PathFinder();
    return pathFinder.findShortestPathDirection(this.currentField, goToField);
  }

  private boolean isGoToSet() {
    return this.getGoToField() != null;
  }

  public void setGoToField(CommonField goToField) {
    this.goToField = goToField;
  }

  public CommonField getGoToField() {
    return this.goToField;
  }

  public void unsetGoToField() {
    this.goToField = null;
  }

  @Override
    public boolean move(CommonField.Direction direction) {
      // check if it is walkable field = PathField
      if (!this.canMove(direction)) {
        return false;
      }
      PathField moveTo = (PathField) this.currentField.nextField(direction);
      performMove(moveTo);
      return true;
    }

    private void performMove(PathField moveTo) {
      // take key if there is one
      if (moveTo.getKey() != null) {
        // remove key from field and list of keys
        this.listOfKeys.remove(moveTo.getKey());
        moveTo.remove(moveTo.getKey());
        // check if there is a target in the field and if all keys are taken
      } else if (moveTo.getTarget() != null && this.canTakeTarget()) {
        // remove target from field if all keys are taken
        moveTo.remove(moveTo.getTarget());
        // TODO: RESOLVE GAME OVER
        System.out.println("GAME OVER!");
        this.setVictory();
      }
      // remove pacman from this field
      this.currentField.remove(this);
      // change field
      moveTo.put(this);

      // check if there is a ghost in the field
      if (!moveTo.getGhosts().isEmpty()) {
        // check if pacman will survive or if it's game over
        if (this.ghostCollision()) {
          System.out.println("GAME OVER!");
        }
      }
    }

  private void setVictory() {
      this.victory = true;
  }

  public boolean isVictorious() {
      return this.victory;
  }

  private boolean canTakeTarget() {
      return this.listOfKeys.isEmpty();
    }

    void resetPosition(PathField field){
      this.currentField = field;
      this.currentField.notifyObservers();
    }
    @Override
    public boolean isPacman() { return true; }

    @Override
    public PathField getField() {
      return this.currentField;
    }

    @Override
    public void setField(CommonField field) {
      this.currentField = (PathField) field;
    }

    @Override
    public int getLives() {
      return this.livesRemaining;
    }

    @Override
    public ObjectType getType() {
      return ObjectType.PACMAN;
    }

    // return true if pacman has no more lives
    public boolean ghostCollision(){
      // remove one life
      this.livesRemaining = this.livesRemaining - 1;
      if (this.livesRemaining > 0){
        // restore the whole maze to its initial state
        this.currentField.getMaze().restore();
      }
      //game over
      return this.livesRemaining <= 0;
  }

  public boolean isDead() {
    return this.livesRemaining <= 0;
  }
}
