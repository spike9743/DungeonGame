package game.states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import framework.gamestates.GameState;
import framework.gamestates.GameStateManager;
import framework.gui.GUI;
import framework.resources.Resources;
import game.entities.Enemy;
import game.entities.Player;
import game.world.Room;
import game.world.World;
import game.world.generator.LevelGenerator;
import game.world.generator.Populator;

public class PlayingState extends GameState {
	
	private LevelGenerator generator;
	private World world;
	private Player player;
	private Populator populator;
	
	protected PlayingState(GameStateManager manager) {
		super(manager);
		this.world = new World(Resources.GAME_WORLD_SIZE);
		this.generator = new LevelGenerator(world);
		generator.generate();
		world.setRooms(generator.getRooms());
		this.player = new Player(world);
		this.populator = new Populator(player,world);
		populator.populate();
	}
	
	@Override
	protected void mouseMoved(MouseEvent e) {
		
	}
	
	protected void mouseClicked(MouseEvent e) {}
	
	@Override
	protected void loop() {
		this.player.move();
		for (Enemy enemy : this.world.getRoomAt(player.getWorldX(), player.getWorldY()).getEnemies()) {
			enemy.move();
		}
		this.collisions();
	}

	private void collisions() {
		Room currentRoom = this.world.getRoomAt(player.getWorldX(), player.getWorldY());
		for(int i=0;i<Resources.WIDTH_IN_TILES;i++) {
			for(int j=0;j<Resources.HEIGHT_IN_TILES;j++) {
				this.player.handleCollisionWithTile(currentRoom.getTileAt(i, j));
				if (currentRoom.hasEnemies()) {
					for(Enemy enemy : currentRoom.getEnemies()) {
						enemy.handleCollisionWithTile(currentRoom.getTileAt(i, j));
					}
				}
			}
		}
	}
	@Override
	protected void render(Graphics g) {
		this.world.getRoomAt(player.getWorldX(),player.getWorldY()).render(g);
		this.player.render(g);
		GUI.render(g,this.player,this.world);
	}
	

	@Override
	protected void keyPressed(int keyCode) {
		switch(keyCode) {
		case KeyEvent.VK_W:
			this.player.setMovingUp(true);
			break;
		case KeyEvent.VK_A:
			this.player.setMovingLeft(true);
			break;
		case KeyEvent.VK_S:
			this.player.setMovingDown(true);
			break;
		case KeyEvent.VK_D:
			this.player.setMovingRight(true);
			break;
		case KeyEvent.VK_Q:
			System.exit(0);
			break;
		}
	}

	@Override
	protected void keyReleased(int keyCode) {
		switch(keyCode) {
		case KeyEvent.VK_W:
			this.player.setMovingUp(false);
			break;
		case KeyEvent.VK_A:
			this.player.setMovingLeft(false);
			break;
		case KeyEvent.VK_S:
			this.player.setMovingDown(false);
			break;
		case KeyEvent.VK_D:
			this.player.setMovingRight(false);
			break;
		}
	}
	
}