package src.game;
import src.game.resources.ObjectType;
import src.tool.common.CommonField;
import src.tool.common.CommonMaze;
import src.tool.common.CommonMazeObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static src.game.Game.sleep;

/**
 * Represents a Pacman game replay.
 * Replays the states of each maze object at each move.
 * @author Gabriel Biel
 */
public class GameReplay implements Runnable {

  /**
   * Represents a list of pairs.
   *
   * @param <A> the type of the first element in the pair
   * @param <B> the type of the second element in the pair
   */
  public class PairList<A, B> {
    /**
     * The list of pairs.
     */
    private List<Pair<A, B>> pairList;

    /**
     * Creates a new PairList instance.
     */
    public PairList() {
      pairList = new ArrayList<>();
    }

    /**
     * Gets the size of the pair list.
     *
     * @return the size of the pair list
     */
    public int listSize() {
      return pairList.size();
    }

    /**
     * Adds a pair to the list.
     *
     * @param first  the first element of the pair
     * @param second the second element of the pair
     */
    public void addPair(A first, B second) {
      pairList.add(new Pair<>(first, second));
    }

    /**
     * Checks if the list contains a specific first element.
     *
     * @param first the first element of the pair
     * @return true if the list contains the first element, false otherwise
     */
    public boolean contains(A first) {
      for (Pair<A, B> pair : pairList) {
        if (pair.first.equals(first)) {
          return true;
        }
      }
      return false;
    }

    /**
     * Gets the second element of the pair based on the first element.
     *
     * @param first the first element of the pair
     * @return the second element of the pair, or null if not found
     */
    public B getSecond(A first) {
      for (Pair<A, B> pair : pairList) {
        if (pair.first.equals(first)) {
          return pair.second;
        }
      }
      return null;
    }

    /**
     * Represents a pair of elements.
     *
     * @param <A> the type of the first element
     * @param <B> the type of the second element
     */
    public class Pair<A, B> {
      public final A first;
      public final B second;

      /**
       * Creates a new Pair instance.
       *
       * @param first  the first element of the pair
       * @param second the second element of the pair
       */
      public Pair(A first, B second) {
        this.first = first;
        this.second = second;
      }
    }
  }
  int currentState;
  int totalStates;
  GameRecorder gameRecorder;
  CommonMaze maze;
  Map<CommonMazeObject, PairList<Integer,CommonField>> stateMap;
  private final ReentrantLock lock = new ReentrantLock();
  private final Condition condition = lock.newCondition();
  private String playPauseButtonText = "Start";
  private volatile boolean running = true;
  private volatile boolean paused = true;
  private boolean runForward = true;

  /**
   * Creates a new game replay.
   */
  public GameReplay() {
    this.gameRecorder = null;
    this.stateMap = new HashMap<>();
    this.currentState = 0;
    this.totalStates = 0;
    this.maze = null;
  }

  /**
   * Gets the maze associated with the game replay.
   *
   * @return the maze
   */
  public CommonMaze getMaze() {
    return maze;
  }

  /**
   * Sets the maze for the game replay.
   *
   * @param maze the maze to set
   */
  public void setMaze(CommonMaze maze) {
    this.maze = maze;
  }

  /**
   * Sets the direction of the game replay.
   *
   * @param runForward true to run the replay forward, false to run it backward
   */
  public void setRunForward(boolean runForward) {
    this.runForward = runForward;
  }

  /**
   * Sets the text for the play/pause button.
   *
   * @param playPauseButtonText the text to set
   */
  public void setPlayPauseButtonText(String playPauseButtonText) {
    this.playPauseButtonText = playPauseButtonText;
  }

  /**
   * Gets the text of the play/pause button.
   *
   * @return the play/pause button text
   */
  public String getPlayPauseButtonText() {
    return playPauseButtonText;
  }

  /**
   * Pauses the game replay.
   */
  public void pause() {
    this.setPlayPauseButtonText("Play");
    paused = true;
  }

  /**
   * Resumes the game replay.
   */
  public void resume() {
    this.setPlayPauseButtonText("Pause");
    lock.lock();
    try {
      paused = false;
      condition.signalAll();
    } finally {
      lock.unlock();
    }
  }

  /**
   * Stops the game replay.
   */
  public void stop() {
    running = false;
  }

  /**
   * Replays the game from the start.
   */
  public void replayGameFromStart() {
    currentState = 0;
    presentState(currentState);
  }

  /**
   * Replays the game from the end.
   */
  public void replayGameFromEnd() {
    currentState = totalStates - 1;
    presentState(currentState);
  }

  /**
   * Loads the game states from a file.
   *
   * @param pathToMaze the path to the game file
   * @return true if the loading was successful, false otherwise
   */
  public boolean loadGameFromFile(Path pathToMaze) {
    boolean success = false;

    // Loading maze from file
    try (InputStream inputStream = Files.newInputStream(pathToMaze)) {
      success =  this.loadMazeFromFile(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    // Loading successful/unsuccessful
    if (!success) {
      return false;
    }

    // Loading steps from file
    try (InputStream inputStream = Files.newInputStream(pathToMaze)) {
      success = this.loadStepsToMapFromFile(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }

    return success;
  }

  private boolean loadStepsToMapFromFile(InputStream inputStream) {
    //Map<CommonMazeObject, List<CommonField>> stateMap; this.stateMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
        String line;
        Pattern pattern = Pattern.compile("\\s*ORD:\\s*(\\d+)\\s*OBJ:\\s*(\\w+)\\s*(\\d+)\\s*ON:\\s*\\((\\d+),(\\d+)\\)\\s*");

        this.stateMap = new HashMap<>();

        int ghostIndex = 0;
        int keyIndex = 0;
        int currentStep = 0;
        int boostIndex = 0;
        // read line by line
        while ((line = br.readLine()) != null) {
          Matcher matcher = pattern.matcher(line);

          if (matcher.matches()) {
            // [ORD]
            int step = Integer.parseInt(matcher.group(1));
            // reset ghost index if step changes
            if (step != currentStep) {
              currentStep = step;
              ghostIndex = 0;
              keyIndex = 0;
              boostIndex = 0;
            }
            // [OBJ]
            String objectInString = matcher.group(2);
            int objectId = Integer.parseInt(matcher.group(3));
            ObjectType objectType = ObjectType.toType(objectInString);
            CommonMazeObject mazeObject = null;
            switch (objectType){
              case PACMAN:
                mazeObject = maze.getPacman();
                break;
              case GHOST:
                mazeObject = maze.getGhosts().get(ghostIndex);
                ghostIndex++;
                break;
              case KEY:
                mazeObject = maze.getKeys().get(keyIndex);
                keyIndex++;
                break;
              case BOOST:
                mazeObject = maze.getBoosts().get(boostIndex);
                boostIndex++;
                break;
              case TARGET:
                mazeObject = maze.getTarget();
                break;
              default:
                System.out.println("Invalid object type in log file.");
                // return false;
            }

            // [ON]
            int x = Integer.parseInt(matcher.group(4));
            int y = Integer.parseInt(matcher.group(5));
            CommonField field = this.maze.getField(x,y);

            // add to map
            if (!this.stateMap.containsKey(mazeObject)) {
              this.stateMap.put(mazeObject, new PairList<>());
            }

            PairList<Integer,CommonField> fieldsList = this.stateMap.get(mazeObject);
            fieldsList.addPair(step, field);
          }
          else {
            System.out.println("Skipping line: " + line);
            //return false;
          }
        }
        // set total states
        this.totalStates = this.stateMap.values().stream().mapToInt(PairList::listSize).max().orElse(0);
        return true;
      } catch (IOException e) {
        e.printStackTrace();
        return false;
      }
  }

  /**
   * Loads the maze from a file.
   *
   * @param inputStream the input stream to read from
   * @return true if the loading was successful, false otherwise
   */
  public boolean loadMazeFromFile(InputStream inputStream){
      MazeConfigure mazeConfigure = new MazeConfigure();
      this.maze = mazeConfigure.loadMaze(inputStream);
      // Loading successful/unsuccessful
      if (this.maze == null) {
        System.out.println("Failed to load maze from file.");
        return false;
      }else {
        return true;
      }

  }

  /**
   * Presents the state of the game.
   *
   * @param state the state to present
   */
  public void presentState(int state) {
    if (state < 0 || state >= totalStates) {
      System.out.println("Invalid state.");
      return;
    }
    Map<CommonMazeObject, PathField> objectsLayout = this.createObjectsLayout(state);
    this.maze.setObjectLayoutTo(objectsLayout);
  }

  private Map<CommonMazeObject, PathField> createObjectsLayout(int state) {
    Map<CommonMazeObject, PathField> objectsLayout = new HashMap<>();
    // iterate over all objects
    for (Map.Entry<CommonMazeObject, PairList<Integer, CommonField>> entry : stateMap.entrySet()) {
      CommonMazeObject mazeObject = entry.getKey();
      PairList<Integer, CommonField> stepsAndFields = entry.getValue();
      // if state is out of range, skip - this object is not present in this state

      if (!stepsAndFields.contains(state)) {
        continue;
      }
      // get field for this state
      PathField field = (PathField) stepsAndFields.getSecond(state);
      objectsLayout.put(mazeObject, field);
    }
    return objectsLayout;
  }


  /**
   * Presents the next state of the game.
   */
  public void presentNextState() {
    if (currentState + 1 < totalStates) {
      currentState++;
      presentState(currentState);
    } else {
      System.out.println("Already at the last state.");
    }
  }

  /**
   * Presents the previous state of the game.
   */
  public void presentPreviousState() {
    if (currentState - 1 >= 0) {
      currentState--;
      presentState(currentState);
    } else {
      System.out.println("Already at the first state.");
    }
  }

  /**
   * Continues the replay forward.
   */
  public void continueForward() {
    this.continueForward(500);
  }

  /**
   * Continues the replay forward.
   *
   * @param gameSpeed the speed of the replay
   */
  public void continueForward(int gameSpeed) {
    while(!this.paused && currentState < totalStates && currentState >= 0) {
      currentState++;
      presentState(currentState);
      sleep(gameSpeed);
    }
  }

  /**
   * Continues the replay backward.
   */
  public void continueBackward() {
    this.continueBackward(400);
  }

  /**
   * Continues the replay backward.
   *
   * @param gameSpeed the speed of the replay
   */
  public void continueBackward(int gameSpeed) {
    while(!this.paused && currentState <= totalStates && currentState > 0) {
      currentState--;
      presentState(currentState);
      sleep(gameSpeed);
    }
  }

  @Override
  public void run() {
    while (running) {
      lock.lock();
      try {
        while (paused) {
          condition.await();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
      if (this.runForward)
        continueForward();
      else
        continueBackward();
    }
  }

  /**
   * Returns if the replay is running forward or backward.
   */
  public boolean getRunForward() {
    return this.runForward;
  }
}
