package ija.ija2022.homework2.tool.tests;

import ija.ija2022.homework2.game.GhostObject;
import ija.ija2022.homework2.game.MazeConfigure;
import ija.ija2022.homework2.game.PacmanObject;
import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMaze;
import ija.ija2022.homework2.tool.common.CommonMazeObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GhostPacmanCollisionTest {

  private CommonMaze maze;

  /**
   * Vytvoří bludiště, nad kterým se provádějí testy.
   */
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
    ghost.move(CommonField.Direction.U);
    ghost.move(CommonField.Direction.U);
    ghost.move(CommonField.Direction.U);
    // collision
    ghost.move(CommonField.Direction.R);

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
    pacman.move(CommonField.Direction.D);
    pacman.move(CommonField.Direction.D);
    pacman.move(CommonField.Direction.D);
    // collision
    pacman.move(CommonField.Direction.L);
    // ghost gets relocated
    Assert.assertEquals(1, maze.getGhosts().size());
    Assert.assertEquals(ghost.getField(), maze.getField(4, 2));
    // pacman gets relocated
    Assert.assertEquals(pacman.getField(), maze.getField(1, 3));
  }
}
