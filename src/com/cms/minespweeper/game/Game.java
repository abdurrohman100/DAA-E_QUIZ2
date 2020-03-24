package com.cms.minespweeper.game;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import com.cma.minesweeper.assets.Assets;

public class Game {
	Frame frame;
	private int width,height;
	private String title;
	private int N;
	private Board board;
	private MouseEvents mouseEvent;
	private BufferStrategy bs;
	
	private boolean ended;

	public Game(String title, int ncells, int nmines) {
		// TODO Auto-generated constructor stub
		this.N=ncells;
		width=Assets.spriteWidth * ncells;
		height=width;
		this.title=title;
		board= new Board(ncells,nmines);
		mouseEvent= new MouseEvents(this);
		
		frame = new Frame(title, width, height);
		frame.getFrame().addMouseListener(mouseEvent);
		frame.getCanvas().addMouseListener(mouseEvent);
		frame.getCanvas().createBufferStrategy(2);
		bs= frame.getCanvas().getBufferStrategy();
		Assets.init();
		
	}
	
	public void start() {
		Graphics g =bs.getDrawGraphics();
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				Assets.draw(i, j, CellStates.COVERED, g);
			}
		}
		bs.show();
		g.dispose();
	}

	public void onClick(boolean isLeft, int x, int y) {
		// TODO Auto-generated method stub
		if(ended)return;
		Graphics g = bs.getDrawGraphics();
		int row=y/Assets.spriteWidth;
		int col=x/Assets.spriteWidth;
		if(isLeft) {
			board.uncoverCell(row, col, g);
		}else {
			board.toogleFlag(row, col, g);
		}
		bs.show();
		GameStates result= board.getGameStates();
		// when game ends
		if (result != GameStates.ONGOING) {
			ended = true;
			System.out.println("Game ended!");
			String msg = (result == GameStates.LOST ? "!!!!! You Lose !!!!!" : "!!!!! You Won !!!!!");
			frame.getFrame().setTitle(msg);
		}
		g.dispose();
	}

	
}
