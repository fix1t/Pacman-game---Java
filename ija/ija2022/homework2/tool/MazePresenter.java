package ija.ija2022.homework2.tool;

import ija.ija2022.homework2.game.PacmanObject;
import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMaze;
import ija.ija2022.homework2.tool.view.FieldView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class MazePresenter {
  private final CommonMaze maze;
  JFrame frame;

  public MazePresenter(CommonMaze maze, JFrame frame) {
    this.maze = maze;
    this.frame = frame;
  }

  public void open() {
    try {
      SwingUtilities.invokeAndWait(this::initializeInterface);
    } catch (InvocationTargetException | InterruptedException var2) {
      Logger.getLogger(MazePresenter.class.getName()).log(Level.SEVERE, (String)null, var2);
    }

  }

  private void initializeInterface() {
    // Add text LIFE COUNTER
    JPanel textPanel = new JPanel(new BorderLayout());
    ImageIcon heartIcon = new ImageIcon(getClass().getResource("./lib/iconHeart.png"));
    JLabel textLabel = new JLabel("Life Counter: " + this.maze.getPacman().getLives() + "x");
    textLabel.setHorizontalAlignment(SwingConstants.LEFT);
    JLabel iconLabel = new JLabel(heartIcon);
    textLabel.setFont(new Font("Arial", Font.BOLD, 14));
    textPanel.add(textLabel, BorderLayout.CENTER);
    textPanel.add(iconLabel, BorderLayout.EAST);
    textPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 10)); // add padding to the text
    textLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    // Add PADDING to the whole maze
    frame.add(textPanel, BorderLayout.SOUTH);
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
          case 'A', 'a' -> {if(pacman.canMove(CommonField.Direction.LEFT)){pacman.setDirection(CommonField.Direction.LEFT);}}
          case 'W', 'w' -> {if(pacman.canMove(CommonField.Direction.UP)){pacman.setDirection(CommonField.Direction.UP);}}
          case 'D', 'd' -> {if(pacman.canMove(CommonField.Direction.RIGHT)){pacman.setDirection(CommonField.Direction.RIGHT);}}
          case 'S', 's' -> {if(pacman.canMove(CommonField.Direction.DOWN)){pacman.setDirection(CommonField.Direction.DOWN);}}
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
        FieldView field = new FieldView(this.maze.getField(i, j));
        content.add(field);
      }
    }

    // Create a timer that updates the LifeCounter every second
    Timer timer = new Timer(1000, e -> {
      textLabel.setText("Life Counter: " + this.maze.getPacman().getLives() + "x");
    });
    timer.start();

    frame.getContentPane().add(content, "Center");
    frame.pack();
    frame.setVisible(true);
  }
}
