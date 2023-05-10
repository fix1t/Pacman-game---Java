package src.tool.tests;

import src.game.Game;
import src.game.PacmanObject;
import src.tool.common.CommonMaze;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static src.tool.common.CommonField.Direction.UP;

public class LogGhostPacmanOnlyTest {

  private CommonMaze maze;
  private Game game;

  // test maze:
//5 3
//.X.
//.X.
//.X.
//.X.
//GXS
  @Test
  public void testLog() {
    this.game = new Game(100, false);
    this.game.startRecording();
    this.maze = this.game.createMazeFromFile(Path.of("src/tool/tests/maps/valid/twoLines"));
    Assert.assertNotNull("Maze neni null", maze);
    PacmanObject pacman = maze.getPacman();
    Assert.assertNotNull("Objekt není null", pacman);
    pacman.setDirection(UP);
    Assert.assertEquals(UP, pacman.getDirection());
    this.game.gameLoop(5);
    Assert.assertEquals(1, pacman.getField().getCoordinate().getX());
    this.game.finishRecording();
    boolean areEqual = areFilesEqual("game.log", "src/tool/tests/replays/expectedLog");
    Assert.assertTrue(areEqual);
  }

  // compare log file with expected log file
  public static boolean areFilesEqual(String file1, String file2) {
    try {
      Path file1Path = Paths.get(file1);
      Path file2Path = Paths.get(file2);
      byte[] f1 = Files.readAllBytes(file1Path);
      byte[] f2 = Files.readAllBytes(file2Path);
      return Arrays.equals(f1, f2);
    } catch (IOException e) {
      System.out.println("Error while reading files");
      return false;
    }
  }
}
