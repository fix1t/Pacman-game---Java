package ija.ija2022.homework2.tool;

import ija.ija2022.homework2.game.PacmanObject;
import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMaze;
import ija.ija2022.homework2.tool.view.FieldView;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class MazePresenter {
  private final CommonMaze maze;

  public MazePresenter(CommonMaze maze) {
    this.maze = maze;
  }

  public void open() {
    try {
      SwingUtilities.invokeAndWait(this::initializeInterface);
    } catch (InvocationTargetException | InterruptedException var2) {
      Logger.getLogger(MazePresenter.class.getName()).log(Level.SEVERE, (String)null, var2);
    }

  }

  private void initializeInterface() {
    JFrame frame = new JFrame("Pacman Demo");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setSize(350, 400);
    frame.setPreferredSize(new Dimension(350, 400));
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

    frame.getContentPane().add(content, "Center");
    frame.pack();
    frame.setVisible(true);
  }
}
