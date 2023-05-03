package ija.ija2022.homework2.tool.tests;

import ija.ija2022.homework2.game.Game;
import ija.ija2022.homework2.tool.common.CommonMaze;
import org.junit.Before;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static ija.ija2022.homework2.game.Game.createMaze;

public class AStarTest {

  private CommonMaze maze;

// testing maze:
//4 3
//..S
//.T.
//.K.
//.G.
  @Before
  public void setUp() {
    Game game = new Game();
    Path pathToMaze = Path.of("ija/ija2022/homework2/tool/tests/maps/valid/valid0");
    try (InputStream inputStream = Files.newInputStream(pathToMaze)) {
      this.maze = createMaze(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
    //check if loaded
    if (maze == null) {
      System.out.println("Error while loading maze");
    }
  }
}
