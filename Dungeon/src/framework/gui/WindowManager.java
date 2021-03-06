package framework.gui;

import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import framework.resources.Resources;

public class WindowManager {
	
	private JFrame frame;
	private JPanel panel;
	
	
	public WindowManager() {
		this.frame = new JFrame("Dungeon Crawler");
		this.frame.setBounds(70, 70, 0, 0);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);
	}
	
	public void addPanel(JPanel panel) {
		this.panel = panel;
		this.panel.setPreferredSize(new Dimension(Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT));
		this.panel.setFocusable(true);
		this.panel.requestFocusInWindow();
	}
	
	public void addKeyListener(KeyListener listener) {
		try {
			this.panel.addKeyListener(listener);
		} catch(NullPointerException e) {
			System.err.println("[WindowManager]: Error! Tried to add KeyListener before JPanel");
			System.exit(-1);
		}
	}
	
	public void addMouseMotionListener(MouseMotionListener listener) {
		try {
			this.panel.addMouseMotionListener(listener);
		} catch(NullPointerException e) {
			System.err.println("[WindowManager]: Error! Tried to add MouseMotionListener before JPanel");
			System.exit(-1);
		}
	}
	
	public void addMouseListener(MouseListener listener) {
		try {
			this.panel.addMouseListener(listener);
		} catch(NullPointerException e) {
			System.err.println("[WindowManager]: Error! Tried to add MouseMotionListener before JPanel");
			System.exit(-1);
		}
	}
	
	public void createWindow() {
		this.frame.setContentPane(panel);
		this.frame.pack();
		this.frame.setVisible(true);
	}

}
