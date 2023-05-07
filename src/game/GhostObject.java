package src.game;

import src.game.resources.ObjectType;
import src.tool.common.CommonField;
import src.tool.common.CommonMazeObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GhostObject implements CommonMazeObject {
  PathField currentField;
  private CommonField.Direction direction;
  private final int imageIndex;

  /**
   * Constructor for GhostObject.
   *
   * @param field the initial field of the ghost
   */
  public GhostObject(PathField field) {
    this.currentField = field;
    this.direction = CommonField.Direction.STOP;

    // initialize imageIndex to a random number between 0 and 3
    Random rand = new Random();
    this.imageIndex = rand.nextInt(4);
  }

  /**
   * Checks if the object is a Pacman.
   *
   * @return always false
   */
  @Override
  public boolean isPacman() {
    return false;
  }

  /**
   * Sets the direction of the object.
   *
   * @param direction the direction to be set
   */
  @Override
  public void setDirection(CommonField.Direction direction) {
    this.direction = direction;
  }

  /**
   * Gets the current direction of the object.
   *
   * @return the current direction of the object
   */
  public CommonField.Direction getDirection() {
    return direction;
  }

  /**
   * Checks if the object can move to the given direction.
   *
   * @param direction the direction to be checked
   * @return true if the object can move, false otherwise
   */
  @Override
  public boolean canMove(PathField.Direction direction) {
    CommonField nextField = this.currentField.nextField(direction);
    return nextField.canMove();
  }

  /**
   * Moves the object.
   *
   * @return true if the object has moved, false otherwise
   */
  @Override
  public boolean move() {
    this.chooseDirection();
    return move(this.direction);
  }

  /**
   * Chooses a random direction for the ghost to move in.
   */
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

  /**
   * Moves the object to the given direction.
   *
   * @param direction the direction to move the object to
   * @return true if the object has moved, false otherwise
   */
  @Override
  public boolean move(CommonField.Direction direction) {
    // check if it is walkable field = PathField
    if (!this.canMove(direction)) {
      return false;
    }
    PathField moveTo = (PathField) this.currentField.nextField(direction);
    return performMove(moveTo);
  }

  /**
   * Moves the ghost to the specified field and checks for a possible collision with a pacman.
   *
   * @param moveTo the field the ghost is moving to
   * @return true if the move was successful, false otherwise
   */
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

  /**
   * Returns the current field of the ghost.
   *
   * @return the current field of the ghost
   */
  @Override
  public CommonField getField() {
    return this.currentField;
  }

  /**
   * Sets the field of the ghost to the specified field.
   *
   * @param field the field the ghost is moving to
   */
  @Override
  public void setField(CommonField field) {
    this.currentField = (PathField) field;
  }

  /**
   * Returns the number of lives of the ghost.
   *
   * @return the number of lives of the ghost
   */
  @Override
  public int getLives() {
    return 0;
  }

  /**
   * Returns the type of the object.
   *
   * @return the type of the object
   */
  @Override
  public ObjectType getType() {
    return ObjectType.GHOST;
  }

  /**
   * Returns the index of the image representing the ghost.
   *
   * @return the index of the image representing the ghost
   */
  public int getImageIndex() {
    return this.imageIndex;
  }
}
