package ija.ija2022.homework2.game;

import ija.ija2022.homework2.game.resources.GameState;
import ija.ija2022.homework2.game.resources.ObjectType;
import ija.ija2022.homework2.tool.*;
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
import java.util.Objects;
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
  private Path mazeFile;
  private GameState gameResult = GameState.TBD;

  /**
   * Creates a new game with default settings.
   */
  public Game() {
    this.tickLength = 500;
    this.pauseGhosts = false;
    this.recorder = null;

    this.mazeFile = Path.of("ija/ija2022/homework2/game/resources/maps/maze0");
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
    this.recorder = null;
    this.mazeFile = Path.of("ija/ija2022/homework2/game/resources/maps/maze0");
  }

  /**
   * Runs the game with the maze specified in the command-line argument.
   *
   * @param args  command-line arguments (not used).
   */
  public static void main(String[] args) {
    Game game = new Game();
    game.goToMenu();
    game.closeFrame();
  }

  private void goToMenu() {
    //MAIN MENU
    playMusic(0);
    MazeMenu menuPresenter = this.createMenuPresenter();
    while (!menuPresenter.menuElementPressed()){
      sleep(500);
    }
    this.frame.dispose();

    switch (menuPresenter.flagEnabled()) {
      case "gameFlag" -> this.play();
      case "mapFlag" -> this.mapMenu();
      case "replayFlag" -> this.runReplay();
      case "exitFlag" -> System.exit(0);
      default -> System.out.println("Unknown flag");
    }
    this.frame.dispose();
    // show menu again
    goToMenu();
  }

  private void mapMenu() {
    //MAP MENU
    MapMenu mapMenuPresenter = this.createMapMenuPresenter();
    while (mapMenuPresenter.mapSelected() == 0){
      sleep(500);
      System.out.println("Waiting for map selection...");
    }
    System.out.println("GOT OUT...");

  }

  private MapMenu createMapMenuPresenter() {
    this.createFrame();
    MapMenu mapMenuPresenter = new MapMenu(this.frame, this.sound);
    mapMenuPresenter.open();
    return mapMenuPresenter;
  }

  private void prepareMaze() {
    this.maze = this.createMazeFromFile(this.mazeFile);
  }

  private MazeMenu createMenuPresenter() {
    this.createFrame();
    System.out.println("Presenting menu...");
    MazeMenu menuPresenter = new MazeMenu(this.frame, this.sound, this.gameResult);
    menuPresenter.open();
    return menuPresenter;
  }

  private void createGamePresenter() {
    this.createFrame();
    System.out.println("Starting the game...");
    MazePresenter presenter = new MazePresenter(this.maze, this.frame, this.sound);
    presenter.open();
  }

  private MazeReplay createReplayPresenter(GameReplay replay) {
    this.createFrame();
    System.out.println("Replaying the game...");
    MazeReplay replayPresenter = new MazeReplay(replay.getMaze(), this.frame, this.sound, replay);
    replayPresenter.open();
    return replayPresenter;
  }

  private void runReplay() {
    //start replay thread
    GameReplay replay = new GameReplay();
    Thread myThread = new Thread(replay);
    myThread.start();

    //load game
    Path pathToReplay = Path.of("game.log");
    replay.loadGameFromFile(pathToReplay);
    replay.ReplayGameFromStart();

    //create replay presenter
    MazeReplay replayPresenter = this.createReplayPresenter(replay);

    //wait for replay to end
    while (!replayPresenter.replayEnded()) {
      sleep(500);
    }

    //stop replay thread
    replay.stop();
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
   * Plays the game with the specified maze file.
   */
  public void play() {
    this.recorder = new GameRecorder();

    this.prepareMaze();
    this.createGamePresenter();
    this.gameLoop();
  }

  /**
   * Runs the main game loop until Pacman wins or dies.
   */
  public void gameLoop() {
    this.setAllMazeObjects();
    //Capture initial state
    this.recorder.captureState(this.allMazeObjects, true);
    PacmanObject pacman = this.maze.getPacman();

    //Run game loop
    do {
      this.recorder.captureState(this.allMazeObjects, true);
      this.moveAllMazeObjects();
      sleep(this.tickLength);
    } while (!pacman.isDead() && !pacman.isVictorious());

    //Capture final state
    this.recorder.captureState(this.allMazeObjects, true);

    if (pacman.isVictorious())
      this.gameResult = GameState.WIN;
    else
      this.gameResult = GameState.LOSE;
  }

  public void gameLoop(int numberOfTicks) {
    this.setAllMazeObjects();
    PacmanObject pacman = this.maze.getPacman();
    for (int i = 0; i < numberOfTicks; i++) {
      this.recorder.captureState(this.allMazeObjects,true);
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
    this.allMazeObjects.removeIf(Objects::isNull);
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
    this.recorder.stopRecording();
  }
  public void createFrame() {
    this.frame = new JFrame("PAC-HAM");
    this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.frame.setSize(350, 400);
    this.frame.setPreferredSize(new Dimension(650, 700));
  }

  public void setMazeFile(int index) {
    if (index < 0 || index > 5) {
      System.out.println("Invalid maze index");
      index = 0;
    }
    String path = "ija/ija2022/homework2/game/resources/maps/maze" + index;
    this.mazeFile = Path.of(path);
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
