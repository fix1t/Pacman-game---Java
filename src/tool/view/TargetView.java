package src.tool.view;

import src.tool.common.CommonMazeObject;

import javax.swing.*;
import java.awt.*;

/**

 The TargetView class represents the view for a target object in the maze field.
 It implements the ComponentView interface.
 @author Jakub Mikyšek
 */
public class TargetView implements ComponentView {
  private CommonMazeObject model;
  private final FieldView parent;
  Image image;

  /**

   Constructs a TargetView object with the specified parent field and maze object.
   @param parent the FieldView parent of the target
   @param m the CommonMazeObject representing the target
   */
  public TargetView(FieldView parent, CommonMazeObject m) {
    this.model = m;
    this.parent = parent;
    image = new ImageIcon(getClass().getClassLoader().getResource("lib/Target.png")).getImage();
  }

  /**

   Paints the target component on the graphics context.
   @param g the Graphics object to paint on
   */
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    // Get the dimensions of the parent field
    int fieldWidth = parent.getWidth();
    int fieldHeight = parent.getHeight();

    // Scale the image to fit the field size
    double imageWidth = image.getWidth(null);
    double imageHeight = image.getHeight(null);
    double scale = Math.min(fieldWidth / imageWidth, fieldHeight / imageHeight);
    int scaledWidth = (int) (imageWidth * scale);
    int scaledHeight = (int) (imageHeight * scale);

    // Draw the scaled image at the center of the field
    int x = (fieldWidth - scaledWidth) / 2;
    int y = (fieldHeight - scaledHeight) / 2;
    g2.drawImage(image, x, y, scaledWidth, scaledHeight, null);
  }
}
