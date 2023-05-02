package ija.ija2022.homework2.tool.tests;

import ija.ija2022.homework2.game.MazeConfigure;
import ija.ija2022.homework2.game.PathField;
import ija.ija2022.homework2.game.resources.ObjectType;
import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMaze;
import ija.ija2022.homework2.tool.common.CommonMazeObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetObjectsTypeTest {

  private CommonMaze maze;

  /**
   * Vytvoří bludiště, nad kterým se provádějí testy.
   */
  @Before
  public void setUp() {
    MazeConfigure cfg = new MazeConfigure();
    cfg.startReading(4, 3);
    cfg.processLine(".S.");
    cfg.processLine(".T.");
    cfg.processLine(".K.");
    cfg.processLine(".G.");
    cfg.stopReading();
    maze = cfg.createMaze();
  }

  @Test
  public void Types() {
    Assert.assertNotNull("Maze neni null", maze);
    PathField field = (PathField) maze.getField(4, 2);
    CommonMazeObject ghost = field.getGhosts().get(0);
    Assert.assertNotNull("Objekt není null", ghost);
    Assert.assertEquals(ObjectType.GHOST, ghost.getType());
    field = (PathField) maze.getField(3, 2);
    CommonMazeObject key = field.getKey();
    Assert.assertNotNull("Objekt není null", key);
    Assert.assertEquals(ObjectType.KEY, key.getType());
    field = (PathField) maze.getField(2, 2);
    CommonMazeObject Target = field.getTarget();
    Assert.assertNotNull("Objekt není null", Target);
    Assert.assertEquals(ObjectType.TARGET, Target.getType());
    field = (PathField) maze.getField(1, 2);
    CommonMazeObject Pacman = field.getPacman();
    Assert.assertNotNull("Objekt není null", Pacman);
    Assert.assertEquals(ObjectType.PACMAN, Pacman.getType());
  }
}
