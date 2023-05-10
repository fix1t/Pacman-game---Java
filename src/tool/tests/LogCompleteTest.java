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
  }

  @Test
  public void testLog() {
    this.game.startRecording();
    this.maze = this.game.createMazeFromFile(Path.of("src/tool/tests/maps/valid/twoLinesWithObjects"));
    Assert.assertNotNull("Maze neni null", this.maze);
    this.pacman = maze.getPacman();
    Assert.assertNotNull("Objekt není null", this.pacman);
    this.pacman.setDirection(UP);
    Assert.assertEquals(UP, this.pacman.getDirection());
    this.game.gameLoop(5);
    Assert.assertEquals(1, this.pacman.getField().getCoordinate().getX());
    this.game.finishRecording();
    boolean areEqual = areFilesEqual("game.log", "src/tool/tests/replays/expectedLogComplete");
    Assert.assertTrue(areEqual);
  }
}
