package src.tool;

import src.game.GameReplay;
import src.game.PacmanObject;
import src.tool.common.CommonMaze;
import src.tool.view.FieldView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MazeReplay {
  private final CommonMaze maze;
  JFrame frame;
  Sound sound;
  Font customFont;
  GameReplay replay;
  private boolean finished;

  public MazeReplay(CommonMaze maze, JFrame frame, Sound sound, GameReplay replay) {
    this.maze = maze;
    this.frame = frame;
    this.sound = sound;
    this.maze.restoreGame();
    this.replay = replay;
    this.finished = false;
    addFont();
    PacmanObject pacman = maze.getPacman();
    pacman.unsetGoToField();
  }

  public void open() {
    try {
      SwingUtilities.invokeAndWait(this::initializeInterface);
    } catch (InvocationTargetException | InterruptedException var2) {
      Logger.getLogger(MazeReplay.class.getName()).log(Level.SEVERE, (String)null, var2);
    }

  }

  /**
   * Visualize Maze
   */
  private void initializeInterface() {
    JButton startButton = new JButton("Start");
    JButton endButton = new JButton("End");
    JButton nextButton = new JButton("Next (N)");
    JButton previousButton = new JButton("Previous (P)");
    JButton playButton = new JButton("Play");
    JButton PlaybackDirection = new JButton(">");
    JButton exitButton = new JButton("Exit");


    // Set font for buttons
    startButton.setFont(customFont);
    endButton.setFont(customFont);
    nextButton.setFont(customFont);
    previousButton.setFont(customFont);
    playButton.setFont(customFont);
    PlaybackDirection.setFont(customFont);
    exitButton.setFont(customFont);

    GameReplay mouseReplay = replay;
    // Add mouse listeners to buttons
    startButton.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {
        mouseReplay.pause();
        playButton.setText(mouseReplay.getPlayPauseButtonText());
        mouseReplay.ReplayGameFromStart();
      }
    });

    endButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        mouseReplay.pause();
        playButton.setText(mouseReplay.getPlayPauseButtonText());
        mouseReplay.ReplayGameFromEnd();
      }
    });

    nextButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        mouseReplay.pause();
        playButton.setText(mouseReplay.getPlayPauseButtonText());
        mouseReplay.presentNextState();
      }
    });

    previousButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        mouseReplay.pause();
        playButton.setText(mouseReplay.getPlayPauseButtonText());
        mouseReplay.presentPreviousState();
      }
    });

    playButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (playButton.getText().equals("Play")) {
          // Handle Play button click
          mouseReplay.resume();
        } else {
          // Handle Pause button click
          mouseReplay.pause();
        }
        playButton.setText(mouseReplay.getPlayPauseButtonText());
      }
    });

    PlaybackDirection.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        mouseReplay.pause();
        playButton.setText(mouseReplay.getPlayPauseButtonText());
        // Toggle between forward and backward
        PlaybackDirection.setText(PlaybackDirection.getText().equals("<") ? ">" : "<");
        mouseReplay.setRunForward(!mouseReplay.getRunForward());
      }
    });

    exitButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        finished = true;
      }
    });

    // Add sound icon to bottom left corner
    final boolean[] soundOn = {sound.isPlaying()};
    ImageIcon soundOnIcon = new ImageIcon(getClass().getResource("../../lib/iconSound.png"));
    ImageIcon soundOffIcon = new ImageIcon(getClass().getResource("../../lib/iconNoSound.png"));
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

    // Create top row panel
    JPanel topRowPanel = new JPanel();
    topRowPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    topRowPanel.add(previousButton);
    topRowPanel.add(nextButton);

    // Create bottom row panel
    JPanel bottomRowPanel = new JPanel();
    bottomRowPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    bottomRowPanel.add(soundButton);
    bottomRowPanel.add(Box.createHorizontalStrut(100)); // Add horizontal space/margin
    bottomRowPanel.add(startButton);
    bottomRowPanel.add(endButton);
    bottomRowPanel.add(playButton);
    bottomRowPanel.add(PlaybackDirection);
    bottomRowPanel.add(Box.createHorizontalStrut(60)); // Add horizontal space/margin
    bottomRowPanel.add(exitButton);

    // Add top and bottom row panels to button panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
    buttonPanel.add(topRowPanel);
    buttonPanel.add(bottomRowPanel);

    frame.add(buttonPanel, BorderLayout.SOUTH);

    // Add PADDING to the whole maze
    frame.add(new JPanel(), BorderLayout.CENTER);
    frame.add(new JPanel(), BorderLayout.WEST);
    frame.add(new JPanel(), BorderLayout.NORTH);
    frame.add(new JPanel(), BorderLayout.EAST);
    // PLAYER KEYBOARD MOVEMENT
    frame.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        PacmanObject pacman = maze.getPacman();
        switch (e.getKeyChar()) {
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

  public boolean replayEnded() {
    return this.finished;
  }
}
