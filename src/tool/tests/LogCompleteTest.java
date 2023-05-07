package src.tool.tests;

import src.game.*;
import src.tool.common.CommonMaze;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;

import static src.tool.common.CommonField.Direction.UP;
import static src.tool.tests.LogGhostPacmanOnlyTest.areFilesEqual;

public class LogCompleteTest {

  private CommonMaze maze;
  private Game game;
  private PacmanObject pacman;

  // test maze:
//5 3
//.XT
//.X.
//KXK
//.X.
//GXS
  @Before
  public void setUp() {
    this.game = new Game(100, false);
    this.maze = this.game.createMazeFromFile(Path.of("src/tool/tests/maps/valid/twoLinesWithObjects"));
    if (maze == null) {
      System.out.println("Error while loading maze");
    }
    this.pacman = maze.getPacman();
  }

  @Test
  public void testLog() {
    this.pacman.setDirection(UP);
    this.game.gameLoop(5);
    this.game.finishRecording();
    boolean areEqual = areFilesEqual("game.log", "src/tool/tests/replays/expectedLogComplete");
    Assert.assertTrue(areEqual);
  }
}
