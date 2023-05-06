package ija.ija2022.homework2.tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
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
  Font customFont;
  Font headerFont;

  /**
   * Hash Table to store option Menu Flags to invoke GUI structure later
   */
  public Map<String, Boolean> flags = new HashMap<>();

  /**
   * List to store all menu buttons to remove MouseListeners, when you click on menu option
   */
  List<JButton> menuElements = new ArrayList<>();

  public MazeMenu(JFrame frame, Sound sound) {
    this.frame = frame;
    this.sound = sound;
    initFlags();
    addFont();
  }

  public void open() {
    try {
      SwingUtilities.invokeAndWait(this::initializeInterface);
    } catch (InvocationTargetException | InterruptedException var2) {
      Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, (String)null, var2);
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

    JLabel heading = new JLabel("PACMAN");
    heading.setForeground(new Color(251,227,67)); // change color to red when mouse enters
    heading.setAlignmentX(Component.CENTER_ALIGNMENT); // center horizontally
    heading.setFont(headerFont);
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

  /**
   * Creates new menu option (button).
   *
   * @param text text in button
   * @param flagName flag to switch
   * @return filled JButton Element for adding to frame
   */
  private JButton elementBody(String text, String flagName) {
    JButton menuElement = new JButton(text);
    menuElement.setAlignmentX(Component.CENTER_ALIGNMENT); // center horizontally
    menuElement.setFont(this.customFont);
    menuElement.setForeground(Color.BLACK);
    menuElement.setMaximumSize(new Dimension(200, 45)); // Set maximum width for all buttons
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

  /**
   * Remove MouseListeners from all the buttons.
   */
  private void removeListeners() {
    for (JButton menuElement : menuElements) {
      for (MouseListener listener : menuElement.getMouseListeners()) {
        menuElement.removeMouseListener(listener);
      }
    }
  }

  /**
   * Initialize flags with <code>false</code> value.
   */
  private void initFlags() {
    this.flags.put("gameFlag", false);
    this.flags.put("replayFlag", false);
    this.flags.put("exitFlag", false);
  }

  /**
   * Shows if flag was switch.
   *
   * @return all flags bool values
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

  /**
   * Add custom font
   */
  public void addFont() {
    try {
      customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("ija/ija2022/homework2/tool/lib/RetroGaming.ttf"));
      customFont = customFont.deriveFont(24f);
      headerFont = customFont.deriveFont(54f);
    } catch (FontFormatException | IOException e) {
      // Handle font loading exception
      e.printStackTrace();
      // Fallback to a default font
      customFont = new Font("Arial", Font.BOLD, 24);
      headerFont = new Font("Arial", Font.BOLD, 54);
      System.out.println(System.getProperty("user.dir"));
    }
  }
}
