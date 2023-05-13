package src.tool.view;

import src.game.PacmanObject;
import src.tool.common.CommonField;
import src.tool.common.CommonMazeObject;

import javax.swing.*;
import java.awt.*;

public class PacmanView implements ComponentView {
  private CommonMazeObject model;
  private src.tool.view.FieldView parent;
  Image[] images;
  static int imageIndex;

  public PacmanView(FieldView parent, CommonMazeObject m) {
    this.model = m;
    this.parent = parent;
    images = new Image[2];
    images[0] = new ImageIcon(getClass().getClassLoader().getResource("lib/PacmanRight.png")).getImage();
    images[1] = new ImageIcon(getClass().getClassLoader().getResource("lib/PacmanLeft.png")).getImage();
    PacmanObject pacman = (PacmanObject) this.model;
    if (pacman.getDirection() == CommonField.Direction.RIGHT)
      imageIndex = 0;
    else if (pacman.getDirection() == CommonField.Direction.LEFT)
      imageIndex = 1;
  }

  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    // Get the dimensions of the parent field
    int fieldWidth = parent.getWidth();
    int fieldHeight = parent.getHeight();

    // Scale the image to fit the field size
    double imageWidth = images[imageIndex].getWidth(null);
    double imageHeight = images[imageIndex].getHeight(null);
    double scale = Math.min(fieldWidth / imageWidth, fieldHeight / imageHeight);
    int scaledWidth = (int) (imageWidth * scale);
    int scaledHeight = (int) (imageHeight * scale);

    // Draw the scaled image at the center of the field
    int x = (fieldWidth - scaledWidth) / 2;
    int y = (fieldHeight - scaledHeight) / 2;
    g2.drawImage(images[imageIndex], x, y, scaledWidth, scaledHeight, null);
  }
}
