package ija.ija2022.homework2.game.resources;

public enum ObjectType {
  PACMAN,
  GHOST,
  KEY,
  TARGET;

  @Override
  public String toString() {
    return switch (this) {
      case PACMAN -> "PACMAN";
      case GHOST -> "GHOST";
      case KEY -> "KEY";
      case TARGET -> "TARGET";
      default -> "UNKNOWN";
    };
  }
  public static ObjectType toType( String type ) {
    return switch (type) {
      case "PACMAN" -> PACMAN;
      case "GHOST" -> GHOST;
      case "KEY" -> KEY;
      case "TARGET" -> TARGET;
      default -> null;
    };
  }
}
