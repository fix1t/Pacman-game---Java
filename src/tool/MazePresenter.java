package src.tool;

import src.game.PacmanObject;
import src.tool.common.CommonField;
import src.tool.common.CommonMaze;
import src.tool.view.FieldView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class MazePresenter {
  private final CommonMaze maze;
  JFrame frame;
  Sound sound;
  Font customFont;

  public MazePresenter(CommonMaze maze, JFrame frame, Sound sound) {
    this.maze = maze;
    this.frame = frame;
    this.sound = sound;
    this.maze.restoreGame();
    addFont();
  }

  public void open() {
    try {
      SwingUtilities.invokeAndWait(this::initializeInterface);
    } catch (InvocationTargetException | InterruptedException var2) {
      Logger.getLogger(MazePresenter.class.getName()).log(Level.SEVERE, (String)null, var2);
    }

  }

  /**
   * Visualize Maze
   */
  private void initializeInterface() {
    // Add text LIFE COUNTER
    JPanel textPanel = new JPanel(new BorderLayout());
    ImageIcon heartIcon = new ImageIcon(getClass().getClassLoader().getResource("lib/iconHeart.png"));
    JLabel textLabel = new JLabel("Life Counter: " + this.maze.getPacman().getLives() + "x");
    textLabel.setHorizontalAlignment(SwingConstants.LEFT);
    textLabel.setFont(customFont);
    JLabel iconLabel = new JLabel(heartIcon);
    textPanel.add(textLabel, BorderLayout.CENTER);
    textPanel.add(iconLabel, BorderLayout.EAST);
    textPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); // add padding to the text
    textLabel.setHorizontalAlignment(SwingConstants.RIGHT);

    // Add sound icon to bottom left corner
    final boolean[] soundOn = {sound.isPlaying()};
    ImageIcon soundOnIcon = new ImageIcon(getClass().getClassLoader().getResource("lib/iconSound.png"));
    ImageIcon soundOffIcon = new ImageIcon(getClass().getClassLoader().getResource("lib/iconNoSound.png"));
    JButton soundButton = new JButton(soundOn[0] ? soundOnIcon:soundOffIcon);  // put icon depending on playback status (on/off)
    // Add a mouse listener to the label
    soundButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        soundOn[0] = !soundOn[0];
        // Update the music state and the icon of the sound button based on the sound state
        if (!soundOn[0]) {
          stopMusic();
          soundButton.setIcon(soundOffIcon);
        }
        else {
          playMusic();
          soundButton.setIcon(soundOnIcon);
        }
      }
    });
    soundButton.setFocusable(false); // fix: ghost not moving by WASD
    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.add(textPanel, BorderLayout.CENTER);
    bottomPanel.add(soundButton, BorderLayout.WEST);
    bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 5)); // add padding for elements in bottom
    frame.add(bottomPanel, BorderLayout.SOUTH);

    // Add PADDING to the whole maze
    frame.add(new JPanel(), BorderLayout.CENTER);
    frame.add(new JPanel(), BorderLayout.WEST);
    frame.add(new JPanel(), BorderLayout.NORTH);
    frame.add(new JPanel(), BorderLayout.EAST);
    // PLAYER KEYBOARD MOVEMENT
    frame.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        PacmanObject pacman = (PacmanObject) maze.getPacman();
        switch (e.getKeyChar()) {
          case 'A', 'a' -> {if(pacman.canMove(CommonField.Direction.LEFT)){
            pacman.unsetGoToField();
            pacman.setDirection(CommonField.Direction.LEFT);}
          }
          case 'W', 'w' -> {if(pacman.canMove(CommonField.Direction.UP)){
            pacman.unsetGoToField();
            pacman.setDirection(CommonField.Direction.UP);}
          }
          case 'D', 'd' -> {if(pacman.canMove(CommonField.Direction.RIGHT)){
            pacman.unsetGoToField();
            pacman.setDirection(CommonField.Direction.RIGHT);}
          }
          case 'S', 's' -> {if(pacman.canMove(CommonField.Direction.DOWN)){
            pacman.unsetGoToField();
            pacman.setDirection(CommonField.Direction.DOWN);}
          }
          case 'M', 'm' -> { soundOn[0] = !soundOn[0];
            // Update the music state and the icon of the sound button based on the sound state
            if (!soundOn[0]) {
              stopMusic();
              soundButton.setIcon(soundOffIcon);
            }
            else {
              playMusic();
              soundButton.setIcon(soundOnIcon);
            }}
        }
      }

      @Override
      public void keyPressed(KeyEvent e) {

      }

      @Override
      public void keyReleased(KeyEvent e) {

      }
    });
    frame.setResizable(false);
    int rows = this.maze.numRows();
    int cols = this.maze.numCols();
    GridLayout layout = new GridLayout(rows, cols);
    JPanel content = new JPanel(layout);

    for(int i = 0; i < rows; ++i) {
      for(int j = 0; j < cols; ++j) {
        FieldView field = new FieldView(this.maze.getField(i, j), this.maze);
        content.add(field);
      }
    }

    final int[] pacmanLives = {3}; // when Pacman loses life, reset movement
    // Create a timer that updates the LifeCounter every second
    Timer timer = new Timer(1000, e -> {
      if (pacmanLives[0] != this.maze.getPacman().getLives()) {
        pacmanLives[0] = this.maze.getPacman().getLives();
        maze.getPacman().unsetGoToField();
      }
      textLabel.setText("Life Counter: " + this.maze.getPacman().getLives() + "x");
    });
    timer.start();

    frame.getContentPane().add(content, "Center");
    frame.pack();
    frame.setVisible(true);
  }

  /**
   * Starts playing music.
   */
  public void playMusic() {
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
   * Add custom font
   */
  public void addFont() {
    try {
      customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("lib/RetroGaming.ttf"));
      customFont = customFont.deriveFont(15f);
    } catch (FontFormatException | IOException e) {
      // Handle font loading exception
      e.printStackTrace();
      // Fallback to a default font
      customFont = new Font("Arial", Font.BOLD, 15);
    }
  }
}
