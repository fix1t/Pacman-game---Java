//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.tool.common;

import src.game.resources.Coordinate;

public interface CommonField extends Observable {
  CommonField nextField(Direction var1);

  boolean isEmpty();

  CommonMazeObject get();

  boolean canMove();

  Coordinate getCoordinate();

  void clearField();

  boolean contains(CommonMazeObject var1);

  public static enum Direction {
    LEFT(0, -1),
    UP(-1, 0),
    RIGHT(0, 1),
    DOWN(1, 0),
    STOP(0, 0);

    private final int r;
    private final int c;

    private Direction(int dr, int dc) {
      this.r = dr;
      this.c = dc;
    }

    public int deltaRow() {
      return this.r;
    }

    public int deltaCol() {
      return this.c;
    }

    public Direction opposite() {
      return switch (this) {
        case LEFT -> RIGHT;
        case UP -> DOWN;
        case RIGHT -> LEFT;
        case DOWN -> UP;
        default -> STOP;
      };
    }
  }
}
