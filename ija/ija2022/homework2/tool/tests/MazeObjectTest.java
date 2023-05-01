package ija.ija2022.homework2.tool.tests;

import ija.ija2022.homework2.game.MazeConfigure;
import ija.ija2022.homework2.tool.common.CommonMaze;
import ija.ija2022.homework2.tool.common.CommonMazeObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class MazeObjectTest {

  private CommonMaze maze;

  /**
   * Vytvoří bludiště, nad kterým se provádějí testy.
   */
  @Before
  public void setUp() {
    MazeConfigure cfg = new MazeConfigure();
    cfg.startReading(4, 3);
    cfg.processLine("..G");
    cfg.processLine(".T.");
    cfg.processLine(".K.");
    cfg.processLine(".S.");
    cfg.stopReading();
    maze = cfg.createMaze();
  }

  @Test
  public void getKey() {
    Assert.assertNotNull("Maze neni null", maze);
    CommonMazeObject object = maze.keys().get(0);
    Assert.assertNotNull("Objekt není null", object);
    Assert.assertEquals("Objekt je na spravne pozici",
      maze.getField(3, 2),
      object.getField());
  }


}
