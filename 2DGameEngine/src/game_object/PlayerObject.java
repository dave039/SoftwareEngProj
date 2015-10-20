package game_object;

import game.GameWindow;

import java.awt.Graphics2D;


public class PlayerObject extends GameObject{
	
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	
	protected int speed;
	protected int health;
	protected int damage;
	
	public PlayerObject(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		image = null;
		
		speed = 0;
		health = 0;
		damage = 0;
	}
	
	public void move(int direction){
		if(direction == PlayerObject.UP){
			y -= speed;
		}
		else if(direction == PlayerObject.LEFT){
			x -= speed;
		}
		else if(direction == PlayerObject.RIGHT){
			x += speed;
		}
		else if(direction == PlayerObject.DOWN){
			y += speed;
		}
	}
	
	public void render(Graphics2D g){
		g.drawImage(image, x, y, width, height, null);
	}
	
	public void update(){
		super.update();
		
		if(x <= 0){
			x = 0;
		}	
		else if(x >= GameWindow.WIDTH - width){
			x = GameWindow.WIDTH - width;
		}
		
		if(y <= 0){
			y = 0;
		}	
		else if(y >= GameWindow.HEIGHT - width - 24){
			y = GameWindow.HEIGHT - width - 24;
		}
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

}
