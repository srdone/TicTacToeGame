import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;


//Maskable box adds a mask color to clickable box that can be set and removed.
public class MaskableBox extends ClickableBox {
  private boolean mask;
  private Color maskColor;
  Container parent;
  
  public MaskableBox(int x, int y, int width, int height, Color borderColor,
      Color backColor, boolean drawBorder, Container parent, boolean mask) {
    super(x, y, width, height, borderColor, backColor, drawBorder, parent);
    this.parent = parent;
    this.mask = mask;
  }
  
  public void draw(Graphics g) {
    if(mask == false) super.draw(g);
    if(mask) {
      setOldColor(g.getColor());
      g.setColor(maskColor);
      g.fillRect(getX(), getY(), getWidth(), getHeight());
      if(isDrawBorder()) {
        g.setColor(getBorderColor());
        g.drawRect(getX(), getY(), getWidth(), getHeight());
      }
      g.setColor(getOldColor());
    }
  }
  
  public boolean isMask() {
    return mask;
  }
  
  public void setMask(boolean mask) {
    this.mask = mask;
  }
  
  public Color getMaskColor() {
    return maskColor;
  }
  
  public void setMaskColor(Color maskColor) {
    this.maskColor = maskColor;
  }

}
