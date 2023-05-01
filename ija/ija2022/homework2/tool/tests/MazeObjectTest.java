package ija.ija2022.homework2.tool.tests;

import ija.ija2022.homework2.game.MazeConfigure;
import ija.ija2022.homework2.game.PathField;
import ija.ija2022.homework2.tool.common.CommonField;
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
    cfg.processLine("..S");
    cfg.processLine(".T.");
    cfg.processLine(".K.");
    cfg.processLine(".G.");
    cfg.stopReading();
    maze = cfg.createMaze();
  }

  @Test
  public void KeyPlacement() {
    Assert.assertNotNull("Maze neni null", maze);
    CommonMazeObject object = maze.keys().get(0);
    Assert.assertNotNull("Objekt není null", object);
    Assert.assertEquals("Objekt je na spravne pozici",
      maze.getField(3, 2),
      object.getField());
    CommonField field = object.getField();
    Assert.assertTrue("Pole ma objekt", field.contains(object));
  }
  @Test
  public void TargetPlacement() {
    Assert.assertNotNull("Maze neni null", maze);
    CommonMazeObject object = maze.target();
    Assert.assertNotNull("Objekt není null", object);
    Assert.assertEquals("Objekt je na spravne pozici",
      maze.getField(2, 2),
      object.getField());
    CommonField field = object.getField();
    Assert.assertTrue("Pole ma objekt", field.contains(object));
  }
  @Test
  public void PacmanEatsKeyAndThenTarget() {
    Assert.assertNotNull("Maze neni null", maze);
    CommonMazeObject pacman = maze.pacman();
    Assert.assertNotNull("Objekt není null", pacman);
    Assert.assertEquals("Objekt je na spravne pozici",
      maze.getField(1, 3),
      pacman.getField());

    CommonMazeObject key = maze.keys().get(0);
    CommonMazeObject target = maze.target();
    // Pacman moves to the key
    Assert.assertTrue("Presun na policko se podari.", pacman.move(CommonField.Direction.L));
    // Pacman cant eat target
    Assert.assertTrue("Presun na policko se podari.", pacman.move(CommonField.Direction.D));
    Assert.assertTrue("Target je nebyl splnen", pacman.getField().contains(target));
    // Pacman eats the key
    Assert.assertTrue("Presun na policko se podari.", pacman.move(CommonField.Direction.D));
    Assert.assertTrue("Klic byl vymazan z listu.", maze.keys().isEmpty());
    Assert.assertNull("Klic byl vymazan policka.", (PathField)((PathField) pacman.getField()).getKey());
    // Pacman eats the target
    Assert.assertTrue("Presun na policko se podari.", pacman.move(CommonField.Direction.U));
    Assert.assertNull("Target byl vymazan policka.", (PathField)((PathField) pacman.getField()).getTarget());
    Assert.assertTrue("Presun na policko se podari.", pacman.move(CommonField.Direction.U));
  }
}
