import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;

//TicTacToeBox allows the box to display an X or an O
public class TicTacToeBox extends ClickableBox {

  private PlayerType displaySymbol;
  private boolean picked;
  Container parent;
  
  public TicTacToeBox(int x, int y, int width, int height, Container parent) {
    super(x, y, width, height, Color.BLACK, Color.WHITE, true, parent);
    this.parent = parent;
    this.displaySymbol = null;
    this.picked = false;
  }
  
  //Pre-condition:
  //Post-condition: If the the box has been picked (picked==true), draws the displaySymbol of the object.
  //Description:
  public void draw(Graphics g) {
    super.draw(g);
    if(picked) {
      if(displaySymbol == PlayerType.exes) drawEx(g);
      if(displaySymbol == PlayerType.ohs) drawOh(g);
    }
  }
  
  //Pre-condition:
  //Post-condition:
  //Description:
  private void drawEx(Graphics g) {
    g.drawLine(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight()); //draw left line
    g.drawLine(this.getX() + this.getWidth(), this.getY(), this.getX(), this.getY() + this.getHeight());  //draw right line
  }
  
  private void drawOh(Graphics g) {
    g.drawOval(this.getX(),this.getY(),this.getWidth(),this.getHeight());
  }
  
  //Change below getters and setters to work on symbols
  public boolean isPicked() {
    return picked;
  }
  
  public void setPicked(PlayerType type) {
    this.picked = true;
    this.displaySymbol = type;
  }
  
  public void setNotPicked() {
    this.picked = false;
  }
  
  public PlayerType getCurrentSymbol() {
    return displaySymbol;
  }


}
