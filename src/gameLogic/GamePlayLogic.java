package gameLogic;

import graphics.*;

import java.awt.*;

import gameLogic.ShipLocations.ShotStatus;

public class GamePlayLogic {
	
	public Player player;
	public Bots bot;
	public static int turnOrder;
	public static boolean compWon = false; 
	public static boolean playerWon = false;  
  
	public GamePlayLogic() {
		player = new Player();
		bot = new Bots(GamePanel.computerDifficulty);
    state = GameState.GAMEPLAY; 
	}


  public void gameLoop(){
    while(state == GameState.GAMEPLAY){ 
      if(turnOrder % 2 == 1)  {
      	switch (bot.botLevel) {
						case EASY ->  status = easyBot(opponentLocations);

						case NORMAL ->  status = normalBot(opponentLocations);

						case HARD ->  status = hardBot(opponentLocations);

						default ->  status = impossibleBot(opponentLocations);
					}
      turnOrder++;
    }
			if(gameOver()){
				state = GameStates.GAMEOVER; 
			}
  }
}

  public boolean gameOver(){ 
    if (player.getUnguessedSections().size() == 0) {
			compWon = true;
		}
		if (computer.getUnguessedSections().size() == 0) {
			playerWon = true;
		}
    
    return compWon || playerWon; 
  }
}
