package src.game.resources;

public enum ObjectType {
  PACMAN,
  GHOST,
  KEY,
  BOOST,
  TARGET;

  @Override
  public String toString() {
    return switch (this) {
      case PACMAN -> "PACMAN";
      case GHOST -> "GHOST";
      case KEY -> "KEY";
      case TARGET -> "TARGET";
      case BOOST -> "BOOST";
      default -> "UNKNOWN";
    };
  }
  public static ObjectType toType( String type ) {
    return switch (type) {
      case "PACMAN" -> PACMAN;
      case "GHOST" -> GHOST;
      case "KEY" -> KEY;
      case "TARGET" -> TARGET;
      case "BOOST" -> BOOST;
      default -> null;
    };
  }
}
