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
	
	/**
	 * start game
	 */
	public void start() {
		Graphics g =bs.getDrawGraphics();
		// draw semua cell dengan kondisi Covered
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				Assets.draw(i, j, CellStates.COVERED, g);
			}
		}
		bs.show();
		g.dispose();
	}

	public void onClick(boolean isLeft, int x, int y) {

		if(ended)return;
		Graphics g = bs.getDrawGraphics();
		// get row dan col dengan membagi koordinat dengan ukuran sprite
		int row=y/Assets.spriteWidth;
		int col=x/Assets.spriteWidth;
		// Jika mouse kiri di-release maka uncover cellnya
		if(isLeft) {
			board.uncoverCell(row, col, g);
		}
		// Jika mouse kanan di-release maka kasih flag pada cellnya
		else {
			board.toogleFlag(row, col, g);
		}
		bs.show();
		// Setiap kali click, cek status game
		GameStates result= board.getGameStates();
		// kalau game nya bukan ongoing berarti game selesai
		if (result != GameStates.ONGOING) {
			ended = true;
			System.out.println("Game Selesai");
			String msg = (result == GameStates.LOST ? "Kamu Kalah!" : "Kamu Menang!");
			frame.getFrame().setTitle(msg);
		}
		g.dispose();
	}
}
