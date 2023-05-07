package src.tool.tests;

import src.game.*;
import src.tool.common.CommonMaze;
import src.tool.common.CommonMazeObject;
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
    Path pathToMaze = Path.of("src/tool/tests/replays/expectedLogComplete");
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
    Assert.assertEquals(this.maze.getField(3, 3), this.keys.get(1).getField());
    Assert.assertEquals(this.maze.getField(3, 1), this.keys.get(0).getField());
    Assert.assertEquals(this.maze.getField(1, 3), this.target.getField());

    Assert.assertTrue(this.pacman.getField().contains(this.pacman));
    Assert.assertTrue(this.ghost.getField().contains(this.ghost));
    Assert.assertTrue(this.keys.get(0).getField().contains(this.keys.get(0)));
    Assert.assertTrue(this.keys.get(1).getField().contains(this.keys.get(1)));
    Assert.assertTrue(this.target.getField().contains(this.target));
  }

  @Test
  public void PacmanEatsKeyAndTarget() {
    Assert.assertNotNull(this.maze);
    Assert.assertNotNull(this.pacman);
    Assert.assertNotNull(this.ghost);
    Assert.assertNotNull(this.keys);
    Assert.assertNotNull(this.target);

    replay.ReplayGameFromStart();
    replay.continueForward(200);
    Assert.assertTrue(this.maze.getField(3, 1).contains(this.keys.get(0)));
    Assert.assertFalse(this.maze.getField(3, 3).contains(this.keys.get(1)));
    Assert.assertFalse(this.maze.getField(1, 3).contains(this.target));

    replay.continueBackward(200);
    Assert.assertEquals(this.maze.getField(5, 3), this.pacman.getField());
    Assert.assertEquals(this.maze.getField(5, 1), this.ghost.getField());
    Assert.assertEquals(this.maze.getField(3, 3), this.keys.get(1).getField());
    Assert.assertEquals(this.maze.getField(3, 1), this.keys.get(0).getField());
    Assert.assertEquals(this.maze.getField(1, 3), this.target.getField());

    Assert.assertTrue(this.pacman.getField().contains(this.pacman));
    Assert.assertTrue(this.ghost.getField().contains(this.ghost));
    Assert.assertTrue(this.keys.get(0).getField().contains(this.keys.get(0)));
    Assert.assertTrue(this.keys.get(1).getField().contains(this.keys.get(1)));
    Assert.assertTrue(this.target.getField().contains(this.target));
  }
}