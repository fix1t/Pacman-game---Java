package ija.ija2022.homework2.tool;

import ija.ija2022.homework2.tool.common.CommonMaze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapPresenter {
  private final CommonMaze maze;
  private JFrame frame;
  private Sound sound;
  JButton soundButton;
  Font customFont;
  List<JButton> menuElements = new ArrayList<>();

  boolean mapFlag = false;

  public MapPresenter(CommonMaze maze, JFrame frame, Sound sound) {
    this.maze = maze;
    this.frame = frame;
    this.sound = sound;
    addFont();
  }

  public void open() {
    try {
      SwingUtilities.invokeAndWait(this::initializeInterface);
    } catch (InvocationTargetException | InterruptedException var2) {
      Logger.getLogger(MapPresenter.class.getName()).log(Level.SEVERE, (String) null, var2);
    }

  }
  /**
   * Visualize Menu
   */
  public void initializeInterface() {
    JPanel content = new JPanel();
    content.setBackground(new Color(67, 91, 251));
    content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS)); // set vertical BoxLayout

    // Add padding from top
    content.setBorder(BorderFactory.createEmptyBorder(200, 0, 0, 0));

    content.add(Box.createRigidArea(new Dimension(0, 20))); // add some spacing between labels
    content.add(elementBody("Map 01", "gameFlag"));
    content.add(Box.createRigidArea(new Dimension(0, 10))); // add some spacing between labels
    content.add(elementBody("Map 02", "replayFlag"));
    content.add(Box.createRigidArea(new Dimension(0, 10))); // add some spacing between labels
    content.add(elementBody("Map 03", "exitFlag"));
    content.add(Box.createRigidArea(new Dimension(0, 10))); // add some spacing between labels
    content.add(elementBody("Map 04", "exitFlag"));
    content.add(Box.createRigidArea(new Dimension(0, 10))); // add some spacing between labels
    content.add(elementBody("Map 05", "exitFlag"));

    final boolean[] soundOn = {sound.isPlaying()};
    ImageIcon soundOnIcon = new ImageIcon(getClass().getResource("../tool/lib/iconSound.png"));
    ImageIcon soundOffIcon = new ImageIcon(getClass().getResource("../tool/lib/iconNoSound.png"));
    soundButton = new JButton(soundOn[0] ? soundOnIcon : soundOffIcon);  // put icon depending on playback status (on/off)
    soundButton.setFocusable(false); // fix: ghost not moving by WASD

    soundButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        soundOn[0] = !soundOn[0];
        if (!soundOn[0]) {
          stopMusic();
          soundButton.setIcon(soundOffIcon);
        } else {
          playMusic();
          soundButton.setIcon(soundOnIcon);
        }
      }
    });

    frame.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
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

    content.add(Box.createVerticalGlue()); // Add vertical glue to push soundButton to the bottom
    // center sound button
    JPanel soundButtonPanel = new JPanel();
    soundButtonPanel.setOpaque(false);
    soundButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    soundButtonPanel.add(soundButton);
    content.add(soundButtonPanel);

    frame.getContentPane().add(content, BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);
  }

  /**
   * Creates new menu option (button).
   *
   * @param text     text in button
   * @param mapPath flag to switch
   * @return filled JButton Element for adding to frame
   */
  private JButton elementBody(String text, String mapPath) {
    JButton menuElement = new JButton(text);
    menuElement.setAlignmentX(Component.CENTER_ALIGNMENT); // center horizontally
    menuElement.setFont(this.customFont);
    menuElement.setForeground(Color.BLACK);
    menuElement.setMaximumSize(new Dimension(225, 45)); // Set maximum width for all buttons
    menuElement.setFocusable(false); // fix: ghost not moving by WASD

    MouseListener mouseListener = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        mapFlag = true;
        removeListeners();
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        menuElement.setForeground(Color.RED); // change color to red when mouse enters
      }

      @Override
      public void mouseExited(MouseEvent e) {
        menuElement.setForeground(Color.BLACK); // change color back to black when mouse exits
      }
    };
    menuElement.addMouseListener(mouseListener);
    menuElements.add(menuElement); // add the JLabel to the list of menu elements
    return menuElement;
  }

  /**
   * Map was selected.
   * @return flag that indicates, map was selected and the game can start
   */
  public boolean mapSelected() {
    return mapFlag;
  }

  /**
   * Remove MouseListeners from all the buttons.
   */
  private void removeListeners() {
    for (JButton menuElement : menuElements) {
      for (MouseListener listener : menuElement.getMouseListeners()) {
        menuElement.removeMouseListener(listener);
      }
    }
    for (MouseListener listener : soundButton.getMouseListeners()) {
      soundButton.removeMouseListener(listener);
    }
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
      customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("ija/ija2022/homework2/tool/lib/RetroGaming.ttf"));
      customFont = customFont.deriveFont(24f);
    } catch (FontFormatException | IOException e) {
      // Handle font loading exception
      e.printStackTrace();
      // Fallback to a default font
      customFont = new Font("Arial", Font.BOLD, 24);
    }
  }
}
