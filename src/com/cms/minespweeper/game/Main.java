package com.cms.minespweeper.game;

public class Main {
	
	/**
	 * @param args
	 * Main Method to run the game
	 */
	public static void main(String[] args) {
		//ncell ukuran minsweepeer, nmines banyak bomb
		int ncells=40;
		int nmines=10;
		
		Game game = new Game("Minesweeper BFS",ncells,nmines);
		game.start();
		
	}
	
}
