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
	//n ukuran cell, nmines banyak mines, ncovered jumlah cell tertutup (n*n)
	private int n,nmines,ncovered;
	
	//array bantuan unutk melakukan bfs ke 8 arah
	private final int[] di = new int[] { -1, -1, -1, 0, 1, 1,  1,  0 };
	private final int[] dj = new int[] { -1,  0,  1, 1, 1, 0, -1, -1 };
	
	//array uncoveredState berisi State berdasarkan jumlah mines
	private final CellStates[] uncoveredStates = new CellStates[] { 
			CellStates.UNC0, CellStates.UNC1, CellStates.UNC2, CellStates.UNC3, 
			CellStates.UNC4, CellStates.UNC5, CellStates.UNC6, CellStates.UNC7, CellStates.UNC8 
	};
	//isMine 2Darray to cek apakah cell tersebut berupa mines
	private boolean[][] isMine;
	//mineCnt 2Darray untuk menyimpan jumlah count mines dissetiap cell
	private int[][] mineCnt;
	//2Darray CellStates untuk menyimpan states setiap cell
	private CellStates[][] states;


	public Board(int ncells, int nmines) {
		
		this.nmines=nmines;
		this.ncovered= ncells*ncells;
		//inisiasi size array
		isMine = new boolean[n][n];
		mineCnt = new int[n + 2][n + 2];
		states = new CellStates[n][n];
		
		//mmebuat mine secara acak
		generatedMines();
		
		//inisiasi cell, isi states semua cell dg covered
		for(int i=0;i<n;i++) {
			Arrays.fill(states[i],CellStates.COVERED);
		}
		gameStates=GameStates.ONGOING;
		
	}
	
	/**
	 * Menempatkan mines secara acak
	 */
	public void generatedMines() {
		Random rand= new Random();
		//mines untuk tracking berapa mines yang harus ditempatkan
		int mines=nmines;
		while(mines-- >0) {
			//Ini buat random
			int position= rand.nextInt(ncovered);
			int x= position % n;
			int y= position / n;
			
			//jika udah ada mines di posisi hasil randoman balikin nilai mines
			if(isMine[y][x]) {
				mines++;
			}
			//jika nggak ada maka
			else {
				//isMines jadi true
				isMine[y][x]=true;
				
				for (int d = 0; d < di.length; d++) {
					//increase count cell di sekitaran mines
					mineCnt[y + di[d] + 1][x + dj[d] + 1]++;
				}
			}
		}	
	}
	
	/**
	 * @param row posisi row
	 * @param col posisi col
	 * @param g utilitas grafik
	 * 
	 * Metode untuk membuat flag, jika cell yang diklik kanan keadanya covered(belum ada flag),maka tambahkan flag
	 * jika sudah ada flag maka hilangkan flag
	 */
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
	
	/**
	 * Implementasi BFS menggunakan Queue
	 * BFS pada minesweeper bisa digambarkan sebagai sebuah graph dimana setiap nodenya
	 * memiliki 8 adjensi node
	 * Disini kita menggunakan bantuan arrray di dan dj untuk menelusuri 8 node tersebut 
	 * Diguanakan arrayDeque untuk mendapat kecepatan yg lebih baik.
	 * dan Element dari queue yang digunakan berupa integer yang merupakan urutan cellnya
	 * (cek metode generated mines)
	 */
	private void bfs(int row, int col, Graphics g) {
		
		//queue berupa integer
		Queue<Integer> q = new ArrayDeque<>();
		Set<Integer> visited = new HashSet<>();
		
		// add cell sekarang ke queue, dengan mengembalikan ke nilai awal integer
		// tandai cell visited
		ncovered++;
		q.add(row * n + col);
		visited.add(row * n + col);
		
		//selama queue nya belum kosong
		while (!q.isEmpty()) {
			//peek = dapatkan nilai teratas
			//poll = retrieve and remove
			int r = q.peek() / n;
			int c = q.poll() % n;
			
			//if cell not covered skip the cell, just continue next iteration
			if (states[r][c] != CellStates.COVERED)
				continue;
			// dapatkan states dari cell sekarang
			// (diambil dari array unCoveredStates dan indenya berdasarkan jumlah mine yag ada
			// disekitar cell itu yang sebelumnya sudah disimpan di mineCnt
			states[r][c] = uncoveredStates[mineCnt[r + 1][c + 1]];
			Assets.draw(r, c, states[r][c], g);
			ncovered--;
			// if cell mempunyai bomb di sekitanya skip ke iterasi selanjutnya
			if (states[r][c] != CellStates.UNC0)
				continue;
			// untuk setiap cell disekeliling cell yang tidak memiliki bomb ( tidak memenuhi kondisi if diatas
			// lakukan pengecekan terhadap cell di sekelilingnya dan masukan cell yang memenuhi dalam queue
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
	
	/**
	 * Fungsi eksekusi untuk menngubah kondisi cell ketika ada input berupa left click
	 */
	public boolean uncoverCell(int row,int col,Graphics g) {
		// jika cell nya sudah dalam kondisi tidak tertutup maka return false
		if(states[row][col]!=CellStates.COVERED) {
			return false;
		}
		// jika cellnya adalah mines, maka permainan selesai dan set gameStatus=LOST, uncover semua cell
		if(isMine[row][col]) {
			gameStates=GameStates.LOST;
			uncoverAll(g,false);
			states[row][col]=CellStates.FIRED_MINE;
			Assets.draw(row, col, CellStates.FIRED_MINE, g);
		}
		else {
			//lakukan bfs
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

		
		//uncover all ketika terkena bomb atau ketika menang
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
