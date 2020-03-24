package com.cms.minespweeper.game;

import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import com.cma.minesweeper.assets.Assets;

public class Board {
	private GameStates gameStates;
	private int n,nmines,ncovered;
	
	//aux to drand.neetect adjent cell
	private final int[] di = new int[] { -1, -1, -1, 0, 1, 1,  1,  0 };
	private final int[] dj = new int[] { -1,  0,  1, 1, 1, 0, -1, -1 };
	private final CellStates[] uncoveredStates = new CellStates[] { 
			CellStates.UNC0, CellStates.UNC1, CellStates.UNC2, CellStates.UNC3, 
			CellStates.UNC4, CellStates.UNC5, CellStates.UNC6, CellStates.UNC7, CellStates.UNC8 
	};
	//strong game data into array
	private boolean[][] isMine;
	private int[][] mineCnt;
	private CellStates[][] states;


	public Board(int ncells, int nmines) {
		// TODO Auto-generated constructor stub
		this.n=ncells;
		this.nmines=nmines;
		this.ncovered= ncells*ncells;
		isMine = new boolean[n][n];
		mineCnt = new int[n + 2][n + 2];
		states = new CellStates[n][n];
		generatedMines();
		for(int i=0;i<n;i++) {
			Arrays.fill(states[i],CellStates.COVERED);
		}
		gameStates=GameStates.ONGOING;
		
	}
	public void generatedMines() {
		Random rand= new Random();
		int mines=nmines;
		while(mines-- >0) {
			int position= rand.nextInt(ncovered);
			int x= position % n;
			int y= position / n;
			//if already filled
			if(isMine[y][x]) {
				mines++;
			}else {
				isMine[y][x]=true;
				for (int d = 0; d < di.length; d++) {
					//increase count adjensi cell
					mineCnt[y + di[d] + 1][x + dj[d] + 1]++;
				}
			}
		}	
	}
	
	public  void toogleFlag(int row,int col,Graphics g) {
		if(states[row][col]==CellStates.COVERED) {
			states[row][col]=CellStates.FLAGGED;
		}
		else if(states[row][col]==CellStates.FLAGGED) {
			states[row][col]=CellStates.COVERED;
		}
		Assets.draw(row, col, states[row][col], g);
		g.dispose();
		
	}
	
	private void bfs(int row, int col, Graphics g) {
		Queue<Integer> q = new ArrayDeque<>();
		Set<Integer> visited = new HashSet<>();

		ncovered++;
		q.add(row * n + col);
		visited.add(row * n + col);

		while (!q.isEmpty()) {
			int r = q.peek() / n;
			int c = q.poll() % n;

			if (states[r][c] != CellStates.COVERED)
				continue;

			states[r][c] = uncoveredStates[mineCnt[r + 1][c + 1]];
			Assets.draw(r, c, states[r][c], g);
			ncovered--;

			if (states[r][c] != CellStates.UNC0)
				continue;

			for (int i = 0; i < di.length; i++) {
				int _r = r + di[i];
				int _c = c + dj[i];
				int key = _r * n + _c;
				if (_r < 0 || _r >= n || _c < 0 || _c >= n || visited.contains(key))
					continue;
				q.add(key);
				visited.add(key);
			}
		}

		if (ncovered == nmines)
			gameStates = GameStates.WON;
	}
	public boolean uncoverCell(int row,int col,Graphics g) {
		if(states[row][col]!=CellStates.COVERED) {
			return false;
		}
		if(isMine[row][col]) {
			gameStates=GameStates.LOST;
			uncoverAll(g,false);
			states[row][col]=CellStates.FIRED_MINE;
			Assets.draw(row, col, CellStates.FIRED_MINE, g);
		}
		else {
			ncovered--;
			
			Assets.draw(row, col, uncoveredStates[mineCnt[row + 1][col + 1]], g);
			
			if(nmines==ncovered) {
				gameStates=GameStates.WON;
				uncoverAll(g,true);
			}
			else {
				bfs(row, col, g);
			}
		
		}
		g.dispose();
		return true;
		
	}
	private void uncoverAll(Graphics g, boolean isWin) {
		// TODO Auto-generated method stub
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				
				if(states[i][j]==CellStates.COVERED && isMine[i][j]) {
					states[i][j] = isWin ? CellStates.FLAGGED : CellStates.MINE;
					Assets.draw(i, j, states[i][j], g);
				}else if(states[i][j]==CellStates.FLAGGED && !isMine[i][j]) {
					states[i][j] = CellStates.WRONG_FLAG;
					Assets.draw(i, j, states[i][j], g);
				}
				
			}
		}
		
	}
	public GameStates getGameStates() {
		return gameStates;
	}
	

}
