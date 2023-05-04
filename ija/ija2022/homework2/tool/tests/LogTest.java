package ija.ija2022.homework2.tool.tests;

import ija.ija2022.homework2.game.Game;
import ija.ija2022.homework2.game.PacmanObject;
import ija.ija2022.homework2.tool.common.CommonMaze;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

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
    this.game = new Game(100, false);
    this.maze = this.game.createMazeFromFile(Path.of("ija/ija2022/homework2/tool/tests/maps/valid/twoLines"));
    if (maze == null) {
      System.out.println("Error while loading maze");
    }
  }

  @Test
  public void testLog() {
    PacmanObject pacman = maze.getPacman();
    pacman.setDirection(UP);
    game.gameLoop(4);
    this.game.finishRecording();
    boolean isEqual = areFilesEqual("game.log", "ija/ija2022/homework2/tool/tests/expected/expectedLog");
    Assert.assertTrue(isEqual);
  }

  // compare log file with expected log file
  public static boolean areFilesEqual(String file1, String file2)  {
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
