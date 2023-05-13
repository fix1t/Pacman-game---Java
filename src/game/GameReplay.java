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

public class GameReplay implements Runnable {
  int currentState;
  int totalStates;
  GameRecorder gameRecorder;
  CommonMaze maze;
  Map<CommonMazeObject, List<CommonField>> stateMap;
  // Add a lock and a condition
  private final ReentrantLock lock = new ReentrantLock();
  private final Condition condition = lock.newCondition();
  private String playPauseButtonText = "Start";

  // Add a flag to control the loop
  private volatile boolean running = true;
  private volatile boolean paused = true;
  private boolean runForward = true;

  public GameReplay() {
    this.gameRecorder = null;
    this.stateMap = new HashMap<>();
    this.currentState = 0;
    this.totalStates = 0;
    this.maze = null;
  }

  public GameReplay(GameRecorder gameRecorder) {
    this.gameRecorder = gameRecorder;
    this.stateMap = gameRecorder.stateMap;
    this.currentState = 0;
    this.totalStates = stateMap.values().stream().mapToInt(List::size).max().orElse(0);
  }

  public CommonMaze getMaze() {
    return maze;
  }

  public void setMaze(CommonMaze maze) {
    this.maze = maze;
  }

  public void setRunForward(boolean runForward) {
    this.runForward = runForward;
  }

  public void setPlayPauseButtonText(String playPauseButtonText) {
    this.playPauseButtonText = playPauseButtonText;
  }

  public String getPlayPauseButtonText() {
    return playPauseButtonText;
  }

  public void pause() {
    this.setPlayPauseButtonText("Play");
    paused = true;
  }

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

  public void stop() {
    running = false;
  }
  public void ReplayGameFromStart() {
    currentState = 0;
    presentState(currentState);
  }

  public void ReplayGameFromEnd() {
    currentState = totalStates - 1;
    presentState(currentState);
  }

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
              this.stateMap.put(mazeObject, new ArrayList<>());
            }

            List<CommonField> fieldsList = this.stateMap.get(mazeObject);
            fieldsList.add(field);
          }
          else {
            System.out.println("Skipping line: " + line);
            //return false;
          }
        }
        this.totalStates = this.stateMap.values().stream().mapToInt(List::size).max().orElse(0);
        return true;
      } catch (IOException e) {
        e.printStackTrace();
        return false;
      }
  }

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

  public boolean loadGameFromRecorder(GameRecorder gameRecorder) {
    this.gameRecorder = gameRecorder;
    this.stateMap = gameRecorder.stateMap;
    this.totalStates = stateMap.values().stream().mapToInt(List::size).max().orElse(0);
    return true;
  }

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
    for (Map.Entry<CommonMazeObject, List<CommonField>> entry : stateMap.entrySet()) {
      CommonMazeObject mazeObject = entry.getKey();
      List<CommonField> fields = entry.getValue();
      // if state is out of range, skip - this object is not present in this state
      if (state >= fields.size()) {
        continue;
      }
      PathField field = (PathField) fields.get(state);
      objectsLayout.put(mazeObject,field);
    }
    return objectsLayout;
  }

  public void presentNextState() {
    if (currentState + 1 < totalStates) {
      currentState++;
      presentState(currentState);
    } else {
      System.out.println("Already at the last state.");
    }
  }

  public void presentPreviousState() {
    if (currentState - 1 >= 0) {
      currentState--;
      presentState(currentState);
    } else {
      System.out.println("Already at the first state.");
    }
  }

  public void continueForward() {
    this.continueForward(500);
  }


    public void continueForward(int gameSpeed) {
    while(!this.paused && currentState < totalStates && currentState >= 0) {
      currentState++;
      presentState(currentState);
      sleep(gameSpeed);
    }
  }

  public void continueBackward() {
    this.continueBackward(400);
  }

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

  public boolean getRunForward() {
    return this.runForward;
  }
}
