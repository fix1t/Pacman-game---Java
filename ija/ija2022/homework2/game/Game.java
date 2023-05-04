package ija.ija2022.homework2.game;

import ija.ija2022.homework2.game.resources.ObjectType;
import ija.ija2022.homework2.tool.MazePresenter;
import ija.ija2022.homework2.tool.Sound;
import ija.ija2022.homework2.tool.common.CommonMaze;
import ija.ija2022.homework2.tool.common.CommonMazeObject;
import ija.ija2022.homework2.tool.tests.Homework2;

import javax.swing.*;
import java.awt.*;
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

  /**
   * Creates a new game with default settings.
   */
  public Game() {
    this.tickLength = 500;
    this.pauseGhosts = false;
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
  }

  /**
   * Runs the game with the maze specified in the command-line argument.
   *
   * @param args  command-line arguments (not used).
   */
  public static void main(String[] args) {
    Game game = new Game();
    //TODO: load maze given in argument
    Path pathToMaze = Path.of("ija/ija2022/homework2/tool/tests/maps/valid/valid0");
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
  public void createGameGUI() {
    this.frame = new JFrame("Pacman Demo");
    this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.frame.setSize(350, 400);
    this.frame.setPreferredSize(new Dimension(650, 700));
//    ImageIcon soundIcon = new ImageIcon(getClass().getResource("../tool/lib/iconSound.png"));
//    JLabel iconLabel = new JLabel(soundIcon);
//    frame.add(new JPanel(), BorderLayout.WEST);
    MazePresenter presenter = new MazePresenter(this.maze, this.frame, this.sound);
    presenter.open();
  }

  /**
   * Plays the game with the specified maze file.
   *
   * @param pathToMaze    the path to the maze file.
   * @return              `true` if the game ended successfully, `false` otherwise.
   */
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

  /**
   * Runs the main game loop until Pacman wins or dies.
   */
  public void gameLoop() {
    this.setAllMazeObjects();
    PacmanObject pacman = this.maze.getPacman();
    do {
      this.moveAllMazeObjects();
      sleep(this.tickLength);
    } while (!pacman.isDead() && !pacman.isVictorious());
  }

  /**
   * Sets the list of all `CommonMazeObject` instances in the maze.
   */
  public void setAllMazeObjects() {
    this.allMazeObjects = this.maze.getGhosts();
    this.allMazeObjects.add(this.maze.getPacman());
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
  private void moveAllMazeObjects() {
    for (CommonMazeObject mazeObject : this.allMazeObjects) {
      if (mazeObject.getType() == ObjectType.GHOST && this.pauseGhosts)
        continue;
      mazeObject.move();
    }
  }
}
