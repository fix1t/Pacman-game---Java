package src.tool.view;

import src.tool.common.CommonField;
import src.tool.common.CommonMaze;
import src.tool.common.CommonMazeObject;
import src.tool.common.Observable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 The FieldView class represents the view for a maze field.
 It extends JPanel and implements the Observable.Observer interface.
 @author Jakub Mikyšek
 @Co-author Gabriel Biel
 */
public class FieldView extends JPanel implements Observable.Observer {
  private final CommonField model;
  private final List<ComponentView> objects;
  private int changedModel = 0;
  private final CommonMaze maze;

  /**

   Constructs a FieldView object with the specified CommonField and CommonMaze.
   @param model the CommonField object representing the field
   @param maze the CommonMaze object containing the field
   */
  public FieldView(CommonField model, CommonMaze maze) {
    this.model = model;
    this.maze = maze;
    this.objects = new ArrayList();
    this.privUpdate();
    model.addObserver(this);
  }

  /**

   Paints the field component on the graphics context.
   @param g the Graphics object on which to paint
   */
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    PacmanView pacman = null;
    GhostView ghost = null;
    for (ComponentView o : this.objects) {
      o.paintComponent(g);
      if(o instanceof PacmanView){
        pacman = (PacmanView) o;
      } else if (o instanceof GhostView) {
        ghost = (GhostView) o;
      }
    }
    // draw ghost and pacman last to be on top of everything
    if (ghost != null) {
      ghost.paintComponent(g);
    }
    if(pacman != null){
      pacman.paintComponent(g);
    }
  }

  /**

   Updates the field view based on the model.
   */
  private void privUpdate() {
    if (this.model.canMove()) {
      // draw Path Field
      Color pathBackground = new Color(53, 131, 209);
      this.setBackground(pathBackground);
      if (!this.model.isEmpty()) {
        CommonMazeObject o = this.model.get();
        ComponentView v;
        switch (o.getType()) {
          case TARGET -> v = new TargetView(this, o);
          case BOOST -> v = new BoostView(this, o);
          case KEY -> v = new KeyView(this, o);
          case PACMAN -> v = new PacmanView(this, o);
          case GHOST -> v = new GhostView(this, o);
          default -> v = null;
        }
        this.objects.clear(); // clear field from all object first to stop overlapping
        this.objects.add(v);
      } else {
        this.objects.clear();
      }
      this.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          maze.getPacman().setGoToField(model);
        }
      });
    } else {
      Color wallBackground = new Color(166, 124, 82);;
      this.setBackground(wallBackground);
    }
    Color borderColor = new Color(35, 25, 66);
    this.setBorder(BorderFactory.createLineBorder(borderColor));
  }

  /**

   Updates the view based on changes in the observable model.
   @param field the Observable field object
   */
  public final void update(Observable field) {
    ++this.changedModel;
    this.privUpdate();
  }

  /**

   Returns the number of updates made to the view.
   @return the number of updates
   */
  public int numberUpdates() {
    return this.changedModel;
  }

  /**

   Clears the update counter.
   */
  public void clearChanged() {
    this.changedModel = 0;
  }

  /**

   Returns the CommonField object associated with the view.
   @return the CommonField object
   */
  public CommonField getField() {
    return this.model;
  }
}

