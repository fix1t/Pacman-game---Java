package ija.ija2022.homework2.tool.tests;

import ija.ija2022.homework2.game.Game;
import ija.ija2022.homework2.game.PacmanObject;
import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMaze;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;

public class AStarTestHard {

  private CommonMaze maze;

  // test maze:
//33 65
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
//XS..........X...................................................X
//X...XXXXX...X...XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX...X...X
//X...X...X.......X...X...X...X...X.......X.......X.......X...X...X
//X...X...XXXXXXXXX...X...X...X...X...X...X...X...X...X...X...X...X
//X...X.......X.......................X.......X.......X.......X...X
//X...XXXXX...X...XXXXX...X...X...X...XXXXXXXXXXXXXXXXXXXXXXXXX...X
//X...X.......X...X...X...X...X...X...X...........X.......X...X...X
//X...X...X...X...X...XXXXX...XXXXXXXXX...XXXXXXXXX...X...X...X...X
//X...X...X.......X.......X...X.......X...............X.......X...X
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX...XXXXXXXXXXXXXXXXX...X...X
//X...................................X.......X...............X...X
//X...XXXXXXXXXXXXXXXXXXXXXXXXXXXXX...X...XXXXX...XXXXXXXXXXXXX...X
//X...X.......X...................X...X.......X...............X...X
//X...XXXXXXXXX...XXXXXXXXXXXXXXXXX...X...XXXXX...XXXXXXXXX...X...X
//X...X.......X...X...........X.......X...X...................X...X
//X...XXXXXXXXX...X...XXXXX...X...X...XXXXX...XXXXXXXXXXXXX...X...X
//X...X.......X...X...XT......X.......X...X...X...........X...X...X
//X...XXXXXXXXX...X...XXXXX...XXXXXXXXX...XXXXX...XXXXXXXXX...X...X
//X...X...X...................X...X...X...........X...........X...X
//X...X...X...XXXXXXXXXXXXXXXXX...X...XXXXXXXXX...XXXXXXXXX...X...X
//X...X...X.......................X...........X...........X...X...X
//X...XXXXX...X...XXXXXXXXXXXXXXXXX...XXXXXXXXXXXXX...X...X...X...X
//X...........X...............X...................X...X...X...X...X
//XXXXXXXXX...X...X...XXXXXXXXX...XXXXXXXXXXXXXXXXX...X...X...X...X
//X.......X...X...X...........X...............X...X.......X...X...X
//X...X...X...X...X...XXXXXXXXX...XXXXXXXXXXXXX...XXXXX...X...X...X
//X...X...........X...........X...X...X...............X.......X...X
//X...X...XXXXXXXXXXXXXXXXXXXXX...X...XXXXXXXXX...X...XXXXX...X...X
//X...X...X...............X...X.......X.......X...X.......X...X...X
//X...X...X...XXXXXXXXX...X...XXXXXXXXX...X...X...X...XXXXXXXXX...X
//X...X...................X...............X.......................X
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

  @Before
  public void setUp() {
    Game game = new Game(300, true);
    this.maze = game.createMazeFromFile(Path.of("ija/ija2022/homework2/tool/tests/maps/maze/hard"));
    //check if loaded
    if (maze == null) {
      System.out.println("Error while loading maze");
    }
    game.createGameGUI();
  }

  @Test
  public void getToTarget() {
    Assert.assertNotNull(this.maze);
    CommonField destinationField = this.maze.getTarget().getField();
    PacmanObject pacman = this.maze.getPacman();
    pacman.setGoToField(destinationField);
    for (int i = 0; i < 200; i++) {
//      Game.sleep(100);
      pacman.move();
    }
    //check if pacman is on destination field
    Assert.assertEquals(this.maze.getPacman().getField(), destinationField);
  }
}
