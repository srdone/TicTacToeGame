import java.awt.event.MouseAdapter;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.Container;

public class ClickableBox extends MouseAdapter {
  
  private int x, y, width, height;
  private Color borderColor, backColor, oldColor;
  private boolean drawBorder, clicked;
  private Container parent;
  
  public ClickableBox(int x, int y, int width, int height, Color borderColor, Color backColor,
      boolean drawBorder, Container parent) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.borderColor = borderColor;
    this.backColor = backColor;
    this.drawBorder = drawBorder;
    this.parent = parent;
  }

  /*
  *Draws the box according to the set colors in ClickableBox().
  *Also draws a border depending on preferences defined in the object creation call.
  */
  public void draw(Graphics g) {
    oldColor = g.getColor();
    g.setColor(backColor);
    g.fillRect(x, y, width, height);
    if(drawBorder) {
      g.setColor(borderColor);
      g.drawRect(x, y, width, height);
    }
    g.setColor(oldColor);
  }
  
  /*
  *Catches the mouseEvent on the box.
  *Sets this.clicked to true so we can tell if the box has been clicked.
  */
  public void mouseReleased(MouseEvent e) {
    if(x < e.getX() && e.getX() < x + width &&
        y < e.getY() && e.getY() < y + height) {
      this.setClicked(true);
      parent.repaint();
    }
  }
  
  //Returns information on whether or not the box has been clicked
  public boolean isClicked() {
    return clicked;
  }
  
  //Sets the box to be clicked or not apart from catching the mouseEvent
  public void setClicked(boolean clicked) {
    this.clicked = clicked;
  }

  //Get the current X coordinate of the box.
  public int getX() {
    return x;
  }

  //Set the X coordinate of the box.
  public void setX(int x) {
    this.x = x;
  }

  //Get the current Y coordinate of the box.
  public int getY() {
    return y;
  }

  //Set the Y coordinate of the box.
  public void setY(int y) {
    this.y = y;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public Color getBorderColor() {
    return borderColor;
  }

  public void setBorderColor(Color borderColor) {
    this.borderColor = borderColor;
  }

  public Color getBackColor() {
    return backColor;
  }

  public void setBackColor(Color backColor) {
    this.backColor = backColor;
  }

  public boolean isDrawBorder() {
    return drawBorder;
  }

  public void setDrawBorder(boolean drawBorder) {
    this.drawBorder = drawBorder;
  }

  public Color getOldColor() {
    return this.oldColor;
  }

  public void setOldColor(Color color) {
    this.oldColor = color;
  }
  
}
