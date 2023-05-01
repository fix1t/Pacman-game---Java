package ija.ija2022.homework2.tool.view;

import ija.ija2022.homework2.tool.common.CommonMazeObject;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class TargetView implements ComponentView {
  private CommonMazeObject model;
  private final FieldView parent;

  public TargetView(FieldView parent, CommonMazeObject m) {
    this.model = m;
    this.parent = parent;
  }

  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    Rectangle bounds = this.parent.getBounds();
    double w = bounds.getWidth();
    double h = bounds.getHeight();
    double sideLength = Math.min(h, w) - 15.0;
    double x = (w - sideLength) / 2.0;
    double y = (h - sideLength) / 2.0;
    Rectangle2D.Double rect = new Rectangle2D.Double(x, y, sideLength, sideLength);
    g2.setColor(Color.blue);
    g2.setStroke(new BasicStroke(5)); // set border width to 5 pixels
    g2.draw(rect);
  }
}
