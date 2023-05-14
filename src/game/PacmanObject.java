package src.game;

import src.game.resources.ObjectType;
import src.tool.common.CommonField;
import src.tool.common.CommonMazeObject;

import java.util.*;

/**
 * Represents Pacman in the game.
 * @author Gabriel Biel
 */
public class PacmanObject implements CommonMazeObject {
  private final List<CommonMazeObject> listOfBoosts;
  private PathField currentField;
  private List<CommonMazeObject> listOfKeys;
  private int livesRemaining;
  private CommonField.Direction direction;
  private boolean victory;
  private CommonField goToField;
  private int boost = 0;

  /**
   * Constructor for PacmanObject.
   *
   * @param field        the initial field of Pacman
   * @param listOfKeys   the list of keys in the maze
   * @param listOfBoosts the list of boosts in the maze
   */
  public PacmanObject(PathField field, List<CommonMazeObject> listOfKeys, List<CommonMazeObject> listOfBoosts) {
    this.currentField = field;
    this.livesRemaining = 3;
    this.listOfKeys = listOfKeys;
    this.listOfBoosts = listOfBoosts;
    this.direction = CommonField.Direction.STOP;
    this.victory = false;
    this.goToField = null;
  }

  /**
   * Sets the direction of Pacman.
   *
   * @param direction the direction to be set
   */
  @Override
  public void setDirection(CommonField.Direction direction) {
    this.direction = direction;
  }

  /**
   * Gets the current direction of Pacman.
   *
   * @return the current direction of Pacman
   */
  public CommonField.Direction getDirection() {
    return this.direction;
  }

  /**
   * Checks if Pacman can move in the given direction.
   *
   * @param direction the direction to be checked
   * @return true if Pacman can move, false otherwise
   */
  @Override
  public boolean canMove(PathField.Direction direction) {
    return this.currentField.nextField(direction).canMove();
  }

  /**
   * Moves Pacman.
   *
   * @return true if Pacman has moved, false otherwise
   */
  @Override
  public boolean move() {
    // check if Pacman is pointed by mouse click
    if (this.isGoToSet()) {
      this.direction = this.searchDirection(this.getGoToField());
    } else if (this.direction == CommonField.Direction.STOP) {
      return true;
    }
    // try to move to the field in the direction
    return move(this.direction);
  }

  /**
   * Searches for the direction to reach the specified field using the A* algorithm.
   *
   * @param goToField the field to reach
   * @return the direction to reach the field
   */
  private CommonField.Direction searchDirection(CommonField goToField) {
    // Find the shortest path to the field using the PathFinder class
    PathFinder pathFinder = new PathFinder();
    return pathFinder.findShortestPathDirection(this.currentField, goToField);
  }

  /**
   * Checks if a go-to field is set for Pacman.
   *
   * @return true if a go-to field is set, false otherwise
   */
  private boolean isGoToSet() {
    return this.getGoToField() != null;
  }

  /**
   * Sets the go-to field for Pacman.
   *
   * @param goToField the field to set as the go-to field
   */
  public void setGoToField(CommonField goToField) {
    // Set random direction to start moving when the game resets
    setDirection(CommonField.Direction.UP);
    this.goToField = goToField;
  }

  /**
   * Gets the go-to field for Pacman.
   *
   * @return the go-to field for Pacman
   */
  public CommonField getGoToField() {
    return this.goToField;
  }

  /**
   * Unsets the go-to field for Pacman.
   */
  public void unsetGoToField() {
    this.goToField = null;
  }

  /**
   * Moves Pacman in the specified direction.
   *
   * @param direction the direction to move Pacman
   * @return true if Pacman has moved, false otherwise
   */
  @Override
  public boolean move(CommonField.Direction direction) {
    // Check if it is a walkable field = PathField
    if (!this.canMove(direction)) {
      return false;
    }
    // Check if Pacman is boosted
    if (this.boost > 0)
      this.boost--;

    PathField moveTo = (PathField) this.currentField.nextField(direction);
    performMove(moveTo);
    return true;
  }

  /**
   * Performs the move of Pacman to the specified field.
   *
   * @param moveTo the field to move Pacman to
   */
  private void performMove(PathField moveTo) {
    // Take key if there is one
    if (moveTo.getKey() != null) {
      CommonMazeObject key = moveTo.getKey();
      // Remove key from field and list of keys
      this.listOfKeys.remove(key);
      key.setField(null);
      moveTo.remove(key);
      // Check if there is a target in the field and if all keys are taken
    } else if (moveTo.getTarget() != null && this.canTakeTarget()) {
      // Remove target from field if all keys are taken
      moveTo.remove(moveTo.getTarget());
      System.out.println("YOU WIN!");
      this.setVictory();
      // Check if there is a boost in the field
    } else if (moveTo.getBoost() != null) {
      CommonMazeObject boost = moveTo.getBoost();
      // Remove boost from field and list of boosts
      this.listOfBoosts.remove(boost);
      boost.setField(null);
      moveTo.remove(boost);
      // Set boost
      this.boost = 15;
    }
    // Remove Pacman from this field
    this.currentField.remove(this);
    // Change field
    moveTo.put(this);
  }

  /**
   * Sets the victory state of Pacman.
   */
  private void setVictory() {
    this.victory = true;
  }

  /**
   * Checks if Pacman is victorious.
   *
   * @return true if Pacman is victorious, false otherwise
   */
  public boolean isVictorious() {
    return this.victory;
  }

  /**
   * Checks if Pacman can take the target (all keys are collected).
   *
   * @return true if Pacman can take the target, false otherwise
   */
  private boolean canTakeTarget() {
    return this.listOfKeys.isEmpty();
  }

  /**
   * Resets the position of Pacman to the specified field.
   *
   * @param field the field to reset Pacman's position to
   */
  void resetPosition(PathField field) {
    this.currentField = field;
    this.currentField.notifyObservers();
  }

  /**
   * Checks if the object is a Pacman.
   *
   * @return always true
   */
  @Override
  public boolean isPacman() {
    return true;
  }

/**
 * Returns the current field of Pacman.
 *
 * @return the current field of Pacman
 */
  @Override
  public PathField getField() {
    return this.currentField;
  }

  /**
   * Sets the field of Pacman to the specified field.
   *
   * @param field the field to set as Pacman's current field
   */
  @Override
  public void setField(CommonField field) {
    this.currentField = (PathField) field;
  }

  /**
   * Returns the number of lives remaining for Pacman.
   *
   * @return the number of lives remaining for Pacman
   */
  @Override
  public int getLives() {
    return this.livesRemaining;
  }

  /**
   * Resets Pacman to its initial state.
   */
  public void reset() {
    this.livesRemaining = 3;
    this.victory = false;
  }

  /**
   * Returns the type of the object.
   *
   * @return the type of the object
   */
  @Override
  public ObjectType getType() {
    return ObjectType.PACMAN;
  }

  /**
   * Handles the collision of Pacman with a ghost.
   * Decreases one life, stops the movement, and restores the maze to its initial state.
   */
  public void ghostCollision() {
    // Remove one life
    this.livesRemaining = this.livesRemaining - 1;
    // Set direction to STOP
    this.direction = CommonField.Direction.STOP;
    // Restore the whole maze to its initial state
    this.currentField.getMaze().restore();
  }

  /**
   * Checks if Pacman is caught by a ghost.
   *
   * @return true if Pacman is caught by a ghost, false otherwise
   */
  public boolean isCaughtByGhost() {
    return !this.getField().getGhosts().isEmpty();
  }

  /**
   * Checks if Pacman is dead (no more lives remaining).
   *
   * @return true if Pacman is dead, false otherwise
   */
  public boolean isDead() {
    return this.livesRemaining <= 0;
  }

  /**
   * Checks if Pacman has a boost.
   *
   * @return true if Pacman has a boost, false otherwise
   */
  public boolean hasBoost() {
    return this.boost > 0;
  }

  /**
   * Sets the boost duration for Pacman.
   *
   * @param boostForXMoves the duration of the boost in number of moves
   */
  public void setBoost(int boostForXMoves) {
    this.boost = boostForXMoves;
  }
}

