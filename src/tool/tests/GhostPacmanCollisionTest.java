package src.tool.tests;

import src.game.GhostObject;
import src.game.MazeConfigure;
import src.game.PacmanObject;
import src.tool.common.CommonField;
import src.tool.common.CommonMaze;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GhostPacmanCollisionTest {

  private CommonMaze maze;

  @Before
  public void setUp() {
    MazeConfigure cfg = new MazeConfigure();
    cfg.startReading(4, 3);
    cfg.processLine("..S");
    cfg.processLine(".T.");
    cfg.processLine(".K.");
    cfg.processLine(".G.");
    cfg.stopReading();
    maze = cfg.createMaze();
  }

  @Test
  public void GhostEatsPacmanLayoutAfterReset() {
    Assert.assertNotNull("Maze neni null", maze);
    GhostObject ghost = (GhostObject) maze.getGhosts().get(0);
    Assert.assertNotNull("Objekt není null", ghost);

    //move to pacman
    ghost.move(CommonField.Direction.UP);
    ghost.move(CommonField.Direction.UP);
    ghost.move(CommonField.Direction.UP);
    // collision
    ghost.move(CommonField.Direction.RIGHT);

    Assert.assertEquals(1, maze.getGhosts().size());
    Assert.assertEquals(ghost.getField(), maze.getField(4, 2));
  }

  @Test
  public void PacmanEatsGhostLayoutAfterReset() {
    Assert.assertNotNull("Maze neni null", maze);
    PacmanObject pacman = (PacmanObject) maze.getPacman();
    GhostObject ghost = (GhostObject) maze.getGhosts().get(0);
    Assert.assertNotNull("Objekt není null", pacman);

    //move to pacman
    pacman.move(CommonField.Direction.DOWN);
    pacman.move(CommonField.Direction.DOWN);
    pacman.move(CommonField.Direction.DOWN);
    // collision
    pacman.move(CommonField.Direction.LEFT);
    // ghost gets relocated
    Assert.assertEquals(1, maze.getGhosts().size());
    Assert.assertEquals(ghost.getField(), maze.getField(4, 2));
    // pacman gets relocated
    Assert.assertEquals(pacman.getField(), maze.getField(1, 3));
  }
}
