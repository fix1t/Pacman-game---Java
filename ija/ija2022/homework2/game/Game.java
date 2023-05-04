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

  List<CommonMazeObject> allMazeObjects;

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

  public CommonMaze createMazeFromFile (Path pathToMaze) {
    try (InputStream inputStream = Files.newInputStream(pathToMaze)) {
      this.maze = this.createMaze(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error opening file.");
      return null;
    }
    //check if loaded
    if (this.maze == null) {
      System.out.println("Error while loading maze");
      return null;
    }
    return this.maze;
  }

  public CommonMaze createMaze(InputStream inputStream) {
    MazeConfigure mazeConfigure = new MazeConfigure();
    return mazeConfigure.loadMaze(inputStream);
  }

  public void createGameGUI() {
    MazePresenter presenter = new MazePresenter(this.maze);
    presenter.open();
  }

  public boolean play(Path pathToMaze) {
    //open stream to file & load maze
    this.maze = this.createMazeFromFile(pathToMaze);
    //create gui
    this.createGameGUI();
    //play music
    playMusic(0);
    //start game
    this.gameLoop();
    return true;
  }

  public void gameLoop() {
    this.setAllMazeObjects();
    PacmanObject pacman = this.maze.getPacman();
    do {
      this.moveAllMazeObjects();
      sleep(this.tickLength);
    } while (!pacman.isDead() && !pacman.isVictorious());
  }

  public void setAllMazeObjects() {
    this.allMazeObjects = this.maze.getGhosts();
    this.allMazeObjects.add(this.maze.getPacman());
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

  private void moveAllMazeObjects() {
    for (CommonMazeObject mazeObject : this.allMazeObjects) {
      if (mazeObject.getType() == ObjectType.GHOST && this.pauseGhosts)
        continue;
      mazeObject.move();
    }
  }
}
