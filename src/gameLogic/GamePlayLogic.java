package gameLogic;

import graphics.*;

import java.awt.*;

import gameLogic.ShipLocations.ShotStatus;

public class GamePlayLogic {
	
	public Player player;
	public Bots bot;
	public static int turnOrder;
  private Object lockObject = new Object();
  private volatile boolean userHasClicked = false; 
  
	public GamePlayLogic() {
		player = new Player();
		bot = new Bots(GamePanel.computerDifficulty);
    state = GameState.GAMEPLAY; 
    
    
		// destroyer
    /*
		Ship playerDestroyer = new Ship(Ship.ShipType.DESTROYER, Ship.Rotation.DOWN);
		playerDestroyer.setCoords(0, 0);
		player.shipLocations.addUnguessedShip(playerDestroyer);

		// submarine
		Ship playerSubmarine = new Ship(Ship.ShipType.SUBMARINE, Ship.Rotation.LEFT);
		playerSubmarine.setCoords(2, 3);
		player.shipLocations.addUnguessedShip(playerSubmarine);

		// cruiser
		Ship playerCruiser = new Ship(Ship.ShipType.CRUISER, Ship.Rotation.UP);
		playerCruiser.setCoords(4, 4);
		player.shipLocations.addUnguessedShip(playerCruiser);

		// battleship
		Ship playerBattleship = new Ship(Ship.ShipType.BATTLESHIP, Ship.Rotation.RIGHT);
		playerBattleship.setCoords(5, 5);
		player.shipLocations.addUnguessedShip(playerBattleship);
		
		bot.shipLocations.shootLocation(new Point(0, 0));
		bot.shipLocations.shootLocation(new Point(1,1));
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				bot.shipLocations.shootLocation(new Point(i, j));
			}
		}
    */

    //
    //Turn Order
      //Have Player Shoot
      //Process
      //Show hit/miss
      //Check if game won 
   
    
	}


  public void gameLoop(){
    while(state == GameState.GAMEPLAY){ 
      if(turnOrder % 2 == 0){
        // Player's turn
          while (!userHasClicked) {
              synchronized (lockObject) {
                  try {
                      lockObject.wait(); // Wait for user click notification
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
          }
          userHasClicked = false; // Reset for next turn
          Point userGuess = board.getClickedCellCoord(); // Get clicked cell coordinateserGuess);

      if(bot.shipLocations.shootLocation(userGuess).equals(ShotStatus.HIT)){
        System.out.println("Hit!"); 
      } else {
        System.out.println("Miss!"); 
      }

      } else {
        if(checkLevel(player.shipLocations).equals(ShotStatus.HIT)){
          System.out.println("Hit!"); 
        } else { 
          System.out.println("Miss!"); 
      }
      if(gameOver()){
        state = GameStates.GAMEOVER; 
      }
    }
  }
}

  public ShotStatus checkLevel(ShipLocations opponentLocations) {
      ShotStatus status; 
      switch (bot.botLevel) {
        case EASY ->  status = easyBot(opponentLocations);

        case NORMAL ->  status = normalBot(opponentLocations);

        case HARD ->  status = hardBot(opponentLocations);

        default ->  status = impossibleBot(opponentLocations);
      }
      return status; 
  }

  public boolean gameOver(){
    boolean compWon = false; 
    boolean playerWon = false; 
    for(Point p : player.getShipLocations().getHitSections().keySet()){
      for(Point q : bot.getShipLocations().keySet()){
        if(!p.equals(q)){
          return false; 
        }
      }
    }

    for(Point p : bot.getShipLocations().getHitSections().keySet()){
      for(Point q : player.getShipLocations().keySet()){
        if(!p.equals(q)){
          return false; 
        }
      }
    }
    
    return compWon || playerWon; 
  }
	
	
}
