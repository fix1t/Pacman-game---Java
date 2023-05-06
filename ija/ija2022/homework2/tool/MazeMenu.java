package ija.ija2022.homework2.tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MazeMenu {
  private JFrame frame;
  private Sound sound;

  /**
   * Hash Table to store option Menu Flags to invoke GUI structure later
   */
  public Map<String, Boolean> flags = new HashMap<>();

  List<JButton> menuElements = new ArrayList<>();

  public MazeMenu(JFrame frame, Sound sound) {
    this.frame = frame;
    this.sound = sound;
    initFlags();
  }

  public void open() {
    try {
      SwingUtilities.invokeAndWait(this::initializeInterface);
    } catch (InvocationTargetException | InterruptedException var2) {
      Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, (String)null, var2);
    }

  }

  public void initializeInterface() {
    JPanel content = new JPanel();
    content.setBackground(new Color(67, 91, 251));
    content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS)); // set vertical BoxLayout

    // Add padding from top
    content.setBorder(BorderFactory.createEmptyBorder(200, 0, 0, 0));

    JLabel heading = new JLabel("PACMAN");
    heading.setForeground(new Color(251,227,67)); // change color to red when mouse enters
    heading.setAlignmentX(Component.CENTER_ALIGNMENT); // center horizontally
    heading.setFont(new Font("Arial", Font.BOLD, 54));
    content.add(heading);

    content.add(Box.createRigidArea(new Dimension(0, 20))); // add some spacing between labels


    content.add(elementBody("Start game!", "gameFlag"));
    content.add(Box.createRigidArea(new Dimension(0, 10))); // add some spacing between labels
    content.add(elementBody("Replay", "replayFlag"));
    content.add(Box.createRigidArea(new Dimension(0, 10))); // add some spacing between labels
    content.add(elementBody("Exit", "exitFlag"));


    frame.getContentPane().add(content, BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);
  }

  private JButton elementBody(String text, String flagName) {
    JButton menuElement = new JButton(text);
    menuElement.setAlignmentX(Component.CENTER_ALIGNMENT); // center horizontally
    menuElement.setFont(new Font("Arial", Font.BOLD, 24));
    menuElement.setForeground(Color.BLACK);
    menuElement.setMaximumSize(new Dimension(200, 42)); // Set maximum width for all buttons
    menuElement.setFocusable(false); // fix: ghost not moving by WASD

    MouseListener mouseListener = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        // Store the flag value using the flag name
        flags.put(flagName, true);
        System.out.println("Starting the game!");
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

  private void removeListeners() {
    for (JButton menuElement : menuElements) {
      for (MouseListener listener : menuElement.getMouseListeners()) {
        menuElement.removeMouseListener(listener);
      }
    }
  }

  private void initFlags() {
    this.flags.put("gameFlag", false);
    this.flags.put("replayFlag", false);
    this.flags.put("exitFlag", false);
  }

  /**
   *
   * @return if game can be started or not
   */
  public boolean menuElementPressed() { return this.flags.get("gameFlag") || this.flags.get("replayFlag") || this.flags.get("exitFlag");}

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
}
