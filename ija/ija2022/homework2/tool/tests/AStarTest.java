package ija.ija2022.homework2.tool.tests;

import ija.ija2022.homework2.game.MazeConfigure;
import ija.ija2022.homework2.tool.common.CommonMaze;
import org.junit.Before;

public class AStarTest {

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
}
