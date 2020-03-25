package com.cms.minespweeper.game;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author seijaku
 * Mouse Listener untuk menerima input mouse
 *
 */
public class MouseEvents implements MouseListener{
	private Game game;
	
	public MouseEvents(Game game) {
		this.game=game;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	//ketika ada mouse yang direlease, cek mouse mana kiri atau kanan, kemudian jika game masih berjalan 
	//dapatkan posisi x y dimana mouse tersebut direlease
	// kemudain fungsi onClick pada class game akan dipanggil
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		boolean isLeft = (e.getButton() == MouseEvent.BUTTON1);
		if (game != null)
			game.onClick(isLeft, e.getX(), e.getY());
	}

}
