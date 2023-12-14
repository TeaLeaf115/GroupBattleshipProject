import java.util.*; 
public class Bots {
    public enum BotLevel{
      EASY,
      NORMAL,
      HARD,
      IMPOSSIBLE
    }

    private BotLevel bl;
  
    public Bots(BotLevel botLevel) {
      this.bl = botLevel;
    }

    public BotLevel getLevel(){
      return botLevel; 
    }

    public Point checkLevel(){
      if(bl == BotLevel.EASY){
        return easyBot(); 
      } else if(bl == BotLevel.NORMAL){
        return normalBot();
      } else if(bl == BotLevel.HARD){
        return hardBot();
      } else {
        return impossibleBot();
      }
    }

   
    public boolean hardBot(){
      return true; 
    }

    public boolean normalBot(){
      return false; 
    }

    public boolean easyBot() {
      while(col < 1 || col > 9){
            col = Randomizer.nextInt(0,9); 
      } 

      while(row < 1 || row > 9){
            row = Randomizer.nextInt(0, 9); 
      }
      
      return checkHit(new Point(row, col)); 

      
    }

    public void impossibleBot(Location l) {
      int numberOfFate = Randomizer.nextInt(0, 99); 
      if(numberOfFate <= 84){
        int randIdx = Randomizer.nextInt(0, l.unguessedSections.size()); 
        int count = 0; 
        for (Map.Entry<Point, shipSection> entry : l.unguessedSections.entrySet()) {
          if (count == randIdx) {
            // Found a match! Declare hit and potentially update the ship section state.
            shipSection = entry.getValue();
            shipSection.setHit(true); 
            // Update ship section if needed
            break; // No need to keep searching after a hit
            count++; 
          }
      }
       
    } else {
           Point p = easyBot(); 
           checkHit(p, l); 
        }
      }

    public boolean checkHit(Point p, Location l){
      for (Map.Entry<Point, shipSection> entry : l.unguessedSections.entrySet()) {
        if (entry.getKey().equals(p)) {
          // Found a match! Declare hit and potentially update the ship section state.
          hit = true;
          shipSection = entry.getValue(); // Update ship section if needed
          break; // No need to keep searching after a hit
          shipSection.setHit(true); 
        }
      }
    }
  }
        

