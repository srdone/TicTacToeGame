  import java.util.ArrayList;
  
  
  /*
   * Should keep track of:
   * Player ID (1 or 2)
   * Player Symbol (X or O)
   * Chosen boxes
   * 
   * When initializing should:
   * Assign ID
   * Symbol determined by 1 (X) or 2 (O)
   */
  
  public class Player {
    private String name;
    private int id;
    private PlayerType playerType;
    private ArrayList<TicTacToeBox> chosenBoxes;
    
    public Player(String name, int id) {
      System.out.println("Player init start");
      setName(name);
      System.out.println("Player name set to " + name);
      setId(id);
      System.out.println("Player id set to " + id);
      this.chosenBoxes = new ArrayList<TicTacToeBox>();
      System.out.println("chosen box list initialized");
    }
    
    public void setName(String name) {
      this.name = name;
    }
    
    public void setId(int id) {
      this.id = id;
      setPlayerType();
    }
    
    public int getID() {
      return this.id;
    }
    
    public String getName() {
      return this.name;
    }
    
    private void setPlayerType() {
      if (id == 0) { 
        this.playerType = PlayerType.ohs;
      } else {
        this.playerType = PlayerType.exes; 
      }
    }
    
    public PlayerType getPlayerType() {
      return playerType;
    }
    
    public void addChosenBox(TicTacToeBox box) {
      chosenBoxes.add(box);
      System.out.println("Box added to " + getName());
    }
    
    public int getNumberofBoxes() {
      return chosenBoxes.size();
    }
    
    public TicTacToeBox getChosenBox(int i) {
      return chosenBoxes.get(i);
    }
    
  }