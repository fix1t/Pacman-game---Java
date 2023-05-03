package ija.ija2022.homework2.game;

import ija.ija2022.homework2.game.resources.ObjectType;
import ija.ija2022.homework2.tool.MazePresenter;
import ija.ija2022.homework2.tool.Sound;
import ija.ija2022.homework2.tool.common.CommonMaze;
import ija.ija2022.homework2.tool.common.CommonMazeObject;
import ija.ija2022.homework2.tool.tests.Homework2;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {
  CommonMaze maze;
  Sound sound = new Sound();
  //game delay in ms
  private final int tickLength;
  private final boolean pauseGhosts;

  public Game() {
    this.tickLength = 500;
    this.pauseGhosts = false;
  }

  public Game(int gameSpeed, boolean pauseGhosts) {
    this.tickLength = gameSpeed;
    this.pauseGhosts = pauseGhosts;
  }

  public static void main(String[] args) {
    Game game = new Game();
    //TODO: load maze given in argument
    Path pathToMaze = Path.of("ija/ija2022/homework2/tool/tests/maps/valid/valid0");
    if( game.play(pathToMaze))
      System.out.println("Game ended successfully");
    else
      System.out.println("Game ended with error");
  }

  private boolean play(Path pathToMaze) {
    //open stream to file & load maze
    try (InputStream inputStream = Files.newInputStream(pathToMaze)) {
      MazeConfigure mazeConfigure = new MazeConfigure();
      this.maze = mazeConfigure.loadMaze(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    //check if loaded
    if (this.maze == null) {
      System.out.println("Error while loading maze");
      return false;
    }
    //create gui
    MazePresenter presenter = new MazePresenter(this.maze);
    presenter.open();
    playMusic(0);
    //start game
    this.gameLoop();
    return true;
  }

  private void gameLoop() {
    List<CommonMazeObject> allMazeObjects = this.maze.getGhosts();
    PacmanObject pacman = (PacmanObject) this.maze.getPacman();
    allMazeObjects.add(pacman);
    do {
      this.moveAllMazeObjects(allMazeObjects);
      sleep(this.tickLength);
    } while (!pacman.isDead() && !pacman.isVictorious());
  }

  public void playMusic(int songIndex) {
    sound.setFile(songIndex);
    sound.play();
    sound.loop();
  }

  public static void sleep(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException ex) {
      Logger.getLogger(Homework2.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void moveAllMazeObjects(List<CommonMazeObject> allMazeObjects) {
    for (CommonMazeObject mazeObject : allMazeObjects) {
      if (mazeObject.getType() == ObjectType.GHOST && this.pauseGhosts)
        continue;
      mazeObject.move();
    }
  }
}
