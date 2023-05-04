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
  private Game game;

// test maze:
//9 21
//XXXXXXXXXXXXXXXXXXXXX
//XK.................KX
//X.XX.XX.XXXXX.XX.XX.X
//X.XX.XX.XXXXX.XX.XX.X
//X.........T.........X
//X.XX.XX.XXXXX.XX.XX.X
//X.XX.XX.XXXXX.XX.XX.X
//XK........S........KX
//XXXXXXXXXXXXXXXXXXXXX
  @Before
  public void setUp() {
    this.game = new Game(300, true);
    this.maze = this.game.createMazeFromFile(Path.of("ija/ija2022/homework2/tool/tests/maps/valid/valid0-ng"));
    //check if loaded
    if (maze == null) {
      System.out.println("Error while loading maze");
    }
    this.game.createGameGUI();
  }

  @Test
  public void goToTopOfTheMazeOn12() {
    Assert.assertNotNull(this.maze);
    CommonField destinationField = this.maze.getField(2, 11);
    PacmanObject pacman = this.maze.getPacman();
    pacman.setGoToField(destinationField);
    for (int i = 0; i < 12; i++) {
      Game.sleep(100);
      pacman.move();
    }
    //check if pacman is on destination field
    Assert.assertEquals(this.maze.getPacman().getField(), destinationField);
  }

  @Test
  public void pickUpAllTheKeysAndTargetSuccess() {
    Assert.assertNotNull(this.maze);
    PacmanObject pacman = this.maze.getPacman();

    // go to top right corner first
    CommonField destinationField = this.maze.getField(2, 20);
    pacman.setGoToField(destinationField);
    for (int i = 0; i < 20; i++) {
      Game.sleep(100);
      pacman.move();
    }
    // go to bottom left corner next
    destinationField = this.maze.getField(8, 2);
    pacman.setGoToField(destinationField);
    for (int i = 0; i < 30; i++) {
      Game.sleep(100);
      pacman.move();
    }
    // go to top left corner next
    destinationField = this.maze.getField(2, 2);
    pacman.setGoToField(destinationField);
    for (int i = 0; i < 20; i++) {
      Game.sleep(100);
      pacman.move();
    }
    // go to bottom right corner next
    destinationField = this.maze.getField(8, 20);
    pacman.setGoToField(destinationField);
    for (int i = 0; i < 30; i++) {
      Game.sleep(100);
      pacman.move();
    }
    //pickup target
    destinationField = this.maze.getField(5, 11);
    pacman.setGoToField(destinationField);
    for (int i = 0; i < 30; i++) {
      Game.sleep(100);
      pacman.move();
    }
    Assert.assertTrue(pacman.isVictorious());
    //check if pacman is on destination field
    Assert.assertEquals(this.maze.getPacman().getField(), destinationField);
  }
}


