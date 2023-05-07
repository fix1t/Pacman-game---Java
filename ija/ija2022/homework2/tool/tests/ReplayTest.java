package ija.ija2022.homework2.tool.tests;

import ija.ija2022.homework2.game.Game;
import ija.ija2022.homework2.game.GameReplay;
import ija.ija2022.homework2.game.GhostObject;
import ija.ija2022.homework2.game.PacmanObject;
import ija.ija2022.homework2.tool.common.CommonMaze;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;

public class ReplayTest {

  private CommonMaze maze;
  private Game game;
  private GameReplay replay;

  @Before
  public void setUp() {
    this.replay = new GameReplay();
    Path pathToMaze = Path.of("ija/ija2022/homework2/tool/tests/expected/expectedLog");
    boolean result = replay.loadGameFromFile(pathToMaze);
    this.maze = replay.getMaze();
    Assert.assertNotNull(this.maze);
    Assert.assertTrue(result);
  }

  @Test
  public void LoadSuccess() {
    Assert.assertNotNull(this.replay.getMaze());
    PacmanObject pacman = this.maze.getPacman();
    GhostObject ghost = (GhostObject) this.maze.getGhosts().get(0);
    Assert.assertNotNull(pacman);
    Assert.assertNotNull(ghost);

    Assert.assertEquals(this.maze.getField(5,3),pacman.getField());
    Assert.assertEquals(this.maze.getField(5,1),ghost.getField());

    Assert.assertTrue(pacman.getField().contains(pacman));
    Assert.assertTrue(ghost.getField().contains(ghost));
  }

  @Test
  public void GetRightPositionFromStart() {
    this.replay.ReplayGameFromStart();
    this.maze = this.replay.getMaze();

    PacmanObject pacman = this.maze.getPacman();
    GhostObject ghost = (GhostObject) this.maze.getGhosts().get(0);
    Assert.assertNotNull(pacman);
    Assert.assertNotNull(ghost);

    Assert.assertEquals(this.maze.getField(5,3),pacman.getField());
    Assert.assertEquals(this.maze.getField(5,1),ghost.getField());

    // First state - show the same position
    this.replay.presentPreviousState();
    Assert.assertEquals(this.maze.getField(5,3),pacman.getField());
    Assert.assertEquals(this.maze.getField(5,1),ghost.getField());
    Assert.assertTrue(pacman.getField().contains(pacman));
    Assert.assertTrue(ghost.getField().contains(ghost));

    this.replay.presentNextState();
    Assert.assertEquals(this.maze.getField(4,3),pacman.getField());
    Assert.assertEquals(this.maze.getField(4,1),ghost.getField());
    Assert.assertTrue(pacman.getField().contains(pacman));
    Assert.assertTrue(ghost.getField().contains(ghost));

    this.replay.presentNextState();
    Assert.assertEquals(this.maze.getField(3,3),pacman.getField());
    Assert.assertEquals(this.maze.getField(3,1),ghost.getField());
    Assert.assertTrue(pacman.getField().contains(pacman));
    Assert.assertTrue(ghost.getField().contains(ghost));

    this.replay.presentNextState();
    Assert.assertEquals(this.maze.getField(2,3),pacman.getField());
    Assert.assertEquals(this.maze.getField(2,1),ghost.getField());
    Assert.assertTrue(pacman.getField().contains(pacman));
    Assert.assertTrue(ghost.getField().contains(ghost));

    // Last state - show the same position
    this.replay.presentNextState();
    Assert.assertEquals(this.maze.getField(2,3),pacman.getField());
    Assert.assertEquals(this.maze.getField(2,1),ghost.getField());
    Assert.assertTrue(pacman.getField().contains(pacman));
    Assert.assertTrue(ghost.getField().contains(ghost));
  }

  @Test
  public void GetRightPositionFromEnd() {
    this.replay.ReplayGameFromEnd();
    this.maze = this.replay.getMaze();

    PacmanObject pacman = this.maze.getPacman();
    GhostObject ghost = (GhostObject) this.maze.getGhosts().get(0);
    Assert.assertNotNull(pacman);
    Assert.assertNotNull(ghost);
    Assert.assertNotNull(this.maze);



    Assert.assertEquals(this.maze.getField(2,3),pacman.getField());
    Assert.assertEquals(this.maze.getField(2,1),ghost.getField());
    Assert.assertTrue(pacman.getField().contains(pacman));
    Assert.assertTrue(ghost.getField().contains(ghost));

    // Last state - show the same position
    this.replay.presentNextState();
    Assert.assertEquals(this.maze.getField(2,3),pacman.getField());
    Assert.assertEquals(this.maze.getField(2,1),ghost.getField());
    Assert.assertTrue(pacman.getField().contains(pacman));
    Assert.assertTrue(ghost.getField().contains(ghost));

    this.replay.presentPreviousState();
    Assert.assertEquals(this.maze.getField(3,3),pacman.getField());
    Assert.assertEquals(this.maze.getField(3,1),ghost.getField());
    Assert.assertTrue(pacman.getField().contains(pacman));
    Assert.assertTrue(ghost.getField().contains(ghost));

    this.replay.presentPreviousState();
    Assert.assertEquals(this.maze.getField(4,3),pacman.getField());
    Assert.assertEquals(this.maze.getField(4,1),ghost.getField());
    Assert.assertTrue(pacman.getField().contains(pacman));
    Assert.assertTrue(ghost.getField().contains(ghost));

    this.replay.presentPreviousState();
    Assert.assertEquals(this.maze.getField(5,3),pacman.getField());
    Assert.assertEquals(this.maze.getField(5,1),ghost.getField());
    Assert.assertTrue(pacman.getField().contains(pacman));
    Assert.assertTrue(ghost.getField().contains(ghost));

    // First state - show the same position
    this.replay.presentPreviousState();
    Assert.assertEquals(this.maze.getField(5,3),pacman.getField());
    Assert.assertEquals(this.maze.getField(5,1),ghost.getField());
    Assert.assertTrue(pacman.getField().contains(pacman));
    Assert.assertTrue(ghost.getField().contains(ghost));
  }

  @Test
  public void GetRightPositionFromStart_Continuous() {
    this.replay.ReplayGameFromStart();
    this.maze = this.replay.getMaze();

    PacmanObject pacman = this.maze.getPacman();
    GhostObject ghost = (GhostObject) this.maze.getGhosts().get(0);
    Assert.assertNotNull(pacman);
    Assert.assertNotNull(ghost);

    Assert.assertEquals(this.maze.getField(5,3),pacman.getField());
    Assert.assertEquals(this.maze.getField(5,1),ghost.getField());

    this.replay.continueForward(0);
    Assert.assertEquals(this.maze.getField(2,3),pacman.getField());
    Assert.assertEquals(this.maze.getField(2,1),ghost.getField());
    Assert.assertTrue(pacman.getField().contains(pacman));
    Assert.assertTrue(ghost.getField().contains(ghost));
  }

  @Test
  public void GetRightPositionFromEnd_Continuous() {
    this.replay.ReplayGameFromEnd();
    this.maze = this.replay.getMaze();

    PacmanObject pacman = this.maze.getPacman();
    GhostObject ghost = (GhostObject) this.maze.getGhosts().get(0);
    Assert.assertNotNull(pacman);
    Assert.assertNotNull(ghost);

    Assert.assertEquals(this.maze.getField(2,3),pacman.getField());
    Assert.assertEquals(this.maze.getField(2,1),ghost.getField());

    this.replay.continueBackward(0);
    Assert.assertEquals(this.maze.getField(5,3),pacman.getField());
    Assert.assertEquals(this.maze.getField(5,1),ghost.getField());
    Assert.assertTrue(pacman.getField().contains(pacman));
    Assert.assertTrue(ghost.getField().contains(ghost));
  }


}
