package ija.ija2022.homework2.tool.tests;

import ija.ija2022.homework2.game.Game;
import ija.ija2022.homework2.game.PacmanObject;
import ija.ija2022.homework2.tool.common.CommonMaze;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;

import static ija.ija2022.homework2.tool.common.CommonField.Direction.UP;

public class LogTest {

  private CommonMaze maze;
  private Game game;

// test maze:
//5 3
//.X.
//.X.
//.X.
//.X.
//GXS
  @Before
  public void setUp() {
    this.game = new Game(300, true);
    this.maze = this.game.createMazeFromFile(Path.of("ija/ija2022/homework2/tool/tests/maps/valid/twoLines"));
    if (maze == null) {
      System.out.println("Error while loading maze");
    }
  }

  @Test
  public void testLog() {
    PacmanObject pacman = maze.getPacman();
    pacman.setDirection(UP);

  }
}
