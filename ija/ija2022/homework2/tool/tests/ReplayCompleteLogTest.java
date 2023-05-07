package ija.ija2022.homework2.tool.tests;

import ija.ija2022.homework2.game.*;
import ija.ija2022.homework2.tool.common.CommonMaze;
import ija.ija2022.homework2.tool.common.CommonMazeObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.util.List;

public class ReplayCompleteLogTest {

  private CommonMaze maze;
  private Game game;
  private GameReplay replay;
  private PacmanObject pacman;
  private GhostObject ghost;
  private TargetObject target;
  private List<CommonMazeObject> keys;

  @Before
  public void setUp() {
    this.replay = new GameReplay();
    Path pathToMaze = Path.of("ija/ija2022/homework2/tool/tests/replays/expectedLog");
    boolean result = replay.loadGameFromFile(pathToMaze);
    this.maze = replay.getMaze();
    Assert.assertNotNull(this.maze);
    Assert.assertTrue(result);
    this.pacman = this.maze.getPacman();
    this.ghost = (GhostObject) this.maze.getGhosts().get(0);
    this.keys = this.maze.getKeys();
    this.target = (TargetObject) this.maze.getTarget();
  }

  @Test
  public void LoadSuccess() {
    Assert.assertNotNull(this.maze);
    Assert.assertNotNull(this.pacman);
    Assert.assertNotNull(this.ghost);
    Assert.assertNotNull(this.keys);
    Assert.assertNotNull(this.target);

    Assert.assertEquals(this.maze.getField(5, 3), this.pacman.getField());
    Assert.assertEquals(this.maze.getField(5, 1), this.ghost.getField());
    Assert.assertEquals(this.maze.getField(5, 3), this.keys.get(0).getField());
    Assert.assertEquals(this.maze.getField(5, 3), this.keys.get(0).getField());
    Assert.assertEquals(this.maze.getField(5, 1), this.target.getField());

    Assert.assertTrue(pacman.getField().contains(pacman));
    Assert.assertTrue(ghost.getField().contains(ghost));
  }
}
