package ija.ija2022.homework2.tool.tests;

import ija.ija2022.homework2.game.Game;
import ija.ija2022.homework2.game.PacmanObject;
import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMaze;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;

public class AStarTestAdvanced {

  private CommonMaze maze;

// test maze:
//9 21
//  XXXXXXXXXXXXXXXXXXXXX
//  XK..G...........G..KX
//  X.XX.XX.XXXXX.XX.XX.X
//  X.XX.XX.XXXXX.XX.XX.X
//  X.........T.........X
//  X.XX.XX.XXXXX.XX.XX.X
//  X.XX.XX.XXXXX.XX.XX.X
//  XK........S........KX
//  XXXXXXXXXXXXXXXXXXXXX
  @Before
  public void setUp() {
    Game game = new Game(300, true);
    this.maze = game.createMazeFromFile(Path.of("ija/ija2022/homework2/tool/tests/maps/valid/valid"));
    //check if loaded
    if (maze == null) {
      System.out.println("Error while loading maze");
    }
  }

  @Test
  public void moveOneLeft() {
    Assert.assertNotNull(this.maze);
    CommonField destinationField = this.maze.getField(1, 2);
    PacmanObject pacman = this.maze.getPacman();
    pacman.setGoToField(destinationField);
    pacman.move();
    //check if pacman is on destination field
    Assert.assertEquals(this.maze.getPacman().getField(), destinationField);
  }

  @Test
  public void moveTwoLeft() {
    Assert.assertNotNull(this.maze);
    CommonField destinationField = this.maze.getField(1, 1);
    PacmanObject pacman = this.maze.getPacman();
    pacman.setGoToField(destinationField);
    pacman.move();
    pacman.move();
    //check if pacman is on destination field
    Assert.assertEquals(this.maze.getPacman().getField(), destinationField);
  }

  @Test
  public void MoveAcrossMap() {
    Assert.assertNotNull(this.maze);
    CommonField destinationField = this.maze.getField(1, 1);
    PacmanObject pacman = this.maze.getPacman();
    pacman.setGoToField(destinationField);
    pacman.move();
    pacman.move();
    pacman.move();
    pacman.move();
    pacman.move();
    //check if pacman is on destination field
    Assert.assertEquals(this.maze.getPacman().getField(), destinationField);
  }
}
