package ija.ija2022.homework2.game;

import ija.ija2022.homework2.game.resources.ObjectType;
import ija.ija2022.homework2.tool.MazeMenu;
import ija.ija2022.homework2.tool.MazePresenter;
import ija.ija2022.homework2.tool.MazeReplay;
import ija.ija2022.homework2.tool.Sound;
import ija.ija2022.homework2.tool.common.CommonMaze;
import ija.ija2022.homework2.tool.common.CommonMazeObject;
import ija.ija2022.homework2.tool.tests.Homework2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The `Game` class represents a game of Pacman.
 */
public class Game {
  CommonMaze maze;
  Sound sound = new Sound();
  //game delay in ms
  private final int tickLength;
  private final boolean pauseGhosts;

  List<CommonMazeObject> allMazeObjects;
  JFrame frame;

  GameRecorder recorder;

  /**
   * Creates a new game with default settings.
   */
  public Game() {
    this.tickLength = 500;
    this.pauseGhosts = false;
    this.recorder = new GameRecorder();
  }

  /**
   * Creates a new game with the specified game speed and ghost pause setting.
   *
   * @param gameSpeed    the delay between game ticks in milliseconds.
   * @param pauseGhosts  whether the ghosts should be paused during the game.
   */
  public Game(int gameSpeed, boolean pauseGhosts) {
    this.tickLength = gameSpeed;
    this.pauseGhosts = pauseGhosts;
    this.recorder = new GameRecorder();
  }

  /**
   * Runs the game with the maze specified in the command-line argument.
   *
   * @param args  command-line arguments (not used).
   */
  public static void main(String[] args) {
    Game game = new Game();
    //TODO: load maze given in argument
    Path pathToMaze = Path.of("ija/ija2022/homework2/tool/tests/maps/valid/valid");
    if (game.play(pathToMaze))
      System.out.println("Game ended successfully");
    else
      System.out.println("Game ended with error");
  }

  /**
   * Creates a `CommonMaze` object from the specified file.
   *
   * @param pathToMaze    the path to the maze file.
   * @return              the `CommonMaze` object representing the maze.
   */
  public CommonMaze createMazeFromFile(Path pathToMaze) {
    //record maze
    this.recorder.recordMaze(pathToMaze);
    //load maze
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

  /**
   * Creates a `CommonMaze` object from the specified input stream.
   *
   * @param inputStream   the input stream to read the maze from.
   * @return              the `CommonMaze` object representing the maze.
   */
  public CommonMaze createMaze(InputStream inputStream) {
    MazeConfigure mazeConfigure = new MazeConfigure();
    return mazeConfigure.loadMaze(inputStream);
  }

  /**
   * Creates the graphical user interface for the game.
   */
  public boolean createGameGUI(String gameStatus) {
    this.createFrame(gameStatus);
    MazeMenu menuPresenter = new MazeMenu(this.frame, this.sound, gameStatus);
    menuPresenter.open();
    while (!menuPresenter.menuElementPressed()){
      //System.out.println("Waiting for game to start");
      sleep(500);
    }
    this.frame.dispose();
    this.createFrame("Pacman");
    switch (menuPresenter.flagEnabled()) {
      case "gameFlag":
        System.out.println("Starting the game...");
        MazePresenter presenter = new MazePresenter(this.maze, this.frame, this.sound);
        presenter.open();
        break;
      case "replayFlag":
        GameReplay replay = new GameReplay();
        Path pathToReplay = Path.of("game.log");
        replay.loadGameFromFile(pathToReplay);
        CommonMaze replayMaze = replay.getMaze();
        MazeReplay replayPresenter = new MazeReplay(replayMaze, this.frame, this.sound, replay);
        replay.ReplayGameFromStart();
        replayPresenter.open();
        while (!replayPresenter.replayEnded()){
          sleep(500);
        }
        break;
      case "exitFlag":
        System.out.println("Exiting...");
        return true;
      default:
        System.out.println("Invalid option");
        break;
    }
    return false;
  }

  /**
   * Plays the game with the specified maze file.
   *
   * @param pathToMaze    the path to the maze file.
   * @return              `true` if the game ended successfully, `false` otherwise.
   */
  public boolean play(Path pathToMaze) {
    this.maze = this.createMazeFromFile(pathToMaze);
    playMusic(0);
    boolean gameExit = this.createGameGUI("PACMAN");
    if(gameExit){
      this.stopMusic();
      this.closeFrame();
      return true;
    }

    //start game
    boolean result = this.gameLoop();
    this.finishRecording();
    this.frame.dispose();

    while(!gameExit) {
      // trigger WON/LOSE screen depending on game result
      if (result) gameExit = this.createGameGUI("YOU WON!");
      else gameExit = this.createGameGUI("GAME OVER");

      result = this.gameLoop();
      this.finishRecording();
      this.frame.dispose();
    }

    this.stopMusic();
    this.closeFrame();
    return true;
  }

  /**
   * Runs the main game loop until Pacman wins or dies.
   */
  public boolean gameLoop() {
    this.setAllMazeObjects();
    PacmanObject pacman = this.maze.getPacman();
    do {
      recorder.captureState(this.allMazeObjects,true);
      this.moveAllMazeObjects();
      sleep(this.tickLength);
    } while (!pacman.isDead() && !pacman.isVictorious());
    if (pacman.isDead()) return false;
    return pacman.isVictorious();
  }

  public void gameLoop(int numberOfTicks) {
    this.setAllMazeObjects();
    PacmanObject pacman = this.maze.getPacman();
    for (int i = 0; i < numberOfTicks; i++) {
      recorder.captureState(this.allMazeObjects,true);
      this.moveAllMazeObjects();
      sleep(this.tickLength);
    }
  }

    /**
   * Sets the list of all `CommonMazeObject` instances in the maze.
   */
  public void setAllMazeObjects() {
    this.allMazeObjects = this.maze.getGhosts();
    this.allMazeObjects.add(this.maze.getPacman());
    this.allMazeObjects.add(this.maze.getTarget());
    this.allMazeObjects.addAll(this.maze.getKeys());
    this.allMazeObjects.removeIf(item -> item == null);
  }

  /**
   * Starts playing music with the specified song index.
   *
   * @param songIndex     the index of the song to play.
   */
  public void playMusic(int songIndex) {
    sound.setFile(songIndex);
    sound.play();
    sound.loop();
  }

  /**
   * Stops playing music.
   */
  public void stopMusic() {
    sound.stop();
  }

  public void finishRecording() {
    this.recorder.closeWriter();
  }

  public void createFrame(String gameStatus) {
    this.frame = new JFrame(gameStatus);
    this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.frame.setSize(350, 400);
    this.frame.setPreferredSize(new Dimension(650, 700));
  }

  /**
   * Close game window.
   */
  public void closeFrame() {
    this.frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
  }

  /**
   * Sleeps the current thread for the specified number of milliseconds.
   *
   * @param ms    the number of milliseconds to sleep.
   */
  public static void sleep(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException ex) {
      Logger.getLogger(Homework2.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Moves all `CommonMazeObject` instances in the maze.
   */
  public void moveAllMazeObjects() {
    for (CommonMazeObject mazeObject : this.allMazeObjects) {
      if (mazeObject.getType() == ObjectType.GHOST && this.pauseGhosts)
        continue;
      mazeObject.move();
    }
  }
}
