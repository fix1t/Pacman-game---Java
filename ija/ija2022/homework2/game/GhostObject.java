package ija.ija2022.homework2.game;

import ija.ija2022.homework2.game.resources.ObjectType;
import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMazeObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

  @Override
  public void setDirection(CommonField.Direction direction) {
    this.direction = direction;
  }

  public CommonField.Direction getDirection() {
    return direction;
  }

  @Override
  public boolean canMove(PathField.Direction direction) {
    CommonField nextField = this.currentField.nextField(direction);
    return nextField.canMove();
  }

  @Override
  public boolean move() {
    this.chooseDirection();
    return move(this.direction);
  }

  private void chooseDirection() {
    List<CommonField.Direction> possibleDirections = new ArrayList<>();
    for (CommonField.Direction direction : CommonField.Direction.values()) {
      // skip STOP and opposite direction
      if (direction == CommonField.Direction.STOP || direction == this.direction.opposite()) {
        continue;
      }
      if (this.canMove((PathField.Direction) direction)) {
        if (this.getDirection() == direction) {
          // let ghost go straight more often
          possibleDirections.add(direction);
        }
        possibleDirections.add(direction);
      }
    }
    // if there is no possible direction, ghost will go back
    if (possibleDirections.size() == 0) {
      this.direction = this.direction.opposite();
    } else {
      // choose random direction from possible directions
      Random random = new Random();
      int randomDirection = random.nextInt(possibleDirections.size());
      this.direction = possibleDirections.get(randomDirection);
    }
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
