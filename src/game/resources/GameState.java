package src.game.resources;

/**
 * Represents the state of the Pacman game.
 */
public enum GameState {
  WIN,
  LOSE,
  TBD;

  /**
   * Retrieves the message associated with the game state.
   *
   * @return the message corresponding to the game state
   */
  public String message() {
    return switch (this) {
      case WIN -> "You won!";
      case LOSE -> "You lost!";
      default -> "PACMAN!";
    };
  }
}
