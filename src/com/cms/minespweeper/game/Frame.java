package com.cms.minespweeper.game;
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Frame {
	
	private JFrame frame;
	private Canvas canvas;
	
	private String title;
	private int width;
	public Frame(JFrame frame, Canvas canvas) {
		super();
		this.frame = frame;
		this.canvas = canvas;
	}

	private int height;

	private void setFrame() {
		frame= new JFrame(title);
		canvas= new Canvas();
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		canvas.setPreferredSize(new Dimension(width,height));
		canvas.setMaximumSize(new Dimension(width,height));
		canvas.setMinimumSize(new Dimension(width,height));
		canvas.setFocusable(false);
		
		frame.add(canvas);
		frame.pack();
	}
	
	public Frame (String title,int w,int h) {
		this.title=title;
		this.width=w;
		this.height=h;
		setFrame();
	}

	public JFrame getFrame() {
		return frame;
	}

	public Canvas getCanvas() {
		return canvas;
	}
}
