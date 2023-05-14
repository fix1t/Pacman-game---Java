package src.tool.view;

import src.game.GhostObject;
import src.tool.common.CommonMazeObject;

import javax.swing.*;
import java.awt.*;

/**

 The GhostView class represents the view for a ghost object in the maze field.
 It implements the ComponentView interface.
 @author Jakub Mikyšek
 */
public class GhostView implements ComponentView {
  private final CommonMazeObject model;
  private final FieldView parent;
  private Image[] images;
  private int imageIndex;

  /**

   Constructs a GhostView object with the specified parent field and maze object.
   @param parent the FieldView parent of the ghost
   @param m the CommonMazeObject representing the ghost
   */
  public GhostView(FieldView parent, CommonMazeObject m) {
    this.model = m;
    this.parent = parent;

    images = new Image[4];
    images[0] = new ImageIcon(getClass().getClassLoader().getResource("lib/GhostRed.png")).getImage();
    images[1] = new ImageIcon(getClass().getClassLoader().getResource("lib/GhostPurple.png")).getImage();
    images[2] = new ImageIcon(getClass().getClassLoader().getResource("lib/GhostBlue.png")).getImage();
    images[3] = new ImageIcon(getClass().getClassLoader().getResource("lib/GhostOrange.png")).getImage();

    GhostObject ghost = (GhostObject) this.model;
    this.imageIndex = ghost.getImageIndex();
  }

  /**

   Paints the ghost component on the graphics context.
   @param g the Graphics object to paint on
   */
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

