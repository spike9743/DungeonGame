package states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Enemy;
import entities.Player;
import game.projectiles.Projectile;
import gamestates.GameState;
import gamestates.GameStateManager;
import resources.Resources;
import world.Room;
import world.World;
import world.generator.LevelGenerator;
import world.generator.Populator;

public class DebugState extends GameState {
	
	private LevelGenerator generator;
	private Populator populator;
	private World world;
	private Player player;
	
	
	public DebugState(GameStateManager manager) {
		super(manager);
		this.world = new World(Resources.DEBUG_WORLD_SIZE);
		this.generator = new LevelGenerator(world);
		generator.generate();
		world.setRooms(generator.getRooms());
		this.player = new Player(world);
		//this.populator = new Populator(player,world);
		//populator.populate();
	}

	@Override
	protected void loop() {
		this.player.move();
		for (Enemy enemy : this.world.getRoomAt(player.getWorldX(), player.getWorldY()).getEnemies()) {
			enemy.move();
		}
		for (Projectile projectile:this.world.getProjectiles()) {
			projectile.move();
		}
		this.collisions();
	}
	
	@Override
	protected void mouseMoved(MouseEvent e) {
		this.player.mouseMoved(e);
	}
	
	@Override
	protected void mouseClicked(MouseEvent e) {
		this.player.mouseClicked(e);
	}
	
	private void collisions() {
		Room currentRoom = this.world.getRoomAt(player.getWorldX(), player.getWorldY());
		for(int i=0;i<Resources.WIDTH_IN_TILES;i++) {
			for(int j=0;j<Resources.HEIGHT_IN_TILES;j++) {
				this.player.handleCollisionWith(currentRoom.getTileAt(i, j));
			}
		}
	}
	@Override 
	protected void render(Graphics g) {
		this.world.getRoomAt(player.getWorldX(),player.getWorldY()).render(g);
		this.player.render(g);
		this.player.getGun().render(g);
		for (Projectile projectile:this.world.getProjectiles()) {
			projectile.render(g);
		}
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