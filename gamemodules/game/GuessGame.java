package nl.hanze.t23i.gamemodule.game;

import java.awt.Component;
import java.util.HashMap;
import java.util.Random;

import nl.hanze.t23i.gamemodule.extern.AbstractGameModule;

public class GuessGame extends AbstractGameModule {

	public static final String GAME_TYPE = Game.GAME_TYPE;
	
	private GameView gameView;
	private String nextPlayer;
	private String moveDetails;
	private HashMap<String, Integer> playerResults;
	
	private int secretNumber;
	
	public GuessGame(String playerOne, String playerTwo) {
		super(playerOne, playerTwo);
		
		moveDetails = null;
		playerResults = new HashMap<String, Integer>();
		gameView = new GameView(playerOne, playerTwo);
	}

	@Override
	public void doPlayerMove(String player, String move) throws IllegalStateException {
		super.doPlayerMove(player, move);
		
		if(!nextPlayer.equals(player)) {
			throw new IllegalStateException("It is not player's turn");
		}
		
		gameView.addText(String.format("%s: %s", player, move));
		
		int guess = -1;
		
		try {
			guess = Integer.parseInt(move);
		} catch (NumberFormatException e) {
			illegalPlayerMove(player);
			return;
		}
		
		if(guess < 1 || guess > 10) {
			illegalPlayerMove(player);
			return;
		}
		
		if(guess == secretNumber) {
			matchStatus = MATCH_FINISHED;
			moveDetails = "Geraden";
			playerResults.put(player, PLAYER_WIN);
			playerResults.put(otherPlayer(player), PLAYER_LOSS);
		} else if(guess < secretNumber) {
			moveDetails = "Hoger";
			nextPlayer();
		} else if(guess > secretNumber) {
			moveDetails = "Lager";
			nextPlayer();
		}
	}

	private void illegalPlayerMove(String player) {
		matchStatus = MATCH_FINISHED;
		moveDetails = "Illegal move";
		playerResults.put(otherPlayer(player), PLAYER_WIN);
		playerResults.put(player, PLAYER_LOSS);
	}
	
	@Override
	public String getMatchResultComment() throws IllegalStateException {
		super.getMatchResultComment();
		
		return "Geraden";
	}

	@Override
	public int getMatchStatus() {
		return matchStatus;
	}

	@Override
	public String getMoveDetails() throws IllegalStateException {
		super.getMoveDetails();
		
		if(moveDetails == null) {
			throw new IllegalStateException("No move has been done during this match");
		}
		
		return moveDetails;
	}

	@Override
	public String getPlayerToMove() throws IllegalStateException {
		super.getPlayerToMove();
		
		return nextPlayer;
	}

	@Override
	public int getPlayerResult(String player) throws IllegalStateException {
		super.getPlayerResult(player);
		
		return playerResults.get(player);
	}
	
	@Override
	public int getPlayerScore(String player) throws IllegalStateException {
		super.getPlayerResult(player);
		
		return playerResults.get(player);
	}

	@Override
	public String getTurnMessage() throws IllegalStateException {
		super.getTurnMessage();
		
		String message = null;
		
		if(moveDetails == null) {
			message = "Raad het getal tussen 1 en 10!";
		} else {
			message = moveDetails;
		}
		
		return message;
	}

	@Override
	public Component getView() {
		return gameView;
	}

	@Override
	public void start() throws IllegalStateException {
		super.start();
		
		nextPlayer = playerOne;
		secretNumber = new Random().nextInt(10) + 1;
	}
	
	private void nextPlayer() {
		nextPlayer = otherPlayer(nextPlayer);
	}
	
	private String otherPlayer(String player) {
		return player.equals(playerOne) ? playerTwo : playerOne;
	}

}
