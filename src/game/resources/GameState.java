package src.game.resources;

public enum GameState {
  WIN,
  LOSE,
  TBD;

  public String message() {
    return switch (this) {
      case WIN -> "You won!";
      case LOSE -> "You lost!";
      default -> "PACMAN!";
    };
  }
}
