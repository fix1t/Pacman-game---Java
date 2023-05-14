package src.game.resources;

/**
 * Represents the types of objects in the Pacman game.
 * @author Gabriel Biel
 */
public enum ObjectType {
  PACMAN,
  GHOST,
  KEY,
  BOOST,
  TARGET;

  /**
   * Returns a string representation of the object type.
   *
   * @return a string representation of the object type
   */
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

  /**
   * Converts a string to its corresponding object type.
   *
   * @param type the string representation of the object type
   * @return the corresponding object type, or null if the string is invalid
   */
  public static ObjectType toType(String type) {
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
