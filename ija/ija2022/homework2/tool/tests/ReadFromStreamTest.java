package ija.ija2022.homework2.tool.tests;

import ija.ija2022.homework2.game.MazeConfigure;
import ija.ija2022.homework2.game.PathField;
import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMaze;
import ija.ija2022.homework2.tool.common.CommonMazeObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.InputStream;

public class ReadFromStreamTest {

  private CommonMaze maze;

  // default map
  //4 3
  //..S
  //.T.
  //.K.
  //.G.
  public void createMaze() {
    String path = "ija/ija2022/homework2/tool/tests/maps/valid/valid";
    Path filePath = Paths.get(path);
    try (InputStream inputStream = Files.newInputStream(filePath)) {
      MazeConfigure mazeConfigure = new MazeConfigure();
      maze = mazeConfigure.loadMaze(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public void createMaze(String path ) {
    Path filePath = Paths.get(path);
    try (InputStream inputStream = Files.newInputStream(filePath)) {
      MazeConfigure mazeConfigure = new MazeConfigure();
      maze = mazeConfigure.loadMaze(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void MazeCreationSuccess() {
    // relative path from tool/tests
    this.createMaze("ija/ija2022/homework2/tool/tests/maps/valid/valid1");
    Assert.assertNotNull("Maze neni null", maze);
  }

  @Test
  public void MazeCreationFail() {
    this.createMaze("ija/ija2022/homework2/tool/tests/maps/invalid/extraLine");
    Assert.assertNull("Maze neni null", maze);
    this.createMaze("ija/ija2022/homework2/tool/tests/maps/invalid/missingLine");
    Assert.assertNull("Maze neni null", maze);
  }

  @Test
  public void KeyPlacement() {
    this.createMaze();
    Assert.assertNotNull("Maze neni null", maze);
    CommonMazeObject object = maze.getKeys().get(0);
    Assert.assertNotNull("Objekt není null", object);
    Assert.assertEquals("Objekt je na spravne pozici",
      maze.getField(3, 2),
      object.getField());
    CommonField field = object.getField();
    Assert.assertTrue("Pole ma objekt", field.contains(object));
  }

  @Test
  public void TargetPlacement() {
    this.createMaze();
    Assert.assertNotNull("Maze neni null", maze);
    CommonMazeObject object = maze.getTarget();
    Assert.assertNotNull("Objekt není null", object);
    Assert.assertEquals("Objekt je na spravne pozici",
      maze.getField(2, 2),
      object.getField());
    CommonField field = object.getField();
    Assert.assertTrue("Pole ma objekt", field.contains(object));
  }

  @Test
  public void PacmanEatsKeyAndThenTarget() {
    this.createMaze();
    Assert.assertNotNull("Maze neni null", maze);
    CommonMazeObject pacman = maze.getPacman();
    Assert.assertNotNull("Objekt není null", pacman);
    Assert.assertEquals("Objekt je na spravne pozici",
      maze.getField(1, 3),
      pacman.getField());

    CommonMazeObject target = maze.getTarget();
    // Pacman moves to the key
    Assert.assertTrue("Presun na policko se podari.", pacman.move(CommonField.Direction.LEFT));
    // Pacman cant eat target
    Assert.assertTrue("Presun na policko se podari.", pacman.move(CommonField.Direction.DOWN));
    Assert.assertTrue("Target je nebyl splnen", pacman.getField().contains(target));
    // Pacman eats the key
    Assert.assertTrue("Presun na policko se podari.", pacman.move(CommonField.Direction.DOWN));
    Assert.assertTrue("Klic byl vymazan z listu.", maze.getKeys().isEmpty());
    Assert.assertNull("Klic byl vymazan policka.", (PathField)((PathField) pacman.getField()).getKey());
    // Pacman eats the target
    Assert.assertTrue("Presun na policko se podari.", pacman.move(CommonField.Direction.UP));
    Assert.assertNull("Target byl vymazan policka.", (PathField)((PathField) pacman.getField()).getTarget());
    Assert.assertTrue("Presun na policko se podari.", pacman.move(CommonField.Direction.UP));
  }
}
