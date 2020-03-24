package com.cms.minespweeper.game;

public class Main {
	
	public static void main(String[] args) {
		int ncells=40;
		int nmines=10;
		
		Game game = new Game("Minesweeper BFS",ncells,nmines);
		game.start();
		
	}
	
}
